
package noobanidus.mods.glimmering.client.beam;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.Glimmering;

public class Beam {
  private static ResourceLocation DARK_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/misc/beam_gold.png");

  private Vec3d start;
  private Vec3d stop;
  private int age = 0;
  private int maxAge;
  private boolean removed = false;

  public Beam(Vec3d start, Vec3d stop, int maxAge) {
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
  public void render(BufferBuilder buffer) {
    Vec3d subtracted = start.subtract(stop);
    float f2 = 1.0F;
    float f3 = f2 * 0.5F % 1.0F;
    double d0 = subtracted.length() + 2;
    double d22 = (double) (f3 - 1.0F);
    double d23 = d0 * 5.05D + d22;

    Vec3d a = new Vec3d(start.x - 0.15, start.y + 0.15, start.z);
    Vec3d b = new Vec3d(start.x, start.y - 0.15, start.z - 0.15);
    Vec3d c = new Vec3d(stop.x - 0.15, stop.y + 0.15, stop.z);
    Vec3d d = new Vec3d(stop.x, stop.y - 0.15, stop.z - 0.15);

    buffer.pos(a.x, a.y, a.z).tex(1, d22).color(255, 255, 255, 255).endVertex();
    buffer.pos(c.x, c.y, c.z).tex(1, d22).color(255, 255, 255, 255).endVertex();

    buffer.pos(d.x, d.y, d.z).tex(0, d23).color(255, 255, 255, 255).endVertex();
    buffer.pos(b.x, b.y, b.z).tex(0, d23).color(255, 255, 255, 255).endVertex();

    Vec3d e = new Vec3d(start.x, start.y + 0.15, start.z - 0.15);
    Vec3d f = new Vec3d(start.x - 0.15, start.y - 0.15, start.z);
    Vec3d g = new Vec3d(stop.x, stop.y + 0.15, stop.z - 0.15);
    Vec3d h = new Vec3d(stop.x - 0.15, stop.y - 0.15, stop.z);

    buffer.pos(e.x, e.y, e.z).tex(1, d22).color(255, 255, 255, 255).endVertex();
    buffer.pos(g.x, g.y, g.z).tex(1, d22).color(255, 255, 255, 255).endVertex();

    buffer.pos(h.x, h.y, h.z).tex(0, d23).color(255, 255, 255, 255).endVertex();
    buffer.pos(f.x, f.y, f.z).tex(0, d23).color(255, 255, 255, 255).endVertex();
  }
}

