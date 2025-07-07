/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class WorldGenRubTree
/*     */   extends WorldGenerator
/*     */ {
/*  15 */   public static int maxHeight = 8;
/*     */ 
/*     */   
/*     */   public boolean func_76484_a(World world, Random random, int x, int count, int z) {
/*  19 */     while (count > 0) {
/*     */       int y;
/*     */       
/*  22 */       for (y = IC2.getWorldHeight(world) - 1; world.func_147437_c(x, y - 1, z) && y > 0; y--);
/*     */ 
/*     */       
/*  25 */       if (!grow(world, x, y, z, random))
/*     */       {
/*  27 */         count -= 3;
/*     */       }
/*  29 */       x += random.nextInt(15) - 7;
/*  30 */       z += random.nextInt(15) - 7;
/*  31 */       count--;
/*     */     } 
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean grow(World world, int x, int y, int z, Random random) {
/*  38 */     if (world == null || Ic2Items.rubberWood == null) {
/*     */       
/*  40 */       System.out.println("[ERROR] Had a null that shouldn't have been. RubberTree did not spawn! w=" + world + " r=" + Ic2Items.rubberWood);
/*  41 */       return false;
/*     */     } 
/*  43 */     int treeholechance = 25;
/*  44 */     int h = getGrowHeight(world, x, y, z);
/*  45 */     if (h < 2)
/*     */     {
/*  47 */       return false;
/*     */     }
/*  49 */     int height = h / 2;
/*  50 */     h -= h / 2;
/*  51 */     height += random.nextInt(h + 1); int i;
/*  52 */     for (i = 0; i < height; i++) {
/*     */       
/*  54 */       world.func_147449_b(x, y + i, z, Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()));
/*  55 */       if (random.nextInt(100) <= treeholechance) {
/*     */         
/*  57 */         treeholechance -= 10;
/*  58 */         world.func_72921_c(x, y + i, z, random.nextInt(4) + 2, 0);
/*     */       }
/*     */       else {
/*     */         
/*  62 */         world.func_72921_c(x, y + i, z, 1, 0);
/*     */       } 
/*  64 */       ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, x, y + i, z);
/*  65 */       if (height < 4 || (height < 7 && i > 1) || i > 2)
/*     */       {
/*  67 */         for (int a = x - 2; a <= x + 2; a++) {
/*     */           
/*  69 */           for (int b = z - 2; b <= z + 2; b++) {
/*     */             
/*  71 */             int c = i + 4 - height;
/*  72 */             if (c < 1)
/*     */             {
/*  74 */               c = 1;
/*     */             }
/*  76 */             boolean gen = ((a > x - 2 && a < x + 2 && b > z - 2 && b < z + 2) || (a > x - 2 && a < x + 2 && random.nextInt(c) == 0) || (b > z - 2 && b < z + 2 && random.nextInt(c) == 0));
/*  77 */             if (gen && world.func_147437_c(a, y + i, b))
/*     */             {
/*  79 */               world.func_147449_b(a, y + i, b, Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*  85 */     for (i = 0; i <= height / 4 + random.nextInt(2); i++) {
/*     */       
/*  87 */       if (world.func_147437_c(x, y + height + i, z))
/*     */       {
/*  89 */         world.func_147449_b(x, y + height + i, z, Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()));
/*     */       }
/*     */     } 
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrowHeight(World world, int x, int y, int z) {
/*  97 */     if ((world.func_147439_a(x, y - 1, z) != Blocks.field_150349_c && world.func_147439_a(x, y - 1, z) != Blocks.field_150346_d) || (!world.func_147437_c(x, y, z) && world.func_147439_a(x, y, z) != Block.func_149634_a(Ic2Items.rubberSapling.func_77973_b())))
/*     */     {
/*  99 */       return 0;
/*     */     }
/*     */     int height;
/* 102 */     for (height = 1; world.func_147437_c(x, y + 1, z) && height < 8; ) { height++; y++; }
/*     */ 
/*     */     
/* 105 */     return height;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\WorldGenRubTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */