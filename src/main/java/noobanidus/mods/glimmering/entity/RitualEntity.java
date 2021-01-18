package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.particle.GlintParticle;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RitualEntity extends UnlivingEntity {
  private static int MAX_TICKS = 240; // 20 * 21;
  public static double offset = -2.5;

  public RitualRuneTile tile = null;

  public RitualEntity(EntityType<? extends LivingEntity> type, World worldIn) {
    super(type, worldIn);
  }

  public void setTile(RitualRuneTile tile) {
    this.tile = tile;
  }

  public List<Vector3d> getBowls() {
    List<Vector3d> result = new ArrayList<>();
    BlockPos origin = new BlockPos(getPosX(), getPosY() + offset, getPosZ());
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      BlockPos bowl = origin.offset(dir, 3);
      result.add(compensate(dir, bowl));
    }
    return result;
  }

  public Vector3d compensate(Direction dir, BlockPos bowl) {
    return compensate(dir, new Vector3d(bowl.getX() + 0.5, bowl.getY() + 1.2, bowl.getZ() + 0.5));
  }

  public Vector3d compensate(Direction dir, Vector3d vec) {
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
    return new Vector3d(tx, ty, tz);
  }

  public List<Vector3d> getPillarTops() {
    BlockPos origin = new BlockPos(getPosX(), getPosY() + offset, getPosZ());
    List<Vector3d> result = new ArrayList<>();
    for (RitualRuneTile.PillarType pillar : RitualRuneTile.PillarType.values()) {
      List<Direction> dirs = pillar.directions();
      result.add(compensate(dirs.get(1), compensate(dirs.get(0), pillar.offset(origin).up().up())));
    }

    return result;
  }

  public List<BlockPos> getPillar(RitualRuneTile.PillarType pillar) {
    BlockPos origin = new BlockPos(getPosX(), getPosY(), getPosZ());
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
    if (world.isRemote) {
      float r = 227 / 255f;
      float g = 103 / 255f;
      float b = 13 / 255f;
      if (ticksExisted < MAX_TICKS - 40) {
        for (int i = 0; i < Math.log(ticksExisted) * 10 * 0.09; i++) {
          GlintParticle.Data data = new GlintParticle.Data(0.8f, r, g, b, 0.9f + (rand.nextFloat() - 0.5f), 0.5f + (rand.nextFloat() - 0.5f), 20, 0f);
          world.addParticle(data, getPosX(), getPosY() + 2.3, getPosZ(), (rand.nextFloat() - 0.5f) * ticksExisted * 0.1, (rand.nextFloat() - 0.5f) * ticksExisted * 0.1, (rand.nextFloat() - 0.5f) * ticksExisted * 0.1);
        }
      }
      if (ticksExisted == MAX_TICKS) {
        for (int i = 0; i < 400; i++) {
          GlintParticle.Data data = new GlintParticle.Data(0.8f, r, g, b, 0.9f + 1, 1, 30, 0f);
          world.addParticle(data, getPosX(), getPosY() + 1.7, getPosZ(), (rand.nextFloat() - 0.5) * 4, (rand.nextFloat() - 0.5) * 4, (rand.nextFloat() - 0.5) * 4);
        }
      }
    }
  }
}
