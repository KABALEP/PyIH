/*    */ package ic2.rfIntigration.tiles.converters.RF;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class MetaBlockMedRF
/*    */   extends BlockConverter.Converter
/*    */ {
/*    */   public MetaBlockMedRF() {
/* 14 */     super(1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityBlock getNewTile() {
/* 20 */     return (TileEntityBlock)new MediumRFConverter();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocizedName() {
/* 26 */     return "tile.medRFConverter";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Block par1) {
/* 32 */     Ic2Items.medRF = new ItemStack(par1, 1, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\converters\RF\MetaBlockMedRF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */