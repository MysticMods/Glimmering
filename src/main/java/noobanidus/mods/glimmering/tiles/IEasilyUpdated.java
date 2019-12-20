package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IEasilyUpdated {
  @Nullable
  World getWorld();

  BlockPos getPos();

  default void updateViaState() {
    if (getWorld() != null) {
      BlockState state = getWorld().getBlockState(getPos());
      getWorld().notifyBlockUpdate(getPos(), state, state, 8);
    }
  }
}
