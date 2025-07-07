/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemFuelCan
/*    */   extends ItemIC2
/*    */   implements IBoxable
/*    */ {
/*    */   public ItemFuelCan(int index) {
/* 11 */     super(index);
/* 12 */     setSpriteID("i1");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 18 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemFuelCan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */