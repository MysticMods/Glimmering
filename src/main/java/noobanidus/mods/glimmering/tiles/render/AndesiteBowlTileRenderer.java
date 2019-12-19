package noobanidus.mods.glimmering.tiles.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import noobanidus.mods.glimmering.tiles.AndesiteBowlTile;

import java.util.Random;

public class AndesiteBowlTileRenderer extends TileEntityRenderer<AndesiteBowlTile> {
  @Override
  public void render(AndesiteBowlTile tei, double x, double y, double z, float partialTicks, int destroyStage) {
    ItemStack inSlot = tei.inventory.getStackInSlot(0);
    if (!inSlot.isEmpty()) {
      Random random = new Random();
      int count = getCount(inSlot);
      ItemRenderer r = Minecraft.getInstance().getItemRenderer();
      for (int i = 0; i < count; i++) {
        random.setSeed(tei.hashCode() + 256 * i);
        GlStateManager.pushMatrix();
        float a = (i == 0) ? 0 : (random.nextFloat() - 0.5f) * (5 - i) * 0.055f;
        float b = (i == 0) ? 0 : (random.nextFloat() - 0.5f) * (5 - i) * 0.055f;
        GlStateManager.translatef((float) x + 0.5f + a, (float) y + 0.8f + 0.04f * i, (float) z + 0.65f + b);
        GlStateManager.rotated(180, 0, 1, 0);
        GlStateManager.rotated(90, 1.0, 0, 0);


        r.renderItem(inSlot, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
      }
    }
  }

  public int getCount(ItemStack s) {
    if (s.getCount() == 64) {
      return 5;
    }
    if (s.getCount() > 33) {
      return 4;
    }
    if (s.getCount() > 16) {
      return 3;
    }
    if (s.getCount() >= 2) {
      return 2;
    }
    if (s.getCount() == 1) {
      return 1;
    }
    return 0;
  }
}
