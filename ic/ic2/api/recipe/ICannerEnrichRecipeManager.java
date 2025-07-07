/*    */ package ic2.api.recipe;
/*    */ 
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
/*    */ public interface ICannerEnrichRecipeManager
/*    */ {
/*    */   void addRecipe(FluidStack paramFluidStack1, IRecipeInput paramIRecipeInput, FluidStack paramFluidStack2);
/*    */   
/*    */   RecipeOutput getOutputFor(FluidStack paramFluidStack, ItemStack paramItemStack, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   Map<Input, FluidStack> getRecipes();
/*    */   
/*    */   public static class Input
/*    */   {
/*    */     public final FluidStack fluid;
/*    */     public final IRecipeInput additive;
/*    */     
/*    */     public Input(FluidStack fluid1, IRecipeInput additive1) {
/* 42 */       this.fluid = fluid1;
/* 43 */       this.additive = additive1;
/*    */     }
/*    */     
/*    */     public boolean matches(FluidStack fluid1, ItemStack additive1) {
/* 47 */       return ((this.fluid == null || this.fluid.isFluidEqual(fluid1)) && this.additive
/* 48 */         .matches(additive1));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ICannerEnrichRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */