package noobanidus.mods.glimmering.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class RitualRuneBlock extends Block {
  public static BooleanProperty ACTIVE = BooleanProperty.create("active");

  public RitualRuneBlock(Properties properties) {
    super(properties);
    this.setDefaultState(getDefaultState().with(ACTIVE, false));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(ACTIVE);
  }

  @Override
  public int getLightValue(BlockState state) {
    if (state.getBlock() == this && state.get(ACTIVE)) {
      return 15;
    }

    return 0;
  }

  @Override
  public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
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
