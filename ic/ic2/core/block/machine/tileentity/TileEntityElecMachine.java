/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public abstract class TileEntityElecMachine
/*     */   extends TileEntityMachine
/*     */   implements IEnergySink, IEnergyContainer
/*     */ {
/*     */   public int energy;
/*     */   public int fuelslot;
/*     */   public int maxEnergy;
/*     */   public int maxInput;
/*     */   public int tier;
/*     */   public int baseTier;
/*     */   public boolean addedToEnergyNet;
/*     */   public boolean redstone;
/*     */   
/*     */   public TileEntityElecMachine(int slots, int fuelslot, int maxenergy, int maxinput) {
/*  37 */     super(slots);
/*  38 */     this.energy = 0;
/*  39 */     this.tier = 0;
/*  40 */     this.addedToEnergyNet = false;
/*  41 */     this.fuelslot = fuelslot;
/*  42 */     this.maxEnergy = maxenergy;
/*  43 */     this.maxInput = maxinput;
/*  44 */     this.tier = EnergyNet.instance.getTierFromPower(maxinput);
/*  45 */     this.baseTier = this.tier;
/*  46 */     addGuiFields(new String[] { "energy", "maxEnergy" });
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityElecMachine(int slots, int fuelslot, int maxenergy, int maxinput, int tier) {
/*  51 */     super(slots);
/*  52 */     this.energy = 0;
/*  53 */     this.tier = 0;
/*  54 */     this.addedToEnergyNet = false;
/*  55 */     this.fuelslot = fuelslot;
/*  56 */     this.maxEnergy = maxenergy;
/*  57 */     this.maxInput = maxinput;
/*  58 */     this.tier = tier;
/*  59 */     this.baseTier = tier;
/*  60 */     addGuiFields(new String[] { "energy", "maxEnergy" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  66 */     super.func_145839_a(nbttagcompound);
/*  67 */     this.energy = nbttagcompound.func_74762_e("energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  73 */     super.func_145841_b(nbttagcompound);
/*  74 */     nbttagcompound.func_74768_a("energy", this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  80 */     super.onLoaded();
/*  81 */     if (isSimulating() && !this.addedToEnergyNet) {
/*     */       
/*  83 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  84 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  91 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  93 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  94 */       this.addedToEnergyNet = false;
/*     */     } 
/*  96 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean provideEnergy() {
/* 101 */     boolean ret = false;
/* 102 */     if (this.inventory[this.fuelslot] == null)
/*     */     {
/* 104 */       return false;
/*     */     }
/* 106 */     Item item = this.inventory[this.fuelslot].func_77973_b();
/* 107 */     if (item instanceof IElectricItem) {
/*     */       
/* 109 */       if (!((IElectricItem)item).canProvideEnergy(this.inventory[this.fuelslot]))
/*     */       {
/* 111 */         return false;
/*     */       }
/* 113 */       int transfer = (int)ElectricItem.manager.discharge(this.inventory[this.fuelslot], (this.maxEnergy - this.energy), this.tier, false, true, false);
/* 114 */       this.energy += transfer;
/* 115 */       if (transfer > 0)
/*     */       {
/* 117 */         getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */       }
/* 119 */       return (ret || transfer > 0);
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (item == Items.field_151137_ax) {
/*     */       
/* 125 */       this.energy += this.maxEnergy;
/* 126 */       ItemStack itemStack = this.inventory[this.fuelslot];
/* 127 */       itemStack.field_77994_a--;
/* 128 */       if ((this.inventory[this.fuelslot]).field_77994_a <= 0)
/*     */       {
/* 130 */         this.inventory[this.fuelslot] = null;
/*     */       }
/* 132 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 133 */       return true;
/*     */     } 
/* 135 */     if (item == Ic2Items.suBattery.func_77973_b()) {
/*     */       
/* 137 */       this.energy += 1000;
/* 138 */       ItemStack itemStack2 = this.inventory[this.fuelslot];
/* 139 */       itemStack2.field_77994_a--;
/* 140 */       if ((this.inventory[this.fuelslot]).field_77994_a <= 0)
/*     */       {
/* 142 */         this.inventory[this.fuelslot] = null;
/*     */       }
/* 144 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 145 */       return true;
/*     */     } 
/* 147 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void useEnergy(int amount) {
/* 153 */     this.energy -= amount;
/* 154 */     if (this.energy < 0) this.energy = 0; 
/* 155 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEnergy(int amount) {
/* 160 */     return (this.energy >= amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxEnergy(int amount) {
/* 165 */     this.maxEnergy = amount;
/* 166 */     getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 172 */     return (this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 178 */     if (amount > this.maxInput)
/*     */     {
/* 180 */       return 0.0D;
/*     */     }
/* 182 */     this.energy = (int)(this.energy + amount);
/* 183 */     int re = 0;
/* 184 */     if (this.energy > this.maxEnergy) {
/*     */       
/* 186 */       re = this.energy - this.maxEnergy;
/* 187 */       this.energy = this.maxEnergy;
/*     */     } 
/* 189 */     ((NetworkManager)IC2.network.get()).updateTileGuiField((TileEntity)this, "energy");
/* 190 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 196 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleRedstone() {
/* 206 */     this.redstone = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRedstonePowered() {
/* 211 */     return this.redstone;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 217 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 223 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 229 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 235 */     return this.maxInput;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityElecMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */