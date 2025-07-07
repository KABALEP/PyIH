/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.inventory.SlotFurnace;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ 
/*     */ public class TileEntityInduction
/*     */   extends TileEntityAdvancedMachine
/*     */   implements IHasGui {
/*     */   public int soundTicker;
/*  20 */   private static int inputSlot = 1;
/*  21 */   private static int fuelSlot = 0;
/*  22 */   private static int outputSlot = 3;
/*     */ 
/*     */   
/*     */   public TileEntityInduction() {
/*  26 */     super(5, 15, 4000);
/*  27 */     this.soundTicker = IC2.random.nextInt(64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  33 */     if (isRendering())
/*     */     {
/*  35 */       return "Induction Furnace";
/*     */     }
/*  37 */     return "InductionFurnace";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/*  43 */     return "block.machine.gui.GuiInduction";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/*  53 */     return new int[] { 1, 2, 3, 4 };
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/*  58 */     return 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/*  64 */     if (input == null || FurnaceRecipes.func_77602_a().func_151395_a(input) == null)
/*     */     {
/*  66 */       return null;
/*     */     }
/*  68 */     if (adjusted)
/*     */     {
/*  70 */       input.field_77994_a--;
/*     */     }
/*  72 */     return new RecipeOutput(null, new ItemStack[] { FurnaceRecipes.func_77602_a().func_151395_a(input).func_77946_l() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutput() {
/*  78 */     RecipeOutput output = getOutput(1);
/*  79 */     if (output == null)
/*     */     {
/*  81 */       output = getOutput(2);
/*     */     }
/*  83 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot[] getInvSlots(InventoryPlayer par2) {
/*  89 */     Slot[] slots = new Slot[5];
/*  90 */     slots[0] = (Slot)new SlotDischarge(this, this.tier, 0, 56, 53);
/*  91 */     slots[1] = new Slot(this, 1, 47, 17);
/*  92 */     slots[2] = new Slot(this, 2, 63, 17);
/*  93 */     slots[3] = (Slot)new SlotFurnace(par2.field_70458_d, this, 3, 113, 35);
/*  94 */     slots[4] = (Slot)new SlotFurnace(par2.field_70458_d, this, 4, 131, 35);
/*  95 */     return slots;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/* 101 */     return new int[] { 3, 4 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputSlot(int slotID) {
/* 107 */     return (slotID == 1 || slotID == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputSlot(int slotID) {
/* 113 */     return (slotID == 3 || slotID == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operate() {
/* 119 */     operate(new int[] { 1, 2 });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 125 */     return "Machines/Induction Furnace/InductionLoop.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 137 */     if (par1 != null && FurnaceRecipes.func_77602_a().func_151395_a(par1) != null) {
/*     */       
/* 139 */       if (this.inventory[1] == null || (this.inventory[1].func_77969_a(par1) && ItemStack.func_77970_a(this.inventory[1], par1)))
/*     */       {
/* 141 */         return true;
/*     */       }
/* 143 */       if (this.inventory[2] == null || (this.inventory[2].func_77969_a(par1) && ItemStack.func_77970_a(this.inventory[2], par1)))
/*     */       {
/* 145 */         return true;
/*     */       }
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 154 */     return TileEntityElecFurnace.recipes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 160 */     if (field.equals("active"))
/*     */     {
/* 162 */       if (getActive() != this.prevActive)
/*     */       {
/* 164 */         if (getActive()) {
/*     */           
/* 166 */           IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/Induction Furnace/InductionStart.ogg", true, IC2.audioManager.defaultVolume * this.soundLevel);
/*     */         }
/*     */         else {
/*     */           
/* 170 */           IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/Induction Furnace/InductionStop.ogg", true, IC2.audioManager.defaultVolume * this.soundLevel);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 175 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityInduction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */