/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.IShearable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockRubLeaves
/*     */   extends BlockLeaves
/*     */   implements IShearable, IExtraData
/*     */ {
/*     */   int[] b;
/*     */   
/*     */   public BlockRubLeaves() {
/*  33 */     func_149675_a(true);
/*  34 */     func_149711_c(0.2F);
/*  35 */     func_149713_g(1);
/*  36 */     func_149672_a(Block.field_149779_h);
/*  37 */     func_149663_c("leaves");
/*  38 */     func_149649_H();
/*  39 */     this.field_150121_P = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int par1, int par2) {
/*  46 */     return Blocks.field_150362_t.func_149691_a(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149741_i(int i) {
/*  59 */     return ColorizerFoliage.func_77469_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149720_d(IBlockAccess iblockaccess, int i, int j, int k) {
/*  66 */     return ColorizerFoliage.func_77469_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/*  72 */     return (random.nextInt(35) == 0) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] func_150125_e() {
/*  80 */     return new String[] { "rubberLeaves" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/*  86 */     return Ic2Items.rubberSapling.func_77973_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int i) {
/*  92 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_149646_a(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
/*  99 */     return (!par1IBlockAccess.func_147439_a(par2, par3, par4).isNormalCube(par1IBlockAccess, par2, par3, par4) || (par1IBlockAccess.func_147439_a(par2, par3, par4) == this && this.field_150121_P));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149690_a(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
/* 105 */     if (!par1World.field_72995_K && par1World.field_73012_v.nextInt(35) == 0) {
/*     */       
/* 107 */       Item var9 = func_149650_a(par5, par1World.field_73012_v, par7);
/* 108 */       func_149642_a(par1World, par2, par3, par4, new ItemStack(var9, 1, func_149692_a(par5)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
/* 121 */     ArrayList<ItemStack> ret = new ArrayList();
/* 122 */     ret.add(new ItemStack((Block)this, 1, world.func_72805_g(x, y, z) & 0x3));
/* 123 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
/* 129 */     par3List.add(new ItemStack(par1, 1, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 135 */     Ic2Items.rubberLeaves = new ItemStack((Block)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockRubLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */