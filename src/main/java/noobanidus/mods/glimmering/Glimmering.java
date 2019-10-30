package noobanidus.mods.glimmering;

import epicsquid.mysticallib.registry.ModRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import noobanidus.mods.glimmering.config.ConfigManager;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;
import noobanidus.mods.glimmering.setup.ModSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("glimmering")
public class Glimmering {
  public static final Logger LOG = LogManager.getLogger();
  public static final String MODID = "glimmering";

  public static final ItemGroup ITEM_GROUP = new ItemGroup("glimmering") {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModItems.GLIMMER.get());
    }
  };

  public static final ModRegistry REGISTRY = new ModRegistry(MODID);

  public static ModSetup setup = new ModSetup();

  public Glimmering() {
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    modBus.addListener(setup::init);
    ModItems.load();
    ModBlocks.load();
    ModEntities.load();

    modBus.addGenericListener(EntityType.class, EventPriority.LOWEST, ModEntities::registerEntities);
    modBus.addGenericListener(Item.class, EventPriority.LOWEST, ModItems::registerItems);

    ConfigManager.loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"));
    REGISTRY.registerEventBus(modBus);
  }
}
