package noobanidus.mods.glimmering.network;

import com.google.common.graph.Network;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import noobanidus.mods.glimmering.Glimmering;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Networking {
  private static Networking INSTANCE = new Networking();

  private final String PROTOCOL_VERSION = Integer.toString(2);
  private short index = 0;

  private final SimpleChannel HANDLER;

  private Networking() {
    this.HANDLER = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(Glimmering.MODID, "main_network_channel"))
        .clientAcceptedVersions(PROTOCOL_VERSION::equals)
        .serverAcceptedVersions(PROTOCOL_VERSION::equals)
        .networkProtocolVersion(() -> PROTOCOL_VERSION)
        .simpleChannel();
  }

  private int id = 0;

  private void sendToInternal(Object msg, ServerPlayerEntity player) {
    if (!(player instanceof FakePlayer))
      HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
  }

  private void sendToServerInternal(Object msg) {
    HANDLER.sendToServer(msg);
  }

  private <MSG> void sendInternal(PacketDistributor.PacketTarget target, MSG message) {
    HANDLER.send(target, message);
  }



  public <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
    HANDLER.registerMessage(index, messageType, encoder, decoder, messageConsumer);
    index++;
    if (index > 0xFF)
      throw new RuntimeException("Too many messages!");
  }

  public static void registerMessages() {
    INSTANCE.registerMessage(BeamMessage.class, BeamMessage::encode, BeamMessage::new, BeamMessage::handle);
  }

  public static void sendTo(Object msg, ServerPlayerEntity player) {
    INSTANCE.sendToInternal(msg, player);
  }

  public static void sendToServer(Object msg) {
    INSTANCE.sendToServerInternal(msg);
  }

  public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
    INSTANCE.sendInternal(target, message);
  }

  public static abstract class Message<T extends Message> {
    public Message () {
    }

    public Message(PacketBuffer buffer) {
    }

    public abstract void encode (PacketBuffer buffer);

    public void handle(Supplier<NetworkEvent.Context> context) {
      context.get().enqueueWork(() -> handle((T) this, context));
    }

    public abstract void handle (T message, Supplier<NetworkEvent.Context> context);
  }
}
