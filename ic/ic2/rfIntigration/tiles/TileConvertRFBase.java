/*     */ package ic2.rfIntigration.tiles;
/*     */ 
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cofh.api.energy.IEnergyReceiver;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.rfIntigration.core.converter.EU;
/*     */ import ic2.rfIntigration.core.converter.RF;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileConvertRFBase
/*     */   extends TileEntityBlock
/*     */   implements IEnergySink, IConverterStorage, IEnergyHandler, IEnergyContainer {
/*     */   public int tier;
/*     */   public int maxInput;
/*  28 */   public EU euConverter = new EU(); public int maxEnergy; public int energy; public float loss;
/*  29 */   public RF rfConverter = new RF();
/*     */   
/*     */   public boolean enet;
/*     */   
/*     */   public TileConvertRFBase(int tier, int maxInput, int maxEnergy, float loss) {
/*  34 */     this.tier = tier;
/*  35 */     this.maxEnergy = maxEnergy;
/*  36 */     this.loss = loss;
/*  37 */     this.maxInput = maxInput;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/*  43 */     return (direction.ordinal() != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/*  49 */     return Math.min(getFreeSpace() / getLoss(), this.maxInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/*  55 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/*  61 */     return this.euConverter.charge(amount, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection dir) {
/*  67 */     return (dir.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection dir, int amount, boolean simulate) {
/*  73 */     return this.rfConverter.discharge(amount, simulate, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection dir, int amount, boolean simulate) {
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection dir) {
/*  85 */     return this.energy * IC2.rfPerEU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection dir) {
/*  91 */     return this.maxEnergy * IC2.rfPerEU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLoss() {
/*  97 */     return 1.0F - this.loss;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreeSpace() {
/* 103 */     return this.maxEnergy - this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int charge(int amount) {
/* 109 */     this.energy += amount;
/* 110 */     if (this.energy > this.maxEnergy)
/*     */     {
/* 112 */       amount -= this.energy - this.maxEnergy;
/*     */     }
/* 114 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int useEnergy(int amount) {
/* 120 */     this.energy -= amount;
/* 121 */     if (this.energy < 0) {
/*     */       
/* 123 */       amount += this.energy;
/* 124 */       this.energy = 0;
/*     */     } 
/* 126 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int useEnergy(int amount, boolean simulate) {
/* 132 */     if (!simulate)
/*     */     {
/* 134 */       return useEnergy(amount);
/*     */     }
/* 136 */     int stored = this.energy;
/* 137 */     stored -= amount;
/* 138 */     if (stored < 0)
/*     */     {
/* 140 */       amount += stored;
/*     */     }
/* 142 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 149 */     super.func_145839_a(nbt);
/* 150 */     this.euConverter.readFromNBT(nbt.func_74775_l("EUConverter"));
/* 151 */     this.rfConverter.readFromNBT(nbt.func_74775_l("RFConverter"));
/* 152 */     this.energy = nbt.func_74762_e("Energy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 159 */     super.func_145841_b(nbt);
/* 160 */     this.euConverter.writeToNBT(getNBT("EUConverter", nbt));
/* 161 */     this.rfConverter.writeToNBT(getNBT("RFConverter", nbt));
/* 162 */     nbt.func_74768_a("Energy", this.energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBT(String par1, NBTTagCompound nbt) {
/* 167 */     if (!nbt.func_74764_b(par1))
/*     */     {
/* 169 */       nbt.func_74782_a(par1, (NBTBase)new NBTTagCompound());
/*     */     }
/* 171 */     return nbt.func_74775_l(par1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 183 */     super.func_145845_h();
/* 184 */     if (this.energy <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 188 */     Direction dir = Direction.fromSideValue(getFacing());
/* 189 */     TileEntity tile = dir.applyToTileEntity((TileEntity)this);
/* 190 */     if (tile != null && tile instanceof IEnergyReceiver) {
/*     */       
/* 192 */       IEnergyReceiver receiver = (IEnergyReceiver)tile;
/* 193 */       ForgeDirection fdir = dir.toForgeDirection().getOpposite();
/* 194 */       if (receiver.canConnectEnergy(fdir)) {
/*     */         
/* 196 */         int toSend = Math.min(this.energy, getNeededRF(receiver, fdir));
/* 197 */         extractEnergy(dir.toForgeDirection(), receiver.receiveEnergy(fdir, toSend, false), false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNeededRF(IEnergyReceiver par1, ForgeDirection dir) {
/* 204 */     return par1.getMaxEnergyStored(dir) - par1.getEnergyStored(dir);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 210 */     super.onLoaded();
/* 211 */     if (!this.enet) {
/*     */       
/* 213 */       this.enet = true;
/* 214 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 221 */     super.onUnloaded();
/* 222 */     if (this.enet) {
/*     */       
/* 224 */       this.enet = false;
/* 225 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 232 */     return (side != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 238 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 244 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 250 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 256 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 262 */     return this.maxInput;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\TileConvertRFBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */