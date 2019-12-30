package noobanidus.mods.glimmering.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;
import vazkii.patchouli.common.network.NetworkHandler;
import vazkii.patchouli.common.network.message.MessageOpenBookGui;

import java.util.List;

public class TabletItem extends Item {
  public TabletItem(Properties properties) {
    super(properties);
  }

  public static Book getBook() {
    ResourceLocation res = new ResourceLocation("glimmering:glimmering_guide");
    return BookRegistry.INSTANCE.books.get(res);
  }

  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    Book book = getBook();
    if (book != null && book.contents != null) {
      tooltip.add((new StringTextComponent(book.contents.getSubtitle())).applyTextStyle(TextFormatting.GRAY));
    }

  }

  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);
    Book book = getBook();
    if (book == null) {
      return new ActionResult(ActionResultType.FAIL, stack);
    } else {
      if (playerIn instanceof ServerPlayerEntity) {
        NetworkHandler.sendToPlayer(new MessageOpenBookGui(book.resourceLoc.toString()), (ServerPlayerEntity) playerIn);
        SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.book_open);
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, sfx, SoundCategory.PLAYERS, 1.0F, (float) (0.7D + Math.random() * 0.4D));
      }

      return new ActionResult(ActionResultType.SUCCESS, stack);
    }
  }
}
