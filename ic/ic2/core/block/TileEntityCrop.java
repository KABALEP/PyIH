/*      */ package ic2.core.block;
/*      */ 
/*      */ import ic2.api.crops.BaseSeed;
/*      */ import ic2.api.crops.CropCard;
/*      */ import ic2.api.crops.Crops;
/*      */ import ic2.api.crops.ICropTile;
/*      */ import ic2.api.network.INetworkDataProvider;
/*      */ import ic2.api.network.INetworkUpdateListener;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.Ic2Icons;
/*      */ import ic2.core.Ic2Items;
/*      */ import ic2.core.block.crop.IC2Crops;
/*      */ import ic2.core.item.ItemCropSeed;
/*      */ import ic2.core.network.NetworkManager;
/*      */ import ic2.core.util.StackUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.ChunkCoordinates;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.IIcon;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.oredict.OreDictionary;
/*      */ 
/*      */ public class TileEntityCrop
/*      */   extends TileEntity
/*      */   implements INetworkDataProvider, INetworkUpdateListener, ICropTile
/*      */ {
/*   43 */   public int growthPoints = 0;
/*      */   public boolean upgraded = false;
/*      */   public char ticker;
/*      */   public boolean dirty = true;
/*   47 */   public static char tickRate = 'Ā';
/*      */   private boolean created = false;
/*   49 */   public byte humidity = -1;
/*   50 */   public byte nutrients = -1;
/*   51 */   public byte airQuality = -1;
/*      */   public boolean speedup = false;
/*   53 */   public int speedLeft = 0;
/*      */ 
/*      */   
/*   56 */   private int id = -1;
/*   57 */   private byte size = 0;
/*   58 */   private byte statGrowth = 0;
/*   59 */   private byte statGain = 0;
/*   60 */   private byte statResistance = 0;
/*   61 */   private NBTTagCompound customData = new NBTTagCompound();
/*   62 */   private int nutrientStorage = 0;
/*   63 */   private int waterStorage = 0;
/*   64 */   private byte scanLevel = 0;
/*   65 */   private int exStorage = 0;
/*   66 */   public static HashMap<Block, List<Block>> blockStorage = new HashMap<>();
/*      */ 
/*      */   
/*      */   public TileEntityCrop() {
/*   70 */     this.ticker = (char)IC2.random.nextInt(tickRate);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*   75 */     super.func_145839_a(nbttagcompound);
/*   76 */     this.id = nbttagcompound.func_74762_e("cropid");
/*   77 */     this.size = nbttagcompound.func_74771_c("size");
/*   78 */     this.statGrowth = nbttagcompound.func_74771_c("statGrowth");
/*   79 */     this.statGain = nbttagcompound.func_74771_c("statGain");
/*   80 */     this.statResistance = nbttagcompound.func_74771_c("statResistance");
/*   81 */     if (nbttagcompound.func_74764_b("customData"))
/*      */     {
/*   83 */       this.customData = nbttagcompound.func_74775_l("customData");
/*      */     }
/*   85 */     this.growthPoints = nbttagcompound.func_74762_e("growthPoints");
/*   86 */     this.nutrientStorage = nbttagcompound.func_74762_e("nutrientStorage");
/*   87 */     this.waterStorage = nbttagcompound.func_74762_e("waterStorage");
/*   88 */     this.upgraded = nbttagcompound.func_74767_n("upgraded");
/*   89 */     this.scanLevel = nbttagcompound.func_74771_c("scanLevel");
/*   90 */     this.speedup = nbttagcompound.func_74767_n("speedUps");
/*   91 */     this.speedLeft = nbttagcompound.func_74762_e("SpeedTicks");
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*   96 */     super.func_145841_b(nbttagcompound);
/*   97 */     nbttagcompound.func_74768_a("cropid", this.id);
/*   98 */     nbttagcompound.func_74774_a("size", this.size);
/*   99 */     nbttagcompound.func_74774_a("statGrowth", this.statGrowth);
/*  100 */     nbttagcompound.func_74774_a("statGain", this.statGain);
/*  101 */     nbttagcompound.func_74774_a("statResistance", this.statResistance);
/*  102 */     nbttagcompound.func_74782_a("customData", (NBTBase)this.customData);
/*  103 */     nbttagcompound.func_74768_a("growthPoints", this.growthPoints);
/*  104 */     nbttagcompound.func_74768_a("nutrientStorage", this.nutrientStorage);
/*  105 */     nbttagcompound.func_74768_a("waterStorage", this.waterStorage);
/*  106 */     nbttagcompound.func_74757_a("upgraded", this.upgraded);
/*  107 */     nbttagcompound.func_74774_a("scanLevel", this.scanLevel);
/*  108 */     nbttagcompound.func_74757_a("speedUps", this.speedup);
/*  109 */     nbttagcompound.func_74768_a("SpeedTicks", this.speedLeft);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145845_h() {
/*  114 */     super.func_145845_h();
/*  115 */     if (!this.created && !IC2.platform.isSimulating()) {
/*      */       
/*  117 */       ((NetworkManager)IC2.network.get()).requestInitialData(this);
/*  118 */       this.created = true;
/*      */     } 
/*      */     
/*  121 */     if (!this.speedup || (this.speedup && this.speedLeft > 0)) {
/*      */       
/*  123 */       this.ticker = (char)(this.ticker + 1);
/*  124 */       if (this.ticker % tickRate == 0 || this.speedLeft > 0) {
/*      */         
/*  126 */         if (this.speedLeft > 0)
/*      */         {
/*  128 */           this.speedLeft--;
/*      */         }
/*  130 */         tick();
/*      */       } 
/*      */     } 
/*  133 */     if (this.dirty) {
/*      */       
/*  135 */       this.dirty = false;
/*  136 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  137 */       this.field_145850_b.func_147463_c(EnumSkyBlock.Block, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  138 */       if (IC2.platform.isSimulating()) {
/*      */         
/*  140 */         ((NetworkManager)IC2.network.get()).announceBlockUpdate(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  141 */         if (!IC2.platform.isRendering())
/*      */         {
/*  143 */           for (String field : getNetworkedFields())
/*      */           {
/*  145 */             ((NetworkManager)IC2.network.get()).updateTileEntityField(this, field);
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getNetworkedFields() {
/*  155 */     List<String> ret = new Vector<>(2);
/*  156 */     ret.add("id");
/*  157 */     ret.add("size");
/*  158 */     ret.add("upgraded");
/*  159 */     ret.add("customData");
/*  160 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  165 */     if (!IC2.platform.isSimulating()) {
/*      */       return;
/*      */     }
/*      */     
/*  169 */     if (this.ticker % (tickRate << 2) == 0)
/*      */     {
/*  171 */       this.humidity = updateHumidity();
/*      */     }
/*  173 */     if ((this.ticker + tickRate) % (tickRate << 2) == 0)
/*      */     {
/*  175 */       this.nutrients = updateNutrients();
/*      */     }
/*  177 */     if ((this.ticker + tickRate * 2) % (tickRate << 2) == 0)
/*      */     {
/*  179 */       this.airQuality = updateAirQuality();
/*      */     }
/*  181 */     if (this.id < 0 && (!this.upgraded || !attemptCrossing())) {
/*      */       
/*  183 */       if (IC2.random.nextInt(100) != 0 || hasEx()) {
/*      */         
/*  185 */         if (this.exStorage > 0 && IC2.random.nextInt(10) == 0)
/*      */         {
/*  187 */           this.exStorage--;
/*      */         }
/*      */         return;
/*      */       } 
/*  191 */       reset();
/*  192 */       this.id = (short)IC2Crops.weed.getId();
/*  193 */       this.size = 1;
/*      */     } 
/*  195 */     crop().tick(this);
/*  196 */     if (this.id == -1) {
/*      */       return;
/*      */     }
/*      */     
/*  200 */     if (crop().canGrow(this)) {
/*      */       
/*  202 */       this.growthPoints += calcGrowthRate();
/*  203 */       if (this.id > -1 && this.growthPoints >= crop().growthDuration(this)) {
/*      */         
/*  205 */         this.growthPoints = 0;
/*  206 */         this.size = (byte)(this.size + 1);
/*  207 */         this.dirty = true;
/*      */       }
/*  209 */       else if (this.id == -1) {
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  214 */     if (this.nutrientStorage > 0)
/*      */     {
/*  216 */       this.nutrientStorage--;
/*      */     }
/*  218 */     if (this.waterStorage > 0)
/*      */     {
/*  220 */       this.waterStorage--;
/*      */     }
/*  222 */     if (crop().isWeed(this) && IC2.random.nextInt(50) - this.statGrowth <= 2)
/*      */     {
/*  224 */       generateWeed();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void generateWeed() {
/*  230 */     int x = this.field_145851_c;
/*  231 */     int y = this.field_145848_d;
/*  232 */     int z = this.field_145849_e;
/*  233 */     switch (IC2.random.nextInt(8)) {
/*      */ 
/*      */       
/*      */       case 0:
/*  237 */         x++;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  242 */         x--;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  247 */         z++;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  252 */         z--;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  257 */         x++;
/*  258 */         z++;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/*  263 */         x--;
/*  264 */         z--;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/*  269 */         x++;
/*  270 */         z--;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 7:
/*  275 */         x--;
/*  276 */         z++;
/*      */         break;
/*      */     } 
/*      */     
/*  280 */     if (this.field_145850_b.func_147438_o(x, y, z) instanceof TileEntityCrop) {
/*      */       
/*  282 */       TileEntityCrop crop = (TileEntityCrop)this.field_145850_b.func_147438_o(x, y, z);
/*  283 */       if (crop.id == -1 || (!crop.crop().isWeed(crop) && IC2.random.nextInt(32) >= crop.statResistance && !crop.hasEx()))
/*      */       {
/*  285 */         byte newGrowth = this.statGrowth;
/*  286 */         if (crop.statGrowth > newGrowth)
/*      */         {
/*  288 */           newGrowth = crop.statGrowth;
/*      */         }
/*  290 */         if (newGrowth < 31 && IC2.random.nextBoolean())
/*      */         {
/*  292 */           newGrowth = (byte)(newGrowth + 1);
/*      */         }
/*  294 */         crop.reset();
/*  295 */         crop.id = 0;
/*  296 */         crop.setSize((byte)1);
/*  297 */         crop.statGrowth = newGrowth;
/*      */       }
/*      */     
/*  300 */     } else if (this.field_145850_b.func_147437_c(x, y, z)) {
/*      */       
/*  302 */       Block block = this.field_145850_b.func_147439_a(x, y - 1, z);
/*  303 */       if (block == Blocks.field_150346_d || block == Blocks.field_150349_c || block == Blocks.field_150458_ak) {
/*      */         
/*  305 */         this.field_145850_b.func_147449_b(x, y - 1, z, (Block)Blocks.field_150349_c);
/*  306 */         this.field_145850_b.func_147465_d(x, y, z, (Block)Blocks.field_150329_H, 1, 3);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEx() {
/*  313 */     if (this.exStorage > 0) {
/*      */       
/*  315 */       this.exStorage -= 5;
/*  316 */       return true;
/*      */     } 
/*  318 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attemptCrossing() {
/*  323 */     if (IC2.random.nextInt(3) != 0)
/*      */     {
/*  325 */       return false;
/*      */     }
/*  327 */     LinkedList<TileEntityCrop> crops = new LinkedList<>();
/*  328 */     askCropJoinCross(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, crops);
/*  329 */     askCropJoinCross(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, crops);
/*  330 */     askCropJoinCross(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, crops);
/*  331 */     askCropJoinCross(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, crops);
/*  332 */     if (crops.size() < 2)
/*      */     {
/*  334 */       return false;
/*      */     }
/*  336 */     int[] ratios = new int[((IC2Crops)Crops.instance).getRegisteredCropSize()];
/*  337 */     for (int i = 1; i < ratios.length; i++) {
/*      */       
/*  339 */       CropCard crop = Crops.instance.getCropList()[i];
/*  340 */       if (crop != null && crop.canGrow(this))
/*      */       {
/*  342 */         for (int j = 0; j < crops.size(); j++) {
/*      */           
/*  344 */           int[] array = ratios;
/*  345 */           int n = i;
/*  346 */           array[n] = array[n] + calculateRatioFor(crop, ((TileEntityCrop)crops.get(j)).crop());
/*      */         } 
/*      */       }
/*      */     } 
/*  350 */     int total = 0; int k;
/*  351 */     for (k = 0; k < ratios.length; k++)
/*      */     {
/*  353 */       total += ratios[k];
/*      */     }
/*  355 */     total = IC2.random.nextInt(total);
/*  356 */     for (k = 0; k < ratios.length; k++) {
/*      */       
/*  358 */       if (ratios[k] > 0 && ratios[k] > total) {
/*      */         
/*  360 */         total = k;
/*      */         break;
/*      */       } 
/*  363 */       total -= ratios[k];
/*      */     } 
/*  365 */     this.upgraded = false;
/*  366 */     this.id = (short)total;
/*  367 */     this.dirty = true;
/*  368 */     this.size = 1;
/*  369 */     this.statGrowth = 0;
/*  370 */     this.statResistance = 0;
/*  371 */     this.statGain = 0;
/*  372 */     for (k = 0; k < crops.size(); k++) {
/*      */       
/*  374 */       this.statGrowth = (byte)(this.statGrowth + ((TileEntityCrop)crops.get(k)).statGrowth);
/*  375 */       this.statResistance = (byte)(this.statResistance + ((TileEntityCrop)crops.get(k)).statResistance);
/*  376 */       this.statGain = (byte)(this.statGain + ((TileEntityCrop)crops.get(k)).statGain);
/*      */     } 
/*  378 */     int count = crops.size();
/*  379 */     this.statGrowth = (byte)(this.statGrowth / (byte)count);
/*  380 */     this.statResistance = (byte)(this.statResistance / (byte)count);
/*  381 */     this.statGain = (byte)(this.statGain / (byte)count);
/*  382 */     this.statGrowth = (byte)(this.statGrowth + (byte)(IC2.random.nextInt(1 + 2 * count) - count));
/*  383 */     if (this.statGrowth < 0)
/*      */     {
/*  385 */       this.statGrowth = 0;
/*      */     }
/*  387 */     if (this.statGrowth > 31)
/*      */     {
/*  389 */       this.statGrowth = 31;
/*      */     }
/*  391 */     this.statGain = (byte)(this.statGain + (byte)(IC2.random.nextInt(1 + 2 * count) - count));
/*  392 */     if (this.statGain < 0)
/*      */     {
/*  394 */       this.statGain = 0;
/*      */     }
/*  396 */     if (this.statGain > 31)
/*      */     {
/*  398 */       this.statGain = 31;
/*      */     }
/*  400 */     this.statResistance = (byte)(this.statResistance + (byte)(IC2.random.nextInt(1 + 2 * count) - count));
/*  401 */     if (this.statResistance < 0)
/*      */     {
/*  403 */       this.statResistance = 0;
/*      */     }
/*  405 */     if (this.statResistance > 31)
/*      */     {
/*  407 */       this.statResistance = 31;
/*      */     }
/*  409 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int calculateRatioFor(CropCard a, CropCard b) {
/*  414 */     if (a == b)
/*      */     {
/*  416 */       return 500;
/*      */     }
/*  418 */     int value = 0;
/*  419 */     int i = 0;
/*  420 */     while (i < 5) {
/*      */       
/*  422 */       int c = a.stat(i) - b.stat(i);
/*  423 */       if (c < 0)
/*      */       {
/*  425 */         c *= -1;
/*      */       }
/*  427 */       switch (c) {
/*      */ 
/*      */         
/*      */         default:
/*  431 */           value--;
/*      */ 
/*      */         
/*      */         case 0:
/*  435 */           value += 2;
/*      */ 
/*      */         
/*      */         case 1:
/*  439 */           value++; break;
/*      */         case 2:
/*      */           break;
/*      */       } 
/*  443 */       i++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  448 */     for (i = 0; i < (a.attributes()).length; i++) {
/*      */       
/*  450 */       for (int j = 0; j < (b.attributes()).length; j++) {
/*      */         
/*  452 */         if (a.attributes()[i].equalsIgnoreCase(b.attributes()[j]))
/*      */         {
/*  454 */           value += 5;
/*      */         }
/*      */       } 
/*      */     } 
/*  458 */     if (b.tier() < a.tier() - 1)
/*      */     {
/*  460 */       value -= 2 * (a.tier() - b.tier());
/*      */     }
/*  462 */     if (b.tier() - 3 > a.tier())
/*      */     {
/*  464 */       value -= b.tier() - a.tier();
/*      */     }
/*  466 */     if (value < 0)
/*      */     {
/*  468 */       value = 0;
/*      */     }
/*  470 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public void askCropJoinCross(int x, int y, int z, LinkedList<TileEntityCrop> crops) {
/*  475 */     if (!(this.field_145850_b.func_147438_o(x, y, z) instanceof TileEntityCrop)) {
/*      */       return;
/*      */     }
/*      */     
/*  479 */     TileEntityCrop sidecrop = (TileEntityCrop)this.field_145850_b.func_147438_o(x, y, z);
/*  480 */     if (sidecrop.id <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  484 */     if (!sidecrop.crop().canGrow(this) || !sidecrop.crop().canCross(sidecrop)) {
/*      */       return;
/*      */     }
/*      */     
/*  488 */     int base = 4;
/*  489 */     if (sidecrop.statGrowth >= 16)
/*      */     {
/*  491 */       base++;
/*      */     }
/*  493 */     if (sidecrop.statGrowth >= 30)
/*      */     {
/*  495 */       base++;
/*      */     }
/*  497 */     if (sidecrop.statResistance >= 28)
/*      */     {
/*  499 */       base += 27 - sidecrop.statResistance;
/*      */     }
/*  501 */     if (base >= IC2.random.nextInt(20))
/*      */     {
/*  503 */       crops.add(sidecrop);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean leftclick(EntityPlayer player) {
/*  509 */     if (this.id >= 0)
/*      */     {
/*  511 */       return crop().leftclick(this, player);
/*      */     }
/*  513 */     if (this.upgraded) {
/*      */       
/*  515 */       this.upgraded = false;
/*  516 */       this.dirty = true;
/*  517 */       if (IC2.platform.isSimulating())
/*      */       {
/*  519 */         StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, new ItemStack(Ic2Items.crop.func_77973_b()));
/*      */       }
/*  521 */       return true;
/*      */     } 
/*  523 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pick(boolean manual) {
/*  529 */     if (this.id < 0)
/*      */     {
/*  531 */       return false;
/*      */     }
/*  533 */     boolean bonus = harvest(false);
/*  534 */     float firstchance = crop().dropSeedChance(this);
/*  535 */     for (int i = 0; i < this.statResistance; i++)
/*      */     {
/*  537 */       firstchance *= 1.1F;
/*      */     }
/*  539 */     int drop = 0;
/*  540 */     if (bonus) {
/*      */       
/*  542 */       if (IC2.random.nextFloat() <= (firstchance + 1.0F) * 0.8F)
/*      */       {
/*  544 */         drop++;
/*      */       }
/*  546 */       float chance = crop().dropSeedChance(this) + this.statGrowth / 100.0F;
/*  547 */       if (!manual)
/*      */       {
/*  549 */         chance *= 0.8F;
/*      */       }
/*  551 */       for (int k = 23; k < this.statGain; k++)
/*      */       {
/*  553 */         chance *= 0.95F;
/*      */       }
/*  555 */       if (IC2.random.nextFloat() <= chance)
/*      */       {
/*  557 */         drop++;
/*      */       }
/*      */     }
/*  560 */     else if (IC2.random.nextFloat() <= firstchance * 1.5F) {
/*      */       
/*  562 */       drop++;
/*      */     } 
/*  564 */     ItemStack[] re = new ItemStack[drop];
/*  565 */     for (int j = 0; j < drop; j++)
/*      */     {
/*  567 */       re[j] = crop().getSeeds(this);
/*      */     }
/*  569 */     reset();
/*  570 */     if (IC2.platform.isSimulating() && re != null && re.length > 0)
/*      */     {
/*  572 */       for (int x = 0; x < re.length; x++) {
/*      */         
/*  574 */         if (re[x].func_77973_b() != Ic2Items.cropSeed.func_77973_b())
/*      */         {
/*  576 */           (re[x]).field_77990_d = null;
/*      */         }
/*  578 */         StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, re[x]);
/*      */       } 
/*      */     }
/*  581 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rightclick(EntityPlayer player) {
/*  586 */     ItemStack current = player.func_71045_bC();
/*  587 */     if (current != null) {
/*      */       
/*  589 */       if (this.id < 0) {
/*      */         
/*  591 */         if (current.func_77973_b() == Ic2Items.crop.func_77973_b() && !this.upgraded) {
/*      */           
/*  593 */           if (!player.field_71075_bZ.field_75098_d) {
/*      */             
/*  595 */             ItemStack itemStack = current;
/*  596 */             itemStack.field_77994_a--;
/*  597 */             if (current.field_77994_a <= 0)
/*      */             {
/*  599 */               player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */             }
/*      */           } 
/*  602 */           this.upgraded = true;
/*  603 */           return this.dirty = true;
/*      */         } 
/*  605 */         if (applyBaseSeed(current, player))
/*      */         {
/*  607 */           return true;
/*      */         }
/*      */       }
/*  610 */       else if (current.func_77973_b() == Ic2Items.cropnalyzer.func_77973_b()) {
/*      */         
/*  612 */         if (IC2.platform.isSimulating())
/*      */         {
/*  614 */           if (player.func_70093_af()) {
/*      */             
/*  616 */             if (this.scanLevel >= 4 || this.id == -1)
/*      */             {
/*  618 */               IC2.platform.messagePlayer(player, "Crop is already Fully Analyzed");
/*      */ 
/*      */             
/*      */             }
/*  622 */             else if (this.scanLevel < 4)
/*      */             {
/*  624 */               this.scanLevel = (byte)(this.scanLevel + 1);
/*  625 */               IC2.platform.messagePlayer(player, "Scanning Level increased to " + this.scanLevel);
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*  631 */           else if (this.id == -1 || this.size <= 0 || (this.scanLevel < 1 && this.size < crop().maxSize())) {
/*      */             
/*  633 */             IC2.platform.messagePlayer(player, EnumChatFormatting.WHITE + StatCollector.func_74838_a("container.cropAnalyzer.CropUnknowen.name"));
/*  634 */             IC2.platform.messagePlayer(player, EnumChatFormatting.GREEN + "Nutrient Storage: " + this.nutrientStorage + " / " + 'd');
/*  635 */             IC2.platform.messagePlayer(player, EnumChatFormatting.BLUE + "Water Storage: " + this.waterStorage + " / " + 'È');
/*  636 */             IC2.platform.messagePlayer(player, EnumChatFormatting.RED + "WeedEx Storage: " + this.exStorage + " / " + '');
/*  637 */             IC2.platform.messagePlayer(player, EnumChatFormatting.DARK_GREEN + "Crop Nutriens: " + getNutrients());
/*  638 */             IC2.platform.messagePlayer(player, EnumChatFormatting.AQUA + "Crop Humidity: " + getHumidity());
/*  639 */             IC2.platform.messagePlayer(player, EnumChatFormatting.YELLOW + "Air Quality: " + getAirQuality());
/*      */           }
/*      */           else {
/*      */             
/*  643 */             IC2.platform.messagePlayer(player, EnumChatFormatting.WHITE + "Crop: " + crop().displayName() + ", By: " + crop().discoveredBy());
/*  644 */             IC2.platform.messagePlayer(player, EnumChatFormatting.GREEN + "Size: " + this.size + " / " + crop().maxSize());
/*  645 */             IC2.platform.messagePlayer(player, EnumChatFormatting.GREEN + "Nutrient Storage: " + this.nutrientStorage + " / " + 'd');
/*  646 */             IC2.platform.messagePlayer(player, EnumChatFormatting.BLUE + "Water Storage: " + this.waterStorage + " / " + 'È');
/*  647 */             IC2.platform.messagePlayer(player, EnumChatFormatting.RED + "WeedEx Storage: " + this.exStorage + " / " + '');
/*  648 */             IC2.platform.messagePlayer(player, EnumChatFormatting.DARK_GREEN + "Crop Nutriens: " + getNutrients());
/*  649 */             IC2.platform.messagePlayer(player, EnumChatFormatting.AQUA + "Crop Humidity: " + getHumidity());
/*  650 */             IC2.platform.messagePlayer(player, EnumChatFormatting.YELLOW + "Air Quality: " + getAirQuality());
/*  651 */             IC2.platform.messagePlayer(player, EnumChatFormatting.GREEN + "Growth: " + this.statGrowth);
/*  652 */             IC2.platform.messagePlayer(player, EnumChatFormatting.YELLOW + "Gain: " + this.statGain);
/*  653 */             IC2.platform.messagePlayer(player, EnumChatFormatting.BLUE + "Resistance: " + this.statResistance);
/*      */           } 
/*      */         }
/*      */         
/*  657 */         return true;
/*      */       } 
/*  659 */       if (current.func_77973_b() == Items.field_151131_as) {
/*      */         
/*  661 */         if (this.waterStorage < 10) {
/*      */           
/*  663 */           this.waterStorage = 10;
/*  664 */           current.field_77994_a--;
/*  665 */           if (current.field_77994_a <= 0) {
/*      */             
/*  667 */             player.func_70062_b(0, new ItemStack(Items.field_151133_ar));
/*      */           }
/*      */           else {
/*      */             
/*  671 */             player.field_71071_by.func_70441_a(new ItemStack(Items.field_151133_ar));
/*      */           } 
/*      */         } 
/*  674 */         return true;
/*      */       } 
/*  676 */       if (current.func_77973_b() == Ic2Items.waterCell.func_77973_b()) {
/*      */         
/*  678 */         if (this.waterStorage < 10) {
/*      */           
/*  680 */           this.waterStorage = 10;
/*  681 */           current.field_77994_a--;
/*  682 */           if (current.field_77994_a <= 0) {
/*      */             
/*  684 */             player.func_70062_b(0, Ic2Items.cell.func_77946_l());
/*      */           }
/*      */           else {
/*      */             
/*  688 */             player.field_71071_by.func_70441_a(Ic2Items.cell.func_77946_l());
/*      */           } 
/*      */         } 
/*  691 */         return true;
/*      */       } 
/*  693 */       if (current.func_77973_b() == Items.field_151014_N) {
/*      */         
/*  695 */         if (this.nutrientStorage <= 50) {
/*      */           
/*  697 */           this.nutrientStorage += 25;
/*  698 */           ItemStack itemStack2 = current;
/*  699 */           itemStack2.field_77994_a--;
/*  700 */           if (current.field_77994_a <= 0)
/*      */           {
/*  702 */             player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */           }
/*  704 */           return true;
/*      */         } 
/*  706 */         return false;
/*      */       } 
/*  708 */       if ((current.func_77973_b() == Items.field_151100_aR && current.func_77960_j() == 15) || current.func_77973_b() == Ic2Items.fertilizer.func_77973_b()) {
/*      */         
/*  710 */         if (applyFertilizer(true)) {
/*      */           
/*  712 */           ItemStack itemStack3 = current;
/*  713 */           itemStack3.field_77994_a--;
/*  714 */           if (current.field_77994_a <= 0)
/*      */           {
/*  716 */             player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */           }
/*  718 */           return true;
/*      */         } 
/*  720 */         return false;
/*      */       } 
/*  722 */       if (current.func_77973_b() == Ic2Items.hydratingCell.func_77973_b()) {
/*      */         
/*  724 */         if (applyHydration(true, current)) {
/*      */           
/*  726 */           if (current.field_77994_a <= 0)
/*      */           {
/*  728 */             player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */           }
/*  730 */           return true;
/*      */         } 
/*  732 */         return false;
/*      */       } 
/*  734 */       if (current.func_77973_b() == Ic2Items.weedEx.func_77973_b() && applyWeedEx(true)) {
/*      */         
/*  736 */         current.func_77972_a(1, (EntityLivingBase)player);
/*  737 */         if (current.func_77960_j() >= current.func_77958_k()) {
/*      */           
/*  739 */           ItemStack itemStack4 = current;
/*  740 */           itemStack4.field_77994_a--;
/*      */         } 
/*  742 */         if (current.field_77994_a <= 0)
/*      */         {
/*  744 */           player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */         }
/*  746 */         return true;
/*      */       } 
/*  748 */       if (current.func_77969_a(Ic2Items.specialFertilzer)) {
/*      */         
/*  750 */         if (this.speedup)
/*      */         {
/*  752 */           return false;
/*      */         }
/*  754 */         current.field_77994_a--;
/*  755 */         if (current.field_77994_a <= 0)
/*      */         {
/*  757 */           player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */         }
/*  759 */         this.speedup = true;
/*  760 */         this.speedLeft = 6000;
/*  761 */         return true;
/*      */       } 
/*      */     } 
/*  764 */     return (this.id >= 0 && crop().rightclick(this, player));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean applyBaseSeed(ItemStack current, EntityPlayer player) {
/*  769 */     BaseSeed seed = Crops.instance.getBaseSeed(current);
/*  770 */     if (seed != null) {
/*      */       
/*  772 */       if (current.field_77994_a < seed.stackSize)
/*      */       {
/*  774 */         return false;
/*      */       }
/*  776 */       if (tryPlantIn(seed.id, seed.size, seed.statGrowth, seed.statGain, seed.statResistance, 1)) {
/*      */         
/*  778 */         if (current.func_77973_b().hasContainerItem(current)) {
/*      */           
/*  780 */           current = current.func_77973_b().getContainerItem(current);
/*      */         }
/*      */         else {
/*      */           
/*  784 */           ItemStack itemStack = current;
/*  785 */           itemStack.field_77994_a -= seed.stackSize;
/*  786 */           if (current.field_77994_a <= 0)
/*      */           {
/*  788 */             player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*      */           }
/*      */         } 
/*  791 */         return true;
/*      */       } 
/*      */     } 
/*  794 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryPlantIn(int i, int si, int statGr, int statGa, int statRe, int scan) {
/*  800 */     if (this.id > -1 || i <= 0 || this.upgraded)
/*      */     {
/*  802 */       return false;
/*      */     }
/*  804 */     CropCard crop = Crops.instance.getCropList()[i];
/*  805 */     if (crop == null || !crop.canGrow(this))
/*      */     {
/*  807 */       return false;
/*      */     }
/*  809 */     reset();
/*  810 */     this.id = (short)i;
/*  811 */     this.size = (byte)si;
/*  812 */     this.statGrowth = (byte)statGr;
/*  813 */     this.statGain = (byte)statGa;
/*  814 */     this.statResistance = (byte)statRe;
/*  815 */     this.scanLevel = (byte)scan;
/*  816 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean applyFertilizer(boolean manual) {
/*  821 */     if (this.nutrientStorage >= 100)
/*      */     {
/*  823 */       return false;
/*      */     }
/*  825 */     this.nutrientStorage += manual ? 100 : 90;
/*  826 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean applyHydration(boolean manual, ItemStack current) {
/*  831 */     if ((!manual && this.waterStorage >= 180) || this.waterStorage >= 200)
/*      */     {
/*  833 */       return false;
/*      */     }
/*  835 */     int apply = manual ? (200 - this.waterStorage) : (180 - this.waterStorage);
/*  836 */     if (apply + current.func_77960_j() > current.func_77958_k())
/*      */     {
/*  838 */       apply = current.func_77958_k() - current.func_77960_j();
/*      */     }
/*  840 */     current.func_77964_b(current.func_77960_j() + apply);
/*  841 */     if (current.func_77960_j() >= current.func_77958_k())
/*      */     {
/*  843 */       current.field_77994_a--;
/*      */     }
/*  845 */     this.waterStorage += apply;
/*  846 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean applyWeedEx(boolean manual) {
/*  851 */     if ((this.exStorage >= 100 && manual) || this.exStorage >= 150)
/*      */     {
/*  853 */       return false;
/*      */     }
/*  855 */     this.exStorage += 50;
/*  856 */     boolean trigger = (this.field_145850_b.field_73012_v.nextInt(3) == 0);
/*  857 */     if (manual)
/*      */     {
/*  859 */       trigger = (this.field_145850_b.field_73012_v.nextInt(5) == 0);
/*      */     }
/*  861 */     if (this.id > 0 && this.exStorage >= 75 && trigger) {
/*      */       
/*  863 */       switch (this.field_145850_b.field_73012_v.nextInt(5)) {
/*      */ 
/*      */         
/*      */         case 0:
/*  867 */           if (this.statGrowth > 0)
/*      */           {
/*  869 */             this.statGrowth = (byte)(this.statGrowth - 1);
/*      */           }
/*      */ 
/*      */         
/*      */         case 1:
/*  874 */           if (this.statGain > 0)
/*      */           {
/*  876 */             this.statGain = (byte)(this.statGain - 1);
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  882 */       if (this.statResistance > 0)
/*      */       {
/*  884 */         this.statResistance = (byte)(this.statResistance - 1);
/*      */       }
/*      */     } 
/*  887 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean harvest(boolean manual) {
/*  893 */     if (this.id < 0 || !crop().canBeHarvested(this))
/*      */     {
/*  895 */       return false;
/*      */     }
/*  897 */     float chance = crop().dropGainChance();
/*  898 */     for (int i = 0; i < this.statGain; i++)
/*      */     {
/*  900 */       chance *= 1.03F;
/*      */     }
/*  902 */     chance -= IC2.random.nextFloat();
/*  903 */     int drop = 0;
/*  904 */     while (chance > 0.0F) {
/*      */       
/*  906 */       drop++;
/*  907 */       chance -= IC2.random.nextFloat();
/*      */     } 
/*  909 */     ItemStack[] re = new ItemStack[drop];
/*  910 */     for (int j = 0; j < drop; j++) {
/*      */       
/*  912 */       re[j] = crop().getGain(this);
/*  913 */       if (re[j] != null && IC2.random.nextInt(100) <= this.statGain) {
/*      */         
/*  915 */         ItemStack itemStack = re[j];
/*  916 */         itemStack.field_77994_a++;
/*      */       } 
/*      */     } 
/*  919 */     this.size = crop().getSizeAfterHarvest(this);
/*  920 */     this.dirty = true;
/*  921 */     if (IC2.platform.isSimulating() && re != null && re.length > 0)
/*      */     {
/*  923 */       for (int x = 0; x < re.length; x++)
/*      */       {
/*  925 */         StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, re[x]);
/*      */       }
/*      */     }
/*  928 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onNeighbourChange() {
/*  933 */     if (this.id < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  937 */     crop().onNeighbourChange(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean emitRedstone() {
/*  942 */     return (this.id >= 0 && crop().emitRedstone(this) > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onBlockDestroyed() {
/*  947 */     if (this.id < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  951 */     crop().onBlockDestroyed(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEmittedLight() {
/*  956 */     if (this.id < 0)
/*      */     {
/*  958 */       return 0;
/*      */     }
/*  960 */     return crop().getEmittedLight(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getHumidity() {
/*  966 */     if (this.humidity == -1)
/*      */     {
/*  968 */       this.humidity = updateHumidity();
/*      */     }
/*  970 */     return this.humidity;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getNutrients() {
/*  976 */     if (this.nutrients == -1)
/*      */     {
/*  978 */       this.nutrients = updateNutrients();
/*      */     }
/*  980 */     return this.nutrients;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getAirQuality() {
/*  986 */     if (this.airQuality == -1)
/*      */     {
/*  988 */       this.airQuality = updateAirQuality();
/*      */     }
/*  990 */     return this.airQuality;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte updateHumidity() {
/*  995 */     int value = Crops.instance.getHumidityBiomeBonus(this.field_145850_b.func_72959_q().func_76935_a(this.field_145851_c, this.field_145849_e));
/*  996 */     if (this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) >= 7)
/*      */     {
/*  998 */       value += 2;
/*      */     }
/* 1000 */     if (this.waterStorage >= 5)
/*      */     {
/* 1002 */       value += 2;
/*      */     }
/* 1004 */     value += (this.waterStorage + 24) / 25;
/* 1005 */     return (byte)value;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte updateNutrients() {
/* 1010 */     int value = Crops.instance.getNutrientBiomeBonus(this.field_145850_b.func_72959_q().func_76935_a(this.field_145851_c, this.field_145849_e));
/* 1011 */     for (int i = 2; i < 5 && this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - i, this.field_145849_e) == Blocks.field_150346_d; i++)
/*      */     {
/* 1013 */       value++;
/*      */     }
/* 1015 */     value += (this.nutrientStorage + 19) / 20;
/* 1016 */     return (byte)value;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte updateAirQuality() {
/* 1021 */     int value = 0;
/* 1022 */     int height = (this.field_145848_d - 64) / 15;
/* 1023 */     if (height > 4)
/*      */     {
/* 1025 */       height = 4;
/*      */     }
/* 1027 */     if (height < 0)
/*      */     {
/* 1029 */       height = 0;
/*      */     }
/* 1031 */     value += height;
/* 1032 */     int fresh = 9;
/* 1033 */     for (int x = this.field_145851_c - 1; x < this.field_145851_c + 1 && fresh > 0; x++) {
/*      */       
/* 1035 */       for (int z = this.field_145849_e - 1; z < this.field_145849_e + 1 && fresh > 0; z++) {
/*      */         
/* 1037 */         if (this.field_145850_b.func_147445_c(x, this.field_145848_d, z, false) || this.field_145850_b.func_147438_o(x, this.field_145848_d, z) instanceof TileEntityCrop)
/*      */         {
/* 1039 */           fresh--;
/*      */         }
/*      */       } 
/*      */     } 
/* 1043 */     value += fresh / 2;
/* 1044 */     if (this.field_145850_b.func_72937_j(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e))
/*      */     {
/* 1046 */       value += 2;
/*      */     }
/* 1048 */     return (byte)value;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte updateMultiCulture() {
/* 1053 */     LinkedList crops = new LinkedList();
/* 1054 */     for (int x = -1; x < 1; x++) {
/*      */       
/* 1056 */       for (int z = -1; z < 1; z++) {
/*      */         
/* 1058 */         if (this.field_145850_b.func_147438_o(x + this.field_145851_c, this.field_145848_d, z + this.field_145849_e) instanceof TileEntityCrop)
/*      */         {
/* 1060 */           addIfNotPresent(((TileEntityCrop)this.field_145850_b.func_147438_o(x + this.field_145851_c, this.field_145848_d, z + this.field_145849_e)).crop(), crops);
/*      */         }
/*      */       } 
/*      */     } 
/* 1064 */     return (byte)(crops.size() - 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addIfNotPresent(CropCard crop, LinkedList<CropCard> crops) {
/* 1069 */     for (int i = 0; i < crops.size(); i++) {
/*      */       
/* 1071 */       if (crop == crops.get(i)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1076 */     crops.add(crop);
/*      */   }
/*      */ 
/*      */   
/*      */   public int calcGrowthRate() {
/* 1081 */     int base = 3 + IC2.random.nextInt(7) + this.statGrowth;
/* 1082 */     int need = (crop().tier() - 1) * 4 + this.statGrowth + this.statGain + this.statResistance;
/* 1083 */     if (need < 0)
/*      */     {
/* 1085 */       need = 0;
/*      */     }
/* 1087 */     int have = crop().weightInfluences(this, getHumidity(), getNutrients(), getAirQuality()) * 5;
/* 1088 */     if (have >= need) {
/*      */       
/* 1090 */       base = base * (100 + have - need) / 100;
/*      */     }
/*      */     else {
/*      */       
/* 1094 */       int neg = (need - have) * 4;
/* 1095 */       if (neg > 100 && IC2.random.nextInt(32) > this.statResistance) {
/*      */         
/* 1097 */         reset();
/* 1098 */         base = 0;
/*      */       }
/*      */       else {
/*      */         
/* 1102 */         base = base * (100 - neg) / 100;
/* 1103 */         if (base < 0)
/*      */         {
/* 1105 */           base = 0;
/*      */         }
/*      */       } 
/*      */     } 
/* 1109 */     return base;
/*      */   }
/*      */ 
/*      */   
/*      */   public void calcTrampling() {
/* 1114 */     if (!IC2.platform.isSimulating()) {
/*      */       return;
/*      */     }
/*      */     
/* 1118 */     if (IC2.random.nextInt(100) == 0 && IC2.random.nextInt(40) > this.statResistance) {
/*      */       
/* 1120 */       reset();
/* 1121 */       this.field_145850_b.func_147449_b(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, Blocks.field_150346_d);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CropCard crop() {
/* 1127 */     return Crops.instance.getCropList()[this.id];
/*      */   }
/*      */ 
/*      */   
/*      */   public IIcon getSprite() {
/* 1132 */     if (this.id >= 0)
/*      */     {
/* 1134 */       return crop().getSprite(this);
/*      */     }
/* 1136 */     if (!this.upgraded)
/*      */     {
/* 1138 */       return Ic2Icons.getTexture("bc")[0];
/*      */     }
/* 1140 */     return Ic2Icons.getTexture("bc")[1];
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEntityCollision(Entity entity) {
/* 1145 */     if (this.id < 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1149 */     if (crop().onEntityCollision(this, entity))
/*      */     {
/* 1151 */       calcTrampling();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 1158 */     this.id = -1;
/* 1159 */     this.size = 0;
/* 1160 */     this.customData = new NBTTagCompound();
/* 1161 */     this.dirty = true;
/* 1162 */     this.statGain = 0;
/* 1163 */     this.statResistance = 0;
/* 1164 */     this.statGrowth = 0;
/* 1165 */     this.nutrients = -1;
/* 1166 */     this.airQuality = -1;
/* 1167 */     this.humidity = -1;
/* 1168 */     this.growthPoints = 0;
/* 1169 */     this.upgraded = false;
/* 1170 */     this.scanLevel = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateState() {
/* 1176 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getScanned() {
/* 1181 */     if (this.scanLevel <= 0 || this.id < 0)
/*      */     {
/* 1183 */       return StatCollector.func_74838_a("container.cropAnalyzer.CropUnknowen.name");
/*      */     }
/* 1185 */     if (this.scanLevel >= 4)
/*      */     {
/* 1187 */       return StatCollector.func_74837_a("container.cropAnalyzer.Crop.name", new Object[] { crop().displayName(), Byte.valueOf(this.statGrowth), Byte.valueOf(this.statGain), Byte.valueOf(this.statResistance) });
/*      */     }
/* 1189 */     return crop().displayName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockBelow(Block block) {
/* 1195 */     List<Block> list = getOreDict(block);
/* 1196 */     for (int i = 1; i < 4; i++) {
/*      */       
/* 1198 */       Block id = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - i, this.field_145849_e);
/* 1199 */       if (this.field_145850_b.func_147437_c(this.field_145851_c, this.field_145848_d - i, this.field_145849_e))
/*      */       {
/* 1201 */         return false;
/*      */       }
/* 1203 */       if (list.contains(id))
/*      */       {
/* 1205 */         return true;
/*      */       }
/*      */     } 
/* 1208 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockBelow(String oreDictionaryName) {
/* 1215 */     List<ItemStack> stack = OreDictionary.getOres(oreDictionaryName, false);
/* 1216 */     for (int i = 1; i < 4; i++) {
/*      */       
/* 1218 */       Block id = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - i, this.field_145849_e);
/* 1219 */       int meta = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 1220 */       if (this.field_145850_b.func_147437_c(this.field_145851_c, this.field_145848_d - i, this.field_145849_e))
/*      */       {
/* 1222 */         return false;
/*      */       }
/* 1224 */       ItemStack item = new ItemStack(id, 1, meta);
/* 1225 */       if (stack.contains(item))
/*      */       {
/* 1227 */         return true;
/*      */       }
/*      */     } 
/* 1230 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Block> getOreDict(Block par1) {
/* 1235 */     if (blockStorage.containsKey(par1))
/*      */     {
/* 1237 */       return blockStorage.get(par1);
/*      */     }
/* 1239 */     ItemStack item = new ItemStack(par1);
/* 1240 */     ArrayList<Block> results = new ArrayList<>();
/* 1241 */     int[] ints = OreDictionary.getOreIDs(item);
/* 1242 */     if (ints != null && ints.length > 0) {
/*      */       
/* 1244 */       boolean anyMatch = false;
/* 1245 */       for (int id : ints) {
/*      */         
/* 1247 */         if (anyMatch) {
/*      */           break;
/*      */         }
/*      */         
/* 1251 */         for (ItemStack cu : OreDictionary.getOres(Integer.valueOf(id))) {
/*      */           
/* 1253 */           if (cu != null && cu.func_77973_b() != null) {
/*      */             
/* 1255 */             Block block = Block.func_149634_a(cu.func_77973_b());
/* 1256 */             if (block == par1) {
/*      */               
/* 1258 */               anyMatch = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1264 */       if (anyMatch) {
/*      */         
/* 1266 */         for (int id : ints) {
/*      */           
/* 1268 */           for (ItemStack cu : OreDictionary.getOres(Integer.valueOf(id))) {
/*      */             
/* 1270 */             if (cu != null && cu.func_77973_b() != null)
/*      */             {
/* 1272 */               Block block = Block.func_149634_a(cu.func_77973_b());
/* 1273 */               if (block != Blocks.field_150350_a)
/*      */               {
/* 1275 */                 results.add(block);
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 1283 */         results.add(par1);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1288 */       results.add(par1);
/*      */     } 
/* 1290 */     if (!blockStorage.containsKey(par1))
/*      */     {
/* 1292 */       blockStorage.put(par1, results);
/*      */     }
/* 1294 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack generateSeeds(short plant, byte growth, byte gain, byte resis, byte scan) {
/* 1300 */     return ItemCropSeed.generateItemStackFromValues(plant, growth, gain, resis, scan);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CropCard getCrop() {
/* 1308 */     return IC2Crops.getCropFromId(this.id);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCrop(CropCard cropCard) {
/* 1314 */     this.id = cropCard.getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] harvest_automated(boolean Optimal) {
/* 1320 */     if (this.id < 0 || !crop().canBeHarvested(this))
/*      */     {
/* 1322 */       return null;
/*      */     }
/* 1324 */     float chance = crop().dropGainChance();
/* 1325 */     for (int i = 0; i < this.statGain; i++)
/*      */     {
/* 1327 */       chance *= 1.03F;
/*      */     }
/* 1329 */     chance -= IC2.random.nextFloat();
/* 1330 */     int drop = 0;
/* 1331 */     while (chance > 0.0F) {
/*      */       
/* 1333 */       drop++;
/* 1334 */       chance -= IC2.random.nextFloat();
/*      */     } 
/* 1336 */     ItemStack[] re = new ItemStack[drop];
/* 1337 */     for (int j = 0; j < drop; j++) {
/*      */       
/* 1339 */       re[j] = crop().getGain(this);
/* 1340 */       if (re[j] != null && IC2.random.nextInt(100) <= this.statGain) {
/*      */         
/* 1342 */         ItemStack itemStack = re[j];
/* 1343 */         itemStack.field_77994_a++;
/*      */       } 
/*      */     } 
/* 1346 */     this.size = crop().getSizeAfterHarvest(this);
/* 1347 */     this.dirty = true;
/* 1348 */     return re;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack generateSeeds(CropCard crop, byte growth, byte gain, byte resis, byte scan) {
/* 1354 */     return generateSeeds((short)crop.getId(), growth, gain, resis, scan);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNetworkUpdate(String field) {
/* 1360 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getID() {
/* 1366 */     return (short)this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setID(short id) {
/* 1372 */     this.id = id;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getSize() {
/* 1378 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSize(byte size) {
/* 1384 */     this.size = size;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getGrowth() {
/* 1390 */     return this.statGrowth;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGrowth(byte growth) {
/* 1396 */     this.statGrowth = growth;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getGain() {
/* 1402 */     return this.statGain;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGain(byte gain) {
/* 1408 */     this.statGain = gain;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getResistance() {
/* 1414 */     return this.statResistance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResistance(byte resistance) {
/* 1420 */     this.statResistance = resistance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getScanLevel() {
/* 1426 */     return this.scanLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScanLevel(byte scanLevel) {
/* 1432 */     this.scanLevel = scanLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getCustomData() {
/* 1438 */     return this.customData;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNutrientStorage() {
/* 1444 */     return this.nutrientStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNutrientStorage(int nutrientStorage) {
/* 1450 */     this.nutrientStorage = nutrientStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHydrationStorage() {
/* 1456 */     return this.waterStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHydrationStorage(int hydrationStorage) {
/* 1462 */     this.waterStorage = hydrationStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWeedExStorage() {
/* 1468 */     return this.exStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWeedExStorage(int weedExStorage) {
/* 1474 */     this.exStorage = weedExStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1480 */     return this.field_145850_b;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkCoordinates getLocation() {
/* 1486 */     return new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightLevel() {
/* 1492 */     return this.field_145850_b.func_72957_l(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */   }
/*      */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\TileEntityCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */