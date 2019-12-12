package noobanidus.mods.glimmering.setup;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import noobanidus.mods.glimmering.client.data.GLLangProvider;
import noobanidus.mods.glimmering.data.GLItemTagsProvider;

public class ModSetup {
  public ModSetup() {
  }

  public void init(FMLCommonSetupEvent event) {
  }

  public void gatherData(GatherDataEvent event) {
    DataGenerator gen = event.getGenerator();
    if (event.includeClient()) {
      gen.addProvider(new GLLangProvider(gen));
    }
    if (event.includeServer()) {
      gen.addProvider(new GLItemTagsProvider(gen));
    }
  }
}
