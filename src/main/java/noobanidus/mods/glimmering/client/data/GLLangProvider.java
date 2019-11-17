package noobanidus.mods.glimmering.client.data;

import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import net.minecraft.data.DataGenerator;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;

public class GLLangProvider extends DeferredLanguageProvider {
  public GLLangProvider(DataGenerator gen) {
    super(gen, Glimmering.MODID);
  }

  @Override
  protected void addTranslations() {
    addItemGroup(Glimmering.ITEM_GROUP, "Glimmering");

    addBlock(ModBlocks.ANDESITE_BOWL);
    addBlock(ModBlocks.RUNE);

    addBlock(ModBlocks.BRICK_WALL);
    addBlock(ModBlocks.BRICK_SLAB);
    addBlock(ModBlocks.BRICK_STAIRS);
    addBlock(ModBlocks.BRICKS);

    // TODO: Containers
  }
}
