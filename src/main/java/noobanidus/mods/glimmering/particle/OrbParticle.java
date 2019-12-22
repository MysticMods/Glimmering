package noobanidus.mods.glimmering.particle;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.Glimmering;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class OrbParticle extends Particle {
  public static final ResourceLocation particles = new ResourceLocation(Glimmering.MODID, "textures/misc/beam1.png");

  protected float particleScale = 1f;
  public final int particle = 16;

  public OrbParticle(World world, double x, double y, double z, float size, float red, float green, float blue, int m) {
    super(world, x, y, z, 0.0D, 0.0D, 0.0D);
    particleRed = red;
    particleGreen = green;
    particleBlue = blue;
    particleGravity = 0;
    motionX = motionY = motionZ = 0;
    particleScale *= size;
    maxAge = 3 * m;
    setSize(0.01F, 0.01F);
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    canCollide = false;
  }

  @Override
  public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    float var8 = 0; // particle % 8 / 8.0F;
    float var9 = 1; // var8 + 0.0624375F * 2;
    float var10 = 0; // particle / 8F / 8.0F;
    float var11 = 1; // var10 + 0.0624375F * 2;
    float scale = 0.1F * particleScale;
    float posX = (float) (prevPosX + (this.posX - prevPosX) * partialTicks - interpPosX);
    float posY = (float) (prevPosY + (this.posY - prevPosY) * partialTicks - interpPosY);
    float posZ = (float) (prevPosZ + (this.posZ - prevPosZ) * partialTicks - interpPosZ);
    float colourScale = 1f;

    buffer.pos(posX - rotationX * scale - rotationXY * scale, posY - rotationZ * scale, posZ - rotationYZ * scale - rotationXZ * scale).tex(var9, var11).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 0.3f).endVertex();
    buffer.pos(posX - rotationX * scale + rotationXY * scale, posY + rotationZ * scale, posZ - rotationYZ * scale + rotationXZ * scale).tex(var9, var10).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 0.3f).endVertex();
    buffer.pos(posX + rotationX * scale + rotationXY * scale, posY + rotationZ * scale, posZ + rotationYZ * scale + rotationXZ * scale).tex(var8, var10).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 0.3f).endVertex();
    buffer.pos(posX + rotationX * scale - rotationXY * scale, posY - rotationZ * scale, posZ + rotationYZ * scale - rotationXZ * scale).tex(var8, var11).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 0.3f).endVertex();
  }

  @Override
  public void tick() {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;

    if (age++ >= maxAge)
      setExpired();
  }

  @Nonnull
  @Override
  public IParticleRenderType getRenderType() {
    return NORMAL_RENDER;
  }

  private static void beginRenderCommon(BufferBuilder buffer, TextureManager textureManager) {
    GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
    GlStateManager.depthMask(false);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    GlStateManager.disableLighting();
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1F);
    textureManager.bindTexture(particles);
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0f, 240.f);
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
  }

  private static void endRenderCommon() {
    GlStateManager.disableBlend();
    GlStateManager.depthMask(true);
    GL11.glPopAttrib();
  }

  private static final IParticleRenderType NORMAL_RENDER = new IParticleRenderType() {
    @Override
    public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
      beginRenderCommon(bufferBuilder, textureManager);
    }

    @Override
    public void finishRender(Tessellator tessellator) {
      tessellator.draw();
      endRenderCommon();
    }

    @Override
    public String toString() {
      return "glimmering:beam";
    }
  };
}
