package noobanidus.mods.glimmering.beam;

import net.minecraft.util.math.Vec3d;

import java.util.Objects;

@Deprecated
public class VisualEdge {
  private Vec3d start;
  private Vec3d stop;

  public VisualEdge(Vec3d start, Vec3d stop) {
    this.start = start;
    this.stop = stop;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VisualEdge that = (VisualEdge) o;
    return this.start.x == this.start.x && this.start.y == that.start.y && this.start.z == that.start.z && this.stop.x == this.stop.x && this.stop.y == that.stop.y && this.stop.z == that.stop.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start.x, start.y, start.z, stop.x, stop.y, stop.z);
  }
}
