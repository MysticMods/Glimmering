package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.item.Item;
import noobanidus.mods.glimmering.Glimmering;

import java.util.function.UnaryOperator;

public class ModRegistries {
  public static final NonNullUnaryOperator<Item.Properties> NOP = (o) -> o;
}
