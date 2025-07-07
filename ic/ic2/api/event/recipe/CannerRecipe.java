/*    */ package ic2.api.event.recipe;
/*    */ 
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
/*    */ public abstract class CannerRecipe
/*    */   extends RecipeEvent
/*    */ {
/*    */   int fuel;
/*    */   
/*    */   public CannerRecipe(ItemStack input, int fuel) {
/* 22 */     super(input, input);
/* 23 */     this.fuel = fuel;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFuelValue() {
/* 28 */     return this.fuel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class FuelValue
/*    */     extends CannerRecipe
/*    */   {
/*    */     public FuelValue(ItemStack input, int fuel) {
/* 41 */       super(input, fuel);
/*    */     }
/*    */   }
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
/*    */   public static class FuelMultiplier
/*    */     extends CannerRecipe
/*    */   {
/*    */     public FuelMultiplier(ItemStack input, int percentage) {
/* 58 */       super(input, percentage);
/*    */     }
/*    */   }
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
/*    */   public static class FoodEffect
/*    */     extends CannerRecipe
/*    */   {
/*    */     public FoodEffect(ItemStack input, int meta) {
/* 83 */       super(input, meta);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\recipe\CannerRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */