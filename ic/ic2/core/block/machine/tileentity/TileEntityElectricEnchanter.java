/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.container.ContainerElectricEnchanter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ public class TileEntityElectricEnchanter
/*     */   extends TileEntityElecMachine
/*     */   implements INetworkClientTileEntityEventListener, IHasGui
/*     */ {
/*     */   public int progress;
/*     */   public int exp;
/*     */   public int neededExp;
/*  33 */   public final int anvilTime = 100;
/*  34 */   public final int enchantTime = 1000;
/*  35 */   public int mode = 0;
/*     */   public boolean isDefined = false;
/*     */   public boolean started = false;
/*  38 */   public float serverProgress = 0.0F;
/*  39 */   public float serverChargeLevel = 0.0F;
/*     */ 
/*     */   
/*     */   public TileEntityElectricEnchanter() {
/*  43 */     super(6, 0, 1000000, 512, 3);
/*  44 */     addGuiFields(new String[] { "progress", "exp", "neededExp" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/*  50 */     return new int[] { 4, 5 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  56 */     return "Electric Enchanter";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  62 */     boolean dirty = false;
/*  63 */     if (this.energy < this.maxEnergy)
/*     */     {
/*  65 */       dirty = provideEnergy();
/*     */     }
/*  67 */     if (this.inventory[5] != null) {
/*     */       
/*  69 */       setActive(false);
/*     */       return;
/*     */     } 
/*  72 */     if (!this.isDefined)
/*     */     {
/*  74 */       if (isExtrasValid() && isItemValidToEnchant(this.inventory[4]))
/*     */       {
/*  76 */         calculate();
/*     */       }
/*     */     }
/*  79 */     if (!this.isDefined) {
/*     */       
/*  81 */       setActive(false);
/*     */       return;
/*     */     } 
/*  84 */     if (this.field_145850_b.func_82737_E() % 20L == 0L)
/*     */     {
/*  86 */       updateCost();
/*     */     }
/*  88 */     if (this.neededExp <= 0) {
/*     */       
/*  90 */       setActive(false);
/*     */       return;
/*     */     } 
/*  93 */     if (!this.started) {
/*     */       
/*  95 */       setActive(false);
/*     */       return;
/*     */     } 
/*  98 */     if (isItemValidToEnchant(this.inventory[4]) && isExtrasValid()) {
/*     */       
/* 100 */       if (this.energy >= 500)
/*     */       {
/* 102 */         setActive(true);
/* 103 */         this.progress++;
/* 104 */         useEnergy(500);
/* 105 */         if (this.progress == getProgressTime() - 1)
/*     */         {
/* 107 */           updateCost();
/*     */         }
/* 109 */         if (this.progress >= getProgressTime()) {
/*     */           
/* 111 */           switch (this.mode) {
/*     */             
/*     */             case 0:
/* 114 */               enchantItem();
/*     */               break;
/*     */             case 1:
/* 117 */               anvilItem();
/*     */               break;
/*     */           } 
/* 120 */           this.exp -= this.neededExp;
/* 121 */           this.neededExp = 0;
/* 122 */           this.isDefined = false;
/* 123 */           this.started = false;
/* 124 */           this.progress = 0;
/* 125 */           dirty = true;
/* 126 */           getNetwork().updateTileGuiField((TileEntity)this, "neededExp");
/* 127 */           getNetwork().updateTileGuiField((TileEntity)this, "exp");
/*     */         } 
/* 129 */         getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */       }
/*     */       else
/*     */       {
/* 133 */         setActive(false);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 138 */       this.progress = 0;
/* 139 */       this.started = false;
/* 140 */       setActive(false);
/* 141 */       getNetwork().updateTileGuiField((TileEntity)this, "neededExp");
/* 142 */       getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */     } 
/* 144 */     if (dirty)
/*     */     {
/* 146 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCost() {
/* 152 */     int newExp = (this.mode == 0) ? getBookCount() : getAnvilCost();
/* 153 */     if (newExp != this.neededExp) {
/*     */       
/* 155 */       this.progress = 0;
/* 156 */       this.started = false;
/*     */     } 
/* 158 */     this.neededExp = newExp;
/* 159 */     getNetwork().updateTileGuiField((TileEntity)this, "neededExp");
/* 160 */     getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */   }
/*     */ 
/*     */   
/*     */   public void enchantItem() {
/* 165 */     int count = 0;
/* 166 */     for (int i = 1; i < 4; i++) {
/*     */       
/* 168 */       ItemStack item = this.inventory[i];
/* 169 */       if (item != null && (item.func_77973_b() == Items.field_151122_aG || item.func_77973_b() == Items.field_151134_bR))
/*     */       {
/* 171 */         count++;
/*     */       }
/*     */     } 
/* 174 */     Map<Integer, Integer> enchs = new HashMap<>();
/* 175 */     List<EnchantmentData> data = getPossibleItemEnchantments(count);
/* 176 */     data.addAll(getItemEnchantments());
/* 177 */     for (EnchantmentData key : data) {
/*     */       
/* 179 */       int enchID = key.field_76302_b.field_77352_x;
/* 180 */       if (enchs.containsKey(Integer.valueOf(enchID))) {
/*     */         
/* 182 */         int level = ((Integer)enchs.get(Integer.valueOf(enchID))).intValue();
/* 183 */         if (level >= key.field_76303_c) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 188 */       enchs.put(Integer.valueOf(enchID), Integer.valueOf(key.field_76303_c));
/*     */     } 
/* 190 */     boolean flag = (this.inventory[4].func_77973_b() == Items.field_151122_aG);
/* 191 */     if (flag) {
/*     */       
/* 193 */       ItemStack item = new ItemStack((Item)Items.field_151134_bR);
/* 194 */       EnchantmentHelper.func_82782_a(enchs, item);
/* 195 */       this.inventory[5] = item;
/* 196 */       (this.inventory[4]).field_77994_a--;
/* 197 */       if ((this.inventory[4]).field_77994_a <= 0)
/*     */       {
/* 199 */         this.inventory[4] = null;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 204 */       EnchantmentHelper.func_82782_a(enchs, this.inventory[4]);
/* 205 */       this.inventory[5] = this.inventory[4];
/* 206 */       this.inventory[4] = null;
/*     */     } 
/* 208 */     for (int j = 1; j < 4; j++) {
/*     */       
/* 210 */       ItemStack item = this.inventory[j];
/* 211 */       if (item != null) {
/*     */         
/* 213 */         item.field_77994_a--;
/* 214 */         if (item.field_77994_a <= 0)
/*     */         {
/* 216 */           this.inventory[j] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void anvilItem() {
/* 224 */     Map<Integer, Integer> ench = new HashMap<>();
/* 225 */     ench.putAll(EnchantmentHelper.func_82781_a(this.inventory[4]));
/* 226 */     List<EnchantmentData> data = getItemEnchantments();
/*     */     
/* 228 */     for (EnchantmentData key : data) {
/*     */       
/* 230 */       if (this.inventory[4].func_77973_b() instanceof IElectricTool && !validEnchantment(((IElectricTool)this.inventory[4].func_77973_b()).getType(this.inventory[4]), key.field_76302_b, this.inventory[4])) {
/*     */         continue;
/*     */       }
/*     */       
/* 234 */       int id = key.field_76302_b.field_77352_x;
/* 235 */       if (ench.containsKey(Integer.valueOf(id))) {
/*     */         
/* 237 */         if (!combineable(ench.keySet(), id)) {
/*     */           continue;
/*     */         }
/*     */         
/* 241 */         int lvl = ((Integer)ench.get(Integer.valueOf(id))).intValue();
/* 242 */         if (lvl < key.field_76303_c) {
/*     */           
/* 244 */           ench.put(Integer.valueOf(id), Integer.valueOf(key.field_76303_c)); continue;
/*     */         } 
/* 246 */         if (lvl == key.field_76303_c && key.field_76302_b.func_77325_b() > lvl)
/*     */         {
/* 248 */           ench.put(Integer.valueOf(id), Integer.valueOf(key.field_76303_c + 1));
/*     */         }
/*     */         continue;
/*     */       } 
/* 252 */       ench.put(Integer.valueOf(id), Integer.valueOf(key.field_76303_c));
/*     */     } 
/* 254 */     EnchantmentHelper.func_82782_a(ench, this.inventory[4]);
/* 255 */     this.inventory[4].func_82841_c(this.inventory[4].func_82838_A() + this.field_145850_b.field_73012_v.nextInt(ench.size()));
/* 256 */     this.inventory[5] = this.inventory[4];
/* 257 */     this.inventory[4] = null;
/* 258 */     for (int i = 1; i < 4; i++) {
/*     */       
/* 260 */       ItemStack item = this.inventory[i];
/* 261 */       if (item != null) {
/*     */         
/* 263 */         item.field_77994_a--;
/* 264 */         if (item.field_77994_a <= 0)
/*     */         {
/* 266 */           this.inventory[i] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean combineable(Set<Integer> intigrated, int toAdd) {
/* 274 */     boolean result = true;
/* 275 */     Enchantment requested = Enchantment.field_77331_b[toAdd];
/* 276 */     if (requested == null)
/*     */     {
/* 278 */       return false;
/*     */     }
/* 280 */     for (Integer toCheck : intigrated) {
/*     */       
/* 282 */       Enchantment target = Enchantment.field_77331_b[toCheck.intValue()];
/* 283 */       if (target != requested && !target.func_77326_a(requested) && !requested.func_77326_a(target)) {
/*     */         
/* 285 */         result = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 289 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnchantmentData> getPossibleItemEnchantments(int max) {
/* 294 */     Map<Integer, EnchantmentData> data = new HashMap<>();
/* 295 */     Enchantment[] ench = Enchantment.field_77331_b;
/* 296 */     int j = ench.length;
/* 297 */     int ability = this.inventory[4].func_77973_b().getItemEnchantability(this.inventory[4]);
/* 298 */     ability /= 2;
/* 299 */     ability = 1 + this.field_145850_b.field_73012_v.nextInt((ability >> 1) + 1) + this.field_145850_b.field_73012_v.nextInt((ability >> 1) + 1);
/* 300 */     int combo = ability + this.neededExp;
/* 301 */     float f = (this.field_145850_b.field_73012_v.nextFloat() + this.field_145850_b.field_73012_v.nextFloat() - 1.0F) * 0.15F;
/* 302 */     int needed = (int)(combo * (1.0F + f) + 0.5F);
/*     */     
/* 304 */     if (needed < 1)
/*     */     {
/* 306 */       needed = 1;
/*     */     }
/* 308 */     for (int k = 0; k < j; k++) {
/*     */       
/* 310 */       Enchantment enchantment = ench[k];
/*     */       
/* 312 */       if (enchantment != null && ((
/* 313 */         this.inventory[4].func_77973_b() instanceof IElectricTool && validEnchantment(((IElectricTool)this.inventory[4].func_77973_b()).getType(this.inventory[4]), enchantment, this.inventory[4])) || enchantment.canApplyAtEnchantingTable(this.inventory[4])))
/*     */       {
/* 315 */         for (int l = enchantment.func_77319_d(); l <= enchantment.func_77325_b(); l++) {
/*     */           
/* 317 */           if (needed >= enchantment.func_77321_a(l) && needed <= enchantment.func_77317_b(l))
/*     */           {
/* 319 */             data.put(Integer.valueOf(enchantment.field_77352_x), new EnchantmentData(enchantment, l));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 325 */     List<EnchantmentData> result = new ArrayList<>();
/* 326 */     List<EnchantmentData> posseblities = new ArrayList<>();
/* 327 */     posseblities.addAll(data.values());
/* 328 */     Collections.shuffle(posseblities);
/* 329 */     for (EnchantmentData current : posseblities) {
/*     */       
/* 331 */       boolean can = combineable(result, current);
/* 332 */       if (can)
/*     */       {
/* 334 */         result.add(current);
/*     */       }
/*     */     } 
/* 337 */     Collections.shuffle(result);
/* 338 */     return result.subList(0, Math.min(max, result.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean combineable(List<EnchantmentData> data, EnchantmentData toAdd) {
/* 343 */     boolean result = true;
/* 344 */     for (EnchantmentData ench : data) {
/*     */       
/* 346 */       if (ench.field_76302_b != toAdd.field_76302_b && !ench.field_76302_b.func_77326_a(toAdd.field_76302_b) && !toAdd.field_76302_b.func_77326_a(ench.field_76302_b)) {
/*     */         
/* 348 */         result = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 352 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validEnchantment(EnumEnchantmentType type, Enchantment ench, ItemStack item) {
/* 357 */     IElectricTool tool = (IElectricTool)item.func_77973_b();
/* 358 */     if (tool.isExcluded(item, ench))
/*     */     {
/* 360 */       return false;
/*     */     }
/* 362 */     if (type == EnumEnchantmentType.all)
/*     */     {
/* 364 */       return true;
/*     */     }
/* 366 */     if (type == EnumEnchantmentType.breakable && item.func_77984_f())
/*     */     {
/* 368 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 372 */     if (ench.field_77351_y == type)
/*     */     {
/* 374 */       return true;
/*     */     }
/* 376 */     if (type == EnumEnchantmentType.armor_feet || type == EnumEnchantmentType.armor_head || type == EnumEnchantmentType.armor_legs || type == EnumEnchantmentType.armor_torso)
/*     */     {
/* 378 */       if (ench.field_77351_y == EnumEnchantmentType.armor)
/*     */       {
/* 380 */         return true;
/*     */       }
/*     */     }
/* 383 */     if (tool.isSpecialSupport(item, ench))
/*     */     {
/* 385 */       return true;
/*     */     }
/*     */     
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnchantmentData> getItemEnchantments() {
/* 393 */     List<EnchantmentData> list = new ArrayList<>();
/* 394 */     for (int i = 1; i < 4; i++) {
/*     */       
/* 396 */       ItemStack item = this.inventory[i];
/* 397 */       if (item != null && item.func_77973_b() == Items.field_151134_bR) {
/*     */         
/* 399 */         Map<Integer, Integer> map = EnchantmentHelper.func_82781_a(item);
/* 400 */         for (Map.Entry<Integer, Integer> entry : map.entrySet())
/*     */         {
/* 402 */           list.add(new EnchantmentData(((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue()));
/*     */         }
/*     */       } 
/*     */     } 
/* 406 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculate() {
/* 411 */     this.isDefined = true;
/* 412 */     this.mode = isItemEnchanted(this.inventory[4]) ? 1 : 0;
/* 413 */     this.neededExp = (this.mode == 0) ? getBookCount() : getAnvilCost();
/* 414 */     if (this.mode == 1)
/*     */     {
/* 416 */       validateCombo();
/*     */     }
/* 418 */     getNetwork().updateTileGuiField((TileEntity)this, "neededExp");
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateCombo() {
/* 423 */     List<EnchantmentData> itemEnchantments = getEnchantments(this.inventory[4]);
/* 424 */     for (EnchantmentData data : getItemEnchantments()) {
/*     */       
/* 426 */       if (!combineable(itemEnchantments, data)) {
/*     */         
/* 428 */         this.neededExp = 0;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnchantmentData> getEnchantments(ItemStack par1) {
/* 436 */     if (par1 == null)
/*     */     {
/* 438 */       return new ArrayList<>();
/*     */     }
/* 440 */     List<EnchantmentData> list = new ArrayList<>();
/* 441 */     Map<Integer, Integer> map = EnchantmentHelper.func_82781_a(par1);
/* 442 */     for (Map.Entry<Integer, Integer> entry : map.entrySet())
/*     */     {
/* 444 */       list.add(new EnchantmentData(((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue()));
/*     */     }
/* 446 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgressTime() {
/* 451 */     return (this.mode == 0) ? 1000 : 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnvilCost() {
/* 456 */     if (this.inventory[4] == null)
/*     */     {
/* 458 */       return 0;
/*     */     }
/* 460 */     int cost = getEnchantmentCount(this.inventory[4]);
/* 461 */     int extra = 0;
/* 462 */     for (int i = 1; i < 4; i++) {
/*     */       
/* 464 */       if (this.inventory[i] != null)
/*     */       {
/* 466 */         extra += getEnchantmentCount(this.inventory[i]);
/*     */       }
/*     */     } 
/* 469 */     if (extra <= 0)
/*     */     {
/* 471 */       return 0;
/*     */     }
/* 473 */     return cost + extra;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBookCount() {
/* 478 */     int amount = 0;
/* 479 */     for (int y = 0; y < 2; y++) {
/*     */       
/* 481 */       for (int x = -2; x <= 2; x++) {
/*     */         
/* 483 */         for (int z = -2; z <= 2; z++)
/*     */         {
/* 485 */           amount += (int)this.field_145850_b.func_147439_a(this.field_145851_c + x, this.field_145848_d + y, this.field_145849_e + z).getEnchantPowerBonus(this.field_145850_b, this.field_145851_c + x, this.field_145848_d + y, this.field_145849_e + z);
/*     */         }
/*     */       } 
/*     */     } 
/* 489 */     return Math.min(amount, 30);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemEnchanted(ItemStack item) {
/* 494 */     return item.func_77948_v();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnchantmentCount(ItemStack item) {
/* 499 */     if (item.func_77973_b() == Items.field_151134_bR)
/*     */     {
/* 501 */       return Items.field_151134_bR.func_92110_g(item).func_74745_c() + item.func_82838_A();
/*     */     }
/* 503 */     return (!item.func_77942_o() ? 0 : item.func_77986_q().func_74745_c()) + item.func_82838_A();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemValidToEnchant(ItemStack item) {
/* 508 */     return (item != null && (item.func_77973_b() == Items.field_151122_aG || item.func_77973_b().func_77616_k(item) || item.func_77973_b() instanceof IElectricTool));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExtrasValid() {
/* 513 */     int count = 0;
/* 514 */     for (int i = 1; i < 4; i++) {
/*     */       
/* 516 */       ItemStack item = this.inventory[i];
/* 517 */       if (item != null) {
/*     */         
/* 519 */         if (item.func_77973_b() != Items.field_151122_aG && item.func_77973_b() != Items.field_151134_bR)
/*     */         {
/* 521 */           return false;
/*     */         }
/* 523 */         count++;
/*     */       } 
/*     */     } 
/* 526 */     return (count > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 532 */     if (event == 0) {
/*     */       
/* 534 */       if (player.field_71075_bZ.field_75098_d) {
/*     */         
/* 536 */         this.exp = this.neededExp;
/* 537 */         getNetwork().updateTileGuiField((TileEntity)this, "exp");
/*     */         
/*     */         return;
/*     */       } 
/* 541 */       int needed = this.neededExp - this.exp;
/* 542 */       if (player.field_71068_ca >= needed) {
/*     */         
/* 544 */         this.exp = this.neededExp;
/* 545 */         player.func_82242_a(-needed);
/* 546 */         player.field_71067_cb = getXP(player);
/* 547 */         player.field_71106_cc = (player.field_71067_cb - getXPForLvl(player.field_71068_ca)) / player.func_71050_bK();
/*     */       }
/*     */       else {
/*     */         
/* 551 */         this.exp += player.field_71068_ca;
/* 552 */         player.field_71068_ca = 0;
/* 553 */         player.field_71067_cb = getXP(player);
/* 554 */         player.field_71106_cc = (player.field_71067_cb - getXPForLvl(player.field_71068_ca)) / player.func_71050_bK();
/*     */       } 
/* 556 */       getNetwork().updateTileGuiField((TileEntity)this, "exp");
/*     */     }
/* 558 */     else if (event == 1) {
/*     */       
/* 560 */       if (!this.started && this.isDefined)
/*     */       {
/* 562 */         this.started = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 570 */     return (ContainerIC2)new ContainerElectricEnchanter(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 576 */     return "block.machine.gui.GuiElectricEnchanter";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private int getXP(EntityPlayer player) {
/* 587 */     return (int)(getXPForLvl(player.field_71068_ca) + player.field_71106_cc * player.func_71050_bK());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getXPForLvl(int level) {
/* 592 */     if (level < 0)
/*     */     {
/* 594 */       return Integer.MAX_VALUE;
/*     */     }
/*     */     
/* 597 */     if (level <= 15)
/*     */     {
/* 599 */       return level * 17;
/*     */     }
/*     */     
/* 602 */     if (level <= 30)
/*     */     {
/* 604 */       return (int)((level * level) * 1.5D - 29.5D * level + 360.0D);
/*     */     }
/*     */     
/* 607 */     return (int)((level * level) * 3.5D - 151.5D * level + 2220.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getChargeLevel() {
/* 612 */     float ret = this.energy / this.maxEnergy;
/* 613 */     if (ret > 1.0F)
/*     */     {
/* 615 */       ret = 1.0F;
/*     */     }
/* 617 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getProgress() {
/* 622 */     float ret = this.progress / getProgressTime();
/* 623 */     if (ret > 1.0F)
/*     */     {
/* 625 */       ret = 1.0F;
/*     */     }
/* 627 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 633 */     super.func_145839_a(nbt);
/* 634 */     this.exp = nbt.func_74762_e("Exp");
/* 635 */     this.neededExp = nbt.func_74762_e("Needed");
/* 636 */     this.progress = nbt.func_74762_e("Progress");
/* 637 */     this.mode = nbt.func_74762_e("Mode");
/* 638 */     this.isDefined = nbt.func_74767_n("Defined");
/* 639 */     this.started = nbt.func_74767_n("Started");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 645 */     super.func_145841_b(nbt);
/* 646 */     nbt.func_74768_a("Exp", this.exp);
/* 647 */     nbt.func_74768_a("Needed", this.neededExp);
/* 648 */     nbt.func_74768_a("Progress", this.progress);
/* 649 */     nbt.func_74768_a("Mode", this.mode);
/* 650 */     nbt.func_74757_a("Defined", this.isDefined);
/* 651 */     nbt.func_74757_a("Started", this.started);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 657 */     return 500;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityElectricEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */