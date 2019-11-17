package noobanidus.mods.glimmering.data;

import epicsquid.mysticallib.data.DeferredItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;

import static noobanidus.mods.glimmering.GLTags.Items.*;

public class GLItemTagsProvider extends DeferredItemTagsProvider {
  public GLItemTagsProvider(DataGenerator generatorIn) {
    super(generatorIn, "Glimmering Item Tags Provider");
  }

  @Override
  protected void registerTags() {
    addItemsToTag(ELIGIBLE_DUSTS, () -> Items.REDSTONE, () -> Items.LAPIS_LAZULI, () -> Items.BLAZE_POWDER, () -> Items.GLOWSTONE_DUST, () -> Items.PRISMARINE_CRYSTALS, () -> Items.GUNPOWDER, () -> Items.SUGAR);

    copy(Blocks.SLABS, SLABS);
    copy(Blocks.WALLS, WALLS);
    copy(Blocks.STAIRS, STAIRS);
  }
}
