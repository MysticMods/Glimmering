package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.init.ModBlocks;

import javax.annotation.Nullable;

public class AndesiteBowlTile extends TileEntity implements IEasilyUpdated {
  public ItemStackHandler inventory = new ItemStackHandler(1) {
    @Override
    protected void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      AndesiteBowlTile.this.markDirty();
      updateViaState(AndesiteBowlTile.this);
    }
  };

  public AndesiteBowlTile(TileEntityType<? extends AndesiteBowlTile> tile) {
    super(tile);
  }

  @Override
  public void read(BlockState state, CompoundNBT compound) {
    super.read(state, compound);
    inventory.deserializeNBT(compound.getCompound("inventory"));
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    CompoundNBT result = super.write(compound);
    CompoundNBT nbt = inventory.serializeNBT();
    result.put("inventory", nbt);
    return result;
  }

  @Override
  public CompoundNBT getUpdateTag() {
    return this.write(new CompoundNBT());
  }

  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    return new SUpdateTileEntityPacket(this.pos, 9, getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    read(ModBlocks.ANDESITE_BOWL.get().getDefaultState(), pkt.getNbtCompound());
  }

  public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    if (world.isRemote()) {
      return ActionResultType.FAIL;
    }

    if (hand != Hand.MAIN_HAND) {
      return ActionResultType.FAIL;
    }

    ItemStack held = player.getHeldItem(hand);
    if (held.isEmpty()) {
      ItemStack inSlot = inventory.getStackInSlot(0);
      if (inSlot.isEmpty()) {
        return ActionResultType.FAIL;
      }

      inSlot = inventory.extractItem(0, inSlot.getCount(), false);
      player.setHeldItem(hand, inSlot);
      return ActionResultType.SUCCESS;
    }

    if (!held.getItem().isIn(GLTags.Items.ELIGIBLE_DUSTS)) {
      return ActionResultType.FAIL;
    }

    player.setHeldItem(hand, inventory.insertItem(0, held, false));
    return ActionResultType.SUCCESS;
  }
}
