/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemMachine2
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemMachine2(Block i) {
/* 15 */     super(i);
/* 16 */     func_77656_e(0);
/* 17 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int i) {
/* 22 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 27 */     int meta = itemstack.func_77960_j();
/* 28 */     switch (meta) {
/*    */ 
/*    */       
/*    */       case 0:
/* 32 */         return "blockTeleporter";
/*    */ 
/*    */       
/*    */       case 1:
/* 36 */         return "blockTesla";
/*    */ 
/*    */       
/*    */       case 2:
/* 40 */         return "blockCropmatron";
/*    */ 
/*    */       
/*    */       case 3:
/* 44 */         return "blockSingularity";
/*    */ 
/*    */       
/*    */       case 4:
/* 48 */         return "blockCentrifug";
/*    */ 
/*    */       
/*    */       case 5:
/* 52 */         return "blockRotary";
/*    */ 
/*    */       
/*    */       case 6:
/* 56 */         return "blockCharged";
/*    */ 
/*    */       
/*    */       case 7:
/* 60 */         return "blockVacuum";
/*    */ 
/*    */       
/*    */       case 8:
/* 64 */         return "blockCompacting";
/*    */ 
/*    */       
/*    */       case 9:
/* 68 */         return "blockElectricEnchanter";
/*    */ 
/*    */       
/*    */       case 10:
/* 72 */         return "blockOreScanner";
/*    */ 
/*    */       
/*    */       case 11:
/* 76 */         return "blockCropScanner";
/*    */ 
/*    */       
/*    */       case 12:
/* 80 */         return "blockReactorPlanner";
/*    */ 
/*    */       
/*    */       case 13:
/* 84 */         return "blockSoundBeacon";
/*    */ 
/*    */       
/*    */       case 14:
/* 88 */         return "blockUraniumEnricher";
/*    */ 
/*    */       
/*    */       case 15:
/* 92 */         return "blockCropHarvester";
/*    */     } 
/*    */ 
/*    */     
/* 96 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemMachine2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */