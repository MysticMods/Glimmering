package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModTiles;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RitualRuneTile extends TileEntity implements ITickableTileEntity, IEasilyUpdated {
  private static int RITUAL_LENGTH = 20 * 20;
  private int ritualTicks = 0;
  private boolean active = false;
  private NonNullList<ItemStack> stacks = NonNullList.withSize(4, ItemStack.EMPTY);

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
          finishRitual();
          return;
        }
      }

      if (!world.isRemote() && !validateStructure()) {
        endRitual();
      }
    }
  }

  public int getRitualTicks() {
    return ritualTicks;
  }

  public boolean isActive() {
    return active;
  }

  public boolean validateStructure() {
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

    for (BlockPos bowl : getBowls()) {
      BlockState bowlState = world.getBlockState(bowl);
      if (bowlState.getBlock() != ModBlocks.ANDESITE_BOWL.get()) {
        return false;
      }
      TileEntity te = world.getTileEntity(bowl);
      if (!(te instanceof AndesiteBowlTile)) {
        return false;
      }

      AndesiteBowlTile ate = (AndesiteBowlTile) te;
      if (ate.inventory.getStackInSlot(0).isEmpty()) {
        return false;
      }
    }

    return true;
  }

  public List<BlockPos> getPillar(PillarType pillar) {
    BlockPos start = pillar.offset(getPos());
    return Arrays.asList(start, start.up(), start.up().up());
  }

  public List<BlockPos> getBowls() {
    List<BlockPos> result = new ArrayList<>();
    for (Direction dir : Direction.Plane.HORIZONTAL) {
      result.add(getPos().offset(dir, 3));
    }
    return result;
  }

  public int getQuantity() {
    float count = 0;
    for (ItemStack stack : stacks) {
      count += getCount(stack);
    }

    return (int) (Math.floor(count));
  }

  private float getCount(ItemStack stack) {
    if (stack.getItem() == Items.GLOWSTONE_DUST) {
      return 1.5f;
    } else if (stack.getItem() == Items.REDSTONE) {
      return 1.25f;
    } else if (stack.getItem() == Items.LAPIS_LAZULI) {
      return 1f;
    } else if (stack.getItem() == Items.BLAZE_POWDER) {
      return 1.75f;
    } else if (stack.getItem() == Items.GUNPOWDER) {
      return 0.75f;
    } else if (stack.getItem() == Items.SUGAR) {
      return 0.5f;
    } else {
      return 0f;
    }
  }

  public void activate(PlayerEntity player) {
    if (world != null && world.isRemote) {
      return;
    }

    if (this.isActive()) {
      return;
    }

    if (!validateStructure()) {
      player.sendMessage(new TranslationTextComponent("glimmering.message.invalid_structure").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
    } else {
      stacks.clear();
      for (BlockPos bowl : getBowls()) {
        int count = 0;
        TileEntity te = world.getTileEntity(bowl);
        if (te instanceof AndesiteBowlTile) {
          ItemStack inSlot = ((AndesiteBowlTile) te).inventory.extractItem(0, 1, false);
          stacks.set(count, inSlot);
        }
      }

      beginRitual();
    }
  }

  public void finishRitual() {
    if (world != null && world.isRemote()) {
      int quantity = getQuantity();
      ItemStack result = new ItemStack(ModEntities.SPAWN_GLIMMER.get(), quantity);
      ItemEntity resultEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, result);
      world.addEntity(resultEntity);

      // some sort of sound
    }
    endRitual();
  }

  public void beginRitual() {
    this.active = true;
    this.ritualTicks = 0;
    this.stacks.clear();
    updateViaState();
  }

  public void endRitual() {
    this.active = false;
    this.ritualTicks = 0;
    this.stacks.clear();
    updateViaState();
  }

  @Override
  public void read(CompoundNBT compound) {
    this.active = compound.getBoolean("active");
    this.ritualTicks = compound.getInt("ritualTicks");
    ListNBT stackList = compound.getList("stacks", Constants.NBT.TAG_COMPOUND);
    stacks.clear();
    for (int i = 0; i < stackList.size(); i++) {
      ItemStack stack = ItemStack.read(stackList.getCompound(i));
      stacks.set(i, stack);
    }
    super.read(compound);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    compound = super.write(compound);
    compound.putBoolean("active", this.active);
    compound.putInt("ritualTicks", this.ritualTicks);
    ListNBT stackList = new ListNBT();
    for (ItemStack stack : stacks) {
      stackList.add(stack.serializeNBT());
    }
    compound.put("stacks", stackList);
    return compound;
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

  public enum PillarType {
    NORTH_EAST(3, 0, 3, 0),
    NORTH_WEST(3, 0, 0, 3),
    SOUTH_EAST(0, 3, 3, 0),
    SOUTH_WEST(0, 3, 0, 3);

    public final int north, south, east, west;

    public BlockPos offset(BlockPos pos) {
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
