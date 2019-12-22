package noobanidus.mods.glimmering.init;

import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.particle.BeamParticleType;
import noobanidus.mods.glimmering.particle.OrbParticleType;

public class ModParticles {
  public static final DeferredRegister<ParticleType<?>> particleRegistry = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Glimmering.MODID);

  public static final RegistryObject<BeamParticleType> BEAM = particleRegistry.register("beam", BeamParticleType::new);
  public static final RegistryObject<OrbParticleType> ORB = particleRegistry.register("orb", OrbParticleType::new);

  public static void load() {
  }
}
