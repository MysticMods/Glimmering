package noobanidus.mods.glimmering.init;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import noobanidus.mods.glimmering.blocks.AndesiteBowlBlock;
import noobanidus.mods.glimmering.blocks.PolishedAndesiteStairs;
import noobanidus.mods.glimmering.blocks.RitualRuneBlock;

import java.util.function.UnaryOperator;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings("unused")
public class ModBlocks {
  private static UnaryOperator<Block.Properties> STONE_PROPS = (o) -> o.hardnessAndResistance(3f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE);

  public static RegistryObject<AndesiteBowlBlock> ANDESITE_BOWL = REGISTRATE.object("andesite_bowl")
      .block(AndesiteBowlBlock::new)
      .blockstate(ctx -> ctx.getProvider().simpleBlock(ctx.getEntry(), ctx.getProvider().getExistingFile(ctx.getProvider().modLoc(ctx.getName()))))
      .properties(STONE_PROPS)
      .simpleItem()
      .register();

  public static RegistryObject<RitualRuneBlock> RITUAL_RUNE = REGISTRATE.object("ritual_rune")
      .block(RitualRuneBlock::new)
      .blockstate(ctx -> ctx.getProvider().simpleBlock(ctx.getEntry(), ctx.getProvider().cubeTop(ctx.getName(), new ResourceLocation("block/polished_andesite"), ctx.getProvider().modLoc("block/andesite_rune"))))
      .properties(STONE_PROPS)
      .simpleItem()
      .register();

  public static RegistryObject<Block> BRICKS = REGISTRATE.object("polished_andesite_bricks")
      .block(Block::new)
      .properties(STONE_PROPS)
      .simpleItem()
      .register();

  public static RegistryObject<PolishedAndesiteStairs> BRICK_STAIRS = REGISTRATE.object("polished_andesite_brick_stairs")
      .block(PolishedAndesiteStairs::new)
      .tag(BlockTags.STAIRS)
      .properties(STONE_PROPS)
      .blockstate(ctx -> ctx.getProvider().stairsBlock(ctx.getEntry(), ctx.getProvider().modLoc("block/polished_andesite_bricks")))
      .simpleItem()
      .register();

  public static RegistryObject<SlabBlock> BRICK_SLABS = REGISTRATE.object("polished_andesite_brick_slab")
      .block(SlabBlock::new)
      .tag(BlockTags.SLABS)
      .properties(STONE_PROPS)
      .blockstate(ctx -> ctx.getProvider().slabBlock(ctx.getEntry(), BRICKS.get().getRegistryName(), ctx.getProvider().modLoc("block/polished_andesite_bricks")))
      .simpleItem()
      .register();

  public static RegistryObject<WallBlock> BRICK_WALL = REGISTRATE.object("polished_andesite_brick_wall")
      .block(WallBlock::new)
      .tag(BlockTags.WALLS)
      .properties(STONE_PROPS)
      .blockstate(ctx -> {
        ctx.getProvider().wallBlock(ctx.getEntry(), ctx.getProvider().modLoc("block/polished_andesite_bricks"));
        ctx.getProvider().wallInventory(ctx.getName() + "_inventory", ctx.getProvider().modLoc("block/polished_andesite_bricks"));
      })
      .item()
      .model(ctx -> ctx.getProvider().blockWithInventoryModel(ModBlocks.BRICK_WALL))
      .build()
      .register();

  public static void load() {
  }
}
