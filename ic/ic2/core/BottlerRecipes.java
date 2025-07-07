/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BottlerRecipes
/*    */   implements ICannerBottleRecipeManager
/*    */ {
/*    */   public void addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output) {}
/*    */   
/*    */   public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, boolean adjustInput, boolean acceptTest) {
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<ICannerBottleRecipeManager.Input, RecipeOutput> getRecipes() {
/* 30 */     return new HashMap<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\BottlerRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */