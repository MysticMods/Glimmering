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
import noobanidus.mods.glimmering.client.beam.Beam;
import noobanidus.mods.glimmering.client.beam.BeamManager;
import noobanidus.mods.glimmering.client.render.BeamRenderer;
import noobanidus.mods.glimmering.graph.EnergyGraph;

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
      Vec3d orig = new Vec3d(central.posX, central.posY - 1.2, central.posZ);
      Vec3d end = new Vec3d(e.posX, e.posY - 1.2, e.posZ);
      Beam beam = new Beam(orig, end, 100);
      BeamManager.addBeam(beam);
      //BeamRenderer.addBeam(world, orig, end, 30);
    }
    context.get().setPacketHandled(true);
  }
}
