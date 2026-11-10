package net.magicherb.item;

import net.magicherb.ModAdvancements;
import net.magicherb.ModConfig;
import net.magicherb.effect.ModEffects;
import net.magicherb.network.ModPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

final class Smoke {

    private Smoke() {}

    static void take(ServerLevel level, ServerPlayer smoker, int baseDuration, boolean indoorGrown, double range, float pitch) {
        int amplifier = indoorGrown ? ModConfig.INSTANCE.indoor_potency_bonus : 0;
        boolean badTrip = smoker.getRandom().nextDouble() < ModConfig.INSTANCE.bad_trip_chance;

        apply(smoker, baseDuration, amplifier, 1.0f, badTrip);

        List<ServerPlayer> nearby = level.getEntitiesOfClass(
            ServerPlayer.class,
            smoker.getBoundingBox().inflate(range),
            p -> p != smoker && !p.isSpectator()
        );
        nearby.forEach(p -> apply(p, baseDuration, amplifier, 0.5f, false));

        ModPackets.sendSmoke(smoker, smoker.position());
        level.playSound(null, smoker.blockPosition(),
            SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3f, pitch);

        ModAdvancements.grant(smoker, "first_smoke");
        if (!nearby.isEmpty()) ModAdvancements.grant(smoker, "sharing_is_caring");
        if (badTrip)           ModAdvancements.grant(smoker, "bad_trip");
    }

    private static void apply(ServerPlayer player, int base, int amplifier, float potency, boolean badTrip) {
        int dur = Math.round(base * potency);

        if (badTrip) {
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA,    Math.round(300 * potency), 0, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Math.round(200 * potency), 0, false, true));
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.BLAZED,       Math.round(base * 1.5f * potency), amplifier, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,     dur,     0,         false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, dur / 2, amplifier, false, false));
        }
        player.getFoodData().addExhaustion(ModConfig.INSTANCE.hunger_exhaustion * potency);
    }
}
