package noobanidus.mods.glimmering.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import noobanidus.mods.glimmering.init.ModTiles;

public class AndesiteBowlTile extends TileEntity {
  private ItemStackHandler inventory = new ItemStackHandler(1);

  public AndesiteBowlTile() {
    super(ModTiles.ANDESITE_BOWL.get());
  }


}
