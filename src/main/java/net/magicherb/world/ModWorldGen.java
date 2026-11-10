package net.magicherb.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biome;

import java.util.Set;
import java.util.function.Predicate;
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

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name));
    }

    private static void add(Predicate<BiomeSelectionContext> biomes, String placedFeature) {
        BiomeModifications.addFeature(biomes, GenerationStep.Decoration.VEGETAL_DECORATION, placed(placedFeature));
    }

    @SafeVarargs
    private static Predicate<BiomeSelectionContext> anyOf(ResourceKey<Biome>... keys) {
        Set<ResourceKey<Biome>> set = Set.of(keys);
        return ctx -> set.contains(ctx.getBiomeKey());
    }

    public static void register() {
        Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "config_rarity"), ConfigRarityFilter.CODEC);

        // Classic strain everywhere in the overworld, denser on plains
        add(BiomeSelectors.foundInOverworld(), "wild_herb");
        add(anyOf(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS), "wild_herb_lush");

        // Biome strains, at lush density
        add(ctx -> ctx.hasTag(BiomeTags.IS_JUNGLE) || ctx.hasTag(BiomeTags.IS_SAVANNA), "wild_herb_energising");
        add(ctx -> ctx.hasTag(BiomeTags.IS_TAIGA) || ctx.getBiomeKey().equals(Biomes.DARK_FOREST), "wild_herb_sedating");
        add(anyOf(Biomes.MEADOW, Biomes.FLOWER_FOREST, Biomes.CHERRY_GROVE), "wild_herb_creative");
    }
}
