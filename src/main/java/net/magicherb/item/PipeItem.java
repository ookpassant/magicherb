package net.magicherb.item;

import net.magicherb.MagicHerbMod;
import net.magicherb.ModAdvancements;
import net.magicherb.ModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class PipeItem extends Item {

    public static final TagKey<Item> SMOKES = TagKey.create(Registries.ITEM,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "smokes"));

    public enum Material {
        WOOD (64,  1.0f,  1.0f,  0.0, 1.5f),
        CLAY (160, 1.25f, 1.0f,  0.0, 1.3f),
        GLASS(32,  0.75f, 1.25f, 1.0, 1.7f);

        public final int durability;
        final float useTimeMultiplier;
        final float durationMultiplier;
        final double extraRange;
        final float pitch;

        Material(int durability, float useTimeMultiplier, float durationMultiplier, double extraRange, float pitch) {
            this.durability = durability;
            this.useTimeMultiplier = useTimeMultiplier;
            this.durationMultiplier = durationMultiplier;
            this.extraRange = extraRange;
            this.pitch = pitch;
        }
    }

    private final Material material;

    public PipeItem(Material material, Properties settings) {
        super(settings);
        this.material = material;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.getOffhandItem().is(SMOKES)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!(level instanceof ServerLevel serverLevel) || !(user instanceof ServerPlayer player)) return stack;

        ItemStack offhand = player.getOffhandItem();
        if (!offhand.is(SMOKES)) return stack;

        boolean dried  = offhand.is(ModItems.DRIED_HERB);
        boolean indoor = offhand.has(ModComponents.INDOOR_GROWN);
        offhand.shrink(1);

        ModConfig cfg = ModConfig.INSTANCE;
        double driedMultiplier = dried ? cfg.dried_herb_duration_multiplier : 1.0;
        int base = (int) (cfg.effect_base_duration * material.durationMultiplier * driedMultiplier);

        Smoke.take(serverLevel, player, base, indoor, cfg.pipe_aoe_range + material.extraRange, material.pitch);
        if (dried) ModAdvancements.grant(player, "cured");

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return Math.round(ModConfig.INSTANCE.pipe_use_ticks * material.useTimeMultiplier);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }
}
