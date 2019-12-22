package noobanidus.mods.glimmering.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.client.model.ModelHolder;
import noobanidus.mods.glimmering.client.model.RitualModel;

import java.util.Arrays;
import java.util.List;

public class RitualRenderer extends UnlivingRenderer<RitualEntity, RitualModel> {
  private final List<RendererModel> outers;
  public RitualRenderer(EntityRendererManager manager, RitualModel model) {
    super(manager, model);
    outers = Arrays.asList(this.entityModel.outer1, this.entityModel.outer2, this.entityModel.outer3, this.entityModel.outer4, this.entityModel.outer5, this.entityModel.outer6);
  }

  private void reset () {
    for (RendererModel model : outers) {
      model.isHidden = true;
      model.offsetX = 0;
      model.offsetZ = 0;
    }
  }

  @Override
  protected void preRenderCallback(float rotation) {
    reset();

    if (rotation > 30) {
      this.entityModel.outer1.isHidden = false;
    }
    if (rotation > 60) {
      this.entityModel.outer2.isHidden = false;
    }
    if (rotation > 90) {
      this.entityModel.outer3.isHidden = false;
    }
    if (rotation > 120) {
      this.entityModel.outer4.isHidden = false;
    }
    if (rotation > 150) {
      this.entityModel.outer5.isHidden = false;
    }
    if (rotation > 180) {
      this.entityModel.outer6.isHidden = false;
    }

    GlStateManager.pushMatrix();
    GlStateManager.scalef(0.65f, 0.65f, 0.65f);
    //GlStateManager.translated(0D, Math.sin(rotation / 20D) / 19.5, 0D);
    GlStateManager.translatef(0, 0.8f, 0);
    GlStateManager.rotatef(-rotation * (rotation * 1.8f * 0.04f), 0F, 1F, 0F);
  }

  @Override
  protected void postRenderCallback() {
    GlStateManager.popMatrix();
  }

  @Override
  protected ResourceLocation getEntityTexture(RitualEntity entity) {
    return new ResourceLocation("glimmering:textures/entity/glimmer_white.png");
  }

  public static class Factory implements IRenderFactory<RitualEntity> {
    @Override
    public EntityRenderer<RitualEntity> createRenderFor(EntityRendererManager manager) {
      return new RitualRenderer(manager, ModelHolder.ritualModel);
    }
  }
}
