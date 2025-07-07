/*    */ package ic2.api.event;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ public class FoamEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   
/*    */   public FoamEvent(World world, int xCoord, int yCoord, int zCoord) {
/* 15 */     super(world);
/* 16 */     this.x = xCoord;
/* 17 */     this.y = yCoord;
/* 18 */     this.z = zCoord;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Cancelable
/*    */   public static class Check
/*    */     extends FoamEvent
/*    */   {
/*    */     public Check(World world, int xCoord, int yCoord, int zCoord) {
/* 32 */       super(world, xCoord, yCoord, zCoord);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Cancelable
/*    */   public static class Foam
/*    */     extends FoamEvent
/*    */   {
/*    */     public Foam(World world, int xCoord, int yCoord, int zCoord) {
/* 47 */       super(world, xCoord, yCoord, zCoord);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\FoamEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */