/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IMiningDrill;
/*     */ import ic2.api.item.IScannerItem;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerMiner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ 
/*     */ public class TileEntityMiner extends TileEntityElecMachine implements IHasGui {
/*  43 */   public static ForgeDirection[] validDirs = new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST };
/*  44 */   public int targetX = 0;
/*  45 */   public int targetY = -1;
/*  46 */   public int targetZ = 0;
/*  47 */   public int currentX = 0;
/*  48 */   public int currentY = 0;
/*  49 */   public int currentZ = 0;
/*  50 */   public short miningTicker = 0;
/*  51 */   public String stuckOn = null;
/*     */   
/*     */   private AudioSource audioSource;
/*     */   
/*     */   public TileEntityMiner() {
/*  56 */     super(4, 0, 1000, 32, IC2.enableMinerLapotron ? 3 : 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  62 */     super.func_145845_h();
/*  63 */     boolean wasOperating = isOperating();
/*  64 */     boolean needsInvUpdate = false;
/*  65 */     if (isOperating()) {
/*     */       
/*  67 */       useEnergy(1);
/*  68 */       if (this.inventory[1] != null && this.inventory[1].func_77973_b() instanceof IElectricItem)
/*     */       {
/*  70 */         this.energy = (int)(this.energy - ElectricItem.manager.charge(this.inventory[1], this.energy, 2, false, false));
/*     */       }
/*  72 */       if (this.inventory[3] != null && this.inventory[3].func_77973_b() instanceof IElectricItem)
/*     */       {
/*  74 */         this.energy = (int)(this.energy - ElectricItem.manager.charge(this.inventory[3], this.energy, 2, false, false));
/*     */       }
/*     */     } 
/*  77 */     if (this.energy <= this.maxEnergy)
/*     */     {
/*  79 */       needsInvUpdate = provideEnergy();
/*     */     }
/*  81 */     if (wasOperating) {
/*     */       
/*  83 */       needsInvUpdate = mine();
/*     */     }
/*  85 */     else if (this.inventory[3] == null) {
/*     */       
/*  87 */       if (this.energy >= 2 && canWithdraw()) {
/*     */         
/*  89 */         this.targetY = -1;
/*  90 */         this.miningTicker = (short)(this.miningTicker + 1);
/*  91 */         useEnergy(2);
/*  92 */         if (this.miningTicker >= 20)
/*     */         {
/*  94 */           this.miningTicker = 0;
/*  95 */           needsInvUpdate = withdrawPipe();
/*     */         }
/*     */       
/*  98 */       } else if (isStuck()) {
/*     */         
/* 100 */         this.miningTicker = 0;
/*     */       } 
/*     */     } 
/* 103 */     setActive(isOperating());
/* 104 */     if (wasOperating != isOperating())
/*     */     {
/* 106 */       needsInvUpdate = true;
/*     */     }
/* 108 */     if (needsInvUpdate)
/*     */     {
/* 110 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 117 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 119 */       IC2.audioManager.removeSources(this);
/* 120 */       this.audioSource = null;
/*     */     } 
/* 122 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 128 */     super.func_145839_a(nbttagcompound);
/* 129 */     this.targetX = nbttagcompound.func_74762_e("targetX");
/* 130 */     this.targetY = nbttagcompound.func_74762_e("targetY");
/* 131 */     this.targetZ = nbttagcompound.func_74762_e("targetZ");
/* 132 */     this.miningTicker = nbttagcompound.func_74765_d("miningTicker");
/* 133 */     this.currentX = nbttagcompound.func_74762_e("currentX");
/* 134 */     this.currentY = nbttagcompound.func_74762_e("currentY");
/* 135 */     this.currentZ = nbttagcompound.func_74762_e("currentZ");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 141 */     super.func_145841_b(nbttagcompound);
/* 142 */     nbttagcompound.func_74768_a("targetX", this.targetX);
/* 143 */     nbttagcompound.func_74768_a("targetY", this.targetY);
/* 144 */     nbttagcompound.func_74768_a("targetZ", this.targetZ);
/* 145 */     nbttagcompound.func_74777_a("miningTicker", this.miningTicker);
/* 146 */     nbttagcompound.func_74768_a("currentX", this.currentX);
/* 147 */     nbttagcompound.func_74768_a("currentY", this.currentY);
/* 148 */     nbttagcompound.func_74768_a("currentZ", this.currentZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mine() {
/* 153 */     if (this.targetY < 0) {
/*     */       
/* 155 */       aquireTarget();
/* 156 */       return false;
/*     */     } 
/* 158 */     boolean forcedTickSkip = false;
/* 159 */     while (!canReachTarget(this.currentX, this.currentY, this.currentZ, true)) {
/*     */       
/* 161 */       int x = this.currentX - this.field_145851_c;
/* 162 */       int z = this.currentZ - this.field_145849_e;
/* 163 */       if (Math.abs(x) > Math.abs(z)) {
/*     */         
/* 165 */         if (x > 0)
/*     */         {
/* 167 */           this.currentX--;
/*     */         }
/*     */         else
/*     */         {
/* 171 */           this.currentX++;
/*     */         }
/*     */       
/* 174 */       } else if (z > 0) {
/*     */         
/* 176 */         this.currentZ--;
/*     */       }
/*     */       else {
/*     */         
/* 180 */         this.currentZ++;
/*     */       } 
/* 182 */       forcedTickSkip = true;
/*     */     } 
/* 184 */     if (forcedTickSkip)
/*     */     {
/* 186 */       return false;
/*     */     }
/*     */     
/* 189 */     if (!canMine(this.currentX, this.currentY, this.currentZ)) {
/*     */       
/* 191 */       Block id = this.field_145850_b.func_147439_a(this.currentX, this.currentY, this.currentZ);
/* 192 */       if ((id == Blocks.field_150358_i || id == Blocks.field_150355_j || id == Blocks.field_150356_k || id == Blocks.field_150353_l) && isAnyPumpConnected())
/*     */       {
/* 194 */         return false;
/*     */       }
/* 196 */       this.miningTicker = -1;
/* 197 */       this.stuckOn = id.func_149739_a();
/* 198 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     this.stuckOn = null;
/* 203 */     this.miningTicker = (short)(this.miningTicker + 1);
/* 204 */     this.energy--;
/* 205 */     IMiningDrill drill = (IMiningDrill)this.inventory[3].func_77973_b();
/* 206 */     if (!drill.isBasicDrill(this.inventory[3])) {
/*     */       
/* 208 */       this.miningTicker = (short)(this.miningTicker + drill.getExtraSpeed(this.inventory[3]));
/* 209 */       this.energy -= drill.getExtraEnergyCost(this.inventory[3]);
/*     */     } 
/*     */ 
/*     */     
/* 213 */     if (this.miningTicker >= 200) {
/*     */       
/* 215 */       this.miningTicker = 0;
/* 216 */       mineBlock();
/* 217 */       return true;
/*     */     } 
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueableOre(int x, int y, int z) {
/* 225 */     ItemStack stack = this.inventory[1];
/* 226 */     if (stack == null)
/*     */     {
/* 228 */       return false;
/*     */     }
/* 230 */     IScannerItem item = (IScannerItem)stack.func_77973_b();
/* 231 */     return item.isValuableOre(stack, this.field_145850_b.func_147439_a(x, y, z), this.field_145850_b.func_72805_g(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public void mineBlock() {
/* 236 */     if (this.inventory[3].func_77973_b() instanceof IMiningDrill)
/*     */     {
/* 238 */       ((IMiningDrill)this.inventory[3].func_77973_b()).useDrill(this.inventory[3]);
/*     */     }
/* 240 */     Block id = this.field_145850_b.func_147439_a(this.currentX, this.currentY, this.currentZ);
/* 241 */     int meta = this.field_145850_b.func_72805_g(this.currentX, this.currentY, this.currentZ);
/* 242 */     boolean liquid = false;
/* 243 */     if (id == Blocks.field_150358_i || id == Blocks.field_150355_j || id == Blocks.field_150356_k || id == Blocks.field_150353_l) {
/*     */       
/* 245 */       liquid = true;
/* 246 */       if (meta != 0)
/*     */       {
/* 248 */         id = null;
/*     */       }
/*     */     } 
/* 251 */     if (!this.field_145850_b.func_147437_c(this.currentX, this.currentY, this.currentZ)) {
/*     */       
/* 253 */       if (!liquid) {
/*     */         
/* 255 */         Block ore = id;
/* 256 */         if (hasEnchantment(this.inventory[3], Enchantment.field_77348_q) && ore.canSilkHarvest(func_145831_w(), (EntityPlayer)FakePlayerFactory.getMinecraft((WorldServer)func_145831_w()), this.currentX, this.currentY, this.currentZ, meta))
/*     */         {
/* 258 */           ArrayList<ItemStack> drops = new ArrayList<>();
/* 259 */           ItemStack stack = createStackedBlock(ore, meta);
/* 260 */           if (stack != null)
/*     */           {
/* 262 */             drops.add(stack);
/*     */           }
/* 264 */           ForgeEventFactory.fireBlockHarvesting(drops, func_145831_w(), ore, this.currentX, this.currentY, this.currentZ, meta, 0, 1.0F, true, (EntityPlayer)FakePlayerFactory.getMinecraft((WorldServer)func_145831_w()));
/* 265 */           StackUtil.distributeDrop((TileEntity)this, drops);
/*     */         }
/*     */         else
/*     */         {
/* 269 */           int fortune = EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, this.inventory[3]);
/* 270 */           ArrayList<ItemStack> drops = ore.getDrops(this.field_145850_b, this.currentX, this.currentY, this.currentZ, meta, fortune);
/* 271 */           ForgeEventFactory.fireBlockHarvesting(drops, func_145831_w(), ore, this.currentX, this.currentY, this.currentZ, meta, fortune, 1.0F, false, (EntityPlayer)FakePlayerFactory.getMinecraft((WorldServer)func_145831_w()));
/* 272 */           StackUtil.distributeDrop((TileEntity)this, drops);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 277 */         if (id == Blocks.field_150358_i || id == Blocks.field_150355_j)
/*     */         {
/* 279 */           usePump(Blocks.field_150355_j);
/*     */         }
/* 281 */         if (id == Blocks.field_150356_k || id == Blocks.field_150353_l)
/*     */         {
/* 283 */           usePump(Blocks.field_150353_l);
/*     */         }
/*     */       } 
/* 286 */       this.field_145850_b.func_147468_f(this.currentX, this.currentY, this.currentZ);
/* 287 */       this.energy -= 2 * (this.field_145848_d - this.currentY);
/*     */     } 
/* 289 */     if (this.currentX == this.field_145851_c && this.currentZ == this.field_145849_e) {
/*     */       
/* 291 */       this.field_145850_b.func_147449_b(this.currentX, this.currentY, this.currentZ, Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()));
/* 292 */       if ((this.inventory[2]).field_77994_a == 0)
/*     */       {
/* 294 */         this.inventory[2] = null;
/*     */       }
/* 296 */       this.energy -= 10;
/*     */     } 
/* 298 */     updateMineTip(this.currentY);
/* 299 */     if ((this.currentX == this.targetX && this.currentY == this.targetY && this.currentZ == this.targetZ) || this.field_145850_b.func_147437_c(this.targetX, this.targetY, this.targetZ) || !isValueableOre(this.targetX, this.targetY, this.targetZ)) {
/*     */       
/* 301 */       this.targetY = -1;
/*     */     }
/*     */     else {
/*     */       
/* 305 */       this.currentX = this.targetX;
/* 306 */       this.currentY = this.targetY;
/* 307 */       this.currentZ = this.targetZ;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack createStackedBlock(Block ore, int meta) {
/* 313 */     int resultMeta = 0;
/* 314 */     if (ore != null) {
/*     */       
/* 316 */       Item item = Item.func_150898_a(ore);
/* 317 */       if (item != null && item.func_77614_k())
/*     */       {
/* 319 */         resultMeta = meta;
/*     */       }
/*     */     } 
/* 322 */     return new ItemStack(ore, 1, resultMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasEnchantment(ItemStack itemStack, Enchantment ench) {
/* 327 */     NBTTagList list = itemStack.func_77986_q();
/* 328 */     boolean flag = false;
/* 329 */     if (list != null)
/*     */     {
/* 331 */       for (int i = 0; i < list.func_74745_c(); i++) {
/*     */         
/* 333 */         NBTTagCompound data = list.func_150305_b(i);
/* 334 */         int id = data.func_74765_d("id");
/* 335 */         if (id == ench.field_77352_x) {
/*     */           
/* 337 */           flag = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 342 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean withdrawPipe() {
/* 347 */     int y = getPipeTip();
/* 348 */     Block block = this.field_145850_b.func_147439_a(this.field_145851_c, y, this.field_145849_e);
/* 349 */     if (!this.field_145850_b.func_147437_c(this.field_145851_c, y, this.field_145849_e));
/*     */     
/* 351 */     if (this.field_145848_d - y > 1)
/*     */     {
/* 353 */       StackUtil.distributeDrop((TileEntity)this, block.getDrops(this.field_145850_b, this.field_145851_c, y, this.field_145849_e, this.field_145850_b.func_72805_g(this.field_145851_c, y, this.field_145849_e), 0));
/*     */     }
/* 355 */     this.field_145850_b.func_147468_f(this.field_145851_c, y, this.field_145849_e);
/*     */     
/* 357 */     if (this.inventory[2] != null && this.inventory[2].func_77973_b() != Ic2Items.miningPipe.func_77973_b() && Block.func_149634_a(this.inventory[2].func_77973_b()) != Blocks.field_150350_a) {
/*     */       
/* 359 */       this.field_145850_b.func_147465_d(this.field_145851_c, y, this.field_145849_e, Block.func_149634_a(this.inventory[2].func_77973_b()), this.inventory[2].func_77960_j(), 3);
/* 360 */       ItemStack itemStack = this.inventory[2];
/* 361 */       itemStack.field_77994_a--;
/* 362 */       if ((this.inventory[2]).field_77994_a == 0)
/*     */       {
/* 364 */         this.inventory[2] = null;
/*     */       }
/* 366 */       updateMineTip(y + 1);
/* 367 */       return true;
/*     */     } 
/* 369 */     updateMineTip(y + 1);
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMineTip(int low) {
/* 375 */     if (low == this.field_145848_d) {
/*     */       return;
/*     */     }
/*     */     
/* 379 */     int x = this.field_145851_c;
/* 380 */     int y = this.field_145848_d - 1;
/* 381 */     int z = this.field_145849_e;
/* 382 */     while (y > low) {
/*     */       
/* 384 */       Block id = this.field_145850_b.func_147439_a(x, y, z);
/* 385 */       if (id != Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()) && this.inventory[2] != null && (this.inventory[2]).field_77994_a > 0) {
/*     */         
/* 387 */         this.field_145850_b.func_147449_b(x, y, z, Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()));
/* 388 */         ItemStack itemStack = this.inventory[2];
/* 389 */         itemStack.field_77994_a--;
/* 390 */         if ((this.inventory[2]).field_77994_a <= 0)
/*     */         {
/* 392 */           this.inventory[2] = null;
/*     */         }
/*     */       } 
/* 395 */       y--;
/*     */     } 
/* 397 */     this.field_145850_b.func_147449_b(x, low, z, Block.func_149634_a(Ic2Items.miningPipeTip.func_77973_b()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReachTarget(int x, int y, int z, boolean ignore) {
/* 402 */     if (this.field_145851_c == x && this.field_145849_e == z)
/*     */     {
/* 404 */       return true;
/*     */     }
/* 406 */     if (!ignore && !canPass(this.field_145850_b.func_147439_a(x, y, z)) && !this.field_145850_b.func_147437_c(x, y, z))
/*     */     {
/* 408 */       return false;
/*     */     }
/* 410 */     int xdif = x - this.field_145851_c;
/* 411 */     int zdif = z - this.field_145849_e;
/* 412 */     if (Math.abs(xdif) > Math.abs(zdif)) {
/*     */       
/* 414 */       if (xdif > 0)
/*     */       {
/* 416 */         x--;
/*     */       }
/*     */       else
/*     */       {
/* 420 */         x++;
/*     */       }
/*     */     
/* 423 */     } else if (zdif > 0) {
/*     */       
/* 425 */       z--;
/*     */     }
/*     */     else {
/*     */       
/* 429 */       z++;
/*     */     } 
/* 431 */     return canReachTarget(x, y, z, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aquireTarget() {
/* 436 */     int y = getPipeTip();
/* 437 */     if (y >= this.field_145848_d || this.inventory[1] == null || !(this.inventory[1].func_77973_b() instanceof IScannerItem)) {
/*     */       
/* 439 */       setTarget(this.field_145851_c, y - 1, this.field_145849_e);
/*     */       return;
/*     */     } 
/* 442 */     IScannerItem item = (IScannerItem)this.inventory[1].func_77973_b();
/* 443 */     int scanrange = item.startLayerScan(this.inventory[1]);
/*     */     
/* 445 */     if (scanrange > 0) {
/*     */       
/* 447 */       int minX = this.field_145851_c - scanrange;
/* 448 */       int maxX = this.field_145851_c + scanrange;
/* 449 */       int minZ = this.field_145849_e - scanrange;
/* 450 */       int maxZ = this.field_145849_e + scanrange;
/* 451 */       Set<ChunkCoordinates> visited = new HashSet<>();
/* 452 */       List<ChunkCoordinates> toCheck = new ArrayList<>();
/* 453 */       visited.add(new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/* 454 */       toCheck.addAll(getAroundCoords(new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e)));
/* 455 */       Collections.shuffle(toCheck);
/* 456 */       while (!toCheck.isEmpty()) {
/*     */         
/* 458 */         ChunkCoordinates coords = toCheck.remove(0);
/* 459 */         int x = coords.field_71574_a;
/* 460 */         int z = coords.field_71573_c;
/* 461 */         if (visited.contains(coords) || x < minX || x > maxX || z < minZ || z > maxZ) {
/*     */           continue;
/*     */         }
/*     */         
/* 465 */         visited.add(coords);
/* 466 */         if (!canMine(x, y, z)) {
/*     */           continue;
/*     */         }
/*     */         
/* 470 */         Block n = this.field_145850_b.func_147439_a(x, y, z);
/* 471 */         int m = this.field_145850_b.func_72805_g(x, y, z);
/* 472 */         if (isAnyPumpConnected() && this.field_145850_b.func_72805_g(x, y, z) == 0 && (n == Blocks.field_150356_k || n == Blocks.field_150353_l)) {
/*     */           
/* 474 */           setTarget(x, y, z);
/*     */           return;
/*     */         } 
/* 477 */         if (item.isValuableOre(this.inventory[1], n, m)) {
/*     */           
/* 479 */           setTarget(x, y, z);
/*     */           return;
/*     */         } 
/* 482 */         toCheck.addAll(getAroundCoords(coords));
/*     */       } 
/*     */     } 
/* 485 */     setTarget(this.field_145851_c, y - 1, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ChunkCoordinates> getAroundCoords(ChunkCoordinates par1) {
/* 490 */     List<ChunkCoordinates> result = new ArrayList<>();
/* 491 */     for (ForgeDirection dir : validDirs)
/*     */     {
/* 493 */       result.add(new ChunkCoordinates(par1.field_71574_a + dir.offsetX, par1.field_71572_b + dir.offsetY, par1.field_71573_c + dir.offsetZ));
/*     */     }
/* 495 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(int x, int y, int z) {
/* 500 */     this.targetX = x;
/* 501 */     this.targetY = y;
/* 502 */     this.targetZ = z;
/* 503 */     this.currentX = x;
/* 504 */     this.currentY = y;
/* 505 */     this.currentZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPipeTip() {
/*     */     int y;
/* 511 */     for (y = this.field_145848_d; this.field_145850_b.func_147439_a(this.field_145851_c, y - 1, this.field_145849_e) == Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()) || this.field_145850_b.func_147439_a(this.field_145851_c, y - 1, this.field_145849_e) == Block.func_149634_a(Ic2Items.miningPipeTip.func_77973_b()); y--);
/*     */ 
/*     */     
/* 514 */     return y;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPass(Block block) {
/* 519 */     return (block == Blocks.field_150350_a || block == Blocks.field_150358_i || block == Blocks.field_150355_j || block == Blocks.field_150356_k || block == Blocks.field_150353_l || block == Block.func_149634_a(Ic2Items.miner.func_77973_b()) || block == Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()) || block == Block.func_149634_a(Ic2Items.miningPipeTip.func_77973_b()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperating() {
/* 524 */     return (this.energy > 100 && canOperate());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canOperate() {
/* 529 */     return (this.inventory[2] != null && this.inventory[3] != null && this.inventory[2].func_77973_b() == Ic2Items.miningPipe.func_77973_b() && canMiningDrillBeUsed() && !isStuck());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMiningDrillBeUsed() {
/* 534 */     if (!(this.inventory[3].func_77973_b() instanceof IMiningDrill))
/*     */     {
/* 536 */       return false;
/*     */     }
/* 538 */     IMiningDrill drill = (IMiningDrill)this.inventory[3].func_77973_b();
/* 539 */     if (!drill.canMine(this.inventory[3])) {
/*     */       
/* 541 */       if (!(drill instanceof IElectricItem))
/*     */       {
/* 543 */         return false;
/*     */       }
/* 545 */       IElectricItem item = (IElectricItem)drill;
/* 546 */       if (item.getMaxCharge(this.inventory[3]) == 0.0D || item.getTransferLimit(this.inventory[3]) == 0.0D)
/*     */       {
/* 548 */         return false;
/*     */       }
/*     */     } 
/* 551 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStuck() {
/* 556 */     return (this.miningTicker < 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStuckOn() {
/* 561 */     return this.stuckOn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMine(int x, int y, int z) {
/* 566 */     Block id = this.field_145850_b.func_147439_a(x, y, z);
/* 567 */     int meta = this.field_145850_b.func_72805_g(x, y, z);
/* 568 */     if (this.field_145850_b.func_147437_c(x, y, z))
/*     */     {
/* 570 */       return canMineEvent(x, y, z, id, meta);
/*     */     }
/* 572 */     if (id == Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()) || id == Block.func_149634_a(Ic2Items.miningPipeTip.func_77973_b()) || id == Blocks.field_150486_ae)
/*     */     {
/* 574 */       return false;
/*     */     }
/* 576 */     if ((id == Blocks.field_150358_i || id == Blocks.field_150355_j || id == Blocks.field_150356_k || id == Blocks.field_150353_l) && isPumpConnected())
/*     */     {
/* 578 */       return canMineEvent(x, y, z, id, meta);
/*     */     }
/* 580 */     Block block = id;
/* 581 */     if (block.func_149712_f(this.field_145850_b, x, y, z) < 0.0F)
/*     */     {
/* 583 */       return false;
/*     */     }
/* 585 */     if (block.func_149678_a(meta, false) && block.func_149688_o().func_76229_l())
/*     */     {
/* 587 */       return canMineEvent(x, y, z, block, meta);
/*     */     }
/* 589 */     if (id == Blocks.field_150321_G)
/*     */     {
/* 591 */       return canMineEvent(x, y, z, block, meta);
/*     */     }
/* 593 */     if (this.inventory[3] != null) {
/*     */       
/* 595 */       if (!ForgeHooks.canToolHarvestBlock(block, meta, this.inventory[3]))
/*     */       {
/* 597 */         if (!this.inventory[3].func_150998_b(block))
/*     */         {
/* 599 */           return false;
/*     */         }
/*     */       }
/* 602 */       return canMineEvent(x, y, z, block, meta);
/*     */     } 
/* 604 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMineEvent(int x, int y, int z, Block block, int meta) {
/* 609 */     BlockEvent.BreakEvent evt = new BlockEvent.BreakEvent(x, y, z, this.field_145850_b, block, meta, (EntityPlayer)FakePlayerFactory.getMinecraft((WorldServer)this.field_145850_b));
/* 610 */     evt.setExpToDrop(0);
/* 611 */     MinecraftForge.EVENT_BUS.post((Event)evt);
/* 612 */     return !evt.isCanceled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWithdraw() {
/* 617 */     return (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == Block.func_149634_a(Ic2Items.miningPipe.func_77973_b()) || this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == Block.func_149634_a(Ic2Items.miningPipeTip.func_77973_b()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPumpConnected() {
/* 622 */     return ((this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).canHarvest()) || (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e)).canHarvest()) || (this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)).canHarvest()) || (this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)).canHarvest()) || (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)).canHarvest()) || (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)).canHarvest()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnyPumpConnected() {
/* 627 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof TileEntityPump || this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityPump || this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump || this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump || this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityPump || this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityPump);
/*     */   }
/*     */ 
/*     */   
/*     */   public void usePump(Block water) {
/* 632 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).canHarvest()) {
/*     */       
/* 634 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).pumpThis(water);
/*     */       return;
/*     */     } 
/* 637 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e)).canHarvest()) {
/*     */       
/* 639 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e)).pumpThis(water);
/*     */       return;
/*     */     } 
/* 642 */     if (this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)).canHarvest()) {
/*     */       
/* 644 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)).pumpThis(water);
/*     */       return;
/*     */     } 
/* 647 */     if (this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)).canHarvest()) {
/*     */       
/* 649 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)).pumpThis(water);
/*     */       return;
/*     */     } 
/* 652 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)).canHarvest()) {
/*     */       
/* 654 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)).pumpThis(water);
/*     */       return;
/*     */     } 
/* 657 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityPump && ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)).canHarvest())
/*     */     {
/* 659 */       ((TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)).pumpThis(water);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 666 */     return "Miner";
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeEnergyScaled(int i) {
/* 671 */     if (this.energy <= 0)
/*     */     {
/* 673 */       return 0;
/*     */     }
/* 675 */     int r = this.energy * i / 1000;
/* 676 */     if (r > i)
/*     */     {
/* 678 */       r = i;
/*     */     }
/* 680 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 686 */     return (ContainerIC2)new ContainerMiner(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 692 */     return "block.machine.gui.GuiMiner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {
/* 698 */     if (this.stuckOn != null)
/*     */     {
/* 700 */       entityPlayer.func_146105_b((IChatComponent)new ChatComponentText("Is Stuck on: " + this.stuckOn + " Coords: x" + this.currentX + " y:" + this.currentY + " z:" + this.currentZ));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 706 */     if (field.equals("active") && this.prevActive != getActive()) {
/*     */       
/* 708 */       if (this.audioSource != null && this.audioSource.isRemoved())
/*     */       {
/* 710 */         this.audioSource = null;
/*     */       }
/*     */       
/* 713 */       if (this.audioSource == null)
/*     */       {
/* 715 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/MinerOp.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */       }
/* 717 */       if (getActive()) {
/*     */         
/* 719 */         if (this.audioSource != null)
/*     */         {
/* 721 */           this.audioSource.play();
/*     */         }
/*     */       }
/* 724 */       else if (this.audioSource != null) {
/*     */         
/* 726 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/* 729 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 734 */     ForgeDirection leftSide = null;
/* 735 */     ForgeDirection rightSide = null;
/* 736 */     ForgeDirection frontSide = null;
/* 737 */     ForgeDirection backSide = null;
/* 738 */     switch (getFacing()) {
/*     */ 
/*     */       
/*     */       case 2:
/* 742 */         leftSide = ForgeDirection.WEST;
/* 743 */         rightSide = ForgeDirection.EAST;
/* 744 */         frontSide = ForgeDirection.SOUTH;
/* 745 */         backSide = ForgeDirection.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 750 */         leftSide = ForgeDirection.EAST;
/* 751 */         rightSide = ForgeDirection.WEST;
/* 752 */         frontSide = ForgeDirection.NORTH;
/* 753 */         backSide = ForgeDirection.SOUTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 758 */         leftSide = ForgeDirection.SOUTH;
/* 759 */         rightSide = ForgeDirection.NORTH;
/* 760 */         frontSide = ForgeDirection.EAST;
/* 761 */         backSide = ForgeDirection.WEST;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 766 */         leftSide = ForgeDirection.NORTH;
/* 767 */         rightSide = ForgeDirection.SOUTH;
/* 768 */         frontSide = ForgeDirection.WEST;
/* 769 */         backSide = ForgeDirection.EAST;
/*     */         break;
/*     */     } 
/*     */     
/* 773 */     if (side == leftSide.ordinal() || side == frontSide.ordinal())
/*     */     {
/* 775 */       return new int[] { 3 };
/*     */     }
/* 777 */     if (side == rightSide.ordinal() || side == backSide.ordinal())
/*     */     {
/* 779 */       return new int[] { 1 };
/*     */     }
/* 781 */     if (side == 0)
/*     */     {
/* 783 */       return new int[] { 0 };
/*     */     }
/* 785 */     return new int[] { 2 };
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeInventorySide(ForgeDirection side) {
/* 790 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 796 */     return 1 + ((this.inventory[3] != null && this.inventory[3].func_77973_b() instanceof IMiningDrill) ? ((IMiningDrill)this.inventory[3].func_77973_b()).getExtraEnergyCost(this.inventory[3]) : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */