
package noobanidus.mods.glimmering.beam;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.Glimmering;

public class Beam {
  private static ResourceLocation DARK_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/misc/beam_dark.png");

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
  public void render(BufferBuilder bufferBuilder) {
    float f2 = 1.0F;
    float f3 = f2 * 0.5F % 1.0F;
    Vec3d vec3d2 = start.subtract(stop);
    double d0 = vec3d2.length();
    vec3d2 = vec3d2.normalize();
    float f5 = (float) Math.acos(vec3d2.y);
    float f6 = (float) Math.atan2(vec3d2.z, vec3d2.x);
    GlStateManager.rotatef((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
    GlStateManager.rotatef(f5 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
    double d1 = (double) f2 * 0.0D;

    double d12 = Math.cos(d1 + Math.PI) * 0.12D;
    double d13 = Math.sin(d1 + Math.PI) * 0.12D;
    double d14 = Math.cos(d1) * 0.12D;
    double d15 = Math.sin(d1) * 0.12D;
    double d16 = Math.cos(d1 + (Math.PI / 2D)) * 0.12D;
    double d17 = Math.sin(d1 + (Math.PI / 2D)) * 0.12D;
    double d18 = Math.cos(d1 + (Math.PI * 1.5D)) * 0.12D;
    double d19 = Math.sin(d1 + (Math.PI * 1.5D)) * 0.12D;
    double d22 = (double) (f3 - 1.0F);
    double d23 = d0 * 5.05D + d22;

    //bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
    bufferBuilder.pos(d12, 0.0D, d13).tex(1, d23).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d12, -d0, d13).tex(1, d22).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d14, -d0, d15).tex(0.0D, d22).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d14, 0.0D, d15).tex(0.0D, d23).color(255, 255, 255, 255).endVertex();

    bufferBuilder.pos(d16, 0.0D, d17).tex(1, d23).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d16, -d0, d17).tex(1, d22).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d18, -d0, d19).tex(0.0D, d22).color(255, 255, 255, 255).endVertex();
    bufferBuilder.pos(d18, 0.0D, d19).tex(0.0D, d23).color(255, 255, 255, 255).endVertex();
  }
}

