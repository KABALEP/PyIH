/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public abstract class TileEntityTransformer
/*     */   extends TileEntityBlock
/*     */   implements IEnergySink, IEnergySource, IEnergySourceInfo, IMultiEnergySource, IEnergyContainer
/*     */ {
/*     */   public int lowOutput;
/*     */   public int highOutput;
/*     */   public int maxStorage;
/*  27 */   public int energy = 0;
/*     */   
/*     */   public boolean redstone = false;
/*     */   public boolean addedToEnergyNet = false;
/*     */   
/*     */   public TileEntityTransformer() {
/*  33 */     addGuiFields(new String[] { "energy" });
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityTransformer(int low, int high, int max) {
/*  38 */     this.lowOutput = low;
/*  39 */     this.highOutput = high;
/*  40 */     this.maxStorage = max;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  46 */     super.func_145839_a(nbttagcompound);
/*  47 */     this.energy = nbttagcompound.func_74762_e("energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  53 */     super.func_145841_b(nbttagcompound);
/*  54 */     nbttagcompound.func_74768_a("energy", this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  60 */     return isSimulating();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  66 */     super.onLoaded();
/*  67 */     if (isSimulating()) {
/*     */       
/*  69 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  70 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  77 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  79 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  80 */       this.addedToEnergyNet = false;
/*     */     } 
/*  82 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  87 */     super.func_145845_h();
/*  88 */     updateRedstone();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRedstone() {
/*  93 */     boolean red = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  94 */     if (red != this.redstone) {
/*     */       
/*  96 */       if (this.addedToEnergyNet)
/*     */       {
/*  98 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */       }
/* 100 */       this.addedToEnergyNet = false;
/* 101 */       this.redstone = red;
/* 102 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 103 */       this.addedToEnergyNet = true;
/* 104 */       setActive(this.redstone);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 110 */     if (this.redstone)
/*     */     {
/* 112 */       return !facingMatchesDirection(direction);
/*     */     }
/* 114 */     return facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 119 */     if (this.redstone)
/*     */     {
/* 121 */       return facingMatchesDirection(direction);
/*     */     }
/* 123 */     return !facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean facingMatchesDirection(ForgeDirection direction) {
/* 128 */     return (direction.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 134 */     if (getActive()) {
/*     */       
/* 136 */       if (this.energy >= this.highOutput)
/*     */       {
/* 138 */         return this.highOutput;
/*     */       }
/* 140 */       return 0.0D;
/*     */     } 
/* 142 */     if (this.energy >= this.lowOutput)
/*     */     {
/* 144 */       return this.lowOutput;
/*     */     }
/* 146 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 152 */     this.energy = (int)(this.energy - amount);
/* 153 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 159 */     return (this.maxStorage - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 165 */     if (amount > (this.redstone ? this.lowOutput : this.highOutput))
/*     */     {
/* 167 */       return 0.0D;
/*     */     }
/* 169 */     this.energy = (int)(this.energy + amount);
/* 170 */     int re = 0;
/* 171 */     if (this.energy > this.maxStorage) {
/*     */       
/* 173 */       re = this.energy - this.maxStorage;
/* 174 */       this.energy = this.maxStorage;
/*     */     } 
/* 176 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 177 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 183 */     if (getActive())
/*     */     {
/* 185 */       return EnergyNet.instance.getTierFromPower(this.lowOutput);
/*     */     }
/* 187 */     return EnergyNet.instance.getTierFromPower(this.highOutput);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 195 */     if (getActive())
/*     */     {
/* 197 */       return EnergyNet.instance.getTierFromPower(this.highOutput);
/*     */     }
/* 199 */     return EnergyNet.instance.getTierFromPower(this.lowOutput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 205 */     return (getFacing() != side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacing(short side) {
/* 211 */     if (this.addedToEnergyNet)
/*     */     {
/* 213 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     }
/* 215 */     this.addedToEnergyNet = false;
/* 216 */     super.setFacing(side);
/* 217 */     MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 218 */     this.addedToEnergyNet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendMultibleEnergyPackets() {
/* 224 */     return !getActive();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultibleEnergyPacketAmount() {
/* 230 */     if (!getActive())
/*     */     {
/* 232 */       return 4;
/*     */     }
/* 234 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 240 */     if (getActive())
/*     */     {
/* 242 */       return this.highOutput;
/*     */     }
/* 244 */     return this.lowOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 250 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 256 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 262 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 268 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 274 */     if (getActive())
/*     */     {
/* 276 */       return this.lowOutput;
/*     */     }
/* 278 */     return this.highOutput;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */