package noobanidus.mods.glimmering.client.data;

import epicsquid.mysticallib.client.data.DeferredItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModItems;

public class GLItemModelProvider extends DeferredItemModelProvider {
  public GLItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
    super("Glimmering Item Model Generator", generator, Glimmering.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    blockItem(ModBlocks.ANDESITE_BOWL);
    blockItem(ModBlocks.RITUAL_RUNE);
    blockWithInventoryModel(ModBlocks.BRICK_WALL);
    blockItem(ModBlocks.BRICK_SLAB);
    blockItem(ModBlocks.BRICK_STAIRS);
    blockItem(ModBlocks.BRICKS);
    handheld(ModItems.RITUAL_KNIFE);
  }
}
