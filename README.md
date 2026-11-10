# Magic Herb

A Fabric mod for Minecraft 26.3 that lets you cultivate and share a smoke with your friends.

---

## Features

### Items

| Item | Description |
|------|-------------|
| **Magic Herb** | Harvested from wild plants or your own crop. Used raw in a pipe or rolled into joints. |
| **Dried Herb** | Furnace-processed magic herb. Gives 2× effect duration when smoked. |
| **Herb Seeds** | Dropped from wild or mature crops. Plant on farmland to grow your own. |
| **Pipes** | Reusable. Hold right-click with herb/dried herb in your offhand. Comes in wood, clay and glass (see below). |
| **Joint** | Single-use. Right-click and hold for ~1.5 seconds. Crafted from magic herb + paper. |

### Pipes

| Pipe | Uses | Hit time | Effect duration | Share range | Notes |
|------|------|----------|-----------------|-------------|-------|
| **Wizard's Pipe** (wood) | 64 | ~2 s | 1× | 5 blocks | The all-rounder. |
| **Clay Pipe** | 160 | ~2.5 s | 1× | 5 blocks | Built to last; shatters like pottery when it finally breaks. |
| **Glass Pipe** | 32 | ~1.5 s | 1.25× | 6 blocks | Smoothest hit, but fragile. |

### Indoor Growing
Herb crops grown **indoors under artificial light** produce extra-potent herb. A crop counts as indoor-grown if, every time it grows a stage, it is lit to at least **block light 12** by lamps (torches, lanterns, glowstone…) and that lamp light is **brighter than any daylight** reaching it. A sealed grow room or a basement works; a greenhouse or an open field doesn't. Get the lights in before the crop's first growth stage — one stage grown in daylight and the batch is ordinary.

Indoor-grown herb shows **"Indoor-grown"** in its tooltip and keeps that through drying and joint rolling. Smoking it gives **Blazed II** and **Regeneration II** instead of level I, and it stacks with the dried-herb duration bonus.

### Smoking Effects
When you smoke (pipe or joint), you and nearby players receive:
- **Blazed** — custom effect, heals 0.5 HP every 3 seconds (1 HP at Blazed II)
- **Slowness I** — you're relaxed
- **Regeneration I** — half the blazed duration

Players within **5 blocks (pipe, 6 for glass)** or **4 blocks (joint)** passively receive half-strength versions of all effects — no action required on their end. Smoke particles are broadcast to everyone within 24 blocks.

### Blocks

| Block | Description |
|-------|-------------|
| **Wild Herb Plant** | Spawns naturally in overworld biomes on grass, dirt, podzol, mud, and moss. Break to get magic herb. |
| **Herb Crop** | 7 growth stages. Plant herb seeds on farmland. Drops magic herb and seeds when fully grown. |

### Crafting

All three pipes use the same shape — bowl material in `B`, stem material in `S`:
```
  B
S B
S  
```
| Pipe | `B` (bowl) | `S` (stem) |
|------|-----------|-----------|
| Wizard's Pipe | Stone | Oak Log |
| Clay Pipe | Brick | Brick |
| Glass Pipe | Glass | Glass Pane |

**Joint** (shapeless): Magic Herb + Paper → 2 joints

**Dried Herb**: Smelt Magic Herb in a furnace

### Advancements
Magic Herb → First Hit → Sharing is Caring / Cured / Bad Trip (hidden), and Magic Herb → Green Thumb → Grow Room.

---

## World Generation

Wild herb plants spawn across overworld biomes on the surface. They're rare enough to feel like a find — roughly a small cluster every few chunks, more often in jungles, savannas, plains, meadows and flower forests. Already-generated chunks won't have them; explore fresh terrain.

---

## Configuration

`config/magicherb.json` is created on first launch. Restart the game/server after editing.

| Option | Default | What it does |
|--------|---------|--------------|
| `pipe_use_ticks` / `joint_use_ticks` | 40 / 32 | How long a hit takes (20 ticks = 1 s). Pipes scale this by material. |
| `effect_base_duration` / `joint_effect_base_duration` | 600 / 200 | Base effect length in ticks. |
| `dried_herb_duration_multiplier` | 2.0 | Duration bonus for dried herb. |
| `pipe_aoe_range` / `joint_aoe_range` | 5.0 / 4.0 | Sharing radius in blocks. |
| `bad_trip_chance` | 0.03 | Chance of Nausea + Blindness instead of a good time. |
| `herb_spawn_rarity` / `herb_spawn_rarity_lush` | 6 / 3 | Wild herb patches appear in 1 of every N chunks (normal / lush biomes). |
| `hunger_exhaustion` | 3.0 | Hunger cost per hit. |
| `indoor_min_block_light` | 12 | Lamp light a crop needs to count as indoor-grown. |
| `indoor_potency_bonus` | 1 | Extra effect levels for indoor-grown herb. |

