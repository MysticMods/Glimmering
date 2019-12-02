package noobanidus.mods.glimmering.client.data;

import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import net.minecraft.data.DataGenerator;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

public class GLLangProvider extends DeferredLanguageProvider {
  public GLLangProvider(DataGenerator gen) {
    super(gen, Glimmering.MODID);
  }

  @Override
  protected void addTranslations() {
    addItemGroup(Glimmering.ITEM_GROUP, "Glimmering");

    addBlock(ModBlocks.ANDESITE_BOWL);
    addBlock(ModBlocks.RITUAL_RUNE);

    addBlock(ModBlocks.BRICK_WALL);
    addBlock(ModBlocks.BRICK_SLAB);
    addBlock(ModBlocks.BRICK_STAIRS);
    addBlock(ModBlocks.BRICKS);

    addItem(ModItems.RITUAL_KNIFE);
    addItem(ModEntities.SPAWN_GLIMMER);

    addEntityType(ModEntities.GLIMMER);

    add("glimmering.node.type.0", "Glimmer Relay");
    add("glimmering.node.type.1", "Glimmer Transmitter");
    add("glimmering.node.type.2", "Glimmer Receiver");
    add("glimmering.message.type_change", "This Glimmer is now a %s");
    add("glimmering.tooltip.line0", "Magical energy transfer node. Hit with %s to change mode.");
    add("glimmering.tooltip.line1", "Sneak while attacking to dismantle node.");

    // TODO: Containers
  }
}
