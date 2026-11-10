package net.magicherb.block;

import net.magicherb.ModAdvancements;
import net.magicherb.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class HerbCropBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public HerbCropBlock(Properties settings) {
        super(settings);
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
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level instanceof ServerLevel && player instanceof ServerPlayer sp && state.getValue(AGE) == getMaxAge()) {
            ModAdvancements.grant(sp, "green_thumb");
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
