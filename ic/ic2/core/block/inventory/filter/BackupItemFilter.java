/*    */ package ic2.core.block.inventory.filter;
/*    */ 
/*    */ import ic2.core.block.inventory.IItemTransporter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BackupItemFilter
/*    */   implements IItemTransporter.IFilter
/*    */ {
/*    */   ItemStack item;
/*    */   
/*    */   public BackupItemFilter(ItemStack item) {
/* 12 */     this.item = ItemStack.func_77944_b(item);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 19 */     return (this.item == null) ? true : this.item.func_77969_a(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\filter\BackupItemFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */