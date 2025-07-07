/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerElectrolyzer;
/*     */ import ic2.core.block.wiring.IElectrolyzerProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileEntityElectrolyzer
/*     */   extends TileEntityMachine
/*     */   implements IHasGui, IEnergyContainer
/*     */ {
/*  24 */   public static Random randomizer = new Random();
/*  25 */   public static HashMap<ItemRecipe, ItemStack> charge = new HashMap<>();
/*  26 */   public static HashMap<ItemRecipe, ItemStack> discharge = new HashMap<>();
/*  27 */   public static HashMap<ItemRecipe, Integer> energyNeeds = new HashMap<>();
/*  28 */   public short energy = 0;
/*  29 */   public short maxEnergy = 0;
/*  30 */   public IElectrolyzerProvider mfe = null;
/*     */   
/*     */   public int ticker;
/*     */   
/*     */   public AudioSource audioSource;
/*     */   
/*     */   public TileEntityElectrolyzer() {
/*  37 */     super(2);
/*  38 */     this.ticker = randomizer.nextInt(16);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  43 */     addRecipe(Ic2Items.waterCell, Ic2Items.electrolyzedWaterCell, 20000, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, ItemStack output, int required, boolean doCharge, boolean doDischarge) {
/*  48 */     if (!doCharge && !doDischarge) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     energyNeeds.put(getRecipe(input), Integer.valueOf(required));
/*  53 */     if (doCharge)
/*     */     {
/*  55 */       charge.put(getRecipe(input), output);
/*     */     }
/*  57 */     if (doDischarge)
/*     */     {
/*  59 */       discharge.put(getRecipe(output), input);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  66 */     super.func_145839_a(nbttagcompound);
/*  67 */     this.energy = nbttagcompound.func_74765_d("energy");
/*  68 */     this.maxEnergy = nbttagcompound.func_74765_d("total");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  74 */     super.func_145841_b(nbttagcompound);
/*  75 */     nbttagcompound.func_74777_a("energy", this.energy);
/*  76 */     nbttagcompound.func_74777_a("total", this.maxEnergy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  82 */     return "Electrolyzer";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  88 */     super.func_145845_h();
/*  89 */     boolean needsInvUpdate = false;
/*  90 */     boolean turnActive = false;
/*  91 */     if (this.ticker++ % 16 == 0)
/*     */     {
/*  93 */       this.mfe = lookForMFE();
/*     */     }
/*  95 */     if (this.mfe == null) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     if (shouldDrain() && canDrain()) {
/*     */       
/* 101 */       needsInvUpdate = drain();
/* 102 */       turnActive = true;
/*     */     } 
/* 104 */     if (shouldPower() && (canPower() || this.energy > 0)) {
/*     */       
/* 106 */       needsInvUpdate = power();
/* 107 */       turnActive = true;
/*     */     } 
/* 109 */     if (getActive() != turnActive) {
/*     */       
/* 111 */       setActive(turnActive);
/* 112 */       needsInvUpdate = true;
/*     */     } 
/* 114 */     if (needsInvUpdate)
/*     */     {
/* 116 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDrain() {
/* 122 */     return (this.mfe != null && this.mfe.getStoredPower() / this.mfe.getMaxStorage() >= 0.7D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPower() {
/* 127 */     return (this.mfe != null && this.mfe.getStoredPower() / this.mfe.getMaxStorage() <= 0.3D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain() {
/* 132 */     if (this.inventory[0] == null)
/*     */     {
/* 134 */       return false;
/*     */     }
/* 136 */     ItemRecipe item = getRecipe(this.inventory[0]);
/* 137 */     ItemStack result = charge.get(item);
/* 138 */     if (result == null)
/*     */     {
/* 140 */       return false;
/*     */     }
/* 142 */     if (((Integer)energyNeeds.get(item)).intValue() > this.mfe.getStoredPower())
/*     */     {
/* 144 */       return false;
/*     */     }
/* 146 */     if (this.inventory[1] == null)
/*     */     {
/* 148 */       return true;
/*     */     }
/* 150 */     if (!this.inventory[1].func_77969_a(result))
/*     */     {
/* 152 */       return false;
/*     */     }
/* 154 */     return ((this.inventory[1]).field_77994_a + result.field_77994_a <= this.inventory[1].func_77976_d());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPower() {
/* 159 */     if (this.inventory[1] == null)
/*     */     {
/* 161 */       return false;
/*     */     }
/* 163 */     ItemRecipe item = getRecipe(this.inventory[1]);
/* 164 */     ItemStack result = discharge.get(item);
/* 165 */     if (result == null)
/*     */     {
/* 167 */       return false;
/*     */     }
/* 169 */     if (this.inventory[0] == null)
/*     */     {
/* 171 */       return true;
/*     */     }
/* 173 */     if (!this.inventory[0].func_77969_a(result))
/*     */     {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     return ((this.inventory[0]).field_77994_a + result.field_77994_a <= this.inventory[0].func_77976_d());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drain() {
/* 183 */     int req = ((Integer)energyNeeds.get(getRecipe(this.inventory[0]))).intValue();
/* 184 */     if (this.maxEnergy != req) {
/*     */       
/* 186 */       this.maxEnergy = (short)req;
/* 187 */       getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */     } 
/* 189 */     IElectrolyzerProvider mfe = this.mfe;
/* 190 */     mfe.drawPower(processRate());
/* 191 */     this.energy = (short)(this.energy + (short)processRate());
/* 192 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 193 */     if (this.energy >= req) {
/*     */       
/* 195 */       this.energy = (short)(this.energy - req);
/* 196 */       ItemStack result = ((ItemStack)charge.get(getRecipe(this.inventory[0]))).func_77946_l();
/* 197 */       ItemStack itemStack = this.inventory[0];
/* 198 */       itemStack.field_77994_a--;
/* 199 */       if ((this.inventory[0]).field_77994_a <= 0)
/*     */       {
/* 201 */         this.inventory[0] = null;
/*     */       }
/* 203 */       if (this.inventory[1] == null) {
/*     */         
/* 205 */         this.inventory[1] = result;
/*     */       }
/*     */       else {
/*     */         
/* 209 */         (this.inventory[1]).field_77994_a += result.field_77994_a;
/*     */       } 
/* 211 */       return true;
/*     */     } 
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean power() {
/* 218 */     if (this.energy > 0) {
/*     */       
/* 220 */       int out = processRate();
/* 221 */       if (out > this.energy)
/*     */       {
/* 223 */         out = this.energy;
/*     */       }
/* 225 */       this.energy = (short)(this.energy - (short)out);
/* 226 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 227 */       IElectrolyzerProvider mfe = this.mfe;
/* 228 */       mfe.addPower(out);
/* 229 */       return false;
/*     */     } 
/* 231 */     if (this.inventory[1] == null)
/*     */     {
/* 233 */       return false;
/*     */     }
/* 235 */     ItemStack result = ((ItemStack)discharge.get(getRecipe(this.inventory[1]))).func_77946_l();
/* 236 */     if (result == null)
/*     */     {
/* 238 */       return false;
/*     */     }
/* 240 */     int totalEnergy = ((Integer)energyNeeds.get(getRecipe(result))).intValue();
/* 241 */     int base = (int)(totalEnergy / 10.0D * 6.0D);
/* 242 */     int extra = (int)(totalEnergy / 10.0D * this.mfe.getTier());
/* 243 */     this.energy = (short)(this.energy + base + extra);
/* 244 */     this.maxEnergy = (short)(base + extra);
/* 245 */     ItemStack itemStack = this.inventory[1];
/* 246 */     itemStack.field_77994_a--;
/* 247 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 248 */     getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/* 249 */     if ((this.inventory[1]).field_77994_a <= 0)
/*     */     {
/* 251 */       this.inventory[1] = null;
/*     */     }
/* 253 */     if (this.inventory[0] == null) {
/*     */       
/* 255 */       this.inventory[0] = result;
/*     */     }
/*     */     else {
/*     */       
/* 259 */       (this.inventory[0]).field_77994_a += result.field_77994_a;
/*     */     } 
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int processRate() {
/* 266 */     return this.mfe.getProcessRate();
/*     */   }
/*     */ 
/*     */   
/*     */   public IElectrolyzerProvider lookForMFE() {
/* 271 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof IElectrolyzerProvider)
/*     */     {
/* 273 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/*     */     }
/* 275 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof IElectrolyzerProvider)
/*     */     {
/* 277 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */     }
/* 279 */     if (this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof IElectrolyzerProvider)
/*     */     {
/* 281 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 283 */     if (this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof IElectrolyzerProvider)
/*     */     {
/* 285 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 287 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof IElectrolyzerProvider)
/*     */     {
/* 289 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
/*     */     }
/* 291 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof IElectrolyzerProvider)
/*     */     {
/* 293 */       return (IElectrolyzerProvider)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
/*     */     }
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeEnergyScaled(int i) {
/* 300 */     if (this.energy <= 0)
/*     */     {
/* 302 */       return 0;
/*     */     }
/* 304 */     int r = this.energy * i / 20000;
/* 305 */     if (r > i)
/*     */     {
/* 307 */       r = i;
/*     */     }
/* 309 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 315 */     return (ContainerIC2)new ContainerElectrolyzer(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 321 */     return "block.machine.gui.GuiElectrolyzer";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 331 */     if (var1 == 0)
/*     */     {
/* 333 */       return new int[] { 0 };
/*     */     }
/* 335 */     return new int[] { 1 };
/*     */   }
/*     */ 
/*     */   
/*     */   private static ItemRecipe getRecipe(ItemStack par1) {
/* 340 */     return new ItemRecipe(par1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 348 */     if (itemstack == null)
/*     */     {
/* 350 */       return false;
/*     */     }
/* 352 */     if (i == 0)
/*     */     {
/* 354 */       return charge.containsKey(getRecipe(itemstack));
/*     */     }
/* 356 */     if (i == 1)
/*     */     {
/* 358 */       return discharge.containsKey(getRecipe(itemstack));
/*     */     }
/* 360 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ItemRecipe
/*     */   {
/*     */     final Item item;
/*     */     final int meta;
/*     */     
/*     */     public ItemRecipe(ItemStack par1) {
/* 370 */       this(par1.func_77973_b(), par1.func_77960_j());
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemRecipe(Item par1, int par2) {
/* 375 */       this.item = par1;
/* 376 */       this.meta = par2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object arg0) {
/* 382 */       if (!(arg0 instanceof ItemRecipe))
/*     */       {
/* 384 */         return false;
/*     */       }
/* 386 */       ItemRecipe item = (ItemRecipe)arg0;
/* 387 */       if (item.item == this.item && item.meta == this.meta)
/*     */       {
/* 389 */         return true;
/*     */       }
/* 391 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 397 */       return this.item.hashCode() + this.meta;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 403 */       String a = "Name: " + (new ItemStack(this.item, 1, this.meta)).func_77977_a();
/* 404 */       String b = "Data: " + Item.func_150891_b(this.item) + ":" + this.meta;
/* 405 */       return b + " " + a;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack toItem() {
/* 410 */       return new ItemStack(this.item, 1, this.meta);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 418 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 424 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 430 */     return getSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 436 */     return getSpeed();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeed() {
/* 441 */     if (this.field_145850_b.func_82737_E() % 80L == 0L)
/*     */     {
/* 443 */       this.mfe = lookForMFE();
/*     */     }
/* 445 */     return (this.mfe != null) ? this.mfe.getProcessRate() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 451 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 457 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 459 */       IC2.audioManager.removeSources(this);
/* 460 */       this.audioSource = null;
/*     */     } 
/* 462 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 468 */     if (field.equals("active")) {
/*     */       
/* 470 */       if (this.audioSource == null)
/*     */       {
/* 472 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/ElectrolyzerLoop.ogg", true, true, IC2.audioManager.defaultVolume);
/*     */       }
/* 474 */       if (this.audioSource != null)
/*     */       {
/* 476 */         if (getActive()) {
/*     */           
/* 478 */           this.audioSource.play();
/*     */         }
/*     */         else {
/*     */           
/* 482 */           this.audioSource.pause();
/*     */         } 
/*     */       }
/*     */     } 
/* 486 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */