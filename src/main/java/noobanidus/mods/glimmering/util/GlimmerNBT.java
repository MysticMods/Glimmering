package noobanidus.mods.glimmering.util;

import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3d;

public class GlimmerNBT {
  public static ListNBT vec3dToNBT(Vector3d start) {
    ListNBT startNBT = new ListNBT();
    startNBT.add(0, DoubleNBT.valueOf(start.x));
    startNBT.add(1, DoubleNBT.valueOf(start.y));
    startNBT.add(2, DoubleNBT.valueOf(start.x));
    return startNBT;
  }

  public static Vector3d vec3dFromNBT(ListNBT nbt) {
    return new Vector3d(nbt.getDouble(0), nbt.getDouble(1), nbt.getDouble(2));
  }
}
