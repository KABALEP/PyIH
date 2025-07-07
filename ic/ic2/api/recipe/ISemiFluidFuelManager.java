/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISemiFluidFuelManager
/*    */   extends ILiquidAcceptManager
/*    */ {
/*    */   void addFluid(String paramString, int paramInt, double paramDouble);
/*    */   
/*    */   BurnProperty getBurnProperty(Fluid paramFluid);
/*    */   
/*    */   Map<String, BurnProperty> getBurnProperties();
/*    */   
/*    */   public static class BurnProperty
/*    */   {
/*    */     public final int amount;
/*    */     public final double power;
/*    */     
/*    */     public BurnProperty(int amount1, double power1) {
/* 25 */       this.amount = amount1;
/* 26 */       this.power = power1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ISemiFluidFuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */