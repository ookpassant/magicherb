package net.magicherb.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.magicherb.MagicHerbMod;
import net.magicherb.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {

    public static final Item MAGIC_HERB = register("magic_herb",
        new Item(props("magic_herb").stacksTo(64)));

    public static final Item DRIED_HERB = register("dried_herb",
        new DriedHerbItem(props("dried_herb").stacksTo(64)));

    public static final Item GROUND_HERB = register("ground_herb",
        new Item(props("ground_herb").stacksTo(64)));

    public static final Item HERB_SEEDS = register("herb_seeds",
        new BlockItem(ModBlocks.HERB_CROP, props("herb_seeds").stacksTo(64)));

    public static final Item PIPE = register("pipe",
        new PipeItem(PipeItem.Material.WOOD, props("pipe").durability(PipeItem.Material.WOOD.durability)));

    public static final Item CLAY_PIPE = register("clay_pipe",
        new PipeItem(PipeItem.Material.CLAY, props("clay_pipe")
            .durability(PipeItem.Material.CLAY.durability)
            .component(DataComponents.BREAK_SOUND, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.DECORATED_POT_SHATTER))));

    public static final Item GLASS_PIPE = register("glass_pipe",
        new PipeItem(PipeItem.Material.GLASS, props("glass_pipe")
            .durability(PipeItem.Material.GLASS.durability)
            .enchantable(15)
            .component(DataComponents.BREAK_SOUND, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.GLASS_BREAK))));

    public static final Item JOINT = register("joint",
        new JointItem(props("joint").stacksTo(16)));

    public static final Item GRINDER = register("grinder",
        new GrinderItem(props("grinder").durability(128)));

    public static final Item BONG = register("bong",
        new BlockItem(ModBlocks.BONG, props("bong").useBlockDescriptionPrefix()));

    public static final Item ROLLING_TABLE = register("rolling_table",
        new BlockItem(ModBlocks.ROLLING_TABLE, props("rolling_table").useBlockDescriptionPrefix()));

    private static Item.Properties props(String name) {
        return new Item.Properties().setId(
            ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name)));
    }

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name), item);
    }

    private static ResourceKey<CreativeModeTab> tab(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace(name));
    }

    public static void register() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "herb_drying"), HerbDryingRecipe.SERIALIZER);

        CreativeModeTabEvents.modifyOutputEvent(tab("natural")).register(output -> {
            output.prepend(GROUND_HERB);
            output.prepend(DRIED_HERB);
            output.prepend(MAGIC_HERB);
            output.prepend(HERB_SEEDS);
            for (Strain strain : Strain.values()) {
                if (strain == Strain.CLASSIC) continue;
                ItemStack seeds = new ItemStack(HERB_SEEDS);
                seeds.set(ModComponents.STRAIN, strain);
                output.accept(seeds);
            }
        });

        CreativeModeTabEvents.modifyOutputEvent(tab("tools")).register(output -> {
            output.prepend(GRINDER);
            output.prepend(JOINT);
            output.prepend(GLASS_PIPE);
            output.prepend(CLAY_PIPE);
            output.prepend(PIPE);
        });

        CreativeModeTabEvents.modifyOutputEvent(tab("functional_blocks")).register(output -> {
            output.prepend(ROLLING_TABLE);
            output.prepend(BONG);
        });
    }
}
