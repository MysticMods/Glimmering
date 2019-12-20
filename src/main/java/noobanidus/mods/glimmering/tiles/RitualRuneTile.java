package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModTiles;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class RitualRuneTile extends TileEntity implements ITickableTileEntity, IEasilyUpdated {
  private static int RITUAL_LENGTH = 20 * 20;
  private int ritualTicks = 0;
  private boolean active = false;

  public RitualRuneTile() {
    super(ModTiles.RUNE.get());
  }

  @Override
  public void tick() {
    if (world == null) {
      return;
    }

    if (this.active) {
      ritualTicks++;
      if (ritualTicks >= RITUAL_LENGTH) {
        if (!world.isRemote()) {
          endRitual();
        }
      }
    }
  }

  public int getRitualTicks() {
    return ritualTicks;
  }

  public boolean isActive() {
    return active;
  }

  public boolean validateStructure () {
    if (world == null || world.isRemote()) {
      return false;
    }

    // Resolve the stairs
    BlockState northStairs = world.getBlockState(getPos().offset(Direction.NORTH, 1));
    if (northStairs.getBlock() != ModBlocks.BRICK_STAIRS.get()) {
      return false;
    }

    if (northStairs.get(StairsBlock.FACING) != Direction.SOUTH) {
      return false;
    }

    BlockState southStairs = world.getBlockState(getPos().offset(Direction.SOUTH, 1));
    if (southStairs.getBlock() != ModBlocks.BRICK_STAIRS.get()) {
      return false;
    }

    if (southStairs.get(StairsBlock.FACING) != Direction.NORTH) {
      return false;
    }

    BlockState eastStairs = world.getBlockState(getPos().offset(Direction.EAST, 1));
    if (eastStairs.getBlock() != ModBlocks.BRICK_STAIRS.get()) {
      return false;
    }

    if (eastStairs.get(StairsBlock.FACING) != Direction.WEST) {
      return false;
    }

    BlockState westStairs = world.getBlockState(getPos().offset(Direction.WEST, 1));
    if (westStairs.getBlock() != ModBlocks.BRICK_STAIRS.get()) {
      return false;
    }

    if (westStairs.get(StairsBlock.FACING) != Direction.EAST) {
      return false;
    }

    BlockState pillar = world.getBlockState(getPos().offset(Direction.NORTH).offset(Direction.EAST));

    if (pillar.getBlock() != Blocks.POLISHED_ANDESITE) {
      return false;
    }

    pillar = world.getBlockState(getPos().offset(Direction.NORTH).offset(Direction.WEST));
    if (pillar.getBlock() != Blocks.POLISHED_ANDESITE) {
      return false;
    }

    pillar = world.getBlockState(getPos().offset(Direction.SOUTH).offset(Direction.EAST));
    if (pillar.getBlock() != Blocks.POLISHED_ANDESITE) {
      return false;
    }

    pillar = world.getBlockState(getPos().offset(Direction.SOUTH).offset(Direction.WEST));
    if (pillar.getBlock() != Blocks.POLISHED_ANDESITE) {
      return false;
    }

    for (Direction dir : Direction.Plane.HORIZONTAL) {
      BlockPos bowl = getBowl(dir);
      BlockState bowlState = world.getBlockState(bowl);
      if (bowlState.getBlock() != ModBlocks.ANDESITE_BOWL.get()) {
        return false;
      }
    }

    for (PillarType p : PillarType.values()) {
      List<BlockPos> pos = getPillar(p);
      BlockState pillarState1 = world.getBlockState(pos.get(0));
      BlockState pillarState2 = world.getBlockState(pos.get(1));
      BlockState pillarState3 = world.getBlockState(pos.get(2));
      if (pillarState1.getBlock() != ModBlocks.BRICKS.get() || pillarState2.getBlock() != ModBlocks.BRICKS.get()) {
        return false;
      }
      if (pillarState3.getBlock() != Blocks.POLISHED_ANDESITE) {
        return false;
      }
    }

    return true;
  }

  public List<BlockPos> getPillar (PillarType pillar) {
    BlockPos start = pillar.offset(getPos());
    return Arrays.asList(start, start.up(), start.up().up());
  }

  public BlockPos getBowl (Direction dir) {
    return getPos().offset(dir, 3);
  }

  public void activate(PlayerEntity player) {
    if (world != null && world.isRemote) {
      return;
    }

    if (!validateStructure()) {
      player.sendMessage(new TranslationTextComponent("glimmering.message.invalid_structure").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
    }
  }

  public void beginRitual () {
    this.active = true;
    this.ritualTicks = 0;
    updateViaState();
  }

  public void endRitual () {
    this.active = false;
    this.ritualTicks = 0;
    updateViaState();
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    return super.write(compound);
  }

  @Override
  public CompoundNBT getUpdateTag() {
    return this.write(new CompoundNBT());
  }

  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    return new SUpdateTileEntityPacket(this.pos, 9, getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    read(pkt.getNbtCompound());
  }

  private enum PillarType {
    NORTH_EAST(3, 0, 3, 0),
    NORTH_WEST(3, 0, 0, 3),
    SOUTH_EAST(0, 3, 3, 0),
    SOUTH_WEST(0, 3, 0, 3);

    public final int north, south, east, west;

    public BlockPos offset (BlockPos pos) {
      return pos.offset(Direction.NORTH, north).offset(Direction.SOUTH, south).offset(Direction.EAST, east).offset(Direction.WEST, west);
    }

    PillarType(int north, int south, int east, int west) {
      this.north = north;
      this.south = south;
      this.east = east;
      this.west = west;
    }
  }
}
