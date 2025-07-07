/*    */ package ic2.core.block.inventory.slots;
/*    */ 
/*    */ import ic2.core.block.inventory.IInvSlot;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class SidedInvSlot
/*    */   implements IInvSlot
/*    */ {
/*    */   int slotID;
/*    */   int side;
/*    */   ISidedInventory inv;
/*    */   
/*    */   public SidedInvSlot(ISidedInventory par1, int par2, ForgeDirection par3) {
/* 16 */     this.inv = par1;
/* 17 */     this.slotID = par2;
/* 18 */     this.side = par3.ordinal();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlotIndex() {
/* 24 */     return this.slotID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getStack() {
/* 30 */     return this.inv.func_70301_a(this.slotID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decreaseStackInSlot() {
/* 36 */     return this.inv.func_70298_a(this.slotID, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decreaseStackInSlot(int amount) {
/* 42 */     return this.inv.func_70298_a(this.slotID, amount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStack(ItemStack par1) {
/* 48 */     this.inv.func_70299_a(this.slotID, par1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canInsertStack(ItemStack par1) {
/* 54 */     return this.inv.func_102007_a(this.slotID, par1, this.side);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canRemoveStack(ItemStack par1) {
/* 60 */     return this.inv.func_102008_b(this.slotID, par1, this.side);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\slots\SidedInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */