/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorProduct;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.generator.container.ContainerNuclearReactor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.Level;
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityNuclearReactor
/*     */   extends TileEntityMachine
/*     */   implements IHasGui, IReactor
/*     */ {
/*  41 */   public static Random randomizer = new Random();
/*  42 */   private static Direction[] directions = Direction.values();
/*  43 */   public int output = 0;
/*     */   public int updateTicker;
/*  45 */   public int heat = 0;
/*  46 */   public int maxHeat = 10000;
/*  47 */   public float hem = 1.0F;
/*     */   public boolean redstonePowered = false;
/*     */   public boolean reactorPower = false;
/*  50 */   public AudioSource audioSourceMain = null;
/*  51 */   public AudioSource audioSourceGeiger = null;
/*  52 */   private int lastOutput = 0;
/*  53 */   private static int[][] slots = new int[10][];
/*     */   
/*  55 */   public int size = 3;
/*  56 */   public long lastCheck = -1L;
/*     */   
/*     */   public boolean hasAddedSomething = false;
/*     */   public boolean refreshRequest = false;
/*     */   
/*     */   public TileEntityNuclearReactor() {
/*  62 */     super(54);
/*  63 */     this.updateTicker = randomizer.nextInt(getTickRate());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  69 */     if (isRendering()) {
/*     */       
/*  71 */       IC2.audioManager.removeSources(this);
/*  72 */       this.audioSourceMain = null;
/*  73 */       this.audioSourceGeiger = null;
/*     */     } 
/*  75 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  81 */     return "Nuclear Reactor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  87 */     super.func_145839_a(nbttagcompound);
/*     */     
/*     */     try {
/*  90 */       this.output = nbttagcompound.func_74762_e("output");
/*     */     }
/*  92 */     catch (Exception e) {
/*     */       
/*  94 */       this.output = nbttagcompound.func_74765_d("output");
/*     */     } 
/*  96 */     this.heat = nbttagcompound.func_74762_e("heat");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 102 */     super.func_145841_b(nbttagcompound);
/* 103 */     nbttagcompound.func_74768_a("heat", this.heat);
/* 104 */     nbttagcompound.func_74768_a("output", this.output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 110 */     super.func_145845_h();
/* 111 */     if (this.updateTicker++ % getTickRate() != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     if (!this.field_145850_b.func_72873_a(this.field_145851_c, this.field_145848_d, this.field_145849_e, 2)) {
/*     */       
/* 117 */       this.output = 0;
/*     */     }
/*     */     else {
/*     */       
/* 121 */       this.reactorPower = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 122 */       dropAllUnfittingStuff();
/* 123 */       this.output = 0;
/* 124 */       this.maxHeat = 10000;
/* 125 */       this.hem = 1.0F;
/* 126 */       processChambers();
/* 127 */       if (calculateHeatEffects()) {
/*     */         return;
/*     */       }
/*     */       
/* 131 */       setActive((this.heat >= 1000 || this.output > 0));
/* 132 */       getNetwork().updateTileEntityField((TileEntity)this, "heat");
/* 133 */       func_70296_d();
/*     */     } 
/* 135 */     getNetwork().updateTileGuiField((TileEntity)this, "output");
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropAllUnfittingStuff() {
/* 140 */     int size = getReactorSize();
/* 141 */     if (!this.hasAddedSomething) {
/*     */       return;
/*     */     }
/*     */     
/* 145 */     this.hasAddedSomething = false;
/* 146 */     for (int x = 0; x < 9; x++) {
/*     */       
/* 148 */       for (int y = 0; y < 6; y++) {
/*     */         
/* 150 */         ItemStack stack = getMatrixCoord(x, y);
/* 151 */         if (stack != null)
/*     */         {
/* 153 */           if (stack.field_77994_a <= 0) {
/*     */             
/* 155 */             setMatrixCoord(x, y, (ItemStack)null);
/*     */           }
/* 157 */           else if (x >= size || !isUsefulItem(stack)) {
/*     */             
/* 159 */             eject(stack);
/* 160 */             setMatrixCoord(x, y, (ItemStack)null);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsefulItem(ItemStack item) {
/* 169 */     if (item == null)
/*     */     {
/* 171 */       return false;
/*     */     }
/* 173 */     Item id = item.func_77973_b();
/* 174 */     if (id instanceof IReactorComponent || (id instanceof IReactorProduct && ((IReactorProduct)id).isProduct(item)))
/*     */     {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     return (id == Ic2Items.reEnrichedUraniumCell.func_77973_b() || id == Ic2Items.nearDepletedUraniumCell.func_77973_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isUsefullReactorItem(ItemStack item) {
/* 184 */     if (item == null)
/*     */     {
/* 186 */       return false;
/*     */     }
/* 188 */     Item id = item.func_77973_b();
/* 189 */     if (id instanceof IReactorComponent || (id instanceof IReactorProduct && ((IReactorProduct)id).isProduct(item)))
/*     */     {
/* 191 */       return true;
/*     */     }
/*     */     
/* 194 */     return (id == Ic2Items.reEnrichedUraniumCell.func_77973_b() || id == Ic2Items.nearDepletedUraniumCell.func_77973_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public void eject(ItemStack drop) {
/* 199 */     if (!isSimulating() || drop == null) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     float f = 0.7F;
/* 204 */     double d = (this.field_145850_b.field_73012_v.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 205 */     double d2 = (this.field_145850_b.field_73012_v.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 206 */     double d3 = (this.field_145850_b.field_73012_v.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 207 */     EntityItem entityitem = new EntityItem(this.field_145850_b, this.field_145851_c + d, this.field_145848_d + d2, this.field_145849_e + d3, drop);
/* 208 */     entityitem.field_145804_b = 10;
/* 209 */     this.field_145850_b.func_72838_d((Entity)entityitem);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean calculateHeatEffects() {
/* 214 */     if (this.heat < 4000 || !isSimulating() || IC2.explosionPowerReactorMax <= 0.0F)
/*     */     {
/* 216 */       return false;
/*     */     }
/* 218 */     float power = this.heat / this.maxHeat;
/* 219 */     if (power >= 1.0F) {
/*     */       
/* 221 */       explode();
/* 222 */       return true;
/*     */     } 
/* 224 */     if (power >= 0.85F && this.field_145850_b.field_73012_v.nextFloat() <= 0.2F * this.hem) {
/*     */       
/* 226 */       int[] coord = getRandCoord(2);
/* 227 */       if (coord != null) {
/*     */         
/* 229 */         Block id = this.field_145850_b.func_147439_a(coord[0], coord[1], coord[2]);
/* 230 */         if (id == null) {
/*     */           
/* 232 */           this.field_145850_b.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */         }
/* 234 */         else if (id.func_149712_f(this.field_145850_b, coord[0], coord[1], coord[2]) <= -1.0F) {
/*     */           
/* 236 */           Material mat = id.func_149688_o();
/* 237 */           if (mat == Material.field_151576_e || mat == Material.field_151573_f || mat == Material.field_151587_i || mat == Material.field_151578_c || mat == Material.field_151571_B) {
/*     */             
/* 239 */             this.field_145850_b.func_147465_d(coord[0], coord[1], coord[2], (Block)Blocks.field_150356_k, 15, 3);
/*     */           }
/*     */           else {
/*     */             
/* 243 */             this.field_145850_b.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 248 */     if (power >= 0.7F) {
/*     */       
/* 250 */       List<EntityLivingBase> list1 = this.field_145850_b.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a((this.field_145851_c - 3), (this.field_145848_d - 3), (this.field_145849_e - 3), (this.field_145851_c + 4), (this.field_145848_d + 4), (this.field_145849_e + 4)));
/* 251 */       for (int l = 0; l < list1.size(); l++) {
/*     */         
/* 253 */         EntityLivingBase ent = list1.get(l);
/* 254 */         ent.func_70097_a((DamageSource)IC2DamageSource.radiation, (int)(this.field_145850_b.field_73012_v.nextInt(4) * this.hem));
/*     */       } 
/*     */     } 
/* 257 */     if (power >= 0.5F && this.field_145850_b.field_73012_v.nextFloat() <= this.hem) {
/*     */       
/* 259 */       int[] coord = getRandCoord(2);
/* 260 */       if (coord != null) {
/*     */         
/* 262 */         Block id = this.field_145850_b.func_147439_a(coord[0], coord[1], coord[2]);
/* 263 */         if (id != null && id.func_149688_o() == Material.field_151586_h)
/*     */         {
/* 265 */           this.field_145850_b.func_147468_f(coord[0], coord[1], coord[2]);
/*     */         }
/*     */       } 
/*     */     } 
/* 269 */     if (power >= 0.4F && this.field_145850_b.field_73012_v.nextFloat() <= this.hem) {
/*     */       
/* 271 */       int[] coord = getRandCoord(2);
/* 272 */       if (coord != null) {
/*     */         
/* 274 */         Block id = this.field_145850_b.func_147439_a(coord[0], coord[1], coord[2]);
/* 275 */         if (id != null) {
/*     */           
/* 277 */           Material mat = id.func_149688_o();
/* 278 */           if (mat == Material.field_151575_d || mat == Material.field_151584_j || mat == Material.field_151580_n)
/*     */           {
/* 280 */             this.field_145850_b.func_147449_b(coord[0], coord[1], coord[2], (Block)Blocks.field_150480_ab);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getRandCoord(int radius) {
/* 290 */     if (radius <= 0)
/*     */     {
/* 292 */       return null;
/*     */     }
/* 294 */     int[] c = { this.field_145851_c + this.field_145850_b.field_73012_v.nextInt(2 * radius + 1) - radius, this.field_145848_d + this.field_145850_b.field_73012_v.nextInt(2 * radius + 1) - radius, this.field_145849_e + this.field_145850_b.field_73012_v.nextInt(2 * radius + 1) - radius };
/* 295 */     if (c[0] == this.field_145851_c && c[1] == this.field_145848_d && c[2] == this.field_145849_e)
/*     */     {
/* 297 */       return null;
/*     */     }
/* 299 */     return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChambers() {
/* 304 */     int size = getReactorSize();
/* 305 */     for (int pass = 0; pass < 2; pass++) {
/*     */       
/* 307 */       for (int y = 0; y < 6; y++) {
/*     */         
/* 309 */         for (int x = 0; x < size; x++) {
/*     */           
/* 311 */           ItemStack thing = getMatrixCoord(x, y);
/* 312 */           if (thing != null && thing.func_77973_b() instanceof IReactorComponent) {
/*     */             
/* 314 */             IReactorComponent comp = (IReactorComponent)thing.func_77973_b();
/* 315 */             comp.processChamber(this, thing, x, y, (pass == 0));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean produceEnergy() {
/* 325 */     return ((this.redstonePowered || this.reactorPower) && IC2.energyGeneratorNuclear != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getMatrixCoord(int x, int y) {
/* 330 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6)
/*     */     {
/* 332 */       return null;
/*     */     }
/* 334 */     return func_70301_a(x + y * 9);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMatrixCoord(int x, int y, ItemStack stack) {
/* 339 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6) {
/*     */       return;
/*     */     }
/*     */     
/* 343 */     func_70299_a(x + y * 9, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 349 */     super.func_70299_a(i, itemstack);
/* 350 */     if (itemstack != null)
/*     */     {
/* 352 */       this.hasAddedSomething = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReactorSize() {
/* 358 */     if (this.lastCheck < getWorld().func_82737_E() || this.refreshRequest)
/*     */     {
/* 360 */       updateReactorSize();
/*     */     }
/* 362 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateReactorSize() {
/* 367 */     this.refreshRequest = false;
/* 368 */     this.lastCheck = getWorld().func_82737_E() + 20L;
/* 369 */     int lastSize = this.size;
/* 370 */     this.size = 3;
/* 371 */     for (Direction direction : directions) {
/*     */       
/* 373 */       TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 374 */       if (target instanceof TileEntityReactorChamber)
/*     */       {
/* 376 */         this.size++;
/*     */       }
/*     */     } 
/* 379 */     if (lastSize != this.size)
/*     */     {
/* 381 */       this.hasAddedSomething = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshChambers() {
/* 387 */     this.refreshRequest = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/* 393 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 399 */     return (ContainerIC2)new ContainerNuclearReactor(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 405 */     return "block.generator.gui.GuiNuclearReactor";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 416 */     if (field.equals("output")) {
/*     */       
/* 418 */       if (this.output > 0) {
/*     */         
/* 420 */         if (this.audioSourceMain != null && this.audioSourceMain.isRemoved())
/*     */         {
/* 422 */           this.audioSourceMain = null;
/*     */         }
/* 424 */         if (this.audioSourceGeiger != null && this.audioSourceGeiger.isRemoved())
/*     */         {
/* 426 */           this.audioSourceGeiger = null;
/*     */         }
/*     */         
/* 429 */         if (this.lastOutput <= 0) {
/*     */           
/* 431 */           if (this.audioSourceMain == null)
/*     */           {
/* 433 */             this.audioSourceMain = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/NuclearReactorLoop.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */           }
/* 435 */           if (this.audioSourceMain != null)
/*     */           {
/* 437 */             this.audioSourceMain.play();
/*     */           }
/*     */         } 
/* 440 */         if (this.output < 40) {
/*     */           
/* 442 */           if (this.lastOutput <= 0 || this.lastOutput >= 40)
/*     */           {
/* 444 */             if (this.audioSourceGeiger != null)
/*     */             {
/* 446 */               this.audioSourceGeiger.remove();
/*     */             }
/* 448 */             this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerLowEU.ogg", true, false, IC2.audioManager.defaultVolume);
/* 449 */             if (this.audioSourceGeiger != null)
/*     */             {
/* 451 */               this.audioSourceGeiger.play();
/*     */             }
/*     */           }
/*     */         
/* 455 */         } else if (this.output < 80) {
/*     */           
/* 457 */           if (this.lastOutput < 40 || this.lastOutput >= 80)
/*     */           {
/* 459 */             if (this.audioSourceGeiger != null)
/*     */             {
/* 461 */               this.audioSourceGeiger.remove();
/*     */             }
/* 463 */             this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerMedEU.ogg", true, false, IC2.audioManager.defaultVolume);
/* 464 */             if (this.audioSourceGeiger != null)
/*     */             {
/* 466 */               this.audioSourceGeiger.play();
/*     */             }
/*     */           }
/*     */         
/* 470 */         } else if (this.output >= 80 && this.lastOutput < 80) {
/*     */           
/* 472 */           if (this.audioSourceGeiger != null)
/*     */           {
/* 474 */             this.audioSourceGeiger.remove();
/*     */           }
/* 476 */           this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerHighEU.ogg", true, false, IC2.audioManager.defaultVolume);
/* 477 */           if (this.audioSourceGeiger != null)
/*     */           {
/* 479 */             this.audioSourceGeiger.play();
/*     */           }
/*     */         }
/*     */       
/* 483 */       } else if (this.lastOutput > 0) {
/*     */         
/* 485 */         if (this.audioSourceMain != null)
/*     */         {
/* 487 */           this.audioSourceMain.stop();
/*     */         }
/* 489 */         if (this.audioSourceGeiger != null)
/*     */         {
/* 491 */           this.audioSourceGeiger.stop();
/*     */         }
/*     */       } 
/* 494 */       this.lastOutput = this.output;
/*     */     } 
/* 496 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 502 */     return 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkCoordinates getPosition() {
/* 508 */     return new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 514 */     return this.field_145850_b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeat() {
/* 520 */     return this.heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeat(int heat) {
/* 526 */     this.heat = heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int addHeat(int amount) {
/* 532 */     return this.heat += amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitHeat(int heat) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemAt(int x, int y) {
/* 543 */     return getMatrixCoord(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemAt(int x, int y, ItemStack item) {
/* 549 */     setMatrixCoord(x, y, item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void explode() {
/* 555 */     float boomPower = 10.0F;
/* 556 */     float boomMod = 1.0F;
/* 557 */     for (int y = 0; y < 6; y++) {
/*     */       
/* 559 */       for (int x = 0; x < getReactorSize(); x++) {
/*     */         
/* 561 */         ItemStack stack = getMatrixCoord(x, y);
/* 562 */         if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */           
/* 564 */           float f = ((IReactorComponent)stack.func_77973_b()).influenceExplosion(this, stack);
/* 565 */           if (f > 0.0F && f < 1.0F) {
/*     */             
/* 567 */             boomMod *= f;
/*     */           }
/*     */           else {
/*     */             
/* 571 */             boomPower += f;
/*     */           } 
/*     */         } 
/* 574 */         setMatrixCoord(x, y, (ItemStack)null);
/*     */       } 
/*     */     } 
/* 577 */     boomPower *= this.hem * boomMod;
/* 578 */     IC2.log.log(Level.INFO, "Nuclear Reactor at " + this.field_145850_b.field_73011_w.field_76574_g + ":(" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ") melted (explosion power " + boomPower + ")");
/* 579 */     if (boomPower > IC2.explosionPowerReactorMax)
/*     */     {
/* 581 */       boomPower = IC2.explosionPowerReactorMax;
/*     */     }
/* 583 */     for (Direction direction : directions) {
/*     */       
/* 585 */       TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 586 */       if (target instanceof TileEntityReactorChamber)
/*     */       {
/* 588 */         this.field_145850_b.func_147468_f(target.field_145851_c, target.field_145848_d, target.field_145849_e);
/*     */       }
/*     */     } 
/* 591 */     this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 592 */     ExplosionIC2 explosion = new ExplosionIC2(this.field_145850_b, null, this.field_145851_c, this.field_145848_d, this.field_145849_e, boomPower, 0.01F, 1.5F, (DamageSource)IC2DamageSource.nuke);
/* 593 */     explosion.doExplosion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat() {
/* 599 */     return this.maxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxHeat(int newMaxHeat) {
/* 605 */     this.maxHeat = newMaxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeatEffectModifier() {
/* 611 */     return this.hem;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeatEffectModifier(float newHEM) {
/* 617 */     this.hem = newHEM;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getReactorEnergyOutput() {
/* 623 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float addOutput(float energy) {
/* 629 */     return (this.output += (short)(int)energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 634 */     int size = getReactorSize();
/* 635 */     if (slots[size] == null) {
/*     */       
/* 637 */       slots[size] = new int[6 * size];
/* 638 */       int k = 0;
/* 639 */       for (int y = 0; y < 6; y++) {
/*     */         
/* 641 */         for (int x = 0; x < size; x++, k++)
/*     */         {
/* 643 */           slots[size][k] = x + y * 9;
/*     */         }
/*     */       } 
/*     */     } 
/* 647 */     return slots[size];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getReactorEUEnergyOutput() {
/* 653 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSignal(boolean redstone) {
/* 659 */     this.redstonePowered = redstone;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFluidCooled() {
/* 665 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityNuclearReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */