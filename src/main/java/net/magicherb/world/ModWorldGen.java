package net.magicherb.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGen {

    private static final ResourceKey<PlacedFeature> WILD_HERB_KEY = ResourceKey.create(
        Registries.PLACED_FEATURE,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "wild_herb")
    );

    private static final ResourceKey<PlacedFeature> WILD_HERB_LUSH_KEY = ResourceKey.create(
        Registries.PLACED_FEATURE,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "wild_herb_lush")
    );

    public static void register() {
        Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "config_rarity"), ConfigRarityFilter.CODEC);

        // Base spawn in all overworld biomes
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WILD_HERB_KEY
        );

        // Denser spawn in jungle, savanna, plains and meadow
        BiomeModifications.addFeature(
            ctx -> ctx.hasTag(BiomeTags.IS_JUNGLE)
                || ctx.hasTag(BiomeTags.IS_SAVANNA)
                || ctx.getBiomeKey().equals(Biomes.PLAINS)
                || ctx.getBiomeKey().equals(Biomes.SUNFLOWER_PLAINS)
                || ctx.getBiomeKey().equals(Biomes.MEADOW)
                || ctx.getBiomeKey().equals(Biomes.FLOWER_FOREST),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WILD_HERB_LUSH_KEY
        );
    }
}
