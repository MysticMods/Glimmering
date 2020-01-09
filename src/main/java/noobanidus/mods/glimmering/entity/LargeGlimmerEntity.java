package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.init.ModItems;
import noobanidus.mods.glimmering.util.BlockPosUtil;

import java.util.stream.Stream;

public class LargeGlimmerEntity extends GlimmerEntity {
  public LargeGlimmerEntity(EntityType<LargeGlimmerEntity> type, World world) {
    super(type, world);
  }

  @Override
  public boolean canRelay() {
    return false;
  }

  @Override
  protected Stream<BlockPos> positions() {
    return BlockPosUtil.getAllInBox(new AxisAlignedBB(getPosition()).grow(3));
  }

  @Override
  protected void dropSelf() {
    this.entityDropItem(new ItemStack(ModItems.SPAWN_LARGE_GLIMMER.get()));
  }
}
