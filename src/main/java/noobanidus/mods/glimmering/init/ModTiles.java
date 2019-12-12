package noobanidus.mods.glimmering.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

public class ModTiles {
  public static RegistryObject<TileEntityType<RitualRuneTile>> RUNE = REGISTRATE.tileEntity("ritual_rune", RitualRuneTile::new).validBlock(ModBlocks.RITUAL_RUNE).register();
  public static RegistryObject<TileEntityType<AndesiteBowlTile>> ANDESITE_BOWL = REGISTRATE.tileEntity("andesite_bowl", AndesiteBowlTile::new).validBlock(ModBlocks.ANDESITE_BOWL).register();

  public static void load() {

  }
}
