package net.magicherb.item;

import com.mojang.serialization.Codec;
import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

public class ModComponents {

    public static final DataComponentType<Unit> INDOOR_GROWN = register("indoor_grown", Unit.CODEC, Unit.STREAM_CODEC);
    public static final DataComponentType<Strain> STRAIN = register("strain", Strain.CODEC, Strain.STREAM_CODEC);
    public static final DataComponentType<Unit> AGED = register("aged", Unit.CODEC, Unit.STREAM_CODEC);
    // Game time the stack went into a barrel; cleared when it comes out.
    public static final DataComponentType<Long> CURING_SINCE = register("curing_since", Codec.LONG, ByteBufCodecs.VAR_LONG);

    private static <T> DataComponentType<T> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> stream) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name),
            DataComponentType.<T>builder().persistent(codec).networkSynchronized(stream).build());
    }

    public static void register() {}
}
