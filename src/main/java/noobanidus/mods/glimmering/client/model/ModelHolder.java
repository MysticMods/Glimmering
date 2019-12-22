package noobanidus.mods.glimmering.client.model;

import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;

public class ModelHolder implements ISelectiveResourceReloadListener {

  public static GlimmerModel glimmerModel;
  public static RitualModel ritualModel;

  public static void init() {
    glimmerModel = new GlimmerModel();
    ritualModel = new RitualModel();
  }

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
    init();
  }
}
