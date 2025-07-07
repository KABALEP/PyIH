/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityBatteryBox
/*     */   extends TileEntityMachine
/*     */   implements IMultiEnergySource, IEnergySourceInfo, IEnergySink, INetworkClientTileEntityEventListener, IHasGui, IElectrolyzerProvider, IEnergyContainer
/*     */ {
/*     */   public int currentStored;
/*     */   public int maxStorage;
/*     */   public int currentOutput;
/*     */   public int currentInput;
/*     */   public int currentChange;
/*  36 */   public int[] batteryTiers = new int[9];
/*  37 */   public int[] stored = new int[9];
/*  38 */   public boolean[] providers = new boolean[9];
/*     */ 
/*     */   
/*  41 */   public int[] incomingTicks = new int[20];
/*  42 */   public int[] outputTicks = new int[20];
/*  43 */   public int tick = 0;
/*  44 */   public int currentDraw = 0;
/*     */   
/*  46 */   public int stationMode = 0;
/*  47 */   public static int maxMode = 3;
/*     */   
/*  49 */   public int sendingMode = 0;
/*  50 */   public static int sendingMaxMode = 3;
/*     */   
/*     */   public boolean added = false;
/*  53 */   public int state = 0;
/*     */   int[] changes;
/*     */   public int[] func_94128_d(int var1) { return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }; }
/*     */   public void onNetworkUpdate(String field) { super.onNetworkUpdate(field); if (field.equals("state")) this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);  }
/*  57 */   public int func_70297_j_() { return 1; } public String func_145825_b() { return "Battery Box"; } public double getOfferedEnergy() { return getOffer(this.sendingMode); } public void drawEnergy(double amount) { this.outputTicks[this.tick] = (int)(this.outputTicks[this.tick] + amount); this.currentStored = (int)(this.currentStored - amount); switch (this.sendingMode) { case 0: drawFromBattery(this.currentDraw, (int)amount); break;case 1: drawFromAll((int)amount); break;case 2: drawFromBattery(this.currentDraw, (int)amount); this.currentDraw = getNextID(); break; }  } public int getNextID() { int id = this.currentDraw; boolean found = false; for (int i = id + 1; i < 9; i++) { if (this.providers[i] && this.stored[i] > 0) { id = i; found = true; break; }  }  if (!found) id = 0;  return id; } public void drawFromAll(int amount) { for (int i = 0; i < 9; i++) { if (this.providers[i] && this.stored[i] > 0) { int drawn = (int)ElectricItem.manager.discharge(func_70301_a(i), Math.min(amount, Math.min(this.stored[i], EnergyNet.instance.getPowerFromTier(this.batteryTiers[i]))), this.batteryTiers[i], false, true, false); this.stored[i] = this.stored[i] - drawn; amount -= drawn; }  }  } public void drawFromBattery(int slot, int amount) { ItemStack stack = func_70301_a(slot); if (stack != null && stack.func_77973_b() instanceof IElectricItem) { ElectricItem.manager.discharge(stack, amount, this.batteryTiers[slot], false, true, false); this.stored[slot] = this.stored[slot] - amount; }  } public int getSourceTier() { switch (this.sendingMode) { case 0: if (this.currentDraw >= 0) return this.batteryTiers[this.currentDraw]; case 1: return EnergyNet.instance.getTierFromPower(getOffer(1));case 2: return this.batteryTiers[this.currentDraw]; }  return 0; } public TileEntityBatteryBox() { super(9);
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
/* 589 */     this.changes = new int[9]; addNetworkFields(new String[] { "state" }); addGuiFields(new String[] { "currentDraw", "currentStored", "maxStorage", "providers", "batteryTiers", "stored", "maxStorage", "currentOutput", "currentInput", "currentChange", "currentStored", "stored" }); }
/*     */   public int getOffer(int type) { if (type == 0) { this.currentDraw = -1; for (int i = 0; i < 9; i++) { if (this.providers[i] && this.stored[i] > 0) { this.currentDraw = i; break; }  }  getNetwork().updateTileGuiField((TileEntity)this, "currentDraw"); if (this.currentDraw >= 0) return (int)Math.min(this.stored[this.currentDraw], EnergyNet.instance.getPowerFromTier(this.batteryTiers[this.currentDraw]));  return 0; }  if (type == 1) { int offer = 0; for (int i = 0; i < 9; i++) { if (this.providers[i] && this.stored[i] > 0) offer = (int)(offer + Math.min(this.stored[i], EnergyNet.instance.getPowerFromTier(this.batteryTiers[i])));  }  return offer; }  if (type == 2) { if (this.currentDraw == -1) this.currentDraw = 0;  int amount = (int)Math.min(this.stored[this.currentDraw], EnergyNet.instance.getPowerFromTier(this.batteryTiers[this.currentDraw])); if (amount <= 0 && this.currentStored > 0) { this.currentDraw = getNextID(); amount = (int)Math.min(this.stored[this.currentDraw], EnergyNet.instance.getPowerFromTier(this.batteryTiers[this.currentDraw])); }  return amount; }  return 0; }
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) { int mode = this.stationMode; return ((mode == 1 || mode == 2) && direction.ordinal() == getFacing()); }
/*     */   public boolean sendMultibleEnergyPackets() { return ((this.stationMode == 1 || this.stationMode == 2) && this.sendingMode == 2); }
/* 593 */   public int getMultibleEnergyPacketAmount() { int amount = 0; for (int i = 0; i < 9; i++) { if (this.providers[i] && this.stored[i] > 0) amount++;  }  return amount; } public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) { int mode = this.stationMode; return ((mode == 0 || mode == 2) && direction.ordinal() != getFacing()); } public double getDemandedEnergy() { return (this.maxStorage - this.currentStored); } public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) { double left = charge(amount); this.incomingTicks[this.tick] = this.incomingTicks[this.tick] + (int)(amount - left); return left; } public double charge(double amount) { double copyAmount = amount; for (int i = 0; i < 9; i++) { if ((int)copyAmount <= 0) break;  copyAmount -= charge(i, copyAmount); }  return copyAmount; } public double charge(int slot, double amount) { double copyAmount = amount; ItemStack stack = func_70301_a(slot); if (stack != null && (stack.func_77973_b() instanceof IElectricItem || ElectricItem.getBackupManager(stack) != null)) { int removed = (int)ElectricItem.manager.charge(stack, (int)amount, this.batteryTiers[slot], false, false); copyAmount -= removed; this.stored[slot] = this.stored[slot] + removed; this.currentStored += removed; }  return amount - copyAmount; } public int getProcessRate() { int total = 0;
/* 594 */     for (int i = 0; i < this.batteryTiers.length; i++) {
/*     */       
/* 596 */       if (this.providers[i]) {
/*     */         
/* 598 */         int change = Math.min(this.stored[i], getTierFromEnergy(this.batteryTiers[i]));
/* 599 */         this.changes[i] = change;
/* 600 */         total += change;
/*     */       } 
/*     */     } 
/* 603 */     return total; } public int getSinkTier() { return 10; } public void func_70296_d() { super.func_70296_d(); recalculate(); } private void recalculate() { this.currentStored = 0; this.maxStorage = 0; for (int i = 0; i < 9; i++) { ItemStack stack = func_70301_a(i); if (stack != null && stack.func_77973_b() instanceof IElectricItem) { IElectricItem item = (IElectricItem)stack.func_77973_b(); this.providers[i] = item.canProvideEnergy(stack); this.batteryTiers[i] = item.getTier(stack); this.maxStorage = (int)(this.maxStorage + item.getMaxCharge(stack)); int charge = (int)ElectricItem.manager.getCharge(stack); this.currentStored += charge; this.stored[i] = charge; } else { this.batteryTiers[i] = 0; this.providers[i] = false; this.stored[i] = 0; }  }  getNetwork().updateTileGuiField((TileEntity)this, "currentStored"); getNetwork().updateTileGuiField((TileEntity)this, "maxStorage"); getNetwork().updateTileGuiField((TileEntity)this, "providers"); getNetwork().updateTileGuiField((TileEntity)this, "batteryTiers"); getNetwork().updateTileGuiField((TileEntity)this, "stored"); getNetwork().updateTileGuiField((TileEntity)this, "maxStorage"); } public void func_145845_h() { super.func_145845_h(); if (this.field_145850_b.field_72995_K) return;  this.tick++; if (this.tick >= 20) { this.tick = 0; double calculationIn = 0.0D; double calculationOut = 0.0D; for (int i = 0; i < 20; i++) { calculationIn += this.incomingTicks[i]; calculationOut += this.outputTicks[i]; }  calculationIn /= 20.0D; calculationOut /= 20.0D; this.currentOutput = (int)calculationOut; this.currentInput = (int)calculationIn; this.currentChange = (int)(calculationIn - calculationOut); getNetwork().updateTileGuiField((TileEntity)this, "currentOutput"); getNetwork().updateTileGuiField((TileEntity)this, "currentInput"); getNetwork().updateTileGuiField((TileEntity)this, "currentChange"); this.incomingTicks = new int[20]; this.outputTicks = new int[20]; }  if (this.field_145850_b.func_82737_E() % 200L == 0L) func_70296_d();  getNetwork().updateTileGuiField((TileEntity)this, "currentStored"); getNetwork().updateTileGuiField((TileEntity)this, "stored"); int newState = getUpdatedState(); if (newState != this.state) { this.state = newState; getNetwork().updateTileEntityField((TileEntity)this, "state"); }  } public void updateEnergyNet(boolean adding) { if (adding) { MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this)); this.added = true; return; }  if (this.added) { this.added = false; MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this)); }  } public void onLoaded() { super.onLoaded(); if (!this.added) { MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this)); this.added = true; }  recalculate(); }
/*     */   public void onUnloaded() { super.onUnloaded(); if (this.added) { this.added = false; MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this)); }  }
/*     */   public void onNetworkEvent(EntityPlayer player, int event) { if (event == 0) { updateEnergyNet(false); this.stationMode++; if (this.stationMode >= maxMode) this.stationMode = 0;  getNetwork().updateTileGuiField((TileEntity)this, "stationMode"); updateEnergyNet(true); IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.batteryBoxMode" + this.stationMode + ".name")); } else if (event == 1) { if (this.stationMode < 1) { IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.batteryBoxNothing.name")); return; }  this.sendingMode++; if (this.sendingMode >= sendingMaxMode) this.sendingMode = 0;  getNetwork().updateTileGuiField((TileEntity)this, "sendingMode"); IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.batteryBoxEnergyMode" + this.sendingMode + ".name")); }  }
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) { return new ContainerBatteryBox(this, p0.field_71071_by); }
/*     */   public String getGuiClassName(EntityPlayer p0) { return "block.wiring.GuiBatteryBox"; }
/* 608 */   public int getTierFromEnergy(int i) { int amount = (int)(EnergyNet.instance.getPowerFromTier(i) / 16.0D);
/* 609 */     if (amount <= 0)
/*     */     {
/* 611 */       return 2;
/*     */     }
/* 613 */     return amount; } public void onGuiClosed(EntityPlayer p0) {} public void func_145839_a(NBTTagCompound nbt) { super.func_145839_a(nbt); this.batteryTiers = nbt.func_74759_k("BatteryTiers"); this.stored = nbt.func_74759_k("Stored"); this.currentChange = nbt.func_74762_e("CurrentChange"); this.currentDraw = nbt.func_74762_e("CurrentDraw"); this.currentInput = nbt.func_74762_e("CurrentInput"); this.currentOutput = nbt.func_74762_e("CurrentOutput"); this.currentStored = nbt.func_74762_e("CurrentStored"); this.maxStorage = nbt.func_74762_e("MaxStorage"); this.sendingMode = nbt.func_74762_e("SendingMode"); this.stationMode = nbt.func_74762_e("StationMode"); injectArray(nbt.func_74770_j("Provider")); this.state = nbt.func_74762_e("State"); } public void func_145841_b(NBTTagCompound nbt) { super.func_145841_b(nbt); nbt.func_74783_a("BatteryTiers", this.batteryTiers); nbt.func_74783_a("Stored", this.stored); nbt.func_74768_a("CurrentChange", this.currentChange); nbt.func_74768_a("CurrentDraw", this.currentDraw); nbt.func_74768_a("CurrentInput", this.currentInput); nbt.func_74768_a("CurrentOutput", this.currentOutput); nbt.func_74768_a("CurrentStored", this.currentStored); nbt.func_74768_a("MaxStorage", this.maxStorage); nbt.func_74768_a("SendingMode", this.sendingMode); nbt.func_74768_a("StationMode", this.stationMode); nbt.func_74773_a("Provider", getProviderInfo()); nbt.func_74768_a("State", this.state); } private byte[] getProviderInfo() { byte[] data = new byte[9]; for (int i = 0; i < 9; i++) data[i] = (byte)(this.providers[i] ? 1 : 0);  return data; } private void injectArray(byte[] par1) { for (int i = 0; i < 9; i++) this.providers[i] = (par1[i] == 1);  }
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) { return (side != getFacing()); }
/*     */   public boolean hasTileMeta() { return true; }
/*     */   public int getTileMeta() { return this.state; }
/*     */   public float getChargeLevel() { float ret = this.currentStored / this.maxStorage; if (ret > 1.0F) ret = 1.0F;  return ret; }
/*     */   public int getUpdatedState() { float charge = getChargeLevel(); if (charge <= 0.01D) return 0;  if (charge >= 0.99D) return 3;  if (charge < 0.5D) return 1;  return 2; }
/* 619 */   public int getTier() { int maxTier = 1;
/* 620 */     for (int i = 0; i < this.providers.length; i++) {
/*     */       
/* 622 */       if (this.providers[i])
/*     */       {
/* 624 */         maxTier = Math.max(maxTier, this.batteryTiers[i]);
/*     */       }
/*     */     } 
/* 627 */     return maxTier; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPower(int amount) {
/* 633 */     for (int i = 0; i < this.changes.length; i++) {
/*     */       
/* 635 */       int change = this.changes[i];
/* 636 */       if (change > 0 && amount > 0) {
/*     */         
/* 638 */         drawFromBattery(i, change);
/* 639 */         amount -= change;
/* 640 */         this.currentStored -= change;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPower(int amount) {
/* 648 */     for (int i = 0; i < this.changes.length; i++) {
/*     */       
/* 650 */       int change = this.changes[i];
/* 651 */       if (change > 0 && amount > 0) {
/*     */         
/* 653 */         charge(i, amount);
/* 654 */         this.currentStored += change;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredPower() {
/* 662 */     return this.currentStored;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStorage() {
/* 668 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 674 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 680 */     return this.currentStored;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 686 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 692 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 698 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 704 */     return (int)EnergyNet.instance.getPowerFromTier(getSinkTier());
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityBatteryBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */