package noobanidus.mods.glimmering.init;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.blocks.AndesiteBowl;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRY;

@SuppressWarnings("unused")
public class ModBlocks {
  private static Supplier<Block.Properties> STONE_PROPS = () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(3f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE);

  public static RegistryObject<AndesiteBowl> ANDESITE_BOWL = REGISTRY.registerBlock("andesite_bowl", REGISTRY.block(AndesiteBowl::new, STONE_PROPS), ModItems.PROPS);

  public static RegistryObject<Block> RUNE = REGISTRY.registerBlock("ritual_rune", REGISTRY.block(Block::new, STONE_PROPS), ModItems.PROPS);

  public static RegistryObject<Block> BRICKS = REGISTRY.registerBlock("polished_andesite_bricks", REGISTRY.block(Block::new, STONE_PROPS), ModItems.PROPS);
  public static RegistryObject<StairsBlock> BRICK_STAIRS = REGISTRY.registerBlock("polished_andesite_brick_stairs", REGISTRY.stair(BRICKS), ModItems.PROPS);
  public static RegistryObject<SlabBlock> BRICK_SLAB = REGISTRY.registerBlock("polished_andesite_brick_slab", REGISTRY.slab(BRICKS), ModItems.PROPS);
  public static RegistryObject<WallBlock> BRICK_WALL = REGISTRY.registerBlock("polished_andesite_brick_wall", REGISTRY.wall(BRICKS), ModItems.PROPS);

  public static void load() {
  }
}
