package noobanidus.mods.glimmering.init;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

import static noobanidus.mods.glimmering.Glimmering.REGISTRY;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
  public static Supplier<Item.Properties> PROPS = () -> new Item.Properties().group(Glimmering.ITEM_GROUP);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRY.registerItem("ritual_knife", REGISTRY.item(Item::new, PROPS));

  public static void load() {
  }
}
