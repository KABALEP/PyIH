/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.block.machine.container.ContainerCanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityCanner
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui, ISidedInventory
/*     */ {
/*  30 */   public static Map<TileEntityElectrolyzer.ItemRecipe, Integer> specialFood = new HashMap<>();
/*  31 */   public static Map<TileEntityElectrolyzer.ItemRecipe, Integer> fuelValues = new HashMap<>();
/*  32 */   public static Map<TileEntityElectrolyzer.ItemRecipe, Float> fuelMultiplyers = new HashMap<>();
/*  33 */   public short progress = 0;
/*  34 */   public int energyconsume = 1;
/*  35 */   public int operationLength = 600;
/*  36 */   private int fuelQuality = 0;
/*  37 */   public AudioSource audioSource = null;
/*     */ 
/*     */   
/*     */   public TileEntityCanner() {
/*  41 */     super(4, 1, 631, 32);
/*  42 */     addGuiFields(new String[] { "progress" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  48 */     super.func_145839_a(nbttagcompound);
/*     */     
/*     */     try {
/*  51 */       this.fuelQuality = nbttagcompound.func_74762_e("fuelQuality");
/*     */     }
/*  53 */     catch (Throwable e) {
/*     */       
/*  55 */       this.fuelQuality = nbttagcompound.func_74765_d("fuelQuality");
/*     */     } 
/*  57 */     this.progress = nbttagcompound.func_74765_d("progress");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  63 */     super.func_145841_b(nbttagcompound);
/*  64 */     nbttagcompound.func_74768_a("fuelQuality", this.fuelQuality);
/*  65 */     nbttagcompound.func_74768_a("progress", this.progress);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeProgressScaled(int i) {
/*  70 */     int l = this.operationLength;
/*  71 */     if (getMode() == 1 && this.inventory[0] != null) {
/*     */       
/*  73 */       int food = getFoodValue(this.inventory[0]);
/*  74 */       if (food > 0)
/*     */       {
/*  76 */         l = 50 * food;
/*     */       }
/*     */     } 
/*  79 */     if (getMode() == 3)
/*     */     {
/*  81 */       l = 50;
/*     */     }
/*  83 */     return this.progress * i / l;
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  88 */     if (this.energy <= 0)
/*     */     {
/*  90 */       return 0;
/*     */     }
/*  92 */     int r = this.energy * i / this.operationLength * this.energyconsume;
/*  93 */     if (r > i)
/*     */     {
/*  95 */       return i;
/*     */     }
/*  97 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 102 */     super.func_145845_h();
/* 103 */     boolean needsInvUpdate = false;
/* 104 */     boolean canOperate = canOperate();
/* 105 */     short prog = this.progress;
/* 106 */     if (this.energy <= this.energyconsume * this.operationLength && canOperate)
/*     */     {
/* 108 */       needsInvUpdate = provideEnergy();
/*     */     }
/* 110 */     boolean newActive = getActive();
/* 111 */     if (canOperate && ((getMode() == 1 && this.progress >= getFoodValue(this.inventory[0]) * 50) || (getMode() == 2 && this.progress > 0 && this.progress % 100 == 0) || (getMode() == 3 && this.progress >= 50))) {
/*     */       
/* 113 */       if (getMode() == 1 || getMode() == 3 || this.progress >= 600) {
/*     */         
/* 115 */         operate(false);
/* 116 */         this.fuelQuality = 0;
/* 117 */         this.progress = 0;
/* 118 */         newActive = false;
/*     */       }
/*     */       else {
/*     */         
/* 122 */         operate(true);
/*     */       } 
/* 124 */       needsInvUpdate = true;
/*     */     } 
/* 126 */     if (!newActive || this.progress == 0) {
/*     */       
/* 128 */       if (canOperate)
/*     */       {
/* 130 */         if (this.energy >= this.energyconsume)
/*     */         {
/* 132 */           newActive = true;
/*     */         }
/*     */       }
/* 135 */       else if (getMode() != 2)
/*     */       {
/* 137 */         this.fuelQuality = 0;
/* 138 */         this.progress = 0;
/*     */       }
/*     */     
/* 141 */     } else if (!canOperate || this.energy < this.energyconsume) {
/*     */       
/* 143 */       if (!canOperate && getMode() != 2) {
/*     */         
/* 145 */         this.fuelQuality = 0;
/* 146 */         this.progress = 0;
/*     */       } 
/* 148 */       newActive = false;
/*     */     } 
/* 150 */     if (newActive) {
/*     */       
/* 152 */       this.progress = (short)(this.progress + 1);
/* 153 */       useEnergy(this.energyconsume);
/*     */     } 
/* 155 */     if (needsInvUpdate)
/*     */     {
/* 157 */       func_70296_d();
/*     */     }
/* 159 */     if (newActive != getActive())
/*     */     {
/* 161 */       setActive(newActive);
/*     */     }
/* 163 */     if (this.progress != prog)
/*     */     {
/* 165 */       getNetwork().updateTileGuiField((TileEntity)this, "progress"); }  } public void operate(boolean incremental) { int food; int fuel; ItemStack itemStack6; int meta;
/*     */     ItemStack itemStack4;
/*     */     ItemStack itemStack;
/*     */     int damage;
/*     */     ItemStack itemStack2;
/*     */     ItemStack itemStack3;
/* 171 */     switch (getMode()) {
/*     */ 
/*     */       
/*     */       case 1:
/* 175 */         food = getFoodValue(this.inventory[0]);
/* 176 */         meta = getFoodMeta(this.inventory[0]);
/* 177 */         itemStack = this.inventory[0];
/* 178 */         itemStack.field_77994_a--;
/* 179 */         if (this.inventory[0].func_77973_b() == Items.field_151009_A && (this.inventory[0]).field_77994_a <= 0)
/*     */         {
/* 181 */           this.inventory[0] = new ItemStack(Items.field_151054_z);
/*     */         }
/* 183 */         if ((this.inventory[0]).field_77994_a <= 0)
/*     */         {
/* 185 */           this.inventory[0] = null;
/*     */         }
/* 187 */         itemStack2 = this.inventory[3];
/* 188 */         itemStack2.field_77994_a -= food;
/* 189 */         if ((this.inventory[3]).field_77994_a <= 0)
/*     */         {
/* 191 */           this.inventory[3] = null;
/*     */         }
/* 193 */         if (this.inventory[2] == null) {
/*     */           
/* 195 */           this.inventory[2] = new ItemStack(Ic2Items.filledTinCan.func_77973_b(), food, meta);
/*     */           break;
/*     */         } 
/* 198 */         itemStack3 = this.inventory[2];
/* 199 */         itemStack3.field_77994_a += food;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 204 */         fuel = getFuelValue(this.inventory[0]);
/* 205 */         itemStack4 = this.inventory[0];
/* 206 */         itemStack4.field_77994_a--;
/* 207 */         if ((this.inventory[0]).field_77994_a <= 0)
/*     */         {
/* 209 */           this.inventory[0] = null;
/*     */         }
/* 211 */         this.fuelQuality += fuel;
/* 212 */         if (incremental) {
/*     */           break;
/*     */         }
/*     */         
/* 216 */         if (this.inventory[3].func_77973_b() instanceof ic2.core.item.ItemFuelCanEmpty) {
/*     */           
/* 218 */           ItemStack itemStack5 = this.inventory[3];
/* 219 */           itemStack5.field_77994_a--;
/* 220 */           if ((this.inventory[3]).field_77994_a <= 0)
/*     */           {
/* 222 */             this.inventory[3] = null;
/*     */           }
/* 224 */           this.inventory[2] = Ic2Items.filledFuelCan.func_77946_l();
/* 225 */           NBTTagCompound data = StackUtil.getOrCreateNbtData(this.inventory[2]);
/* 226 */           data.func_74768_a("value", this.fuelQuality);
/* 227 */           data.func_74768_a("maxValue", this.fuelQuality);
/*     */           break;
/*     */         } 
/* 230 */         damage = this.inventory[3].func_77960_j();
/* 231 */         damage -= this.fuelQuality;
/* 232 */         if (damage < 1)
/*     */         {
/* 234 */           damage = 1;
/*     */         }
/* 236 */         this.inventory[3] = null;
/* 237 */         this.inventory[2] = new ItemStack(Ic2Items.jetpack.func_77973_b(), 1, damage);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 242 */         itemStack6 = this.inventory[0];
/* 243 */         itemStack6.field_77994_a--;
/* 244 */         this.inventory[3].func_77964_b(this.inventory[3].func_77960_j() - 2);
/* 245 */         if ((this.inventory[0]).field_77994_a <= 0)
/*     */         {
/* 247 */           this.inventory[0] = null;
/*     */         }
/* 249 */         if (this.inventory[0] == null || this.inventory[3].func_77960_j() <= 1) {
/*     */           
/* 251 */           this.inventory[2] = this.inventory[3];
/* 252 */           this.inventory[3] = null;
/*     */         } 
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 263 */     if (this.audioSource != null) {
/*     */       
/* 265 */       IC2.audioManager.removeSources(this);
/* 266 */       this.audioSource = null;
/*     */     } 
/* 268 */     super.onUnloaded();
/*     */   }
/*     */   public boolean canOperate() {
/*     */     int food;
/*     */     int fuel;
/* 273 */     if (this.inventory[0] == null)
/*     */     {
/* 275 */       return false;
/*     */     }
/* 277 */     switch (getMode()) {
/*     */ 
/*     */       
/*     */       case 1:
/* 281 */         food = getFoodValue(this.inventory[0]);
/* 282 */         if (food > 0 && food <= (this.inventory[3]).field_77994_a && (this.inventory[2] == null || ((this.inventory[2]).field_77994_a + food <= this.inventory[2].func_77976_d() && this.inventory[2].func_77973_b() == Ic2Items.filledTinCan.func_77973_b() && this.inventory[2].func_77960_j() == getFoodMeta(this.inventory[0]))))
/*     */         {
/* 284 */           return true;
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 290 */         fuel = getFuelValue(this.inventory[0]);
/* 291 */         if (fuel > 0 && this.inventory[2] == null)
/*     */         {
/* 293 */           return true;
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 299 */         if (this.inventory[3].func_77960_j() >= 2 && getPelletValue(this.inventory[0]) > 0 && this.inventory[2] == null)
/*     */         {
/* 301 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 311 */     if (this.inventory[3] == null)
/*     */     {
/* 313 */       return 0;
/*     */     }
/* 315 */     if (this.inventory[3].func_77973_b() == Ic2Items.tinCan.func_77973_b())
/*     */     {
/* 317 */       return 1;
/*     */     }
/* 319 */     if (this.inventory[3].func_77973_b() instanceof ic2.core.item.ItemFuelCanEmpty || this.inventory[3].func_77973_b() == Ic2Items.jetpack.func_77973_b())
/*     */     {
/* 321 */       return 2;
/*     */     }
/* 323 */     if (this.inventory[3].func_77973_b() == Ic2Items.cfPack.func_77973_b())
/*     */     {
/* 325 */       return 3;
/*     */     }
/* 327 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 332 */     return "Canning Machine";
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFoodValue(ItemStack item) {
/* 337 */     if (item == null)
/*     */     {
/* 339 */       return 0;
/*     */     }
/* 341 */     if (item.func_77973_b() instanceof ItemFood) {
/*     */       
/* 343 */       ItemFood food = (ItemFood)item.func_77973_b();
/* 344 */       return (int)Math.ceil(food.func_150905_g(item) / 2.0D);
/*     */     } 
/* 346 */     if (item.func_77973_b() == Items.field_151105_aU || item.func_77973_b() == Item.func_150898_a(Blocks.field_150414_aQ))
/*     */     {
/* 348 */       return 6;
/*     */     }
/* 350 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFuelValue(ItemStack item) {
/* 355 */     if (item == null)
/*     */     {
/* 357 */       return 0;
/*     */     }
/* 359 */     TileEntityElectrolyzer.ItemRecipe recipe = new TileEntityElectrolyzer.ItemRecipe(item);
/* 360 */     if (fuelValues.containsKey(recipe)) { this; if (fuelMultiplyers.containsKey(recipe))
/*     */       {
/* 362 */         throw new RuntimeException("Invalid case. This Item: " + item.func_82833_r() + " has a Mutiplyer and Fuel value. Which is not allowed. Crash!");
/*     */       } }
/*     */     
/* 365 */     if (fuelValues.containsKey(recipe))
/*     */     {
/* 367 */       return ((Integer)fuelValues.get(recipe)).intValue();
/*     */     }
/* 369 */     if (fuelMultiplyers.containsKey(recipe))
/*     */     {
/* 371 */       return (int)(this.fuelQuality * ((Float)fuelMultiplyers.get(recipe)).floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 394 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 400 */     return this.energyconsume;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPelletValue(ItemStack item) {
/* 405 */     if (item == null)
/*     */     {
/* 407 */       return 0;
/*     */     }
/* 409 */     if (item.func_77973_b() != Ic2Items.constructionFoamPellet.func_77973_b())
/*     */     {
/* 411 */       return 0;
/*     */     }
/* 413 */     return item.field_77994_a;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFoodMeta(ItemStack item) {
/* 418 */     if (item == null)
/*     */     {
/* 420 */       return 0;
/*     */     }
/* 422 */     TileEntityElectrolyzer.ItemRecipe ccip = new TileEntityElectrolyzer.ItemRecipe(item.func_77973_b(), item.func_77960_j());
/* 423 */     return specialFood.containsKey(ccip) ? ((Integer)specialFood.get(ccip)).intValue() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 428 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 433 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 439 */     return (ContainerIC2)new ContainerCanner(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 445 */     return "block.machine.gui.GuiCanner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 455 */     ForgeDirection leftSide = null;
/* 456 */     switch (getFacing()) {
/*     */ 
/*     */       
/*     */       case 2:
/* 460 */         leftSide = ForgeDirection.EAST;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 465 */         leftSide = ForgeDirection.WEST;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 470 */         leftSide = ForgeDirection.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 475 */         leftSide = ForgeDirection.SOUTH;
/*     */         break;
/*     */     } 
/*     */     
/* 479 */     if (side == leftSide.ordinal())
/*     */     {
/* 481 */       return new int[] { 3 };
/*     */     }
/* 483 */     if (side == 0)
/*     */     {
/* 485 */       return new int[] { 1 };
/*     */     }
/* 487 */     if (side == 1)
/*     */     {
/* 489 */       return new int[] { 0 };
/*     */     }
/* 491 */     return new int[] { 2 };
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeInventorySide(ForgeDirection side) {
/* 496 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 501 */     return 0.85F;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 506 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151078_bh, 0), Integer.valueOf(1));
/* 507 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151070_bp, 0), Integer.valueOf(2));
/* 508 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151170_bI, 0), Integer.valueOf(2));
/* 509 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151076_bf, 0), Integer.valueOf(3));
/* 510 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151153_ao, 0), Integer.valueOf(4));
/* 511 */     specialFood.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151153_ao, 1), Integer.valueOf(5));
/* 512 */     fuelValues.put(new TileEntityElectrolyzer.ItemRecipe(Ic2Items.coalfuelCell), Integer.valueOf(2548));
/* 513 */     fuelValues.put(new TileEntityElectrolyzer.ItemRecipe(Ic2Items.biofuelCell), Integer.valueOf(868));
/* 514 */     fuelMultiplyers.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151137_ax, 0), Float.valueOf(0.2F));
/* 515 */     fuelMultiplyers.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151114_aO, 0), Float.valueOf(0.3F));
/* 516 */     fuelMultiplyers.put(new TileEntityElectrolyzer.ItemRecipe(Items.field_151016_H, 0), Float.valueOf(0.4F));
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */