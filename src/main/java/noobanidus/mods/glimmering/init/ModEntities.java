package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;
import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;
import noobanidus.mods.glimmering.entity.RitualEntity;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  public static EntityEntry<GlimmerEntity> GLIMMER_TYPE = REGISTRATE.entity("glimmer", GlimmerEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.4f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_GLIMMER.get()))))).register();

  public static EntityEntry<LargeGlimmerEntity> LARGE_GLIMMER_TYPE = REGISTRATE.entity("large_glimmer", LargeGlimmerEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.4f, 0.85f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_LARGE_GLIMMER.get()))))).register();

  public static EntityEntry<GlimmerStarEntity> GLIMMER_STAR_TYPE = REGISTRATE.entity("star_glimmer", GlimmerStarEntity::new, EntityClassification.CREATURE).properties(o -> o.size(0.3f, 0.6f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.SPAWN_STAR_GLIMMER.get()))))).register();

  public static EntityEntry<RitualEntity> RITUAL_TYPE = REGISTRATE.entity("ritual", RitualEntity::new, EntityClassification.CREATURE).properties(o -> o.size(5, 5).setTrackingRange(18).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3)).loot((ctx, p) -> ctx.registerLootTable(p, LootTable.builder())).register();

  public static void load() {
  }
}
