/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class RecipeInputFluidContainer implements IRecipeInput {
/*    */   public final Fluid fluid;
/*    */   public final int amount;
/*    */   
/*    */   public RecipeInputFluidContainer(Fluid fluid) {
/* 15 */     this(fluid, 1000);
/*    */   }
/*    */   
/*    */   public RecipeInputFluidContainer(Fluid fluid, int amount) {
/* 19 */     this.fluid = fluid;
/* 20 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 25 */     FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(subject);
/* 26 */     if (fs == null) return false;
/*    */     
/* 28 */     return (fs.getFluid() == this.fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 33 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 38 */     List<ItemStack> ret = new ArrayList<>();
/*    */     
/* 40 */     for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 41 */       if (data.fluid.getFluid() == this.fluid) ret.add(data.filledContainer);
/*    */     
/*    */     } 
/* 44 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "RInputFluidContainer<" + this.amount + "x" + this.fluid.getName() + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\RecipeInputFluidContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */