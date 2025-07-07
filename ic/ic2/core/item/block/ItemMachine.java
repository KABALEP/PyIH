/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemMachine
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemMachine(Block i) {
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
/* 27 */         return "blockMachine";
/*    */ 
/*    */       
/*    */       case 1:
/* 31 */         return "blockIronFurnace";
/*    */ 
/*    */       
/*    */       case 2:
/* 35 */         return "blockElecFurnace";
/*    */ 
/*    */       
/*    */       case 3:
/* 39 */         return "blockMacerator";
/*    */ 
/*    */       
/*    */       case 4:
/* 43 */         return "blockExtractor";
/*    */ 
/*    */       
/*    */       case 5:
/* 47 */         return "blockCompressor";
/*    */ 
/*    */       
/*    */       case 6:
/* 51 */         return "blockCanner";
/*    */ 
/*    */       
/*    */       case 7:
/* 55 */         return "blockMiner";
/*    */ 
/*    */       
/*    */       case 8:
/* 59 */         return "blockPump";
/*    */ 
/*    */       
/*    */       case 9:
/* 63 */         return "blockMagnetizer";
/*    */ 
/*    */       
/*    */       case 10:
/* 67 */         return "blockElectrolyzer";
/*    */ 
/*    */       
/*    */       case 11:
/* 71 */         return "blockRecycler";
/*    */ 
/*    */       
/*    */       case 12:
/* 75 */         return "blockAdvMachine";
/*    */ 
/*    */       
/*    */       case 13:
/* 79 */         return "blockInduction";
/*    */ 
/*    */       
/*    */       case 14:
/* 83 */         return "blockMatter";
/*    */ 
/*    */       
/*    */       case 15:
/* 87 */         return "blockTerra";
/*    */     } 
/*    */ 
/*    */     
/* 91 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */