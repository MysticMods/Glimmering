package noobanidus.mods.glimmering.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

/**
 * Spark - Undefined
 * Created using Tabula 7.0.1
 */
public class GlimmerModel extends EntityModel<GlimmerEntity> {

  public RendererModel shape1;
  public RendererModel shape2;
  public RendererModel shape2_1;

  public GlimmerModel() {
    this.textureWidth = 16;
    this.textureHeight = 16;
    this.shape2 = new RendererModel(this, 3, 5);
    this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.shape2.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1, 0.0F);
    this.shape2_1 = new RendererModel(this, 0, 7);
    this.shape2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.shape2_1.addBox(-0.5F, -0.5F, -2.5F, 1, 1, 5, 0.0F);
    this.shape1 = new RendererModel(this, 0, 0);
    this.shape1.setRotationPoint(0.0F, 21.0F, 0.0F);
    this.shape1.addBox(-0.5F, -2.5F, -0.5F, 1, 5, 1, 0.0F);
    this.shape1.addChild(this.shape2);
    this.shape1.addChild(this.shape2_1);
  }

  @Override
  public void render(GlimmerEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.pushMatrix();
    GlStateManager.translatef(0f, -1.5f, 0f);
    GlStateManager.scalef(2f, 2f, 2f);
    this.shape1.render(f5);
    GlStateManager.popMatrix();
  }

  /**
   * This is a helper function from Tabula to set the rotation of model parts
   */
  public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }

}
