# Magic Herb

A Fabric mod for Minecraft 26.3 that lets you cultivate and share a smoke with your friends.

---

## Features

### Items

| Item | Description |
|------|-------------|
| **Magic Herb** | Harvested from wild plants or your own crop. Used raw in a pipe or rolled into joints. |
| **Dried Herb** | Furnace-processed magic herb. Gives 2× effect duration when smoked. Can be aged in a barrel. |
| **Ground Herb** | Magic herb run through a grinder: 1 herb → 2 ground herb. Rolls and smokes like magic herb. |
| **Herb Seeds** | Dropped from wild or mature crops. Plant on farmland to grow your own. Seeds remember their strain. |
| **Herb Grinder** | 128 uses. Craft it with magic herb to grind it; it comes back out of the grid one use more worn. |
| **Pipes** | Reusable. Hold right-click with herb/dried herb in your offhand. Comes in wood, clay and glass (see below). |
| **Joint** | Single-use. Right-click and hold for ~1.5 seconds. Crafted from magic herb (or ground herb) + paper. |

### Strains
Every herb has a strain, shown in its tooltip. Seeds, herb, dried herb, ground herb and joints all keep it.

| Strain | Where it grows wild | Effects (instead of Slowness + Regeneration) |
|--------|---------------------|---------|
| **Classic** | Everywhere in the overworld, densest on plains | Slowness I, Regeneration |
| **Energising** | Jungles and savannas | Speed, Haste |
| **Sedating** | Taigas and dark forests | Slowness II, stronger Regeneration, Resistance |
| **Creative** | Meadows, flower forests and cherry groves | Night Vision, Luck |

Every strain also gives Blazed. Plant a strain's seeds to farm it anywhere.

### Bong
A placeable glass bong for groups. Right-click it holding herb to load it (up to 8 loads), then right-click with an empty hand (or any non-herb item) to take a hit. Anyone can use it, no waiting for the person before you, but each player has a 2-second cooldown between hits. Effects reach friends within **8 blocks**. Breaking the bong drops any herb still loaded.

### Rolling Table
Right-click the table holding magic herb or ground herb to roll the whole stack at once, using paper from anywhere in your inventory. Each herb + paper makes **3 joints** (2 in a crafting grid). Sneak-click to roll just one.

### Barrel Aging
Put dried herb in a barrel and leave it. After **one Minecraft day** (20 minutes) it comes out **barrel-aged**: it shimmers, its effects last **1.5× longer** and it **never causes a bad trip**. Hover over herb in the barrel to see how far along it is. Taking it out early resets it; the rest of the stack keeps aging.

### Pipes

| Pipe | Uses | Hit time | Effect duration | Share range | Notes |
|------|------|----------|-----------------|-------------|-------|
| **Wizard's Pipe** (wood) | 64 | ~2 s | 1× | 5 blocks | The all-rounder. |
| **Clay Pipe** | 160 | ~2.5 s | 1× | 5 blocks | Built to last; shatters like pottery when it finally breaks. |
| **Glass Pipe** | 32 | ~1.5 s | 1.25× | 6 blocks | Smoothest hit, but fragile. The only enchantable pipe. |

### Enchanting Glass Pipes
Glass pipes can be enchanted at an enchanting table or with books on an anvil:
- **Unbreaking I–III**: from the enchanting table, like any tool.
- **Mending**: from books (villagers, loot), like any tool.
- **Hotbox I–II**: new. Each level adds **2 blocks** to the sharing range (6 → 8 → 10). Shows up at the enchanting table, on books, from villagers and in loot. Glass pipes only.

### Indoor Growing
Herb crops grown **indoors under artificial light** produce extra-potent herb. A crop counts as indoor-grown if, every time it grows a stage, it is lit to at least **block light 12** by lamps (torches, lanterns, glowstone…) and that lamp light is **brighter than any daylight** reaching it. A sealed grow room or a basement works; a greenhouse or an open field doesn't. Get the lights in before the crop's first growth stage — one stage grown in daylight and the batch is ordinary.

Indoor-grown herb has its own frosty look, shows **"Indoor-grown"** in its tooltip, and keeps both through drying and joint rolling. Smoking it gives **Blazed II** and **Regeneration II** instead of level I, and it stacks with the dried-herb duration bonus.

### Smoking Effects
When you smoke classic herb (pipe, joint or bong), you and nearby players receive:
- **Blazed** — custom effect, heals 0.5 HP every 3 seconds (1 HP at Blazed II)
- **Slowness I** — you're relaxed
- **Regeneration I** — half the blazed duration

Other strains swap Slowness and Regeneration for their own effects (see Strains).

Players within **5 blocks (pipe, 6 for glass)**, **4 blocks (joint)** or **8 blocks (bong)** passively receive half-strength versions of all effects — no action required on their end. Smoke particles are broadcast to everyone within 24 blocks.

### Blocks

| Block | Description |
|-------|-------------|
| **Wild Herb Plant** | Spawns naturally in overworld biomes on grass, dirt, podzol, mud, and moss. Break to get magic herb. |
| **Herb Crop** | 7 growth stages. Plant herb seeds on farmland. Drops magic herb and seeds when fully grown. |
| **Bong** | Shared smoking station (see Bong). |
| **Rolling Table** | Bulk joint rolling (see Rolling Table). |

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

