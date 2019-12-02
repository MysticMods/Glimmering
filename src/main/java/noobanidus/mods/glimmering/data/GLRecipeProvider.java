package noobanidus.mods.glimmering.data;

import epicsquid.mysticallib.data.DeferredRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

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
        .key('X', ModBlocks.BRICKS.get())
        .key('S', ModBlocks.BRICK_SLAB.get())
        .key('P', Blocks.POLISHED_ANDESITE_SLAB)
        .addCriterion("has_bricks", this.hasItem(ModBlocks.BRICKS.get()))
        .build(consumer);

    ShapedRecipeBuilder.shapedRecipe(ModBlocks.RITUAL_RUNE.get(), 1)
        .patternLine("PXP")
        .patternLine("XDX")
        .patternLine("PXP")
        .key('P', Blocks.POLISHED_ANDESITE)
        .key('X', ModBlocks.BRICKS.get())
        .key('D', Ingredient.fromTag(GLTags.Items.ELIGIBLE_DUSTS))
        .addCriterion("has_bricks", this.hasItem(ModBlocks.BRICKS.get()))
        .build(consumer);

    ShapelessRecipeBuilder.shapelessRecipe(ModItems.RITUAL_KNIFE.get(), 1)
        .addIngredient(Tags.Items.RODS_WOODEN)
        .addIngredient(Tags.Items.NUGGETS_GOLD)
        .addCriterion("has_gold", this.hasItem(Tags.Items.INGOTS_GOLD))
        .build(consumer);

    ShapedRecipeBuilder.shapedRecipe(ModEntities.SPAWN_GLIMMER.get(), 1)
        .patternLine(" R ")
        .patternLine("RRR")
        .patternLine(" R ")
        .key('R', Tags.Items.DUSTS_REDSTONE)
        .addCriterion("has_redstone", this.hasItem(Tags.Items.DUSTS_REDSTONE))
        .build(consumer);
  }
}
