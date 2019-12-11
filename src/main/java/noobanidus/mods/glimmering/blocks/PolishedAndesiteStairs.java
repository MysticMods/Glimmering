package noobanidus.mods.glimmering.blocks;

import net.minecraft.block.StairsBlock;
import noobanidus.mods.glimmering.init.ModBlocks;

public class PolishedAndesiteStairs extends StairsBlock {
  public PolishedAndesiteStairs(Properties properties) {
    super(() -> ModBlocks.BRICKS.get().getDefaultState(), properties);
  }
}
