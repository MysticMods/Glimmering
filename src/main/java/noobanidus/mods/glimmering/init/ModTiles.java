package noobanidus.mods.glimmering.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import static noobanidus.mods.glimmering.Glimmering.REGISTRY;

public class ModTiles {
  public static RegistryObject<TileEntityType<RitualRuneTile>> RUNE = REGISTRY.registerTileEntity("ritual_rune", REGISTRY.tile(RitualRuneTile::new, ModBlocks.RITUAL_RUNE));
  public static RegistryObject<TileEntityType<AndesiteBowlTile>> ANDESITE_BOWL = REGISTRY.registerTileEntity("andesite_bowl", REGISTRY.tile(AndesiteBowlTile::new, ModBlocks.ANDESITE_BOWL));

  public static void load () {

  }
}
