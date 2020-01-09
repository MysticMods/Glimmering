package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.items.GlimmerSpawnItem;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  public static RegistryEntry<EntityType<GlimmerEntity>> GLIMMER_TYPE = REGISTRATE.entity("glimmer", GlimmerEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.75f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).register();

  public static RegistryEntry<EntityType<RitualEntity>> RITUAL_TYPE = REGISTRATE.entity("ritual", RitualEntity::new, EntityClassification.CREATURE).properties(o -> o.size(5, 5).setTrackingRange(18).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).register();

  public static RegistryEntry<GlimmerSpawnItem> SPAWN_GLIMMER = REGISTRATE.item("glimmer", GlimmerSpawnItem::new).model((ctx, p) -> p.withExistingParent("glimmer", new ResourceLocation(Glimmering.MODID, "item/relay"))).register();

  public static void load() {
  }
}
