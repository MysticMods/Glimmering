package noobanidus.mods.glimmering.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import noobanidus.mods.glimmering.graph.EnergyGraph;
import noobanidus.mods.glimmering.graph.EnergyGraph.NodeType;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;
import noobanidus.mods.glimmering.network.BeamMessage;
import noobanidus.mods.glimmering.network.Networking;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GlimmerEntity extends LivingEntity {
  public static final double RANGE = 11.5;
  public static final int DELAY = 3 * 20;
  public int lastPowered = 0;
  private boolean justLoaded = false;

  public static IDataSerializer<NodeType> NODE_SERIALIZER = new IDataSerializer<NodeType>() {
    @Override
    public void write(PacketBuffer packetBuffer, NodeType nodeType) {
      packetBuffer.writeInt(nodeType.ordinal());
    }

    @Override
    public NodeType read(PacketBuffer packetBuffer) {
      return NodeType.byIndex(packetBuffer.readInt());
    }

    @Override
    public NodeType copyValue(NodeType nodeType) {
      return nodeType;
    }
  };

  public static final DataParameter<NodeType> TYPE = EntityDataManager.createKey(GlimmerEntity.class, NODE_SERIALIZER);

  private static List<ItemStack> EMPTY_INVENTORY = new ArrayList<>();

  public GlimmerEntity(EntityType<GlimmerEntity> type, World world) {
    super(type, world);
  }

  @Override
  protected void registerData() {
    super.registerData();
    getDataManager().register(TYPE, NodeType.RELAY);
  }

  @Override
  public Iterable<ItemStack> getArmorInventoryList() {
    return EMPTY_INVENTORY;
  }

  @Override
  public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
    return ItemStack.EMPTY;
  }

  @Override
  public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
  }

  @Override
  public HandSide getPrimaryHand() {
    return HandSide.LEFT;
  }

  @Override
  public boolean canAttack(EntityType<?> typeIn) {
    return false;
  }

  @Override
  public boolean canBreatheUnderwater() {
    return true;
  }

  @Override
  protected boolean canDropLoot() {
    return true; // ???
  }

  @Override
  public boolean canAttack(LivingEntity target) {
    return false;
  }

  @Override
  public void tick() {
    super.tick();

    if (justLoaded) {
      world.getProfiler().startSection("Initial Load");
      justLoaded = false;
      updateGraph(false);
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

    if (getDataManager().get(TYPE) == NodeType.RECEIVE) {
      world.getProfiler().startSection("Finding Requirements");
      List<Requirement> requirements = required();
      if (requirements != null) {
        requirements.removeIf(Requirement::isMaxed);
      }
      world.getProfiler().endSection();
      world.getProfiler().startSection("Fulfilling Requirements");
      if (requirements != null && !requirements.isEmpty()) {
        for (GlimmerEntity glimmer : new EnergyGraph.EntityIter(this)) {
          if (glimmer == null) {
            continue;
          }
          glimmer.supply(this, requirements);
          requirements.removeIf(Requirement::isMaxed);
          if (requirements.isEmpty()) {
            break;
          }
        }
      }
      world.getProfiler().endSection();
    }

    world.getProfiler().endSection();
  }

  @Override
  public void livingTick() {
    if (this.world.isRemote) {
    }

    super.livingTick();
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

  public void updateGraph (boolean force) {
    if (force) {
      EnergyGraph.clearEntity(this);
    }
    List<GlimmerEntity> list = world.getEntitiesWithinAABB(ModEntities.GLIMMER.get(), new AxisAlignedBB(getPosition()).grow(RANGE), (e) -> !e.equals(this)).stream().map(e -> (GlimmerEntity) e).collect(Collectors.toList());
    if (!list.isEmpty()) {
      EnergyGraph.addEntity(this, list);
    }
  }

  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getTrueSource() != null && source.getTrueSource() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) source.getTrueSource();
      ItemStack stack = player.getHeldItemMainhand();
      if (stack.getItem() == ModItems.RITUAL_KNIFE.get()) {
        if (player.isSneaking()) {
          this.entityDropItem(new ItemStack(ModEntities.SPAWN_GLIMMER.get()));
          this.remove();
          return true;
        } else {
          int current = getDataManager().get(TYPE).ordinal();
          if (current >= 2) {
            current = 0;
          } else {
            current++;
          }
          getDataManager().set(TYPE, NodeType.byIndex(current));
          if (!player.world.isRemote) {
            player.sendMessage(new TranslationTextComponent("glimmering.message.type_change", new TranslationTextComponent("glimmering.node.type." + current)).setStyle(new Style().setColor(TextFormatting.GOLD)));
          }
          updateGraph(true);
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
    super.knockBack(entityIn, strength, xRatio, zRatio); // ???
  }

  @Override
  public boolean canAttack(LivingEntity livingentityIn, EntityPredicate predicateIn) {
    return false;
  }

  @Override
  public boolean isEntityUndead() {
    return false;
  }

  @Nullable
  @Override
  public LivingEntity getRevengeTarget() {
    return null;
  }

  @Override
  public boolean isChild() {
    return false;
  }

  @Override
  public boolean isOnLadder() {
    return false;
  }

  @Override
  public void fall(float distance, float damageMultiplier) {
  }

  @Override
  public boolean canEntityBeSeen(Entity entityIn) {
    return true;
  }

  @Override
  public void applyEntityCollision(Entity entityIn) {
  }

  @Override
  public void addVelocity(double x, double y, double z) {
  }

  @Override
  protected boolean isMovementBlocked() {
    return true;
  }

  @Override
  public boolean canBePushed() {
    return false;
  }

  @Override
  public void setFire(int seconds) {
  }

  @Override
  public boolean isSilent() {
    return true;
  }

  @Override
  public boolean hasNoGravity() {
    return true;
  }

  @Override
  public boolean canSwim() {
    return true;
  }

  @Override
  protected boolean canTriggerWalking() {
    return false;
  }

  @Override
  public int getAir() {
    return 300;
  }

  @Override
  public boolean isImmuneToExplosions() {
    return true;
  }

  @Override
  public void onKillCommand() {
    super.onKillCommand(); // ???
  }

  @Override
  public boolean canBeHitWithPotion() {
    return false;
  }

  @Override
  public boolean isSleeping() {
    return false;
  }

  @Override
  protected void collideWithNearbyEntities() {
  }

  @Override
  protected void collideWithEntity(Entity entityIn) {
  }

  @Override
  public void travel(Vec3d p_213352_1_) {
    return;
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

  @Nullable
  public List<Requirement> required() {
    world.getProfiler().startSection("Gathering Requirements");
    List<Requirement> requirements = new ArrayList<>();

    for (Direction dir : Direction.values()) {
      TileEntity te = world.getTileEntity(getPosition().offset(dir));
      if (te != null) {
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent((cap) -> {
          requirements.add(new Requirement(cap));
        });
      }
    }

    requirements.removeIf(Requirement::isMaxed);
    world.getProfiler().endSection();

    return requirements;
  }

  public List<Requirement> supply(GlimmerEntity entity, List<Requirement> requirements) {
    world.getProfiler().startSection("Supplying Required Power from " + getEntityId());
    for (Direction dir : Direction.values()) {
      if (requirements.isEmpty()) {
        break;
      }

      TileEntity te = world.getTileEntity(getPosition().offset(dir));
      if (te != null) {
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent((cap) -> {
          if (!cap.canExtract()) {
            return;
          }

          requirements.removeIf(Requirement::isMaxed);

          for (Requirement req : requirements) {
            int canExtract = cap.extractEnergy(req.getRequired(), true);
            int accepted = req.getHandler().receiveEnergy(canExtract, false);
            cap.extractEnergy(accepted, false);
            req.setRequired(req.getRequired() - accepted);
            entity.lastPowered = entity.ticksExisted;
          }
        });
      }
    }
    world.getProfiler().endSection();

    return requirements;
  }

  @Override
  public ITextComponent getName() {
    switch (getDataManager().get(TYPE)) {
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
    compound.putInt("glimmer_type", getDataManager().get(TYPE).ordinal());
  }

  @Override
  public void readAdditional(CompoundNBT compound) {
    super.readAdditional(compound);
    if (compound.contains("glimmer_type")) {
      getDataManager().set(TYPE, NodeType.byIndex(compound.getInt("glimmer_type")));
    }
  }

  public static class Requirement {
    private IEnergyStorage handler;
    private int required;

    public Requirement(IEnergyStorage handler) {
      this.handler = handler;
      this.required = handler.receiveEnergy(Integer.MAX_VALUE, true);
    }

    public IEnergyStorage getHandler() {
      return handler;
    }

    public int getRequired() {
      return required;
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
