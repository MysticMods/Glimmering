
package noobanidus.mods.glimmering.client.beam;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.Glimmering;

public class Beam {
  private static ResourceLocation DARK_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/misc/beam_gold.png");

  private Vector3d start;
  private Vector3d stop;
  private int age = 0;
  private int maxAge;
  private boolean removed = false;

  public Beam(Vector3d start, Vector3d stop, int maxAge) {
    this.start = start;
    this.stop = stop;
    this.maxAge = maxAge;
  }

  public void remove() {
    this.removed = true;
  }

  public boolean removed() {
    return removed;
  }

  public ResourceLocation getTexture() {
    return DARK_TEXTURE;
  }

  public void tick() {
    this.age++;

    if (this.age >= maxAge) {
      this.remove();
    }
  }

  @OnlyIn(Dist.CLIENT)
  public void render(BufferBuilderWrapper buffer, boolean alpha) {
    Vector3d subtracted = start.subtract(stop);
    float f2 = 1.0F;
    float f3 = f2 * 0.5F % 1.0F;
    double d0 = subtracted.length();
    double d22 = (double) (f3 - 1.0F);
    double d23 = d0 * 5.05D + d22;
    double r = 0.12d;
    Vector3d cross1 = subtracted.crossProduct(new Vector3d(0, 1, 0)).normalize().mul(r, r, r);
    if (cross1.x == 0 && cross1.y == 0 && cross1.z == 0) {
      cross1 = subtracted.crossProduct(new Vector3d(1, 0, 0)).normalize().mul(r, r, r);
    }
    Vector3d cross2 = subtracted.crossProduct(cross1).normalize().mul(r, r, r);

    Vector3d a = start.add(cross1);
    Vector3d b = stop.add(cross1);
    Vector3d c = stop.subtract(cross1);
    Vector3d d = start.subtract(cross1);

    /*Vector3d a = new Vector3d(start.x - 0.15, start.y + 0.1, start.z);
    Vector3d b = new Vector3d(start.x, start.y - 0.1, start.z - 0.15);
    Vector3d c = new Vector3d(stop.x - 0.15, stop.y + 0.1, stop.z);
    Vector3d d = new Vector3d(stop.x, stop.y - 0.1, stop.z - 0.15);*/

    if (!alpha) {
      buffer.pos(a).tex(1, d22).color255().endVertex();
      buffer.pos(b).tex(1, d22).color255().endVertex();

      buffer.pos(c).tex(0, d23).color255().endVertex();
      buffer.pos(d).tex(0, d23).color255().endVertex();
    } else {
      buffer.pos(a).tex(1, d22).color255a().endVertex();
      buffer.pos(b).tex(1, d22).color255a().endVertex();

      buffer.pos(c).tex(0, d23).color255a().endVertex();
      buffer.pos(d).tex(0, d23).color255a().endVertex();
    }

    /*Vector3d e = new Vector3d(start.x, start.y + 0.1, start.z - 0.15);
    Vector3d f = new Vector3d(start.x - 0.15, start.y - 0.1, start.z);
    Vector3d g = new Vector3d(stop.x, stop.y + 0.1, stop.z - 0.15);
    Vector3d h = new Vector3d(stop.x - 0.15, stop.y - 0.1, stop.z);*/

    Vector3d e = start.add(cross2);
    Vector3d f = stop.add(cross2);
    Vector3d g = stop.subtract(cross2);
    Vector3d h = start.subtract(cross2);

    if (!alpha) {
      buffer.pos(e).tex(1, d22).color255().endVertex();
      buffer.pos(f).tex(1, d22).color255().endVertex();

      buffer.pos(g).tex(0, d23).color255().endVertex();
      buffer.pos(h).tex(0, d23).color255().endVertex();
    } else {
      buffer.pos(e).tex(1, d22).color255a().endVertex();
      buffer.pos(f).tex(1, d22).color255a().endVertex();

      buffer.pos(g).tex(0, d23).color255a().endVertex();
      buffer.pos(h).tex(0, d23).color255a().endVertex();
    }
  }

  @OnlyIn(Dist.CLIENT)
  private BufferBuilder pos(BufferBuilder builder, Vector3d vec) {
    return builder.pos(vec.x, vec.y, vec.z);
  }
}

