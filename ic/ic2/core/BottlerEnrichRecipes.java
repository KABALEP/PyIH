/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BottlerEnrichRecipes
/*    */   implements ICannerEnrichRecipeManager
/*    */ {
/*    */   public void addRecipe(FluidStack input, IRecipeInput additive, FluidStack output) {}
/*    */   
/*    */   public RecipeOutput getOutputFor(FluidStack input, ItemStack additive, boolean adjustInput, boolean acceptTest) {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<ICannerEnrichRecipeManager.Input, FluidStack> getRecipes() {
/* 31 */     return new HashMap<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\BottlerEnrichRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */