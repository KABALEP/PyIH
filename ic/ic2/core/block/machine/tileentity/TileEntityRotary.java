/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class TileEntityRotary
/*    */   extends TileEntityAdvancedMachine
/*    */ {
/*    */   public TileEntityRotary() {
/* 18 */     super(4, 15, 4000);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getGuiClassName(EntityPlayer p0) {
/* 24 */     return "block.machine.gui.GuiRotary";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] func_94128_d(int side) {
/* 30 */     return new int[] { 1, 2, 3 };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/* 36 */     return Recipes.macerator.getOutputFor(input, adjusted);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Slot[] getInvSlots(InventoryPlayer par2) {
/* 42 */     Slot[] slots = new Slot[4];
/* 43 */     slots[0] = (Slot)new SlotDischarge(this, 2147483647, 0, 56, 53);
/* 44 */     slots[1] = new Slot(this, 1, 56, 17);
/* 45 */     slots[2] = (Slot)new SlotOutput(par2.field_70458_d, this, 2, 113, 35);
/* 46 */     slots[3] = (Slot)new SlotOutput(par2.field_70458_d, this, 3, 131, 35);
/* 47 */     return slots;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getOutputSlots() {
/* 53 */     return new int[] { 2, 3 };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 59 */     return "Rotary Macerrator";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStartSoundFile() {
/* 65 */     return "Machines/MaceratorOp.ogg";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getInterruptSoundFile() {
/* 71 */     return "Machines/InterruptOne.ogg";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInputSlot(int slotID) {
/* 77 */     return (slotID == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOutputSlot(int slotID) {
/* 83 */     return (slotID == 2 || slotID == 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachineRecipeManager getRecipeList() {
/* 89 */     return Recipes.macerator;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityRotary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */