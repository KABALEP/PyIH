/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ 
/*     */ public abstract class SteamReactorLogicBase
/*     */   extends ReactorLogicBase
/*     */   implements ISteamReactor
/*     */ {
/*  16 */   FluidTank waterTank = new FluidTank(2000);
/*  17 */   FluidTank steamTank = new FluidTank(20000);
/*  18 */   public long totalUsedWater = 0L;
/*  19 */   public long totalProducedSteam = 0L;
/*     */ 
/*     */   
/*     */   public SteamReactorLogicBase(TileEntityReactorPlanner tile) {
/*  23 */     super(tile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  30 */     super.clear();
/*  31 */     this.totalUsedWater = 0L;
/*  32 */     this.totalProducedSteam = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  38 */     this.totalUsedWater = 0L;
/*  39 */     this.totalProducedSteam = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/*  45 */     this.waterTank.fill(new FluidStack(FluidRegistry.WATER, 2147483647), true);
/*  46 */     this.steamTank.drain(2147483647, true);
/*  47 */     this.totalUsedWater = 0L;
/*  48 */     this.totalProducedSteam = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTanks() {
/*  53 */     this.totalUsedWater += this.waterTank.fill(new FluidStack(FluidRegistry.WATER, 2147483647), true);
/*  54 */     FluidStack steam = this.steamTank.drain(2147483647, true);
/*  55 */     if (steam != null)
/*     */     {
/*  57 */       this.totalProducedSteam += steam.amount;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSteamLogic() {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTank getWaterTank() {
/*  70 */     return this.waterTank;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTank getSteamTank() {
/*  76 */     return this.steamTank;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  82 */     super.readFromNBT(nbt);
/*  83 */     this.waterTank.readFromNBT(nbt.func_74775_l("WaterTank"));
/*  84 */     this.steamTank.readFromNBT(nbt.func_74775_l("SteamTank"));
/*  85 */     this.totalUsedWater = nbt.func_74763_f("TotalWater");
/*  86 */     this.totalProducedSteam = nbt.func_74763_f("TotalSteam");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  92 */     super.writeToNBT(nbt);
/*  93 */     this.waterTank.writeToNBT(getTag(nbt, "WaterTank"));
/*  94 */     this.steamTank.writeToNBT(getTag(nbt, "SteamTank"));
/*  95 */     nbt.func_74772_a("TotalWater", this.totalUsedWater);
/*  96 */     nbt.func_74772_a("TotalSteam", this.totalProducedSteam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DataInput stream) {
/* 102 */     super.read(stream);
/*     */     
/*     */     try {
/* 105 */       this.totalUsedWater = stream.readLong();
/* 106 */       this.totalProducedSteam = stream.readLong();
/*     */     }
/* 108 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DataOutput stream) {
/* 116 */     super.write(stream);
/*     */     
/*     */     try {
/* 119 */       stream.writeLong(this.totalUsedWater);
/* 120 */       stream.writeLong(this.totalProducedSteam);
/*     */     }
/* 122 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStateFromNBT(NBTTagCompound nbt) {
/* 130 */     super.readStateFromNBT(nbt);
/* 131 */     this.waterTank.readFromNBT(nbt.func_74775_l("WaterTank"));
/* 132 */     this.steamTank.readFromNBT(nbt.func_74775_l("SteamTank"));
/* 133 */     this.totalUsedWater = nbt.func_74763_f("TotalWater");
/* 134 */     this.totalProducedSteam = nbt.func_74763_f("TotalSteam");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStateToNBT(NBTTagCompound nbt) {
/* 140 */     super.writeStateToNBT(nbt);
/* 141 */     this.waterTank.writeToNBT(getTag(nbt, "WaterTank"));
/* 142 */     this.steamTank.writeToNBT(getTag(nbt, "SteamTank"));
/* 143 */     nbt.func_74772_a("TotalWater", this.totalUsedWater);
/* 144 */     nbt.func_74772_a("TotalSteam", this.totalProducedSteam);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\SteamReactorLogicBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */