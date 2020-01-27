package noobanidus.mods.glimmering;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class CommandSerializers {
  private final CommandDispatcher<CommandSource> dispatcher;

  public CommandSerializers(CommandDispatcher<CommandSource> dispatcher) {
    this.dispatcher = dispatcher;
  }

  public CommandSerializers register () {
    this.dispatcher.register(builder(Commands.literal("glimmering").requires(p -> p.hasPermissionLevel(2))));
    return this;
  }

  public LiteralArgumentBuilder<CommandSource> builder (LiteralArgumentBuilder<CommandSource> builder) {
    builder.executes(c -> {
      c.getSource().sendFeedback(new StringTextComponent("Usage: /glimmering serializers"), false);
      return 1;
    });
    builder.then(Commands.literal("serializers").executes(c -> {
      IntIdentityHashBiMap<IDataSerializer<?>> map = ObfuscationReflectionHelper.getPrivateValue(DataSerializers.class, null, "field_187204_n");
      if (map == null) {
        c.getSource().sendErrorMessage(new StringTextComponent("ObfuscationReflectionHelper failed?!"));
      } else {
        int i = 0;
        for (IDataSerializer<?> serializer : map) {
          c.getSource().sendFeedback(new StringTextComponent("Serializer #" + i + ": is " + serializer.getClass().getName()), false);
          i++;
        }
      }
      return 1;
    }));
    return builder;
  }
}
