package net.magicherb;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.magicherb.item.Aging;
import net.magicherb.item.ModComponents;
import net.magicherb.item.Strain;
import net.magicherb.network.ModPackets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MagicHerbModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
            List<Component> extra = new ArrayList<>();
            Strain strain = stack.get(ModComponents.STRAIN);
            if (strain != null) {
                extra.add(Component.translatable("tooltip.magicherb.strain",
                    Component.translatable("strain.magicherb." + strain.getSerializedName())).withStyle(strain.colour));
            }
            if (stack.has(ModComponents.INDOOR_GROWN)) {
                extra.add(Component.translatable("tooltip.magicherb.indoor_grown").withStyle(ChatFormatting.GREEN));
            }
            if (stack.has(ModComponents.AGED)) {
                extra.add(Component.translatable("tooltip.magicherb.aged").withStyle(ChatFormatting.GOLD));
            }
            ClientLevel level = Minecraft.getInstance().level;
            if (stack.has(ModComponents.CURING_SINCE) && level != null) {
                int percent = Math.round(Aging.progress(stack, level.getGameTime()) * 100);
                extra.add(Component.translatable("tooltip.magicherb.curing", percent).withStyle(ChatFormatting.GRAY));
            }
            lines.addAll(Math.min(1, lines.size()), extra);
        });

        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SmokePayload.ID, (payload, context) ->
            context.client().execute(() -> {
                ClientLevel level = context.client().level;
                if (level == null) return;

                var rand = ThreadLocalRandom.current();
                for (int i = 0; i < 10; i++) {
                    level.addParticle(
                        ParticleTypes.LARGE_SMOKE,
                        payload.x() + rand.nextDouble() * 0.6 - 0.3,
                        payload.y() + rand.nextDouble() * 0.4,
                        payload.z() + rand.nextDouble() * 0.6 - 0.3,
                        rand.nextDouble() * 0.06 - 0.03,
                        rand.nextDouble() * 0.1 + 0.05,
                        rand.nextDouble() * 0.06 - 0.03
                    );
                }
            })
        );
    }
}
