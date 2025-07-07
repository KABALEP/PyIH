/*    */ package ic2.core;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ public interface ISubModul
/*    */ {
/*    */   boolean canLoad();
/*    */   
/*    */   void beforeItemLoad();
/*    */   
/*    */   void afterItemLoad();
/*    */   
/*    */   void onPostLoad();
/*    */   
/*    */   String getModulName();
/*    */   
/*    */   String getConfigName();
/*    */   
/*    */   boolean supportsSide(Side paramSide);
/*    */   
/*    */   public enum LoadingState
/*    */   {
/* 24 */     BeforeObjectLoad,
/* 25 */     AfterObjectLoad,
/* 26 */     PostLoad;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ISubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */