/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.block.BlockGlass;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTexGlass
/*    */   extends BlockGlass
/*    */ {
/*    */   public int sprite;
/*    */   
/*    */   public BlockTexGlass(int sprite, Material mat, boolean renderAdjacent) {
/* 18 */     super(mat, renderAdjacent);
/* 19 */     this.sprite = sprite;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_149691_a(int par1, int par2) {
/* 26 */     return Ic2Icons.getTexture("b0")[this.sprite];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockTexGlass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */