package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.block.*;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.blocks.AndesiteBowlBlock;
import noobanidus.mods.glimmering.blocks.AndesiteCapstone;
import noobanidus.mods.glimmering.blocks.PolishedAndesiteStairs;
import noobanidus.mods.glimmering.blocks.RitualRuneBlock;

import java.util.function.UnaryOperator;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModBlocks {
  private static NonNullUnaryOperator<Block.Properties> STONE_PROPS = (o) -> o.hardnessAndResistance(3f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE);

  public static RegistryObject<Block> BRICKS = REGISTRATE.object("polished_andesite_bricks")
      .block(Block::new)
      .properties(STONE_PROPS)
      .item()
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 4)
            .patternLine("XX")
            .patternLine("XX")
            .key('X', Items.POLISHED_ANDESITE)
            .addCriterion("has_andesite", ctx.getProvider().hasItem(Items.POLISHED_ANDESITE))
            .build(ctx.getProvider());
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Items.POLISHED_ANDESITE), ctx.getEntry())
            .addCriterion("has_andesite", ctx.getProvider().hasItem(Items.POLISHED_ANDESITE))
            .build(ctx.getProvider(), new ResourceLocation(Glimmering.MODID, "polished_andesite_bricks_from_stonecutting"));
      })
      .register();

  public static RegistryObject<PolishedAndesiteStairs> BRICK_STAIRS = REGISTRATE.object("polished_andesite_brick_stairs")
      .block(PolishedAndesiteStairs::new)
      .properties(STONE_PROPS)
      .blockstate(ctx -> ctx.getProvider().stairsBlock(ctx.getEntry(), ctx.getProvider().modLoc("block/polished_andesite_bricks")))
      .item()
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 4)
            .patternLine("X  ")
            .patternLine("XX ")
            .patternLine("XXX")
            .key('X', ModBlocks.BRICKS.get())
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider());
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ModBlocks.BRICKS.get()), ctx.getEntry())
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider(), new ResourceLocation(Glimmering.MODID, "polished_andesite_brick_stairs_from_stonecutting"));
      })
      .register();

  public static RegistryObject<SlabBlock> BRICK_SLABS = REGISTRATE.object("polished_andesite_brick_slab")
      .block(SlabBlock::new)
      .properties(STONE_PROPS)
      .blockstate(ctx -> ctx.getProvider().slabBlock(ctx.getEntry(), BRICKS.get().getRegistryName(), ctx.getProvider().modLoc("block/polished_andesite_bricks")))
      .item()
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 6)
            .patternLine("XXX")
            .key('X', ModBlocks.BRICKS.get())
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider());
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ModBlocks.BRICKS.get()), ctx.getEntry(), 2)
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider(), new ResourceLocation(Glimmering.MODID, "polished_andesite_brick_slabs_from_stonecutting"));
      })
      .register();

  public static RegistryObject<WallBlock> BRICK_WALL = REGISTRATE.object("polished_andesite_brick_wall")
      .block(WallBlock::new)
      .properties(STONE_PROPS)
      .blockstate(ctx -> {
        ctx.getProvider().wallBlock(ctx.getEntry(), ctx.getProvider().modLoc("block/polished_andesite_bricks"));
        ctx.getProvider().wallInventory(ctx.getName() + "_inventory", ctx.getProvider().modLoc("block/polished_andesite_bricks"));
      })
      .item()
        .model(ctx -> ctx.getProvider().blockWithInventoryModel(NonNullSupplier.of(ModBlocks.BRICK_WALL)))
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 6)
            .patternLine("XXX")
            .patternLine("XXX")
            .key('X', ModBlocks.BRICKS.get())
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider());
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ModBlocks.BRICKS.get()), ctx.getEntry(), 2)
            .addCriterion("has_polished_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
            .build(ctx.getProvider(), new ResourceLocation(Glimmering.MODID, "polished_andesite_brick_wall_from_stonecutting"));
      })
      .register();

  public static RegistryObject<AndesiteBowlBlock> ANDESITE_BOWL = REGISTRATE.object("andesite_bowl")
      .block(AndesiteBowlBlock::new)
      .blockstate(ctx -> ctx.getProvider().simpleBlock(ctx.getEntry(), ctx.getProvider().getExistingFile(ctx.getProvider().modLoc(ctx.getName()))))
      .properties(STONE_PROPS)
      .item()
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx ->
          ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
              .patternLine("X X")
              .patternLine("SSS")
              .patternLine(" X ")
              .key('X', ModBlocks.BRICKS.get())
              .key('S', ModBlocks.BRICK_SLABS.get())
              .addCriterion("has_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
              .build(ctx.getProvider()))
      .register();

  public static RegistryObject<RitualRuneBlock> RITUAL_RUNE = REGISTRATE.object("ritual_rune")
      .block(RitualRuneBlock::new)
      .blockstate(ctx -> ctx.getProvider().getVariantBuilder(ctx.getEntry()).forAllStates(state -> ConfiguredModel.builder().modelFile(ctx.getProvider().cubeTop(ctx.getName() + (state.get(RitualRuneBlock.ACTIVE) ? "_active" : ""), new ResourceLocation("block/polished_andesite"), state.get(RitualRuneBlock.ACTIVE) ? ctx.getProvider().modLoc("block/andesite_rune_active") : ctx.getProvider().modLoc("block/andesite_rune"))).build()))
      .properties(STONE_PROPS)
      .item()
        .properties((o) -> o.group(Glimmering.ITEM_GROUP))
        .build()
      .recipe(ctx ->
          ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
              .patternLine("PXP")
              .patternLine("XDX")
              .patternLine("PXP")
              .key('P', Blocks.POLISHED_ANDESITE)
              .key('X', ModBlocks.BRICKS.get())
              .key('D', GLTags.Items.ELIGIBLE_DUSTS)
              .addCriterion("has_bricks", ctx.getProvider().hasItem(ModBlocks.BRICKS.get()))
              .build(ctx.getProvider())
      )
      .register();

  public static RegistryObject<AndesiteCapstone> ANDESITE_CAPSTONE = REGISTRATE.object("andesite_capstone")
      .block(AndesiteCapstone::new)
      .blockstate(ctx -> ctx.getProvider().getVariantBuilder(ctx.getEntry()).forAllStates(state -> ConfiguredModel.builder().modelFile(ctx.getProvider().cubeAll(ctx.getName() + (state.get(RitualRuneBlock.ACTIVE) ? "_active" : ""), state.get(RitualRuneBlock.ACTIVE) ? ctx.getProvider().modLoc("block/andesite_capstone_active") : ctx.getProvider().modLoc("block/andesite_capstone"))).build()))
      .item().properties(o -> o.group(Glimmering.ITEM_GROUP)).build()
      .recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 4)
            .patternLine("PDP")
            .patternLine("D D")
            .patternLine("PDP")
            .key('P', Blocks.POLISHED_ANDESITE)
            .key('D', GLTags.Items.ELIGIBLE_DUSTS)
            .addCriterion("has_andesite", ctx.getProvider().hasItem(Blocks.POLISHED_ANDESITE))
            .addCriterion("has_glimmer_dust", ctx.getProvider().hasItem(GLTags.Items.ELIGIBLE_DUSTS))
            .build(ctx.getProvider());
      })
      .register();

  public static void load() {
  }
}
