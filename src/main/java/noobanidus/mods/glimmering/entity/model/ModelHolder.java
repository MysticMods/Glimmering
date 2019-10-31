package noobanidus.mods.glimmering.entity.model;

import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModelHolder implements ISelectiveResourceReloadListener {

  public static GlimmerModel glimmerModel;

  public static void init() {
    glimmerModel = new GlimmerModel();
  }

  @Override
  public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
  }

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
    // TODO make this work selectively
    init();
  }
}
