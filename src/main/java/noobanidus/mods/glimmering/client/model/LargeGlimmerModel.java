package noobanidus.mods.glimmering.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.LargeGlimmerEntity;

public class LargeGlimmerModel extends EntityModel<LargeGlimmerEntity> {
  private final RendererModel central;
  private final RendererModel outer;

  public LargeGlimmerModel() {
    textureWidth = 64;
    textureHeight = 64;

    central = new RendererModel(this);
    central.setRotationPoint(0.0F, 24.0F, 0.0F);
    central.cubeList.add(new ModelBox(central, 6, 6, -1.5F, -20.0F, -2.7143F, 3, 17, 4, 0.0F, false));

    outer = new RendererModel(this);
    outer.setRotationPoint(1.5F, 0.0F, -1.7143F);
    central.addChild(outer);
    outer.cubeList.add(new ModelBox(outer, 23, 9, 0.0F, -16.0F, 1.0F, 3, 10, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 22, 31, -4.0F, -17.0F, -4.0F, 3, 12, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 24, 41, -4.0F, -5.0F, -2.0F, 3, 1, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 35, 39, -1.0F, -15.0F, -2.0F, 2, 10, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 3, 22, -6.0F, -18.0F, 0.0F, 3, 15, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 31, 5, -2.0F, -17.0F, 2.0F, 3, 13, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 41, 35, -6.0F, -16.0F, -2.0F, 2, 11, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 42, -7.0F, -15.0F, -1.0F, 1, 8, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -4.0F, -14.0F, 3.0F, 2, 7, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -1.0F, -14.0F, -3.0F, 1, 8, 1, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 42, 15, 1.0F, -13.0F, -1.0F, 1, 6, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -5.0F, -15.0F, -3.0F, 1, 8, 1, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 41, 42, -5.0F, -16.0F, 2.0F, 3, 11, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 31, 6, -3.0F, -21.0F, -1.0F, 3, 1, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 35, 17, -3.0F, -3.0F, 0.0F, 3, 1, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 8, 8, -3.0F, -22.0F, -1.0F, 2, 1, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 37, 8, -3.0F, -2.0F, 1.0F, 2, 1, 2, 0.0F, false));
  }

  @Override
  public void render(LargeGlimmerEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    central.render(f5);
  }

  public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
}