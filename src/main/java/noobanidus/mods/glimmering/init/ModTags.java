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
    ctx.getBuilder(GLTags.Items.INCONSEQUENTIAL_DUSTS).add(Items.SUGAR).build(GLTags.Items.INCONSEQUENTIAL_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.INFERIOR_DUSTS).add(Items.GUNPOWDER).build(GLTags.Items.INFERIOR_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.COMMON_DUSTS).add(Items.LAPIS_LAZULI).build(GLTags.Items.COMMON_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.SUPERIOR_DUSTS).add(Items.REDSTONE).build(GLTags.Items.SUPERIOR_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.EMPOWERED_DUSTS).add(Items.GLOWSTONE_DUST).build(GLTags.Items.EMPOWERED_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.MYTHICAL_DUSTS).add(Items.BLAZE_POWDER).build(GLTags.Items.MYTHICAL_DUSTS.getId());
    ctx.getBuilder(GLTags.Items.ELIGIBLE_DUSTS).add(GLTags.Items.INCONSEQUENTIAL_DUSTS, GLTags.Items.INFERIOR_DUSTS, GLTags.Items.COMMON_DUSTS, GLTags.Items.SUPERIOR_DUSTS, GLTags.Items.EMPOWERED_DUSTS, GLTags.Items.MYTHICAL_DUSTS).build(GLTags.Items.ELIGIBLE_DUSTS.getId());
  }
}
