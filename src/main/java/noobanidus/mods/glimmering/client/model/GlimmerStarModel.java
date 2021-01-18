package noobanidus.mods.glimmering.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;

public class GlimmerStarModel extends EntityModel<GlimmerStarEntity> {
  private final ModelRenderer central;
  private final ModelRenderer outer;

  public GlimmerStarModel() {
    textureWidth = 64;
    textureHeight = 64;

    central = new ModelRenderer(this);
    central.setRotationPoint(0.0F, 24.0F, 0.0F);
    central.cubeList.add(new ModelRenderer.ModelBox(central, 8, 8, -1.5F, -11.0F, -1.7143F, 3, 7, 3, 0.0F, false));

    outer = new ModelRenderer(this);
    outer.setRotationPoint(1.5F, 0.0F, -1.7143F);
    central.addChild(outer);
    outer.cubeList.add(new ModelBox(outer, 42, 23, 0.0F, -9.0F, 1.0F, 2, 4, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 16, 32, -4.0F, -8.0F, -2.0F, 3, 3, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 29, 41, -1.0F, -7.0F, -1.0F, 2, 1, 2, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 24, 18, -5.0F, -9.0F, 0.0F, 2, 4, 3, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 31, 3, -2.0F, -8.0F, 2.0F, 3, 3, 4, 0.0F, false));
    outer.cubeList.add(new ModelBox(outer, 1, 45, -4.0F, -7.0F, 3.0F, 2, 1, 2, 0.0F, false));
  }

  @Override
  public void render(GlimmerStarEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    central.render(f5);
  }
}