/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemBlockMetal
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemBlockMetal(Block i) {
/* 10 */     super(i);
/* 11 */     func_77656_e(0);
/* 12 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77647_b(int i) {
/* 18 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 23 */     int meta = itemstack.func_77960_j();
/* 24 */     switch (meta) {
/*    */ 
/*    */       
/*    */       case 0:
/* 28 */         return "blockMetalCopper";
/*    */ 
/*    */       
/*    */       case 1:
/* 32 */         return "blockMetalTin";
/*    */ 
/*    */       
/*    */       case 2:
/* 36 */         return "blockMetalBronze";
/*    */ 
/*    */       
/*    */       case 3:
/* 40 */         return "blockMetalUranium";
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockMetal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */