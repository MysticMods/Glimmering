package noobanidus.mods.glimmering.setup;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import noobanidus.mods.glimmering.network.Networking;

public class ModSetup {
  public ModSetup() {
  }

  public void init(FMLCommonSetupEvent event) {
    Networking.registerMessages();
  }
}
