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
| **Pipe** | Reusable. Hold right-click for ~2 seconds with herb/dried herb in your offhand. 64 uses before it breaks. |
| **Joint** | Single-use. Right-click and hold for ~1.5 seconds. Crafted from magic herb + paper. |

### Smoking Effects
When you smoke (pipe or joint), you and nearby players receive:
- **Blazed** — custom effect, heals 0.5 HP every 3 seconds
- **Slowness I** — you're relaxed
- **Regeneration I** — half the blazed duration

Players within **5 blocks (pipe)** or **4 blocks (joint)** passively receive half-strength versions of all effects — no action required on their end. Smoke particles are broadcast to everyone within 24 blocks.

### Blocks

| Block | Description |
|-------|-------------|
| **Wild Herb Plant** | Spawns naturally in overworld biomes on grass, dirt, podzol, mud, and moss. Break to get magic herb. |
| **Herb Crop** | 7 growth stages. Plant herb seeds on farmland. Drops magic herb and seeds when fully grown. |

### Crafting

**Pipe** (shaped):
```
  S
W S
W  
```
Stone (`S`) + Oak Log (`W`)

**Joint** (shapeless): Magic Herb + Paper → 2 joints

**Dried Herb**: Smelt Magic Herb in a furnace

---

## World Generation

Wild herb plants spawn across overworld biomes on the surface. They're rare enough to feel like a find — roughly a small cluster every few chunks. Already-generated chunks won't have them; explore fresh terrain.

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

Run the texture generator to get large grid-preview images (one cell = one Minecraft pixel):
```bash
python3 gentextures.py
# Previews → texture_previews/
```

Edit the 16×16 files directly, then rebuild.

---

## TODO / Planned

### High Priority
- [ ] **Proper textures** — current placeholders are functional pixel art but need artist love. Grid previews in `texture_previews/` show the pixel layout at 10× scale.
- [ ] **Client-side mod** — players need the mod installed client-side to see smoke particles. Consider making the client portion optional or adding a server-only mode.

### Gameplay
- [ ] **Hunger interaction** — smoking could optionally satisfy a small amount of hunger (food effect)
- [ ] **Bad Trip** — rare chance at high amplifier to get Nausea or Blindness briefly
- [ ] **Bong block** — placeable block that multiple players can use at once; larger AOE
- [ ] **Rolling table** — crafting station for bulk joint rolling
- [ ] **Herb strains** — different herb variants with different effect profiles (energising, sedating, creative)
- [ ] **Aging / curing** — herb improves if stored in a barrel for some time
- [ ] **Grinder item** — processes herb for more efficient joint crafting

### Polish
- [ ] **Sounds** — custom inhale/exhale sounds; the fire extinguish placeholder is functional but rough
- [ ] **Smoke particle colour** — tint the smoke green when Blazed, grey otherwise
- [ ] **Pipe break animation** — visual feedback when the pipe durability runs out
- [ ] **Effect icons** — custom HUD icon for the Blazed effect (currently uses a placeholder)
- [ ] **Advancement tree** — "First Smoke", "Sharing is Caring" (AOE hit), "Cured" (use dried herb), "Green Thumb" (grow a full crop)
- [ ] **Wild herb biome weighting** — spawn more in jungles/plains, less in deserts/tundra

### Technical
- [ ] **Server-only networking** — currently requires client mod for smoke particles; degrade gracefully without it
- [ ] **Config file** — expose effect durations, AOE range, pipe durability, spawn rarity as server-configurable options
- [ ] **Datagen** — replace hand-written loot table / recipe / blockstate JSONs with Fabric data generation
- [ ] **Tag support** — `magicherb:smokes` tag so other mods can add compatible items

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
