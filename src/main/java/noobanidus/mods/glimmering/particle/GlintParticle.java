package noobanidus.mods.glimmering.particle;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.init.ModParticles;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public class GlintParticle extends Particle {
  public static final ResourceLocation particles = new ResourceLocation(Glimmering.MODID, "textures/misc/glint.png");

  protected float particleScale = 1f;
  public final int particle = 16;

  public GlintParticle(World world, double x, double y, double z, float size, float red, float green, float blue, int m) {
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
    float var8 = particle % 8 / 8.0F;
    float var9 = var8 + 0.0624375F * 2;
    float var10 = particle / 8F / 8.0F;
    float var11 = var10 + 0.0624375F * 2;
    float scale = 0.1F * particleScale;
    float posX = (float) (prevPosX + (this.posX - prevPosX) * partialTicks - interpPosX);
    float posY = (float) (prevPosY + (this.posY - prevPosY) * partialTicks - interpPosY);
    float posZ = (float) (prevPosZ + (this.posZ - prevPosZ) * partialTicks - interpPosZ);
    float colourScale = 0.8f;

    buffer.pos(posX - rotationX * scale - rotationXY * scale, posY - rotationZ * scale, posZ - rotationYZ * scale - rotationXZ * scale).tex(var9, var11).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 1).endVertex();
    buffer.pos(posX - rotationX * scale + rotationXY * scale, posY + rotationZ * scale, posZ - rotationYZ * scale + rotationXZ * scale).tex(var9, var10).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 1).endVertex();
    buffer.pos(posX + rotationX * scale + rotationXY * scale, posY + rotationZ * scale, posZ + rotationYZ * scale + rotationXZ * scale).tex(var8, var10).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 1).endVertex();
    buffer.pos(posX + rotationX * scale - rotationXY * scale, posY - rotationZ * scale, posZ + rotationYZ * scale - rotationXZ * scale).tex(var8, var11).color(particleRed * colourScale, particleGreen * colourScale, particleBlue * colourScale, 1).endVertex();
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
    GlStateManager.disableDepthTest();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    GlStateManager.disableLighting();
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1F);
    textureManager.bindTexture(particles);
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0f, 240.f);
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
  }

  private static void endRenderCommon() {
    GlStateManager.enableDepthTest();
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

  public static class Data implements IParticleData {
    public final float size;
    public final float r, g, b;
    public final int m;

    public Data(float size, float r, float g, float b, int m) {
      this.size = size;
      this.r = r;
      this.g = g;
      this.b = b;
      this.m = m;
    }

    @Nonnull
    @Override
    public ParticleType<Data> getType() {
      return ModParticles.GLINT.get();
    }

    @Override
    public void write(PacketBuffer buf) {
      buf.writeFloat(size);
      buf.writeFloat(r);
      buf.writeFloat(g);
      buf.writeFloat(b);
      buf.writeInt(m);
    }

    @Nonnull
    @Override
    public String getParameters() {
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d", this.getType().getRegistryName(), this.size, this.r, this.g, this.b, this.m);
    }

    public static final IDeserializer<Data> DESERIALIZER = new IDeserializer<Data>() {
      @Nonnull
      @Override
      public Data deserialize(@Nonnull ParticleType<Data> type, @Nonnull StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float size = reader.readFloat();
        reader.expect(' ');
        float r = reader.readFloat();
        reader.expect(' ');
        float g = reader.readFloat();
        reader.expect(' ');
        float b = reader.readFloat();
        reader.expect(' ');
        int m = reader.readInt();
        reader.expect(' ');

        return new Data(size, r, g, b, m);
      }

      @Override
      public Data read(@Nonnull ParticleType<Data> type, PacketBuffer buf) {
        return new Data(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
      }
    };
  }

  public static class Type extends ParticleType<Data> {
    public Type() {
      super(false, Data.DESERIALIZER);
    }

    public static class Factory implements IParticleFactory<Data> {

      @Nullable
      @Override
      public Particle makeParticle(Data data, World world, double x, double y, double z, double mx, double my, double mz) {
        return new GlintParticle(world, x, y, z, data.size, data.r, data.g, data.b, data.m);
      }
    }
  }
}
