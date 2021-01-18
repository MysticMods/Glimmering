package noobanidus.mods.glimmering.client.beam;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Vector3d;

public class BufferBuilderWrapper {
  private BufferBuilder buffer;

  public BufferBuilderWrapper(BufferBuilder buffer) {
    this.buffer = buffer;
  }

  public BufferBuilderWrapper pos(Vector3d vec) {
    this.buffer = buffer.pos(vec.x, vec.y, vec.z);
    return this;
  }

  public BufferBuilderWrapper pos(double x, double y, double z) {
    this.buffer = buffer.pos(x, y, z);
    return this;
  }

  public BufferBuilderWrapper tex(double a, double b) {
    this.buffer = buffer.tex(a, b);
    return this;
  }

  public BufferBuilderWrapper color(int red, int green, int blue, int alpha) {
    this.buffer = buffer.color(red, green, blue, alpha);
    return this;
  }

  public BufferBuilderWrapper color255() {
    this.buffer = buffer.color(255, 255, 255, 255);
    return this;
  }

  public BufferBuilderWrapper color255a() {
    this.buffer = buffer.color(255, 255, 255, 50);
    return this;
  }

  public void endVertex() {
    this.buffer.endVertex();
  }
}
