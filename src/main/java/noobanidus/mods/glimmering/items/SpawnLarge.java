package noobanidus.mods.glimmering.items;

import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;
import noobanidus.mods.glimmering.init.ModEntities;

public class SpawnLarge extends GlimmerSpawnItem<LargeGlimmerEntity> {
  public SpawnLarge(Properties properties) {
    super(ModEntities.LARGE_GLIMMER_TYPE, 0x0, 0x0, properties);
  }
}
