package noobanidus.mods.glimmering.setup;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {
  public ModSetup() {
  }

  public void init(FMLCommonSetupEvent event) {
  }

  public void gatherData(GatherDataEvent event) {
/*    DataGenerator gen = event.getGenerator();
    if (event.includeClient()) {
      gen.addProvider(new GLBlockStateProvider(gen, event.getExistingFileHelper()));
      gen.addProvider(new GLItemModelProvider(gen, event.getExistingFileHelper()));
      gen.addProvider(new GLLangProvider(gen));
    }
    if (event.includeServer()) {
      gen.addProvider(new GLRecipeProvider(gen));
      gen.addProvider(new GLLootTableProvider(gen));
      gen.addProvider(new GLItemTagsProvider(gen));
      gen.addProvider(new GLBlockTagsProvider(gen));
      gen.addProvider(new GLRecipeProvider(gen));
    }*/
  }
}
