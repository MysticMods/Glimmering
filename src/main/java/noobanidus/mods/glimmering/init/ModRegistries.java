package noobanidus.mods.glimmering.init;

import noobanidus.mods.glimmering.Glimmering;
import net.minecraft.item.*;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModRegistries {
  public static final UnaryOperator<Item.Properties> SIG = (o) -> o.group(Glimmering.ITEM_GROUP);
}
