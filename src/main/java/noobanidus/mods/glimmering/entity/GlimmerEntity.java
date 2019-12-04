package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import noobanidus.mods.glimmering.graph.EnergyGraph;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GlimmerEntity extends LivingEntity {
  public static final double RANGE = 8.5;

  public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(GlimmerEntity.class, DataSerializers.VARINT);
  // 0 = Relay
  // 1 = Transmit
  // 2 = Receive
  private static List<ItemStack> EMPTY_INVENTORY = new ArrayList<>();

  public GlimmerEntity(EntityType<GlimmerEntity> type, World world) {
    super(type, world);
  }

  @Override
  protected void registerData() {
    super.registerData();
    getDataManager().register(TYPE, 0);
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

    if (world.isRemote()) return;

    // Remove all pairs with this entity
    if (this.ticksExisted % 20 == 0) {
      updateGraph();
    }

    EnergyGraph.findEnergyFor(this);
  }

  public void updateGraph() {
    List<Entity> list = world.getEntitiesWithinAABB(ModEntities.GLIMMER.get(), new AxisAlignedBB(getPosition()).grow(RANGE), (e) -> true);
    list.removeIf(o -> o.getUniqueID() == this.getUniqueID());
    EnergyGraph.clearEntity(this);
    if (!list.isEmpty()) {
      EnergyGraph.addEntity(this, list);
    }
  }

  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getTrueSource() != null && source.getTrueSource() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) source.getTrueSource();
      if (player.isSneaking()) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getItem() == ModItems.RITUAL_KNIFE.get()) {
          this.entityDropItem(new ItemStack(ModEntities.SPAWN_GLIMMER.get()));
          EnergyGraph.clearEntity(this);
          this.remove();
          return true;
        }
      } else {
        // Toggle stuff
        // 0 = Relay
        // 1 = Transmit
        // 2 = Receive
        int current = getDataManager().get(TYPE);
        if (current >= 2) {
          current = 0;
        } else {
          current++;
        }
        getDataManager().set(TYPE, current);
        if (!player.world.isRemote) {
          player.sendMessage(new TranslationTextComponent("glimmering.message.type_change", new TranslationTextComponent("glimmering.node.type." + current)).setStyle(new Style().setColor(TextFormatting.GOLD)));
        }
        return true;
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

  public int push(int amount) {
    return energyOperation(amount, false);
  }

  public int desired() {
    return energyOperation(0, true);
  }

  private int energyOperation(int amount, boolean simulate) {
    if (getDataManager().get(TYPE) != 2) {
      return 0;
    }

    for (Direction dir : Direction.values()) {
      TileEntity te = world.getTileEntity(getPosition().offset(dir));
      if (te != null) {
        LazyOptional<IEnergyStorage> opt = te.getCapability(CapabilityEnergy.ENERGY);
        if (opt.isPresent()) {
          IEnergyStorage cap = opt.orElseThrow(IllegalStateException::new);
          if (simulate) {
            amount += cap.receiveEnergy(Integer.MAX_VALUE, true);
          } else {
            amount -= cap.receiveEnergy(amount, false);
          }
        }
      }
    }

    return amount;
  }

  public int pull(int amount) {
    if (getDataManager().get(TYPE) != 1) {
      return 0;
    }

    int gathered = 0;

    for (Direction dir : Direction.values()) {
      TileEntity te = world.getTileEntity(getPosition().offset(dir));
      if (te != null) {
        LazyOptional<IEnergyStorage> opt = te.getCapability(CapabilityEnergy.ENERGY);
        if (opt.isPresent()) {
          IEnergyStorage cap = opt.orElseThrow(IllegalStateException::new);
          gathered += cap.extractEnergy(amount - gathered, false);
          if (gathered >= amount) {
            break;
          }
        }
      }
    }

    return gathered;
  }

  @Override
  public ITextComponent getName() {
    switch (getDataManager().get(TYPE)) {
      default:
      case 0:
        return new TranslationTextComponent("glimmering.node.type.0");
      case 1:
        return new TranslationTextComponent("glimmering.node.type.1");
      case 2:
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
}
