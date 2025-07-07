/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class NuclearJetpackLogic
/*     */   implements IReactor {
/*  28 */   ItemStack[] parts = new ItemStack[25];
/*     */   
/*     */   int tick;
/*     */   int output;
/*     */   int heat;
/*     */   int partHeat;
/*     */   int maxHeat;
/*     */   float hem;
/*     */   boolean active;
/*     */   EntityPlayer player;
/*     */   
/*     */   public NuclearJetpackLogic(ItemStack par1, EntityPlayer par2) {
/*  40 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(par1);
/*  41 */     readInventory(data.func_74775_l("Inventory"));
/*  42 */     readLogic(data.func_74775_l("Logic"));
/*  43 */     this.player = par2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(ItemStack par1) {
/*  48 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(par1);
/*  49 */     NBTTagCompound inv = new NBTTagCompound();
/*  50 */     NBTTagCompound logic = new NBTTagCompound();
/*  51 */     writeInventory(inv);
/*  52 */     writeLogic(logic);
/*  53 */     data.func_74782_a("Inventory", (NBTBase)inv);
/*  54 */     data.func_74782_a("Logic", (NBTBase)logic);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readInventory(NBTTagCompound par1) {
/*  59 */     NBTTagList nbttaglist = par1.func_150295_c("Items", 10);
/*  60 */     this.parts = new ItemStack[25];
/*  61 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/*  63 */       NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
/*  64 */       byte b0 = nbttagcompound1.func_74771_c("Slot");
/*  65 */       if (b0 >= 0 && b0 < this.parts.length)
/*     */       {
/*  67 */         this.parts[b0] = ItemStack.func_77949_a(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeInventory(NBTTagCompound par1) {
/*  74 */     NBTTagList nbttaglist = new NBTTagList();
/*  75 */     for (int i = 0; i < this.parts.length; i++) {
/*     */       
/*  77 */       if (this.parts[i] != null) {
/*     */         
/*  79 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  80 */         nbttagcompound1.func_74774_a("Slot", (byte)i);
/*  81 */         this.parts[i].func_77955_b(nbttagcompound1);
/*  82 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*  85 */     par1.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readLogic(NBTTagCompound par1) {
/*  90 */     this.tick = par1.func_74762_e("CurrentTick");
/*  91 */     this.active = par1.func_74767_n("Active");
/*  92 */     this.heat = par1.func_74762_e("Heat");
/*  93 */     this.output = par1.func_74762_e("output");
/*  94 */     this.partHeat = par1.func_74762_e("PartHeat");
/*  95 */     this.maxHeat = par1.func_74762_e("MaxHeat");
/*  96 */     this.hem = par1.func_74760_g("Hem");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeLogic(NBTTagCompound par1) {
/* 101 */     par1.func_74768_a("CurrentTick", this.tick);
/* 102 */     par1.func_74757_a("Active", this.active);
/* 103 */     par1.func_74768_a("Heat", this.heat);
/* 104 */     par1.func_74768_a("output", this.output);
/* 105 */     par1.func_74768_a("PartHeat", this.partHeat);
/* 106 */     par1.func_74768_a("MaxHeat", this.maxHeat);
/* 107 */     par1.func_74776_a("Hem", this.hem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(EntityPlayer par1, ItemStack jetpack) {
/* 112 */     updateState(jetpack);
/* 113 */     if (this.tick++ % 20 == 0) {
/*     */       
/* 115 */       if (updateReactor()) {
/*     */         
/* 117 */         par1.field_71071_by.field_70460_b[2] = null;
/*     */         return;
/*     */       } 
/* 120 */       updatepartHeat();
/*     */     } 
/* 122 */     ElectricItem.manager.charge(jetpack, this.output, 2147483647, true, false);
/* 123 */     updateState(jetpack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatepartHeat() {
/* 128 */     int[] partHeat = new int[25];
/* 129 */     int count = 0;
/* 130 */     for (int i = 0; i < this.parts.length; i++) {
/*     */       
/* 132 */       ItemStack part = this.parts[i];
/* 133 */       if (part != null && part.func_77973_b() instanceof IReactorComponent) {
/*     */         
/* 135 */         IReactorComponent comp = (IReactorComponent)part.func_77973_b();
/* 136 */         int maxHeat = comp.getMaxHeat(this, part, i % 5, i / 5);
/* 137 */         if (maxHeat > 0) {
/*     */           
/* 139 */           partHeat[count] = (int)(part.func_77960_j() / maxHeat * 100.0D);
/* 140 */           count++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 144 */     if (count > 0) {
/*     */       
/* 146 */       double cu = 0.0D;
/* 147 */       for (int j = 0; j < count; j++)
/*     */       {
/* 149 */         cu += partHeat[j];
/*     */       }
/* 151 */       cu /= count;
/* 152 */       this.partHeat = (int)cu;
/*     */     }
/*     */     else {
/*     */       
/* 156 */       this.partHeat = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateState(ItemStack par1) {
/* 162 */     double maxEnergy = 30000.0D;
/* 163 */     if (par1 != null && par1.func_77973_b() instanceof IElectricItem)
/*     */     {
/* 165 */       maxEnergy = ((IElectricItem)par1.func_77973_b()).getMaxCharge(par1);
/*     */     }
/* 167 */     if (!this.active) {
/*     */       
/* 169 */       double produce = ElectricItem.manager.getCharge(par1) / maxEnergy * 100.0D;
/* 170 */       if (produce < 30.0D)
/*     */       {
/* 172 */         this.active = true;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 177 */     else if (ElectricItem.manager.getCharge(par1) >= maxEnergy) {
/*     */       
/* 179 */       this.active = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateReactor() {
/* 186 */     this.output = 0;
/* 187 */     this.maxHeat = 10000;
/* 188 */     this.hem = 1.0F;
/* 189 */     processChamber();
/* 190 */     if (calculateHeatEffects())
/*     */     {
/* 192 */       return true;
/*     */     }
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean calculateHeatEffects() {
/* 199 */     if (this.heat < 4000 || !IC2.platform.isSimulating() || IC2.explosionPowerReactorMax <= 0.0F)
/*     */     {
/* 201 */       return false;
/*     */     }
/* 203 */     float power = this.heat / this.maxHeat;
/* 204 */     if (power >= 1.0F) {
/*     */       
/* 206 */       explode();
/* 207 */       return true;
/*     */     } 
/* 209 */     World worldObj = getWorld();
/* 210 */     if (power >= 0.85F && worldObj.field_73012_v.nextFloat() <= 0.2F * this.hem) {
/*     */       
/* 212 */       int[] coord = getRandCoord(2);
/* 213 */       if (coord != null) {
/*     */         
/* 215 */         Block id = worldObj.func_147439_a(coord[0], coord[1], coord[2]);
/* 216 */         if (id == null) {
/*     */           
/* 218 */           worldObj.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */         }
/* 220 */         else if (id.func_149712_f(worldObj, coord[0], coord[1], coord[2]) <= -1.0F) {
/*     */           
/* 222 */           Material mat = id.func_149688_o();
/* 223 */           if (mat == Material.field_151576_e || mat == Material.field_151573_f || mat == Material.field_151587_i || mat == Material.field_151578_c || mat == Material.field_151571_B) {
/*     */             
/* 225 */             worldObj.func_147465_d(coord[0], coord[1], coord[2], (Block)Blocks.field_150356_k, 15, 3);
/*     */           }
/*     */           else {
/*     */             
/* 229 */             worldObj.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 234 */     if (power >= 0.7F) {
/*     */       
/* 236 */       ChunkCoordinates coords = getPosition();
/* 237 */       List<EntityLivingBase> list1 = worldObj.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a((coords.field_71574_a - 3), (coords.field_71572_b - 3), (coords.field_71573_c - 3), (coords.field_71574_a + 4), (coords.field_71572_b + 4), (coords.field_71573_c + 4)));
/* 238 */       for (int l = 0; l < list1.size(); l++) {
/*     */         
/* 240 */         EntityLivingBase ent = list1.get(l);
/* 241 */         ent.func_70097_a((DamageSource)IC2DamageSource.radiation, (int)(worldObj.field_73012_v.nextInt(4) * this.hem));
/*     */       } 
/*     */     } 
/* 244 */     if (power >= 0.5F && worldObj.field_73012_v.nextFloat() <= this.hem) {
/*     */       
/* 246 */       int[] coord = getRandCoord(2);
/* 247 */       if (coord != null) {
/*     */         
/* 249 */         Block id = worldObj.func_147439_a(coord[0], coord[1], coord[2]);
/* 250 */         if (id != null && id.func_149688_o() == Material.field_151586_h)
/*     */         {
/* 252 */           worldObj.func_147468_f(coord[0], coord[1], coord[2]);
/*     */         }
/*     */       } 
/*     */     } 
/* 256 */     if (power >= 0.4F && worldObj.field_73012_v.nextFloat() <= this.hem) {
/*     */       
/* 258 */       int[] coord = getRandCoord(2);
/* 259 */       if (coord != null) {
/*     */         
/* 261 */         Block id = worldObj.func_147439_a(coord[0], coord[1], coord[2]);
/* 262 */         if (id != null) {
/*     */           
/* 264 */           Material mat = id.func_149688_o();
/* 265 */           if (mat == Material.field_151575_d || mat == Material.field_151584_j || mat == Material.field_151580_n)
/*     */           {
/* 267 */             worldObj.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChamber() {
/* 277 */     for (int pass = 0; pass < 2; pass++) {
/*     */       
/* 279 */       for (int x = 0; x < 5; x++) {
/*     */         
/* 281 */         for (int y = 0; y < 5; y++) {
/*     */           
/* 283 */           ItemStack stack = getMatrix(x, y);
/* 284 */           if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */             
/* 286 */             IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/* 287 */             comp.processChamber(this, stack, x, y, (pass == 0));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getRandCoord(int radius) {
/* 296 */     if (radius <= 0)
/*     */     {
/* 298 */       return null;
/*     */     }
/* 300 */     ChunkCoordinates coords = getPosition();
/* 301 */     World worldObj = getWorld();
/* 302 */     int[] c = { coords.field_71574_a + worldObj.field_73012_v.nextInt(2 * radius + 1) - radius, coords.field_71572_b + worldObj.field_73012_v.nextInt(2 * radius + 1) - radius, coords.field_71573_c + worldObj.field_73012_v.nextInt(2 * radius + 1) - radius };
/* 303 */     if (c[0] == coords.field_71574_a && c[1] == coords.field_71572_b && c[2] == coords.field_71573_c)
/*     */     {
/* 305 */       return null;
/*     */     }
/* 307 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkCoordinates getPosition() {
/* 313 */     return new ChunkCoordinates((int)this.player.field_70165_t, (int)this.player.field_70163_u, (int)this.player.field_70161_v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 319 */     return this.player.field_70170_p;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeat() {
/* 325 */     return this.heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeat(int heat) {
/* 331 */     this.heat = heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int addHeat(int amount) {
/* 337 */     return this.heat += amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat() {
/* 343 */     return this.maxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxHeat(int newMaxHeat) {
/* 349 */     this.maxHeat = newMaxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitHeat(int heat) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeatEffectModifier() {
/* 361 */     return this.hem;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeatEffectModifier(float newHEM) {
/* 367 */     this.hem = newHEM;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getReactorEnergyOutput() {
/* 373 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float addOutput(float energy) {
/* 379 */     return (this.output = (int)(this.output + energy));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemAt(int x, int y) {
/* 385 */     return getMatrix(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemAt(int x, int y, ItemStack item) {
/* 391 */     setMatrix(x, y, item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void explode() {
/* 397 */     float boomPower = 10.0F;
/* 398 */     float boomMod = 1.0F;
/* 399 */     for (int y = 0; y < 5; y++) {
/*     */       
/* 401 */       for (int x = 0; x < 5; x++) {
/*     */         
/* 403 */         ItemStack stack = getMatrix(x, y);
/* 404 */         if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */           
/* 406 */           float f = ((IReactorComponent)stack.func_77973_b()).influenceExplosion(this, stack);
/* 407 */           if (f > 0.0F && f < 1.0F) {
/*     */             
/* 409 */             boomMod *= f;
/*     */           }
/*     */           else {
/*     */             
/* 413 */             boomPower += f;
/*     */           } 
/*     */         } 
/* 416 */         setMatrix(x, y, null);
/*     */       } 
/*     */     } 
/* 419 */     boomPower *= this.hem * boomMod;
/* 420 */     IC2.log.info("Nuclear Jetpack at " + this.player.field_70170_p.field_73011_w.field_76574_g + ":(" + this.player.field_70165_t + "," + this.player.field_70163_u + "," + this.player.field_70161_v + ") melted (explosion power " + boomPower + ")");
/* 421 */     if (boomPower > IC2.explosionPowerReactorMax)
/*     */     {
/* 423 */       boomPower = IC2.explosionPowerReactorMax;
/*     */     }
/* 425 */     this.player.field_71071_by.field_70460_b[2] = null;
/* 426 */     this.player.field_71070_bA.func_75142_b();
/* 427 */     ExplosionIC2 explosion = new ExplosionIC2(this.player.field_70170_p, null, this.player.field_70165_t, this.player.field_70163_u, this.player.field_70161_v, boomPower, 0.01F, 1.5F, (DamageSource)IC2DamageSource.nuke);
/* 428 */     explosion.doExplosion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/* 434 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean produceEnergy() {
/* 440 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int i) {
/* 445 */     return this.parts[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInventoryContent(int i, ItemStack par1) {
/* 450 */     this.parts[i] = par1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMatrix(int x, int y, ItemStack par1) {
/* 455 */     if (x < 0 || x >= 5 || y < 0 || y >= 5) {
/*     */       return;
/*     */     }
/*     */     
/* 459 */     setInventoryContent(x + y * 5, par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getMatrix(int x, int y) {
/* 464 */     if (x < 0 || x >= 5 || y < 0 || y >= 5)
/*     */     {
/* 466 */       return null;
/*     */     }
/* 468 */     return getStackInSlot(x + y * 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getReactorEUEnergyOutput() {
/* 474 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSignal(boolean redstone) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFluidCooled() {
/* 486 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\NuclearJetpackLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */