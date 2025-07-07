/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ItemBlockChargePad
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemBlockChargePad(Block block) {
/* 11 */     super(block);
/* 12 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack item) {
/* 18 */     switch (item.func_77960_j()) {
/*    */       case 0:
/* 20 */         return "tile.chargePadLV";
/* 21 */       case 1: return "tile.chargePadMV";
/* 22 */       case 2: return "tile.chargePadHV";
/* 23 */       case 3: return "tile.chargePadNuclear";
/*    */     } 
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77647_b(int p_77647_1_) {
/* 31 */     return p_77647_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockChargePad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */