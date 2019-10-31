package noobanidus.mods.glimmering.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

import java.util.function.Supplier;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  private static final String GLIMMER_ID = "glimmer";

  private static EntityType<GlimmerEntity> GLIMMER_TYPE = (EntityType<GlimmerEntity>) EntityType.Builder.create(GlimmerEntity::new, EntityClassification.CREATURE).size(0.75f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3).build(GLIMMER_ID).setRegistryName(Glimmering.MODID, GLIMMER_ID);

  public static Supplier<EntityType<GlimmerEntity>> GLIMMER = () -> GLIMMER_TYPE;

  public static RegistryObject<SpawnEggItem> SPAWN_GLIMMER = Glimmering.REGISTRY.registerItem(GLIMMER_ID + "_spawn_egg", () -> new SpawnEggItem(GLIMMER_TYPE, 0x418594, 0x211D15, ModRegistries.SIG.get()));

  public static void load() {
  }

  public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
    IForgeRegistry<EntityType<?>> registry = event.getRegistry();

    registry.registerAll(GLIMMER_TYPE);
  }
}
