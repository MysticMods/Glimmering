package noobanidus.mods.glimmering.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;

public class GlimmerSpawnItem extends SpawnEggItem {
  public GlimmerSpawnItem(EntityType<?> typeIn, int primaryColorIn, int secondaryColorIn, Properties builder) {
    super(typeIn, primaryColorIn, secondaryColorIn, builder);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line0").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line1", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line2", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
  }
}
