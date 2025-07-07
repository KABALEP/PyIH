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
/*     */ import ic2.core.block.machine.container.ContainerElectricMachine;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public abstract class TileEntityElectricMachine
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui, INetworkTileEntityEventListener, ISidedInventory, IRecipeMachine {
/*  30 */   public short progress = 0;
/*     */   public int defaultEnergyConsume;
/*     */   public int defaultOperationLength;
/*     */   public int defaultMaxInput;
/*     */   public int defaultEnergyStorage;
/*     */   public int energyConsume;
/*     */   public int operationLength;
/*     */   public float serverChargeLevel;
/*     */   public float serverProgress;
/*  39 */   public float soundLevel = 1.0F;
/*     */   public AudioSource audioSource;
/*     */   public boolean redstoneInverted;
/*     */   public boolean redstoneSensitive;
/*     */   public boolean defaultSensitive;
/*  44 */   public List<ItemStack> results = new ArrayList<>();
/*  45 */   private static int EventStart = 0;
/*  46 */   private static int EventInterrupt = 1;
/*  47 */   private static int EventStop = 2;
/*     */ 
/*     */   
/*     */   public TileEntityElectricMachine(int slots, int e, int length, int maxinput) {
/*  51 */     super(slots + 4, 1, e * length + maxinput - 1, maxinput);
/*  52 */     this.energyConsume = e;
/*  53 */     this.defaultEnergyConsume = e;
/*  54 */     this.operationLength = length;
/*  55 */     this.defaultOperationLength = length;
/*  56 */     this.defaultMaxInput = this.maxInput;
/*  57 */     this.defaultEnergyStorage = e * length;
/*  58 */     this.defaultSensitive = false;
/*  59 */     addNetworkFields(new String[] { "redstoneInverted", "redstoneSensitive", "soundLevel" });
/*  60 */     addGuiFields(new String[] { "operationLength", "progress" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  66 */     super.func_145839_a(nbttagcompound);
/*  67 */     this.progress = nbttagcompound.func_74765_d("progress");
/*  68 */     this.results.clear();
/*  69 */     NBTTagList list = nbttagcompound.func_150295_c("Results", 10);
/*  70 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  72 */       NBTTagCompound nbt = list.func_150305_b(i);
/*  73 */       this.results.add(ItemStack.func_77949_a(nbt));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  80 */     super.func_145841_b(nbttagcompound);
/*  81 */     nbttagcompound.func_74777_a("progress", this.progress);
/*  82 */     NBTTagList list = new NBTTagList();
/*  83 */     for (ItemStack item : this.results) {
/*     */       
/*  85 */       NBTTagCompound nbt = new NBTTagCompound();
/*  86 */       item.func_77955_b(nbt);
/*  87 */       list.func_74742_a((NBTBase)nbt);
/*     */     } 
/*  89 */     nbttagcompound.func_74782_a("Results", (NBTBase)list);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getChargeLevel() {
/*  94 */     float ret = this.energy / this.maxEnergy;
/*  95 */     if (ret > 1.0F)
/*     */     {
/*  97 */       ret = 1.0F;
/*     */     }
/*  99 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getProgress() {
/* 104 */     float ret = this.progress / this.operationLength;
/* 105 */     if (ret > 1.0F)
/*     */     {
/* 107 */       ret = 1.0F;
/*     */     }
/* 109 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 115 */     super.onLoaded();
/* 116 */     if (isSimulating())
/*     */     {
/* 118 */       setOverclockRates();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 125 */     super.onUnloaded();
/* 126 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 128 */       IC2.audioManager.removeSources(this);
/* 129 */       this.audioSource = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProcessing() {
/* 136 */     return getActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 141 */     super.func_145845_h();
/* 142 */     handleRedstone();
/* 143 */     boolean needsInvUpdate = addToInventory();
/* 144 */     RecipeOutput output = getOutput();
/* 145 */     boolean canWork = canWork();
/* 146 */     if (this.energy <= this.energyConsume * this.operationLength && output != null && canWork)
/*     */     {
/* 148 */       if (provideEnergy())
/*     */       {
/* 150 */         needsInvUpdate = true;
/*     */       }
/*     */     }
/* 153 */     if (canWork && output != null && this.energy >= this.energyConsume) {
/*     */       
/* 155 */       setActive(true);
/* 156 */       if (this.progress == 0)
/*     */       {
/* 158 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 0, true);
/*     */       }
/* 160 */       this.progress = (short)(this.progress + 1);
/* 161 */       useEnergy(this.energyConsume);
/* 162 */       if (this.progress >= this.operationLength) {
/*     */         
/* 164 */         operate(copy(output));
/* 165 */         needsInvUpdate = true;
/* 166 */         this.progress = 0;
/* 167 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 2, true);
/*     */       } 
/* 169 */       getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */     }
/*     */     else {
/*     */       
/* 173 */       if (this.progress != 0 && getActive())
/*     */       {
/* 175 */         getNetwork().initiateTileEntityEvent((TileEntity)this, 1, true);
/*     */       }
/* 177 */       if (output == null && this.progress != 0) {
/*     */         
/* 179 */         this.progress = 0;
/* 180 */         getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */       } 
/* 182 */       setActive(false);
/*     */     } 
/*     */     
/* 185 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 187 */       ItemStack item = this.inventory[i + this.inventory.length - 4];
/* 188 */       if (item != null && item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 190 */         IMachineUpgradeItem upgrade = (IMachineUpgradeItem)item.func_77973_b();
/* 191 */         if (upgrade.onTick(item, (IMachine)this))
/*     */         {
/* 193 */           needsInvUpdate = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 197 */     if (needsInvUpdate)
/*     */     {
/* 199 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private RecipeOutput getOutput() {
/* 205 */     if (this.inventory[0] == null && !canWorkWithoutItems())
/*     */     {
/* 207 */       return null;
/*     */     }
/* 209 */     if (this.results.size() > 0)
/*     */     {
/* 211 */       return null;
/*     */     }
/* 213 */     ItemStack item = ItemStack.func_77944_b(this.inventory[0]);
/* 214 */     RecipeOutput out = getResultFor(item, false);
/* 215 */     if (out == null)
/*     */     {
/* 217 */       return null;
/*     */     }
/* 219 */     if (this.inventory[2] == null)
/*     */     {
/* 221 */       return out;
/*     */     }
/*     */     
/* 224 */     for (ItemStack output : out.items) {
/*     */       
/* 226 */       if (this.inventory[2].func_77969_a(output) && ItemStack.func_77970_a(this.inventory[2], output)) {
/*     */         
/* 228 */         if ((this.inventory[2]).field_77994_a + output.field_77994_a <= this.inventory[2].func_77976_d())
/*     */         {
/* 230 */           return out;
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWorkWithoutItems() {
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private RecipeOutput copy(RecipeOutput par1) {
/* 245 */     List<ItemStack> items = new ArrayList<>();
/* 246 */     for (ItemStack item : par1.items)
/*     */     {
/* 248 */       items.add(item.func_77946_l());
/*     */     }
/* 250 */     return new RecipeOutput(par1.metadata, items);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverclockRates() {
/* 255 */     int extraProcessTime = 0;
/* 256 */     double processTimeMultiplier = 1.0D;
/* 257 */     int extraEnergyDemand = 0;
/* 258 */     double energyDemandMultiplier = 1.0D;
/* 259 */     int extraEnergyStorage = 0;
/* 260 */     double energyStorageMultiplier = 1.0D;
/* 261 */     int extraTier = 0;
/* 262 */     float soundModfier = 1.0F;
/* 263 */     boolean redstonePowered = false;
/* 264 */     this.redstoneSensitive = this.defaultSensitive;
/* 265 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 267 */       ItemStack item = this.inventory[i + this.inventory.length - 4];
/* 268 */       if (item != null && item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 270 */         IMachineUpgradeItem upgrade = (IMachineUpgradeItem)item.func_77973_b();
/* 271 */         upgrade.onInstalling(item, (IMachine)this);
/* 272 */         extraProcessTime += upgrade.getExtraProcessTime(item, (IMachine)this) * item.field_77994_a;
/* 273 */         processTimeMultiplier *= Math.pow(upgrade.getProcessTimeMultiplier(item, (IMachine)this), item.field_77994_a);
/* 274 */         extraEnergyDemand += upgrade.getExtraEnergyDemand(item, (IMachine)this) * item.field_77994_a;
/* 275 */         energyDemandMultiplier *= Math.pow(upgrade.getEnergyDemandMultiplier(item, (IMachine)this), item.field_77994_a);
/* 276 */         extraEnergyStorage += upgrade.getExtraEnergyStorage(item, (IMachine)this) * item.field_77994_a;
/* 277 */         energyStorageMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, (IMachine)this), item.field_77994_a);
/* 278 */         soundModfier = (float)(soundModfier * Math.pow(upgrade.getSoundVolumeMultiplier(item, (IMachine)this), item.field_77994_a));
/* 279 */         extraTier += upgrade.getExtraTier(item, (IMachine)this) * item.field_77994_a;
/* 280 */         if (upgrade.useRedstoneinverter(item, (IMachine)this))
/*     */         {
/* 282 */           redstonePowered = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 286 */     this.redstoneInverted = redstonePowered;
/*     */     
/* 288 */     this.energyConsume = applyModifier(this.defaultEnergyConsume, extraEnergyDemand, energyDemandMultiplier);
/* 289 */     this.operationLength = applyModifier(this.defaultOperationLength, extraProcessTime, processTimeMultiplier);
/* 290 */     setMaxEnergy(applyModifier(this.defaultEnergyStorage, extraEnergyStorage, energyStorageMultiplier));
/* 291 */     this.tier = this.baseTier + extraTier;
/* 292 */     this.maxInput = (int)EnergyNet.instance.getPowerFromTier(this.tier);
/* 293 */     if (this.maxInput < 0)
/*     */     {
/* 295 */       this.maxInput = Integer.MAX_VALUE;
/*     */     }
/* 297 */     if (this.energy > this.maxEnergy)
/*     */     {
/* 299 */       this.energy = this.maxEnergy;
/*     */     }
/* 301 */     this.soundLevel = soundModfier;
/* 302 */     if (this.operationLength < 1)
/*     */     {
/* 304 */       this.operationLength = 1;
/*     */     }
/* 306 */     if (this.energyConsume < 1)
/*     */     {
/* 308 */       this.energyConsume = 1;
/*     */     }
/* 310 */     getNetwork().updateTileEntityField((TileEntity)this, "redstoneInverted");
/* 311 */     getNetwork().updateTileEntityField((TileEntity)this, "redstoneSensitive");
/* 312 */     getNetwork().updateTileEntityField((TileEntity)this, "soundLevel");
/* 313 */     getNetwork().updateTileGuiField((TileEntity)this, "operationLength");
/* 314 */     getNetwork().updateTileGuiField((TileEntity)this, "energyConsume");
/* 315 */     getNetwork().updateTileGuiField((TileEntity)this, "maxInput");
/* 316 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean provideEnergy() {
/* 334 */     return super.provideEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addToInventory() {
/* 339 */     if (this.results.isEmpty())
/*     */     {
/* 341 */       return false;
/*     */     }
/* 343 */     int added = 0;
/* 344 */     for (int i = 0; i < this.results.size(); i++) {
/*     */       
/* 346 */       ItemStack item = this.results.get(i);
/* 347 */       if (item != null && item.field_77994_a <= 0) {
/*     */         
/* 349 */         this.results.remove(i--);
/*     */       
/*     */       }
/* 352 */       else if (this.inventory[2] == null) {
/*     */         
/* 354 */         this.inventory[2] = ItemStack.func_77944_b(item);
/* 355 */         this.results.remove(i--);
/* 356 */         added++;
/*     */       }
/*     */       else {
/*     */         
/* 360 */         int left = this.inventory[2].func_77976_d() - (this.inventory[2]).field_77994_a;
/* 361 */         if (left <= 0) {
/*     */           break;
/*     */         }
/*     */         
/* 365 */         if (left >= item.field_77994_a) {
/*     */           
/* 367 */           (this.inventory[2]).field_77994_a += item.field_77994_a;
/* 368 */           added++;
/* 369 */           this.results.remove(i--);
/*     */         }
/*     */         else {
/*     */           
/* 373 */           int itemLeft = item.field_77994_a - left;
/* 374 */           item.field_77994_a = itemLeft;
/* 375 */           (this.inventory[2]).field_77994_a = this.inventory[2].func_77976_d();
/* 376 */           added++;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 381 */     return (added > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void operate(RecipeOutput output) {
/* 386 */     List<ItemStack> result = new ArrayList<>();
/* 387 */     operateOnce(output, result);
/* 388 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 390 */       ItemStack itemStack = this.inventory[i + this.inventory.length - 4];
/* 391 */       if (itemStack != null && itemStack.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */         
/* 393 */         IMachineUpgradeItem item = (IMachineUpgradeItem)itemStack.func_77973_b();
/* 394 */         item.onProcessEnd(itemStack, (IMachine)this, result);
/*     */       } 
/*     */     } 
/* 397 */     if (result.size() > 0) {
/*     */       
/* 399 */       this.results.addAll(result);
/* 400 */       addToInventory();
/*     */     } 
/* 402 */     if (this.inventory[0] != null)
/*     */     {
/* 404 */       if ((this.inventory[0]).field_77994_a <= 0)
/*     */       {
/* 406 */         this.inventory[0] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, List<ItemStack> items) {
/* 413 */     getResultFor(this.inventory[0], true);
/* 414 */     items.addAll(result.items);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract RecipeOutput getResultFor(ItemStack paramItemStack, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   public abstract IMachineRecipeManager getRecipeList();
/*     */   
/*     */   public abstract String func_145825_b();
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 426 */     return (ContainerIC2)new ContainerElectricMachine(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 436 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 441 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 447 */     if (this.audioSource != null && this.audioSource.isRemoved())
/*     */     {
/* 449 */       this.audioSource = null;
/*     */     }
/* 451 */     if (this.audioSource == null && getStartSoundFile() != null) {
/*     */       
/* 453 */       this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getStartSoundFile(), true, false, IC2.audioManager.defaultVolume * this.soundLevel);
/* 454 */       this.audioSource.setLoop(true);
/*     */     } 
/* 456 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 460 */         if (this.audioSource != null)
/*     */         {
/* 462 */           this.audioSource.play();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 469 */         if (this.audioSource == null) {
/*     */           break;
/*     */         }
/*     */         
/* 473 */         this.audioSource.stop();
/* 474 */         if (getInterruptSoundFile() != null)
/*     */         {
/* 476 */           IC2.audioManager.playOnce(this, PositionSpec.Center, getInterruptSoundFile(), false, IC2.audioManager.defaultVolume * this.soundLevel);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 483 */         if (this.audioSource != null)
/*     */         {
/* 485 */           this.audioSource.stop();
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
/* 496 */     super.onNetworkUpdate(field);
/* 497 */     if (field.equals("soundLevel"))
/*     */     {
/* 499 */       if (this.audioSource != null)
/*     */       {
/* 501 */         this.audioSource.setVolume(IC2.audioManager.defaultVolume * this.soundLevel);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 509 */     return new int[] { 2, 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 517 */     if (i == 2)
/*     */     {
/* 519 */       return false;
/*     */     }
/* 521 */     return isValidInput(itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 527 */     if (i == 0)
/*     */     {
/* 529 */       return false;
/*     */     }
/* 531 */     return super.func_102008_b(i, itemstack, j);
/*     */   }
/*     */ 
/*     */   
/*     */   static int applyModifier(int base, int extra, double multiplier) {
/* 536 */     double ret = Math.round((base + extra) * multiplier);
/* 537 */     return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int)ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 543 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount, boolean simulate) {
/* 549 */     if (this.energy < amount)
/*     */     {
/* 551 */       return false;
/*     */     }
/* 553 */     if (!simulate)
/*     */     {
/* 555 */       useEnergy((int)amount);
/*     */     }
/* 557 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSensitive(boolean active) {
/* 563 */     if (this.redstoneSensitive != active)
/*     */     {
/* 565 */       this.redstoneSensitive = active;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWork() {
/* 571 */     if (!this.redstoneSensitive)
/*     */     {
/* 573 */       return true;
/*     */     }
/* 575 */     return isRedstonePowered();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRedstonePowered() {
/* 581 */     if (this.redstoneInverted)
/*     */     {
/* 583 */       return !super.isRedstonePowered();
/*     */     }
/* 585 */     return super.isRedstonePowered();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRedstoneSensitive() {
/* 591 */     return this.redstoneSensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 597 */     return (this.inventory[0] == null || (this.inventory[0].func_77969_a(par1) && ItemStack.func_77970_a(this.inventory[0], par1)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMachine.UpgradeType> getSupportedTypes() {
/* 603 */     List<IMachine.UpgradeType> list = new ArrayList<>();
/* 604 */     list.addAll(Arrays.asList(IMachine.UpgradeType.values()));
/* 605 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 611 */     return this.energyConsume;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityElectricMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */