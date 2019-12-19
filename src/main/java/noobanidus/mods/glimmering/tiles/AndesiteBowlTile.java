package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.init.ModTiles;

public class AndesiteBowlTile extends TileEntity {
  public ItemStackHandler inventory = new ItemStackHandler(1) {
    @Override
    protected void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      AndesiteBowlTile.this.markDirty();
      updatePacketViaState();
    }
  };

  public AndesiteBowlTile() {
    super(ModTiles.ANDESITE_BOWL.get());
  }

  public void updatePacketViaState() {
    if (world != null) {
      BlockState state = world.getBlockState(getPos());
      world.notifyBlockUpdate(getPos(), state, state, 8);
    }
  }

  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    ItemStack held = player.getHeldItem(hand);
    if (held.isEmpty()) {
      ItemStack inSlot = inventory.getStackInSlot(0);
      if (inSlot.isEmpty()) {
        return false;
      }

      inSlot = inventory.extractItem(0, inSlot.getCount(), false);
      player.setHeldItem(hand, inSlot);
      return true;
    }

    if (!held.getItem().isIn(GLTags.Items.ELIGIBLE_DUSTS)) {
      return false;
    }

    ItemStack inserted = inventory.insertItem(0, held, true);
    if (!inserted.isEmpty() && inserted.getCount() == held.getCount()) {
      return false;
    }

    int leftover = inserted.getCount();
    inventory.insertItem(0, held, false);

    if (leftover != 0) {
      held.setCount(leftover);
      player.setHeldItem(hand, held);
    }

    return true;
  }
}
