package net.magicherb.item;

import net.magicherb.ModAdvancements;
import net.magicherb.ModConfig;
import net.magicherb.effect.ModEffects;
import net.magicherb.network.ModPackets;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class Smoke {

    private Smoke() {}

    public record Herb(boolean dried, boolean indoor, boolean aged, Strain strain) {
        public static Herb of(ItemStack stack) {
            return new Herb(
                stack.is(ModItems.DRIED_HERB),
                stack.has(ModComponents.INDOOR_GROWN),
                stack.has(ModComponents.AGED),
                Strain.of(stack));
        }
    }

    public static void take(ServerLevel level, ServerPlayer smoker, Herb herb,
                            int deviceBaseDuration, float deviceMultiplier, double range, float pitch) {
        ModConfig cfg = ModConfig.INSTANCE;
        double multiplier = deviceMultiplier
            * (herb.dried() ? cfg.dried_herb_duration_multiplier : 1.0)
            * (herb.aged() ? cfg.aged_duration_multiplier : 1.0);
        int base = (int) (deviceBaseDuration * multiplier);
        int amplifier = herb.indoor() ? cfg.indoor_potency_bonus : 0;
        boolean badTrip = !herb.aged() && smoker.getRandom().nextDouble() < cfg.bad_trip_chance;

        apply(smoker, herb.strain(), base, amplifier, 1.0f, badTrip);

        List<ServerPlayer> nearby = level.getEntitiesOfClass(
            ServerPlayer.class,
            smoker.getBoundingBox().inflate(range),
            p -> p != smoker && !p.isSpectator()
        );
        nearby.forEach(p -> apply(p, herb.strain(), base, amplifier, 0.5f, false));

        ModPackets.sendSmoke(smoker, smoker.position());
        level.playSound(null, smoker.blockPosition(),
            SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3f, pitch);

        ModAdvancements.grant(smoker, "first_smoke");
        if (!nearby.isEmpty()) ModAdvancements.grant(smoker, "sharing_is_caring");
        if (badTrip)           ModAdvancements.grant(smoker, "bad_trip");
    }

    private static void apply(ServerPlayer player, Strain strain, int base, int amp, float potency, boolean badTrip) {
        int dur = Math.round(base * potency);

        if (badTrip) {
            add(player, MobEffects.NAUSEA,    Math.round(300 * potency), 0, true);
            add(player, MobEffects.BLINDNESS, Math.round(200 * potency), 0, true);
        } else {
            add(player, ModEffects.BLAZED, Math.round(base * 1.5f * potency), amp, true);
            switch (strain) {
                case CLASSIC -> {
                    add(player, MobEffects.SLOWNESS,     dur,     0,   false);
                    add(player, MobEffects.REGENERATION, dur / 2, amp, false);
                }
                case ENERGISING -> {
                    add(player, MobEffects.SPEED, dur, amp, false);
                    add(player, MobEffects.HASTE, dur, amp, false);
                }
                case SEDATING -> {
                    add(player, MobEffects.SLOWNESS,     dur,     1,       false);
                    add(player, MobEffects.REGENERATION, dur / 2, amp + 1, false);
                    add(player, MobEffects.RESISTANCE,   dur / 2, amp,     false);
                }
                case CREATIVE -> {
                    add(player, MobEffects.NIGHT_VISION, dur, 0,   false);
                    add(player, MobEffects.LUCK,         dur, amp, false);
                }
            }
        }
        player.getFoodData().addExhaustion(ModConfig.INSTANCE.hunger_exhaustion * potency);
    }

    private static void add(ServerPlayer player, Holder<MobEffect> effect, int duration, int amplifier, boolean particles) {
        player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, particles));
    }
}
