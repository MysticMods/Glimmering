package noobanidus.mods.glimmering.init;

import net.minecraft.item.Item;
import noobanidus.mods.glimmering.Glimmering;

import java.util.function.UnaryOperator;

public class ModRegistries {
  public static final UnaryOperator<Item.Properties> SIG = (o) -> o.group(Glimmering.ITEM_GROUP);
}
