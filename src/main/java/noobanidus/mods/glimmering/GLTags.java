package noobanidus.mods.glimmering;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class GLTags {
  public static class Items extends GLTags {
    public static Tag<Item> ELIGIBLE_DUSTS = modTag("glimmer_dusts");

    public static Tag<Item> MYTHICAL_DUSTS = modTag("mythical_dusts");
    public static Tag<Item> EMPOWERED_DUSTS = modTag("empowered_dusts");
    public static Tag<Item> SUPERIOR_DUSTS = modTag("superior_dusts");
    public static Tag<Item> COMMON_DUSTS = modTag("common_dusts");
    public static Tag<Item> INFERIOR_DUSTS = modTag("inferior_dusts");
    public static Tag<Item> INCONSEQUENTIAL_DUSTS = modTag("inconsequential_dusts");

    static Tag<Item> tag(String modid, String name) {
      return tag(ItemTags.Wrapper::new, modid, name);
    }

    static Tag<Item> modTag(String name) {
      return tag(Glimmering.MODID, name);
    }
  }

  static <T extends Tag<?>> T tag(Function<ResourceLocation, T> creator, String modid, String name) {
    return creator.apply(new ResourceLocation(modid, name));
  }
}
