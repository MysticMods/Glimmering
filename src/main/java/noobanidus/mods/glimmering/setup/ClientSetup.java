package noobanidus.mods.glimmering.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import noobanidus.mods.glimmering.client.render.entity.GlimmerStarRenderer;
import noobanidus.mods.glimmering.client.render.entity.LargeGlimmerRenderer;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;
import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.client.model.ModelHolder;
import noobanidus.mods.glimmering.client.render.entity.GlimmerRenderer;
import noobanidus.mods.glimmering.client.render.entity.RitualRenderer;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModParticles;
import noobanidus.mods.glimmering.init.ModTiles;
import noobanidus.mods.glimmering.particle.BeamParticle;
import noobanidus.mods.glimmering.particle.GlintParticle;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;
import noobanidus.mods.glimmering.client.render.tile.AndesiteBowlTileRenderer;

public class ClientSetup {

  public static void init(FMLClientSetupEvent event) {
    ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new ModelHolder());
    ModelHolder.init();

    RenderingRegistry.registerEntityRenderingHandler(ModEntities.LARGE_GLIMMER_TYPE.get(), new LargeGlimmerRenderer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(ModEntities.GLIMMER_STAR_TYPE.get(), new GlimmerStarRenderer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(ModEntities.GLIMMER_TYPE.get(), new GlimmerRenderer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(ModEntities.RITUAL_TYPE.get(), new RitualRenderer.Factory());
    ClientRegistry.bindTileEntityRenderer(ModTiles.ANDESITE_BOWL.get(), new AndesiteBowlTileRenderer());
  }

  public static void registerParticles(ParticleFactoryRegisterEvent event) {
    Minecraft.getInstance().particles.registerFactory(ModParticles.BEAM.get(), new BeamParticle.Type.Factory());
    Minecraft.getInstance().particles.registerFactory(ModParticles.GLINT.get(), new GlintParticle.Type.Factory());
  }
}
