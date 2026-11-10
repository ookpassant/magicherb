package net.magicherb.block;

import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

    public static final Block WILD_HERB_PLANT = register("wild_herb_plant",
        new WildHerbBlock(BlockBehaviour.Properties.of()
            .setId(key("wild_herb_plant"))
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.POPPED)
            .replaceable()
        )
    );

    public static final Block HERB_CROP = register("herb_crop",
        new HerbCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)
            .setId(key("herb_crop"))
            .pushReaction(PushReaction.POPPED)
        )
    );

    private static ResourceKey<Block> key(String name) {
        return ResourceKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name));
    }

    private static Block register(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name), block);
    }

    public static void register() {}
}
