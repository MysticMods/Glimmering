package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;

import java.util.function.UnaryOperator;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModItems {
  public static UnaryOperator<Item.Properties> PROPS = (o) -> o.group(Glimmering.ITEM_GROUP);

  public static RegistryObject<Item> RITUAL_KNIFE = REGISTRATE.item("ritual_knife", Item::new).properties(PROPS).register();

  static {
    REGISTRATE.item("lang_keys", Item::new).addData(ProviderType.LANG, ctx -> {
      ctx.getProvider().add("glimmering.node.type.0", "Glimmer Relay");
      ctx.getProvider().add("glimmering.node.type.1", "Glimmer Transmitter");
      ctx.getProvider().add("glimmering.node.type.2", "Glimmer Receiver");
      ctx.getProvider().add("glimmering.message.type_change", "This Glimmer is now a %s.");
      ctx.getProvider().add("glimmering.tooltip.line0", "A touch of glimmering magic.");
      ctx.getProvider().add("glimmering.tooltip.line1", "Magical energy transfer node. Hit with %s to change mode.");
      ctx.getProvider().add("glimmering.tooltip.line2", "Sneak while hitting with %s to dismantle the Glimmer.");
    });
  }

  public static void load() {
  }
}
