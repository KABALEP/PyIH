/*    */ package ic2.api.event;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RetextureEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   public final int x;
/*    */   public final int y;
/*    */   public final int z;
/*    */   public final int side;
/*    */   public final Block referencedBlock;
/*    */   public final int referencedMeta;
/*    */   public final int referencedSide;
/*    */   public boolean applied = false;
/*    */   
/*    */   public RetextureEvent(World world1, int x1, int y1, int z1, int side1, Block referencedBlock, int referencedMeta1, int referencedSide1) {
/* 27 */     super(world1);
/*    */     
/* 29 */     this.x = x1;
/* 30 */     this.y = y1;
/* 31 */     this.z = z1;
/* 32 */     this.side = side1;
/* 33 */     this.referencedBlock = referencedBlock;
/* 34 */     this.referencedMeta = referencedMeta1;
/* 35 */     this.referencedSide = referencedSide1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\RetextureEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */