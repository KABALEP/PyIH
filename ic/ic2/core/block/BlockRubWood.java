/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockRubWood
/*     */   extends BlockTex
/*     */   implements IExtraData
/*     */ {
/*     */   public BlockRubWood() {
/*  23 */     super(44, Material.field_151575_d);
/*  24 */     func_149675_a(true);
/*  25 */     func_149711_c(1.0F);
/*  26 */     func_149672_a(Block.field_149766_f);
/*  27 */     func_149663_c("blockRubWood");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/*  33 */     int meta = iblockaccess.func_72805_g(i, j, k);
/*  34 */     if (side < 2)
/*     */     {
/*  36 */       return Ic2Icons.getTexture("b0")[47];
/*     */     }
/*  38 */     if (side != meta % 6)
/*     */     {
/*  40 */       return Ic2Icons.getTexture("b0")[44];
/*     */     }
/*  42 */     if (meta > 5)
/*     */     {
/*  44 */       return Ic2Icons.getTexture("b0")[46];
/*     */     }
/*  46 */     return Ic2Icons.getTexture("b0")[45];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  52 */     if (side < 2)
/*     */     {
/*  54 */       return Ic2Icons.getTexture("b0")[47];
/*     */     }
/*  56 */     return Ic2Icons.getTexture("b0")[44];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  62 */     ArrayList<ItemStack> list = super.getDrops(world, x, y, z, metadata, fortune);
/*  63 */     for (int i = 0; i < fortune + 1; i++) {
/*     */       
/*  65 */       if (metadata != 0 && world.field_73012_v.nextInt(6) == 0)
/*     */       {
/*  67 */         list.add(new ItemStack(Ic2Items.resin.func_77973_b()));
/*     */       }
/*     */     } 
/*  70 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int i, int j, int k, Block a, int b) {
/*  76 */     byte byte0 = 4;
/*  77 */     int l = byte0 + 1;
/*  78 */     if (world.func_72904_c(i - l, j - l, k - l, i + l, j + l, k + l))
/*     */     {
/*  80 */       for (int i2 = -byte0; i2 <= byte0; i2++) {
/*     */         
/*  82 */         for (int j2 = -byte0; j2 <= byte0; j2++) {
/*     */           
/*  84 */           for (int k2 = -byte0; k2 <= byte0; k2++) {
/*     */             
/*  86 */             Block l2 = world.func_147439_a(i + i2, j + j2, k + k2);
/*  87 */             if (l2 == Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b())) {
/*     */               
/*  89 */               int i3 = world.func_72805_g(i + i2, j + j2, k + k2);
/*  90 */               if ((i3 & 0x8) == 0)
/*     */               {
/*  92 */                 world.func_72921_c(i + i2, j + j2, k + k2, i3 | 0x8, 0);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int x, int y, int z, Random random) {
/* 104 */     int meta = world.func_72805_g(x, y, z);
/* 105 */     if (meta < 6) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     if (random.nextInt(200) == 0) {
/*     */       
/* 111 */       world.func_72921_c(x, y, z, meta % 6, 0);
/* 112 */       ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, x, y, z);
/*     */     }
/*     */     else {
/*     */       
/* 116 */       world.func_147464_a(x, y, z, this, func_149738_a(world));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149738_a(World world) {
/* 123 */     return 100;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149656_h() {
/* 129 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 141 */     Ic2Items.rubberWood = new ItemStack(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockRubWood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */