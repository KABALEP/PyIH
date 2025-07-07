/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ISemiFluidFuelManager;
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
/*    */ public class SemiFluidFuelManager
/*    */   implements ISemiFluidFuelManager
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
/*    */   public void addFluid(String fluidName, int amount, double power) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ISemiFluidFuelManager.BurnProperty getBurnProperty(Fluid fluid) {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, ISemiFluidFuelManager.BurnProperty> getBurnProperties() {
/* 42 */     return new HashMap<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\SemiFluidFuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */