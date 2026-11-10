package net.magicherb;

import net.fabricmc.api.ModInitializer;
import net.magicherb.block.ModBlocks;
import net.magicherb.effect.ModEffects;
import net.magicherb.item.ModComponents;
import net.magicherb.item.ModItems;
import net.magicherb.network.ModPackets;
import net.magicherb.world.ModWorldGen;
import net.magicherb.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicHerbMod implements ModInitializer {
    public static final String MOD_ID = "magicherb";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.load();
        ModEffects.register();
        ModComponents.register();
        ModBlocks.register();
        ModItems.register();
        ModPackets.registerServer();
        ModWorldGen.register();
        LOGGER.info("Magic Herb initialised");
    }
}
