/*    */ package ic2.core.block.inventory.transporter;
/*    */ 
/*    */ import ic2.core.block.inventory.IInvSlot;
/*    */ import ic2.core.block.inventory.slots.SidedInvSlot;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SidedInventoryIterator
/*    */   implements Iterable<IInvSlot>
/*    */ {
/*    */   ForgeDirection dir;
/*    */   ISidedInventory inv;
/*    */   
/*    */   public SidedInventoryIterator(ISidedInventory par1, ForgeDirection par2) {
/* 18 */     this.inv = par1;
/* 19 */     this.dir = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<IInvSlot> iterator() {
/* 26 */     return new Iterator<IInvSlot>()
/*    */       {
/* 28 */         int[] slots = SidedInventoryIterator.this.inv.func_94128_d(SidedInventoryIterator.this.dir.ordinal());
/* 29 */         int slotID = 0;
/*    */ 
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 34 */           return (this.slotID < this.slots.length);
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public IInvSlot next() {
/* 40 */           return (IInvSlot)new SidedInvSlot(SidedInventoryIterator.this.inv, this.slots[this.slotID++], SidedInventoryIterator.this.dir);
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public void remove() {
/* 46 */           throw new UnsupportedOperationException("Remove not supported.");
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\transporter\SidedInventoryIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */