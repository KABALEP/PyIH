/*    */ package ic2.cgIntegration.core;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.Ic2Items;
/*    */ 
/*    */ 
/*    */ public class CraftingGuidePlugin
/*    */ {
/*    */   public static void init() {
/* 10 */     new AdvRecipeGenerator();
/* 11 */     new MachineGenerator(Recipes.macerator.getRecipes(), 1, 61, 82, 61, Ic2Items.macerator.func_77946_l());
/* 12 */     new MachineGenerator(Recipes.extractor.getRecipes(), 1, 121, 82, 121, Ic2Items.extractor.func_77946_l());
/* 13 */     new MachineGenerator(Recipes.compressor.getRecipes(), 1, 1, 82, 1, Ic2Items.compressor.func_77946_l());
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cgIntegration\core\CraftingGuidePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */