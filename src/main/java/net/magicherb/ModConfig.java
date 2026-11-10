package net.magicherb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static ModConfig INSTANCE = new ModConfig();

    public int    pipe_use_ticks                 = 40;
    public int    joint_use_ticks                = 32;
    public int    effect_base_duration           = 600;
    public int    joint_effect_base_duration     = 200;
    public double dried_herb_duration_multiplier = 2.0;
    public double pipe_aoe_range                 = 5.0;
    public double joint_aoe_range                = 4.0;
    public double bad_trip_chance                = 0.03;
    public int    herb_spawn_rarity              = 6;
    public int    herb_spawn_rarity_lush         = 3;
    public float  hunger_exhaustion              = 3.0f;
    public int    indoor_min_block_light         = 12;
    public int    indoor_potency_bonus           = 1;

    public static void load() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve("magicherb.json");
        if (file.toFile().exists()) {
            try (Reader r = new FileReader(file.toFile())) {
                ModConfig loaded = GSON.fromJson(r, ModConfig.class);
                if (loaded != null) INSTANCE = loaded;
            } catch (IOException e) {
                MagicHerbMod.LOGGER.error("Failed to read config, using defaults: {}", e.getMessage());
            }
        }
        try (Writer w = new FileWriter(file.toFile())) {
            GSON.toJson(INSTANCE, w);
        } catch (IOException e) {
            MagicHerbMod.LOGGER.error("Failed to write config: {}", e.getMessage());
        }
    }
}
