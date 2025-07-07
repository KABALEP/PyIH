/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.crops.BaseSeed;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ 
/*     */ 
/*     */ public class IC2Crops
/*     */   extends Crops
/*     */ {
/*  31 */   private final Map<BiomeDictionary.Type, Integer> humidityBiomeBonus = new HashMap<>();
/*  32 */   private final Map<BiomeDictionary.Type, Integer> nutrientBiomeBonus = new HashMap<>();
/*  33 */   private final Map<ItemStack, BaseSeed> baseSeeds = new HashMap<>();
/*  34 */   private final HashMap<String, HashMap<String, CropCard>> mapCrops = new HashMap<>();
/*  35 */   private Map<ResourceLocation, ItemStack> cardItems = new LinkedHashMap<>();
/*     */   
/*  37 */   private final CropCard[] crops = new CropCard[32767];
/*     */   private boolean loaded = false;
/*  39 */   private int size = 0;
/*     */   
/*  41 */   public static CropCard cropWheat = new CropWheat();
/*  42 */   public static CropCard cropPumpkin = new CropPumpkin();
/*  43 */   public static CropCard cropMelon = new CropMelon();
/*  44 */   public static CropCard cropYellowFlower = new CropColorFlower("Dandelion", new String[] { "Yellow", "Flower" }, 15, 11);
/*  45 */   public static CropCard cropRedFlower = new CropColorFlower("Rose", new String[] { "Red", "Flower", "Rose" }, 21, 1);
/*  46 */   public static CropCard cropBlackFlower = new CropColorFlower("Blackthorn", new String[] { "Black", "Flower", "Rose" }, 22, 0);
/*  47 */   public static CropCard cropPurpleFlower = new CropColorFlower("Tulip", new String[] { "Purple", "Flower", "Tulip" }, 23, 5);
/*  48 */   public static CropCard cropBlueFlower = new CropColorFlower("Cyazint", new String[] { "Blue", "Flower" }, 24, 6);
/*  49 */   public static CropCard cropVenomilia = new CropVenomilia();
/*  50 */   public static CropCard cropReed = new CropReed();
/*  51 */   public static CropCard cropStickReed = new CropStickReed();
/*  52 */   public static CropCard cropCocoa = new CropCocoa();
/*  53 */   public static CropCard cropFerru = new CropFerru();
/*  54 */   public static CropCard cropAurelia = new CropAurelia();
/*  55 */   public static CropCard cropRedwheat = new CropRedWheat();
/*  56 */   public static CropCard cropNetherWart = new CropNetherWart();
/*  57 */   public static CropCard cropTerraWart = new CropTerraWart();
/*  58 */   public static CropCard cropCoffee = new CropCoffee();
/*  59 */   public static CropCard cropHops = new CropHops();
/*  60 */   public static CropCard cropCarrots = new CropSeedFood("Carrots", 50, "Orange", new ItemStack(Items.field_151172_bF));
/*  61 */   public static CropCard cropPotato = new CropPotato();
/*  62 */   public static CropCard cropRedMushroom = new CropMushroom(true);
/*  63 */   public static CropCard cropBrownMushroom = new CropMushroom(false);
/*  64 */   public static CropCard cropCacti = new CropCacti();
/*  65 */   public static CropCard cropTea = new CropTea();
/*     */ 
/*     */   
/*     */   public static void init() {
/*  69 */     Crops.instance = new IC2Crops();
/*  70 */     Crops.weed = new CropWeed();
/*     */     
/*  72 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.JUNGLE, 10);
/*  73 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.SWAMP, 10);
/*  74 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.MUSHROOM, 5);
/*  75 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.FOREST, 5);
/*  76 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.RIVER, 2);
/*  77 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.PLAINS, 0);
/*  78 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.SAVANNA, -2);
/*  79 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.HILLS, -5);
/*  80 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.MOUNTAIN, -5);
/*  81 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.WASTELAND, -8);
/*  82 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.END, -10);
/*  83 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.NETHER, -10);
/*  84 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.DEAD, -10);
/*     */     
/*  86 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.SWAMP, 5);
/*  87 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.RIVER, 10);
/*  88 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.SAVANNA, -8);
/*  89 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.WASTELAND, -5);
/*  90 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.END, -7);
/*  91 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.NETHER, -10);
/*     */ 
/*     */     
/*  94 */     registerCrops();
/*  95 */     registerBaseSeeds();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCrops() {
/* 100 */     if (!Crops.instance.registerCrop(weed, 0) || !Crops.instance.registerCrop(cropWheat, 1) || !Crops.instance.registerCrop(cropPumpkin, 2) || !Crops.instance.registerCrop(cropMelon, 3) || !Crops.instance.registerCrop(cropYellowFlower, 4) || !Crops.instance.registerCrop(cropRedFlower, 5) || !Crops.instance.registerCrop(cropBlackFlower, 6) || !Crops.instance.registerCrop(cropPurpleFlower, 7) || !Crops.instance.registerCrop(cropBlueFlower, 8) || !Crops.instance.registerCrop(cropVenomilia, 9) || !Crops.instance.registerCrop(cropReed, 10) || !Crops.instance.registerCrop(cropStickReed, 11) || !Crops.instance.registerCrop(cropCocoa, 12) || !Crops.instance.registerCrop(cropFerru, 13) || !Crops.instance.registerCrop(cropAurelia, 14) || !Crops.instance.registerCrop(cropRedwheat, 15) || !Crops.instance.registerCrop(cropNetherWart, 16) || !Crops.instance.registerCrop(cropTerraWart, 17) || !Crops.instance.registerCrop(cropCoffee, 18) || !Crops.instance.registerCrop(cropHops, 19) || !Crops.instance.registerCrop(cropCarrots, 20) || !Crops.instance.registerCrop(cropPotato, 21) || !Crops.instance.registerCrop(cropRedMushroom, 22) || !Crops.instance.registerCrop(cropBrownMushroom, 23) || !Crops.instance.registerCrop(cropCacti, 24) || !Crops.instance.registerCrop(cropTea, 25))
/*     */     {
/* 102 */       IC2.platform.displayError("One or more crops have failed to initialize.\nThis could happen due to a crop addon using a crop ID already taken\nby a crop from IndustrialCraft 2.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerBaseSeeds() {
/* 108 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151014_N, 1, 32767), cropWheat, 1, 1, 1, 1);
/* 109 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151080_bb, 1, 32767), cropPumpkin, 1, 1, 1, 1);
/* 110 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151081_bc, 1, 32767), cropMelon, 1, 1, 1, 1);
/* 111 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151075_bm, 1, 32767), cropNetherWart, 1, 1, 1, 1);
/* 112 */     Crops.instance.registerBaseSeed(new ItemStack(Ic2Items.terraWart.func_77973_b(), 1, 32767), cropTerraWart, 1, 1, 1, 1);
/* 113 */     Crops.instance.registerBaseSeed(new ItemStack(Ic2Items.coffeeBeans.func_77973_b(), 1, 32767), cropCoffee, 1, 1, 1, 1);
/* 114 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151120_aE, 1, 32767), cropReed, 1, 3, 0, 2);
/* 115 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151100_aR, 1, 3), cropCocoa, 1, 0, 0, 0);
/* 116 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150328_O, 4, 32767), cropRedFlower, 4, 1, 1, 1);
/* 117 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150327_N, 4, 32767), cropYellowFlower, 4, 1, 1, 1);
/* 118 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151172_bF, 1, 32767), cropCarrots, 1, 1, 1, 1);
/* 119 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151174_bG, 1, 32767), cropPotato, 1, 1, 1, 1);
/* 120 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150337_Q, 4, 32767), cropRedMushroom, 3, 1, 1, 1);
/* 121 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150338_P, 4, 32767), cropBrownMushroom, 3, 1, 1, 1);
/* 122 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150434_aF, 1, 32767), cropCacti, 1, 1, 1, 1);
/* 123 */     registerCropDisplayItem(weed, new ItemStack(Items.field_151078_bh));
/* 124 */     registerCropDisplayItem(cropWheat, new ItemStack(Items.field_151015_O));
/* 125 */     registerCropDisplayItem(cropPumpkin, new ItemStack(Items.field_151080_bb));
/* 126 */     registerCropDisplayItem(cropMelon, new ItemStack(Items.field_151081_bc));
/* 127 */     registerCropDisplayItem(cropYellowFlower, new ItemStack(Items.field_151100_aR, 1, 10));
/* 128 */     registerCropDisplayItem(cropRedFlower, new ItemStack(Items.field_151100_aR, 1, 1));
/* 129 */     registerCropDisplayItem(cropBlackFlower, new ItemStack(Items.field_151100_aR, 1, 0));
/* 130 */     registerCropDisplayItem(cropPurpleFlower, new ItemStack(Items.field_151100_aR, 1, 5));
/* 131 */     registerCropDisplayItem(cropBlueFlower, new ItemStack(Items.field_151100_aR, 1, 6));
/* 132 */     registerCropDisplayItem(cropVenomilia, Ic2Items.weedEx.func_77946_l());
/* 133 */     registerCropDisplayItem(cropReed, new ItemStack(Items.field_151120_aE));
/* 134 */     registerCropDisplayItem(cropStickReed, Ic2Items.resin.func_77946_l());
/* 135 */     registerCropDisplayItem(cropCocoa, new ItemStack(Items.field_151100_aR, 1, 3));
/* 136 */     registerCropDisplayItem(cropFerru, Ic2Items.ironDust.func_77946_l());
/* 137 */     registerCropDisplayItem(cropAurelia, Ic2Items.goldDust.func_77946_l());
/* 138 */     registerCropDisplayItem(cropRedwheat, new ItemStack(Items.field_151137_ax));
/* 139 */     registerCropDisplayItem(cropNetherWart, new ItemStack(Items.field_151075_bm));
/* 140 */     registerCropDisplayItem(cropTerraWart, Ic2Items.terraWart.func_77946_l());
/* 141 */     registerCropDisplayItem(cropCoffee, Ic2Items.coffeeBeans.func_77946_l());
/* 142 */     registerCropDisplayItem(cropHops, Ic2Items.hops.func_77946_l());
/* 143 */     registerCropDisplayItem(cropCarrots, new ItemStack(Items.field_151172_bF));
/* 144 */     registerCropDisplayItem(cropPotato, new ItemStack(Items.field_151174_bG));
/* 145 */     registerCropDisplayItem(cropRedMushroom, new ItemStack(Items.field_151009_A));
/* 146 */     registerCropDisplayItem(cropBrownMushroom, new ItemStack(Items.field_151009_A));
/* 147 */     registerCropDisplayItem(cropCacti, new ItemStack(Items.field_151100_aR, 1, 2));
/* 148 */     registerCropDisplayItem(cropTea, Ic2Items.teaLeaf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHumidityBiomeBonus(BiomeGenBase biome) {
/* 154 */     int max = 0;
/* 155 */     for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome)) {
/*     */       
/* 157 */       if (this.humidityBiomeBonus.containsKey(type))
/*     */       {
/* 159 */         max = Math.max(max, ((Integer)this.humidityBiomeBonus.get(type)).intValue());
/*     */       }
/*     */     } 
/* 162 */     return max;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRegisteredCropSize() {
/* 167 */     if (!this.loaded) {
/*     */       
/* 169 */       this.loaded = true;
/* 170 */       int max = 0;
/* 171 */       for (int i = 0; i < this.crops.length; i++) {
/*     */         
/* 173 */         if (this.crops[i] != null)
/*     */         {
/* 175 */           max = Math.max(i, max);
/*     */         }
/*     */       } 
/* 178 */       this.size = max;
/*     */     } 
/* 180 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNutrientBiomeBonus(BiomeGenBase biome) {
/* 186 */     int max = 0;
/* 187 */     for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome)) {
/*     */       
/* 189 */       if (this.nutrientBiomeBonus.containsKey(type))
/*     */       {
/* 191 */         max = Math.max(max, ((Integer)this.nutrientBiomeBonus.get(type)).intValue());
/*     */       }
/*     */     } 
/* 194 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard[] getCropList() {
/* 201 */     return this.crops;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short registerCrop(CropCard crop) {
/* 207 */     for (short x = 0; x < this.crops.length; x = (short)(x + 1)) {
/*     */       
/* 209 */       if (this.crops[x] == null) {
/*     */         
/* 211 */         registerCrop(crop, x);
/* 212 */         return x;
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean registerCrop(CropCard crop, int i) {
/* 222 */     if (i < 0 || i >= this.crops.length)
/*     */     {
/* 224 */       return false;
/*     */     }
/* 226 */     if (this.crops[i] == null) {
/*     */       
/* 228 */       this.crops[i] = crop;
/* 229 */       check(crop.owner());
/* 230 */       ((HashMap<String, CropCard>)this.mapCrops.get(crop.owner())).put(crop.name(), crop);
/* 231 */       return true;
/*     */     } 
/* 233 */     IC2.log.warn("Cannot add crop:" + crop.name() + " on ID #" + i + ", slot already occupied by crop:" + this.crops[i].name());
/* 234 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCropDisplayItem(CropCard card, ItemStack displayItem) {
/* 239 */     ((IC2Crops)instance).registerCropDisplayItemImpl(card, displayItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerCropDisplayItemImpl(CropCard card, ItemStack displayItem) {
/* 244 */     if (card == null || displayItem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     this.cardItems.put(new ResourceLocation(card.owner(), card.name()), displayItem.func_77946_l());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getDisplayItem(CropCard card) {
/* 253 */     return this.cardItems.get(new ResourceLocation(card.owner(), card.name()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean registerBaseSeed(ItemStack stack, int id, int size, int growth, int gain, int resistance) {
/* 259 */     for (Map.Entry<ItemStack, BaseSeed> entry : this.baseSeeds.entrySet()) {
/*     */       
/* 261 */       if (ItemStack.func_77989_b(entry.getKey(), stack))
/*     */       {
/* 263 */         return false;
/*     */       }
/*     */     } 
/* 266 */     this.baseSeeds.put(stack, new BaseSeed(id, size, growth, gain, resistance, stack.field_77994_a));
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSeed getBaseSeed(ItemStack stack) {
/* 273 */     if (stack == null)
/*     */     {
/* 275 */       return null;
/*     */     }
/* 277 */     for (Map.Entry<ItemStack, BaseSeed> entry : this.baseSeeds.entrySet()) {
/*     */       
/* 279 */       ItemStack key = (ItemStack)entry.getKey();
/* 280 */       if (key.func_77973_b() == stack.func_77973_b() && (key.func_77960_j() == 32767 || key.func_77960_j() == stack.func_77960_j()))
/*     */       {
/* 282 */         return this.baseSeeds.get(key);
/*     */       }
/*     */     } 
/* 285 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void startSpriteRegistration(IIconRegister iconRegister) {
/* 293 */     for (int i = 22; i < this.crops.length; i++) {
/*     */       
/* 295 */       CropCard cropCard = this.crops[i];
/* 296 */       if (cropCard != null)
/*     */       {
/* 298 */         cropCard.registerSprites(iconRegister);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdFor(CropCard crop) {
/* 307 */     for (int i = 0; i < this.crops.length; i++) {
/*     */       
/* 309 */       if (this.crops[i] == crop)
/* 310 */         return i; 
/*     */     } 
/* 312 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBiomenutrientsBonus(BiomeDictionary.Type type, int nutrientsBonus) {
/* 318 */     this.nutrientBiomeBonus.put(type, Integer.valueOf(nutrientsBonus));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBiomehumidityBonus(BiomeDictionary.Type type, int humidityBonus) {
/* 324 */     this.humidityBiomeBonus.put(type, Integer.valueOf(humidityBonus));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard getCropCard(String owner, String name) {
/* 330 */     check(owner);
/* 331 */     return (CropCard)((HashMap)this.mapCrops.get(owner)).get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard getCropCard(ItemStack stack) {
/* 337 */     if (!stack.func_77942_o()) {
/* 338 */       return null;
/*     */     }
/* 340 */     NBTTagCompound nbt = stack.func_77978_p();
/*     */     
/* 342 */     if (nbt.func_74764_b("owner") && nbt.func_74764_b("name"))
/*     */     {
/* 344 */       return getCropCard(nbt.func_74779_i("owner"), nbt.func_74779_i("name"));
/*     */     }
/* 346 */     if (nbt.func_74764_b("id"))
/*     */     {
/* 348 */       return this.crops[nbt.func_74765_d("id")];
/*     */     }
/* 350 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<CropCard> getCrops() {
/* 356 */     ArrayList<CropCard> cards = new ArrayList<>();
/* 357 */     cards.addAll(Arrays.asList(this.crops));
/* 358 */     return cards;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean registerBaseSeed(ItemStack stack, CropCard crop, int size, int growth, int gain, int resistance) {
/* 364 */     for (Map.Entry<ItemStack, BaseSeed> entry : this.baseSeeds.entrySet()) {
/*     */       
/* 366 */       if (StackUtil.isStackEqual(stack, entry.getKey()))
/*     */       {
/* 368 */         return false;
/*     */       }
/*     */     } 
/* 371 */     this.baseSeeds.put(stack, new BaseSeed(crop, size, growth, gain, resistance, stack.field_77994_a));
/* 372 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void check(String owner) {
/* 377 */     if (!this.mapCrops.containsKey(owner))
/*     */     {
/* 379 */       this.mapCrops.put(owner, new HashMap<>());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static CropCard getCropFromId(int id) {
/* 385 */     CropCard[] crops = Crops.instance.getCropList();
/* 386 */     if (id < 0 || id >= crops.length)
/*     */     {
/* 388 */       return null;
/*     */     }
/* 390 */     return crops[id];
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\IC2Crops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */