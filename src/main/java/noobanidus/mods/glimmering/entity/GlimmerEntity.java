package noobanidus.mods.glimmering.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import noobanidus.mods.glimmering.energy.EnergyGraph;
import noobanidus.mods.glimmering.energy.EnergyGraph.NodeType;
import noobanidus.mods.glimmering.energy.EnergyTick;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;
import noobanidus.mods.glimmering.network.BeamMessage;
import noobanidus.mods.glimmering.network.Networking;
import noobanidus.mods.glimmering.util.BlockPosUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlimmerEntity extends UnlivingEntity {
  private static final double RANGE = 11.5;
  private static final int DELAY = 3 * 20;
  public int lastPowered = 0;
  private boolean justLoaded = false;

  public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(GlimmerEntity.class, DataSerializers.VARINT);

  public GlimmerEntity(EntityType<? extends GlimmerEntity> type, World world) {
    super(type, world);
  }

  @Override
  protected void registerData() {
    super.registerData();
    getDataManager().register(TYPE, NodeType.RELAY.ordinal());
  }

  @Override
  protected boolean canDropLoot() {
    return true; // ???
  }

  @Override
  public void tick() {
    super.tick();

    if (justLoaded) {
      world.getProfiler().startSection("Initial Load");
      justLoaded = false;
      updateGraph();
      world.getProfiler().endSection();
    }

    world.getProfiler().startSection("Tick");
    if (world.isRemote()) return;

    // Remove all pairs with this entity
    /*if (this.ticksExisted % (20 * 4) == 0) {
      world.getProfiler().startSection("Updating Graph");
      updateGraph();
      world.getProfiler().endSection();
    }*/

    if (getDataManager().get(TYPE) == NodeType.RECEIVE.ordinal()) {
      world.getProfiler().startSection("Finding Requirements");
      List<Requirement> requirements = required();
      world.getProfiler().endSection();
      if (requirements != null && !requirements.isEmpty()) {
        EnergyTick.addGlimmer(this);
      }
    }

    world.getProfiler().endSection();
  }

  @Override
  public void remove(boolean p_remove_1_) {
    EnergyGraph.clearEntity(this);
    super.remove(p_remove_1_);
  }

  @Override
  public void onAddedToWorld() {
    justLoaded = true;
    super.onAddedToWorld();
  }

  public boolean canRelay() {
    return true;
  }

  public void updateGraph() {
    AxisAlignedBB axis = new AxisAlignedBB(getPosition()).grow(RANGE);
    List<Entity> list = world.getEntitiesWithinAABB(ModEntities.GLIMMER_TYPE.get(), axis, (e) -> !e.equals(this));
    list.addAll(world.getEntitiesWithinAABB(ModEntities.GLIMMER_STAR_TYPE.get(), axis, (e) -> !e.equals(this)));
    list.addAll(world.getEntitiesWithinAABB(ModEntities.LARGE_GLIMMER_TYPE.get(), axis, (e) -> !e.equals(this)));
    if (!list.isEmpty()) {
      EnergyGraph.addEntity(this, list);
    }
  }

  protected NodeType cycleType() {
    int current = getDataManager().get(TYPE);
    if (current >= 2) {
      if (canRelay()) {
        current = 0;
      } else {
        current = 1;
      }
    } else {
      current++;
    }
    return NodeType.byIndex(current);
  }

  protected void dropSelf() {
    this.entityDropItem(new ItemStack(ModItems.SPAWN_GLIMMER.get()));
  }

  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getTrueSource() != null && source.getTrueSource() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) source.getTrueSource();
      ItemStack stack = player.getHeldItemMainhand();
      if (stack.getItem() == ModItems.RITUAL_KNIFE.get()) {
        if (player.isSneaking()) {
          this.dropSelf();
          this.remove();
          return true;
        } else {
          NodeType current = cycleType();
          EnergyGraph.clearEntity(this);
          getDataManager().set(TYPE, current.ordinal());
          if (!player.world.isRemote) {
            player.sendMessage(new TranslationTextComponent("glimmering.message.type_change", new TranslationTextComponent("glimmering.node.type." + current.toString().toLowerCase())).setStyle(new Style().setColor(TextFormatting.GOLD)));
          }
          updateGraph();
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void onKillCommand() {
    super.onKillCommand(); // ???
  }

  public boolean recentlyPowered() {
    return lastPowered != 0 && ((ticksExisted - lastPowered) < DELAY);
  }

  @Override
  public boolean processInitialInteract(PlayerEntity player, Hand hand) {
    ItemStack held = player.getHeldItem(hand);
    if (held.getItem() == ModItems.RITUAL_KNIFE.get() && !player.world.isRemote()) {
      BeamMessage message = new BeamMessage(Lists.newArrayList(EnergyGraph.getEdgesFrom(this)));
      Networking.sendTo(message, (ServerPlayerEntity) player);
    } else {
      return super.processInitialInteract(player, hand);
    }

    return false;
  }

  protected Stream<BlockPos> positions() {
    return BlockPosUtil.getAllInBox(new AxisAlignedBB(getPosition()).grow(1));
  }

  @Nullable
  public List<Requirement> required() {
    world.getProfiler().startSection("Gathering Requirements");
    List<Requirement> requirements = new ArrayList<>();

    positions().forEach(tePos -> {
      TileEntity te = world.getTileEntity(tePos);
      if (te != null) {
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent((cap) -> {
          requirements.add(new Requirement(tePos.toImmutable(), cap));
        });
      }
    });

    requirements.removeIf(Requirement::isMaxed);
    world.getProfiler().endSection();

    return requirements;
  }

  public List<Requirement> supply(GlimmerEntity entity, List<Requirement> requirements) {
    world.getProfiler().startSection("Supplying Required Power from " + getEntityId());
    positions().forEach(tePos -> {
      if (requirements.isEmpty()) {
        return;
      }

      TileEntity te = world.getTileEntity(tePos);
      if (te != null) {
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent((cap) -> {
          if (!cap.canExtract()) {
            return;
          }

          requirements.removeIf(Requirement::isMaxed);

          for (Requirement req : requirements) {
            if (req.getLocation().equals(tePos)) {
              continue;
            }
            int canExtract = cap.extractEnergy(req.getRequired(), true);
            int accepted = req.getHandler().receiveEnergy(canExtract, false);
            cap.extractEnergy(accepted, false);
            req.setRequired(req.getRequired() - accepted);
            entity.lastPowered = entity.ticksExisted;
          }
        });
      }
    });
    world.getProfiler().endSection();

    return requirements;
  }

  @Override
  public ITextComponent getName() {
    switch (NodeType.byIndex(getDataManager().get(TYPE))) {
      default:
      case RELAY:
        return new TranslationTextComponent("glimmering.node.type.0");
      case TRANSMIT:
        return new TranslationTextComponent("glimmering.node.type.1");
      case RECEIVE:
        return new TranslationTextComponent("glimmering.node.type.2");
    }
  }

  @Override
  public void writeAdditional(CompoundNBT compound) {
    super.writeAdditional(compound);
    compound.putInt("glimmer_type", getDataManager().get(TYPE));
  }

  @Override
  public void readAdditional(CompoundNBT compound) {
    super.readAdditional(compound);
    if (compound.contains("glimmer_type")) {
      getDataManager().set(TYPE, compound.getInt("glimmer_type"));
    }
  }

  public static class Requirement {
    private IEnergyStorage handler;
    private int required;
    private BlockPos location;

    public Requirement(BlockPos location, IEnergyStorage handler) {
      this.handler = handler;
      this.required = handler.receiveEnergy(Integer.MAX_VALUE, true);
      this.location = location;
    }

    public IEnergyStorage getHandler() {
      return handler;
    }

    public int getRequired() {
      return required;
    }

    public BlockPos getLocation() {
      return location;
    }

    public void setRequired(int required) {
      this.required = required;
    }

    public boolean isFull() {
      if (!this.handler.canReceive()) {
        return true;
      }

      return this.handler.getEnergyStored() >= this.handler.getMaxEnergyStored();
    }

    public boolean isMaxed() {
      return this.isFull() || this.getRequired() <= 0;
    }
  }
}
