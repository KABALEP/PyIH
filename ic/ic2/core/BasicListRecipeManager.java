/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.IListRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class BasicListRecipeManager
/*    */   implements IListRecipeManager
/*    */ {
/* 14 */   private final List<IRecipeInput> list = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(ItemStack input) {
/* 19 */     if (input == null)
/*    */     {
/* 21 */       return false;
/*    */     }
/* 23 */     for (IRecipeInput cu : this.list) {
/*    */       
/* 25 */       for (ItemStack stack : cu.getInputs()) {
/*    */         
/* 27 */         if (input.func_77973_b() == stack.func_77973_b() && (input.func_77960_j() == stack.func_77960_j() || stack.func_77960_j() == 32767) && input.field_77994_a >= stack.field_77994_a)
/*    */         {
/* 29 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 39 */     return this.list.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(IRecipeInput input) {
/* 46 */     this.list.add(input);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IRecipeInput> getInputs() {
/* 52 */     return this.list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<IRecipeInput> iterator() {
/* 58 */     return this.list.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\BasicListRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */