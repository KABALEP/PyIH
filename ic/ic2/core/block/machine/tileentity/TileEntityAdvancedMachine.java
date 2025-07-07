/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.api.tile.IRecipeMachine;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerAdvancedMachine;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public abstract class TileEntityAdvancedMachine
/*     */   extends TileEntityElecMachine implements IRecipeMachine, IHasGui, INetworkTileEntityEventListener, ISidedInventory {
/*  31 */   public int speed = 0;
/*     */   public int MaxSpeed;
/*  33 */   public short progress = 0;
/*     */   public int defaultEnergyConsume;
/*     */   public int defaultOperationLength;
/*     */   public int defaultMaxInput;
/*     */   public int defaultEnergyStorage;
/*     */   public int energyConsume;
/*     */   public int operationLength;
/*  40 */   public float soundLevel = 1.0F;
/*     */   public AudioSource audioSource;
/*     */   public boolean redstoneInverted;
/*     */   public boolean redstoneSensitive;
/*     */   public boolean defaultSensitive;
/*     */   public boolean isProcessing;
/*  46 */   public List<ItemStack> results = new ArrayList<>();
/*  47 */   private static int EventStart = 0;
/*  48 */   private static int EventInterrupt = 1;
/*  49 */   private static int EventStop = 2;
/*     */ 
/*     */   
/*     */   public TileEntityAdvancedMachine(int slots, int usage, int maxProgress) {
/*  53 */     super(slots + 2, 0, 10000, 128, 2);
/*  54 */     this.defaultEnergyConsume = this.energyConsume = usage;
/*  55 */     this.MaxSpeed = 10000;
/*  56 */     this.defaultOperationLength = this.operationLength = maxProgress;
/*  57 */     this.defaultEnergyStorage = 10000;
/*  58 */     this.redstoneSensitive = true;
/*  59 */     this.defaultSensitive = true;
/*  60 */     addNetworkFields(new String[] { "redstoneInverted", "redstoneSensitive", "soundLevel" });
/*  61 */     addGuiFields(new String[] { "operationLength", "progress", "speed" });
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract RecipeOutput getResultFor(ItemStack paramItemStack, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   public abstract IMachineRecipeManager getRecipeList();
/*     */   
/*     */   public abstract Slot[] getInvSlots(InventoryPlayer paramInventoryPlayer);
/*     */   
/*     */   public abstract int[] getOutputSlots();
/*     */   
/*     */   public abstract boolean isInputSlot(int paramInt);
/*     */   
/*     */   public abstract boolean isOutputSlot(int paramInt);
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  79 */     super.func_145839_a(nbttagcompound);
/*  80 */     this.progress = nbttagcompound.func_74765_d("progress");
/*  81 */     this.speed = nbttagcompound.func_74762_e("Speed");
/*  82 */     this.results.clear();
/*  83 */     NBTTagList list = nbttagcompound.func_150295_c("Results", 10);
/*  84 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  86 */       NBTTagCompound nbt = list.func_150305_b(i);
/*  87 */       this.results.add(ItemStack.func_77949_a(nbt));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  94 */     super.func_145841_b(nbttagcompound);
/*  95 */     nbttagcompound.func_74777_a("progress", this.progress);
/*  96 */     nbttagcompound.func_74768_a("Speed", this.speed);
/*  97 */     NBTTagList list = new NBTTagList();
/*  98 */     for (ItemStack item : this.results) {
/*     */       
/* 100 */       NBTTagCompound nbt = new NBTTagCompound();
/* 101 */       item.func_77955_b(nbt);
/* 102 */       list.func_74742_a((NBTBase)nbt);
/*     */     } 
/* 104 */     nbttagcompound.func_74782_a("Results", (NBTBase)list);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getChargeLevel() {
/* 109 */     float ret = this.energy / this.maxEnergy;
/* 110 */     if (ret < 0.0F)
/*     */     {
/* 112 */       ret = 0.0F;
/*     */     }
/* 114 */     if (ret > 1.0F)
/*     */     {
/* 116 */       ret = 1.0F;
/*     */     }
/* 118 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeedLevel() {
/* 123 */     float ret = this.speed / this.MaxSpeed;
/* 124 */     if (ret > 1.0F)
/*     */     {
/* 126 */       ret = 1.0F;
/*     */     }
/* 128 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getProgress() {
/* 133 */     float ret = getClientProgress() / getClientOperationLenght();
/* 134 */     if (ret > 1.0F)
/*     */     {
/* 136 */       ret = 1.0F;
/*     */     }
/* 138 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProcessing() {
/* 144 */     return this.isProcessing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 150 */     return (getFacing() != side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 156 */     super.func_145845_h();
/* 157 */     handleRedstone();
/* 158 */     boolean needsInvUpdate = addToInventory();
/* 159 */     RecipeOutput output = getOutput();
/* 160 */     boolean canWork = (canWork() || (output != null && this.energy > 0));
/* 161 */     if (this.energy < this.maxEnergy)
/*     */     {
/* 163 */       needsInvUpdate = provideEnergy();
/*     */     }
/* 165 */     if ((canWork || output != null) && this.energy > 0) {
/*     */       
/* 167 */       if (this.speed < this.MaxSpeed) {
/*     */         
/* 169 */         this.speed++;
/* 170 */         getNetwork().updateTileGuiField((TileEntity)this, "speed");
/*     */       } 
/* 172 */       useEnergy(1);
/*     */ 
/*     */     
/*     */     }
/* 176 */     else if (this.speed > 0) {
/*     */       
/* 178 */       this.speed -= Math.min(this.speed, 4);
/* 179 */       getNetwork().updateTileGuiField((TileEntity)this, "speed");
/*     */     } 
/*     */     
/* 182 */     if (canWork && output != null && this.energy >= this.energyConsume) {
/*     */       
/* 184 */       setActive(true);
/* 185 */       this.isProcessing = true;
/* 186 */       if (this.progress == 0)
/*     */       {
/* 188 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 0, true);
/*     */       }
/* 190 */       this.progress = (short)(this.progress + this.speed / 30);
/* 191 */       useEnergy(this.energyConsume);
/* 192 */       if (this.progress >= getOperationLenght()) {
/*     */         
/* 194 */         this.progress = 0;
/* 195 */         operate();
/* 196 */         needsInvUpdate = true;
/* 197 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 2, true);
/*     */       } 
/* 199 */       getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */     }
/*     */     else {
/*     */       
/* 203 */       if (this.progress != 0 && getActive())
/*     */       {
/* 205 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 1, true);
/*     */       }
/* 207 */       if (output == null && this.progress != 0) {
/*     */         
/* 209 */         this.progress = 0;
/* 210 */         getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */       } 
/* 212 */       this.isProcessing = false;
/* 213 */       setActive(false);
/*     */     } 
/* 215 */     if (canWork && this.energy > 0)
/*     */     {
/* 217 */       setActive(true);
/*     */     }
/* 219 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 221 */       ItemStack item = this.inventory[i + this.inventory.length - 4];
/* 222 */       if (item != null && item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 224 */         IMachineUpgradeItem upgrade = (IMachineUpgradeItem)item.func_77973_b();
/* 225 */         if (upgrade.onTick(item, (IMachine)this))
/*     */         {
/* 227 */           needsInvUpdate = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 231 */     if (needsInvUpdate)
/*     */     {
/* 233 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 240 */     return isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public void operate() {
/* 245 */     operate(new int[] { 1 });
/*     */   }
/*     */ 
/*     */   
/*     */   public void operate(int... slots) {
/* 250 */     List<ItemStack> result = new ArrayList<>();
/* 251 */     for (int slot : slots) {
/*     */       
/* 253 */       RecipeOutput output = getOutput(slot);
/* 254 */       if (output != null)
/*     */       {
/* 256 */         operateOnce(copy(output), slot, result);
/*     */       }
/*     */     } 
/* 259 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 261 */       ItemStack itemStack = this.inventory[i + this.inventory.length - 2];
/* 262 */       if (itemStack != null && itemStack.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 264 */         IMachineUpgradeItem item = (IMachineUpgradeItem)itemStack.func_77973_b();
/* 265 */         item.onProcessEnd(itemStack, (IMachine)this, result);
/*     */       } 
/*     */     } 
/* 268 */     if (result.size() > 0) {
/*     */       
/* 270 */       this.results.addAll(result);
/* 271 */       addToInventory();
/*     */     } 
/* 273 */     for (int slot : slots) {
/*     */       
/* 275 */       if (this.inventory[slot] != null && (this.inventory[slot]).field_77994_a <= 0)
/*     */       {
/* 277 */         this.inventory[slot] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, int slot, List<ItemStack> items) {
/* 284 */     getResultFor(this.inventory[slot], true);
/* 285 */     items.addAll(result.items);
/*     */   }
/*     */ 
/*     */   
/*     */   private RecipeOutput copy(RecipeOutput par1) {
/* 290 */     List<ItemStack> items = new ArrayList<>();
/* 291 */     for (ItemStack item : par1.items)
/*     */     {
/* 293 */       items.add(item.func_77946_l());
/*     */     }
/* 295 */     return new RecipeOutput(par1.metadata, items);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutput() {
/* 300 */     return getOutput(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutput(int input) {
/* 305 */     if (this.inventory[input] == null && !canWorkWithoutItems())
/*     */     {
/* 307 */       return null;
/*     */     }
/* 309 */     if (this.results.size() > 0)
/*     */     {
/* 311 */       return null;
/*     */     }
/* 313 */     ItemStack item = ItemStack.func_77944_b(this.inventory[input]);
/* 314 */     RecipeOutput out = getResultFor(item, false);
/* 315 */     if (out == null)
/*     */     {
/* 317 */       return null;
/*     */     }
/* 319 */     if (hasEmptyOutput())
/*     */     {
/* 321 */       return out;
/*     */     }
/*     */     
/* 324 */     for (ItemStack output : out.items) {
/*     */       
/* 326 */       for (int slot : getOutputSlots()) {
/*     */         
/* 328 */         if (this.inventory[slot].func_77969_a(output) && ItemStack.func_77970_a(this.inventory[slot], output))
/*     */         {
/* 330 */           if ((this.inventory[slot]).field_77994_a + output.field_77994_a <= this.inventory[slot].func_77976_d())
/*     */           {
/* 332 */             return out;
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasEmptyOutput() {
/* 342 */     for (int slot : getOutputSlots()) {
/*     */       
/* 344 */       if (this.inventory[slot] == null)
/*     */       {
/* 346 */         return true;
/*     */       }
/*     */     } 
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWorkWithoutItems() {
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 360 */     super.onLoaded();
/* 361 */     if (isSimulating())
/*     */     {
/* 363 */       setOverclockRates();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOperationLenght() {
/* 369 */     return this.operationLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClientProgress() {
/* 374 */     return this.progress;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClientOperationLenght() {
/* 379 */     return this.operationLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 385 */     super.onUnloaded();
/* 386 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 388 */       IC2.audioManager.removeSources(this);
/* 389 */       this.audioSource = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 396 */     return (ContainerIC2)new ContainerAdvancedMachine(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverclockRates() {
/* 402 */     int extraProcessTime = 0;
/* 403 */     double processTimeMultiplier = 1.0D;
/* 404 */     int extraEnergyDemand = 0;
/* 405 */     double energyDemandMultiplier = 1.0D;
/* 406 */     int extraEnergyStorage = 0;
/* 407 */     double energyStorageMultiplier = 1.0D;
/* 408 */     int extraTier = 0;
/* 409 */     float soundModfier = 1.0F;
/* 410 */     boolean redstonePowered = false;
/* 411 */     this.redstoneSensitive = this.defaultSensitive;
/* 412 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 414 */       ItemStack item = this.inventory[i + this.inventory.length - 2];
/* 415 */       if (item != null && item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 417 */         IMachineUpgradeItem upgrade = (IMachineUpgradeItem)item.func_77973_b();
/* 418 */         upgrade.onInstalling(item, (IMachine)this);
/* 419 */         extraProcessTime += upgrade.getExtraProcessTime(item, (IMachine)this) * item.field_77994_a;
/* 420 */         processTimeMultiplier *= Math.pow(upgrade.getProcessTimeMultiplier(item, (IMachine)this), item.field_77994_a);
/* 421 */         extraEnergyDemand += upgrade.getExtraEnergyDemand(item, (IMachine)this) * item.field_77994_a;
/* 422 */         energyDemandMultiplier *= Math.pow(upgrade.getEnergyDemandMultiplier(item, (IMachine)this), item.field_77994_a);
/* 423 */         extraEnergyStorage += upgrade.getExtraEnergyStorage(item, (IMachine)this) * item.field_77994_a;
/* 424 */         energyStorageMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, (IMachine)this), item.field_77994_a);
/* 425 */         soundModfier = (float)(soundModfier * Math.pow(upgrade.getSoundVolumeMultiplier(item, (IMachine)this), item.field_77994_a));
/* 426 */         extraTier += upgrade.getExtraTier(item, (IMachine)this) * item.field_77994_a;
/* 427 */         if (upgrade.useRedstoneinverter(item, (IMachine)this))
/*     */         {
/* 429 */           redstonePowered = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 433 */     this.redstoneInverted = redstonePowered;
/*     */     
/* 435 */     this.energyConsume = applyModifier(this.defaultEnergyConsume, extraEnergyDemand, energyDemandMultiplier);
/* 436 */     this.operationLength = applyModifier(this.defaultOperationLength, extraProcessTime, processTimeMultiplier);
/* 437 */     setMaxEnergy(applyModifier(this.defaultEnergyStorage, extraEnergyStorage, energyStorageMultiplier));
/* 438 */     this.tier = this.baseTier + extraTier;
/* 439 */     this.maxInput = (int)EnergyNet.instance.getPowerFromTier(this.tier);
/* 440 */     this.soundLevel = soundModfier;
/* 441 */     if (this.maxInput < 0)
/*     */     {
/* 443 */       this.maxInput = Integer.MAX_VALUE;
/*     */     }
/* 445 */     if (this.energy > this.maxEnergy)
/*     */     {
/* 447 */       this.energy = this.maxEnergy;
/*     */     }
/* 449 */     if (this.operationLength < 1)
/*     */     {
/* 451 */       this.operationLength = 1;
/*     */     }
/* 453 */     if (this.energyConsume < 1)
/*     */     {
/* 455 */       this.energyConsume = 1;
/*     */     }
/* 457 */     getNetwork().updateTileEntityField((TileEntity)this, "redstoneInverted");
/* 458 */     getNetwork().updateTileEntityField((TileEntity)this, "redstoneSensitive");
/* 459 */     getNetwork().updateTileEntityField((TileEntity)this, "soundLevel");
/* 460 */     getNetwork().updateTileGuiField((TileEntity)this, "operationLength");
/* 461 */     getNetwork().updateTileGuiField((TileEntity)this, "energyConsume");
/* 462 */     getNetwork().updateTileGuiField((TileEntity)this, "maxInput");
/* 463 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   static int applyModifier(int base, int extra, double multiplier) {
/* 468 */     double ret = Math.round((base + extra) * multiplier);
/* 469 */     return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String func_145825_b();
/*     */   
/*     */   public String getStartSoundFile() {
/* 476 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 481 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 487 */     if (this.audioSource != null && this.audioSource.isRemoved())
/*     */     {
/* 489 */       this.audioSource = null;
/*     */     }
/* 491 */     if (this.audioSource == null && getStartSoundFile() != null) {
/*     */       
/* 493 */       this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getStartSoundFile(), true, false, IC2.audioManager.defaultVolume * this.soundLevel);
/* 494 */       this.audioSource.setLoop(true);
/*     */     } 
/*     */     
/* 497 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 501 */         if (this.audioSource != null)
/*     */         {
/* 503 */           this.audioSource.play();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 510 */         if (this.audioSource == null) {
/*     */           break;
/*     */         }
/*     */         
/* 514 */         this.audioSource.stop();
/* 515 */         if (getInterruptSoundFile() != null)
/*     */         {
/* 517 */           IC2.audioManager.playOnce(this, PositionSpec.Center, getInterruptSoundFile(), false, IC2.audioManager.defaultVolume * this.soundLevel);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 524 */         if (this.audioSource != null)
/*     */         {
/* 526 */           this.audioSource.stop();
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 537 */     super.onNetworkUpdate(field);
/* 538 */     if (field.equals("soundLevel"))
/*     */     {
/* 540 */       if (this.audioSource != null)
/*     */       {
/* 542 */         this.audioSource.setVolume(IC2.audioManager.defaultVolume * this.soundLevel);
/*     */       }
/*     */     }
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
/*     */   public double getEnergy() {
/* 556 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount, boolean simulate) {
/* 562 */     if (amount > this.energy)
/*     */     {
/* 564 */       return false;
/*     */     }
/* 566 */     if (!simulate)
/*     */     {
/* 568 */       this.energy = (int)(this.energy - amount);
/*     */     }
/* 570 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSensitive(boolean active) {
/* 576 */     if (this.redstoneSensitive != active)
/*     */     {
/* 578 */       this.redstoneSensitive = active;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWork() {
/* 584 */     if (!this.redstoneSensitive)
/*     */     {
/* 586 */       return true;
/*     */     }
/* 588 */     return isRedstonePowered();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRedstonePowered() {
/* 594 */     if (this.redstoneInverted)
/*     */     {
/* 596 */       return !super.isRedstonePowered();
/*     */     }
/* 598 */     return super.isRedstonePowered();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRedstoneSensitive() {
/* 604 */     return this.redstoneSensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 610 */     return (this.inventory[1] == null || (this.inventory[1].func_77969_a(par1) && ItemStack.func_77970_a(this.inventory[1], par1)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMachine.UpgradeType> getSupportedTypes() {
/* 616 */     List<IMachine.UpgradeType> types = new ArrayList<>();
/* 617 */     types.addAll(Arrays.asList(IMachine.UpgradeType.values()));
/* 618 */     types.remove(IMachine.UpgradeType.MachineModifierA);
/* 619 */     return types;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 625 */     return (isInputSlot(i) && isValidInput(itemstack));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 631 */     return (isInputSlot(i) && isValidInput(itemstack));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 637 */     return isOutputSlot(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addToInventory() {
/* 642 */     if (this.results.isEmpty())
/*     */     {
/* 644 */       return false;
/*     */     }
/* 646 */     int added = 0;
/* 647 */     for (int i = 0; i < this.results.size(); i++) {
/*     */       
/* 649 */       ItemStack item = this.results.get(i);
/* 650 */       if (item != null && item.field_77994_a <= 0) {
/*     */         
/* 652 */         this.results.remove(i--);
/*     */       } else {
/*     */         
/* 655 */         for (int slot : getOutputSlots()) {
/*     */           
/* 657 */           if (this.results.isEmpty() || item == null) {
/*     */             break;
/*     */           }
/*     */           
/* 661 */           if (this.inventory[slot] == null) {
/*     */             
/* 663 */             this.inventory[slot] = ItemStack.func_77944_b(item);
/* 664 */             this.results.remove(i--);
/* 665 */             added++;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 670 */           if (item.func_77969_a(this.inventory[slot]) && ItemStack.func_77970_a(item, this.inventory[slot])) {
/*     */             
/* 672 */             int left = this.inventory[slot].func_77976_d() - (this.inventory[slot]).field_77994_a;
/* 673 */             if (left > 0) {
/*     */ 
/*     */ 
/*     */               
/* 677 */               if (left >= item.field_77994_a) {
/*     */                 
/* 679 */                 (this.inventory[slot]).field_77994_a += item.field_77994_a;
/* 680 */                 added++;
/* 681 */                 this.results.remove(i--);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 686 */               int itemLeft = item.field_77994_a - left;
/* 687 */               item.field_77994_a = itemLeft;
/* 688 */               (this.inventory[slot]).field_77994_a = this.inventory[slot].func_77976_d();
/* 689 */               added++;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 696 */     return (added > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 702 */     return this.energyConsume + 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityAdvancedMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */