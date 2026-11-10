package net.magicherb.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildHerbBlock extends VegetationBlock {

    private static final VoxelShape SHAPE = Block.column(12.0, 0.0, 13.0);

    public WildHerbBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(Blocks.GRASS_BLOCK)
            || floor.is(Blocks.DIRT)
            || floor.is(Blocks.COARSE_DIRT)
            || floor.is(Blocks.PODZOL)
            || floor.is(Blocks.ROOTED_DIRT)
            || floor.is(Blocks.MOSS_BLOCK)
            || floor.is(Blocks.MUD);
    }
}
