package net.magicherb.block;

import net.magicherb.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BongBlockEntity extends BlockEntity {

    private final List<ItemStack> loads = new ArrayList<>();
    private final Map<UUID, Long> lastHit = new HashMap<>();

    public BongBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BONG_ENTITY, pos, state);
    }

    public boolean load(ItemStack herb) {
        if (loads.size() >= ModConfig.INSTANCE.bong_capacity) return false;
        loads.add(herb.copyWithCount(1));
        setChanged();
        return true;
    }

    public int loadCount() {
        return loads.size();
    }

    public ItemStack takeLoad() {
        ItemStack herb = loads.removeFirst();
        setChanged();
        return herb;
    }

    // Per-player cooldown so several people can share the bong without one person spamming it.
    public boolean tryStartHit(Player player, long gameTime) {
        Long last = lastHit.get(player.getUUID());
        if (last != null && gameTime - last < ModConfig.INSTANCE.bong_cooldown_ticks) return false;
        lastHit.put(player.getUUID(), gameTime);
        return true;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("loads", ItemStack.CODEC.listOf(), List.copyOf(loads));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        loads.clear();
        input.read("loads", ItemStack.CODEC.listOf()).ifPresent(loads::addAll);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (level != null) loads.forEach(herb -> Block.popResource(level, pos, herb));
    }
}
