/*    */ package ic2.thcIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.core.ISubModul;
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
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public void afterItemLoad() {}
/*    */   
/*    */   public void beforeItemLoad() {}
/*    */   
/*    */   public boolean canLoad() {
/* 25 */     return Loader.isModLoaded("Thaumcraft");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 31 */     return "Thaumcraft";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {
/* 37 */     ThaumcraftPlugin.load();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsSide(Side arg0) {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConfigName() {
/* 49 */     return "Thaumcraft";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\thcIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */