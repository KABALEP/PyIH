/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class EntityChargePadAuraFX
/*    */   extends EntityFX {
/*    */   private boolean typeSwirl;
/*    */   
/*    */   public EntityChargePadAuraFX(World world, double x, double y, double z, int maxAge, double[] velocity, float[] colour, boolean swirl) {
/* 14 */     super(world, x, y, z, velocity[0], velocity[1], velocity[2]);
/* 15 */     this.typeSwirl = swirl;
/* 16 */     this.field_70552_h = colour[0];
/* 17 */     this.field_70553_i = colour[1];
/* 18 */     this.field_70551_j = colour[2];
/* 19 */     func_70536_a(0);
/* 20 */     func_70105_a(0.02F, 0.02F);
/* 21 */     this.field_70544_f *= this.field_70146_Z.nextFloat() * 0.5F + 0.5F;
/* 22 */     this.field_70181_x *= 0.2D;
/* 23 */     this.field_70547_e = (int)(maxAge / (Math.random() * 0.8D + 0.2D));
/* 24 */     this.field_70145_X = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 30 */     this.field_70169_q = this.field_70165_t;
/* 31 */     this.field_70167_r = this.field_70163_u;
/* 32 */     this.field_70166_s = this.field_70161_v;
/* 33 */     this.field_70165_t += this.field_70159_w;
/* 34 */     this.field_70163_u += this.field_70181_x;
/* 35 */     this.field_70161_v += this.field_70179_y;
/* 36 */     this.field_70159_w *= 0.99D;
/* 37 */     this.field_70181_x *= 0.99D;
/* 38 */     this.field_70179_y *= 0.99D;
/* 39 */     if (this.field_70547_e-- <= 0)
/*    */     {
/* 41 */       func_70106_y();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\EntityChargePadAuraFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */