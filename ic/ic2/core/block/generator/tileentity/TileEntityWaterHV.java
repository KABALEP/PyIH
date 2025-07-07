/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import ic2.core.util.AabbUtil;
/*    */ import java.util.Arrays;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityWaterHV
/*    */   extends TileEntityWaterGenerator
/*    */ {
/*    */   public TileEntityWaterHV() {
/* 13 */     super(250);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateWaterCount() {
/* 19 */     this.water = 40 * AabbUtil.getBlockCount(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, 2, filter, true, false, Arrays.asList(ForgeDirection.VALID_DIRECTIONS));
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityWaterHV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */