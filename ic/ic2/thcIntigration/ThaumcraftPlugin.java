/*    */ package ic2.thcIntigration;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeInputItemStack;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.config.ConfigBlocks;
/*    */ import thaumcraft.common.config.ConfigItems;
/*    */ 
/*    */ public class ThaumcraftPlugin
/*    */ {
/*    */   public static void load() {
/* 16 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 0, 2);
/* 17 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 7, 2);
/* 18 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 1, 3);
/* 19 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 2, 3);
/* 20 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 3, 3);
/* 21 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 4, 3);
/* 22 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 5, 3);
/* 23 */     IC2.addValuableOre(ConfigBlocks.blockCustomOre, 6, 3);
/*    */     
/* 25 */     for (int i = 1; i < 7; i++) {
/*    */       
/* 27 */       IMachineRecipeManager list = Recipes.extractor;
/* 28 */       if (list instanceof IMachineRecipeManagerExp) {
/*    */         
/* 30 */         ((IMachineRecipeManagerExp)list).addRecipe((IRecipeInput)new RecipeInputItemStack(new ItemStack(ConfigBlocks.blockCustomOre, 1, i)), null, 0.5F, new ItemStack[] { new ItemStack(ConfigItems.itemShard, 2, i - 1) });
/*    */       }
/*    */       else {
/*    */         
/* 34 */         list.addRecipe((IRecipeInput)new RecipeInputItemStack(new ItemStack(ConfigBlocks.blockCustomOre, 1, i)), null, new ItemStack[] { new ItemStack(ConfigItems.itemShard, 2, i - 1) });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\thcIntigration\ThaumcraftPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */