package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tileentity.TileEntityType;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

public class ModTiles {
  public static RegistryEntry<TileEntityType<RitualRuneTile>> RUNE = REGISTRATE.tileEntity("ritual_rune", RitualRuneTile::new).validBlock(NonNullSupplier.of(ModBlocks.RITUAL_RUNE)).register();
  public static RegistryEntry<TileEntityType<AndesiteBowlTile>> ANDESITE_BOWL = REGISTRATE.tileEntity("andesite_bowl", AndesiteBowlTile::new).validBlock(NonNullSupplier.of(ModBlocks.ANDESITE_BOWL)).register();

  public static void load() {

  }
}
