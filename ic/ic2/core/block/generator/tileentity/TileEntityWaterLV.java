/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import com.google.common.math.DoubleMath;
/*    */ import ic2.core.util.AabbUtil;
/*    */ import java.math.RoundingMode;
/*    */ import java.util.Arrays;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityWaterLV
/*    */   extends TileEntityWaterGenerator
/*    */ {
/*    */   public TileEntityWaterLV() {
/* 16 */     super(4);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateWaterCount() {
/* 22 */     this.water = DoubleMath.roundToInt(7.5D * AabbUtil.getBlockCount(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, filter, true, false, Arrays.asList(ForgeDirection.VALID_DIRECTIONS)), RoundingMode.DOWN);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityWaterLV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */