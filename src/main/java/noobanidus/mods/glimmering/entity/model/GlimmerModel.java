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
  public RendererModel shape3;

  public GlimmerModel() {
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.shape1 = new RendererModel(this, 0, 0);
    this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
    this.shape1.addBox(-1.5F, -7.5F, -1.5F, 3, 16, 3, 0.0F);
    this.shape3 = new RendererModel(this, 0, 6);
    this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.shape3.addBox(-1.5F, -1.5F, -8.0F, 3, 3, 16, 0.0F);
    this.shape2 = new RendererModel(this, 12, 0);
    this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.shape2.addBox(-8.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
    this.shape1.addChild(this.shape3);
    this.shape1.addChild(this.shape2);
  }

  @Override
  public void render(GlimmerEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.pushMatrix();
    GlStateManager.translatef(0f, 0.5f, 0f);
    GlStateManager.scalef(0.7f, 0.7f, 0.7f);
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
