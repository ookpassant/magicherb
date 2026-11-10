package net.magicherb.item;

import net.magicherb.MagicHerbMod;
import net.magicherb.ModConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Herb ages while it sits in a barrel: stamped on the way in, judged on the way out.
public final class Aging {

    public static final TagKey<Item> AGEABLE = TagKey.create(Registries.ITEM,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "ageable"));

    private Aging() {}

    public static void startCuring(ItemStack stack, long gameTime) {
        if (stack.is(AGEABLE) && !stack.has(ModComponents.AGED) && !stack.has(ModComponents.CURING_SINCE)) {
            stack.set(ModComponents.CURING_SINCE, gameTime);
        }
    }

    public static void finishCuring(ItemStack stack, long gameTime) {
        Long since = stack.remove(ModComponents.CURING_SINCE);
        if (since != null && gameTime - since >= ModConfig.INSTANCE.aging_ticks) {
            stack.set(ModComponents.AGED, Unit.INSTANCE);
            stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        }
    }

    public static float progress(ItemStack stack, long gameTime) {
        Long since = stack.get(ModComponents.CURING_SINCE);
        if (since == null) return 0f;
        return Math.min(1f, (gameTime - since) / (float) Math.max(1, ModConfig.INSTANCE.aging_ticks));
    }
}
