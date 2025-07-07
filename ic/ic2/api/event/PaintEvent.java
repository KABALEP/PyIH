/*    */ package ic2.api.event;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class PaintEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   public final int x;
/*    */   public final int y;
/*    */   public final int z;
/*    */   public final int side;
/*    */   public final int color;
/*    */   public boolean painted = false;
/*    */   
/*    */   public PaintEvent(World world1, int x1, int y1, int z1, int side1, int color1) {
/* 24 */     super(world1);
/*    */     
/* 26 */     this.x = x1;
/* 27 */     this.y = y1;
/* 28 */     this.z = z1;
/* 29 */     this.side = side1;
/* 30 */     this.color = color1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\PaintEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */