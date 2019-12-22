package noobanidus.mods.glimmering.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class OrbParticleType extends ParticleType<OrbParticleData> {
  public OrbParticleType() {
    super(false, OrbParticleData.DESERIALIZER);
  }

  public static class Factory implements IParticleFactory<OrbParticleData> {

    @Nullable
    @Override
    public Particle makeParticle(OrbParticleData data, World world, double x, double y, double z, double mx, double my, double mz) {
      return new OrbParticle(world, x, y, z, data.size, data.r, data.g, data.b, data.m);
    }
  }
}
