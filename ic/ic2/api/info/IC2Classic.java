/*    */ package ic2.api.info;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import ic2.api.item.IWrenchHandler;
/*    */ 
/*    */ 
/*    */ public class IC2Classic
/*    */ {
/*    */   public static IWindTicker windNetwork;
/* 10 */   private static IC2Type ic2 = IC2Type.NeedLoad;
/*    */   
/*    */   public static IWrenchHandler.IWrenchRegistry customRegistry;
/*    */   
/*    */   public static IC2Type getLoadedIC2Type() {
/* 15 */     if (ic2 == IC2Type.NeedLoad)
/*    */     {
/* 17 */       updateState();
/*    */     }
/* 19 */     return ic2;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isIc2ExpLoaded() {
/* 24 */     if (ic2 == IC2Type.NeedLoad)
/*    */     {
/* 26 */       updateState();
/*    */     }
/* 28 */     return (ic2 == IC2Type.Experimental);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isIc2ClassicLoaded() {
/* 33 */     if (ic2 == IC2Type.NeedLoad)
/*    */     {
/* 35 */       updateState();
/*    */     }
/* 37 */     return (ic2 == IC2Type.SpeigersClassic);
/*    */   }
/*    */ 
/*    */   
/*    */   public static IWindTicker getWindNetwork() {
/* 42 */     return windNetwork;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean enabledCustoWindNetwork() {
/* 47 */     return (windNetwork != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerWrenchHandler(IWrenchHandler par1) {
/* 52 */     if (isIc2ClassicLoaded()) {
/*    */       
/* 54 */       Object obj = Info.ic2ModInstance;
/* 55 */       if (obj instanceof IWrenchHandler.IWrenchRegistry)
/*    */       {
/* 57 */         ((IWrenchHandler.IWrenchRegistry)obj).registerWrenchSupporter(par1);
/*    */       }
/*    */     }
/* 60 */     else if (customRegistry != null) {
/*    */       
/* 62 */       customRegistry.registerWrenchSupporter(par1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static void updateState() {
/* 69 */     if (Loader.isModLoaded("IC2-Classic")) {
/*    */       
/* 71 */       ic2 = IC2Type.ImmibisClassic;
/*    */       return;
/*    */     } 
/* 74 */     if (Loader.isModLoaded("IC2")) {
/*    */       
/* 76 */       if (Loader.isModLoaded("IC2-Classic-Spmod")) {
/*    */         
/* 78 */         ic2 = IC2Type.SpeigersClassic;
/*    */         return;
/*    */       } 
/* 81 */       ic2 = IC2Type.Experimental;
/*    */     }
/*    */     else {
/*    */       
/* 85 */       ic2 = IC2Type.None;
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum IC2Type
/*    */   {
/* 91 */     NeedLoad,
/* 92 */     Experimental,
/* 93 */     SpeigersClassic,
/* 94 */     ImmibisClassic,
/* 95 */     None;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\info\IC2Classic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */