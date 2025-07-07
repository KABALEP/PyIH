/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Icons;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockIC2Door
/*    */   extends BlockDoor
/*    */ {
/*    */   public Item itemDropped;
/*    */   
/*    */   public BlockIC2Door(Material mat) {
/* 23 */     super(mat);
/* 24 */     func_149649_H();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */   
/*    */   public BlockIC2Door setItemDropped(Item itemid) {
/* 34 */     this.itemDropped = itemid;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int side, int meta) {
/* 40 */     if ((meta & 0x8) == 8)
/*    */     {
/* 42 */       return Ic2Icons.getTexture("b0")[14];
/*    */     }
/* 44 */     return Ic2Icons.getTexture("b0")[15];
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_149673_e(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
/* 49 */     return func_149691_a(par5, par1IBlockAccess.func_72805_g(par2, par3, par4));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int meta, Random p_149650_2_, int p_149650_3_) {
/* 55 */     if ((meta & 0x8) == 8)
/*    */     {
/* 57 */       return null;
/*    */     }
/* 59 */     return this.itemDropped;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 64 */     return "/ic2/sprites/block_0.png";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public Item func_149694_d(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
/* 71 */     return this.itemDropped;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockIC2Door.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */