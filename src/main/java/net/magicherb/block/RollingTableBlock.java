package net.magicherb.block;

import net.magicherb.MagicHerbMod;
import net.magicherb.ModConfig;
import net.magicherb.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Prediction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RollingTableBlock extends Block {

    public static final TagKey<Item> ROLLABLE = TagKey.create(Registries.ITEM,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "rollable"));

    public RollingTableBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack herb, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        if (!herb.is(ROLLABLE)) return InteractionResult.TRY_WITH_EMPTY_HAND;
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        Inventory inventory = player.getInventory();
        boolean free = player.hasInfiniteMaterials();
        int paper = free ? Integer.MAX_VALUE : countPaper(inventory);
        if (paper == 0) {
            player.sendOverlayMessage(Component.translatable("message.magicherb.rolling_needs_paper"));
            return InteractionResult.CONSUME;
        }

        int rolls = player.isShiftKeyDown() ? 1 : Math.min(herb.getCount(), paper);
        ItemStack joints = new ItemStack(ModItems.JOINT, rolls * ModConfig.INSTANCE.rolling_table_joints_per_herb);
        joints.applyComponents(herb.getComponentsPatch());
        int made = joints.getCount();

        if (!free) {
            herb.shrink(rolls);
            removePaper(inventory, rolls);
        }
        inventory.placeItemBackInInventory(joints, Prediction.SERVER_ONLY);

        level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0f, 0.9f + level.getRandom().nextFloat() * 0.2f);
        player.sendOverlayMessage(Component.translatable("message.magicherb.rolled", made));
        return InteractionResult.SUCCESS;
    }

    private static int countPaper(Inventory inventory) {
        int count = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack s = inventory.getItem(i);
            if (s.is(Items.PAPER)) count += s.getCount();
        }
        return count;
    }

    private static void removePaper(Inventory inventory, int amount) {
        for (int i = 0; i < inventory.getContainerSize() && amount > 0; i++) {
            ItemStack s = inventory.getItem(i);
            if (!s.is(Items.PAPER)) continue;
            int take = Math.min(amount, s.getCount());
            s.shrink(take);
            amount -= take;
        }
    }
}
