/*    */ package ic2.core.block.inventory.transporter;
/*    */ 
/*    */ import ic2.core.block.inventory.IInvSlot;
/*    */ import ic2.core.block.inventory.slots.BasicInvSlot;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicInventoryIterator
/*    */   implements Iterable<IInvSlot>
/*    */ {
/*    */   IInventory inv;
/*    */   
/*    */   public BasicInventoryIterator(IInventory par1) {
/* 16 */     this.inv = par1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<IInvSlot> iterator() {
/* 22 */     return new Iterator<IInvSlot>()
/*    */       {
/* 24 */         int slotID = 0;
/*    */ 
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 29 */           return (this.slotID < BasicInventoryIterator.this.inv.func_70302_i_());
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public IInvSlot next() {
/* 35 */           return (IInvSlot)new BasicInvSlot(BasicInventoryIterator.this.inv, this.slotID++);
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public void remove() {
/* 41 */           throw new UnsupportedOperationException("Remove not supported.");
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\transporter\BasicInventoryIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */