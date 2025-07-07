/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ICraftingRecipeManagerList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ 
/*    */ 
/*    */ public class AdvCraftingRecipeManager
/*    */   implements ICraftingRecipeManagerList
/*    */ {
/* 13 */   List<ICraftingRecipeManagerList.IAdvRecipe> recipes = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRecipe(ItemStack output, Object... input) {
/* 18 */     this.recipes.add(AdvRecipe.addAndRegister(output, input));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addShapelessRecipe(ItemStack output, Object... input) {
/* 24 */     this.recipes.add(AdvShapelessRecipe.addAndRegister(output, input));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ICraftingRecipeManagerList.IAdvRecipe> getRecipeList() {
/* 30 */     return new ArrayList<>(this.recipes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(ICraftingRecipeManagerList.IAdvRecipe recipe) {
/* 35 */     this.recipes.add(recipe);
/* 36 */     CraftingManager.func_77594_a().func_77592_b().add(recipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\AdvCraftingRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */