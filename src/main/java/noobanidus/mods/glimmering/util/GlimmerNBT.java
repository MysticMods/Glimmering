package noobanidus.mods.glimmering.util;

import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Vec3d;

public class GlimmerNBT {
  public static ListNBT vec3dToNBT (Vec3d start) {
    ListNBT startNBT = new ListNBT();
    startNBT.add(0, new DoubleNBT(start.x));
    startNBT.add(1, new DoubleNBT(start.y));
    startNBT.add(2, new DoubleNBT(start.x));
    return startNBT;
  }

  public static Vec3d vec3dFromNBT (ListNBT nbt) {
    return new Vec3d(nbt.getDouble(0), nbt.getDouble(1), nbt.getDouble(2));
  }
}
