package noobanidus.mods.glimmering;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Function;

public class GLTags {
  public static class Blocks extends GLTags {
    public static Tag<Block> SLABS = modTag("slabs");
    public static Tag<Block> STAIRS = modTag("stairs");
    public static Tag<Block> WALLS = modTag("walls");

    static Tag<Block> tag(String modid, String name) {
      return tag(BlockTags.Wrapper::new, modid, name);
    }

    static Tag<Block> modTag(String name) {
      return tag(Glimmering.MODID, name);
    }

    static Tag<Block> compatTag(String name) {
      return tag("forge", name);
    }
  }

  public static class Items extends GLTags {
    public static Tag<Item> ELIGIBLE_DUSTS = modTag("glimmer_dusts");

    public static Tag<Item> SLABS = modTag("slabs");
    public static Tag<Item> STAIRS = modTag("stairs");
    public static Tag<Item> FENCES = modTag("fences");
    public static Tag<Item> WALLS = modTag("walls");

    static Tag<Item> tag(String modid, String name) {
      return tag(ItemTags.Wrapper::new, modid, name);
    }

    static Tag<Item> modTag(String name) {
      return tag(Glimmering.MODID, name);
    }

    static Tag<Item> compatTag(String name) {
      return tag("forge", name);
    }
  }

  static <T extends Tag<?>> T tag(Function<ResourceLocation, T> creator, String modid, String name) {
    return creator.apply(new ResourceLocation(modid, name));
  }

  static <T extends Tag<?>> T modTag(Function<ResourceLocation, T> creator, String name) {
    return tag(creator, Glimmering.MODID, name);
  }

  static <T extends Tag<?>> T compatTag(Function<ResourceLocation, T> creator, String name) {
    return tag(creator, "forge", name);
  }
}
