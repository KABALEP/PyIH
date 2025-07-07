/*    */ package ic2.core;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class ContainerIC2
/*    */   extends Container
/*    */ {
/*    */   public abstract int guiInventorySize();
/*    */   
/*    */   public abstract int getInput();
/*    */   
/*    */   public int firstEmptyFrom(int start, int end, IInventory inv) {
/* 17 */     for (int i = start; i <= end; i++) {
/*    */       
/* 19 */       if (inv.func_70301_a(i) == null)
/*    */       {
/* 21 */         return i;
/*    */       }
/*    */     } 
/* 24 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_82846_b(EntityPlayer player, int i) {
/* 29 */     ItemStack itemstack = null;
/* 30 */     Slot slot = this.field_75151_b.get(i);
/* 31 */     if (slot != null && slot.func_75216_d()) {
/*    */       
/* 33 */       ItemStack itemstack2 = slot.func_75211_c();
/* 34 */       itemstack = itemstack2.func_77946_l();
/* 35 */       if (i < guiInventorySize()) {
/*    */         
/* 37 */         transferToSlots(itemstack2, guiInventorySize(), this.field_75151_b.size(), false);
/* 38 */         slot.func_75220_a(itemstack2, itemstack);
/*    */       }
/* 40 */       else if (i >= guiInventorySize() && i < this.field_75151_b.size()) {
/*    */         
/* 42 */         boolean transferDone = false;
/* 43 */         for (int j = 0; j < guiInventorySize(); j++) {
/*    */           
/* 45 */           Slot slot2 = func_75139_a(j);
/* 46 */           if (slot2 != null && slot2.func_75214_a(itemstack2) && transferToSlots(itemstack2, j, j + 1, false)) {
/*    */             
/* 48 */             transferDone = true;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/* 54 */       if (itemstack2.field_77994_a == 0) {
/*    */         
/* 56 */         slot.func_75215_d((ItemStack)null);
/*    */       }
/*    */       else {
/*    */         
/* 60 */         slot.func_75218_e();
/*    */       } 
/* 62 */       if (itemstack2.field_77994_a == itemstack.field_77994_a)
/*    */       {
/* 64 */         return null;
/*    */       }
/* 66 */       slot.func_82870_a(player, itemstack2);
/*    */     } 
/* 68 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean transferToSlots(ItemStack stack, int startIndex, int endIndex, boolean lookBackwards) {
/* 73 */     return func_75135_a(stack, startIndex, endIndex, lookBackwards);
/*    */   }
/*    */   
/*    */   public abstract boolean func_75145_c(EntityPlayer paramEntityPlayer);
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ContainerIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */