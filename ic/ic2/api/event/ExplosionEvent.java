/*    */ package ic2.api.event;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ @Cancelable
/*    */ public class ExplosionEvent
/*    */   extends WorldEvent {
/*    */   public final Entity entity;
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   
/*    */   public ExplosionEvent(World world, Entity entity, double x, double y, double z, double power, EntityLivingBase igniter, int radiationRange, double rangeLimit) {
/* 18 */     super(world);
/*    */     
/* 20 */     this.entity = entity;
/* 21 */     this.x = x;
/* 22 */     this.y = y;
/* 23 */     this.z = z;
/* 24 */     this.power = power;
/* 25 */     this.igniter = igniter;
/* 26 */     this.radiationRange = radiationRange;
/* 27 */     this.rangeLimit = rangeLimit;
/*    */   }
/*    */   
/*    */   public double power;
/*    */   public final EntityLivingBase igniter;
/*    */   public final int radiationRange;
/*    */   public final double rangeLimit;
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\ExplosionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */