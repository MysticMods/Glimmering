package noobanidus.mods.glimmering.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;

public class GlimmerSpawnItem extends SpawnEggItem {
  public GlimmerSpawnItem(Properties builder) {
    super(ModEntities.GLIMMER.get(), 0x418594, 0x211D15, builder);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (worldIn == null) {
      return;
    }
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line0").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line1", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line2", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
  }
}
