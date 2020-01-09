package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;
import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.items.GlimmerSpawnItem;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  public static RegistryEntry<EntityType<GlimmerEntity>> GLIMMER_TYPE = REGISTRATE.entity("glimmer", GlimmerEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.4f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_GLIMMER.get()))))).register();

  public static RegistryEntry<EntityType<LargeGlimmerEntity>> LARGE_GLIMMER_TYPE = REGISTRATE.entity("large_glimmer", LargeGlimmerEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.4f, 0.85f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_LARGE_GLIMMER.get()))))).register();

  public static RegistryEntry<EntityType<GlimmerStarEntity>> GLIMMER_STAR_TYPE = REGISTRATE.entity("star_glimmer", GlimmerStarEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.3f, 0.6f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_STAR_GLIMMER.get()))))).register();

  public static RegistryEntry<EntityType<RitualEntity>> RITUAL_TYPE = REGISTRATE.entity("ritual", RitualEntity::new, EntityClassification.CREATURE).properties(o -> o.size(5, 5).setTrackingRange(18).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder())).register();

  public static void load() {
  }
}
