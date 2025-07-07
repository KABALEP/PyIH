/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemGenerator
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemGenerator(Block i) {
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
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 23 */     int meta = itemstack.func_77960_j();
/* 24 */     switch (meta) {
/*    */       case 0:
/* 26 */         return "blockGenerator";
/* 27 */       case 1: return "blockGeoGenerator";
/* 28 */       case 2: return "blockWaterGenerator";
/* 29 */       case 3: return "blockSolarGenerator";
/* 30 */       case 4: return "blockWindGenerator";
/* 31 */       case 5: return "blockNuclearReactor";
/* 32 */       case 6: return "blockSteamReactor";
/* 33 */       case 7: return "blockSolarLV";
/* 34 */       case 8: return "blockSolarMV";
/* 35 */       case 9: return "blockSolarHV";
/* 36 */       case 10: return "blockWaterMillLV";
/* 37 */       case 11: return "blockWaterMillMV";
/* 38 */       case 12: return "blockWaterMillHV";
/* 39 */     }  return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */