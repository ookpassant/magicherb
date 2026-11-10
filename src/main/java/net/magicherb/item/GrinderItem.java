package net.magicherb.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public class GrinderItem extends Item {

    public GrinderItem(Properties settings) {
        super(settings);
    }

    // Stays in the crafting grid one use more worn, until it wears out.
    @Override
    public ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        int damage = stack.getDamageValue() + 1;
        if (damage >= stack.getMaxDamage()) return null;
        ItemStack worn = stack.copyWithCount(1);
        worn.setDamageValue(damage);
        return ItemStackTemplate.fromNonEmptyStack(worn);
    }
}
