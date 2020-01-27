package noobanidus.mods.glimmering.init;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

public class ModSerializers {
  public static final DeferredRegister<DataSerializerEntry> serializerRegistry = new DeferredRegister<>(ForgeRegistries.DATA_SERIALIZERS, Glimmering.MODID);

  public static final RegistryObject<DataSerializerEntry> NODE_SERIALIZER = serializerRegistry.register("node_serializer", () -> new DataSerializerEntry(GlimmerEntity.NODE_SERIALIZER));

  public static void load() {
  }
}
