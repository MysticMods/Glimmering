package noobanidus.mods.glimmering.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BeamParticleType extends ParticleType<BeamParticleData> {
  public BeamParticleType() {
    super(false, BeamParticleData.DESERIALIZER);
  }

  public static class Factory implements IParticleFactory<BeamParticleData> {

    @Nullable
    @Override
    public Particle makeParticle(BeamParticleData data, World world, double x, double y, double z, double mx, double my, double mz) {
      return new BeamParticle(world, x, y, z, data.size, data.r, data.g, data.b, data.m);
    }
  }
}
