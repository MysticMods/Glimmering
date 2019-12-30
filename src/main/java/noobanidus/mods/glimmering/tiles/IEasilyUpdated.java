package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IEasilyUpdated {
  default void updateViaState(TileEntity te) {
    if (te.getWorld() != null) {
      BlockState state = te.getWorld().getBlockState(te.getPos());
      te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 8);
    }
  }
}