**Joint** (shapeless): Magic Herb (or Ground Herb) + Paper → 2 joints

**Ground Herb** (shapeless): Magic Herb + Herb Grinder → 2 ground herb, grinder returned with 1 use spent

**Dried Herb**: Smelt Magic Herb in a furnace

**Bong**: Glass Pane in the top two middle slots, 3 Glass along the bottom

**Rolling Table**: 3 Paper over 3 Planks (any wood)

**Herb Grinder**: Iron Ingot, Iron Nugget, Iron Ingot in a column

### Advancements
Magic Herb → First Hit → Sharing is Caring / Cured / Bad Trip (hidden), and Magic Herb → Green Thumb → Grow Room.

---

## World Generation

Wild herb plants spawn across overworld biomes on the surface. They're rare enough to feel like a find — roughly a small cluster every few chunks, more often in plains and in the biomes of each strain (see Strains). Already-generated chunks won't have them; explore fresh terrain.

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
| `bong_aoe_range` / `bong_capacity` / `bong_cooldown_ticks` | 8.0 / 8 / 40 | Bong sharing radius, how many loads it holds, and each player's wait between hits. |
| `rolling_table_joints_per_herb` | 3 | Joints per herb + paper at the rolling table. |
| `aging_ticks` / `aged_duration_multiplier` | 24000 / 1.5 | How long dried herb must stay in a barrel, and the duration bonus once aged. |
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
- [ ] **Proper textures** — current placeholders are functional pixel art but need artist love. All paths are under `src/main/resources/assets/magicherb/`; replace a file with the same name and size to swap it in.
  - [x] `textures/item/magic_herb.png`, `dried_herb.png`, `herb_seeds.png` (16×16, hand-drawn)
  - [ ] `textures/item/pipe.png`, `clay_pipe.png`, `glass_pipe.png`, `joint.png` (16×16; pipes drawn diagonally, mouthpiece bottom-left)
  - [ ] `textures/item/magic_herb_indoor.png`, `dried_herb_indoor.png`, `joint_indoor.png`, `ground_herb_indoor.png` (16×16; indoor-grown versions, currently "frosted" copies)
  - [ ] `textures/item/ground_herb.png`, `grinder.png` (16×16)
  - [ ] `textures/block/wild_herb_plant.png` (16×16; shown as two crossed planes like tall grass)
  - [ ] `textures/block/herb_crop_stage0.png` … `herb_crop_stage7.png` (16×16; shown in a `#` like wheat, stage 7 = harvestable)
  - [ ] `textures/block/rolling_table_top.png`, `rolling_table_side.png` (16×16; side is the table front, transparent between the legs)
  - [ ] `textures/block/bong.png` (16×16 sheet the 3D model reads parts from: top-left 3×3 = mouthpiece opening, top-right 3×3 = herb in the bowl, columns 6–9 rows 0–8 = neck, columns 5–10 rows 9–15 = water chamber, columns 11–14 rows 9–13 = bowl and stem. Run `python3 gentextures.py block/bong` and look at `texture_previews/block/bong_grid.png` for the layout)
  - [ ] `textures/mob_effect/blazed.png` (18×18; effect icon)
  - [ ] `icon.png` (128×128; mod icon in mod lists, currently the magic herb scaled up)
- [ ] **Client-side mod** — players need the mod installed client-side to see smoke particles. Consider making the client portion optional or adding a server-only mode.

### Gameplay
- [x] **Hunger interaction** — smoking costs a little hunger (`hunger_exhaustion`)
- [x] **Bad Trip** — rare chance to get Nausea and Blindness briefly
- [x] **Pipe variants** — wood, clay and glass pipes
- [x] **Indoor growing** — crops grown under artificial light are more potent
- [x] **Bong block** — placeable block that multiple players can use at once; larger AOE
- [x] **Rolling table** — station for bulk joint rolling
- [x] **Herb strains** — classic, energising, sedating and creative, found wild by biome
- [x] **Aging / curing** — dried herb improves if stored in a barrel for a Minecraft day
- [x] **Grinder item** — processes herb for more efficient joint crafting

### Polish
- [ ] **Sounds** — custom inhale/exhale sounds; the fire extinguish placeholder is functional but rough
- [ ] **Smoke particle colour** — tint the smoke green when Blazed, grey otherwise
- [x] **Pipe break animation** — vanilla break animation, plus glass-shatter and pottery-shatter sounds for the glass and clay pipes
- [ ] **Effect icons** — custom HUD icon for the Blazed effect (currently uses a placeholder; see Proper textures)
- [x] **Advancement tree** — First Hit, Sharing is Caring, Cured, Green Thumb, Grow Room, Bad Trip
- [x] **Wild herb biome weighting** — spawns more on plains and in each strain's biomes

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
    block/          Crops, wild herb, bong, rolling table + registration
    effect/         Blazed mob effect
    item/           Pipes, joint, grinder, strains, aging, smoking effects, item registration
    mixin/          Barrel hook for aging
    network/        Smoke packet (server → client)
    world/          Biome modification / wild herb spawning
    MagicHerbMod.java
  client/java/net/magicherb/
    MagicHerbModClient.java   Particle rendering, herb tooltips
  main/resources/
    assets/magicherb/         Textures, models, blockstates, lang
    data/magicherb/           Recipes, loot tables, worldgen
```

---

## Licence

Do whatever you want with it.
