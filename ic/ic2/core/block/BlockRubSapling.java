/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockRubSapling
/*     */   extends BlockSapling
/*     */   implements IExtraData
/*     */ {
/*     */   public BlockRubSapling() {
/*  29 */     func_149711_c(0.0F);
/*  30 */     func_149672_a(Block.field_149779_h);
/*  31 */     func_149663_c("blockRubSapling");
/*  32 */     func_149647_a((CreativeTabs)IC2.tabIC2);
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
/*     */   public IIcon func_149691_a(int par1, int par2) {
/*  45 */     return Ic2Icons.getTexture("b0")[38];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int i, int j, int k, Random random) {
/*  51 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     if (!func_149718_j(world, i, j, k)) {
/*     */       
/*  57 */       func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
/*  58 */       world.func_147468_f(i, j, k);
/*     */       return;
/*     */     } 
/*  61 */     if (world.func_72957_l(i, j + 1, k) >= 9 && random.nextInt(30) == 0)
/*     */     {
/*  63 */       growTree(world, i, j, k, random);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149878_d(World world, int i, int j, int k, Random random) {
/*  70 */     growTree(world, i, j, k, random);
/*     */   }
/*     */ 
/*     */   
/*     */   public void growTree(World world, int i, int j, int k, Random random) {
/*  75 */     (new WorldGenRubTree()).grow(world, i, j, k, random);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int i) {
/*  81 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/*  87 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  89 */       return false;
/*     */     }
/*  91 */     ItemStack equipped = entityplayer.func_71045_bC();
/*  92 */     if (equipped == null)
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     if (equipped.func_77973_b() == Items.field_151100_aR && equipped.func_77960_j() == 15) {
/*     */       
/*  98 */       growTree(world, i, j, k, world.field_73012_v);
/*  99 */       if (!entityplayer.field_71075_bZ.field_75098_d) {
/*     */         
/* 101 */         ItemStack itemStack = equipped;
/* 102 */         itemStack.field_77994_a--;
/*     */       } 
/* 104 */       entityplayer.func_71038_i();
/*     */     } 
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
/* 112 */     par3List.add(new ItemStack(par1, 1, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 118 */     Ic2Items.rubberSapling = new ItemStack((Block)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockRubSapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */