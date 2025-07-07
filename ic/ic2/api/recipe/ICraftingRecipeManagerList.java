/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public interface ICraftingRecipeManagerList
/*    */   extends ICraftingRecipeManager
/*    */ {
/*    */   List<IAdvRecipe> getRecipeList();
/*    */   
/*    */   public static class RecipeObject
/*    */   {
/*    */     int slot;
/*    */     List<ItemStack> items;
/*    */     
/*    */     public RecipeObject(int slot, ItemStack item) {
/* 52 */       this(slot, Arrays.asList(new ItemStack[] { item }));
/*    */     }
/*    */ 
/*    */     
/*    */     public RecipeObject(int slot, List<ItemStack> items) {
/* 57 */       this.slot = slot;
/* 58 */       this.items = items;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public int getSlot() {
/* 66 */       return this.slot;
/*    */     }
/*    */ 
/*    */     
/*    */     public List<ItemStack> getItems() {
/* 71 */       return this.items;
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface IAdvRecipe {
/*    */     boolean isShaped();
/*    */     
/*    */     boolean isHidden();
/*    */     
/*    */     List<ICraftingRecipeManagerList.RecipeObject> getRecipeInput();
/*    */     
/*    */     int getRecipeHeight();
/*    */     
/*    */     int getRecipeLength();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ICraftingRecipeManagerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */