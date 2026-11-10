package net.magicherb.block;

import net.magicherb.ModAdvancements;
import net.magicherb.ModConfig;
import net.magicherb.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class HerbCropBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    // True while every growth step so far happened indoors under artificial light.
    public static final BooleanProperty INDOOR = BooleanProperty.create("indoor");

    public HerbCropBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(INDOOR, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(INDOOR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(INDOOR, true);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.HERB_SEEDS;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        carryIndoorFlag(level, pos, state);
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        super.growCrops(level, pos, state);
        carryIndoorFlag(level, pos, state);
    }

    // Vanilla growth rebuilds the state from the default, so re-apply the flag after each growth step.
    private void carryIndoorFlag(Level level, BlockPos pos, BlockState before) {
        BlockState after = level.getBlockState(pos);
        if (!after.is(this) || after == before) return;
        boolean indoor = before.getValue(INDOOR) && isUnderArtificialLight(level, pos);
        if (after.getValue(INDOOR) != indoor) {
            level.setBlock(pos, after.setValue(INDOOR, indoor), Block.UPDATE_CLIENTS);
        }
    }

    public static boolean isUnderArtificialLight(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight   = level.getBrightness(LightLayer.SKY, pos);
        return blockLight >= ModConfig.INSTANCE.indoor_min_block_light && blockLight > skyLight;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level instanceof ServerLevel && player instanceof ServerPlayer sp && state.getValue(AGE) == getMaxAge()) {
            ModAdvancements.grant(sp, "green_thumb");
            if (state.getValue(INDOOR)) ModAdvancements.grant(sp, "grow_room");
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
