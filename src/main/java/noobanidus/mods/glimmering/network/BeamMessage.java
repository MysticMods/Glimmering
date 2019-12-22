package noobanidus.mods.glimmering.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import noobanidus.mods.glimmering.graph.EnergyGraph;
import noobanidus.mods.glimmering.particle.BeamParticleData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeamMessage {
  private List<EnergyGraph.Edge> edges;

  public BeamMessage(PacketBuffer buffer) {
    int count = buffer.readInt();
    this.edges = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      edges.add(new EnergyGraph.Edge(buffer.readInt(), buffer.readInt()));
    }
  }

  public BeamMessage(List<EnergyGraph.Edge> edges) {
    this.edges = edges;
  }

  public List<EnergyGraph.Edge> getEdges() {
    return edges;
  }

  public void encode(PacketBuffer buf) {
    buf.writeInt(edges.size());
    for (EnergyGraph.Edge i : edges) {
      buf.writeInt(i.getEntity1());
      buf.writeInt(i.getEntity2());
    }
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  @OnlyIn(Dist.CLIENT)
  public void handle(BeamMessage message, Supplier<NetworkEvent.Context> context) {
    PlayerEntity target = Minecraft.getInstance().player;
    World world = target.world;

    for (EnergyGraph.Edge z : message.getEdges()) {
      Entity central = world.getEntityByID(z.getEntity1());
      Entity e = world.getEntityByID(z.getEntity2());
      if (e == null || central == null) {
        continue;
      }
      Vec3d orig = new Vec3d(central.posX, central.posY + 0.55, central.posZ);
      Vec3d end = new Vec3d(e.posX, e.posY + 0.55, e.posZ);
      Vec3d diff = end.subtract(orig);
      Vec3d movement = diff.normalize().mul(0.1, 0.1, 0.1);
      int iters = (int) (mag(diff) / mag(movement));

      Vec3d current = orig;
      for (int i = 0; i < iters; i++) {
        float r = 227 / 255f;
        float g = 103 / 255f;
        float b = 13 / 255f;

        BeamParticleData data = new BeamParticleData(1.4f, r, g, b, 40);
        world.addParticle(data, current.x, current.y, current.z, 0, 0, 0);
        current = current.add(movement);
      }
    }
    context.get().setPacketHandled(true);
  }

  public static double mag(Vec3d vec) {
    return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
  }
}
