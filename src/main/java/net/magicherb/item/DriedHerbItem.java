package net.magicherb.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DriedHerbItem extends Item {

    public DriedHerbItem(Properties settings) {
        super(settings);
    }

    // Catches stacks that left a barrel without going through removeItem (e.g. shift-click).
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, EquipmentSlot slot) {
        if (stack.has(ModComponents.CURING_SINCE)) Aging.finishCuring(stack, level.getGameTime());
    }
}
