/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityBaseGenerator
/*     */   extends TileEntityMachine
/*     */   implements IEnergySourceInfo, IHasGui, IEnergyContainer
/*     */ {
/*  29 */   public static Random random = new Random();
/*  30 */   public int fuel = 0;
/*  31 */   public short storage = 0;
/*     */   public short maxStorage;
/*     */   public int production;
/*  34 */   public int tier = 1;
/*     */   public int ticksSinceLastActiveUpdate;
/*  36 */   public int activityMeter = 0;
/*     */   public boolean addedToEnergyNet = false;
/*  38 */   public AudioSource audioSource = null;
/*     */ 
/*     */   
/*     */   public TileEntityBaseGenerator(int slots, int production, int maxStorage) {
/*  42 */     super(slots);
/*  43 */     this.production = production;
/*  44 */     this.maxStorage = (short)maxStorage;
/*  45 */     this.ticksSinceLastActiveUpdate = random.nextInt(256);
/*  46 */     addGuiFields(new String[] { "storage", "fuel" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  52 */     super.func_145839_a(nbttagcompound);
/*     */     
/*     */     try {
/*  55 */       this.fuel = nbttagcompound.func_74762_e("fuel");
/*     */     }
/*  57 */     catch (Throwable e) {
/*     */       
/*  59 */       this.fuel = nbttagcompound.func_74765_d("fuel");
/*     */     } 
/*  61 */     this.storage = nbttagcompound.func_74765_d("storage");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  67 */     super.func_145841_b(nbttagcompound);
/*  68 */     nbttagcompound.func_74768_a("fuel", this.fuel);
/*  69 */     nbttagcompound.func_74777_a("storage", this.storage);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeStorageScaled(int i) {
/*  74 */     return this.storage * i / this.maxStorage;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int gaugeFuelScaled(int paramInt);
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  82 */     super.onLoaded();
/*  83 */     if (isSimulating()) {
/*     */       
/*  85 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  86 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  93 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  95 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  96 */       this.addedToEnergyNet = false;
/*     */     } 
/*  98 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 100 */       IC2.audioManager.removeSources(this);
/* 101 */       this.audioSource = null;
/*     */     } 
/* 103 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 109 */     super.func_145845_h();
/* 110 */     boolean needsInvUpdate = false;
/* 111 */     if (needsFuel())
/*     */     {
/* 113 */       if (gainFuel()) {
/*     */         
/* 115 */         needsInvUpdate = true;
/* 116 */         getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/*     */       } 
/*     */     }
/* 119 */     short oldEnergy = this.storage;
/* 120 */     boolean newActive = gainEnergy();
/* 121 */     if (this.storage > this.maxStorage)
/*     */     {
/* 123 */       this.storage = this.maxStorage;
/*     */     }
/* 125 */     if (this.storage > 0)
/*     */     {
/* 127 */       if (this.inventory[0] != null && (this.inventory[0].func_77973_b() instanceof ic2.api.item.IElectricItem || ElectricItem.getBackupManager(this.inventory[0]) != null)) {
/*     */         
/* 129 */         int used = (int)ElectricItem.manager.charge(this.inventory[0], this.storage, 1, false, false);
/* 130 */         this.storage = (short)(this.storage - (short)used);
/* 131 */         if (used > 0)
/*     */         {
/* 133 */           needsInvUpdate = true;
/*     */         }
/*     */       } 
/*     */     }
/* 137 */     if (needsInvUpdate)
/*     */     {
/* 139 */       func_70296_d();
/*     */     }
/* 141 */     if (!delayActiveUpdate()) {
/*     */       
/* 143 */       setActive(newActive);
/*     */     }
/*     */     else {
/*     */       
/* 147 */       if (this.ticksSinceLastActiveUpdate % 256 == 0) {
/*     */         
/* 149 */         setActive((this.activityMeter > 0));
/* 150 */         this.activityMeter = 0;
/*     */       } 
/* 152 */       if (newActive) {
/*     */         
/* 154 */         this.activityMeter++;
/*     */       }
/*     */       else {
/*     */         
/* 158 */         this.activityMeter--;
/*     */       } 
/* 160 */       this.ticksSinceLastActiveUpdate++;
/*     */     } 
/* 162 */     if (oldEnergy != this.storage || newActive)
/*     */     {
/* 164 */       getNetwork().updateTileGuiField((TileEntity)this, "storage");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gainEnergy() {
/* 170 */     if (isConverting()) {
/*     */       
/* 172 */       this.storage = (short)(this.storage + (short)this.production);
/* 173 */       this.fuel--;
/* 174 */       getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/* 175 */       return true;
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConverting() {
/* 182 */     return (this.fuel > 0 && this.storage + this.production <= this.maxStorage);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/* 187 */     return (this.fuel <= 0 && this.storage + this.production <= this.maxStorage);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean gainFuel();
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 201 */     if (this.storage < 1.0D)
/*     */     {
/* 203 */       return 0.0D;
/*     */     }
/* 205 */     return Math.min(this.production, this.storage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 211 */     this.storage = (short)(int)(this.storage - amount);
/* 212 */     getNetwork().updateTileGuiField((TileEntity)this, "storage");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String func_145825_b();
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */   
/*     */   public String getOperationSoundFile() {
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 236 */     if (field.equals("active") && this.prevActive != getActive()) {
/*     */       
/* 238 */       if (this.audioSource != null && this.audioSource.isRemoved())
/*     */       {
/* 240 */         this.audioSource = null;
/*     */       }
/*     */       
/* 243 */       if (this.audioSource == null && getOperationSoundFile() != null)
/*     */       {
/* 245 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getOperationSoundFile(), true, false, IC2.audioManager.defaultVolume);
/*     */       }
/* 247 */       if (getActive()) {
/*     */         
/* 249 */         if (this.audioSource != null)
/*     */         {
/* 251 */           this.audioSource.play();
/*     */         }
/*     */       }
/* 254 */       else if (this.audioSource != null) {
/*     */         
/* 256 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/* 259 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 265 */     return 0.9F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 271 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 277 */     return this.storage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 283 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 289 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 295 */     return this.production;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 301 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityBaseGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */