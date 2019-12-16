package noobanidus.mods.glimmering.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.entity.model.GlimmerModel;

@OnlyIn(Dist.CLIENT)
public class GlimmerItemRenderer extends ItemStackTileEntityRenderer {
  public static GlimmerItemRenderer instance = new GlimmerItemRenderer();

  private static GlimmerModel model = new GlimmerModel();

  public GlimmerItemRenderer() {
  }

  @Override
  public void renderByItem(ItemStack itemStackIn) {
    Minecraft mc = Minecraft.getInstance();
    TextureManager tm = mc.getTextureManager();
    tm.bindTexture(new ResourceLocation("glimmering:textures/entity/glimmer_gold.png"));
    GlStateManager.pushMatrix();
    GlStateManager.scalef(0.09F, -0.09F, -0.09F);
    GlStateManager.translatef(11.5f, 6.5f, -1f);
    model.render(null, 0, 0, 0, 0, 0, 0);
    GlStateManager.popMatrix();
  }
}
