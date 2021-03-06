package noobanidus.mods.glimmering.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import noobanidus.mods.glimmering.entity.RitualEntity;

public class RitualModel extends EntityModel<RitualEntity> {
  private final RendererModel central;
  private final RendererModel outer;

  public final RendererModel outer1;
  public final RendererModel outer2;
  public final RendererModel outer3;
  public final RendererModel outer4;
  public final RendererModel outer5;
  public final RendererModel outer6;

  public RitualModel() {
    textureWidth = 64;
    textureHeight = 64;

    central = new RendererModel(this);
    central.setRotationPoint(0.0F, 24.0F, 0.0F);
    central.cubeList.add(new ModelBox(central, 8, 8, -1.5F, -19.0F, -1.7143F, 3, 16, 3, 0.0F, false));

    outer = new RendererModel(this);
    outer.setRotationPoint(1.5F, 0.0F, -1.7143F);
    central.addChild(outer);

    outer1 = new RendererModel(this);
    outer1.cubeList.add(new ModelBox(outer1, 42, 23, 12.0F, -17.0F, 1.0F, 3, 13, 3, 0.0F, false));
    outer.addChild(outer1);

    outer2 = new RendererModel(this);
    outer2.cubeList.add(new ModelBox(outer2, 16, 32, -5.0F, -16.0F, -16.0F, 3, 11, 3, 0.0F, false));
    outer.addChild(outer2);

    outer3 = new RendererModel(this);
    outer3.cubeList.add(new ModelBox(outer3, 29, 41, 7.0F, -15.0F, -11.0F, 2, 9, 2, 0.0F, false));
    outer.addChild(outer3);

    outer4 = new RendererModel(this);
    outer4.cubeList.add(new ModelBox(outer4, 24, 18, -18.0F, -17.0F, -3.0F, 3, 13, 3, 0.0F, false));
    outer.addChild(outer4);

    outer5 = new RendererModel(this);
    outer5.cubeList.add(new ModelBox(outer5, 31, 3, -1.0F, -16.0F, 14.0F, 3, 11, 4, 0.0F, false));
    outer.addChild(outer5);

    outer6 = new RendererModel(this);
    outer6.cubeList.add(new ModelBox(outer6, 1, 45, -13.0F, -14.0F, 10.0F, 2, 7, 2, 0.0F, false));
    outer.addChild(outer6);

/*    outer1: 0.0F, -17.0F, 1.0F
    outer2: -4.0F, -16.0F, -3.0F
    outer3: -1.0F, -15.0F, -1.0F
    outer4: -6.0F, -17.0F, 0.0F
    outer5: -2.0F, -16.0F, 2.0F
    outer6: -4.0F, -14.0F, 3.0F*/
  }

  @Override
  public void render(RitualEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    central.render(f5);
  }

  public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
}