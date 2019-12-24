package noobanidus.mods.glimmering.client.render;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.particle.BeamParticle;

@OnlyIn(Dist.CLIENT)
public class BeamRenderer {
  public static void addBeam(World world, Vec3d orig, Vec3d end, int age) {
    float r = 227 / 255f;
    float g = 103 / 255f;
    float b = 13 / 255f;
    addBeam(world, orig, end, age, r, g, b);
  }

  public static void addBeam(World world, Vec3d orig, Vec3d end, int age, float r, float g, float b) {
    Vec3d diff = end.subtract(orig);
    Vec3d movement = diff.normalize().mul(0.1, 0.1, 0.1);
    int iters = (int) (mag(diff) / mag(movement));

    Vec3d current = orig;
    for (int i = 0; i < iters; i++) {
      BeamParticle.Data data = new BeamParticle.Data(1.4f, r, g, b, age);
      world.addParticle(data, current.x, current.y, current.z, 0, 0, 0);
      current = current.add(movement);
    }
  }

  public static double mag(Vec3d vec) {
    return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
  }
}
