/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.network.INetworkFieldData;
/*     */ import ic2.api.reactor.IReactorPlanner;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ReactorLogicBase
/*     */   implements IReactorPlanner, IInventory, INetworkFieldData
/*     */ {
/*     */   TileEntityReactorPlanner planner;
/*     */   public int ticksDone;
/*     */   public int ticksLeft;
/*     */   public int maxTick;
/*     */   public boolean custom;
/*     */   public int currentHeat;
/*     */   public int maxHeat;
/*     */   public float explosionEffect;
/*     */   public float production;
/*     */   public boolean producing;
/*     */   public boolean isExploded;
/*     */   public boolean didComponentBreak;
/*     */   public boolean isValid;
/*     */   public boolean collecting;
/*     */   public int fuelPulses;
/*     */   public int reEnrichPulses;
/*  51 */   public ItemStack[] inv = new ItemStack[54];
/*     */ 
/*     */   
/*     */   public ReactorLogicBase(TileEntityReactorPlanner tile) {
/*  55 */     this.planner = tile;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void onTick();
/*     */ 
/*     */   
/*     */   public abstract void onFinished();
/*     */ 
/*     */   
/*     */   public abstract void validate();
/*     */   
/*     */   public abstract void reset();
/*     */   
/*     */   public abstract IReactorPrediction createPrediction();
/*     */   
/*     */   public abstract TileEntityReactorPlanner.ReactorBackup createBackup();
/*     */   
/*     */   public abstract void restoreFromBackup(TileEntityReactorPlanner.ReactorBackup paramReactorBackup);
/*     */   
/*     */   public abstract boolean isSteamLogic();
/*     */   
/*     */   public void pauseProduction() {
/*  78 */     this.producing = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void continueProduction() {
/*  83 */     this.producing = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  88 */     return this.isValid;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean didReactorBreak() {
/*  93 */     return (this.didComponentBreak || this.isExploded);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/*  98 */     return (this.ticksLeft == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityReactorPlanner getPlanner() {
/* 103 */     return this.planner;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReactorSize() {
/* 108 */     return 3 + (getPlanner()).reactorSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSizeUpdate() {
/* 113 */     int size = getReactorSize();
/* 114 */     for (int x = size; x < 9; x++) {
/*     */       
/* 116 */       for (int y = 0; y < 6; y++)
/*     */       {
/* 118 */         func_70299_a(y * 9 + x, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSignal(boolean redstone) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 131 */     return this.inv.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int par1) {
/* 137 */     return this.inv[par1];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
/* 142 */     if (this.inv[p_70298_1_] != null) {
/*     */ 
/*     */ 
/*     */       
/* 146 */       if ((this.inv[p_70298_1_]).field_77994_a <= p_70298_2_) {
/*     */         
/* 148 */         ItemStack itemStack = this.inv[p_70298_1_];
/* 149 */         this.inv[p_70298_1_] = null;
/* 150 */         return itemStack;
/*     */       } 
/*     */ 
/*     */       
/* 154 */       ItemStack itemstack = this.inv[p_70298_1_].func_77979_a(p_70298_2_);
/*     */       
/* 156 */       if ((this.inv[p_70298_1_]).field_77994_a == 0)
/*     */       {
/* 158 */         this.inv[p_70298_1_] = null;
/*     */       }
/*     */       
/* 161 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int p_70304_1_) {
/* 172 */     if (this.inv[p_70304_1_] != null) {
/*     */       
/* 174 */       ItemStack itemstack = this.inv[p_70304_1_];
/* 175 */       this.inv[p_70304_1_] = null;
/* 176 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
/* 186 */     this.inv[p_70299_1_] = p_70299_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 192 */     return "ReactorLogicBase";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 204 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer p_70300_1_) {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 237 */     return getPlanner().func_145831_w();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkCoordinates getPosition() {
/* 243 */     TileEntityReactorPlanner planner = getPlanner();
/* 244 */     return new ChunkCoordinates(planner.field_145851_c, planner.field_145848_d, planner.field_145849_e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeat() {
/* 250 */     return this.currentHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeat(int heat) {
/* 256 */     this.currentHeat = heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int addHeat(int amount) {
/* 262 */     return this.currentHeat += amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat() {
/* 268 */     return this.maxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxHeat(int newMaxHeat) {
/* 274 */     this.maxHeat = newMaxHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitHeat(int heat) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeatEffectModifier() {
/* 285 */     return this.explosionEffect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeatEffectModifier(float newHEM) {
/* 291 */     this.explosionEffect = newHEM;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getReactorEnergyOutput() {
/* 297 */     return this.production;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getReactorEUEnergyOutput() {
/* 303 */     return (this.production * IC2.energyGeneratorNuclear);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float addOutput(float energy) {
/* 309 */     return this.production += energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemAt(int x, int y) {
/* 315 */     return getMatrixItem(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemAt(int x, int y, ItemStack item) {
/* 321 */     setMatrixItem(x, y, item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMatrixItem(int x, int y, ItemStack item) {
/* 326 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6) {
/*     */       return;
/*     */     }
/*     */     
/* 330 */     func_70299_a(y * 9 + x, item);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getMatrixItem(int x, int y) {
/* 335 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6)
/*     */     {
/* 337 */       return null;
/*     */     }
/* 339 */     return func_70301_a(y * 9 + x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void explode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/* 351 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean produceEnergy() {
/* 357 */     return this.producing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFluidCooled() {
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent getComponent(int slot) {
/* 368 */     ItemStack item = this.inv[slot];
/* 369 */     if (item == null)
/*     */     {
/* 371 */       return null;
/*     */     }
/* 373 */     if (item.func_77973_b() instanceof IReactorPlannerComponent)
/*     */     {
/* 375 */       return (IReactorPlannerComponent)item.func_77973_b();
/*     */     }
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DataInput stream) {
/*     */     try {
/* 385 */       this.ticksDone = stream.readInt();
/* 386 */       this.ticksLeft = stream.readInt();
/* 387 */       this.maxTick = stream.readInt();
/* 388 */       this.currentHeat = stream.readInt();
/* 389 */       this.maxHeat = stream.readInt();
/* 390 */       this.explosionEffect = stream.readFloat();
/* 391 */       this.production = stream.readFloat();
/* 392 */       this.producing = stream.readBoolean();
/* 393 */       this.isExploded = stream.readBoolean();
/* 394 */       this.isValid = stream.readBoolean();
/* 395 */       this.custom = stream.readBoolean();
/*     */     }
/* 397 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DataOutput stream) {
/*     */     try {
/* 407 */       stream.writeInt(this.ticksDone);
/* 408 */       stream.writeInt(this.ticksLeft);
/* 409 */       stream.writeInt(this.maxTick);
/* 410 */       stream.writeInt(this.currentHeat);
/* 411 */       stream.writeInt(this.maxHeat);
/* 412 */       stream.writeFloat(this.explosionEffect);
/* 413 */       stream.writeFloat(this.production);
/* 414 */       stream.writeBoolean(this.producing);
/* 415 */       stream.writeBoolean(this.isExploded);
/* 416 */       stream.writeBoolean(this.isValid);
/* 417 */       stream.writeBoolean(this.custom);
/*     */     }
/* 419 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStateFromNBT(NBTTagCompound nbt) {
/* 426 */     this.ticksDone = nbt.func_74762_e("TimeGone");
/* 427 */     this.ticksLeft = nbt.func_74762_e("TimeLeft");
/* 428 */     this.maxTick = nbt.func_74762_e("TotalTime");
/* 429 */     this.currentHeat = nbt.func_74762_e("Heat");
/* 430 */     this.maxHeat = nbt.func_74762_e("MaxHeat");
/* 431 */     this.explosionEffect = nbt.func_74760_g("ExplosionEffect");
/* 432 */     this.production = nbt.func_74760_g("Production");
/* 433 */     this.producing = nbt.func_74767_n("Producing");
/* 434 */     this.isExploded = nbt.func_74767_n("IsExploded");
/* 435 */     this.isValid = nbt.func_74767_n("isValid");
/* 436 */     this.custom = nbt.func_74767_n("Custom");
/* 437 */     NBTTagList list = nbt.func_150295_c("Setup", 10);
/* 438 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 440 */       NBTTagCompound data = list.func_150305_b(i);
/* 441 */       this.inv[data.func_74762_e("Slot")] = ItemStack.func_77949_a(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStateToNBT(NBTTagCompound nbt) {
/* 447 */     nbt.func_74768_a("TimeGone", this.ticksDone);
/* 448 */     nbt.func_74768_a("TimeLeft", this.ticksLeft);
/* 449 */     nbt.func_74768_a("TotalTime", this.maxTick);
/* 450 */     nbt.func_74768_a("Heat", this.currentHeat);
/* 451 */     nbt.func_74768_a("MaxHeat", this.maxHeat);
/* 452 */     nbt.func_74776_a("ExplosionEffect", this.explosionEffect);
/* 453 */     nbt.func_74776_a("Production", this.production);
/* 454 */     nbt.func_74757_a("Producing", this.producing);
/* 455 */     nbt.func_74757_a("IsExploded", this.isExploded);
/* 456 */     nbt.func_74757_a("isValid", this.isValid);
/* 457 */     nbt.func_74757_a("Custom", this.custom);
/* 458 */     NBTTagList list = new NBTTagList();
/* 459 */     for (int i = 0; i < 54; i++) {
/*     */       
/* 461 */       if (this.inv[i] != null) {
/*     */         
/* 463 */         NBTTagCompound data = new NBTTagCompound();
/* 464 */         this.inv[i].func_77955_b(data);
/* 465 */         data.func_74768_a("Slot", i);
/* 466 */         list.func_74742_a((NBTBase)data);
/*     */       } 
/*     */     } 
/* 469 */     nbt.func_74782_a("Setup", (NBTBase)list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 474 */     nbt.func_74768_a("TimeGone", this.ticksDone);
/* 475 */     nbt.func_74768_a("TimeLeft", this.ticksLeft);
/* 476 */     nbt.func_74768_a("TotalTime", this.maxTick);
/* 477 */     nbt.func_74768_a("Heat", this.currentHeat);
/* 478 */     nbt.func_74768_a("MaxHeat", this.maxHeat);
/* 479 */     nbt.func_74776_a("ExplosionEffect", this.explosionEffect);
/* 480 */     nbt.func_74776_a("Production", this.production);
/* 481 */     nbt.func_74757_a("Producing", this.producing);
/* 482 */     nbt.func_74757_a("IsExploded", this.isExploded);
/* 483 */     nbt.func_74757_a("isValid", this.isValid);
/* 484 */     nbt.func_74757_a("Custom", this.custom);
/* 485 */     NBTTagList list = new NBTTagList();
/* 486 */     for (int i = 0; i < 54; i++) {
/*     */       
/* 488 */       if (this.inv[i] != null) {
/*     */         
/* 490 */         NBTTagCompound data = new NBTTagCompound();
/* 491 */         this.inv[i].func_77955_b(data);
/* 492 */         data.func_74768_a("Slot", i);
/* 493 */         list.func_74742_a((NBTBase)data);
/*     */       } 
/*     */     } 
/* 496 */     nbt.func_74782_a("Setup", (NBTBase)list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 501 */     this.ticksDone = nbt.func_74762_e("TimeGone");
/* 502 */     this.ticksLeft = nbt.func_74762_e("TimeLeft");
/* 503 */     this.maxTick = nbt.func_74762_e("TotalTime");
/* 504 */     this.currentHeat = nbt.func_74762_e("Heat");
/* 505 */     this.maxHeat = nbt.func_74762_e("MaxHeat");
/* 506 */     this.explosionEffect = nbt.func_74760_g("ExplosionEffect");
/* 507 */     this.production = nbt.func_74760_g("Production");
/* 508 */     this.producing = nbt.func_74767_n("Producing");
/* 509 */     this.isExploded = nbt.func_74767_n("IsExploded");
/* 510 */     this.isValid = nbt.func_74767_n("isValid");
/* 511 */     this.custom = nbt.func_74767_n("Custom");
/* 512 */     this.inv = new ItemStack[54];
/* 513 */     NBTTagList list = nbt.func_150295_c("Setup", 10);
/* 514 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 516 */       NBTTagCompound data = list.func_150305_b(i);
/* 517 */       this.inv[data.func_74762_e("Slot")] = ItemStack.func_77949_a(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 523 */     this.ticksDone = 0;
/* 524 */     this.ticksLeft = 0;
/* 525 */     this.maxTick = 0;
/* 526 */     this.currentHeat = 0;
/* 527 */     this.maxHeat = 10000;
/* 528 */     this.explosionEffect = 1.0F;
/* 529 */     this.production = 0.0F;
/* 530 */     this.producing = false;
/* 531 */     this.isExploded = false;
/* 532 */     this.isValid = false;
/* 533 */     this.custom = false;
/* 534 */     this.inv = new ItemStack[54];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollecting() {
/* 540 */     return this.collecting;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFuelPulse() {
/* 546 */     this.fuelPulses++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReEnrichPulse() {
/* 552 */     this.reEnrichPulses++;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getTag(NBTTagCompound nbt, String tag) {
/* 557 */     if (!nbt.func_74764_b(tag))
/*     */     {
/* 559 */       nbt.func_74782_a(tag, (NBTBase)new NBTTagCompound());
/*     */     }
/* 561 */     return nbt.func_74775_l(tag);
/*     */   }
/*     */   
/*     */   public static interface IReactorPrediction extends INetworkFieldData {
/*     */     void readFromNBT(NBTTagCompound param1NBTTagCompound);
/*     */     
/*     */     void writeToNBT(NBTTagCompound param1NBTTagCompound);
/*     */     
/*     */     boolean isSteam();
/*     */     
/*     */     void clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\ReactorLogicBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */