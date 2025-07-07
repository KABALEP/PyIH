/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.api.recipe.IFluidHeatManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FluidHeatManager
/*    */   implements IFluidHeatManager
/*    */ {
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 24 */     return new HashSet<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFluid(String fluidName, int amount, int heat) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IFluidHeatManager.BurnProperty getBurnProperty(Fluid fluid) {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, IFluidHeatManager.BurnProperty> getBurnProperties() {
/* 42 */     return new HashMap<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\FluidHeatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */