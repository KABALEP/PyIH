/*    */ package ic2.core.block.inventory.filter;
/*    */ 
/*    */ import ic2.core.block.inventory.IItemTransporter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class BasicItemFilter
/*    */   implements IItemTransporter.IFilter
/*    */ {
/*    */   ItemStack item;
/*    */   
/*    */   public BasicItemFilter(ItemStack par1) {
/* 13 */     this.item = par1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 19 */     return ItemStack.func_77970_a(this.item, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\filter\BasicItemFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */