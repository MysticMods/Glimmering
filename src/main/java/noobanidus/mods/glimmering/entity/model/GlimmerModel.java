package noobanidus.mods.glimmering.entity.model;

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

  public GlimmerModel() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.shape1 = new RendererModel(this, 0, 0);
    this.shape1.setRotationPoint(-2.1F, 0.5F, 0.5F);
    this.shape1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.setRotateAngle(shape1, 0.0F, 0.7853981633974483F, 0.0F);
    this.shape2 = new RendererModel(this, 4, 0);
    this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.shape2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.setRotateAngle(shape2, 0.7853981633974483F, 0.9424777960769379F, 1.5707963267948966F);
    this.shape1.addChild(this.shape2);
  }

  @Override
  public void render(GlimmerEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.shape1.render(f5);
  }

  public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
}
