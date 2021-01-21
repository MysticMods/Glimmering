package noobanidus.mods.glimmering.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import noobanidus.mods.glimmering.GLTags;
import noobanidus.mods.glimmering.blocks.RitualRuneBlock;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.init.ModBlocks;
import noobanidus.mods.glimmering.init.ModEntities;
import noobanidus.mods.glimmering.init.ModItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RitualRuneTile extends TileEntity implements ITickableTileEntity, IEasilyUpdated {
  private boolean active = false;
  private int quantity;
  private RitualEntity ritualEntity;

  public RitualRuneTile(TileEntityType<? extends RitualRuneTile> tile) {
    super(tile);
  }

  @Override
  public void tick() {
    if (world == null) {
      return;
    }

    if (this.active) {
      if (!world.isRemote() && !validateStructure()) {
        endRitual();
        return;
      }

      if (this.ritualEntity != null && this.ritualEntity.isAlive() && !this.ritualEntity.isActive()) {
        endRitual();
        return;
      }

      if (this.ritualEntity == null) {

        List<RitualEntity> entities = world.getEntitiesWithinAABB(RitualEntity.class, new AxisAlignedBB(getPos()), (o) -> true);
        boolean foundValid = false;
        if (!entities.isEmpty()) {
          for (RitualEntity entity : entities) {
            if (entity.isActive() && entity.isAlive()) {
              foundValid = true;
              break;
            }
          }
        }

        if (!foundValid) {
          endRitual();
        }
      }
    }
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
      if (pillarState3.getBlock() != ModBlocks.ANDESITE_CAPSTONE.get()) {
        return false;
      }
    }

    for (BlockPos bowl : getBowls()) {
      BlockState bowlState = world.getBlockState(bowl);
      if (bowlState.getBlock() != ModBlocks.ANDESITE_BOWL.get()) {
        return false;
      }
    }

    return true;
  }

  public boolean validateIngredients() {
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

  public static float getCount(ItemStack stack) {
    if (stack.getItem().isIn(GLTags.Items.EMPOWERED_DUSTS)) {
      return 1.5f;
    } else if (stack.getItem().isIn(GLTags.Items.SUPERIOR_DUSTS)) {
      return 1.25f;
    } else if (stack.getItem().isIn(GLTags.Items.COMMON_DUSTS)) {
      return 1f;
    } else if (stack.getItem().isIn(GLTags.Items.MYTHICAL_DUSTS)) {
      return 1.75f;
    } else if (stack.getItem().isIn(GLTags.Items.INFERIOR_DUSTS)) {
      return 0.75f;
    } else if (stack.getItem().isIn(GLTags.Items.INCONSEQUENTIAL_DUSTS)) {
      return 0.25f;
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
      player.sendMessage(new TranslationTextComponent("glimmering.message.invalid_structure").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.LIGHT_PURPLE))), Util.DUMMY_UUID);
    } else if (!validateIngredients()) {
      player.sendMessage(new TranslationTextComponent("glimmering.message.invalid_ingredients").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.LIGHT_PURPLE))), Util.DUMMY_UUID);
    } else {
      float count = 0;
      for (BlockPos bowl : getBowls()) {
        TileEntity te = world.getTileEntity(bowl);
        if (te instanceof AndesiteBowlTile) {
          ItemStack inSlot = ((AndesiteBowlTile) te).inventory.extractItem(0, 1, false);
          count += getCount(inSlot);
        }
      }
      this.quantity = (int) Math.floor(count);

      beginRitual();
    }
  }

  public void finishRitual() {
    if (world != null && !world.isRemote()) {
      ItemStack result = new ItemStack(ModItems.SPAWN_GLIMMER.get(), quantity);
      ItemEntity resultEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, result);
      world.addEntity(resultEntity);
      world.setBlockState(pos, ModBlocks.RITUAL_RUNE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, false));
      for (PillarType p : PillarType.values()) {
        BlockPos capstone = getPillar(p).get(2);
        world.setBlockState(capstone, ModBlocks.ANDESITE_CAPSTONE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, false));
      }
      // some sort of sound
    }

    this.active = false;
    this.ritualEntity = null;
    this.quantity = 0;
  }

  public void beginRitual() {
    if (world == null || world.isRemote()) {
      return;
    }

    if (this.ritualEntity != null) {
      if (this.ritualEntity.isActive() && this.ritualEntity.isAlive()) {
        return;
      }

      this.ritualEntity.remove();
      this.ritualEntity = null;
    }
    List<RitualEntity> entities = world.getEntitiesWithinAABB(RitualEntity.class, new AxisAlignedBB(getPos()), (o) -> true);
    boolean foundViable = false;
    if (!entities.isEmpty()) {
      for (RitualEntity entity : entities) {
        if (foundViable) {
          entity.remove();
        }
        if (entity.isActive() && entity.isAlive()) {
          foundViable = true;
        }
      }
    }
    if (!foundViable) {
      RitualEntity ritual = ModEntities.RITUAL_TYPE.get().create(world);
      if (ritual == null) {
        return;
      }

      ritual.setTile(this);
      this.ritualEntity = ritual;
      BlockPos pos = getPos();
      ritual.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
      world.addEntity(ritual);
      world.setBlockState(pos, ModBlocks.RITUAL_RUNE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, true));
      for (PillarType p : PillarType.values()) {
        BlockPos capstone = getPillar(p).get(2);
        world.setBlockState(capstone, ModBlocks.ANDESITE_CAPSTONE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, true));
      }
    }

    this.active = true;
    updateViaState(this);
  }

  public void endRitual() {
    if (world == null || world.isRemote()) {
      return;
    }
    world.setBlockState(pos, ModBlocks.RITUAL_RUNE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, false));
    for (PillarType p : PillarType.values()) {
      BlockPos capstone = getPillar(p).get(2);
      world.setBlockState(capstone, ModBlocks.ANDESITE_CAPSTONE.get().getDefaultState().with(RitualRuneBlock.ACTIVE, false));
    }
    this.active = false;
    this.quantity = 0;
    if (this.ritualEntity != null) {
      this.ritualEntity.remove();
      this.ritualEntity = null;
    }
    world.getEntitiesWithinAABB(RitualEntity.class, new AxisAlignedBB(getPos()), (o) -> true).forEach(Entity::remove);
    updateViaState(this);
  }

  @Override
  public void read(BlockState state, CompoundNBT compound) {
    this.active = compound.getBoolean("active");
    if (compound.contains("quantity")) {
      this.quantity = compound.getInt("quantity");
    }
    if (compound.contains("ritualEntity")) {
      if (world != null) {
        int entityId = compound.getInt("ritualEntity");
        Entity entity = world.getEntityByID(entityId);
        if (entity instanceof RitualEntity) {
          this.ritualEntity = (RitualEntity) entity;
        }
      }
    }
    super.read(state, compound);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    compound = super.write(compound);
    compound.putBoolean("active", this.active);
    compound.putInt("quantity", this.quantity);
    if (this.ritualEntity != null) {
      compound.putInt("ritualEntity", this.ritualEntity.getEntityId());
    }
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
    read(ModBlocks.RITUAL_RUNE.get().getDefaultState(), pkt.getNbtCompound());
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

    public List<Direction> directions() {
      List<Direction> result = new ArrayList<>();
      if (north != 0) {
        result.add(Direction.NORTH);
      }
      if (south != 0) {
        result.add(Direction.SOUTH);
      }
      if (east != 0) {
        result.add(Direction.EAST);
      }
      if (west != 0) {
        result.add(Direction.WEST);
      }
      return result;
    }
  }
}
