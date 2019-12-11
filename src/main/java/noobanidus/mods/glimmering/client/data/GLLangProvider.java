package noobanidus.mods.glimmering.client.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

public class GLLangProvider extends LanguageProvider {
  public GLLangProvider(DataGenerator gen) {
    super(gen, Glimmering.MODID, "en_us");
  }

  @Override
  protected void addTranslations() {
    add("glimmering.node.type.0", "Glimmer Relay");
    add("glimmering.node.type.1", "Glimmer Transmitter");
    add("glimmering.node.type.2", "Glimmer Receiver");
    add("glimmering.message.type_change", "This Glimmer is now a %s.");
    add("glimmering.tooltip.line0", "A touch of glimmering magic.");
    add("glimmering.tooltip.line1", "Magical energy transfer node. Hit with %s to change mode.");
    add("glimmering.tooltip.line2", "Sneak while hitting with %s to dismantle the Glimmer.");
  }
}
