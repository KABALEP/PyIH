/*    */ package ic2.core;
/*    */ 
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IC2DamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   public IC2DamageSource(String s) {
/* 13 */     super(s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public static IC2DamageSource electricity = new IC2DamageSource("electricity");
/* 19 */   public static IC2DamageSource nuke = new IC2DamageSource("nuke");
/* 20 */   public static IC2DamageSource radiation = (IC2DamageSource)(new IC2DamageSource("radiation")).func_76348_h();
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IC2DamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */