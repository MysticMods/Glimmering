package noobanidus.mods.glimmering.items;

import com.tterrag.registrate.util.LazySpawnEggItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class GlimmerSpawnItem<T extends Entity> extends LazySpawnEggItem<T> {
  public GlimmerSpawnItem(Supplier<EntityType<T>> type, int primaryColor, int secondaryColor, Properties properties) {
    super(type, primaryColor, secondaryColor, properties);
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    World world = context.getWorld();

    ItemStack itemstack = context.getItem();
    BlockPos blockpos = context.getPos();
    Direction direction = context.getFace();
    BlockState blockstate = world.getBlockState(blockpos);

    if (!world.getEntitiesWithinAABB((EntityType<?>) null, new AxisAlignedBB(blockpos.offset(direction.getOpposite())), (o) -> true).isEmpty()) {
      return ActionResultType.FAIL;
    }

    if (world.isRemote) {
      return ActionResultType.SUCCESS;
    } else {

      BlockPos blockpos1;
      if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
        blockpos1 = blockpos;
      } else {
        blockpos1 = blockpos.offset(direction);
      }

      EntityType<?> entitytype = this.getType(itemstack.getTag());
      if (entitytype.spawn(world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
        itemstack.shrink(1);
      }

      return ActionResultType.SUCCESS;
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    if (worldIn.isRemote) {
      return new ActionResult<>(ActionResultType.PASS, itemstack);
    } else {
      RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
      if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
        return new ActionResult<>(ActionResultType.PASS, itemstack);
      } else {
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
        BlockPos blockpos = blockraytraceresult.getPos();
        if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
          return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {

          if (!worldIn.getEntitiesWithinAABB((EntityType<?>) null, new AxisAlignedBB(blockpos.up()), (o) -> true).isEmpty()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
          }

          EntityType<?> entitytype = this.getType(itemstack.getTag());
          if (entitytype.spawn(worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
          } else {
            if (!playerIn.abilities.isCreativeMode) {
              itemstack.shrink(1);
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
          }
        } else {
          return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
      }
    }
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (worldIn == null) {
      return;
    }
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line0").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line1", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
    tooltip.add(new TranslationTextComponent("glimmering.tooltip.line2", ModItems.RITUAL_KNIFE.get().getName().setStyle(new Style().setColor(TextFormatting.RED).setBold(true))).setStyle(new Style().setColor(TextFormatting.GOLD)));
  }
}
