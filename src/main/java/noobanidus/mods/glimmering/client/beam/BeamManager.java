package noobanidus.mods.glimmering.client.beam;

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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class BeamManager {
  private static Map<ResourceLocation, List<Beam>> BEAM_MAP = new HashMap<>();

  public static void addBeam(Beam beam) {
    List<Beam> beams = BEAM_MAP.computeIfAbsent(beam.getTexture(), o -> new ArrayList<>());
    beams.add(beam);
  }

  public static void tick(TickEvent.ClientTickEvent event) {
    if (event.side == LogicalSide.CLIENT) {
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
  }

  @OnlyIn(Dist.CLIENT)
  public static void render(RenderWorldLastEvent event) { // WorldRenderer context, float partialTicks) {
    if (BEAM_MAP.isEmpty()) {
      return;
    }

    Minecraft mc = Minecraft.getInstance();
    PlayerEntity e = mc.player;
    TextureManager tm = mc.getTextureManager();
    Tessellator tessellator = Tessellator.getInstance();
    GlStateManager.pushMatrix();
    float partialTicks = event.getPartialTicks();
    double iPX = e.prevPosX + (e.posX - e.prevPosX) * partialTicks;
    double iPY = e.prevPosY + (e.posY - e.prevPosY) * partialTicks;
    double iPZ = e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks;
    GlStateManager.translated(-iPX, -iPY, -iPZ);
    GlStateManager.disableCull();
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
    BufferBuilder buffer = tessellator.getBuffer();
    buffer.setTranslation(-iPX, -iPY, -iPZ);

    for (Map.Entry<ResourceLocation, List<Beam>> entry : BEAM_MAP.entrySet()) {
      tm.bindTexture(entry.getKey());
      entry.getValue().forEach(beam -> {
        // The client shouldn't bne getting stuff for invalid dimensions
        if (beam.removed()) {
          return;
        }
        GlStateManager.pushMatrix();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        beam.render(buffer);
        tessellator.draw();
        GlStateManager.popMatrix();
      });
    }

    buffer.setTranslation(0, 0, 0);

    GlStateManager.enableCull();
    GlStateManager.popMatrix();
  }
}
