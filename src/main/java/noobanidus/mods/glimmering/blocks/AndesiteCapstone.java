package noobanidus.mods.glimmering.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

@SuppressWarnings("deprecation")
public class AndesiteCapstone extends Block {
  public AndesiteCapstone(Properties properties) {
    super(properties);
    this.setDefaultState(getDefaultState().with(RitualRuneBlock.ACTIVE, false));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(RitualRuneBlock.ACTIVE);
  }

  @Override
  public int getLightValue(BlockState state) {
    if (state.getBlock() == this && state.get(RitualRuneBlock.ACTIVE)) {
      return 15;
    }

    return 0;
  }

}
