/*    */ package ic2.core.block;
/*    */ 
/*    */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*    */ import net.minecraft.dispenser.IPosition;
/*    */ import net.minecraft.entity.IProjectile;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BehaviorDynamiteDispense
/*    */   extends BehaviorProjectileDispense
/*    */ {
/*    */   private boolean sticky;
/*    */   
/*    */   public BehaviorDynamiteDispense(boolean sticky) {
/* 14 */     this.sticky = sticky;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IProjectile func_82499_a(World var1, IPosition var2) {
/* 19 */     return new EntityDynamite(var1, var2.func_82615_a(), var2.func_82617_b(), var2.func_82616_c(), this.sticky);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BehaviorDynamiteDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */