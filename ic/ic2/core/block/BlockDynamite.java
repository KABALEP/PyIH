/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockDynamite
/*     */   extends BlockTex
/*     */ {
/*     */   public int sprite;
/*     */   
/*     */   public BlockDynamite(int sprite) {
/*  27 */     super(Material.field_151590_u);
/*  28 */     this.sprite = sprite;
/*  29 */     func_149675_a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/*  34 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  49 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/*  54 */     return (world.func_147445_c(i - 1, j, k, false) || world.func_147445_c(i + 1, j, k, false) || world.func_147445_c(i, j, k - 1, false) || world.func_147445_c(i, j, k + 1, false) || world.func_147445_c(i, j - 1, k, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149714_e(World world, int i, int j, int k, int l) {
/*  59 */     int i2 = world.func_72805_g(i, j, k);
/*  60 */     if (l == 1 && world.func_147445_c(i, j - 1, k, false))
/*     */     {
/*  62 */       i2 = 5;
/*     */     }
/*  64 */     if (l == 2 && world.func_147445_c(i, j, k + 1, false))
/*     */     {
/*  66 */       i2 = 4;
/*     */     }
/*  68 */     if (l == 3 && world.func_147445_c(i, j, k - 1, false))
/*     */     {
/*  70 */       i2 = 3;
/*     */     }
/*  72 */     if (l == 4 && world.func_147445_c(i + 1, j, k, false))
/*     */     {
/*  74 */       i2 = 2;
/*     */     }
/*  76 */     if (l == 5 && world.func_147445_c(i - 1, j, k, false))
/*     */     {
/*  78 */       i2 = 1;
/*     */     }
/*  80 */     world.func_72921_c(i, j, k, i2, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int i, int j, int k, Random random) {
/*  85 */     super.func_149674_a(world, i, j, k, random);
/*  86 */     if (world.func_72805_g(i, j, k) == 0)
/*     */     {
/*  88 */       func_149726_b(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149726_b(World world, int i, int j, int k) {
/*  94 */     if (world.func_72864_z(i, j, k)) {
/*     */       
/*  96 */       func_149664_b(world, i, j, k, 1);
/*  97 */       world.func_147468_f(i, j, k);
/*     */       return;
/*     */     } 
/* 100 */     if (world.func_147445_c(i, j - 1, k, false)) {
/*     */       
/* 102 */       world.func_72921_c(i, j, k, 5, 3);
/*     */     }
/* 104 */     else if (world.func_147445_c(i - 1, j, k, false)) {
/*     */       
/* 106 */       world.func_72921_c(i, j, k, 1, 3);
/*     */     }
/* 108 */     else if (world.func_147445_c(i + 1, j, k, false)) {
/*     */       
/* 110 */       world.func_72921_c(i, j, k, 2, 3);
/*     */     }
/* 112 */     else if (world.func_147445_c(i, j, k - 1, false)) {
/*     */       
/* 114 */       world.func_72921_c(i, j, k, 3, 3);
/*     */     }
/* 116 */     else if (world.func_147445_c(i, j, k + 1, false)) {
/*     */       
/* 118 */       world.func_72921_c(i, j, k, 4, 3);
/*     */     } 
/* 120 */     dropBlockIfCantStay(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 132 */     return Ic2Items.dynamite.func_77973_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World world, int x, int y, int z) {
/* 137 */     EntityDynamite entitytntprimed = new EntityDynamite(world, (x + 0.5F), (y + 0.5F), (z + 0.5F));
/* 138 */     entitytntprimed.fuse = 5;
/* 139 */     world.func_72838_d(entitytntprimed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149664_b(World world, int x, int y, int z, int l) {
/* 144 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/* 148 */     EntityDynamite entitytntprimed = new EntityDynamite(world, (x + 0.5F), (y + 0.5F), (z + 0.5F));
/* 149 */     entitytntprimed.fuse = 40;
/* 150 */     world.func_72838_d(entitytntprimed);
/* 151 */     world.func_72956_a(entitytntprimed, "random.fuse", 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/* 156 */     if (l != null && l.func_149744_f() && world.func_72864_z(i, j, k)) {
/*     */       
/* 158 */       func_149664_b(world, i, j, k, 1);
/* 159 */       world.func_147468_f(i, j, k);
/*     */       return;
/*     */     } 
/* 162 */     if (dropBlockIfCantStay(world, i, j, k)) {
/*     */       
/* 164 */       int i2 = world.func_72805_g(i, j, k);
/* 165 */       boolean flag = false;
/* 166 */       if (!world.func_147445_c(i - 1, j, k, false) && i2 == 1)
/*     */       {
/* 168 */         flag = true;
/*     */       }
/* 170 */       if (!world.func_147445_c(i + 1, j, k, false) && i2 == 2)
/*     */       {
/* 172 */         flag = true;
/*     */       }
/* 174 */       if (!world.func_147445_c(i, j, k - 1, false) && i2 == 3)
/*     */       {
/* 176 */         flag = true;
/*     */       }
/* 178 */       if (!world.func_147445_c(i, j, k + 1, false) && i2 == 4)
/*     */       {
/* 180 */         flag = true;
/*     */       }
/* 182 */       if (!world.func_147445_c(i, j - 1, k, false) && i2 == 5)
/*     */       {
/* 184 */         flag = true;
/*     */       }
/* 186 */       if (flag) {
/*     */         
/* 188 */         func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
/* 189 */         world.func_147468_f(i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dropBlockIfCantStay(World world, int i, int j, int k) {
/* 196 */     if (!func_149742_c(world, i, j, k)) {
/*     */       
/* 198 */       onBlockDestroyedByExplosion(world, i, j, k);
/* 199 */       world.func_147468_f(i, j, k);
/* 200 */       return false;
/*     */     } 
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition func_149731_a(World world, int i, int j, int k, Vec3 vec31, Vec3 Vec31) {
/* 207 */     int l = world.func_72805_g(i, j, k) & 0x7;
/* 208 */     float f = 0.15F;
/* 209 */     if (l == 1) {
/*     */       
/* 211 */       func_149676_a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */     }
/* 213 */     else if (l == 2) {
/*     */       
/* 215 */       func_149676_a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */     }
/* 217 */     else if (l == 3) {
/*     */       
/* 219 */       func_149676_a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */     }
/* 221 */     else if (l == 4) {
/*     */       
/* 223 */       func_149676_a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 227 */       float f2 = 0.1F;
/* 228 */       func_149676_a(0.5F - f2, 0.0F, 0.5F - f2, 0.5F + f2, 0.6F, 0.5F + f2);
/*     */     } 
/* 230 */     return super.func_149731_a(world, i, j, k, vec31, Vec31);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int par1, int par2) {
/* 237 */     return Ic2Icons.getTexture("b0")[this.sprite];
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */