/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.inventory.SlotFurnace;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityVacuumCanner
/*     */   extends TileEntityAdvancedMachine
/*     */ {
/*  29 */   public int fuelQuality = 0;
/*  30 */   public int step = 0;
/*  31 */   public int mode = 0;
/*     */ 
/*     */   
/*     */   public TileEntityVacuumCanner() {
/*  35 */     super(6, 15, 3000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  41 */     super.func_145839_a(nbt);
/*  42 */     this.fuelQuality = nbt.func_74762_e("Fuel");
/*  43 */     this.step = nbt.func_74762_e("Step");
/*  44 */     this.mode = nbt.func_74762_e("Mode");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  50 */     super.func_145841_b(nbt);
/*  51 */     nbt.func_74768_a("Fuel", this.fuelQuality);
/*  52 */     nbt.func_74768_a("Step", this.step);
/*  53 */     nbt.func_74768_a("Mode", this.mode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/*  59 */     return "block.machine.gui.GuiVacuum";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  65 */     return new int[] { 1, 2, 3, 4, 5 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  71 */     if (itemstack == null)
/*     */     {
/*  73 */       return false;
/*     */     }
/*  75 */     if (i == 1) {
/*     */       
/*  77 */       if (itemstack.func_77973_b() == Ic2Items.fuelCan.func_77973_b() || itemstack.func_77973_b() == Ic2Items.jetpack.func_77973_b())
/*     */       {
/*  79 */         return true;
/*     */       }
/*  81 */       if (itemstack.func_77973_b() == Ic2Items.constructionFoamSprayer.func_77973_b() || itemstack.func_77973_b() == Ic2Items.cfPack.func_77973_b())
/*     */       {
/*  83 */         return true;
/*     */       }
/*  85 */       return (itemstack.func_77973_b() == Ic2Items.tinCan.func_77973_b());
/*     */     } 
/*  87 */     if (i == 2 || i == 3 || i == 4) {
/*     */       
/*  89 */       if (this.mode == 1)
/*     */       {
/*  91 */         return (getFoodValue(itemstack) > 0);
/*     */       }
/*  93 */       if (this.mode == 2)
/*     */       {
/*  95 */         return validFuel(itemstack);
/*     */       }
/*  97 */       if (this.mode == 3)
/*     */       {
/*  99 */         return (getPelletValue(itemstack) > 0);
/*     */       }
/* 101 */       return false;
/*     */     } 
/* 103 */     if (i == 5)
/*     */     {
/* 105 */       return false;
/*     */     }
/* 107 */     return super.func_102007_a(i, itemstack, j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 113 */     if (i == 5)
/*     */     {
/* 115 */       return super.func_102008_b(i, itemstack, j);
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputSlot(int slotID) {
/* 124 */     return (slotID >= 1 && slotID < 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputSlot(int slotID) {
/* 130 */     return (slotID == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot[] getInvSlots(InventoryPlayer par2) {
/* 142 */     Slot[] slot = new Slot[6];
/* 143 */     slot[0] = (Slot)new SlotDischarge(this, 0, 30, 45);
/* 144 */     slot[1] = new Slot(this, 1, 69, 53);
/* 145 */     slot[2] = new Slot(this, 2, 51, 17);
/* 146 */     slot[3] = new Slot(this, 3, 69, 17);
/* 147 */     slot[4] = new Slot(this, 4, 87, 17);
/* 148 */     slot[5] = (Slot)new SlotFurnace(par2.field_70458_d, this, 5, 119, 35);
/* 149 */     return slot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/* 155 */     return new int[] { 5 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 161 */     return "Vacuum Canner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClientOperationLenght() {
/* 167 */     switch (this.mode) {
/*     */       
/*     */       case 1:
/* 170 */         return 800 * getFoodValue(this.inventory[2]);
/*     */       case 2:
/* 172 */         return 12000;
/*     */       case 3:
/* 174 */         return 1200;
/*     */     } 
/* 176 */     return 1500;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClientProgress() {
/* 183 */     return this.step * 800 + super.getClientProgress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOperationLenght() {
/* 189 */     switch (this.mode) {
/*     */       
/*     */       case 1:
/* 192 */         return 800 * getFoodValue(this.inventory[2]);
/*     */       case 2:
/* 194 */         return 800;
/*     */       case 3:
/* 196 */         return 1200;
/*     */     } 
/* 198 */     return 3000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 205 */     if (this.field_145850_b.func_82737_E() % 10L == 0L)
/*     */     {
/* 207 */       sortInventory();
/*     */     }
/* 209 */     int oldMode = this.mode;
/* 210 */     this.mode = getMode();
/* 211 */     getNetwork().updateTileGuiField((TileEntity)this, "mode");
/* 212 */     if (oldMode == 2 && this.mode != 2) {
/*     */       
/* 214 */       this.fuelQuality = 0;
/* 215 */       this.step = 0;
/* 216 */       getNetwork().updateTileGuiField((TileEntity)this, "step");
/*     */     } 
/* 218 */     super.func_145845_h();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortInventory() {
/* 225 */     for (int i = 2; i < 4; i++) {
/*     */       
/* 227 */       if (this.inventory[i] == null && this.inventory[i + 1] != null) {
/*     */         
/* 229 */         this.inventory[i] = this.inventory[i + 1].func_77946_l();
/* 230 */         this.inventory[i + 1] = null;
/*     */       
/*     */       }
/* 233 */       else if (this.inventory[i] != null && this.inventory[i + 1] != null && this.inventory[i].func_77969_a(this.inventory[i + 1]) && ItemStack.func_77970_a(this.inventory[i], this.inventory[i + 1])) {
/*     */         
/* 235 */         int left = this.inventory[i].func_77976_d() - (this.inventory[i]).field_77994_a;
/* 236 */         if (left > 0)
/*     */         {
/*     */ 
/*     */           
/* 240 */           if (left >= (this.inventory[i + 1]).field_77994_a) {
/*     */             
/* 242 */             (this.inventory[i]).field_77994_a += (this.inventory[i + 1].func_77946_l()).field_77994_a;
/* 243 */             this.inventory[i + 1] = null;
/*     */           }
/*     */           else {
/*     */             
/* 247 */             (this.inventory[i]).field_77994_a = this.inventory[i].func_77976_d();
/* 248 */             (this.inventory[i + 1]).field_77994_a -= left;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void operate() {
/* 257 */     List<ItemStack> results = new ArrayList<>();
/* 258 */     if (this.mode == 1) {
/*     */       
/* 260 */       int food = getFoodValue(this.inventory[2]);
/* 261 */       if (food > 0) {
/*     */         
/* 263 */         results.add(new ItemStack(Ic2Items.filledTinCan.func_77973_b(), food, getFoodMeta(this.inventory[2])));
/* 264 */         (this.inventory[1]).field_77994_a -= food;
/* 265 */         (this.inventory[2]).field_77994_a--;
/* 266 */         if ((this.inventory[1]).field_77994_a <= 0)
/*     */         {
/* 268 */           this.inventory[1] = null;
/*     */         }
/* 270 */         if ((this.inventory[2]).field_77994_a <= 0)
/*     */         {
/* 272 */           this.inventory[2] = null;
/*     */         }
/*     */       } 
/*     */     } 
/* 276 */     if (this.mode == 2) {
/*     */       
/* 278 */       if (this.step < 14) {
/*     */         
/* 280 */         this.step++;
/* 281 */         getNetwork().updateTileGuiField((TileEntity)this, "step");
/* 282 */         handleStep();
/*     */         return;
/*     */       } 
/* 285 */       handleStep();
/* 286 */       this.step = 0;
/* 287 */       getNetwork().updateTileGuiField((TileEntity)this, "step");
/* 288 */       (this.inventory[1]).field_77994_a--;
/* 289 */       if (this.inventory[1].func_77973_b() instanceof ic2.core.item.ItemFuelCanEmpty) {
/*     */         
/* 291 */         ItemStack result = new ItemStack(Ic2Items.filledFuelCan.func_77973_b());
/* 292 */         NBTTagCompound data = StackUtil.getOrCreateNbtData(result);
/* 293 */         data.func_74768_a("value", this.fuelQuality);
/* 294 */         data.func_74768_a("maxValue", this.fuelQuality);
/* 295 */         results.add(result);
/* 296 */         if ((this.inventory[1]).field_77994_a <= 0)
/*     */         {
/* 298 */           this.inventory[1] = null;
/*     */         }
/*     */       }
/* 301 */       else if (this.inventory[1].func_77973_b() == Ic2Items.jetpack.func_77973_b()) {
/*     */         
/* 303 */         int damage = this.inventory[1].func_77960_j();
/* 304 */         damage -= this.fuelQuality;
/* 305 */         if (damage < 1)
/*     */         {
/* 307 */           damage = 1;
/*     */         }
/* 309 */         this.inventory[1] = null;
/* 310 */         results.add(new ItemStack(Ic2Items.jetpack.func_77973_b(), 1, damage));
/*     */       } 
/*     */     } 
/* 313 */     if (this.mode == 3) {
/*     */       
/* 315 */       ItemStack itemStack6 = this.inventory[2];
/* 316 */       itemStack6.field_77994_a--;
/* 317 */       this.inventory[1].func_77964_b(this.inventory[1].func_77960_j() - 2);
/* 318 */       if (this.inventory[1].func_77960_j() > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 322 */       results.add(this.inventory[1].func_77946_l());
/* 323 */       this.inventory[1] = null;
/*     */     } 
/*     */     
/* 326 */     if (results.size() > 0) {
/*     */       
/* 328 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 330 */         ItemStack itemStack = this.inventory[i + this.inventory.length - 2];
/* 331 */         if (itemStack != null && itemStack.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */           
/* 333 */           IMachineUpgradeItem item = (IMachineUpgradeItem)itemStack.func_77973_b();
/* 334 */           item.onProcessEnd(itemStack, (IMachine)this, results);
/*     */         } 
/*     */       } 
/* 337 */       if (results.size() > 0) {
/*     */         
/* 339 */         this.results.addAll(results);
/* 340 */         addToInventory();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutput() {
/*     */     int food;
/* 348 */     if (!this.results.isEmpty())
/*     */     {
/* 350 */       return null;
/*     */     }
/* 352 */     switch (this.mode) {
/*     */ 
/*     */       
/*     */       case 1:
/* 356 */         food = getFoodValue(this.inventory[2]);
/* 357 */         if (food > 0 && food <= (this.inventory[1]).field_77994_a)
/*     */         {
/* 359 */           return new RecipeOutput(null, new ItemStack[] { this.inventory[2].func_77946_l() });
/*     */         }
/*     */ 
/*     */       
/*     */       case 2:
/* 364 */         if (getFuelValue(this.inventory[2]) > 0)
/*     */         {
/* 366 */           return new RecipeOutput(null, new ItemStack[] { this.inventory[2].func_77946_l() });
/*     */         }
/*     */ 
/*     */       
/*     */       case 3:
/* 371 */         if (this.inventory[1].func_77960_j() >= 2 && getPelletValue(this.inventory[2]) > 0)
/*     */         {
/* 373 */           return new RecipeOutput(null, new ItemStack[] { this.inventory[2].func_77946_l() });
/*     */         }
/*     */         break;
/*     */     } 
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 383 */     if (this.inventory[1] == null)
/*     */     {
/* 385 */       return 0;
/*     */     }
/* 387 */     if (this.inventory[1].func_77973_b() == Ic2Items.tinCan.func_77973_b())
/*     */     {
/* 389 */       return 1;
/*     */     }
/* 391 */     if (this.inventory[1].func_77973_b() instanceof ic2.core.item.ItemFuelCanEmpty || this.inventory[1].func_77973_b() == Ic2Items.jetpack.func_77973_b())
/*     */     {
/* 393 */       return 2;
/*     */     }
/* 395 */     if (this.inventory[1].func_77973_b() == Ic2Items.cfPack.func_77973_b())
/*     */     {
/* 397 */       return 3;
/*     */     }
/* 399 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStep() {
/* 404 */     this.fuelQuality += getFuelValue(this.inventory[2]);
/* 405 */     (this.inventory[2]).field_77994_a--;
/* 406 */     if ((this.inventory[2]).field_77994_a <= 0)
/*     */     {
/* 408 */       this.inventory[2] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFoodValue(ItemStack item) {
/* 414 */     if (item == null)
/*     */     {
/* 416 */       return 0;
/*     */     }
/* 418 */     if (item.func_77973_b() instanceof ItemFood) {
/*     */       
/* 420 */       ItemFood food = (ItemFood)item.func_77973_b();
/* 421 */       return (int)Math.ceil(food.func_150905_g(item) / 2.0D);
/*     */     } 
/* 423 */     if (item.func_77973_b() == Items.field_151105_aU || item.func_77973_b() == Item.func_150898_a(Blocks.field_150414_aQ))
/*     */     {
/* 425 */       return 6;
/*     */     }
/* 427 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFuelValue(ItemStack item) {
/* 432 */     if (item == null)
/*     */     {
/* 434 */       return 0;
/*     */     }
/* 436 */     TileEntityElectrolyzer.ItemRecipe recipe = new TileEntityElectrolyzer.ItemRecipe(item);
/* 437 */     if (TileEntityCanner.fuelValues.containsKey(recipe) && TileEntityCanner.fuelMultiplyers.containsKey(recipe))
/*     */     {
/* 439 */       throw new RuntimeException("Invalid case. This Item: " + item.func_82833_r() + " has a Mutiplyer and Fuel value. Which is not allowed. Crash!");
/*     */     }
/*     */     
/* 442 */     if (TileEntityCanner.fuelValues.containsKey(recipe))
/*     */     {
/* 444 */       return ((Integer)TileEntityCanner.fuelValues.get(recipe)).intValue();
/*     */     }
/* 446 */     if (TileEntityCanner.fuelMultiplyers.containsKey(recipe))
/*     */     {
/* 448 */       return (int)(this.fuelQuality * ((Float)TileEntityCanner.fuelMultiplyers.get(recipe)).floatValue());
/*     */     }
/* 450 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validFuel(ItemStack item) {
/* 455 */     TileEntityElectrolyzer.ItemRecipe recipe = new TileEntityElectrolyzer.ItemRecipe(item);
/* 456 */     if (TileEntityCanner.fuelValues.containsKey(recipe) && TileEntityCanner.fuelMultiplyers.containsKey(recipe))
/*     */     {
/* 458 */       throw new RuntimeException("Invalid case. This Item: " + item.func_82833_r() + " has a Mutiplyer and Fuel value. Which is not allowed. Crash!");
/*     */     }
/*     */     
/* 461 */     if (TileEntityCanner.fuelValues.containsKey(recipe))
/*     */     {
/* 463 */       return true;
/*     */     }
/* 465 */     if (TileEntityCanner.fuelMultiplyers.containsKey(recipe))
/*     */     {
/* 467 */       return true;
/*     */     }
/* 469 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPelletValue(ItemStack item) {
/* 474 */     if (item == null)
/*     */     {
/* 476 */       return 0;
/*     */     }
/* 478 */     if (item.func_77973_b() != Ic2Items.constructionFoamPellet.func_77973_b())
/*     */     {
/* 480 */       return 0;
/*     */     }
/* 482 */     return item.field_77994_a;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFoodMeta(ItemStack item) {
/* 487 */     if (item == null)
/*     */     {
/* 489 */       return 0;
/*     */     }
/* 491 */     TileEntityElectrolyzer.ItemRecipe ccip = new TileEntityElectrolyzer.ItemRecipe(item.func_77973_b(), item.func_77960_j());
/* 492 */     return TileEntityCanner.specialFood.containsKey(ccip) ? ((Integer)TileEntityCanner.specialFood.get(ccip)).intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack itemstack) {
/* 498 */     if (itemstack.func_77973_b() == Ic2Items.fuelCan.func_77973_b() || itemstack.func_77973_b() == Ic2Items.jetpack.func_77973_b())
/*     */     {
/* 500 */       return true;
/*     */     }
/* 502 */     if (itemstack.func_77973_b() == Ic2Items.constructionFoamSprayer.func_77973_b() || itemstack.func_77973_b() == Ic2Items.cfPack.func_77973_b())
/*     */     {
/* 504 */       return true;
/*     */     }
/* 506 */     if (this.mode == 1)
/*     */     {
/* 508 */       return (getFoodValue(itemstack) > 0);
/*     */     }
/* 510 */     if (this.mode == 2)
/*     */     {
/* 512 */       return validFuel(itemstack);
/*     */     }
/* 514 */     if (this.mode == 3)
/*     */     {
/* 516 */       return (getPelletValue(itemstack) > 0);
/*     */     }
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 524 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityVacuumCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */