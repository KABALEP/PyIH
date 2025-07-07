/*    */ package ic2.rfIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.ModAPIManager;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.core.ISubModul;
/*    */ import ic2.rfIntigration.core.RFConverter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public boolean canLoad() {
/* 14 */     return ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void beforeItemLoad() {
/* 20 */     RFConverter.loadTiles();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void afterItemLoad() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {
/* 32 */     RFConverter.loadRecipes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 38 */     return "RF Plugin";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsSide(Side par1) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConfigName() {
/* 50 */     return "RedstoneFlux";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */