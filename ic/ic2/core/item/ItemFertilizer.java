/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemFertilizer
/*    */   extends ItemIC2
/*    */   implements IBoxable
/*    */ {
/*    */   public ItemFertilizer(int index) {
/* 11 */     super(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 17 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemFertilizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */