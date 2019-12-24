package noobanidus.mods.glimmering.init;

import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.particle.BeamParticle;
import noobanidus.mods.glimmering.particle.GlintParticle;
import noobanidus.mods.glimmering.particle.OrbParticle;

public class ModParticles {
  public static final DeferredRegister<ParticleType<?>> particleRegistry = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Glimmering.MODID);

  public static final RegistryObject<BeamParticle.Type> BEAM = particleRegistry.register("beam", BeamParticle.Type::new);
  public static final RegistryObject<OrbParticle.Type> ORB = particleRegistry.register("orb", OrbParticle.Type::new);
  public static final RegistryObject<GlintParticle.Type> GLINT = particleRegistry.register("glint", GlintParticle.Type::new);

  public static void load() {
  }
}
