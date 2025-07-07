/*    */ package ic2.rfIntigration.tiles.converters.EU;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class MetaBlockHighEU
/*    */   extends BlockConverter.Converter
/*    */ {
/*    */   public MetaBlockHighEU() {
/* 13 */     super(5);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityBlock getNewTile() {
/* 19 */     return (TileEntityBlock)new HighEUConverter();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocizedName() {
/* 25 */     return "tile.highEUConverter";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Block par1) {
/* 31 */     Ic2Items.highEU = new ItemStack(par1, 1, 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\converters\EU\MetaBlockHighEU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */