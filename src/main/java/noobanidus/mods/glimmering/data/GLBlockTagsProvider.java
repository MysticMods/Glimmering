package noobanidus.mods.glimmering.data;

import epicsquid.mysticallib.data.DeferredBlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.init.ModBlocks;

public class GLBlockTagsProvider extends DeferredBlockTagsProvider {
  public GLBlockTagsProvider(DataGenerator generatorIn) {
    super(generatorIn, "Glimmering Block Tags Provider");
  }

  @Override
  protected void registerTags() {
    createAndAppend(GLTags.Blocks.SLABS, BlockTags.SLABS, ModBlocks.BRICK_SLAB);
    createAndAppend(GLTags.Blocks.STAIRS, BlockTags.STAIRS, ModBlocks.BRICK_STAIRS);
    createAndAppend(GLTags.Blocks.WALLS, BlockTags.WALLS, ModBlocks.BRICK_WALL);
  }
}
