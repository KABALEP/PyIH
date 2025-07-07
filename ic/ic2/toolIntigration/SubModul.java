/*    */ package ic2.toolIntigration;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.core.ISubModul;
/*    */ import ic2.toolIntigration.core.WrenchPlugin;
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
/* 25 */     return anyWrenchAviable();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 31 */     return "Wrench";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {
/* 37 */     WrenchPlugin.load();
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
/*    */   public boolean anyWrenchAviable() {
/*    */     try {
/* 50 */       Class.forName("buildcraft.api.tools.IToolWrench");
/* 51 */       return true;
/*    */     }
/* 53 */     catch (Exception exception) {
/*    */ 
/*    */       
/*    */       try {
/*    */ 
/*    */         
/* 59 */         Class.forName("appeng.api.implementations.items.IAEWrench");
/* 60 */         return true;
/*    */       }
/* 62 */       catch (Exception exception1) {
/*    */ 
/*    */         
/*    */         try {
/*    */ 
/*    */           
/* 68 */           Class.forName("crazypants.enderio.api.tool.ITool");
/* 69 */           return true;
/*    */         }
/* 71 */         catch (Exception exception2) {
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 76 */           return false;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   public String getConfigName() {
/* 82 */     return "WrenchPlugin";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\toolIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */