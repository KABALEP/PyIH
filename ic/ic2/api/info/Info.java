/*    */ package ic2.api.info;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.LoaderState;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Info
/*    */ {
/*    */   public static IEnergyValueProvider itemEnergy;
/*    */   public static IFuelValueProvider itemFuel;
/*    */   public static Object ic2ModInstance;
/*    */   public static DamageSource DMG_ELECTRIC;
/*    */   public static DamageSource DMG_NUKE_EXPLOSION;
/*    */   public static DamageSource DMG_RADIATION;
/*    */   public static Potion POTION_RADIATION;
/*    */   
/*    */   public static boolean isIc2Available() {
/* 27 */     if (ic2Available != null) return ic2Available.booleanValue();
/*    */     
/* 29 */     boolean loaded = Loader.isModLoaded("IC2");
/*    */     
/* 31 */     if (Loader.instance().hasReachedState(LoaderState.CONSTRUCTING)) {
/* 32 */       ic2Available = Boolean.valueOf(loaded);
/*    */     }
/*    */     
/* 35 */     return loaded;
/*    */   }
/*    */   
/* 38 */   private static Boolean ic2Available = null;
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\info\Info.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */