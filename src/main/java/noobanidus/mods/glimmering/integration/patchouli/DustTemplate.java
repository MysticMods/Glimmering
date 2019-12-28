package noobanidus.mods.glimmering.integration.patchouli;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import noobanidus.mods.glimmering.GLTags;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

public class DustTemplate implements IComponentProcessor {
  private Tag<Item> dustTag;

  @Override
  public void setup(IVariableProvider<String> iVariableProvider) {
    String tagName = iVariableProvider.get("tag");
    switch (tagName) {
      default:
      case "glimmer_dusts":
        dustTag = GLTags.Items.ELIGIBLE_DUSTS;
        break;
      case "mythical_dusts":
        dustTag = GLTags.Items.MYTHICAL_DUSTS;
        break;
      case "empowered_dusts":
        dustTag = GLTags.Items.EMPOWERED_DUSTS;
        break;
      case "superior_dusts":
        dustTag = GLTags.Items.SUPERIOR_DUSTS;
        break;
      case "common_dusts":
        dustTag = GLTags.Items.COMMON_DUSTS;
        break;
      case "inferior_dusts":
        dustTag = GLTags.Items.INFERIOR_DUSTS;
        break;
      case "inconsequential_dusts":
        dustTag = GLTags.Items.INCONSEQUENTIAL_DUSTS;
        break;
    }
  }

  @Override
  public String process(String s) {
    if (s.startsWith("result")) {
      return ItemStackUtil.serializeIngredient(Ingredient.fromTag(dustTag));
    }
    return null;
  }
}
