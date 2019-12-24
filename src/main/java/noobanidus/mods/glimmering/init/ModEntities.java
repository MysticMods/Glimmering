package noobanidus.mods.glimmering.init;

import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import noobanidus.mods.glimmering.Glimmering;
import noobanidus.mods.glimmering.entity.GlimmerEntity;
import noobanidus.mods.glimmering.entity.RitualEntity;
import noobanidus.mods.glimmering.client.render.item.GlimmerItemRenderer;
import noobanidus.mods.glimmering.items.GlimmerSpawnItem;

import java.util.function.Supplier;

import static noobanidus.mods.glimmering.Glimmering.REGISTRATE;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  private static final String GLIMMER_ID = "glimmer";
  private static final String RITUAL_ID = "ritual";
  private static NonNullUnaryOperator<Item.Properties> GLIMMER_RPOPS = (o) -> o.setTEISR(() -> () -> GlimmerItemRenderer.instance);

  private static EntityType<GlimmerEntity> GLIMMER_TYPE = (EntityType<GlimmerEntity>) EntityType.Builder.create(GlimmerEntity::new, EntityClassification.CREATURE).size(0.75f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3).build(GLIMMER_ID).setRegistryName(Glimmering.MODID, GLIMMER_ID);

  private static EntityType<RitualEntity> RITUAL_TYPE = (EntityType<RitualEntity>) EntityType.Builder.create(RitualEntity::new, EntityClassification.MISC).size(1, 1).setTrackingRange(18).setShouldReceiveVelocityUpdates(false).setUpdateInterval(3).size(5, 5).build(RITUAL_ID).setRegistryName(Glimmering.MODID, RITUAL_ID);

  public static Supplier<EntityType<GlimmerEntity>> GLIMMER = () -> GLIMMER_TYPE;
  public static Supplier<EntityType<RitualEntity>> RITUAL = () -> RITUAL_TYPE;

  public static RegistryObject<GlimmerSpawnItem> SPAWN_GLIMMER = REGISTRATE.item(GLIMMER_ID, GlimmerSpawnItem::new).properties(GLIMMER_RPOPS).model((ctx) -> {
    ctx.getProvider().withExistingParent("glimmer", new ResourceLocation(Glimmering.MODID, "item/template_glimmer"));
  }).recipe(ctx -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 4)
            .patternLine(" X ")
            .patternLine("XXX")
            .patternLine(" X ")
            .key('X', Items.REDSTONE)
            .addCriterion("has_redstone", ctx.getProvider().hasItem(Items.REDSTONE))
            .build(ctx.getProvider());
  }).register();

  public static void load() {
  }

  public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
    IForgeRegistry<EntityType<?>> registry = event.getRegistry();

    registry.registerAll(GLIMMER_TYPE, RITUAL_TYPE);
  }
}
