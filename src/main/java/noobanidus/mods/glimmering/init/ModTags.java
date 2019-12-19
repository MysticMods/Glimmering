package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import noobanidus.mods.glimmering.GLTags;

import java.util.function.Consumer;

public class ModTags implements Consumer<RegistrateTagsProvider<Item>> {
  public static ModTags instance = new ModTags();

  @Override
  public void accept(RegistrateTagsProvider<Item> ctx) {
    ctx.getBuilder(GLTags.Items.ELIGIBLE_DUSTS).add(Items.REDSTONE, Items.GLOWSTONE_DUST, Items.LAPIS_LAZULI, Items.SUGAR, Items.GUNPOWDER, Items.BLAZE_POWDER).build(GLTags.Items.ELIGIBLE_DUSTS.getId());
  }
}
