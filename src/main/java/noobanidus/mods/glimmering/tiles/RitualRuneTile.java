package noobanidus.mods.glimmering.tiles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import noobanidus.mods.glimmering.init.ModTiles;

public class RitualRuneTile extends TileEntity implements ITickableTileEntity {
  public RitualRuneTile() {
    super(ModTiles.RUNE.get());
  }

  @Override
  public void tick() {

  }

  public void activate (PlayerEntity player) {

  }
}
