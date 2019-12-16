package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModItems {
  public static NonNullUnaryOperator<Item.Properties> PROPS = (o) -> o.group(Glimmering.ITEM_GROUP);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRATE.item("ritual_knife", Item::new).properties(PROPS).register();

  public static void load() {
  }
}
