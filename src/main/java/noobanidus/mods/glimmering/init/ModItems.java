package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModItems {
  public static NonNullUnaryOperator<Item.Properties> PROPS = (o) -> o.group(Glimmering.ITEM_GROUP);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRATE.item("ritual_knife", Item::new).properties(PROPS).recipe(ctx -> {
    ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
        .addIngredient(Tags.Items.NUGGETS_GOLD)
        .addIngredient(Tags.Items.RODS_WOODEN)
        .addCriterion("gold_nugget", ctx.getProvider().hasItem(Tags.Items.NUGGETS_GOLD))
        .build(ctx.getProvider());
  }).model(ctx -> ctx.getProvider().handheld(ctx::getEntry)).register();

  public static void load() {
  }
}
