package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;
import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;
import noobanidus.mods.glimmering.items.*;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModItems {
  private static NonNullUnaryOperator<Item.Properties> PROPS = (o) -> o.group(Glimmering.ITEM_GROUP);
  private static NonNullUnaryOperator<Item.Properties> NULL_PROPS = (o) -> o.group(null);

  public static RegistryEntry<Item> RITUAL_KNIFE = REGISTRATE.item("ritual_knife", Item::new).properties(PROPS).recipe((ctx, p) -> ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
      .addIngredient(Tags.Items.NUGGETS_GOLD)
      .addIngredient(Tags.Items.RODS_WOODEN)
      .addCriterion("gold_nugget", p.hasItem(Tags.Items.NUGGETS_GOLD))
      .build(p)).model((ctx, p) -> p.handheld(ctx::getEntry)).register();

  public static RegistryEntry<TabletItem> GLIMMERING_TABLET = REGISTRATE.item("glimmering_tablet", TabletItem::new).properties(PROPS).recipe((ctx, p) -> ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
      .addIngredient(Items.POLISHED_ANDESITE)
      .addIngredient(Items.POLISHED_ANDESITE)
      .addIngredient(Items.POLISHED_ANDESITE)
      .addIngredient(GLTags.Items.ELIGIBLE_DUSTS)
      .addCriterion("has_dusts", p.hasItem(GLTags.Items.ELIGIBLE_DUSTS))
      .build(p)).model((ctx, p) -> p.handheld(ctx::getEntry)).register();

 public static RegistryEntry<SpawnGlimmer> SPAWN_GLIMMER = REGISTRATE.item("glimmer", SpawnGlimmer::new).model((ctx, p) -> p.withExistingParent("glimmer", new ResourceLocation(Glimmering.MODID, "item/relay"))).recipe((ctx, p) -> {
   ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
       .patternLine("XX")
       .patternLine("XX")
       .key('X', ModItems.SPAWN_STAR_GLIMMER.get())
       .addCriterion("has_stars", p.hasItem(ModItems.SPAWN_STAR_GLIMMER.get()))
       .build(p, new ResourceLocation(Glimmering.MODID, "glimmer_from_stars"));
   ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 4)
       .addIngredient(ModItems.SPAWN_LARGE_GLIMMER.get())
       .addCriterion("has_large", p.hasItem(ModItems.LARGE_TRANSMITTER.get()))
       .build(p, new ResourceLocation(Glimmering.MODID, "glimmer_from_large"));
 }).register();

  public static RegistryEntry<Item> TRANSMITTER = REGISTRATE.item("transmitter", Item::new).properties(NULL_PROPS).model((ctx, p) -> {
  }).register();

  public static RegistryEntry<Item> RECEIVER = REGISTRATE.item("receiver", Item::new).properties(NULL_PROPS).model((ctx, p) -> {
  }).register();

  public static RegistryEntry<SpawnLarge> SPAWN_LARGE_GLIMMER = REGISTRATE.item("large_glimmer", SpawnLarge::new).model((ctx, p) -> p.withExistingParent("large_glimmer", new ResourceLocation(Glimmering.MODID, "item/large_receiver"))).recipe((ctx, p) -> ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
      .patternLine("XX")
      .patternLine("XX")
      .key('X', ModItems.SPAWN_GLIMMER.get())
      .addCriterion("has_glimmer", p.hasItem(ModItems.SPAWN_GLIMMER.get()))
      .build(p, new ResourceLocation(Glimmering.MODID, "large_from_glimmers"))).register();

  public static RegistryEntry<Item> LARGE_TRANSMITTER = REGISTRATE.item("large_transmitter", Item::new).properties(NULL_PROPS).model((ctx, p) -> {}).register();

  public static RegistryEntry<SpawnStar> SPAWN_STAR_GLIMMER = REGISTRATE.item("star_glimmer", SpawnStar::new).model((ctx, p) -> p.withExistingParent("star_glimmer", new ResourceLocation(Glimmering.MODID, "item/star_receiver"))).recipe((ctx, p) -> ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 4)
      .addIngredient(ModItems.SPAWN_GLIMMER.get())
      .addCriterion("has_glimmer", p.hasItem(ModItems.SPAWN_GLIMMER.get()))
      .build(p, new ResourceLocation(Glimmering.MODID, "star_from_glimmer"))).register();

  public static RegistryEntry<Item> STAR_TRANSMITTER = REGISTRATE.item("star_transmitter", Item::new).properties(NULL_PROPS).model((ctx, p) -> {}).register();

  public static void load() {
  }
}
