/*    */ package ic2.bcIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.bcIntigration.core.BuildcraftModul;
/*    */ import ic2.core.ISubModul;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul
/*    */ {
/*    */   public boolean canLoad() {
/* 14 */     return Loader.isModLoaded("BuildCraft|Core");
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
/* 32 */     BuildcraftModul.postLoad();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 38 */     return "BuildCraft";
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
/* 50 */     return "BuildCraft";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */