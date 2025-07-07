/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerChargedElectrolyzer;
/*     */ import ic2.core.block.wiring.IElectrolyzerProvider;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityCharged
/*     */   extends TileEntityMachine
/*     */   implements IHasGui, IEnergyContainer {
/*  20 */   IElectrolyzerProvider[] provider = new IElectrolyzerProvider[6];
/*     */   
/*     */   int updateTicker;
/*  23 */   public int energy = 0;
/*  24 */   public int maxEnergy = 0;
/*     */   
/*     */   public AudioSource audioSource;
/*     */   
/*     */   public TileEntityCharged() {
/*  29 */     super(2);
/*  30 */     this.updateTicker = TileEntityElectrolyzer.randomizer.nextInt(16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  36 */     super.func_145839_a(nbt);
/*  37 */     this.energy = nbt.func_74762_e("Energy");
/*  38 */     this.maxEnergy = nbt.func_74762_e("MaxEnergy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  44 */     super.func_145841_b(nbt);
/*  45 */     nbt.func_74768_a("Energy", this.energy);
/*  46 */     nbt.func_74768_a("MaxEnergy", this.maxEnergy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  52 */     return "Charged Electrolyzer";
/*     */   }
/*     */ 
/*     */   
/*     */   public IElectrolyzerProvider[] getProviders() {
/*  57 */     IElectrolyzerProvider[] update = new IElectrolyzerProvider[6];
/*  58 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/*  60 */       TileEntity tile = this.field_145850_b.func_147438_o(this.field_145851_c + dir.offsetX, this.field_145848_d + dir.offsetY, this.field_145849_e + dir.offsetZ);
/*  61 */       if (tile instanceof IElectrolyzerProvider)
/*     */       {
/*  63 */         update[dir.ordinal()] = (IElectrolyzerProvider)tile;
/*     */       }
/*     */     } 
/*  66 */     return update;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/*  72 */     if (var1 == 0)
/*     */     {
/*  74 */       return new int[] { 0 };
/*     */     }
/*  76 */     return new int[] { 1 };
/*     */   }
/*     */ 
/*     */   
/*     */   private static TileEntityElectrolyzer.ItemRecipe getRecipe(ItemStack par1) {
/*  81 */     return new TileEntityElectrolyzer.ItemRecipe(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeEnergyScaled(int i) {
/*  86 */     if (this.energy <= 0)
/*     */     {
/*  88 */       return 0;
/*     */     }
/*  90 */     int r = this.energy * i / 20000;
/*  91 */     if (r > i)
/*     */     {
/*  93 */       r = i;
/*     */     }
/*  95 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 101 */     if (itemstack == null)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     if (i == 0)
/*     */     {
/* 107 */       return TileEntityElectrolyzer.charge.containsKey(getRecipe(itemstack));
/*     */     }
/* 109 */     if (i == 1)
/*     */     {
/* 111 */       return TileEntityElectrolyzer.discharge.containsKey(getRecipe(itemstack));
/*     */     }
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 119 */     super.func_145845_h();
/* 120 */     boolean needsInvUpdate = false;
/* 121 */     boolean turnActive = false;
/* 122 */     if (this.updateTicker++ % 16 == 0)
/*     */     {
/* 124 */       this.provider = getProviders();
/*     */     }
/*     */     
/* 127 */     if (shouldDrain() && canDrain()) {
/*     */       
/* 129 */       needsInvUpdate = drain();
/* 130 */       turnActive = true;
/*     */     } 
/* 132 */     if (shouldPower() && (canPower() || this.energy > 0)) {
/*     */       
/* 134 */       needsInvUpdate = power();
/* 135 */       turnActive = true;
/*     */     } 
/* 137 */     if (getActive() != turnActive) {
/*     */       
/* 139 */       setActive(turnActive);
/* 140 */       needsInvUpdate = true;
/*     */     } 
/* 142 */     if (needsInvUpdate)
/*     */     {
/* 144 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean power() {
/* 150 */     if (this.energy > 0) {
/*     */       
/* 152 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */         
/* 154 */         IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 155 */         if (mfe != null && needsEnergy(mfe)) {
/*     */           
/* 157 */           int speed = Math.min(this.energy, mfe.getProcessRate());
/* 158 */           mfe.addPower(speed);
/* 159 */           this.energy -= speed;
/* 160 */           getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */         } 
/* 162 */         if (this.energy <= 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 167 */       if (this.energy > 0)
/*     */       {
/* 169 */         return false;
/*     */       }
/*     */     } 
/* 172 */     if (this.inventory[1] == null)
/*     */     {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     ItemStack result = ((ItemStack)TileEntityElectrolyzer.discharge.get(getRecipe(this.inventory[1]))).func_77946_l();
/* 178 */     if (result == null)
/*     */     {
/* 180 */       return false;
/*     */     }
/* 182 */     int totalEnergy = ((Integer)TileEntityElectrolyzer.energyNeeds.get(getRecipe(result))).intValue();
/* 183 */     int base = (int)(totalEnergy / 10.0D * 6.0D);
/* 184 */     int extra = (int)(totalEnergy / 10.0D * getHighestMFE());
/* 185 */     this.energy += base + extra;
/* 186 */     this.maxEnergy = (short)(base + extra);
/* 187 */     ItemStack itemStack = this.inventory[1];
/* 188 */     itemStack.field_77994_a--;
/* 189 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 190 */     getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */     
/* 192 */     if ((this.inventory[1]).field_77994_a <= 0)
/*     */     {
/* 194 */       this.inventory[1] = null;
/*     */     }
/* 196 */     if (this.inventory[0] == null) {
/*     */       
/* 198 */       this.inventory[0] = result;
/*     */     }
/*     */     else {
/*     */       
/* 202 */       (this.inventory[0]).field_77994_a += result.field_77994_a;
/*     */     } 
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getHighestMFE() {
/* 209 */     int tier = 0;
/* 210 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 212 */       IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 213 */       if (mfe != null)
/*     */       {
/* 215 */         tier = Math.max(tier, mfe.getTier());
/*     */       }
/*     */     } 
/* 218 */     return tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPower() {
/* 223 */     if (this.inventory[1] == null)
/*     */     {
/* 225 */       return false;
/*     */     }
/* 227 */     TileEntityElectrolyzer.ItemRecipe item = getRecipe(this.inventory[1]);
/* 228 */     ItemStack result = TileEntityElectrolyzer.discharge.get(item);
/* 229 */     if (result == null)
/*     */     {
/* 231 */       return false;
/*     */     }
/* 233 */     if (this.inventory[0] == null)
/*     */     {
/* 235 */       return true;
/*     */     }
/* 237 */     if (!this.inventory[0].func_77969_a(result))
/*     */     {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     return ((this.inventory[0]).field_77994_a + result.field_77994_a <= this.inventory[0].func_77976_d());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPower() {
/* 247 */     int needsPower = 0;
/* 248 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 250 */       IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 251 */       if (mfe != null && needsEnergy(mfe))
/*     */       {
/* 253 */         needsPower++;
/*     */       }
/*     */     } 
/* 256 */     return (needsPower > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean needsEnergy(IElectrolyzerProvider par1) {
/* 261 */     return (par1.getStoredPower() / par1.getMaxStorage() <= 0.3D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drain() {
/* 266 */     int req = ((Integer)TileEntityElectrolyzer.energyNeeds.get(getRecipe(this.inventory[0]))).intValue();
/* 267 */     if (this.maxEnergy != req) {
/*     */       
/* 269 */       this.maxEnergy = (short)req;
/* 270 */       getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */     } 
/* 272 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 274 */       IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 275 */       if (mfe != null && hasPowerAviable(mfe)) {
/*     */         
/* 277 */         int needed = Math.min(mfe.getProcessRate(), this.maxEnergy - this.energy);
/* 278 */         mfe.drawPower(needed);
/* 279 */         this.energy += needed;
/* 280 */         getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */       } 
/*     */     } 
/* 283 */     if (this.energy >= req) {
/*     */       
/* 285 */       this.energy -= req;
/* 286 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 287 */       ItemStack result = ((ItemStack)TileEntityElectrolyzer.charge.get(getRecipe(this.inventory[0]))).func_77946_l();
/* 288 */       ItemStack itemStack = this.inventory[0];
/* 289 */       itemStack.field_77994_a--;
/* 290 */       if ((this.inventory[0]).field_77994_a <= 0)
/*     */       {
/* 292 */         this.inventory[0] = null;
/*     */       }
/* 294 */       if (this.inventory[1] == null) {
/*     */         
/* 296 */         this.inventory[1] = result;
/*     */       }
/*     */       else {
/*     */         
/* 300 */         (this.inventory[1]).field_77994_a += result.field_77994_a;
/*     */       } 
/* 302 */       return true;
/*     */     } 
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDrain() {
/* 309 */     int couldProvide = 0;
/* 310 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 312 */       IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 313 */       if (mfe != null && hasPowerAviable(mfe))
/*     */       {
/* 315 */         couldProvide++;
/*     */       }
/*     */     } 
/* 318 */     return (couldProvide > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasPowerAviable(IElectrolyzerProvider mfe) {
/* 323 */     return (mfe.getStoredPower() / mfe.getMaxStorage() >= 0.7D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain() {
/* 328 */     if (this.inventory[0] == null)
/*     */     {
/* 330 */       return false;
/*     */     }
/* 332 */     TileEntityElectrolyzer.ItemRecipe item = getRecipe(this.inventory[0]);
/* 333 */     ItemStack result = TileEntityElectrolyzer.charge.get(item);
/* 334 */     if (result == null)
/*     */     {
/* 336 */       return false;
/*     */     }
/* 338 */     if (((Integer)TileEntityElectrolyzer.energyNeeds.get(item)).intValue() > getAviablePower())
/*     */     {
/* 340 */       return false;
/*     */     }
/* 342 */     if (this.inventory[1] == null)
/*     */     {
/* 344 */       return true;
/*     */     }
/* 346 */     if (!this.inventory[1].func_77969_a(result))
/*     */     {
/* 348 */       return false;
/*     */     }
/* 350 */     return ((this.inventory[1]).field_77994_a + result.field_77994_a <= this.inventory[1].func_77976_d());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAviablePower() {
/* 355 */     int power = 0;
/* 356 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 358 */       IElectrolyzerProvider mfe = this.provider[dir.ordinal()];
/* 359 */       if (mfe != null)
/*     */       {
/* 361 */         power += mfe.getStoredPower();
/*     */       }
/*     */     } 
/* 364 */     return power;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 370 */     return (ContainerIC2)new ContainerChargedElectrolyzer(p0, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 376 */     return "block.machine.gui.GuiCharged";
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
/* 388 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 394 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 400 */     return getSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 406 */     return getSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 412 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeed() {
/* 417 */     int speed = 0;
/* 418 */     if (this.field_145850_b.func_82737_E() % 80L == 0L)
/*     */     {
/* 420 */       this.provider = getProviders();
/*     */     }
/* 422 */     for (int i = 0; i < this.provider.length; i++) {
/*     */       
/* 424 */       IElectrolyzerProvider prov = this.provider[i];
/* 425 */       if (prov != null)
/*     */       {
/* 427 */         speed += prov.getProcessRate();
/*     */       }
/*     */     } 
/* 430 */     return speed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 436 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 438 */       IC2.audioManager.removeSources(this);
/* 439 */       this.audioSource = null;
/*     */     } 
/* 441 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 447 */     if (field.equals("active")) {
/*     */       
/* 449 */       if (this.audioSource == null)
/*     */       {
/* 451 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/ElectrolyzerLoop.ogg", true, true, IC2.audioManager.defaultVolume);
/*     */       }
/* 453 */       if (this.audioSource != null)
/*     */       {
/* 455 */         if (getActive()) {
/*     */           
/* 457 */           this.audioSource.play();
/*     */         }
/*     */         else {
/*     */           
/* 461 */           this.audioSource.pause();
/*     */         } 
/*     */       }
/*     */     } 
/* 465 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCharged.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */