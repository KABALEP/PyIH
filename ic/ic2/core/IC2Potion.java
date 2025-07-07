/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ 
/*    */ public class IC2Potion
/*    */   extends Potion
/*    */ {
/*    */   public static IC2Potion radiation;
/*    */   
/*    */   public IC2Potion(int id, boolean badEffect, int liquidColor) {
/* 14 */     super(id, badEffect, liquidColor);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_76394_a(EntityLivingBase entity, int amplifier) {
/* 19 */     if (this.field_76415_H == radiation.field_76415_H)
/*    */     {
/* 21 */       entity.func_70097_a(IC2DamageSource.radiation, (amplifier + 1));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_76397_a(int duration, int amplifier) {
/* 27 */     if (this.field_76415_H == radiation.field_76415_H) {
/*    */       
/* 29 */       int rate = 25 >> amplifier;
/* 30 */       return (duration % rate == 0);
/*    */     } 
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void init() {
/* 37 */     radiation.func_76390_b("potion.radiation.name");
/* 38 */     radiation.func_76399_b(6, 0);
/* 39 */     radiation.func_76404_a(0.25D);
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 44 */     Info.POTION_RADIATION = radiation = new IC2Potion(IC2.radiationPotionId, true, 5149489);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IC2Potion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */