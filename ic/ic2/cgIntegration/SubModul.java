/*    */ package ic2.cgIntegration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.cgIntegration.core.CraftingGuidePlugin;
/*    */ import ic2.core.ISubModul;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public boolean canLoad() {
/* 14 */     return Loader.isModLoaded("craftguide");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 20 */     return "Crafting Guide Submodul";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsSide(Side par1) {
/* 26 */     return par1.isClient();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void beforeItemLoad() {}
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
/* 44 */     CraftingGuidePlugin.init();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConfigName() {
/* 50 */     return "CraftingGuide";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cgIntegration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */