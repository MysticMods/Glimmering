package noobanidus.mods.glimmering.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import epicsquid.mysticallib.data.DeferredBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.ValidationResults;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModBlocks;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class GLLootTableProvider extends LootTableProvider {
  public GLLootTableProvider(DataGenerator dataGeneratorIn) {
    super(dataGeneratorIn);
  }

  @Override
  public String getName() {
    return "Glimmering Loot Table Provider";
  }

  @Override
  protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
    return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
  }

  @Override
  protected void validate(Map<ResourceLocation, LootTable> map, ValidationResults validationresults) {
  }

  public static class Blocks extends DeferredBlockLootTableProvider {
    @Override
    protected void addTables() {
      self(ModBlocks.RUNE);
      self(ModBlocks.ANDESITE_BOWL);
      self(ModBlocks.BRICK_STAIRS);
      self(ModBlocks.BRICK_SLAB);
      self(ModBlocks.BRICK_WALL);
      self(ModBlocks.BRICKS);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
      return Glimmering.REGISTRY.getBlocks().stream().map(Supplier::get).collect(Collectors.toList());
    }
  }
}
