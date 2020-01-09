package noobanidus.mods.glimmering.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.glimmering.client.model.GlimmerStarModel;
import noobanidus.mods.glimmering.client.model.ModelHolder;
import noobanidus.mods.glimmering.client.render.entity.layer.LayerElectric;
import noobanidus.mods.glimmering.energy.EnergyGraph;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.GlimmerStarEntity;

public class GlimmerStarRenderer extends UnlivingRenderer<GlimmerStarEntity, GlimmerStarModel> {
  public GlimmerStarRenderer(EntityRendererManager manager, GlimmerStarModel model) {
    super(manager, model);
    this.layerRenderers.add(new LayerElectric<>(this, model));
  }

  @Override
  protected void preRenderCallback(float rotation) {
    GlStateManager.pushMatrix();
    GlStateManager.scalef(0.65f, 0.65f, 0.65f);
    GlStateManager.translated(0D, Math.sin(rotation / 20D) / 19.5, 0D);
    GlStateManager.translated(0, 0.6, 0);
    GlStateManager.rotatef(-rotation * 0.7f, 0F, 1F, 0F);
  }

  @Override
  protected void postRenderCallback() {
    GlStateManager.popMatrix();
  }

  @Override
  protected ResourceLocation getEntityTexture(GlimmerStarEntity entity) {
    EnergyGraph.NodeType current = entity.getDataManager().get(GlimmerEntity.TYPE);
    switch (current) {
      default:
        // 0 = Relay
        // 1 = Transmit
        // 2 = Receive
      case RELAY:
      case RECEIVE:
        return new ResourceLocation("glimmering:textures/entity/glimmer_blue.png");
      case TRANSMIT:
        return new ResourceLocation("glimmering:textures/entity/glimmer_green.png");
    }
  }

  public static class Factory implements IRenderFactory<GlimmerStarEntity> {
    @Override
    public EntityRenderer<GlimmerStarEntity> createRenderFor(EntityRendererManager manager) {
      return new GlimmerStarRenderer(manager, ModelHolder.glimmerStarModel);
    }
  }
}
