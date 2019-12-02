package noobanidus.mods.glimmering.client.data;

import epicsquid.mysticallib.client.data.DeferredBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;

public class GLBlockStateProvider extends DeferredBlockStateProvider {
  public GLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
    super("Glimmering Block State & Model Generator", gen, Glimmering.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    getVariantBuilder(ModBlocks.ANDESITE_BOWL.get()).partialState().setModels(new ConfiguredModel(getExistingFile(new ResourceLocation(Glimmering.MODID, "block/andesite_bowl"))));

    getVariantBuilder(ModBlocks.RITUAL_RUNE.get()).partialState().addModels(new ConfiguredModel(cubeTop(name(ModBlocks.RITUAL_RUNE), new ResourceLocation("minecraft", "block/polished_andesite"), new ResourceLocation(Glimmering.MODID, "block/andesite_rune"))));

    simpleBlock(ModBlocks.BRICKS);
    slabBlock(ModBlocks.BRICK_SLAB, ModBlocks.BRICKS);
    stairsBlock(ModBlocks.BRICK_STAIRS, "polished_andesite_bricks");
    wallBlock(ModBlocks.BRICK_WALL, "polished_andesite_bricks");

  }
}
