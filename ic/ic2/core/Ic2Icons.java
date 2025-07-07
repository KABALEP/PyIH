/*     */ package ic2.core;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.fluids.IC2Fluid;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ic2Icons
/*     */ {
/*  19 */   public static Map<String, IIcon[]> textures = (Map)new HashMap<>();
/*  20 */   public static Map<String, String> iconToFile = new HashMap<>();
/*  21 */   public static Map<String, SpriteData> iconIDToData = new HashMap<>();
/*  22 */   public static Map<Integer, List<TextureEntry>> texturesToLoad = new HashMap<>();
/*  23 */   public static Map<Integer, List<SpecialEntry>> specialTexturesToLoad = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static IIcon[] getTexture(String par1) {
/*  27 */     return textures.get(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addSprite(SpriteData data) {
/*  32 */     String id = data.spriteID;
/*  33 */     iconIDToData.put(id, data);
/*  34 */     int amount = data.info.spriteAmount;
/*  35 */     textures.put(id, new IIcon[amount]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addTextureEntry(int id, List<TextureEntry> entries) {
/*  40 */     check(id);
/*  41 */     ((List<TextureEntry>)texturesToLoad.get(Integer.valueOf(id))).addAll(entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addTextureEntry(int id, TextureEntry entry) {
/*  46 */     check(id);
/*  47 */     ((List<TextureEntry>)texturesToLoad.get(Integer.valueOf(id))).add(entry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addSpecialEntry(int id, List<SpecialEntry> entries) {
/*  52 */     checkSpecial(id);
/*  53 */     ((List<SpecialEntry>)specialTexturesToLoad.get(Integer.valueOf(id))).addAll(entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addSpecialEntry(int id, SpecialEntry entry) {
/*  58 */     checkSpecial(id);
/*  59 */     ((List<SpecialEntry>)specialTexturesToLoad.get(Integer.valueOf(id))).add(entry);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkSpecial(int id) {
/*  64 */     if (!specialTexturesToLoad.containsKey(Integer.valueOf(id)))
/*     */     {
/*  66 */       specialTexturesToLoad.put(Integer.valueOf(id), new ArrayList<>());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void check(int id) {
/*  72 */     if (!texturesToLoad.containsKey(Integer.valueOf(id)))
/*     */     {
/*  74 */       texturesToLoad.put(Integer.valueOf(id), new ArrayList<>());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  80 */     addSprite(new SpriteData("b0", "ic2:textures/sprites/block_0.png", new SpriteInfo(16, 16)));
/*  81 */     addSprite(new SpriteData("bc", "ic2:textures/sprites/crops_0.png", new SpriteInfo(16, 16)));
/*  82 */     addSprite(new SpriteData("bgen", "ic2:textures/sprites/block_generator.png", new SpriteInfo(16, 12)));
/*  83 */     addSprite(new SpriteData("bmach", "ic2:textures/sprites/block_machine.png", new SpriteInfo(16, 12)));
/*  84 */     addSprite(new SpriteData("bmach2", "ic2:textures/sprites/block_machine2.png", new SpriteInfo(16, 12)));
/*  85 */     addSprite(new SpriteData("bpersonal", "ic2:textures/sprites/block_personal.png", new SpriteInfo(16, 12)));
/*  86 */     addSprite(new SpriteData("bcable", "ic2:textures/sprites/block_cable.png", new SpriteInfo(17, 17)));
/*  87 */     addSprite(new SpriteData("belec", "ic2:textures/sprites/block_electric.png", new SpriteInfo(16, 12)));
/*  88 */     addSprite(new SpriteData("i0", "ic2:textures/sprites/item_0.png", new SpriteInfo(16, 16)));
/*  89 */     addSprite(new SpriteData("i1", "ic2:textures/sprites/item_1.png", new SpriteInfo(16, 16)));
/*  90 */     addSprite(new SpriteData("i2", "ic2:textures/sprites/item_2.png", new SpriteInfo(16, 16)));
/*  91 */     addSprite(new SpriteData("i3", "ic2:textures/sprites/item_3.png", new SpriteInfo(16, 16)));
/*  92 */     addSprite(new SpriteData("batBox", "ic2:textures/sprites/Batbox.png", new SpriteInfo(4, 12)));
/*  93 */     addSprite(new SpriteData("mfe", "ic2:textures/sprites/MFE.png", new SpriteInfo(4, 12)));
/*  94 */     addSprite(new SpriteData("mfsu", "ic2:textures/sprites/MFSU.png", new SpriteInfo(4, 12)));
/*  95 */     addSprite(new SpriteData("bBox", "ic2:textures/sprites/BatteryBox.png", new SpriteInfo(4, 12)));
/*  96 */     addSprite(new SpriteData("triggers", "ic2:textures/sprites/bc_triggers.png", new SpriteInfo(5, 5)));
/*  97 */     addSprite(new SpriteData("conv", "ic2:textures/sprites/block_Converter.png", new SpriteInfo(16, 12)));
/*  98 */     addSprite(new SpriteData("CPad", "ic2:textures/sprites/block_pads.png", new SpriteInfo(4, 12)));
/*     */     
/* 100 */     List<TextureEntry> blocks = new ArrayList<>();
/* 101 */     List<TextureEntry> items = new ArrayList<>();
/* 102 */     List<SpecialEntry> specialBlocks = new ArrayList<>();
/* 103 */     List<SpecialEntry> specialItems = new ArrayList<>();
/*     */ 
/*     */     
/* 106 */     blocks.add(new TextureEntry("b0", 1, 0, 2, 1));
/* 107 */     blocks.add(new TextureEntry("b0", 12, 0, 16, 1));
/* 108 */     blocks.add(new TextureEntry("b0", 0, 1, 4, 2));
/* 109 */     blocks.add(new TextureEntry("b0", 11, 1, 14, 2));
/* 110 */     blocks.add(new TextureEntry("b0", 15, 1, 16, 2));
/* 111 */     blocks.add(new TextureEntry("b0", 0, 2, 7, 3));
/* 112 */     blocks.add(new TextureEntry("b0", 8, 2, 16, 3));
/* 113 */     blocks.add(new TextureEntry("b0", 8, 3, 16, 4));
/* 114 */     blocks.add(new TextureEntry("b0", 3, 4, 5, 5));
/* 115 */     blocks.add(new TextureEntry("b0", 14, 4, 16, 5));
/* 116 */     blocks.add(new TextureEntry("b0", 13, 5, 16, 6));
/* 117 */     blocks.add(new TextureEntry("b0", 4, 7, 7, 9));
/*     */     
/* 119 */     blocks.add(new TextureEntry("bc", 0, 0, 16, 4));
/* 120 */     blocks.add(new TextureEntry("bc", 0, 4, 3, 5));
/*     */     
/* 122 */     blocks.add(new TextureEntry("bgen", 0, 0, 13, 12));
/*     */     
/* 124 */     blocks.add(new TextureEntry("bmach", 0, 0, 16, 12));
/*     */     
/* 126 */     blocks.add(new TextureEntry("bmach2", 0, 0, 16, 12));
/*     */     
/* 128 */     blocks.add(new TextureEntry("bpersonal", 0, 0, 4, 12));
/*     */     
/* 130 */     blocks.add(new TextureEntry("bcable", 0, 0, 17, 16));
/* 131 */     blocks.add(new TextureEntry("bcable", 0, 16, 5, 17));
/*     */     
/* 133 */     blocks.add(new TextureEntry("belec", 0, 0, 6, 12));
/*     */     
/* 135 */     blocks.add(new TextureEntry("batBox", 0, 0, 4, 12));
/*     */     
/* 137 */     blocks.add(new TextureEntry("mfe", 0, 0, 4, 12));
/*     */     
/* 139 */     blocks.add(new TextureEntry("mfsu", 0, 0, 4, 12));
/*     */     
/* 141 */     blocks.add(new TextureEntry("bBox", 0, 0, 4, 12));
/*     */ 
/*     */     
/* 144 */     blocks.add(new TextureEntry("conv", 0, 0, 6, 6));
/* 145 */     blocks.add(new TextureEntry("conv", 6, 0, 7, 12));
/*     */     
/* 147 */     blocks.add(new TextureEntry("CPad", 0, 0, 4, 12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     items.add(new TextureEntry("i0", 0, 0, 12, 2));
/* 154 */     items.add(new TextureEntry("i0", 0, 2, 10, 3));
/* 155 */     items.add(new TextureEntry("i0", 0, 3, 16, 5));
/* 156 */     items.add(new TextureEntry("i0", 0, 5, 10, 6));
/* 157 */     items.add(new TextureEntry("i0", 0, 6, 16, 7));
/* 158 */     items.add(new TextureEntry("i0", 0, 8, 16, 9));
/* 159 */     items.add(new TextureEntry("i0", 0, 9, 1, 10));
/* 160 */     items.add(new TextureEntry("i0", 0, 10, 16, 11));
/*     */     
/* 162 */     items.add(new TextureEntry("i1", 0, 0, 16, 1));
/* 163 */     items.add(new TextureEntry("i1", 0, 1, 10, 2));
/* 164 */     items.add(new TextureEntry("i1", 0, 2, 12, 3));
/* 165 */     items.add(new TextureEntry("i1", 0, 3, 10, 4));
/* 166 */     items.add(new TextureEntry("i1", 0, 4, 16, 5));
/* 167 */     items.add(new TextureEntry("i1", 0, 5, 6, 6));
/* 168 */     items.add(new TextureEntry("i1", 0, 6, 7, 7));
/* 169 */     items.add(new TextureEntry("i1", 0, 7, 10, 8));
/* 170 */     items.add(new TextureEntry("i1", 0, 8, 8, 9));
/* 171 */     items.add(new TextureEntry("i1", 0, 9, 12, 10));
/*     */     
/* 173 */     items.add(new TextureEntry("i2", 0, 0, 16, 1));
/* 174 */     items.add(new TextureEntry("i2", 0, 1, 4, 2));
/* 175 */     items.add(new TextureEntry("i2", 0, 2, 14, 3));
/*     */     
/* 177 */     items.add(new TextureEntry("i3", 0, 0, 11, 1));
/* 178 */     items.add(new TextureEntry("i3", 0, 1, 4, 3));
/* 179 */     items.add(new TextureEntry("i3", 0, 3, 3, 6));
/* 180 */     items.add(new TextureEntry("i3", 0, 6, 15, 7));
/* 181 */     items.add(new TextureEntry("i3", 0, 7, 8, 8));
/* 182 */     items.add(new TextureEntry("i3", 0, 8, 3, 9));
/* 183 */     items.add(new TextureEntry("i3", 0, 9, 5, 11));
/*     */ 
/*     */     
/* 186 */     items.add(new TextureEntry("triggers", 0, 0, 5, 2));
/* 187 */     items.add(new TextureEntry("triggers", 0, 2, 4, 3));
/*     */     
/* 189 */     addTextureEntry(0, blocks);
/* 190 */     addTextureEntry(1, items);
/* 191 */     addSpecialEntry(0, specialBlocks);
/* 192 */     addSpecialEntry(1, specialItems);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static void load(TextureMap map, SpriteData data, int x, int y) {
/* 199 */     ((IIcon[])textures.get(data.spriteID))[x + y * data.info.maxX] = (IIcon)SubIconHandler.getSubIcon(map, x, y, data, -1, (map.func_130086_a() == 1));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static void loadSpecial(TextureMap r, SpriteData data, int x, int y, int modify) {
/* 205 */     int pos = x + y * data.info.maxX;
/* 206 */     TextureAtlasSprite textureAtlasSprite = SubIconHandler.getSubIcon(r, x, y, data, modify, (r.func_130086_a() == 1));
/* 207 */     IIcon[] array = textures.get(data.spriteID);
/* 208 */     for (int i = x; i < x + modify; i++) {
/*     */       
/* 210 */       for (int j = y; j < y + modify; j++) {
/*     */         
/* 212 */         pos = i + j * data.info.maxX;
/* 213 */         array[pos] = (IIcon)textureAtlasSprite;
/*     */       } 
/*     */     } 
/* 216 */     textures.put(data.spriteID, array);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void load(TextureMap r) {
/* 222 */     if (r.func_130086_a() == 0)
/*     */     {
/* 224 */       IC2Fluid.initTextures((IIconRegister)r);
/*     */     }
/* 226 */     List<TextureEntry> entries = texturesToLoad.get(Integer.valueOf(r.func_130086_a()));
/* 227 */     if (entries != null && entries.size() > 0)
/*     */     {
/* 229 */       for (TextureEntry entry : entries)
/*     */       {
/* 231 */         entry.load(r);
/*     */       }
/*     */     }
/* 234 */     List<SpecialEntry> special = specialTexturesToLoad.get(Integer.valueOf(r.func_130086_a()));
/* 235 */     if (special != null && special.size() > 0)
/*     */     {
/* 237 */       for (SpecialEntry entry : special)
/*     */       {
/* 239 */         entry.load(r);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class TextureEntry
/*     */   {
/*     */     String sheetID;
/*     */     int xMin;
/*     */     int xMax;
/*     */     int yMin;
/*     */     int yMax;
/*     */     
/*     */     public TextureEntry(String icon, int x, int y, int xM, int yM) {
/* 254 */       this.sheetID = icon;
/* 255 */       this.xMin = x;
/* 256 */       this.xMax = xM;
/* 257 */       this.yMin = y;
/* 258 */       this.yMax = yM;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void load(TextureMap par1) {
/* 265 */       Ic2Icons.SpriteData sprite = Ic2Icons.iconIDToData.get(this.sheetID);
/* 266 */       for (int y = this.yMin; y < this.yMax; y++) {
/*     */         
/* 268 */         for (int x = this.xMin; x < this.xMax; x++) {
/*     */           
/* 270 */           int pos = x + y * sprite.info.maxX;
/* 271 */           Ic2Icons.load(par1, sprite, x, y);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SpecialEntry
/*     */   {
/*     */     String sheetID;
/*     */     int xCoord;
/*     */     int yCoord;
/*     */     int modify;
/*     */     
/*     */     public SpecialEntry(String icon, int x, int y, int mod) {
/* 286 */       this.sheetID = icon;
/* 287 */       this.xCoord = x;
/* 288 */       this.yCoord = y;
/* 289 */       this.modify = mod;
/*     */     }
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void load(TextureMap par1) {
/* 295 */       Ic2Icons.loadSpecial(par1, Ic2Icons.iconIDToData.get(this.sheetID), this.xCoord, this.yCoord, this.modify);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SpriteData
/*     */   {
/*     */     String spriteID;
/*     */     String texture;
/*     */     Ic2Icons.SpriteInfo info;
/*     */     
/*     */     public SpriteData(String spriteID, String texture, Ic2Icons.SpriteInfo info) {
/* 307 */       this.spriteID = spriteID;
/* 308 */       this.texture = texture;
/* 309 */       this.info = info;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SpriteInfo
/*     */   {
/*     */     int maxX;
/*     */     int maxY;
/*     */     int spriteAmount;
/*     */     
/*     */     public SpriteInfo(int maxX, int maxY) {
/* 321 */       this.maxX = maxX;
/* 322 */       this.maxY = maxY;
/* 323 */       this.spriteAmount = maxX * maxY;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\Ic2Icons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */