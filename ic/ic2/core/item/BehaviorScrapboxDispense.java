/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.dispenser.IPosition;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BehaviorScrapboxDispense
/*    */   extends BehaviorDefaultDispenseItem
/*    */ {
/*    */   protected ItemStack func_82487_b(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
/* 15 */     EnumFacing var3 = EnumFacing.func_82600_a(par1IBlockSource.func_82620_h());
/* 16 */     IPosition var2 = BlockDispenser.func_149939_a(par1IBlockSource);
/* 17 */     BehaviorDefaultDispenseItem.func_82486_a(par1IBlockSource.func_82618_k(), Recipes.scrapboxDrops.getDrop(par2ItemStack, true), 6, var3, var2);
/* 18 */     return par2ItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\BehaviorScrapboxDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */