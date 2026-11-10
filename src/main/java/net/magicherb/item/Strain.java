package net.magicherb.item;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

public enum Strain implements StringRepresentable {
    CLASSIC("classic", ChatFormatting.GRAY),
    ENERGISING("energising", ChatFormatting.YELLOW),
    SEDATING("sedating", ChatFormatting.AQUA),
    CREATIVE("creative", ChatFormatting.LIGHT_PURPLE);

    public static final Codec<Strain> CODEC = StringRepresentable.fromEnum(Strain::values);
    public static final StreamCodec<ByteBuf, Strain> STREAM_CODEC =
        ByteBufCodecs.VAR_INT.map(i -> values()[i], Strain::ordinal);

    private final String name;
    public final ChatFormatting colour;

    Strain(String name, ChatFormatting colour) {
        this.name = name;
        this.colour = colour;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static Strain of(ItemStack stack) {
        return stack.getOrDefault(ModComponents.STRAIN, CLASSIC);
    }
}
