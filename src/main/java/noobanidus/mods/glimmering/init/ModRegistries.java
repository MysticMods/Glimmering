package noobanidus.mods.glimmering.init;

import noobanidus.mods.glimmering.Glimmering;
import net.minecraft.item.*;

import java.util.function.Supplier;

public class ModRegistries {
  public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(Glimmering.ITEM_GROUP);
}
