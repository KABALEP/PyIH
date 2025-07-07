/*    */ package ic2.rfIntigration.tiles.converters.RF;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class MetBlockHighRF
/*    */   extends BlockConverter.Converter
/*    */ {
/*    */   public MetBlockHighRF() {
/* 14 */     super(2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityBlock getNewTile() {
/* 20 */     return (TileEntityBlock)new HighRFConverter();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocizedName() {
/* 26 */     return "tile.HighRFConverter";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Block par1) {
/* 32 */     Ic2Items.highRF = new ItemStack(par1, 1, 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\converters\RF\MetBlockHighRF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */