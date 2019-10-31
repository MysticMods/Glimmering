package noobanidus.mods.glimmering.entity.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.model.GlimmerModel;
import noobanidus.mods.glimmering.entity.model.ModelHolder;

public class GlimmerRenderer extends EntityRenderer<GlimmerEntity> implements IEntityRenderer<GlimmerEntity, GlimmerModel> {
  private GlimmerModel model;

  public GlimmerRenderer(EntityRendererManager manager, GlimmerModel model) {
    super(manager);
    this.model = model;
  }

  @Override
  protected ResourceLocation getEntityTexture(GlimmerEntity entity) {
    return new ResourceLocation("glimmering:textures/entity/glimmer.png");
  }

  @Override
  public GlimmerModel getEntityModel() {
    return model;
  }

  public static class Factory implements IRenderFactory<GlimmerEntity> {
    @Override
    public EntityRenderer<GlimmerEntity> createRenderFor(EntityRendererManager manager) {
      return new GlimmerRenderer(manager, ModelHolder.glimmerModel);
    }
  }
}
