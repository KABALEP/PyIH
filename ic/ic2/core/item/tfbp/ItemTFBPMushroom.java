/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.BlockMushroom;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class ItemTFBPMushroom
/*     */   extends ItemTFBP {
/*     */   public ItemTFBPMushroom(int index) {
/*  15 */     super(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConsume(ItemStack item) {
/*  21 */     return 8000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack item) {
/*  27 */     return 25;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/*  33 */     int y = TileEntityTerra.getFirstSolidBlockFrom(world, x, z, yCoord + 20);
/*  34 */     return (y != -1 && growBlockWithDependancy(world, x, y, z, Blocks.field_150420_aW, (Block)Blocks.field_150338_P));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean growBlockWithDependancy(World world, int x, int y, int z, Block id, Block dependancy) {
/*  39 */     for (int xm = x - 1; dependancy != Blocks.field_150350_a && xm < x + 1; xm++) {
/*     */       
/*  41 */       for (int zm = z - 1; zm < z + 1; zm++) {
/*     */         
/*  43 */         for (int ym = y + 5; ym > y - 2; ym--) {
/*     */           
/*  45 */           Block block = world.func_147439_a(xm, ym, zm);
/*  46 */           if (dependancy == Blocks.field_150391_bh) {
/*     */             
/*  48 */             if (block == dependancy || block == Blocks.field_150420_aW) {
/*     */               break;
/*     */             }
/*     */             
/*  52 */             if (block == Blocks.field_150419_aX) {
/*     */               break;
/*     */             }
/*     */             
/*  56 */             if (world.func_147437_c(xm, ym, zm)) {
/*     */               continue;
/*     */             }
/*     */             
/*  60 */             if (block == Blocks.field_150346_d || block == Blocks.field_150349_c) {
/*     */               
/*  62 */               world.func_147449_b(xm, ym, zm, dependancy);
/*  63 */               TileEntityTerra.setBiomeAt(world, x, z, BiomeGenBase.field_76789_p);
/*  64 */               return true;
/*     */             } 
/*     */           } 
/*  67 */           if (dependancy == Blocks.field_150338_P) {
/*     */             
/*  69 */             if (block == Blocks.field_150338_P) {
/*     */               break;
/*     */             }
/*     */             
/*  73 */             if (block == Blocks.field_150337_Q) {
/*     */               break;
/*     */             }
/*     */             
/*  77 */             if (!world.func_147437_c(xm, ym, zm) && growBlockWithDependancy(world, xm, ym, zm, (Block)Blocks.field_150338_P, (Block)Blocks.field_150391_bh))
/*     */             {
/*  79 */               return true; } 
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/*  85 */     if (id != Blocks.field_150338_P) {
/*     */       
/*  87 */       if (id == Blocks.field_150420_aW) {
/*     */         
/*  89 */         Block block = world.func_147439_a(x, y + 1, z);
/*  90 */         if (block != Blocks.field_150338_P && block != Blocks.field_150337_Q)
/*     */         {
/*  92 */           return false;
/*     */         }
/*  94 */         if (((BlockMushroom)block).func_149884_c(world, x, y + 1, z, world.field_73012_v)) {
/*     */           
/*  96 */           for (int xm2 = x - 1; xm2 < x + 1; xm2++) {
/*     */             
/*  98 */             for (int zm2 = z - 1; zm2 < z + 1; zm2++) {
/*     */               
/* 100 */               Block block1 = world.func_147439_a(xm2, y + 1, zm2);
/* 101 */               if (block1 == Blocks.field_150338_P || block1 == Blocks.field_150337_Q)
/*     */               {
/* 103 */                 world.func_147449_b(xm2, y + 1, zm2, Blocks.field_150350_a);
/*     */               }
/*     */             } 
/*     */           } 
/* 107 */           return true;
/*     */         } 
/*     */       } 
/* 110 */       return false;
/*     */     } 
/* 112 */     Block base = world.func_147439_a(x, y, z);
/* 113 */     if (base != Blocks.field_150391_bh) {
/*     */       
/* 115 */       if (base != Blocks.field_150420_aW && base != Blocks.field_150419_aX)
/*     */       {
/* 117 */         return false;
/*     */       }
/* 119 */       world.func_147449_b(x, y, z, (Block)Blocks.field_150391_bh);
/*     */     } 
/* 121 */     Block above = world.func_147439_a(x, y + 1, z);
/* 122 */     if (above != null && above != Blocks.field_150329_H)
/*     */     {
/* 124 */       return false;
/*     */     }
/* 126 */     BlockBush blockBush = Blocks.field_150338_P;
/* 127 */     if (world.field_73012_v.nextBoolean())
/*     */     {
/* 129 */       blockBush = Blocks.field_150337_Q;
/*     */     }
/* 131 */     world.func_147449_b(x, y + 1, z, (Block)blockBush);
/* 132 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */