#!/usr/bin/env python3
"""
Generate 16x16 placeholder textures for Magic Herb mod.
Also writes 160x160 grid-preview PNGs to texture_previews/ so you can
count every pixel before editing the real 16x16 files.
"""
from PIL import Image, ImageDraw
import os
import sys

# ── colour palette ──────────────────────────────────────────────────────────
T = (  0,   0,   0,   0)   # transparent
G = ( 45, 140,  69, 255)   # dark green  (matches BlazedEffect colour)
g = ( 85, 175,  95, 255)   # mid green
l = (145, 215, 130, 255)   # light green / highlight
y = (170, 130,  55, 255)   # yellow-green (young stem)
B = ( 95,  55,  25, 255)   # dark brown  (pipe body)
b = (150,  95,  50, 255)   # mid brown
t = (200, 155,  90, 255)   # tan / light brown
D = ( 35,  20,  10, 255)   # very dark   (pipe shadow)
W = (255, 250, 230, 255)   # cream-white (joint paper)
w = (215, 205, 175, 255)   # warm off-white
s = ( 75, 115,  45, 255)   # seed dark
S = (120, 165,  75, 255)   # seed light
O = (210, 140,  40, 255)   # orange-brown (dried)
o = (165, 100,  35, 255)   # dark dried
Q = ( 45, 115, 135, 255)   # glass outline (teal)
q = (175, 225, 235, 255)   # glass body
h = (240, 252, 255, 255)   # glass highlight
K = (120,  72,  48, 255)   # fired clay outline
c = (234, 200, 165, 255)   # fired clay light
m = (205, 155, 115, 255)   # fired clay shade
e = (240, 130,  40, 255)   # ember

PALETTE = {'.': T, 'G': G, 'g': g, 'l': l, 'y': y,
           'B': B, 'b': b, 't': t, 'D': D, 'W': W,
           'w': w, 's': s, 'S': S, 'O': O, 'o': o,
           'Q': Q, 'q': q, 'h': h, 'K': K, 'c': c,
           'm': m, 'e': e}

# ── pixel-art designs (16 rows × 16 cols) ───────────────────────────────────
DESIGNS = {}

# magic_herb — five-point cannabis leaf + stem
DESIGNS['item/magic_herb'] = [
    '........G.......',
    '.......GgG......',
    '......GglgG.....',
    '....G.GlllG.G...',
    '...GGGGlllGGGG..',
    '..GgllllGlllllG.',
    '.GgllllllllllgG.',
    '.GgllllllllllgG.',
    '..GglllllllllG..',
    '...GGGlllGGGG...',
    '....GGGlGGG.....',
    '.....GGlGG......',
    '......GGG.......',
    '......GgG.......',
    '.......G........',
    '................',
]

# dried_herb — same silhouette, amber/brown palette
DESIGNS['item/dried_herb'] = [
    '........o.......',
    '.......OoO......',
    '......OotO......',
    '....o.OttO.o....',
    '...ooOOtttOOoo..',
    '..OotttttttttO..',
    '.OottttttttttoO.',
    '.OottttttttttoO.',
    '..OottttttttO...',
    '...OOOtttOOO....',
    '....OOOtOO......',
    '.....OOtO.......',
    '......OOO.......',
    '......OoO.......',
    '.......o........',
    '................',
]

# herb_seeds — scattered seed pellets
DESIGNS['item/herb_seeds'] = [
    '................',
    '................',
    '.....sS.........',
    '....Ss..........',
    '................',
    '........sS......',
    '.......Ss.......',
    '................',
    '...sS...........',
    '...Ss...........',
    '................',
    '........sS......',
    '.......Ss.......',
    '................',
    '................',
    '................',
]

# pipe — L-shaped clay/wood pipe with bowl opening at top
DESIGNS['item/pipe'] = [
    '................',
    '........DD......',
    '.......DBBD.....',
    '......DBbbD.....',
    '......Dbb.D.....',
    '.......DD.......',
    '................',
    '.....DDDDDDD....',
    '....DBbbbbbBD...',
    '....DBbbbbbBD...',
    '....DBbbbbbBD...',
    '....DDDDDDDDD...',
    '................',
    '................',
    '................',
    '................',
]

