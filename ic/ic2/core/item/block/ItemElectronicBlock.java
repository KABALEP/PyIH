/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemElectronicBlock
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemElectronicBlock(Block i) {
/* 10 */     super(i);
/* 11 */     func_77656_e(0);
/* 12 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int i) {
/* 17 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 22 */     int meta = itemstack.func_77960_j();
/* 23 */     switch (meta) {
/*    */ 
/*    */       
/*    */       case 0:
/* 27 */         return "blockBatBox";
/*    */     } 
/*    */ 
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemElectronicBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */