package noobanidus.mods.glimmering.entity.render;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.model.GlimmerModel;
import noobanidus.mods.glimmering.entity.model.ModelHolder;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class GlimmerRenderer extends EntityRenderer<GlimmerEntity> implements IEntityRenderer<GlimmerEntity, GlimmerModel> {
  private GlimmerModel entityModel;

  private final List<LayerRenderer<GlimmerEntity, GlimmerModel>> layerRenderers = new ArrayList<>();
  private final FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
  private static final DynamicTexture TEXTURE_BRIGHTNESS = Util.make(new DynamicTexture(16, 16, false), (p_203414_0_) -> {
    p_203414_0_.getTextureData().untrack();

    for (int i = 0; i < 16; ++i) {
      for (int j = 0; j < 16; ++j) {
        p_203414_0_.getTextureData().setPixelRGBA(j, i, -1);
      }
    }

    p_203414_0_.updateDynamicTexture();
  });

  public GlimmerRenderer(EntityRendererManager manager, GlimmerModel model) {
    super(manager);
    this.entityModel = model;
  }

  private void renderLivingAt(GlimmerEntity entityLivingBaseIn, double x, double y, double z) {
    GlStateManager.translatef((float) x, (float) y, (float) z);
  }

  private float handleRotationFloat(GlimmerEntity livingBase, float partialTicks) {
    return (float) livingBase.ticksExisted + partialTicks;
  }

  private float prepareScale(GlimmerEntity entitylivingbaseIn, float partialTicks) {
    GlStateManager.enableRescaleNormal();
    GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
    GlStateManager.translatef(0.0F, -1.501F, 0.0F);
    return 0.0625F;
  }

  private boolean isVisible(GlimmerEntity p_193115_1_) {
    return !p_193115_1_.isInvisible() || this.renderOutlines;
  }

  private void renderModel(GlimmerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    boolean flag = this.isVisible(entitylivingbaseIn);
    boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getInstance().player);
    if (flag || flag1) {
      if (!this.bindEntityTexture(entitylivingbaseIn)) {
        return;
      }

      if (flag1) {
        GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
      }
      GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0f, 240.f);
      GlStateManager.pushMatrix();
      GlStateManager.scalef(0.65f, 0.65f, 0.65f);
      GlStateManager.translatef(0, 0.85f, 0);
      this.entityModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GlStateManager.popMatrix();
      if (flag1) {
        GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
      }
    }
  }

  private boolean setBrightness(GlimmerEntity entitylivingbaseIn, float partialTicks, boolean combineTextures) {
    boolean flag1 = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;
    int i = 0;
    if (!flag1) {
      return false;
    } else if (!combineTextures) {
      return false;
    } else {
      standardTexEnv();
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 7681);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, GLX.GL_TEXTURE0);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
      GlStateManager.activeTexture(GLX.GL_TEXTURE1);
      GlStateManager.enableTexture();
      GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, GLX.GL_INTERPOLATE);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, GLX.GL_CONSTANT);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PREVIOUS);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE2_RGB, GLX.GL_CONSTANT);
      secondaryTexEnv(GLX.GL_OPERAND2_RGB, 770, GLX.GL_COMBINE_ALPHA, 7681, GLX.GL_SOURCE0_ALPHA, GLX.GL_PREVIOUS);
      GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
      this.brightnessBuffer.position(0);
      this.brightnessBuffer.put(1.0F);
      this.brightnessBuffer.put(0.0F);
      this.brightnessBuffer.put(0.0F);
      this.brightnessBuffer.put(0.3F);
      this.brightnessBuffer.flip();
      GlStateManager.texEnv(8960, 8705, this.brightnessBuffer);
      GlStateManager.activeTexture(GLX.GL_TEXTURE2);
      GlStateManager.enableTexture();
      GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
      GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
      GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, GLX.GL_PREVIOUS);
      GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_TEXTURE1);
      secondaryTexEnv(GLX.GL_COMBINE_ALPHA, 7681, GLX.GL_SOURCE0_ALPHA, GLX.GL_PREVIOUS, GLX.GL_OPERAND0_ALPHA, 770);
      GlStateManager.activeTexture(GLX.GL_TEXTURE0);
      return true;
    }
  }

  private void secondaryTexEnv(int glCombineAlpha, int i2, int glSource0Alpha, int glPrevious, int glOperand0Alpha, int i3) {
    GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
    GlStateManager.texEnv(8960, glCombineAlpha, i2);
    GlStateManager.texEnv(8960, glSource0Alpha, glPrevious);
    GlStateManager.texEnv(8960, glOperand0Alpha, i3);
  }

  private void standardTexEnv() {
    GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    GlStateManager.enableTexture();
    GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
    GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, GLX.GL_TEXTURE0);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PRIMARY_COLOR);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
  }

  private void unsetBrightness() {
    standardTexEnv();
    GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 8448);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, GLX.GL_TEXTURE0);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE1_ALPHA, GLX.GL_PRIMARY_COLOR);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND1_ALPHA, 770);
    GlStateManager.activeTexture(GLX.GL_TEXTURE1);
    tertiaryTexEnv();
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.activeTexture(GLX.GL_TEXTURE2);
    GlStateManager.disableTexture();
    GlStateManager.bindTexture(0);
    tertiaryTexEnv();
    GlStateManager.activeTexture(GLX.GL_TEXTURE0);
  }

  private void tertiaryTexEnv() {
    GlStateManager.texEnv(8960, 8704, GLX.GL_COMBINE);
    GlStateManager.texEnv(8960, GLX.GL_COMBINE_RGB, 8448);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND0_RGB, 768);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND1_RGB, 768);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE0_RGB, 5890);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE1_RGB, GLX.GL_PREVIOUS);
    GlStateManager.texEnv(8960, GLX.GL_COMBINE_ALPHA, 8448);
    GlStateManager.texEnv(8960, GLX.GL_OPERAND0_ALPHA, 770);
    GlStateManager.texEnv(8960, GLX.GL_SOURCE0_ALPHA, 5890);
  }

  private void renderLayers(GlimmerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
    for (LayerRenderer<GlimmerEntity, GlimmerModel> layerrenderer : this.layerRenderers) {
      boolean flag = this.setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
      layerrenderer.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
      if (flag) {
        this.unsetBrightness();
      }
    }
  }

  private boolean setDoRenderBrightness(GlimmerEntity entityLivingBaseIn, float partialTicks) {
    return this.setBrightness(entityLivingBaseIn, partialTicks, true);
  }

  @Override
  public void doRender(GlimmerEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    GlStateManager.pushMatrix();
    GlStateManager.disableCull();

    try {
      float f = MathHelper.func_219805_h(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset);
      float f1 = MathHelper.func_219805_h(partialTicks, entity.prevRotationYawHead, entity.rotationYawHead);
      float f2 = f1 - f;

      float f7 = MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch);
      this.renderLivingAt(entity, x, y, z);
      float f8 = this.handleRotationFloat(entity, partialTicks);
      float f4 = this.prepareScale(entity, partialTicks);
      float f5 = 0.0F;
      float f6 = 0.0F;
      if (entity.isAlive()) {
        f5 = MathHelper.lerp(partialTicks, entity.prevLimbSwingAmount, entity.limbSwingAmount);
        f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

        if (f5 > 1.0F) {
          f5 = 1.0F;
        }
      }

      GlStateManager.enableAlphaTest();
      this.entityModel.setLivingAnimations(entity, f6, f5, partialTicks);
      this.entityModel.setRotationAngles(entity, f6, f5, f8, f2, f7, f4);
      if (this.renderOutlines) {
        GlStateManager.enableColorMaterial();
        GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
        this.renderModel(entity, f6, f5, f8, f2, f7, f4);
        this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);

        GlStateManager.tearDownSolidRenderingTextureCombine();
        GlStateManager.disableColorMaterial();

      } else {
        boolean flag1 = this.setDoRenderBrightness(entity, partialTicks);
        // Rendering is here!

        this.renderModel(entity, f6, f5, f8, f2, f7, f4);
        if (flag1) {
          this.unsetBrightness();
        }

        GlStateManager.depthMask(true);
        if (!entity.isSpectator()) {
          this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
        }
      }

      GlStateManager.disableRescaleNormal();
    } catch (Exception exception) {
      Glimmering.LOG.error("Couldn't render entity", exception);
    }

    GlStateManager.activeTexture(GLX.GL_TEXTURE1);
    GlStateManager.enableTexture();
    GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    GlStateManager.enableCull();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }

  @Override
  protected ResourceLocation getEntityTexture(GlimmerEntity entity) {
    int current = entity.getDataManager().get(GlimmerEntity.TYPE);
    switch (current) {
      default:
        // 0 = Relay
        // 1 = Transmit
        // 2 = Receive
      case 0:
        return new ResourceLocation("glimmering:textures/entity/glimmer_gold.png");
      case 1:
        return new ResourceLocation("glimmering:textures/entity/glimmer_green.png");
      case 2:
        return new ResourceLocation("glimmering:textures/entity/glimmer_blue.png");
    }
  }

  @Override
  public GlimmerModel getEntityModel() {
    return entityModel;
  }

  public static class Factory implements IRenderFactory<GlimmerEntity> {
    @Override
    public EntityRenderer<GlimmerEntity> createRenderFor(EntityRendererManager manager) {
      return new GlimmerRenderer(manager, ModelHolder.glimmerModel);
    }
  }
}
