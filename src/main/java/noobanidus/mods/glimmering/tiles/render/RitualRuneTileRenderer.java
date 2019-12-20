package noobanidus.mods.glimmering.tiles.render;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

public class RitualRuneTileRenderer extends TileEntityRenderer<RitualRuneTile> {
  @Override
  public void render(RitualRuneTile tei, double x, double y, double z, float partialTicks, int destroyStage) {
    if (!tei.isActive()) {
      return;
    }

    int ticksActive = tei.getRitualTicks();

  }
}
