package noobanidus.mods.glimmering.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.model.ModelHolder;
import noobanidus.mods.glimmering.entity.render.GlimmerRenderer;
import noobanidus.mods.glimmering.init.ModParticles;
import noobanidus.mods.glimmering.particle.BeamParticleType;

public class ClientSetup {

  public static void init(FMLClientSetupEvent event) {
    ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new ModelHolder());
    ModelHolder.init();

    RenderingRegistry.registerEntityRenderingHandler(GlimmerEntity.class, new GlimmerRenderer.Factory());
  }

  public static void registerParticles(ParticleFactoryRegisterEvent event) {
    Minecraft.getInstance().particles.registerFactory(ModParticles.BEAM.get(), new BeamParticleType.Factory());
  }
}
