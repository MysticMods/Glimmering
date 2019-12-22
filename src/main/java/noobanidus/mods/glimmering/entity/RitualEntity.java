package noobanidus.mods.glimmering.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;

import javax.annotation.Nullable;

public class RitualEntity extends UnlivingEntity {
  private static int MAX_TICKS = 20 * 20;

  private RitualRuneTile tile = null;

  public RitualEntity(EntityType<? extends LivingEntity> type, World worldIn) {
    super(type, worldIn);
  }

  public void setTile (RitualRuneTile tile) {
    this.tile = tile;
  }

  @Nullable
  private RitualRuneTile getTile () {
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

  public boolean isActive () {
    return this.ticksExisted < 20 * 20; // 900; // MAX_TICKS;
  }

  private void finishRitual () {
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
      return;
    }
  }
}
