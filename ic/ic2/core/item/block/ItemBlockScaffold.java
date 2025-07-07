/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBlockScaffold
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemBlockScaffold(Block block) {
/* 12 */     super(block);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
/* 18 */     if (par1Block == this.field_150939_a)
/*    */     {
/* 20 */       return true;
/*    */     }
/* 22 */     return super.canHarvestBlock(par1Block, itemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */