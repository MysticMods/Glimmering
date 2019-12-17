package noobanidus.mods.glimmering.entity.render;

import com.mojang.blaze3d.platform.GLX;
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
  public static final GlimmerItemRenderer instance = new GlimmerItemRenderer();

  private final GlimmerModel model = new GlimmerModel();

  public GlimmerItemRenderer() {
  }

  @Override
  public void renderByItem(ItemStack itemStackIn) {
    Minecraft mc = Minecraft.getInstance();
    TextureManager tm = mc.getTextureManager();
    tm.bindTexture(new ResourceLocation("glimmering:textures/entity/glimmer_gold.png"));
    GlStateManager.pushMatrix();
/*    GlStateManager.translatef(2.15f, -1.5f, -1f);
    GlStateManager.scalef(0.0045F, -0.0045f, -0.0045F);*/
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0f, 240.f);
    model.render(null, 0, 0, 0, 0, 0, 1);
    GlStateManager.popMatrix();
  }
}
