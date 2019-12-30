package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.items.TabletItem;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModItems {
  public static NonNullUnaryOperator<Item.Properties> PROPS = (o) -> o.group(Glimmering.ITEM_GROUP);
  public static NonNullUnaryOperator<Item.Properties> NULL_PROPS = (o) -> o.group(null);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRATE.item("ritual_knife", Item::new).properties(PROPS).recipe(ctx -> {
    ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
        .addIngredient(Tags.Items.NUGGETS_GOLD)
        .addIngredient(Tags.Items.RODS_WOODEN)
        .addCriterion("gold_nugget", ctx.getProvider().hasItem(Tags.Items.NUGGETS_GOLD))
        .build(ctx.getProvider());
  }).model(ctx -> ctx.getProvider().handheld(ctx::getEntry)).register();

  public static RegistryObject<TabletItem> GLIMMERING_TABLET = REGISTRATE.item("glimmering_tablet", TabletItem::new).properties(PROPS).recipe(ctx -> {
    ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
        .addIngredient(Items.POLISHED_ANDESITE)
        .addIngredient(Items.POLISHED_ANDESITE)
        .addIngredient(Items.POLISHED_ANDESITE)
        .addIngredient(GLTags.Items.ELIGIBLE_DUSTS)
        .addCriterion("has_dusts", ctx.getProvider().hasItem(GLTags.Items.ELIGIBLE_DUSTS))
        .build(ctx.getProvider());
  }).model(ctx -> ctx.getProvider().handheld(ctx::getEntry)).register();

  public static RegistryObject<Item> RECEIVER = REGISTRATE.item("receiver", Item::new).properties(NULL_PROPS).model(ctx -> {
  }).register();

  public static RegistryObject<Item> TRANSMITTER = REGISTRATE.item("transmitter", Item::new).properties(NULL_PROPS).model(ctx -> {
  }).register();

  public static RegistryObject<Item> RELAY = REGISTRATE.item("relay", Item::new).properties(NULL_PROPS).model(ctx -> {
  }).register();

  public static void load() {
  }
}
