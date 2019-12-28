package noobanidus.mods.glimmering.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.stream.Stream;

public class BlockPosUtil {
  public static Stream<BlockPos> getAllInBox (AxisAlignedBB bb) {
    return BlockPos.getAllInBox(new BlockPos(bb.minX, bb.minY, bb.minZ), new BlockPos(bb.maxX, bb.maxY, bb.maxZ));
  }
}
