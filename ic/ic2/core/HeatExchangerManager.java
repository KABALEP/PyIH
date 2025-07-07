/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ILiquidAcceptManager;
/*    */ import ic2.api.recipe.ILiquidHeatExchangerManager;
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
/*    */ public class HeatExchangerManager
/*    */   implements ILiquidHeatExchangerManager
/*    */ {
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 25 */     return new HashSet<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFluid(String fluidName, String fluidOutput, int huPerMB) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ILiquidHeatExchangerManager.HeatExchangeProperty getHeatExchangeProperty(Fluid fluid) {
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, ILiquidHeatExchangerManager.HeatExchangeProperty> getHeatExchangeProperties() {
/* 43 */     return new HashMap<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ILiquidAcceptManager getSingleDirectionLiquidManager() {
/* 49 */     return (ILiquidAcceptManager)this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\HeatExchangerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */