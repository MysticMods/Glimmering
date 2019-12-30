package noobanidus.mods.glimmering.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;

public class StarModel extends EntityModel<GlimmerStarEntity> {
  private final RendererModel central;
  private final RendererModel outer;

  public StarModel() {
    textureWidth = 64;
    textureHeight = 64;

    central = new RendererModel(this);
    central.setRotationPoint(0.0F, 24.0F, 0.0F);
    central.cubeList.add(new ModelBox(central, 8, 8, -1.5F, -19.0F, -1.7143F, 3, 9, 3, 0.0F, false));

    outer = new RendererModel(this);
    outer.setRotationPoint(1.5F, 0.0F, -1.7143F);
    central.addChild(outer);
    outer.cubeList.add(new ModelBox(outer, 42, 23, 0.0F, -17.0F, 1.0F, 3, 6, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 16, 32, -4.0F, -16.0F, -3.0F, 3, 5, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 29, 41, -1.0F, -15.0F, -1.0F, 2, 3, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 24, 18, -6.0F, -17.0F, 0.0F, 3, 6, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 31, 3, -2.0F, -16.0F, 2.0F, 3, 4, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -4.0F, -15.0F, 3.0F, 2, 2, 2, 0.0F, false));
  }

  @Override
  public void render(GlimmerStarEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    central.render(f5);
  }

  public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
}