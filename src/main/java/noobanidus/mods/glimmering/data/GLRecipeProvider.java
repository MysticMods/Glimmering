package noobanidus.mods.glimmering.data;

import epicsquid.mysticallib.data.DeferredRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;

import java.util.function.Consumer;

public class GLRecipeProvider extends DeferredRecipeProvider {
  public GLRecipeProvider(DataGenerator generatorIn) {
    super(generatorIn, Glimmering.MODID);
  }

  @Override
  protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
    twoByTwo(() -> Items.POLISHED_ANDESITE, ModBlocks.BRICKS, "polished_andesite_bricks", consumer);
    slab(ModBlocks.BRICKS, ModBlocks.BRICK_SLAB, "polished_andesite_bricks", true, consumer);
    stairs(ModBlocks.BRICKS, ModBlocks.BRICK_STAIRS, "polished_andesite_bricks", true, consumer);
    wall(ModBlocks.BRICKS, ModBlocks.BRICK_WALL, true, consumer);

    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ANDESITE_BOWL.get(), 1)
        .patternLine("P P")
        .patternLine("SSS")
        .patternLine(" X ")
        .key('X', ModBlocks.BRICK_WALL.get())
        .key('S', ModBlocks.BRICK_SLAB.get())
        .key('P', Blocks.POLISHED_ANDESITE_SLAB)
        .addCriterion("has_bricks", this.hasItem(ModBlocks.BRICKS.get()))
        .build(consumer);

    ShapedRecipeBuilder.shapedRecipe(ModBlocks.RUNE.get(), 1)
        .patternLine("PXP")
        .patternLine("XDX")
        .patternLine("PXP")
        .key('P', Blocks.POLISHED_ANDESITE)
        .key('X', ModBlocks.BRICKS.get())
        .key('D', Ingredient.fromTag(GLTags.Items.ELIGIBLE_DUSTS))
        .addCriterion("has_bricks", this.hasItem(ModBlocks.BRICKS.get()))
        .build(consumer);
  }
}
