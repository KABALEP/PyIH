/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityElectricBlock
/*     */   extends TileEntityMachine
/*     */   implements IEnergySink, IEnergySourceInfo, IHasGui, ISidedInventory, INetworkClientTileEntityEventListener, IEnergyStorage, IElectrolyzerProvider, IEnergyContainer
/*     */ {
/*     */   public int tier;
/*     */   public int output;
/*     */   public int maxStorage;
/*  40 */   public int energy = 0;
/*  41 */   public byte redstoneMode = 0;
/*  42 */   public static byte redstoneModes = 8;
/*     */   public boolean addedToEnergyNet = false;
/*  44 */   public int state = 0;
/*     */   
/*     */   public boolean emit = false;
/*     */   public boolean redstone;
/*     */   
/*     */   public TileEntityElectricBlock(int tierc, int outputc, int maxStoragec) {
/*  50 */     super(2);
/*  51 */     this.tier = tierc;
/*  52 */     this.output = outputc;
/*  53 */     this.maxStorage = maxStoragec;
/*  54 */     addNetworkFields(new String[] { "state" });
/*  55 */     addGuiFields(new String[] { "energy" });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameByTier() {
/*  60 */     switch (this.tier) {
/*     */ 
/*     */       
/*     */       case 1:
/*  64 */         return StatCollector.func_74838_a("blockBatBox.name");
/*     */ 
/*     */       
/*     */       case 2:
/*  68 */         return StatCollector.func_74838_a("blockMFE.name");
/*     */ 
/*     */       
/*     */       case 3:
/*  72 */         return StatCollector.func_74838_a("blockMFSU.name");
/*     */     } 
/*     */ 
/*     */     
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/*  84 */     super.onNetworkUpdate(field);
/*  85 */     if (field.equals("state"))
/*     */     {
/*  87 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getChargeLevel() {
/*  93 */     float ret = this.energy / this.maxStorage;
/*  94 */     if (ret > 1.0F)
/*     */     {
/*  96 */       ret = 1.0F;
/*     */     }
/*  98 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 104 */     super.func_145839_a(nbttagcompound);
/* 105 */     setActiveWithoutNotify(nbttagcompound.func_74767_n("active"));
/* 106 */     this.energy = nbttagcompound.func_74762_e("energy");
/* 107 */     if (this.maxStorage > Integer.MAX_VALUE)
/*     */     {
/* 109 */       this.energy *= 10;
/*     */     }
/* 111 */     this.redstoneMode = nbttagcompound.func_74771_c("redstoneMode");
/* 112 */     this.state = nbttagcompound.func_74762_e("state");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 118 */     super.func_145841_b(nbttagcompound);
/* 119 */     int write = this.energy;
/* 120 */     if (this.maxStorage > Integer.MAX_VALUE)
/*     */     {
/* 122 */       write /= 10;
/*     */     }
/* 124 */     nbttagcompound.func_74768_a("energy", write);
/* 125 */     nbttagcompound.func_74757_a("active", getActive());
/* 126 */     nbttagcompound.func_74774_a("redstoneMode", this.redstoneMode);
/* 127 */     nbttagcompound.func_74768_a("state", this.state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 132 */     super.onLoaded();
/* 133 */     if (isSimulating()) {
/*     */       
/* 135 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 136 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 142 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 144 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 145 */       this.addedToEnergyNet = false;
/*     */     } 
/* 147 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 153 */     super.func_145845_h();
/* 154 */     this.redstone = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 155 */     boolean needsInvUpdate = false;
/* 156 */     boolean wasEmittingRedstone = isEmittingRedstone();
/* 157 */     if (this.energy > 0 && this.inventory[0] != null && (this.inventory[0].func_77973_b() instanceof IElectricItem || ElectricItem.getBackupManager(this.inventory[0]) != null)) {
/*     */       
/* 159 */       int sent = (int)ElectricItem.manager.charge(this.inventory[0], this.energy, this.tier, false, false);
/* 160 */       this.energy -= sent;
/* 161 */       needsInvUpdate = (sent > 0);
/*     */     } 
/* 163 */     if (getDemandedEnergy() > 0.0D && this.inventory[1] != null)
/*     */     {
/* 165 */       if (this.inventory[1].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 167 */         IElectricItem item = (IElectricItem)this.inventory[1].func_77973_b();
/* 168 */         if (item.canProvideEnergy(this.inventory[1]))
/*     */         {
/* 170 */           int gain = (int)ElectricItem.manager.discharge(this.inventory[1], (this.maxStorage - this.energy), this.tier, false, true, false);
/* 171 */           this.energy += gain;
/* 172 */           needsInvUpdate = (gain > 0 || needsInvUpdate);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 177 */         Item id = this.inventory[1].func_77973_b();
/* 178 */         int gain = 0;
/* 179 */         if (id == Items.field_151137_ax)
/*     */         {
/* 181 */           gain = 500;
/*     */         }
/* 183 */         if (id == Ic2Items.suBattery.func_77973_b())
/*     */         {
/* 185 */           gain = 1000;
/*     */         }
/* 187 */         if (gain > 0 && gain <= this.maxStorage - this.energy) {
/*     */           
/* 189 */           ItemStack itemStack = this.inventory[1];
/* 190 */           itemStack.field_77994_a--;
/* 191 */           if ((this.inventory[1]).field_77994_a <= 0)
/*     */           {
/* 193 */             this.inventory[1] = null;
/*     */           }
/* 195 */           this.energy += gain;
/* 196 */           needsInvUpdate = true;
/*     */         } 
/*     */       } 
/*     */     }
/* 200 */     int newState = getUpdatedState();
/* 201 */     if (newState != this.state) {
/*     */       
/* 203 */       this.state = newState;
/* 204 */       getNetwork().updateTileEntityField((TileEntity)this, "state");
/*     */     } 
/* 206 */     boolean isFull = false;
/* 207 */     if (this.energy >= this.maxStorage)
/*     */     {
/* 209 */       isFull = true;
/*     */     }
/* 211 */     setActive(isFull);
/* 212 */     if (wasEmittingRedstone != isEmittingRedstone() || this.emit != isEmittingRedstone()) {
/*     */       
/* 214 */       updateNeighbors(true);
/* 215 */       this.emit = isEmittingRedstone();
/*     */     } 
/* 217 */     if (needsInvUpdate) {
/*     */       
/* 219 */       func_70296_d();
/* 220 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 226 */     return !facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 231 */     return facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean facingMatchesDirection(ForgeDirection direction) {
/* 236 */     return (direction.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 242 */     if (this.energy >= this.output && (this.redstoneMode != 6 || !this.redstone) && (this.redstoneMode != 7 || !this.redstone || this.energy >= this.maxStorage))
/*     */     {
/* 244 */       return this.output;
/*     */     }
/* 246 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 254 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 260 */     this.energy = (int)(this.energy - amount);
/* 261 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 267 */     return (this.maxStorage - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 273 */     if (amount > this.output)
/*     */     {
/* 275 */       return 0.0D;
/*     */     }
/* 277 */     this.energy = (int)(this.energy + amount);
/* 278 */     int re = 0;
/* 279 */     if (this.energy > this.maxStorage) {
/*     */       
/* 281 */       re = this.energy - this.maxStorage;
/* 282 */       this.energy = this.maxStorage;
/*     */     } 
/* 284 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 285 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 291 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 297 */     return new ContainerElectricBlock(entityPlayer.field_71071_by, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 303 */     return "block.wiring.GuiElectricBlock";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 313 */     return (getFacing() != side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacing(short facing) {
/* 318 */     if (this.addedToEnergyNet)
/*     */     {
/* 320 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     }
/* 322 */     this.addedToEnergyNet = false;
/* 323 */     super.setFacing(facing);
/* 324 */     MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 325 */     this.addedToEnergyNet = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUpdatedState() {
/* 330 */     float charge = getChargeLevel();
/* 331 */     if (charge <= 0.01D)
/*     */     {
/* 333 */       return 0;
/*     */     }
/* 335 */     if (charge >= 0.99D)
/*     */     {
/* 337 */       return 3;
/*     */     }
/* 339 */     if (charge < 0.5D)
/*     */     {
/* 341 */       return 1;
/*     */     }
/* 343 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmittingRedstone() {
/* 348 */     switch (this.redstoneMode) {
/*     */ 
/*     */       
/*     */       case 1:
/* 352 */         return (this.energy >= this.maxStorage);
/*     */ 
/*     */       
/*     */       case 2:
/* 356 */         return (this.energy > this.output && this.energy < this.maxStorage);
/*     */ 
/*     */       
/*     */       case 3:
/* 360 */         return (this.energy >= this.maxStorage / 2);
/*     */ 
/*     */       
/*     */       case 4:
/* 364 */         return (this.energy <= this.maxStorage / 2);
/*     */ 
/*     */       
/*     */       case 5:
/* 368 */         return (this.energy < this.output);
/*     */     } 
/*     */ 
/*     */     
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 379 */     if (var1 == 0)
/*     */     {
/* 381 */       return new int[] { 1 };
/*     */     }
/* 383 */     if (var1 == 1)
/*     */     {
/* 385 */       return new int[] { 0 };
/*     */     }
/* 387 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 392 */     this.redstoneMode = (byte)(this.redstoneMode + 1);
/* 393 */     if (this.redstoneMode >= redstoneModes)
/*     */     {
/* 395 */       this.redstoneMode = 0;
/*     */     }
/* 397 */     updateNeighbors(true);
/* 398 */     IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.energyStorageRedstoneMode" + this.redstoneMode + ".name"));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStored() {
/* 403 */     return this.energy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCapacity() {
/* 408 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOutput() {
/* 413 */     return this.output;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStored(int energy) {
/* 418 */     this.energy = energy;
/* 419 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   public int addEnergy(int amount) {
/* 424 */     this.energy += amount;
/* 425 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 426 */     return amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTeleporterCompatible(Direction side) {
/* 431 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 437 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 443 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOutputEnergyUnitsPerTick() {
/* 449 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTeleporterCompatible(ForgeDirection side) {
/* 455 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNeighbors(boolean needSelf) {
/* 460 */     if (needSelf)
/*     */     {
/* 462 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, func_145838_q());
/*     */     }
/* 464 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
/*     */     {
/* 466 */       notifyBlocksOfNeighborChange(side);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyBlocksOfNeighborChange(ForgeDirection side) {
/* 472 */     this.field_145850_b.func_147459_d(this.field_145851_c + side.offsetX, this.field_145848_d + side.offsetY, this.field_145849_e + side.offsetZ, func_145838_q());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTileMeta() {
/* 478 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileMeta() {
/* 484 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProcessRate() {
/* 490 */     switch (this.tier) {
/*     */ 
/*     */       
/*     */       default:
/* 494 */         return 2;
/*     */ 
/*     */       
/*     */       case 2:
/* 498 */         return 8;
/*     */       case 3:
/*     */         break;
/*     */     } 
/* 502 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier() {
/* 510 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPower(int amount) {
/* 516 */     this.energy = Math.max(this.energy - amount, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPower(int amount) {
/* 522 */     this.energy = Math.min(this.energy + amount, this.maxStorage);
/* 523 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredPower() {
/* 529 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStorage() {
/* 535 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 541 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 547 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 553 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 559 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 565 */     return (int)EnergyNet.instance.getPowerFromTier(this.tier);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */