/*    */ package ic2.peIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.core.ISubModul;
/*    */ import ic2.peIntigration.core.RecipeLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public boolean canLoad() {
/* 14 */     return Loader.isModLoaded("ProjectE");
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
/* 32 */     RecipeLoader.init();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 38 */     return "Project E";
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
/* 50 */     return "ProjectE";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\peIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */