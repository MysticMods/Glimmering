package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import noobanidus.mods.glimmering.GLTags;

import java.util.function.Consumer;

public class ModTags implements Consumer<RegistrateTagsProvider<Item>> {
  public static ModTags instance = new ModTags();

  @SuppressWarnings("unchecked")
  @Override
  public void accept(RegistrateTagsProvider<Item> ctx) {
    ctx.getOrCreateBuilder(GLTags.Items.INCONSEQUENTIAL_DUSTS).add(Items.SUGAR);
    ctx.getOrCreateBuilder(GLTags.Items.INFERIOR_DUSTS).add(Items.GUNPOWDER);
    ctx.getOrCreateBuilder(GLTags.Items.COMMON_DUSTS).add(Items.LAPIS_LAZULI);
    ctx.getOrCreateBuilder(GLTags.Items.SUPERIOR_DUSTS).add(Items.REDSTONE);
    ctx.getOrCreateBuilder(GLTags.Items.EMPOWERED_DUSTS).add(Items.GLOWSTONE_DUST);
    ctx.getOrCreateBuilder(GLTags.Items.MYTHICAL_DUSTS).add(Items.BLAZE_POWDER);
    ctx.getOrCreateBuilder(GLTags.Items.ELIGIBLE_DUSTS).addTags(GLTags.Items.INCONSEQUENTIAL_DUSTS, GLTags.Items.INFERIOR_DUSTS, GLTags.Items.COMMON_DUSTS, GLTags.Items.SUPERIOR_DUSTS, GLTags.Items.EMPOWERED_DUSTS, GLTags.Items.MYTHICAL_DUSTS);
  }
}
