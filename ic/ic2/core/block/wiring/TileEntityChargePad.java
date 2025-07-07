/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.tileentity.FakeMachine;
/*     */ import ic2.core.block.personal.IPersonalBlock;
/*     */ import ic2.core.block.personal.IPersonalInventory;
/*     */ import ic2.core.block.personal.PersonalInventory;
/*     */ import ic2.core.item.ItemChargePadUpgrade;
/*     */ import ic2.core.util.IElectricHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityChargePad
/*     */   extends TileEntityBlock
/*     */   implements IEnergySink, IPersonalBlock, INetworkTileEntityEventListener, IHasGui, IEnergyContainer
/*     */ {
/*     */   public int defaultMaxEnergy;
/*     */   public int defaultTier;
/*     */   public int defaultMaxInput;
/*     */   public int maxEnergy;
/*  63 */   public int storedEnergy = 0;
/*     */   public int tier;
/*     */   public int maxInput;
/*     */   public int transferlimit;
/*     */   public int extraRange;
/*  68 */   public int activeTime = 0;
/*     */   
/*     */   public boolean isForcedOff;
/*     */   
/*     */   public int forcedTicker;
/*     */   
/*  74 */   FakeMachine fake = null;
/*  75 */   public boolean[] installedUpgrades = new boolean[(PadUpgrade.values()).length];
/*     */   
/*     */   public ChargePadType type;
/*     */   
/*     */   public PersonalInventory inv;
/*     */   public boolean Enet = false;
/*     */   
/*     */   public TileEntityChargePad(ChargePadType type) {
/*  83 */     this.type = type;
/*  84 */     this.defaultMaxEnergy = this.maxEnergy = type.getMaxEnergy();
/*  85 */     this.defaultTier = this.tier = type.getTier();
/*  86 */     this.defaultMaxInput = this.maxInput = (int)EnergyNet.instance.getPowerFromTier(this.tier);
/*  87 */     this.transferlimit = this.maxInput;
/*  88 */     this.inv = new PersonalInventory(this, "Inventory", 1 + type.getUpgradeSlots() + ((type == ChargePadType.LV) ? 0 : 2));
/*  89 */     addNetworkFields(new String[] { "type", "installedUpgrades", "extraRange" });
/*  90 */     addGuiFields(new String[] { "storedEnergy", "maxEnergy", "maxInput", "transferlimit" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/*  96 */     return (direction == ForgeDirection.DOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 102 */     return (this.maxEnergy - this.storedEnergy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 108 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 114 */     if (amount > this.maxInput)
/*     */     {
/* 116 */       return 0.0D;
/*     */     }
/* 118 */     this.storedEnergy = (int)(this.storedEnergy + amount);
/* 119 */     int re = 0;
/* 120 */     if (this.storedEnergy > this.maxEnergy) {
/*     */       
/* 122 */       re = this.storedEnergy - this.maxEnergy;
/* 123 */       this.storedEnergy = this.maxEnergy;
/*     */     } 
/* 125 */     getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/* 126 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(EntityPlayer p0) {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(UUID player) {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(EntityPlayer player) {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(UUID player) {
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 156 */     super.onLoaded();
/* 157 */     if (isSimulating())
/*     */     {
/* 159 */       updateUpgrades();
/*     */     }
/*     */     
/* 162 */     if (!this.Enet && isSimulating()) {
/*     */       
/* 164 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 165 */       this.Enet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 171 */     if (isSimulating() && this.Enet) {
/*     */       
/* 173 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 174 */       this.Enet = false;
/*     */     } 
/* 176 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 182 */     return isSimulating();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 188 */     super.func_145845_h();
/* 189 */     if (getDemandedEnergy() > 1.0D) {
/*     */       
/* 191 */       boolean invUpdate = provideEnergy();
/* 192 */       if (invUpdate)
/*     */       {
/* 194 */         func_70296_d();
/*     */       }
/*     */     } 
/* 197 */     if (!getActive()) {
/*     */       
/* 199 */       if (this.isForcedOff && this.forcedTicker > 0) {
/*     */         
/* 201 */         this.forcedTicker--;
/* 202 */         if (this.forcedTicker <= 0)
/*     */         {
/* 204 */           this.isForcedOff = false;
/*     */         }
/*     */       } 
/*     */       return;
/*     */     } 
/* 209 */     if (this.activeTime > 0) {
/*     */       
/* 211 */       this.activeTime--;
/* 212 */       if (this.activeTime <= 0) {
/*     */         
/* 214 */         deactiveChargePad();
/*     */         return;
/*     */       } 
/*     */     } 
/* 218 */     boolean drain = (this.tier > 2 && this.installedUpgrades[PadUpgrade.Drain.ordinal()] && IC2.platform.isPVP());
/* 219 */     int toSend = Math.min(drain ? (this.maxEnergy - this.storedEnergy) : this.storedEnergy, this.transferlimit);
/* 220 */     if (toSend <= 0) {
/*     */       
/* 222 */       setForcedOff(20);
/* 223 */       this.activeTime = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 227 */     List<EntityLivingBase> list = getEntitiesInRange();
/* 228 */     if (list.isEmpty()) {
/*     */       
/* 230 */       deactiveChargePad();
/*     */       
/*     */       return;
/*     */     } 
/* 234 */     if (this.installedUpgrades[PadUpgrade.Damage.ordinal()] && IC2.platform.isPVP()) {
/*     */       
/* 236 */       damageEntities(list);
/* 237 */       this.storedEnergy -= toSend;
/* 238 */       getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/*     */       return;
/*     */     } 
/* 241 */     List<ItemStack> toCharge = new ArrayList<>();
/* 242 */     for (EntityLivingBase base : list) {
/*     */       
/* 244 */       if (base == null || base.field_70128_L) {
/*     */         continue;
/*     */       }
/*     */       
/* 248 */       if (!(base instanceof EntityPlayer)) {
/*     */         continue;
/*     */       }
/*     */       
/* 252 */       EntityPlayer player = (EntityPlayer)base;
/* 253 */       if (this.installedUpgrades[PadUpgrade.ArmorPriority.ordinal()]) {
/*     */         
/* 255 */         int i = toCharge.size();
/* 256 */         addPlayerArmor(player, toCharge, drain);
/* 257 */         if (i == toCharge.size())
/*     */         {
/* 259 */           addPlayerHotbar(player, toCharge, drain);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 264 */       int amount = toCharge.size();
/* 265 */       addPlayerHotbar(player, toCharge, drain);
/* 266 */       if (amount == toCharge.size())
/*     */       {
/* 268 */         addPlayerArmor(player, toCharge, drain);
/*     */       }
/*     */     } 
/*     */     
/* 272 */     int size = toCharge.size();
/* 273 */     if (size <= 0) {
/*     */       
/* 275 */       setForcedOff(20);
/* 276 */       this.activeTime = 0;
/*     */       return;
/*     */     } 
/* 279 */     if (size > 1)
/*     */     {
/* 281 */       Collections.shuffle(toCharge);
/*     */     }
/* 283 */     if (toSend < size)
/*     */     {
/* 285 */       toCharge = toCharge.subList(0, toSend);
/*     */     }
/* 287 */     size = toCharge.size();
/* 288 */     for (ItemStack target : toCharge) {
/*     */       
/* 290 */       int change, toAdd = (int)Math.ceil((toSend / size--));
/*     */       
/* 292 */       if (drain) {
/*     */         
/* 294 */         change = (int)ElectricItem.manager.discharge(target, toAdd, this.tier, true, false, false);
/*     */       }
/*     */       else {
/*     */         
/* 298 */         change = (int)ElectricItem.manager.charge(target, toAdd, this.tier, true, false);
/*     */       } 
/* 300 */       if (change > 0) {
/*     */         
/* 302 */         this.storedEnergy -= change;
/* 303 */         toSend -= change;
/* 304 */         getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void damageEntities(List<EntityLivingBase> list) {
/* 311 */     for (EntityLivingBase base : list) {
/*     */       
/* 313 */       if (base == null || base.field_70128_L) {
/*     */         continue;
/*     */       }
/*     */       
/* 317 */       base.func_70097_a((DamageSource)IC2DamageSource.electricity, 1.0F);
/* 318 */       if (!base.func_70644_a(Potion.field_76436_u))
/*     */       {
/* 320 */         base.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, this.type.ordinal() * 20, this.type.ordinal() * 10));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addPlayerArmor(EntityPlayer player, List<ItemStack> list, boolean drain) {
/* 327 */     ItemStack[] armor = player.field_71071_by.field_70460_b;
/* 328 */     for (int i = 0; i < armor.length; i++) {
/*     */       
/* 330 */       if (canTransferEnergy(armor[i], drain))
/*     */       {
/* 332 */         list.add(armor[i]);
/*     */       }
/*     */     } 
/* 335 */     if (IC2.modul.containsKey("Baubles Modul")) {
/*     */       
/* 337 */       IInventory inv = ((IElectricHelper)IC2.modul.get("Baubles Modul")).getBaublesInventory(player);
/* 338 */       if (inv != null)
/*     */       {
/* 340 */         for (int j = 0; j < inv.func_70302_i_(); j++) {
/*     */           
/* 342 */           ItemStack item = inv.func_70301_a(j);
/* 343 */           if (canTransferEnergy(item, drain))
/*     */           {
/* 345 */             list.add(item);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addPlayerHotbar(EntityPlayer player, List<ItemStack> list, boolean drain) {
/* 354 */     int start = player.field_71071_by.field_70461_c;
/* 355 */     int end = start;
/* 356 */     if (this.installedUpgrades[PadUpgrade.Proximity.ordinal()]) {
/*     */       
/* 358 */       start = Math.max(0, start - 1);
/* 359 */       end = Math.min(8, end + 1);
/*     */     }
/* 361 */     else if (this.installedUpgrades[PadUpgrade.WideBand.ordinal()]) {
/*     */       
/* 363 */       start = 0;
/* 364 */       end = 8;
/*     */     } 
/* 366 */     if (start < 0 || end > 8) {
/*     */       return;
/*     */     }
/*     */     
/* 370 */     for (int i = start; i <= end; i++) {
/*     */       
/* 372 */       ItemStack item = player.field_71071_by.func_70301_a(i);
/* 373 */       if (canTransferEnergy(item, drain))
/*     */       {
/* 375 */         list.add(item);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canTransferEnergy(ItemStack item, boolean drain) {
/* 382 */     if (item == null)
/*     */     {
/* 384 */       return false;
/*     */     }
/* 386 */     if (isElectricItem(item)) {
/*     */       
/* 388 */       IElectricItem eItem = (IElectricItem)item.func_77973_b();
/* 389 */       if (eItem.getTier(item) > this.tier)
/*     */       {
/* 391 */         return false;
/*     */       }
/*     */     }
/* 394 */     else if (!isIndirectElectricItem(item)) {
/*     */       
/* 396 */       return false;
/*     */     } 
/* 398 */     double transfered = 0.0D;
/* 399 */     if (drain) {
/*     */       
/* 401 */       transfered = ElectricItem.manager.discharge(item, 2.147483647E9D, this.tier, true, false, true);
/*     */     }
/*     */     else {
/*     */       
/* 405 */       transfered = ElectricItem.manager.charge(item, 2.147483647E9D, this.tier, true, true);
/*     */     } 
/* 407 */     return (transfered > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIndirectElectricItem(ItemStack item) {
/* 412 */     if (item == null)
/*     */     {
/* 414 */       return false;
/*     */     }
/* 416 */     return (ElectricItem.getBackupManager(item) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isElectricItem(ItemStack item) {
/* 421 */     return (item != null && item.func_77973_b() instanceof IElectricItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EntityLivingBase> getEntitiesInRange() {
/* 426 */     return this.field_145850_b.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a(this.field_145851_c, this.field_145848_d, this.field_145849_e, (this.field_145851_c + 1), this.field_145848_d + 0.25D, (this.field_145849_e + 1)).func_72314_b(this.extraRange, getFieldHeight(), this.extraRange));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getFieldHeight() {
/* 431 */     return 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setForcedOff(int ticks) {
/* 436 */     this.isForcedOff = true;
/* 437 */     this.forcedTicker = ticks;
/* 438 */     deactiveChargePad();
/*     */   }
/*     */ 
/*     */   
/*     */   public FakeMachine getMachine() {
/* 443 */     if (this.fake == null)
/*     */     {
/* 445 */       this.fake = new FakeMachine(this, new ArrayList(Arrays.asList((Object[])new IMachine.UpgradeType[] { IMachine.UpgradeType.MachineModifierA, IMachine.UpgradeType.MachineModifierB })));
/*     */     }
/* 447 */     return this.fake;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUpgrades() {
/* 452 */     getMachine();
/* 453 */     int extraProcessTime = 0;
/* 454 */     double processTimeMultiplier = 1.0D;
/* 455 */     int extraEnergyStorage = 0;
/* 456 */     double energyStorageMultiplier = 1.0D;
/* 457 */     int extraTier = 0;
/* 458 */     this.installedUpgrades = new boolean[(PadUpgrade.values()).length];
/* 459 */     for (int i = 1; i < this.inv.func_70302_i_(); i++) {
/*     */       
/* 461 */       ItemStack stack = this.inv.func_70301_a(i);
/* 462 */       if (stack != null) {
/*     */         
/* 464 */         Item item = stack.func_77973_b();
/* 465 */         if (item instanceof IMachineUpgradeItem) {
/*     */           
/* 467 */           IMachineUpgradeItem mach = (IMachineUpgradeItem)item;
/* 468 */           mach.onInstalling(stack, (IMachine)this.fake);
/* 469 */           extraProcessTime += mach.getExtraProcessTime(stack, (IMachine)this.fake) * stack.field_77994_a;
/* 470 */           processTimeMultiplier *= Math.pow(mach.getProcessTimeMultiplier(stack, (IMachine)this.fake), stack.field_77994_a);
/* 471 */           extraEnergyStorage += mach.getExtraEnergyStorage(stack, (IMachine)this.fake) * stack.field_77994_a;
/* 472 */           energyStorageMultiplier *= Math.pow(mach.getEnergyStorageMultiplier(stack, (IMachine)this.fake), stack.field_77994_a);
/* 473 */           extraTier += mach.getExtraTier(stack, (IMachine)this.fake) * stack.field_77994_a;
/*     */         }
/* 475 */         else if (item instanceof ItemChargePadUpgrade) {
/*     */           
/* 477 */           PadUpgrade up = ((ItemChargePadUpgrade)item).getUpgrade(stack);
/* 478 */           if (up != null)
/*     */           {
/* 480 */             this.installedUpgrades[up.ordinal()] = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 486 */     int newTransfer = applyModifier(this.defaultMaxInput, extraProcessTime, processTimeMultiplier);
/* 487 */     setMaxEnergy(applyModifier(this.defaultMaxEnergy, extraEnergyStorage, energyStorageMultiplier));
/* 488 */     this.tier = this.defaultTier + extraTier;
/* 489 */     this.maxInput = (int)EnergyNet.instance.getPowerFromTier(this.tier);
/* 490 */     this.transferlimit = newTransfer;
/* 491 */     getNetwork().updateTileGuiField((TileEntity)this, "maxInput");
/* 492 */     getNetwork().updateTileGuiField((TileEntity)this, "transferlimit");
/*     */     
/* 494 */     this.extraRange = 0;
/* 495 */     List<PadUpgrade> range = Arrays.asList(new PadUpgrade[] { PadUpgrade.FieldExpI, PadUpgrade.FieldExpII, PadUpgrade.FieldExpIII });
/* 496 */     for (int j = 0; j < range.size(); j++) {
/*     */       
/* 498 */       if (this.installedUpgrades[((PadUpgrade)range.get(j)).ordinal()])
/*     */       {
/* 500 */         this.extraRange += 1 + j;
/*     */       }
/*     */     } 
/* 503 */     getNetwork().updateTileEntityField((TileEntity)this, "extraRange");
/* 504 */     getNetwork().updateTileEntityField((TileEntity)this, "installedUpgrades");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxEnergy(int max) {
/* 509 */     this.maxEnergy = max;
/* 510 */     getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */   }
/*     */ 
/*     */   
/*     */   static int applyModifier(int base, int extra, double multiplier) {
/* 515 */     double ret = Math.round((base + extra) * multiplier);
/* 516 */     return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactiveChargePad() {
/* 521 */     setActive(false);
/* 522 */     getNetwork().initiateTileEntityEvent((TileEntity)this, 0, true);
/* 523 */     for (ForgeDirection dir : ForgeDirection.values()) {
/*     */       
/* 525 */       if (this.field_145850_b.func_72899_e(this.field_145851_c + dir.offsetZ, this.field_145848_d + dir.offsetY, this.field_145849_e + dir.offsetZ))
/*     */       {
/* 527 */         this.field_145850_b.func_147460_e(this.field_145851_c + dir.offsetX, this.field_145848_d + dir.offsetY, this.field_145849_e + dir.offsetZ, func_145838_q());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 535 */     if (event == 0) {
/*     */       
/* 537 */       this.field_145850_b.func_72908_a(this.field_145851_c + 0.5D, this.field_145848_d + 0.25D, this.field_145849_e + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     }
/* 539 */     else if (event == 1) {
/*     */       
/* 541 */       this.field_145850_b.func_72908_a(this.field_145851_c + 0.5D, this.field_145848_d + 0.25D, this.field_145849_e + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean provideEnergy() {
/* 547 */     boolean ret = false;
/* 548 */     if (this.inv.func_70301_a(0) == null)
/*     */     {
/* 550 */       return false;
/*     */     }
/* 552 */     Item item = this.inv.func_70301_a(0).func_77973_b();
/* 553 */     if (item instanceof IElectricItem) {
/*     */       
/* 555 */       if (!((IElectricItem)item).canProvideEnergy(this.inv.func_70301_a(0)))
/*     */       {
/* 557 */         return false;
/*     */       }
/* 559 */       int transfer = (int)ElectricItem.manager.discharge(this.inv.func_70301_a(0), (this.maxEnergy - this.storedEnergy), this.tier, false, true, false);
/* 560 */       this.storedEnergy += transfer;
/* 561 */       if (transfer > 0)
/*     */       {
/* 563 */         getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/*     */       }
/* 565 */       return (ret || transfer > 0);
/*     */     } 
/*     */ 
/*     */     
/* 569 */     if (item == Items.field_151137_ax) {
/*     */       
/* 571 */       this.storedEnergy += this.maxEnergy;
/* 572 */       ItemStack itemStack = this.inv.func_70301_a(0);
/* 573 */       itemStack.field_77994_a--;
/* 574 */       if ((this.inv.func_70301_a(0)).field_77994_a <= 0)
/*     */       {
/* 576 */         this.inv.func_70299_a(0, null);
/*     */       }
/* 578 */       getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/* 579 */       return true;
/*     */     } 
/* 581 */     if (item == Ic2Items.suBattery.func_77973_b()) {
/*     */       
/* 583 */       this.storedEnergy += 1000;
/* 584 */       ItemStack itemStack2 = this.inv.func_70301_a(0);
/* 585 */       itemStack2.field_77994_a--;
/* 586 */       if ((this.inv.func_70301_a(0)).field_77994_a <= 0)
/*     */       {
/* 588 */         this.inv.func_70299_a(0, null);
/*     */       }
/* 590 */       getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/* 591 */       return true;
/*     */     } 
/* 593 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public enum PadUpgrade
/*     */   {
/* 599 */     Damage(false, true, 2),
/* 600 */     Drain(false, false, 2),
/* 601 */     Proximity(true, false, 2),
/* 602 */     WideBand(true, true, 2),
/* 603 */     ArmorPriority(false, false, 2),
/* 604 */     FieldExpI(true, false, 2),
/* 605 */     FieldExpII(true, false, 3),
/* 606 */     FieldExpIII(true, true, 3);
/*     */     
/*     */     public boolean isProject;
/*     */     
/*     */     public boolean isRare;
/*     */     public int requiredTier;
/*     */     
/*     */     PadUpgrade(boolean par1, boolean par2, int par3) {
/* 614 */       this.isProject = par1;
/* 615 */       this.isRare = par2;
/* 616 */       this.requiredTier = par3;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ChargePadType
/*     */   {
/* 622 */     LV(40, 1, 1, 3.5F, TileEntityChargePadLV.class, "tile.chargePadLV.name"),
/* 623 */     MV(600, 2, 2, 2.5F, TileEntityChargePadMV.class, "tile.chargePadMV.name"),
/* 624 */     HV(10000, 3, 3, 1.5F, TileEntityChargePadHV.class, "tile.chargePadHV.name"),
/* 625 */     Nuclear(150000, 4, 3, 1.5F, TileEntityChargePadNuclear.class, "tile.chargePadNuclear.name");
/*     */     
/*     */     int maxEnergy;
/*     */     
/*     */     int tier;
/*     */     int upgradeSlots;
/*     */     float maxImpact;
/*     */     String name;
/*     */     Class<? extends TileEntityChargePad> clz;
/*     */     
/*     */     ChargePadType(int par1, int par2, int par3, float par4, Class<? extends TileEntityChargePad> par5, String par6) {
/* 636 */       this.maxEnergy = par1 * 1000;
/* 637 */       this.tier = par2;
/* 638 */       this.upgradeSlots = par3;
/* 639 */       this.maxImpact = par4;
/* 640 */       this.clz = par5;
/* 641 */       this.name = par6;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxEnergy() {
/* 646 */       return this.maxEnergy;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTier() {
/* 651 */       return this.tier;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getUpgradeSlots() {
/* 656 */       return this.upgradeSlots;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getMaxImpact() {
/* 661 */       return this.maxImpact;
/*     */     }
/*     */ 
/*     */     
/*     */     public TileEntityChargePad createPad() {
/* 666 */       if (this.clz == null)
/*     */       {
/* 668 */         return null;
/*     */       }
/*     */       
/*     */       try {
/* 672 */         return this.clz.newInstance();
/*     */       }
/* 674 */       catch (Exception exception) {
/*     */ 
/*     */         
/* 677 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public static ChargePadType getType(int meta) {
/* 682 */       ChargePadType[] types = values();
/* 683 */       if (meta >= 0 && meta < types.length)
/*     */       {
/* 685 */         return types[meta];
/*     */       }
/* 687 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 692 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticals(Random rand) {
/* 699 */     float multPos = (this.extraRange * 2) + 0.9F;
/* 700 */     int blockX = this.field_145851_c - this.extraRange;
/* 701 */     int blockZ = this.field_145849_e - this.extraRange;
/* 702 */     boolean swirl = (this.type == ChargePadType.Nuclear);
/* 703 */     EffectRenderer er = (Minecraft.func_71410_x()).field_71452_i;
/* 704 */     for (int i = 1 + this.extraRange * 2; i > 0; i--) {
/*     */       
/* 706 */       for (int k = getParticalAmount(rand); k > 0; k--) {
/*     */         
/* 708 */         double x = (blockX + 0.05F + rand.nextFloat() * multPos);
/* 709 */         double y = (this.field_145848_d + 0.2F + rand.nextFloat() * 0.2F);
/* 710 */         double z = (blockZ + 0.05F + rand.nextFloat() * multPos);
/* 711 */         double[] velocity = getParticleVelocity(rand);
/* 712 */         if (k < 4)
/*     */         {
/* 714 */           velocity[2] = velocity[2] * 0.55D;
/*     */         }
/* 716 */         float[] colour = getParticalColour(rand);
/* 717 */         er.func_78873_a(new EntityChargePadAuraFX(this.field_145850_b, x, y, z, getMaxParticalAge(), velocity, colour, swirl));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected abstract int getMaxParticalAge();
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected abstract float[] getParticalColour(Random paramRandom);
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected abstract double[] getParticleVelocity(Random paramRandom);
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected abstract int getParticalAmount(Random paramRandom);
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 737 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 743 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 749 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ItemStack getWrenchDrop(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 758 */     super.func_145839_a(nbt);
/* 759 */     this.activeTime = nbt.func_74762_e("ActiveTime");
/* 760 */     this.forcedTicker = nbt.func_74762_e("ForcedTime");
/* 761 */     this.storedEnergy = nbt.func_74762_e("StoredEnergy");
/* 762 */     this.isForcedOff = nbt.func_74767_n("ForcedOff");
/* 763 */     this.inv.readFromNBT(nbt.func_74775_l("Inventory"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 769 */     super.func_145841_b(nbt);
/* 770 */     nbt.func_74768_a("ActiveTime", this.activeTime);
/* 771 */     nbt.func_74768_a("ForcedTime", this.forcedTicker);
/* 772 */     nbt.func_74768_a("StoredEnergy", this.storedEnergy);
/* 773 */     nbt.func_74757_a("ForcedOff", this.isForcedOff);
/* 774 */     NBTTagCompound data = new NBTTagCompound();
/* 775 */     this.inv.writeToNBT(data);
/* 776 */     nbt.func_74782_a("Inventory", (NBTBase)data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 782 */     return new ContainerChargePad(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 788 */     return "block.wiring.GuiChargePad";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 800 */     return this.storedEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 806 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 812 */     return this.transferlimit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 818 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 824 */     return this.maxInput;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityChargePad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */