package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RitualEntity extends UnlivingEntity {
  private static int MAX_TICKS = 20 * 21;
  public static double offset = -2.5;

  public RitualRuneTile tile = null;

  public RitualEntity(EntityType<? extends LivingEntity> type, World worldIn) {
    super(type, worldIn);
  }

  public void setTile(RitualRuneTile tile) {
    this.tile = tile;
  }

  public List<Vec3d> getBowls() {
    List<Vec3d> result = new ArrayList<>();
    BlockPos origin = new BlockPos(posX, posY + offset, posZ);
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      BlockPos bowl = origin.offset(dir, 3);
      result.add(compensate(dir, bowl));
    }
    return result;
  }

  public Vec3d compensate (Direction dir, BlockPos bowl) {
    return compensate(dir, new Vec3d(bowl.getX() + 0.5, bowl.getY() + 1.2, bowl.getZ() + 0.5));
  }

  public Vec3d compensate (Direction dir, Vec3d vec) {
      double tx = vec.x;
      double ty = vec.y;
      double tz = vec.z;
      switch (dir.getAxisDirection()) {
        case POSITIVE:
          switch (dir.getAxis()) {
            case X:
              tx += 0.15;
              break;
            case Z:
              tz += 0.15;
              break;
          }
        case NEGATIVE:
          switch (dir.getAxis()) {
            case X:
              tx -= 0.15;
              break;
            case Z:
              tz -= 0.15;
              break;
          }
      }
      return new Vec3d(tx, ty, tz);
  }

  public List<Vec3d> getPillarTops() {
    BlockPos origin = new BlockPos(posX, posY + offset, posZ);
    List<Vec3d> result = new ArrayList<>();
    for (RitualRuneTile.PillarType pillar : RitualRuneTile.PillarType.values()) {
      List<Direction> dirs = pillar.directions();
      result.add(compensate(dirs.get(1), compensate(dirs.get(0), pillar.offset(origin).up().up())));
    }

    return result;
  }

  public List<BlockPos> getPillar(RitualRuneTile.PillarType pillar) {
    BlockPos origin = new BlockPos(posX, posY + offset, posZ);
    BlockPos start = pillar.offset(origin);
    return Arrays.asList(start, start.up(), start.up().up());
  }

  @Nullable
  public RitualRuneTile getTile() {
    if (tile == null) {
      TileEntity te = world.getTileEntity(getPosition());
      if (te instanceof RitualRuneTile) {
        this.tile = (RitualRuneTile) te;
      } else {
        remove();
      }
    }

    if (tile == null) {
      remove();
    }

    if (tile != null && tile.isRemoved()) {
      remove();
    }

    return tile;
  }

  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }

  public boolean isActive() {
    return this.ticksExisted <= MAX_TICKS;
  }

  private void finishRitual() {
    RitualRuneTile tile = getTile();
    if (tile != null) {
      tile.finishRitual();
    }
    this.remove();
  }

  @Override
  public void tick() {
    super.tick();
    if (!this.isActive()) {
      finishRitual();
    }
  }
}
