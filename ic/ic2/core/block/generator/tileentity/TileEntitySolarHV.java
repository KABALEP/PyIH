/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import net.minecraft.util.StatCollector;
/*    */ 
/*    */ public class TileEntitySolarHV
/*    */   extends TileEntitySolarGenerator
/*    */ {
/*    */   public TileEntitySolarHV() {
/*  9 */     super(1, 512, 2048);
/* 10 */     this.tier = 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 16 */     return StatCollector.func_74838_a("blockSolarHV.name");
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntitySolarHV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */