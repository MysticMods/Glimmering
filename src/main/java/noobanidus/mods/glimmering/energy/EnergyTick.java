package noobanidus.mods.glimmering.energy;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import noobanidus.mods.glimmering.entity.GlimmerEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnergyTick {
  private static List<GlimmerEntity> standardRequests = new ArrayList<>();

  public static void addGlimmer(GlimmerEntity entity) {
    EnergyGraph.NodeType type = EnergyGraph.NodeType.byIndex(entity.getDataManager().get(GlimmerEntity.TYPE));
    if (type == EnergyGraph.NodeType.RECEIVE) {
      standardRequests.add(entity);
    }
  }

  public static void tickEntity(GlimmerEntity entity) {
    List<GlimmerEntity.Requirement> requirements = entity.required();
    if (requirements == null) {
      return;
    }
    for (GlimmerEntity glimmer : new EnergyGraph.EntityIter(entity)) {
      if (glimmer == null) {
        continue;
      }
      glimmer.supply(entity, requirements);
      requirements.removeIf(GlimmerEntity.Requirement::isMaxed);
      if (requirements.isEmpty()) {
        break;
      }
    }
  }

  public static void tick(TickEvent.WorldTickEvent event) {
    if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
      if (standardRequests.isEmpty()) {
        return;
      }

      Iterator<GlimmerEntity> iterator;

      iterator = standardRequests.iterator();
      while (iterator.hasNext()) {
        GlimmerEntity next = iterator.next();
        if (next.isAlive()) {
          tickEntity(next);
        }
        iterator.remove();
      }
    }
  }
}
