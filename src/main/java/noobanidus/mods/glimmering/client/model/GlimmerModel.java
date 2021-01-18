package noobanidus.mods.glimmering.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

/**
 * Spark - Undefined
 * Created using Tabula 7.0.1
 */
public class GlimmerModel extends EntityModel<GlimmerEntity> {

  private final ModelRenderer central;
  private final ModelRenderer outer;

  public GlimmerModel() {
    this.textureWidth = 64;
    this.textureHeight = 64;

    central = new ModelRenderer(this);
    central.setRotationPoint(0.0F, 24.0F, 0.0F);
    central.cubeList.add(new ModelRenderer.ModelBox(central, 8, 8, -1.5F, -19.0F, -1.7143F, 3, 16, 3, 0.0F, false));

    outer = new ModelRenderer(this);
    outer.setRotationPoint(1.5F, 0.0F, -1.7143F);
    central.addChild(outer);
    outer.cubeList.add(new ModelBox(outer, 42, 23, 0.0F, -17.0F, 1.0F, 3, 13, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 16, 32, -4.0F, -16.0F, -3.0F, 3, 11, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 29, 41, -1.0F, -15.0F, -1.0F, 2, 9, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 24, 18, -6.0F, -17.0F, 0.0F, 3, 13, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 31, 3, -2.0F, -16.0F, 2.0F, 3, 11, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -4.0F, -14.0F, 3.0F, 2, 7, 2, 0.0F, false));
  }


  @Override
  public void render(GlimmerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    this.central.render(scaleFactor);
  }

  /**
   * This is a helper function from Tabula to set the rotation of model parts
   */
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }

}
