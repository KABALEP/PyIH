/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipeInputItemStack implements IRecipeInput {
/*    */   public final ItemStack input;
/*    */   public final int amount;
/*    */   
/*    */   public RecipeInputItemStack(ItemStack aInput) {
/* 12 */     this(aInput, aInput.field_77994_a);
/*    */   }
/*    */   
/*    */   public RecipeInputItemStack(ItemStack aInput, int aAmount) {
/* 16 */     if (aInput.func_77973_b() == null) throw new IllegalArgumentException("Invalid item stack specfied");
/*    */     
/* 18 */     this.input = aInput.func_77946_l();
/* 19 */     this.amount = aAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 24 */     return (subject.func_77973_b() == this.input.func_77973_b() && (subject
/* 25 */       .func_77960_j() == this.input.func_77960_j() || this.input.func_77960_j() == 32767));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 30 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 35 */     return Arrays.asList(new ItemStack[] { this.input });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     ItemStack stack = this.input.func_77946_l();
/* 41 */     this.input.field_77994_a = this.amount;
/* 42 */     return "RInputItemStack<" + stack + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\RecipeInputItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */