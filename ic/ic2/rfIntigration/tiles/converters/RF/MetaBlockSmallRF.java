/*    */ package ic2.rfIntigration.tiles.converters.RF;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class MetaBlockSmallRF
/*    */   extends BlockConverter.Converter
/*    */ {
/*    */   public MetaBlockSmallRF() {
/* 14 */     super(0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityBlock getNewTile() {
/* 20 */     return (TileEntityBlock)new LowRFConverter();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocizedName() {
/* 26 */     return "tile.smallRFConverter";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Block par1) {
/* 32 */     Ic2Items.smallRF = new ItemStack(par1, 1, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\converters\RF\MetaBlockSmallRF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */