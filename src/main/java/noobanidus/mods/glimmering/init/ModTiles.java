package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.entry.TileEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

public class ModTiles {
  public static TileEntityEntry<RitualRuneTile> RUNE = REGISTRATE.tileEntity("ritual_rune", RitualRuneTile::new).validBlock(NonNullSupplier.of(ModBlocks.RITUAL_RUNE)).register();
  public static TileEntityEntry<AndesiteBowlTile> ANDESITE_BOWL = REGISTRATE.tileEntity("andesite_bowl", AndesiteBowlTile::new).validBlock(NonNullSupplier.of(ModBlocks.ANDESITE_BOWL)).register();

  public static void load() {

  }
}
