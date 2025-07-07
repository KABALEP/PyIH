/*    */ package ic2.core.util;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.Optional.Method;
/*    */ import forestry.api.farming.Farmables;
/*    */ import forestry.api.farming.IFarmable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class AddonModifier
/*    */ {
/* 14 */   private static AddonModifier mod = new AddonModifier();
/*    */   
/*    */   public static void modify() {
/* 17 */     if (Loader.isModLoaded("Forestry"))
/*    */     {
/* 19 */       mod.modifyForestry();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Method(modid = "Forestry")
/*    */   public void modifyForestry() {
/* 26 */     Collection<IFarmable> farms = (Collection<IFarmable>)Farmables.farmables.get("farmOrchard");
/* 27 */     Iterator<IFarmable> checker = (new ArrayList<>(farms)).iterator();
/* 28 */     while (checker.hasNext()) {
/*    */       
/* 30 */       IFarmable farm = checker.next();
/* 31 */       if (farm == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 35 */       Class<?> clz = farm.getClass();
/* 36 */       if (clz.getSimpleName().equals("FarmableBasicIC2Crop"))
/*    */       {
/* 38 */         farms.remove(farm);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\AddonModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */