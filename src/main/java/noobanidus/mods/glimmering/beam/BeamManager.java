package noobanidus.mods.glimmering.beam;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class BeamManager {
  private static Map<ResourceLocation, List<Beam>> BEAM_MAP = new HashMap<>();

  public void addBeam(Beam beam) {
    List<Beam> beams = BEAM_MAP.computeIfAbsent(beam.getTexture(), o -> new ArrayList<>());
    beams.add(beam);
  }

  public void tick() {
    for (Map.Entry<ResourceLocation, List<Beam>> entry : BEAM_MAP.entrySet()) {
      Iterator<Beam> iterator = entry.getValue().iterator();
      while (iterator.hasNext()) {
        Beam value = iterator.next();
        value.tick();
        if (value.removed()) {
          iterator.remove();
        }
      }
    }
  }

  @OnlyIn(Dist.CLIENT)
  public void render() { // WorldRenderer context, float partialTicks) {
    if (BEAM_MAP.isEmpty()) {
      return;
    }

    Minecraft mc = Minecraft.getInstance();
    PlayerEntity player = mc.player;
    int dimId = player.getEntityWorld().getDimension().getType().getId();
    TextureManager tm = mc.getTextureManager();
    Tessellator tessellator = Tessellator.getInstance();
    GlStateManager.texParameter(3553, 10242, 10497);
    GlStateManager.texParameter(3553, 10243, 10497);
    GlStateManager.disableCull();
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
    for (Map.Entry<ResourceLocation, List<Beam>> entry : BEAM_MAP.entrySet()) {
      tm.bindTexture(entry.getKey());
      BufferBuilder buffer = tessellator.getBuffer();
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
      entry.getValue().forEach(beam -> {
        // The client shouldn't bne getting stuff for invalid dimensions
        if (beam.removed()) {
          return;
        }

        beam.render(buffer);
      });
      tessellator.draw();
    }
    GlStateManager.enableCull();
  }


}
