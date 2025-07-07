/*    */ package ic2.core.block;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityStickyDynamite
/*    */   extends EntityDynamite
/*    */ {
/*    */   public EntityStickyDynamite(World world) {
/* 10 */     super(world, 0.0D, 0.0D, 0.0D);
/* 11 */     this.sticky = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityStickyDynamite(World world, EntityLivingBase entityliving) {
/* 16 */     super(world, entityliving);
/* 17 */     this.sticky = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\EntityStickyDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */