/*     */ package ic2.rfIntigration.tiles;
/*     */ 
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
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
/*     */ public class TileConverterEUBase
/*     */   extends TileEntityBlock
/*     */   implements IEnergySourceInfo, IConverterStorage, IEnergyHandler, IEnergyContainer, IMultiEnergySource {
/*     */   public int tier;
/*     */   public int maxOffer;
/*     */   public int storedEnergy;
/*  28 */   public EU euConverter = new EU(); public int maxEnergy; public float loss; boolean enet;
/*  29 */   public RF rfConverter = new RF();
/*     */ 
/*     */   
/*     */   public TileConverterEUBase(int tier, int maxEnergy, int maxOffer, float loss) {
/*  33 */     this.tier = tier;
/*  34 */     this.maxEnergy = maxEnergy;
/*  35 */     this.maxOffer = maxOffer;
/*  36 */     this.loss = loss;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/*  42 */     return Math.min(this.storedEnergy * getLoss(), this.maxOffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/*  48 */     this.euConverter.discharge(amount, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/*  54 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/*  60 */     return (direction.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/*  66 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreeSpace() {
/*  72 */     return this.maxEnergy - this.storedEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int charge(int amount) {
/*  78 */     this.storedEnergy += amount;
/*  79 */     if (this.storedEnergy > this.maxEnergy)
/*     */     {
/*  81 */       amount -= this.storedEnergy - this.maxEnergy;
/*     */     }
/*  83 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int useEnergy(int amount) {
/*  89 */     this.storedEnergy -= amount;
/*  90 */     if (this.storedEnergy < 0) {
/*     */       
/*  92 */       amount += this.storedEnergy;
/*  93 */       this.storedEnergy = 0;
/*     */     } 
/*  95 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int useEnergy(int amount, boolean simulate) {
/* 101 */     if (!simulate)
/*     */     {
/* 103 */       return useEnergy(amount);
/*     */     }
/* 105 */     int stored = this.storedEnergy;
/* 106 */     stored -= amount;
/* 107 */     if (stored < 0)
/*     */     {
/* 109 */       amount += stored;
/*     */     }
/* 111 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLoss() {
/* 117 */     return 1.0F - this.loss;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 123 */     super.onLoaded();
/* 124 */     if (!this.enet) {
/*     */       
/* 126 */       this.enet = true;
/* 127 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 134 */     super.onUnloaded();
/* 135 */     if (this.enet) {
/*     */       
/* 137 */       this.enet = false;
/* 138 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection side) {
/* 145 */     return (side.ordinal() != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection side) {
/* 151 */     return this.storedEnergy * IC2.rfForEU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection side) {
/* 157 */     return this.maxEnergy * IC2.rfForEU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection side, int amount, boolean simulate) {
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection side, int amount, boolean simulate) {
/* 169 */     return amount - this.rfConverter.charge(amount, simulate, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 175 */     super.func_145841_b(nbt);
/* 176 */     this.rfConverter.writeToNBT(getNBT("RFConverter", nbt));
/* 177 */     this.euConverter.writeToNBT(getNBT("EuConverter", nbt));
/* 178 */     nbt.func_74768_a("Energy", this.storedEnergy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 184 */     super.func_145839_a(nbt);
/* 185 */     this.rfConverter.readFromNBT(nbt.func_74775_l("RFConverter"));
/* 186 */     this.euConverter.readFromNBT(nbt.func_74775_l("EuConverter"));
/* 187 */     this.storedEnergy = nbt.func_74762_e("Energy");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBT(String par1, NBTTagCompound nbt) {
/* 192 */     if (!nbt.func_74764_b(par1))
/*     */     {
/* 194 */       nbt.func_74782_a(par1, (NBTBase)new NBTTagCompound());
/*     */     }
/* 196 */     return nbt.func_74775_l(par1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 202 */     return (side != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 208 */     return this.storedEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 214 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 220 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 226 */     return this.maxOffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendMultibleEnergyPackets() {
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultibleEnergyPacketAmount() {
/* 244 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\TileConverterEUBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */