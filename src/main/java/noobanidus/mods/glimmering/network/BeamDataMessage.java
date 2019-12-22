package noobanidus.mods.glimmering.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import noobanidus.mods.glimmering.beam.Beam;

import java.util.List;
import java.util.function.Supplier;

public class BeamDataMessage extends Networking.Message<BeamDataMessage> {
  public BeamDataMessage(List<Beam> beams) {
  }

  public BeamDataMessage(PacketBuffer buffer) {

  }

  @Override
  public void encode(PacketBuffer buffer) {

  }

  @Override
  public void handle(BeamDataMessage message, Supplier<NetworkEvent.Context> context) {

  }
}
