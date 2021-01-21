package noobanidus.mods.glimmering.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import noobanidus.mods.glimmering.init.ModTiles;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.util.VoxelUtil;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class AndesiteBowlBlock extends Block {
  private static VoxelShape SHAPE = VoxelUtil.multiOr(Block.makeCuboidShape(4, 0, 4,12, 8, 12), Block.makeCuboidShape(0, 10, 0,2, 16, 16), Block.makeCuboidShape(14, 10, 0,16, 16, 16), Block.makeCuboidShape(0, 8, 0,16, 10, 16), Block.makeCuboidShape(2, 10, 0,14, 16, 2), Block.makeCuboidShape(2, 10, 14,14, 16, 16));

  public AndesiteBowlBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new AndesiteBowlTile(ModTiles.ANDESITE_BOWL.get());
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    TileEntity te = worldIn.getTileEntity(pos);
    if (te instanceof AndesiteBowlTile) {
      return ((AndesiteBowlTile) te).onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
  }

  @Override
  public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock() && !world.isRemote()) {
      TileEntity te = world.getTileEntity(pos);
      if (te instanceof AndesiteBowlTile) {
        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((cap) -> {
          for (int i = 0; i < cap.getSlots(); i++) {
            ItemStack stack = cap.extractItem(i, cap.getSlotLimit(i), false);
            if (!stack.isEmpty()) {
              InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            }
          }
        });
      }
    }
    super.onReplaced(state, world, pos, newState, isMoving);
  }
}
