/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFoam
/*     */   extends BlockTex
/*     */   implements IExtraData {
/*     */   public BlockFoam(int sprite) {
/*  23 */     super(sprite, Material.field_151580_n);
/*  24 */     func_149675_a(true);
/*  25 */     func_149711_c(0.01F);
/*  26 */     func_149752_b(10.0F);
/*  27 */     func_149663_c("blockFoam");
/*  28 */     func_149672_a(field_149775_l);
/*  29 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149738_a(World world) {
/*  35 */     return 500;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random r) {
/*  40 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int i, int j, int k) {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149747_d(IBlockAccess world, int x, int y, int z, int side) {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int i, int j, int k, Random random) {
/*  65 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     if (world.func_72957_l(i, j, k) * 6 >= world.field_73012_v.nextInt(1000)) {
/*     */       
/*  71 */       world.func_147465_d(i, j, k, Block.func_149634_a(Ic2Items.constructionFoamWall.func_77973_b()), 7, 3);
/*  72 */       ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, i, j, k);
/*     */     }
/*     */     else {
/*     */       
/*  76 */       world.func_147464_a(i, j, k, this, func_149738_a(world));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/*  82 */     ItemStack cur = entityplayer.func_71045_bC();
/*  83 */     if (cur != null && cur.func_77973_b() == Item.func_150898_a((Block)Blocks.field_150354_m)) {
/*     */       
/*  85 */       world.func_147465_d(i, j, k, Block.func_149634_a(Ic2Items.constructionFoamWall.func_77973_b()), 7, 3);
/*  86 */       ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, i, j, k);
/*  87 */       if (!entityplayer.field_71075_bZ.field_75098_d) {
/*     */         
/*  89 */         ItemStack itemStack = cur;
/*  90 */         itemStack.field_77994_a--;
/*  91 */         if (cur.field_77994_a <= 0)
/*     */         {
/*  93 */           entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/*     */         }
/*     */       } 
/*  96 */       return true;
/*     */     } 
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/* 103 */     Block id = world.func_147439_a(i, j, k);
/* 104 */     return (id == Blocks.field_150350_a || id == Blocks.field_150480_ab || world.func_147437_c(i, j, k) || id.func_149688_o().func_76224_d());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_149644_j(int i) {
/* 109 */     return Ic2Items.constructionFoam.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_149700_E() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 127 */     Ic2Items.constructionFoam = new ItemStack(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockFoam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */