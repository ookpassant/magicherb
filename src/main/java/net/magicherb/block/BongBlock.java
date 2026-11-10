package net.magicherb.block;

import net.magicherb.ModConfig;
import net.magicherb.item.PipeItem;
import net.magicherb.item.Smoke;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BongBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(5, 0, 5, 11, 7, 11),
        Block.box(6, 7, 6, 10, 16, 10),
        Block.box(11, 3, 6.5, 15, 7, 9.5));

    public BongBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BongBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(PipeItem.SMOKES)) return InteractionResult.TRY_WITH_EMPTY_HAND;
        if (level.isClientSide() || !(level.getBlockEntity(pos) instanceof BongBlockEntity bong)) return InteractionResult.SUCCESS;

        if (!bong.load(stack)) {
            player.sendOverlayMessage(Component.translatable("message.magicherb.bong_full"));
            return InteractionResult.CONSUME;
        }
        if (!player.hasInfiniteMaterials()) stack.shrink(1);
        level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.6f, 1.3f);
        player.sendOverlayMessage(Component.translatable("message.magicherb.bong_loaded",
            bong.loadCount(), ModConfig.INSTANCE.bong_capacity));
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(level instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer smoker)) return InteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof BongBlockEntity bong)) return InteractionResult.PASS;

        if (bong.loadCount() == 0) {
            player.sendOverlayMessage(Component.translatable("message.magicherb.bong_empty"));
            return InteractionResult.CONSUME;
        }
        if (!bong.tryStartHit(smoker, level.getGameTime())) return InteractionResult.CONSUME;

        Smoke.Herb herb = Smoke.Herb.of(bong.takeLoad());
        level.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS,
            1.0f, 0.7f + level.getRandom().nextFloat() * 0.3f);
        ModConfig cfg = ModConfig.INSTANCE;
        Smoke.take(serverLevel, smoker, herb, cfg.effect_base_duration, 1.0f, cfg.bong_aoe_range, 1.1f);
        return InteractionResult.SUCCESS;
    }
}