# clay_pipe — long-stemmed fired-clay pipe, ember glowing in the bowl
DESIGNS['item/clay_pipe'] = [
    '................',
    '................',
    '..........KKKK..',
    '.........KDeDDK.',
    '.........KccccK.',
    '.........KcccmK.',
    '........KccccmK.',
    '.......KcmKmmK..',
    '......KcmK.KK...',
    '.....KcmK.......',
    '....KcmK........',
    '...KcmK.........',
    '..KcmK..........',
    '.KcmK...........',
    '.KmK............',
    '..K.............',
]

# glass_pipe — spoon pipe with a green swirl and a bowl hole at the bulb
DESIGNS['item/glass_pipe'] = [
    '................',
    '.........QQQQ...',
    '........QqhhqQ..',
    '.......QqDDDhqQ.',
    '.......QhDDDgqQ.',
    '.......QhqDggqQ.',
    '......QhqggqqQ..',
    '.....QhqgqQQQ...',
    '....QhqgqQ......',
    '...QhqgqQ.......',
    '..QhqgqQ........',
    '.QhqgqQ.........',
    '.QhqqQ..........',
    '.QQQQ...........',
    '................',
    '................',
]

# joint — wrapped cylinder, tapered at one end
DESIGNS['item/joint'] = [
    '................',
    '......WWWWWWW...',
    '.....WwwwwwwwW..',
    '....WwwwwwwwwwW.',
    '...WwwwwwwwwwwW.',
    '..WwwwwwwwwwwW..',
    '.WwwwwwwwwwwW...',
    '.WwwwwwwwwwW....',
    '..WwwwwwwwW.....',
    '...WwwwwwW......',
    '....WwwwW.......',
    '.....WWWW.......',
    '................',
    '................',
    '................',
    '................',
]

# wild_herb_plant — cross-sprite, tall grass with rounder leaves
# (used twice in the cross model so it tiles sideways)
DESIGNS['block/wild_herb_plant'] = [
    '......G.G.......',
    '.....GgGgG......',
    '....GglGlgG.....',
    '...GglllllgG....',
    '....GlllllG.....',
    '.....GlllG......',
    '......GlG.......',
    '.......G........',
    '.......G........',
    '.......G........',
    '......yGy.......',
    '.....yGGGy......',
    '......yGy.......',
    '.......G........',
    '.......G........',
    '................',
]

# herb_crop stages 0-7 — plant grows from seedling to bushy
# Stage 0: tiny sprout
DESIGNS['block/herb_crop_stage0'] = [
    '................',
    '................',
    '................',
    '................',
    '................',
    '................',
    '................',
    '................',
    '................',
    '.......gG.......',
    '......gGlG......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '................',
]

# Stage 1: two leaves
DESIGNS['block/herb_crop_stage1'] = [
    '................',
    '................',
    '................',
    '................',
    '................',
    '................',
    '.....gG.........',
    '....gGlG........',
    '.....GG.gG......',
    '......GGgGlG....',
    '.......GGG......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '................',
]

# Stage 2: small plant with 4 leaves
DESIGNS['block/herb_crop_stage2'] = [
    '................',
    '................',
    '................',
    '................',
    '.....gG.........',
    '....gGlG........',
    '.....GGgG.......',
    '....gGGGlG......',
    '.....GGG.gG.....',
    '......GGGgGlG...',
    '.......GGG......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '................',
]

# Stage 3
DESIGNS['block/herb_crop_stage3'] = [
    '................',
    '................',
    '................',
    '.....gG.........',
    '....gGlG........',
    '.....GG.gG......',
    '....gGGGlG.gG...',
    '.....GGGGGlGlG..',
    '....gGGGGGGGG...',
    '.....GGGGlGGlG..',
    '......GGGGGG....',
    '.......GGG......',
    '.......GG.......',
    '.......GG.......',
    '.......GG.......',
    '................',
]

# Stage 4: half-height bush
DESIGNS['block/herb_crop_stage4'] = [
    '................',
    '................',
    '....gG.....gG...',
    '...gGlG...gGlG..',
    '....GGGgG..GGG..',
    '...gGGGlGgGGGG..',
    '....GGGGGlGGGG..',
    '...gGGGGGGGGGg..',
    '....GGGGGGGGlG..',
    '.....GGGGGGlG...',
    '......GGGlGG....',
    '.......GGGGG....',
    '.......GGG......',
    '.......GG.......',
    '.......GG.......',
    '................',
]

