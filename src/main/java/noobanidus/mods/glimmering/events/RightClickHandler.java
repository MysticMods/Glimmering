package noobanidus.mods.glimmering.events;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModItems;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

public class RightClickHandler {
  public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
    PlayerEntity player = event.getPlayer();
    if (!player.isServerWorld()) {
      return;
    }
    Hand hand = event.getHand();
    ItemStack stack = player.getHeldItem(hand);
    if (stack.getItem() != ModItems.RITUAL_KNIFE.get()) {
      return;
    }

    World world = event.getWorld();
    BlockState state = world.getBlockState(event.getPos());
    if (state.getBlock() != ModBlocks.RITUAL_RUNE.get()) {
      return;
    }

    TileEntity te = world.getTileEntity(event.getPos());
    if (te instanceof RitualRuneTile) {
      ((RitualRuneTile) te).activate(player);
      event.setUseItem(Event.Result.DENY);
      event.setUseBlock(Event.Result.DENY);
      event.setCanceled(true);
      event.setCancellationResult(ActionResultType.SUCCESS);
    }
  }
}
