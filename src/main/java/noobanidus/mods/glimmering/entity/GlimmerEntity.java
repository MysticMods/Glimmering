package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

public class GlimmerEntity extends LivingEntity {
  public GlimmerEntity(EntityType<GlimmerEntity> type, World world) {
    super(type, world);
  }

  @Override
  protected void registerData() {
  }

  @Override
  public Iterable<ItemStack> getArmorInventoryList() {
    return null;
  }

  @Override
  public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
    return null;
  }

  @Override
  public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

  }

  @Override
  public HandSide getPrimaryHand() {
    return null;
  }

  @Override
  public IPacket<?> createSpawnPacket() {
    return null;
  }
}
