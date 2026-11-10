package net.magicherb.item;

import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

public class ModComponents {

    public static final DataComponentType<Unit> INDOOR_GROWN = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "indoor_grown"),
        DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC).build()
    );

    public static void register() {}
}
