package noobanidus.mods.glimmering.items;

import noobanidus.mods.glimmering.entity.GlimmerStarEntity;
import noobanidus.mods.glimmering.init.ModEntities;

public class SpawnStar extends GlimmerSpawnItem<GlimmerStarEntity> {
  public SpawnStar(Properties properties) {
    super(ModEntities.GLIMMER_STAR_TYPE, 0x0, 0x0, properties);
  }
}
