package noobanidus.mods.glimmering.setup;

import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.network.Networking;

public class ModSetup {
  public ModSetup() {
  }

  public void init(FMLCommonSetupEvent event) {
    Networking.registerMessages();

    DataSerializers.registerSerializer(GlimmerEntity.NODE_SERIALIZER);
  }
}
