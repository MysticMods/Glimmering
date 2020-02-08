package noobanidus.mods.glimmering;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DataSerializerEntry;
import noobanidus.mods.glimmering.client.beam.BeamManager;
import noobanidus.mods.glimmering.config.ConfigManager;
import noobanidus.mods.glimmering.energy.EnergyTick;
import noobanidus.mods.glimmering.events.RightClickHandler;
import noobanidus.mods.glimmering.init.*;
import noobanidus.mods.glimmering.setup.ClientSetup;
import noobanidus.mods.glimmering.setup.ModSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("glimmering")
public class Glimmering {
  public static final Logger LOG = LogManager.getLogger();
  public static final String MODID = "glimmering";

  private static CommandSerializers serializerCommand;

  public static final ItemGroup ITEM_GROUP = new ItemGroup("glimmering") {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModItems.SPAWN_GLIMMER.get());
    }
  };

  public static Registrate REGISTRATE;

  private static ModSetup setup = new ModSetup();

  public Glimmering() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    modBus.addListener(setup::init);
    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      modBus.addListener(ClientSetup::init);
      modBus.addListener(ClientSetup::registerParticles);
      MinecraftForge.EVENT_BUS.addListener(BeamManager::tick);
      MinecraftForge.EVENT_BUS.addListener(BeamManager::render);
      ModParticles.load();
    });

    REGISTRATE = Registrate.create(MODID);
    REGISTRATE.itemGroup(NonNullSupplier.of(() -> ITEM_GROUP));
    REGISTRATE.addDataGenerator(ProviderType.LANG, ModLang.instance);
    REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ModTags.instance);

    ModItems.load();
    ModBlocks.load();
    ModEntities.load();
    ModTiles.load();

    MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, true, RightClickHandler::onRightClick);
    MinecraftForge.EVENT_BUS.addListener(EnergyTick::tick);
    MinecraftForge.EVENT_BUS.addListener(Glimmering::onServerStarting);
    ModParticles.particleRegistry.register(modBus);

    ConfigManager.loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"));
  }

  public static void onServerStarting (FMLServerStartingEvent event) {
    //serializerCommand = new CommandSerializers(event.getCommandDispatcher()).register();
  }
}
