package noobanidus.mods.glimmering;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class GLTags {
  public static class Items extends GLTags {
    public static ITag.INamedTag<Item> ELIGIBLE_DUSTS = modTag("glimmer_dusts");

    public static ITag.INamedTag<Item> MYTHICAL_DUSTS = modTag("mythical_dusts");
    public static ITag.INamedTag<Item> EMPOWERED_DUSTS = modTag("empowered_dusts");
    public static ITag.INamedTag<Item> SUPERIOR_DUSTS = modTag("superior_dusts");
    public static ITag.INamedTag<Item> COMMON_DUSTS = modTag("common_dusts");
    public static ITag.INamedTag<Item> INFERIOR_DUSTS = modTag("inferior_dusts");
    public static ITag.INamedTag<Item> INCONSEQUENTIAL_DUSTS = modTag("inconsequential_dusts");

    static ITag.INamedTag<Item> modTag(String name) {
      return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, new ResourceLocation(Glimmering.MODID, name));
    }

    static ITag.INamedTag<Item> compatTag(String name) {
      return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, new ResourceLocation("forge", name));
    }
  }
}
