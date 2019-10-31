package noobanidus.mods.glimmering.init;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

@SuppressWarnings("unused")
public class ModItems {
  public static RegistryObject<Item> GLIMMER = Glimmering.REGISTRY.registerItem("glimmering", Glimmering.REGISTRY.item(Item::new, ModRegistries.SIG));

  public static void load() {
  }
}
