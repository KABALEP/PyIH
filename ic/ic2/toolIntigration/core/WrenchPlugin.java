/*    */ package ic2.toolIntigration.core;
/*    */ 
/*    */ import ic2.api.info.IC2Classic;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrenchPlugin
/*    */ {
/*    */   public static void load() {
/*    */     try {
/* 11 */       Class.forName("buildcraft.api.tools.IToolWrench");
/* 12 */       IC2Classic.registerWrenchHandler(new BCWrenchModul());
/*    */     }
/* 14 */     catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 19 */       Class.forName("appeng.api.implementations.items.IAEWrench");
/* 20 */       IC2Classic.registerWrenchHandler(new AEWrenchModul());
/*    */     }
/* 22 */     catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 28 */       Class.forName("crazypants.enderio.api.tool.ITool");
/* 29 */       IC2Classic.registerWrenchHandler(new EnderIOWrenchModul());
/*    */     }
/* 31 */     catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\toolIntigration\core\WrenchPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */