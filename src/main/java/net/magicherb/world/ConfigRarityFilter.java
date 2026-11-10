package net.magicherb.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.magicherb.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

// Like minecraft:rarity_filter, but reads the chance from magicherb.json so servers can tune spawn density.
public record ConfigRarityFilter(boolean lush) implements PlacementFilter {

    public static final MapCodec<ConfigRarityFilter> CODEC =
        Codec.BOOL.optionalFieldOf("lush", false).xmap(ConfigRarityFilter::new, ConfigRarityFilter::lush);

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        int chance = lush ? ModConfig.INSTANCE.herb_spawn_rarity_lush : ModConfig.INSTANCE.herb_spawn_rarity;
        return random.nextFloat() < 1.0f / Math.max(1, chance);
    }

    @Override
    public MapCodec<ConfigRarityFilter> codec() {
        return CODEC;
    }
}