# Stage 5
DESIGNS['block/herb_crop_stage5'] = [
    '................',
    '..gG.....gG.....',
    '.gGlG...gGlG....',
    '..GGGgG.GGGgG...',
    '.gGGGlGgGGGlG...',
    '..GGGGGGGGGlG...',
    '.gGGGGGGGGGGg...',
    '..GGGGGGGGGlG...',
    '.gGGGGGGGGGg....',
    '..GGGGGGGGlG....',
    '...GGGGGGlGG....',
    '....GGGlGGG.....',
    '.....GGlGG......',
    '......GGGG......',
    '.......GG.......',
    '................',
]

# Stage 6: nearly mature
DESIGNS['block/herb_crop_stage6'] = [
    '..gG....gG......',
    '.gGlG..gGlGgG...',
    '..GGGgGGGGlGlG..',
    '.gGGGlGGGGGGGg..',
    '..GGGGGGGGGGlG..',
    '.gGGGGGGGGGGGg..',
    '..GGGGGGGGGGlG..',
    '.gGGGGGGGGGGg...',
    '..GGGGGGGGGlG...',
    '..gGGGGGGGGg....',
    '...GGGGGGlGG....',
    '....GGGlGGG.....',
    '.....GlGGG......',
    '......GlGG......',
    '.......GGG......',
    '................',
]

# Stage 7: fully mature, dense bush
DESIGNS['block/herb_crop_stage7'] = [
    '.gG..gG..gG.....',
    'gGlGgGlGgGlGgG..',
    '.GGGGGGGGGGlGlG.',
    'gGGGGGGGGGGGGGg.',
    '.GGGGGGGGGGGGlG.',
    'gGGGGGGGGGGGGGg.',
    '.GGGGGGGGGGGGlG.',
    'gGGGGGGGGGGGGg..',
    '.GGGGGGGGGGGlG..',
    '.gGGGGGGGGGGg...',
    '..GGGGGGGGlGG...',
    '...GGGGGlGGG....',
    '....GGlGGGG.....',
    '.....GlGGG......',
    '......GlGG......',
    '.......GGG......',
]

# ── helpers ──────────────────────────────────────────────────────────────────

def parse(design):
    """Convert list-of-strings into a 16×16 RGBA Image."""
    img = Image.new('RGBA', (16, 16), T)
    for y, row in enumerate(design):
        for x, ch in enumerate(row):
            img.putpixel((x, y), PALETTE.get(ch, T))
    return img

def make_preview(img16, scale=10):
    """
    Scale up to scale*16 × scale*16, burn in a 1-px grid every `scale` px.
    Each cell = one Minecraft pixel.
    """
    size = scale * 16
    big = Image.new('RGBA', (size, size), (30, 30, 30, 255))  # dark bg
    for py in range(16):
        for px in range(16):
            colour = img16.getpixel((px, py))
            # fill cell (leave 1-px border for grid line)
            for dy in range(scale - 1):
                for dx in range(scale - 1):
                    bx = px * scale + dx + 1
                    by = py * scale + dy + 1
                    if colour[3] == 0:
                        # transparent cell: show dark grey so the grid is visible
                        big.putpixel((bx, by), (55, 55, 55, 255))
                    else:
                        big.putpixel((bx, by), colour)
    # grid lines are already dark bg colour — no extra drawing needed
    # Add row / col numbers along top & left edge (requires a font-free approach:
    # just rely on the grid lines; 10-px cells are easy to count)
    return big

# ── main ─────────────────────────────────────────────────────────────────────

ROOT = os.path.dirname(os.path.abspath(__file__))
BASE = os.path.join(ROOT, 'src/main/resources/assets/magicherb/textures')
PREVIEW_BASE = os.path.join(ROOT, 'texture_previews')

# Pass design names (e.g. item/glass_pipe) to write only those; with no args every
# texture is regenerated, which overwrites any hand-edited art.
selected = sys.argv[1:] or list(DESIGNS)
unknown = [p for p in selected if p not in DESIGNS]
if unknown:
    sys.exit(f'Unknown design(s): {", ".join(unknown)}')

for path in selected:
    design = DESIGNS[path]
    # real 16×16 texture
    dest = os.path.join(BASE, path + '.png')
    os.makedirs(os.path.dirname(dest), exist_ok=True)
    img = parse(design)
    img.save(dest)

    # grid preview
    preview_dest = os.path.join(PREVIEW_BASE, path + '_grid.png')
    os.makedirs(os.path.dirname(preview_dest), exist_ok=True)
    make_preview(img).save(preview_dest)

    print(f'  ✓  {path}')

print(f'\nTextures → {BASE}/')
print(f'Previews  → {PREVIEW_BASE}/   (160×160, 1 cell = 1 Minecraft pixel)')
