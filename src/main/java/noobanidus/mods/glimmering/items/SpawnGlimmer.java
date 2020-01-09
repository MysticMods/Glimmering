package noobanidus.mods.glimmering.items;

import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.init.ModEntities;

public class SpawnGlimmer extends GlimmerSpawnItem<GlimmerEntity> {
  public SpawnGlimmer(Properties properties) {
    super(ModEntities.GLIMMER_TYPE, 0x0, 0x0, properties);
  }
}
