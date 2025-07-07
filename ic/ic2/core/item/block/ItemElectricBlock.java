/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemElectricBlock
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemElectricBlock(Block i) {
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
/*    */ 
/*    */       
/*    */       case 1:
/* 31 */         return "blockMFE";
/*    */ 
/*    */       
/*    */       case 2:
/* 35 */         return "blockMFSU";
/*    */ 
/*    */       
/*    */       case 3:
/* 39 */         return "blockTransformerLV";
/*    */ 
/*    */       
/*    */       case 4:
/* 43 */         return "blockTransformerMV";
/*    */ 
/*    */       
/*    */       case 5:
/* 47 */         return "blockTransformerHV";
/*    */ 
/*    */       
/*    */       case 6:
/* 51 */         return "blockTransformerEV";
/*    */ 
/*    */       
/*    */       case 7:
/* 55 */         return "blockBatteryBox";
/*    */ 
/*    */       
/*    */       case 8:
/* 59 */         return "blockAdjustableTranformer";
/*    */ 
/*    */       
/*    */       case 9:
/* 63 */         return "blockCreativeStorage";
/*    */     } 
/*    */ 
/*    */     
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */