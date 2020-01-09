package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.init.ModItems;

import java.util.stream.Stream;

public class GlimmerStarEntity extends GlimmerEntity {
  public GlimmerStarEntity(EntityType<GlimmerStarEntity> type, World world) {
    super(type, world);
  }

  @Override
  public boolean canRelay() {
    return false;
  }

  @Override
  protected void dropSelf() {
    this.entityDropItem(new ItemStack(ModItems.SPAWN_STAR_GLIMMER.get()));
  }

  @Override
  protected Stream<BlockPos> positions() {
    return Stream.of(Direction.values()).map(o -> getPosition().offset(o));
  }
}
