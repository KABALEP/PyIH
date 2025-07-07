/*    */ package ic2.nerIntigration;
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
/* 25 */     return Loader.isModLoaded("neresources");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 31 */     return "Not Enough Resources";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {
/* 37 */     NerPlugin.load();
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
/* 49 */     return "NotEnoughResources";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\nerIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */