/*    */ package ic2.core.block.inventory.slots;
/*    */ 
/*    */ import ic2.core.block.inventory.IInvSlot;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BasicInvSlot
/*    */   implements IInvSlot
/*    */ {
/*    */   int slotID;
/*    */   IInventory inv;
/*    */   
/*    */   public BasicInvSlot(IInventory par1, int par2) {
/* 14 */     this.inv = par1;
/* 15 */     this.slotID = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlotIndex() {
/* 21 */     return this.slotID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getStack() {
/* 27 */     return this.inv.func_70301_a(this.slotID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decreaseStackInSlot() {
/* 33 */     return this.inv.func_70298_a(this.slotID, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decreaseStackInSlot(int amount) {
/* 39 */     return this.inv.func_70298_a(this.slotID, amount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStack(ItemStack par1) {
/* 45 */     this.inv.func_70299_a(this.slotID, par1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canInsertStack(ItemStack par1) {
/* 51 */     return this.inv.func_94041_b(this.slotID, par1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canRemoveStack(ItemStack par1) {
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\slots\BasicInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */