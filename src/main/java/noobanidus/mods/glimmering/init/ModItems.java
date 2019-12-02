package noobanidus.mods.glimmering.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRY;

@SuppressWarnings("unused")
public class ModItems {
  public static Supplier<Item.Properties> PROPS = () -> new Item.Properties().group(Glimmering.ITEM_GROUP);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRY.registerItem("ritual_knife", REGISTRY.item(Item::new, PROPS));

  public static void load() {
  }
}
