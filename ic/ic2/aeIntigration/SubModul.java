/*    */ package ic2.aeIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.aeIntigration.core.PluginAE;
/*    */ import ic2.core.ISubModul;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public void afterItemLoad() {
/* 14 */     PluginAE.load();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void beforeItemLoad() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canLoad() {
/* 26 */     return Loader.isModLoaded("appliedenergistics2");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 32 */     return "Applied Energistics 2";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {
/* 38 */     PluginAE.postLoad();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsSide(Side arg0) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConfigName() {
/* 50 */     return "appliedenergistics2";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */