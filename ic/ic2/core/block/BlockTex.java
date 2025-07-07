/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTex
/*    */   extends Block
/*    */ {
/*    */   int blockIndexInTexture;
/*    */   
/*    */   public BlockTex(Material mat) {
/* 26 */     super(mat);
/* 27 */     this.blockIndexInTexture = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockTex(int sprite, Material mat) {
/* 32 */     this(mat);
/* 33 */     this.blockIndexInTexture = sprite;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_149691_a(int par1, int par2) {
/* 46 */     return Ic2Icons.getTexture("b0")[this.blockIndexInTexture];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 54 */     if (Ic2Items.uraniumOre != null && this == Block.func_149634_a(Ic2Items.uraniumOre.func_77973_b()))
/*    */     {
/* 56 */       return Ic2Items.uraniumDrop.func_77973_b();
/*    */     }
/* 58 */     return super.func_149650_a(p_149650_1_, p_149650_2_, p_149650_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(int meta, int fortune, Random random) {
/* 64 */     if (Ic2Items.uraniumOre != null && this == Block.func_149634_a(Ic2Items.uraniumOre.func_77973_b())) {
/*    */       
/* 66 */       int j = random.nextInt(fortune + 2) - 1;
/* 67 */       if (j < 0)
/*    */       {
/* 69 */         j = 0;
/*    */       }
/* 71 */       return 1 + j;
/*    */     } 
/* 73 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149690_a(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
/* 79 */     super.func_149690_a(par1World, par2, par3, par4, par5, par6, par7);
/* 80 */     if (Ic2Items.uraniumOre != null && this == Block.func_149634_a(Ic2Items.uraniumOre.func_77973_b()))
/*    */     {
/* 82 */       func_149657_c(par1World, par2, par3, par4, MathHelper.func_76136_a(par1World.field_73012_v, 1, 3));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockTex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */