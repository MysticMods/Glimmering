package noobanidus.mods.glimmering.graph;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class EnergyGraph {
  private static MutableValueGraph<UUID, Integer> graph = ValueGraphBuilder.undirected().build();

  public static void clearEntity(GlimmerEntity entity) {
    graph.removeNode(entity.getUniqueID());
  }

  public static void addEntity(GlimmerEntity glimmerEntity, List<UUID> entities) {
    int type = glimmerEntity.getDataManager().get(GlimmerEntity.TYPE);
    UUID start = glimmerEntity.getUniqueID();
    for (UUID e : entities) {
      graph.putEdgeValue(start, e, type);
    }
  }

  private static List<UUID> linkedTransmitters (GlimmerEntity start) {
    start.getEntityWorld().getProfiler().startSection("Find Linked Transmitters");
    List<UUID> result = linkedTransmitters(start.getUniqueID());
    start.getEntityWorld().getProfiler().endSection();
    return result;
  }

  private static List<UUID> linkedTransmitters (UUID start) {
    return linkedTransmitters(start, new ArrayList<>(), new HashSet<>());
  }

  // I think this is a bread-first search. It's pretty dough-y.
  private static List<UUID> linkedTransmitters(UUID start, List<UUID> transmitterNodes, Set<UUID> processedNodes) {
    processedNodes.add(start);
    if (!graph.nodes().contains(start)) return transmitterNodes;

    List<UUID> toProcess = new ArrayList<>();
    for (UUID u : graph.adjacentNodes(start)) {
      if (processedNodes.contains(u)) {
        continue;
      }
      int value = graph.edgeValue(start, u);
      // 0 = Relay
      // 1 = Transmit
      // 2 = Receive
      if (value == 0) {
        toProcess.add(u);
      } else if (value == 1) {
        transmitterNodes.add(u);
      }
    }

    // Convert to breadth instead of depth-first searching
    for (UUID u : toProcess) {
      linkedTransmitters(u, transmitterNodes, processedNodes);
    }

    return transmitterNodes;
  }

  public static class EntityIter implements Iterable<GlimmerEntity> {
    private GlimmerEntity start;
    private Iterator<UUID> uuidIterator;
    private List<UUID> uuidInternal;

    public EntityIter(GlimmerEntity entity) {
      if (!entity.isServerWorld()) {
        throw new IllegalStateException("Cannot create an EntityIter on non-server worlds");
      }
      this.start = entity;
      this.uuidInternal = linkedTransmitters(entity);
    }

    @Override
    public Iterator<GlimmerEntity> iterator() {
      return new EntityIterator();
    }

    public class EntityIterator implements Iterator<GlimmerEntity> {
      public EntityIterator() {
        if (uuidIterator == null) {
          uuidIterator = uuidInternal.iterator();
        }
      }

      @Override
      public boolean hasNext() {
        return uuidIterator.hasNext();
      }

      @Override
      public GlimmerEntity next() {
        return (GlimmerEntity) ((ServerWorld) start.getEntityWorld()).getEntityByUuid(uuidIterator.next());
      }
    }
  }
}
