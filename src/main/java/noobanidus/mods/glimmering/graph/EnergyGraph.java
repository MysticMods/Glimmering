package noobanidus.mods.glimmering.graph;

import net.minecraft.entity.Entity;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class EnergyGraph {
  public static class Edge {
    private final int entity1;
    private final int entity2;

    public Edge(Vertex entity1, Vertex entity2) {
      this.entity1 = entity1.entityId;
      this.entity2 = entity2.entityId;
    }

    public Edge(int entity1, int entity2) {
      this.entity1 = entity1;
      this.entity2 = entity2;
    }

    public int getEntity1() {
      return entity1;
    }

    public int getEntity2() {
      return entity2;
    }
  }

  public static class Vertex {
    private final int entityId;
    private final NodeType type;

    public Vertex(GlimmerEntity entity) {
      this(entity.getEntityId(), entity.getDataManager().get(GlimmerEntity.TYPE));
    }

    public Vertex(int entityId, NodeType type) {
      this.entityId = entityId;
      this.type = type;
    }

    public int getEntityId() {
      return entityId;
    }

    public NodeType getType() {
      return type;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Vertex vertex = (Vertex) o;
      return entityId == vertex.entityId &&
          type == vertex.type;
    }

    @Override
    public int hashCode() {
      return Objects.hash(entityId, type);
    }
  }

  public enum NodeType {
    RELAY,
    TRANSMIT,
    RECEIVE;

    public static NodeType byIndex(int index) {
      int i = 0;
      for (NodeType t : values()) {
        if (i == index) {
          return t;
        }
        i++;
      }
      return RELAY;
    }
  }

  private static final Set<Vertex> removedNodes = new HashSet<>();

  private static final Map<Vertex, Set<Vertex>> adjacencies = new HashMap<>();

  private static Set<Vertex> getAdjacent(Vertex vertex) {
    Set<Vertex> result = adjacencies.computeIfAbsent(vertex, v -> new HashSet<>());
    result.removeAll(removedNodes);
    return result;
  }

  private static void addEdge(Vertex a, Vertex b) {
    removedNodes.remove(a);
    removedNodes.remove(b);
    Set<Vertex> vertA = getAdjacent(a);
    vertA.add(b);
  }

  public static void clearEntity(GlimmerEntity entity) {
    Vertex node = new Vertex(entity);
    removedNodes.add(node);
    adjacencies.remove(node);
  }

  public static void addEntity(GlimmerEntity glimmerEntity, List<GlimmerEntity> entities) {
    Vertex start = new Vertex(glimmerEntity);
    removedNodes.remove(start);
    for (GlimmerEntity e : entities) {
      addEdge(start, new Vertex(e));
    }
  }

  public static Set<Edge> getEdgesFrom (GlimmerEntity entity) {
    Vertex start = new Vertex(entity);
    Set<Edge> result = new HashSet<>();
    return getEdgesFrom(start, result);
  }

  private static Set<Edge> getEdgesFrom (Vertex start, Set<Edge> result) {
    for (Vertex n : getAdjacent(start)) {
      result.add(new Edge(start, n));
      getEdgesFrom(n, result);
    }
    return result;
  }

  private static List<Vertex> linkedTransmitters(GlimmerEntity start) {
    start.getEntityWorld().getProfiler().startSection("Find Linked Transmitters");
    List<Vertex> result = linkedTransmitters(new Vertex(start));
    start.getEntityWorld().getProfiler().endSection();
    return result;
  }

  private static List<Vertex> linkedTransmitters(Vertex start) {
    return linkedTransmitters(start, new ArrayList<>(), new HashSet<>());
  }

  // I think this is a bread-first search. It's pretty dough-y.
  private static List<Vertex> linkedTransmitters(Vertex start, List<Vertex> transmitterNodes, Set<Vertex> processedNodes) {
    processedNodes.add(start);

    List<Vertex> toProcess = new ArrayList<>();
    for (Vertex u : getAdjacent(start)) {
      if (processedNodes.contains(u)) {
        continue;
      }
      if (u.getType() == NodeType.RELAY) {
        toProcess.add(u);
      } else if (u.getType() == NodeType.TRANSMIT) {
        transmitterNodes.add(u);
      }
    }

    // Convert to breadth instead of depth-first searching
    for (Vertex u : toProcess) {
      linkedTransmitters(u, transmitterNodes, processedNodes);
    }

    return transmitterNodes;
  }

  public static class EntityIter implements Iterable<GlimmerEntity> {
    private GlimmerEntity start;
    private Iterator<Vertex> vertIterator;
    private List<Vertex> vertInternal;

    public EntityIter(GlimmerEntity entity) {
      this.start = entity;
      this.vertInternal = linkedTransmitters(entity);
    }

    @Override
    public Iterator<GlimmerEntity> iterator() {
      return new EntityIterator();
    }

    public class EntityIterator implements Iterator<GlimmerEntity> {
      public EntityIterator() {
        if (vertIterator == null) {
          vertIterator = vertInternal.iterator();
        }
      }

      @Override
      public boolean hasNext() {
        return vertIterator.hasNext();
      }

      @Override
      @Nullable
      public GlimmerEntity next() {
        Vertex next = vertIterator.next();
        Entity entity = start.getEntityWorld().getEntityByID(next.getEntityId());
        if (entity instanceof GlimmerEntity) {
          return (GlimmerEntity) entity;
        }

        return null;
      }
    }
  }
}
