package noobanidus.mods.glimmering.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import javax.annotation.Nullable;

public class RitualRuneBlock extends Block {
  public RitualRuneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new RitualRuneTile();
  }
}
