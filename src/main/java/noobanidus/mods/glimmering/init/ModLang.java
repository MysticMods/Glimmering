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
    ctx.add("glimmering.message.invalid_structure", "There is something lacking about your ritual site.");
    ctx.add("glimmering.message.invalid_ingredients", "You lack the proper reagents to perform this ritual.");
    ctx.add("glimmering.node.type.receive", "receiver");
    ctx.add("glimmering.node.type.transmit", "transmitter");
    ctx.add("glimmering.node.type.relay", "relay");
  }
}
