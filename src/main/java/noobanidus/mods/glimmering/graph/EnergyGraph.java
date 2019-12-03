package noobanidus.mods.glimmering.graph;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.init.ModEntities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class EnergyGraph {
  public static MutableValueGraph<UUID, Integer> graph = ValueGraphBuilder.undirected().build();

  public static void clearEntity(GlimmerEntity entity) {
    graph.removeNode(entity.getUniqueID());
  }

  public static void addEntity(GlimmerEntity glimmerEntity, List<Entity> entities) {
    int type = glimmerEntity.getDataManager().get(GlimmerEntity.TYPE);
    for (Entity e : entities) {
      graph.putEdgeValue(glimmerEntity.getUniqueID(), e.getUniqueID(), type);
    }
  }

  private static Set<UUID> eligibleNodes(UUID start, Set<UUID> energyNodes, Set<UUID> consideredNodes) {
    consideredNodes.add(start);
    if (!graph.nodes().contains(start)) return energyNodes;

    for (UUID u : graph.adjacentNodes(start)) {
      if (consideredNodes.contains(u)) {
        continue;
      }
      int value = graph.edgeValue(start, u);
      // 0 = Relay
      // 1 = Transmit
      // 2 = Receive
      if (value == 0) {
        eligibleNodes(u, energyNodes, consideredNodes);
      } else if (value == 1) {
        energyNodes.add(u);
      }
    }

    return energyNodes;
  }

  public static void findEnergyFor(GlimmerEntity entity) {
    if (entity.isServerWorld()) {
      int required = entity.desired();

      if (required == 0) {
        return;
      }
      int gathered = 0;

      UUID glimmer = entity.getUniqueID();
      Set<UUID> energyNodes = eligibleNodes(glimmer, new HashSet<>(), new HashSet<>());

      if (!energyNodes.isEmpty()) {
        ServerWorld world = (ServerWorld) entity.getEntityWorld();
        List<GlimmerEntity> entities = world.getEntities(ModEntities.GLIMMER.get(), (p) -> energyNodes.contains(p.getUniqueID())).stream().map(o -> (GlimmerEntity) o).collect(Collectors.toList());
        for (GlimmerEntity e : entities) {
          gathered += e.pull(required - gathered);
        }
      }

      int leftover = gathered - entity.push(gathered);
      if (leftover > 0) {
        //Glimmering.LOG.error(String.format("%s energy was wasted at %s,%s,%s!", leftover, entity.posX, entity.posY, entity.posZ));
      }
    }
  }
}
