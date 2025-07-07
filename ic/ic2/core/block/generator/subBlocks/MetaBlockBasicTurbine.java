/*    */ package ic2.core.block.generator.subBlocks;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import ic2.core.block.generator.tileentity.TileEntityBasicTurbine;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetaBlockBasicTurbine
/*    */   extends BlockConverter.Converter
/*    */ {
/*    */   public MetaBlockBasicTurbine() {
/* 16 */     super(6);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(Block par1) {
/* 21 */     Ic2Items.basicTurbine = new ItemStack(par1, 1, 6);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityBlock getNewTile() {
/* 27 */     return (TileEntityBlock)new TileEntityBasicTurbine();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocizedName() {
/* 33 */     return "tile.basicTurbine";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\subBlocks\MetaBlockBasicTurbine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */