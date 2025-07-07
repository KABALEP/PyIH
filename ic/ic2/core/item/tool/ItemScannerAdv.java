/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemScannerAdv
/*    */   extends ItemScanner
/*    */ {
/*    */   public ItemScannerAdv(int index, int t) {
/* 10 */     super(index, t);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int startLayerScan(ItemStack itemStack) {
/* 16 */     return ElectricItem.manager.use(itemStack, 250.0D, null) ? 4 : 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack par1) {
/* 22 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean searchOreValue(ItemStack par1) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyCost(ItemStack par1) {
/* 34 */     return 250;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemScannerAdv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */