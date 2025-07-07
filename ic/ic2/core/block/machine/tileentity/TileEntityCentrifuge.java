/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import ic2.core.slot.SlotOutput;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class TileEntityCentrifuge
/*     */   extends TileEntityAdvancedMachine
/*     */ {
/*     */   public TileEntityCentrifuge() {
/*  19 */     super(5, 15, 4000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/*  25 */     return "block.machine.gui.GuiCentrifuge";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/*  31 */     return new int[] { 1, 2, 3, 4 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/*  37 */     return Recipes.extractor.getOutputFor(input, adjusted);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot[] getInvSlots(InventoryPlayer par2) {
/*  43 */     Slot[] slots = new Slot[5];
/*  44 */     slots[0] = (Slot)new SlotDischarge(this, 2147483647, 0, 40, 53);
/*  45 */     slots[1] = new Slot(this, 1, 40, 17);
/*  46 */     slots[2] = (Slot)new SlotOutput(par2.field_70458_d, this, 2, 92, 35);
/*  47 */     slots[3] = (Slot)new SlotOutput(par2.field_70458_d, this, 3, 110, 35);
/*  48 */     slots[4] = (Slot)new SlotOutput(par2.field_70458_d, this, 4, 128, 35);
/*  49 */     return slots;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/*  55 */     return new int[] { 2, 3, 4 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  61 */     return "Centrifuge Extractor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/*  67 */     return "Machines/ExtractorOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/*  73 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/*  79 */     if (par1 == null)
/*     */     {
/*  81 */       return false;
/*     */     }
/*  83 */     for (IRecipeInput input : Recipes.extractor.getRecipes().keySet()) {
/*     */       
/*  85 */       if (input.matches(par1))
/*     */       {
/*  87 */         return super.isValidInput(par1);
/*     */       }
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputSlot(int slotID) {
/*  96 */     return (slotID == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputSlot(int slotID) {
/* 102 */     return (slotID == 2 || slotID == 3 || slotID == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 108 */     return Recipes.extractor;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCentrifuge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */