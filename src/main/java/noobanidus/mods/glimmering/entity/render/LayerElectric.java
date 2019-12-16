package noobanidus.mods.glimmering.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.model.GlimmerModel;

@OnlyIn(Dist.CLIENT)
public class LayerElectric extends LayerRenderer<GlimmerEntity, GlimmerModel> {
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/entity/electric.png");
  private final GlimmerModel glimmerModel = new GlimmerModel();

  public LayerElectric(IEntityRenderer<GlimmerEntity, GlimmerModel> p_i50947_1_) {
    super(p_i50947_1_);
  }

  public void render(GlimmerEntity glimmer, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
    if (true) { // glimmer.recentlyPowered()) {
      GlStateManager.depthMask(true);
      this.bindTexture(LIGHTNING_TEXTURE);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      float lvt_10_1_ = (float) glimmer.ticksExisted + p_212842_4_;
      GlStateManager.translatef(lvt_10_1_ * 0.01F, lvt_10_1_ * 0.01F, 0.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.enableBlend();
      GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
      GlStateManager.disableLighting();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      this.getEntityModel().setModelAttributes(this.glimmerModel);
      GameRenderer renderer = Minecraft.getInstance().gameRenderer;
      renderer.setupFogColor(true);
      GlStateManager.pushMatrix();
      GlStateManager.scalef(1.1f, 1.1f, 1.1f);
      GlStateManager.translatef(0, -0.08f, 0);
      this.glimmerModel.render(glimmer, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      GlStateManager.popMatrix();
      renderer.setupFogColor(false);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.enableLighting();
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
    }
  }

  public boolean shouldCombineTextures() {
    return false;
  }
}
