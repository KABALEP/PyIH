/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotMatterScrap
/*    */   extends Slot
/*    */ {
/*    */   public SlotMatterScrap(IInventory par1iInventory, int par2, int par3, int par4) {
/* 13 */     super(par1iInventory, par2, par3, par4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 18 */     if (stack == null)
/*    */     {
/* 20 */       return false;
/*    */     }
/* 22 */     RecipeOutput output = Recipes.matterAmplifier.getOutputFor(stack, false);
/* 23 */     return (output != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotMatterScrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */