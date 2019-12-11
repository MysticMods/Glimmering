package noobanidus.mods.glimmering.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;

import static noobanidus.mods.glimmering.GLTags.Items.ELIGIBLE_DUSTS;

public class GLItemTagsProvider extends ItemTagsProvider {
  public GLItemTagsProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }

  @Override
  public String getName() {
    return "Glimmering Item Tags provider";
  }

  @Override
  protected void registerTags() {
    this.getBuilder(ELIGIBLE_DUSTS).add(Items.REDSTONE, Items.LAPIS_LAZULI, Items.BLAZE_POWDER, Items.GLOWSTONE_DUST, Items.GUNPOWDER, Items.SUGAR);
  }
}
