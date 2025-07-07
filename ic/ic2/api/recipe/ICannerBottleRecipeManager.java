/*    */ package ic2.api.recipe;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ICannerBottleRecipeManager
/*    */ {
/*    */   void addRecipe(IRecipeInput paramIRecipeInput1, IRecipeInput paramIRecipeInput2, ItemStack paramItemStack);
/*    */   
/*    */   RecipeOutput getOutputFor(ItemStack paramItemStack1, ItemStack paramItemStack2, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   Map<Input, RecipeOutput> getRecipes();
/*    */   
/*    */   public static class Input
/*    */   {
/*    */     public final IRecipeInput container;
/*    */     public final IRecipeInput fill;
/*    */     
/*    */     public Input(IRecipeInput container1, IRecipeInput fill1) {
/* 40 */       this.container = container1;
/* 41 */       this.fill = fill1;
/*    */     }
/*    */     
/*    */     public boolean matches(ItemStack container1, ItemStack fill1) {
/* 45 */       return (this.container.matches(container1) && this.fill.matches(fill1));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ICannerBottleRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */