package noobanidus.mods.glimmering.client.render.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.client.model.ModelHolder;
import noobanidus.mods.glimmering.client.model.RitualModel;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.particle.GlintParticle;
import noobanidus.mods.glimmering.tiles.RitualRuneTile;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RitualRenderer extends UnlivingRenderer<RitualEntity, RitualModel> {
  private static ResourceLocation DARK_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/misc/beam_gold.png");
  private static ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(Glimmering.MODID, "textures/entity/gold_electric.png");
  private final List<RendererModel> outers;

  public RitualRenderer(EntityRendererManager manager, RitualModel model) {
    super(manager, model);
    outers = Arrays.asList(this.entityModel.outer1, this.entityModel.outer2, this.entityModel.outer3, this.entityModel.outer4, this.entityModel.outer5, this.entityModel.outer6);
  }

  private void reset() {
    for (RendererModel model : outers) {
      model.isHidden = true;
      model.offsetX = 0;
      model.offsetZ = 0;
    }
  }

  @Override
  protected void preRenderCallback(float rotation) {
    reset();

    if (rotation > 60) {
      this.entityModel.outer1.isHidden = false;
    }
    if (rotation > 90) {
      this.entityModel.outer2.isHidden = false;
    }
    if (rotation > 120) {
      this.entityModel.outer3.isHidden = false;
    }
    if (rotation > 140) {
      this.entityModel.outer4.isHidden = false;
    }
    if (rotation > 150) {
      this.entityModel.outer5.isHidden = false;
    }
    if (rotation > 155) {
      this.entityModel.outer6.isHidden = false;
    }

    GlStateManager.pushMatrix();
    GlStateManager.scalef(0.65f, 0.65f, 0.65f);
    //GlStateManager.translated(0D, Math.sin(rotation / 20D) / 19.5, 0D);
    GlStateManager.translatef(0, -2.5f, 0);
    GlStateManager.rotatef(-(rotation * (rotation * 1.15f * 0.04f)), 0F, 1F, 0F);
  }

  @Override
  protected void postRenderCallback() {
    GlStateManager.popMatrix();
  }

  public static List<Vec3d> topFace(BlockPos pos) {
    Vec3d ne = new Vec3d(pos.getX() + 1.01, pos.getY() + 1.01, pos.getZ());
    Vec3d nw = new Vec3d(pos.getX(), pos.getY() + 1.01, pos.getZ());
    Vec3d se = new Vec3d(pos.getX() + 1.01, pos.getY() + 1.01, pos.getZ() + 1.02);
    Vec3d sw = new Vec3d(pos.getX(), pos.getY() + 1.01, pos.getZ() + 1.01);
    return Arrays.asList(ne, nw, sw, se);
  }

  public static List<Vec3d> northFace(BlockPos pos) {
    Vec3d tne = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    Vec3d tnw = new Vec3d(pos.getX() - 1, pos.getY(), pos.getZ());
    Vec3d bne = new Vec3d(pos.getX(), pos.getY() - 1, pos.getZ());
    Vec3d bnw = new Vec3d(pos.getX() - 1, pos.getY() - 1, pos.getZ());
    return Arrays.asList(tne, tnw, bnw, bne);
  }

  public static List<Vec3d> eastFace(BlockPos pos) {
    Vec3d tne = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    Vec3d tse = new Vec3d(pos.getX(), pos.getY(), pos.getZ() - 1);
    Vec3d bne = new Vec3d(pos.getX(), pos.getY() - 1, pos.getZ());
    Vec3d bse = new Vec3d(pos.getX(), pos.getY() - 1, pos.getZ() - 1);
    return Arrays.asList(tne, tse, bse, bne);
  }

  public static List<Vec3d> westFace(BlockPos pos) {
    Vec3d tnw = new Vec3d(pos.getX() - 1, pos.getY(), pos.getZ());
    Vec3d tsw = new Vec3d(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
    Vec3d bnw = new Vec3d(pos.getX() - 1, pos.getY() - 1, pos.getZ());
    Vec3d bsw = new Vec3d(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1);
    return Arrays.asList(tnw, tsw, bsw, bnw);
  }

  public static List<Vec3d> southFace(BlockPos pos) {
    Vec3d tse = new Vec3d(pos.getX(), pos.getY(), pos.getZ() - 1);
    Vec3d tsw = new Vec3d(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
    Vec3d bse = new Vec3d(pos.getX(), pos.getY() - 1, pos.getZ() - 1);
    Vec3d bsw = new Vec3d(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1);
    return Arrays.asList(tse, tsw, bsw, bse);
  }


  @Override
  public void doRender(RitualEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);

    Tessellator tessellator = Tessellator.getInstance();
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
    BufferBuilder buffer = tessellator.getBuffer();
    GlStateManager.pushMatrix();
    GlStateManager.translated(x, y + 2, z);

/*    //GlStateManager.depthMask(true);
    //bindTexture(GOLDEN_TEXTURE);
    //GlStateManager.matrixMode(5890);
    //GlStateManager.loadIdentity();
    float lvt_10_1_ = (float) entity.ticksExisted + partialTicks;

    //GlStateManager.matrixMode(5888);
    //GlStateManager.enableBlend();
    //GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
    GlStateManager.disableLighting();
    //GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    GameRenderer renderer = Minecraft.getInstance().gameRenderer;
    renderer.setupFogColor(true);
    Minecraft mc = Minecraft.getInstance();
    World world = mc.player.world;

    for (RitualRuneTile.PillarType pillar : RitualRuneTile.PillarType.values()) {
      List<BlockPos> pillarBlocks = entity.getPillar(pillar);

      // Top face
      GlStateManager.pushMatrix();
      GlStateManager.translatef(lvt_10_1_ * 0.01F, lvt_10_1_ * 0.01F, 0.0F);
      GlStateManager.pushMatrix();
      GlStateManager.scalef(1.1f, 1.1f, 1.1f);
      //GlStateManager.translatef(0, -0.08f, 0);

      List<Vec3d> topCorners = topFace(pillarBlocks.get(2));
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
      buffer.pos(topCorners.get(0).x, topCorners.get(0).y, topCorners.get(0).z).tex(0, 1).color(0, 0, 0, 255).endVertex();
      buffer.pos(topCorners.get(1).x, topCorners.get(1).y, topCorners.get(1).z).tex(0, 1).color(0, 0, 0, 255).endVertex();
      buffer.pos(topCorners.get(2).x, topCorners.get(2).y, topCorners.get(2).z).tex(0, 1).color(0, 0, 0, 255).endVertex();
      buffer.pos(topCorners.get(3).x, topCorners.get(3).y, topCorners.get(3).z).tex(0, 1).color(0, 0, 0, 255).endVertex();
      tessellator.draw();

      GlintParticle.Data data = new GlintParticle.Data(0.8f, 50, 50, 50, 1, 1, 20, 0f);
      world.addParticle(data, topCorners.get(0).x, topCorners.get(0).y, topCorners.get(0).z, 0, 1, 0);
      world.addParticle(data, topCorners.get(1).x, topCorners.get(1).y, topCorners.get(1).z, 1, 1, 1);
      world.addParticle(data, topCorners.get(2).x, topCorners.get(2).y, topCorners.get(2).z, 2, 1, 2);
      world.addParticle(data, topCorners.get(3).x, topCorners.get(3).y, topCorners.get(3).z, 3, 1, 3);
      GlStateManager.popMatrix();
      GlStateManager.popMatrix();
    }

    renderer.setupFogColor(false);
    GlStateManager.matrixMode(5890);
    GlStateManager.loadIdentity();
    GlStateManager.matrixMode(5888);
    GlStateManager.enableLighting();
    GlStateManager.disableBlend();
    GlStateManager.depthMask(true);*/

    List<Vec3d> bowls = entity.getBowls();
    List<Vec3d> pillars = entity.getPillarTops();

    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    GlStateManager.depthMask(false);
    float age = handleRotationFloat(entity, partialTicks);
    GlStateManager.disableCull();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.texParameter(3553, 10242, 10497);
    GlStateManager.texParameter(3553, 10243, 10497);
    float f2 = 1.0F;
    float f3 = f2 * 0.5F % 1.0F;
    bindTexture(DARK_TEXTURE);

    Vec3d pos = new Vec3d(entity.posX, entity.posY + 1, entity.posZ);

    List<Vec3d> toDraw = new ArrayList<>();

    if (age > 5) {
      toDraw.add(bowls.get(0));
    }
    if (age > 15) {
      toDraw.add(bowls.get(1));
    }
    if (age > 25) {
      toDraw.add(bowls.get(2));
    }
    if (age > 35) {
      toDraw.add(bowls.get(3));
    }
    if (age > 40) {
      toDraw.add(pillars.get(0));
    }
    if (age > 45) {
      toDraw.add(pillars.get(1));
    }
    if (age > 50) {
      toDraw.add(pillars.get(2));
    }
    if (age > 55) {
      toDraw.add(pillars.get(3));
    }

    for (Vec3d bowl : toDraw) {
      GlStateManager.pushMatrix();
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
      Vec3d subtracted = pos.subtract(bowl);
      double d0 = subtracted.length();
      Vec3d normalized = subtracted.normalize();
      float f5 = (float) Math.acos(normalized.y);
      float f6 = (float) Math.atan2(normalized.z, normalized.x);
      GlStateManager.translated(0, 0.5, 0);
      GlStateManager.rotatef((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
      GlStateManager.rotatef(f5 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
      double d1 = (double) f2 * 0.0D;

      double d12 = Math.cos(d1 + Math.PI) * 0.12D;
      double d13 = Math.sin(d1 + Math.PI) * 0.12D;
      double d14 = Math.cos(d1) * 0.12D;
      double d15 = Math.sin(d1) * 0.12D;

      double d16 = Math.cos(d1 + (Math.PI / 2D)) * 0.12D;
      double d17 = Math.sin(d1 + (Math.PI / 2D)) * 0.12D;
      double d18 = Math.cos(d1 + (Math.PI * 1.5D)) * 0.12D;
      double d19 = Math.sin(d1 + (Math.PI * 1.5D)) * 0.12D;

      double d22 = (double) (f3 - 1.0F);
      double d23 = d0 * 5.05D + d22;

      buffer.pos(d12, 0.0D, d13).tex(1, d23).color(255, 255, 255, 255).endVertex();
      buffer.pos(d12, -d0, d13).tex(1, d22).color(255, 255, 255, 255).endVertex();
      buffer.pos(d14, -d0, d15).tex(0.0D, d22).color(255, 255, 255, 255).endVertex();
      buffer.pos(d14, 0.0D, d15).tex(0.0D, d23).color(255, 255, 255, 255).endVertex();

      buffer.pos(d16, 0.0D, d17).tex(1, d23).color(255, 255, 255, 255).endVertex();
      buffer.pos(d16, -d0, d17).tex(1, d22).color(255, 255, 255, 255).endVertex();
      buffer.pos(d18, -d0, d19).tex(0.0D, d22).color(255, 255, 255, 255).endVertex();
      buffer.pos(d18, 0.0D, d19).tex(0.0D, d23).color(255, 255, 255, 255).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
    }

    GlStateManager.enableCull();
    RenderHelper.enableStandardItemLighting();
    GlStateManager.depthMask(true);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.disableBlend();
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
