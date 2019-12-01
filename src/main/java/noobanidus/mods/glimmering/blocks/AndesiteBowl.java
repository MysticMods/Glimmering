package noobanidus.mods.glimmering.blocks;

import epicsquid.mysticallib.util.VoxelUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("deprecation")
public class AndesiteBowl extends Block {
  private static VoxelShape SHAPE = VoxelUtil.multiOr(Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0), Block.makeCuboidShape(0.0, 11.0, 0.0, 2.0, 17.0, 16.0), Block.makeCuboidShape(14.0, 11.0, 0.0, 16.0, 17.0, 16.0), Block.makeCuboidShape(0.0, 9.0, 0.0, 16.0, 11.0, 16.0), Block.makeCuboidShape(2.0, 11.0, 0.0, 14.0, 17.0, 2.0), Block.makeCuboidShape(2.0, 11.0, 14.0, 14.0, 17.0, 16.0));

  public AndesiteBowl(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }
}