Other mods and datapacks can make their items smokeable in a pipe by adding them to the `magicherb:smokes` item tag.

---

## Installation

**Requirements:**
- Minecraft 26.3
- Fabric Loader 0.20.1+
- Fabric API 0.166.0+26.3

Drop `magicherb-1.0.0.jar` into your `mods/` folder.

---

## Building from Source

```bash
git clone https://github.com/zakopowell-max/magicherb.git
cd magicherb
./gradlew build
# Output: build/libs/magicherb-1.0.0.jar
```

Requires Java 25+.

### Editing Textures

All textures are 16×16 PNGs in `src/main/resources/assets/magicherb/textures/`.

Run the texture generator to get large grid-preview images (one cell = one Minecraft pixel). Name the designs you want so it doesn't overwrite hand-drawn textures:
```bash
python3 gentextures.py item/glass_pipe item/clay_pipe
# Textures → src/main/resources/assets/magicherb/textures/
# Previews → texture_previews/
```
Running it with no arguments regenerates **every** texture, replacing any hand-edited art.

Edit the 16×16 files directly, then rebuild.

---

## TODO / Planned

### High Priority
- [ ] **Proper textures** — current placeholders are functional pixel art but need artist love. Grid previews in `texture_previews/` show the pixel layout at 10× scale.
- [ ] **Client-side mod** — players need the mod installed client-side to see smoke particles. Consider making the client portion optional or adding a server-only mode.

### Gameplay
- [x] **Hunger interaction** — smoking costs a little hunger (`hunger_exhaustion`)
- [x] **Bad Trip** — rare chance to get Nausea and Blindness briefly
- [x] **Pipe variants** — wood, clay and glass pipes
- [x] **Indoor growing** — crops grown under artificial light are more potent
- [ ] **Bong block** — placeable block that multiple players can use at once; larger AOE
- [ ] **Rolling table** — crafting station for bulk joint rolling
- [ ] **Herb strains** — different herb variants with different effect profiles (energising, sedating, creative)
- [ ] **Aging / curing** — herb improves if stored in a barrel for some time
- [ ] **Grinder item** — processes herb for more efficient joint crafting

### Polish
- [ ] **Sounds** — custom inhale/exhale sounds; the fire extinguish placeholder is functional but rough
- [ ] **Smoke particle colour** — tint the smoke green when Blazed, grey otherwise
- [x] **Pipe break animation** — vanilla break animation, plus glass-shatter and pottery-shatter sounds for the glass and clay pipes
- [ ] **Effect icons** — custom HUD icon for the Blazed effect (currently uses a placeholder)
- [x] **Advancement tree** — First Hit, Sharing is Caring, Cured, Green Thumb, Grow Room, Bad Trip
- [x] **Wild herb biome weighting** — spawns more in jungles, savannas, plains, meadows and flower forests

### Technical
- [ ] **Server-only networking** — currently requires client mod for smoke particles; degrade gracefully without it
- [x] **Config file** — effect durations, AOE range, spawn rarity, indoor thresholds (see Configuration)
- [ ] **Datagen** — replace hand-written loot table / recipe / blockstate JSONs with Fabric data generation (would have caught the 26.x data-format changes at build time)
- [x] **Tag support** — `magicherb:smokes` tag so other mods can add compatible items

---

## Contributing

Pull requests welcome. Clone the repo, make your changes on a branch, and open a PR.

The mod is structured as a standard Fabric project:

```
src/
  main/java/net/magicherb/
    block/          Block classes + registration
    effect/         Blazed mob effect
    item/           Pipe, Joint, item registration
    network/        Smoke packet (server → client)
    world/          Biome modification / wild herb spawning
    MagicHerbMod.java
  client/java/net/magicherb/
    MagicHerbModClient.java   Particle rendering
  main/resources/
    assets/magicherb/         Textures, models, blockstates, lang
    data/magicherb/           Recipes, loot tables, worldgen
```

---

## Licence

Do whatever you want with it.
