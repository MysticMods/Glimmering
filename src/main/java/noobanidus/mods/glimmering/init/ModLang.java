package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;

import java.util.function.Consumer;

public class ModLang implements Consumer<RegistrateLangProvider> {
  public static ModLang instance = new ModLang();

  @Override
  public void accept(RegistrateLangProvider ctx) {
    ctx.add("glimmering.node.type.0", "Glimmer Relay");
    ctx.add("glimmering.node.type.1", "Glimmer Transmitter");
    ctx.add("glimmering.node.type.2", "Glimmer Receiver");
    ctx.add("glimmering.message.type_change", "This Glimmer is now a %s.");
    ctx.add("glimmering.tooltip.line0", "A touch of glimmering magic.");
    ctx.add("glimmering.tooltip.line1", "Magical energy transfer node. Hit with %s to change mode.");
    ctx.add("glimmering.tooltip.line2", "Sneak while hitting with %s to dismantle the Glimmer.");
  }

  public static void load () {
  }
}
