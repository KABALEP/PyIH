/*    */ package ic2.core.util;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SideGateway<T>
/*    */ {
/*    */   private final T clientInstance;
/*    */   private final T serverInstance;
/*    */   
/*    */   public SideGateway(String server, String client) {
/*    */     try {
/* 15 */       if (FMLCommonHandler.instance().getSide().isClient()) {
/* 16 */         this.clientInstance = (T)Class.forName(client).newInstance();
/*    */       } else {
/*    */         
/* 19 */         this.clientInstance = null;
/*    */       } 
/*    */       
/* 22 */       this.serverInstance = (T)Class.forName(server).newInstance();
/*    */     }
/* 24 */     catch (Exception e) {
/*    */       
/* 26 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public T get() {
/* 32 */     if (FMLCommonHandler.instance().getEffectiveSide().isClient())
/*    */     {
/* 34 */       return this.clientInstance;
/*    */     }
/* 36 */     return this.serverInstance;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\SideGateway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */