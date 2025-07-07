/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IScannerItem;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.container.ContainerOreScanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityOreScanner
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui, INetworkClientTileEntityEventListener
/*     */ {
/*     */   public int range;
/*     */   public int currentX;
/*     */   public int currentZ;
/*     */   public int maxBlocks;
/*     */   public int currentBlocks;
/*     */   public boolean reset;
/*     */   public boolean started;
/*     */   public boolean finished;
/*  40 */   public long scannerID = -1L;
/*  41 */   public int state = -1;
/*  42 */   Map<Block, Map<Integer, Integer>> oresMap = new HashMap<>();
/*  43 */   public OreInventory inv = new OreInventory();
/*  44 */   public NBTTagCompound oreData = new NBTTagCompound();
/*     */ 
/*     */   
/*     */   public TileEntityOreScanner() {
/*  48 */     super(2, 0, 50000, 128, 2);
/*  49 */     addGuiFields(new String[] { "finished", "maxBlocks", "currentBlocks", "oreData", "state" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  55 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  61 */     return "OreScanner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  67 */     super.func_145845_h();
/*  68 */     if (this.energy < this.maxEnergy && this.inventory[0] != null)
/*     */     {
/*  70 */       provideEnergy();
/*     */     }
/*  72 */     if (!canRun()) {
/*     */       
/*  74 */       onChanged();
/*     */       return;
/*     */     } 
/*  77 */     if (this.inventory[1].func_77973_b() instanceof ic2.api.item.IElectricItem) {
/*     */       
/*  79 */       int moved = (int)ElectricItem.manager.charge(this.inventory[1], this.energy, 2147483647, false, false);
/*  80 */       useEnergy(moved);
/*  81 */       if (moved > 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (hasChanged())
/*     */     {
/*  89 */       onChanged();
/*     */     }
/*  91 */     if (this.finished) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (!this.started) {
/*     */       
/*  98 */       this.started = true;
/*  99 */       restart();
/*     */       return;
/*     */     } 
/* 102 */     work();
/*     */   }
/*     */ 
/*     */   
/*     */   public void work() {
/* 107 */     IScannerItem item = (IScannerItem)this.inventory[1].func_77973_b();
/* 108 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 110 */       if (this.energy < this.field_145848_d || this.finished) {
/*     */         break;
/*     */       }
/*     */       
/* 114 */       scanArea(item, this.currentX, this.currentZ);
/* 115 */       this.currentX++;
/* 116 */       if (this.currentX > this.field_145851_c + this.range) {
/*     */         
/* 118 */         this.currentX = this.field_145851_c - this.range;
/* 119 */         this.currentZ++;
/* 120 */         if (this.currentZ > this.field_145849_e + this.range) {
/*     */           
/* 122 */           this.finished = true;
/* 123 */           onFinish();
/*     */         } 
/*     */       } 
/* 126 */       useEnergy(this.field_145848_d);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFinish() {
/* 132 */     Map<ItemStack, Integer> items = new HashMap<>();
/* 133 */     for (Map.Entry<Block, Map<Integer, Integer>> entry : this.oresMap.entrySet()) {
/*     */       
/* 135 */       Block block = entry.getKey();
/* 136 */       if (Item.func_150898_a(block) == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 140 */       Map<Integer, Integer> value = entry.getValue();
/* 141 */       for (Map.Entry<Integer, Integer> data : value.entrySet())
/*     */       {
/* 143 */         items.put(new ItemStack(block, 0, ((Integer)data.getKey()).intValue()), data.getValue());
/*     */       }
/*     */     } 
/* 146 */     NBTTagList list = new NBTTagList();
/* 147 */     for (Map.Entry<ItemStack, Integer> entry : items.entrySet()) {
/*     */       
/* 149 */       NBTTagCompound data = new NBTTagCompound();
/* 150 */       ((ItemStack)entry.getKey()).func_77955_b(data);
/* 151 */       data.func_74768_a("amount", ((Integer)entry.getValue()).intValue());
/* 152 */       list.func_74742_a((NBTBase)data);
/*     */     } 
/* 154 */     this.oreData.func_74782_a("Items", (NBTBase)list);
/* 155 */     this.inv.readOreData(this.oreData);
/* 156 */     this.finished = true;
/* 157 */     this.started = false;
/* 158 */     this.reset = false;
/* 159 */     this.state = 0;
/* 160 */     getNetwork().updateTileGuiField((TileEntity)this, "oreData");
/* 161 */     getNetwork().updateTileGuiField((TileEntity)this, "finished");
/* 162 */     getNetwork().updateTileGuiField((TileEntity)this, "state");
/*     */   }
/*     */ 
/*     */   
/*     */   public void scanArea(IScannerItem item, int x, int z) {
/* 167 */     for (int y = 0; y < this.field_145848_d; y++) {
/*     */       
/* 169 */       this.currentBlocks++;
/* 170 */       Block block = this.field_145850_b.func_147439_a(x, y, z);
/* 171 */       int meta = this.field_145850_b.func_72805_g(x, y, z);
/* 172 */       if (item.isValuableOre(this.inventory[1], block, meta)) {
/*     */         
/* 174 */         if (!this.oresMap.containsKey(block))
/*     */         {
/* 176 */           this.oresMap.put(block, new HashMap<>());
/*     */         }
/* 178 */         Map<Integer, Integer> map = this.oresMap.get(block);
/* 179 */         if (!map.containsKey(Integer.valueOf(meta))) {
/*     */           
/* 181 */           map.put(Integer.valueOf(meta), Integer.valueOf(1));
/*     */         }
/*     */         else {
/*     */           
/* 185 */           map.put(Integer.valueOf(meta), Integer.valueOf(((Integer)map.get(Integer.valueOf(meta))).intValue() + 1));
/*     */         } 
/* 187 */         this.oresMap.put(block, map);
/*     */       } 
/*     */     } 
/* 190 */     getNetwork().updateTileGuiField((TileEntity)this, "currentBlocks");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChanged() {
/* 195 */     if (this.finished && this.inventory[1] != null) {
/*     */       
/* 197 */       this.started = false;
/* 198 */       this.reset = true;
/* 199 */       this.finished = false;
/* 200 */       getNetwork().updateTileGuiField((TileEntity)this, "finished");
/*     */     } 
/* 202 */     if (this.started) {
/*     */       
/* 204 */       this.started = false;
/* 205 */       this.reset = true;
/*     */     } 
/* 207 */     if (this.reset) {
/*     */       
/* 209 */       this.reset = false;
/* 210 */       this.maxBlocks = 0;
/* 211 */       this.currentBlocks = 0;
/* 212 */       this.range = 0;
/* 213 */       this.state = 0;
/* 214 */       this.scannerID = -1L;
/* 215 */       this.oresMap.clear();
/* 216 */       this.inv.clear();
/* 217 */       this.oreData = new NBTTagCompound();
/* 218 */       getNetwork().updateTileGuiField((TileEntity)this, "oreData");
/* 219 */       getNetwork().updateTileGuiField((TileEntity)this, "maxBlocks");
/* 220 */       getNetwork().updateTileGuiField((TileEntity)this, "currentBlocks");
/* 221 */       getNetwork().updateTileGuiField((TileEntity)this, "state");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/* 227 */     IScannerItem item = (IScannerItem)this.inventory[1].func_77973_b();
/* 228 */     int newRange = item.startLayerScan(this.inventory[1]);
/* 229 */     this.range = newRange;
/* 230 */     newRange *= 2;
/* 231 */     newRange++;
/* 232 */     int blocksXZ = newRange * newRange;
/* 233 */     int totalBlocks = blocksXZ * this.field_145848_d;
/* 234 */     this.maxBlocks = totalBlocks;
/* 235 */     getNetwork().updateTileGuiField((TileEntity)this, "maxBlocks");
/* 236 */     setScannerID(this.inventory[1]);
/* 237 */     this.scannerID = getScannerID(this.inventory[1]);
/* 238 */     this.currentX = this.field_145851_c - this.range;
/* 239 */     this.currentZ = this.field_145849_e - this.range;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 244 */     if (this.scannerID == -1L || !hasScannerID(this.inventory[1]))
/*     */     {
/* 246 */       return true;
/*     */     }
/* 248 */     return (this.scannerID != getScannerID(this.inventory[1]));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRun() {
/* 253 */     if (this.inventory[1] == null)
/*     */     {
/* 255 */       return false;
/*     */     }
/* 257 */     return this.inventory[1].func_77973_b() instanceof IScannerItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScannerID(ItemStack par1) {
/* 262 */     if (par1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 266 */     StackUtil.getOrCreateNbtData(par1).func_74772_a("ScannerID", System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasScannerID(ItemStack par1) {
/* 271 */     if (par1 == null)
/*     */     {
/* 273 */       return false;
/*     */     }
/* 275 */     return StackUtil.getOrCreateNbtData(par1).func_74764_b("ScannerID");
/*     */   }
/*     */ 
/*     */   
/*     */   public long getScannerID(ItemStack par1) {
/* 280 */     if (par1 == null)
/*     */     {
/* 282 */       return -1L;
/*     */     }
/* 284 */     return StackUtil.getOrCreateNbtData(par1).func_74763_f("ScannerID");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 290 */     super.func_145839_a(nbttagcompound);
/* 291 */     this.started = nbttagcompound.func_74767_n("Started");
/* 292 */     this.reset = nbttagcompound.func_74767_n("Reset");
/* 293 */     this.finished = nbttagcompound.func_74767_n("Finished");
/* 294 */     this.currentBlocks = nbttagcompound.func_74762_e("currentBlocks");
/* 295 */     this.currentX = nbttagcompound.func_74762_e("StateX");
/* 296 */     this.currentZ = nbttagcompound.func_74762_e("StateZ");
/* 297 */     this.maxBlocks = nbttagcompound.func_74762_e("Max");
/* 298 */     this.range = nbttagcompound.func_74762_e("Range");
/* 299 */     this.state = nbttagcompound.func_74762_e("state");
/* 300 */     this.oresMap.clear();
/* 301 */     NBTTagList list = nbttagcompound.func_150295_c("OreValues", 10);
/* 302 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 304 */       NBTTagCompound nbt = list.func_150305_b(i);
/* 305 */       Block block = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/*     */       
/* 307 */       NBTTagList metaList = nbt.func_150295_c("MetaValue", 10);
/* 308 */       Map<Integer, Integer> metaMap = new HashMap<>();
/* 309 */       for (int z = 0; z < metaList.func_74745_c(); z++) {
/*     */         
/* 311 */         NBTTagCompound data = metaList.func_150305_b(z);
/* 312 */         metaMap.put(Integer.valueOf(data.func_74762_e("MetaData")), Integer.valueOf(data.func_74762_e("Count")));
/*     */       } 
/* 314 */       this.oresMap.put(block, metaMap);
/*     */     } 
/* 316 */     this.inv.clear();
/* 317 */     this.oreData = nbttagcompound.func_74775_l("OreData");
/* 318 */     this.inv.readOreData(this.oreData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 324 */     super.func_145841_b(nbttagcompound);
/* 325 */     nbttagcompound.func_74757_a("Started", this.started);
/* 326 */     nbttagcompound.func_74757_a("Reset", this.reset);
/* 327 */     nbttagcompound.func_74757_a("Finished", this.finished);
/* 328 */     nbttagcompound.func_74768_a("currentBlocks", this.currentBlocks);
/* 329 */     nbttagcompound.func_74768_a("StateX", this.currentX);
/* 330 */     nbttagcompound.func_74768_a("StateZ", this.currentZ);
/* 331 */     nbttagcompound.func_74768_a("Max", this.maxBlocks);
/* 332 */     nbttagcompound.func_74768_a("Range", this.range);
/* 333 */     nbttagcompound.func_74768_a("state", this.state);
/*     */     
/* 335 */     NBTTagList list = new NBTTagList();
/* 336 */     for (Map.Entry<Block, Map<Integer, Integer>> maps : this.oresMap.entrySet()) {
/*     */       
/* 338 */       NBTTagCompound data = new NBTTagCompound();
/* 339 */       String id = Block.field_149771_c.func_148750_c(maps.getKey());
/* 340 */       data.func_74778_a("BlockID", id);
/* 341 */       NBTTagList metaAndStack = new NBTTagList();
/* 342 */       for (Map.Entry<Integer, Integer> meta : (Iterable<Map.Entry<Integer, Integer>>)((Map)maps.getValue()).entrySet()) {
/*     */         
/* 344 */         NBTTagCompound nbt = new NBTTagCompound();
/* 345 */         nbt.func_74768_a("MetaData", ((Integer)meta.getKey()).intValue());
/* 346 */         nbt.func_74768_a("Count", ((Integer)meta.getValue()).intValue());
/* 347 */         metaAndStack.func_74742_a((NBTBase)nbt);
/*     */       } 
/* 349 */       data.func_74782_a("MetaValue", (NBTBase)metaAndStack);
/* 350 */       list.func_74742_a((NBTBase)data);
/*     */     } 
/* 352 */     nbttagcompound.func_74782_a("OreValues", (NBTBase)list);
/* 353 */     nbttagcompound.func_74782_a("OreData", (NBTBase)this.oreData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 359 */     super.onNetworkUpdate(field);
/* 360 */     if (field.equals("oreData"))
/*     */     {
/* 362 */       this.inv.readOreData(this.oreData);
/*     */     }
/*     */   }
/*     */   
/*     */   public class OreInventory
/*     */     implements IInventory {
/* 368 */     ItemStack[] items = new ItemStack[0];
/* 369 */     Map<TileEntityElectrolyzer.ItemRecipe, Integer> sizes = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     public int func_70302_i_() {
/* 374 */       return this.items.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSize(ItemStack par1) {
/* 379 */       if (par1 == null)
/*     */       {
/* 381 */         return 0;
/*     */       }
/* 383 */       TileEntityElectrolyzer.ItemRecipe recipe = new TileEntityElectrolyzer.ItemRecipe(par1);
/* 384 */       if (this.sizes.containsKey(recipe))
/*     */       {
/* 386 */         return ((Integer)this.sizes.get(recipe)).intValue();
/*     */       }
/* 388 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack func_70301_a(int par1) {
/* 394 */       par1 += TileEntityOreScanner.this.state * 5;
/* 395 */       if (par1 >= this.items.length || par1 < 0)
/*     */       {
/* 397 */         return null;
/*     */       }
/* 399 */       return this.items[par1];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
/* 405 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack func_70304_b(int par1) {
/* 411 */       return func_70301_a(par1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70299_a(int par1, ItemStack par2) {
/* 417 */       par1 += TileEntityOreScanner.this.state * 5;
/* 418 */       if (par1 >= this.items.length || par1 < 0) {
/*     */         return;
/*     */       }
/*     */       
/* 422 */       this.items[par1] = par2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String func_145825_b() {
/* 428 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_145818_k_() {
/* 434 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int func_70297_j_() {
/* 440 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_70300_a(EntityPlayer p_70300_1_) {
/* 452 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70305_f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 469 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 474 */       this.sizes.clear();
/* 475 */       this.items = new ItemStack[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public void readOreData(NBTTagCompound nbt) {
/* 480 */       List<ItemStack> array = new ArrayList<>();
/* 481 */       NBTTagList list = nbt.func_150295_c("Items", 10);
/* 482 */       for (int i = 0; i < list.func_74745_c(); i++) {
/*     */         
/* 484 */         NBTTagCompound data = list.func_150305_b(i);
/* 485 */         ItemStack stack = ItemStack.func_77949_a(data);
/* 486 */         int amount = data.func_74762_e("amount");
/* 487 */         array.add(stack);
/* 488 */         this.sizes.put(new TileEntityElectrolyzer.ItemRecipe(stack), Integer.valueOf(amount));
/*     */       } 
/* 490 */       Collections.sort(array, new Comparator<ItemStack>()
/*     */           {
/*     */             
/*     */             public int compare(ItemStack par1, ItemStack par2)
/*     */             {
/* 495 */               int sizeA = TileEntityOreScanner.OreInventory.this.getSize(par1);
/* 496 */               int sizeB = TileEntityOreScanner.OreInventory.this.getSize(par2);
/* 497 */               if (sizeA > sizeB)
/*     */               {
/* 499 */                 return -1;
/*     */               }
/* 501 */               if (sizeB > sizeA)
/*     */               {
/* 503 */                 return 1;
/*     */               }
/* 505 */               return 0;
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 510 */       this.items = array.<ItemStack>toArray(new ItemStack[array.size()]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 518 */     return (ContainerIC2)new ContainerOreScanner(p0.field_71071_by, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 524 */     return "block.machine.gui.GuiOreScanner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 536 */     if (event == -1 || event == 1) {
/*     */       
/* 538 */       this.state += event;
/* 539 */       if (this.state < 0) { this.state = 0; }
/* 540 */       else if (event > 0 && this.state > this.inv.func_70302_i_() / 5) { this.state--; }
/* 541 */        getNetwork().updateTileGuiField((TileEntity)this, "state");
/* 542 */       getNetwork().updateTileGuiField((TileEntity)this, "oreData");
/*     */     }
/* 544 */     else if (event == 0) {
/*     */       
/* 546 */       this.scannerID = -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 553 */     return this.field_145848_d * 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityOreScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */