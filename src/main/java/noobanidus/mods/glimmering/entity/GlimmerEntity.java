package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GlimmerEntity extends LivingEntity {
  private static List<ItemStack> EMPTY_INVENTORY = new ArrayList<>();

  public GlimmerEntity(EntityType<GlimmerEntity> type, World world) {
    super(type, world);
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
  public boolean attackEntityFrom(DamageSource source, float amount) {
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
}
