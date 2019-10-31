package noobanidus.mods.glimmering.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

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
}
