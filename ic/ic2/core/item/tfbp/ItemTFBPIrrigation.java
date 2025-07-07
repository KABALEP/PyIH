/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockRubSapling;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemTFBPIrrigation
/*     */   extends ItemTFBP
/*     */ {
/*     */   public ItemTFBPIrrigation(int index) {
/*  16 */     super(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConsume(ItemStack item) {
/*  22 */     return 3000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack item) {
/*  28 */     return 60;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/*  34 */     if (world.field_73012_v.nextInt(48000) == 0) {
/*     */       
/*  36 */       world.func_72912_H().func_76084_b(true);
/*  37 */       return true;
/*     */     } 
/*  39 */     int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 10);
/*  40 */     if (y == -1)
/*     */     {
/*  42 */       return false;
/*     */     }
/*  44 */     if (TileEntityTerra.switchGround(world, (Block)Blocks.field_150354_m, Blocks.field_150346_d, x, y, z, true)) {
/*     */       
/*  46 */       TileEntityTerra.switchGround(world, (Block)Blocks.field_150354_m, Blocks.field_150346_d, x, y, z, true);
/*  47 */       return true;
/*     */     } 
/*  49 */     Block id = world.func_147439_a(x, y, z);
/*  50 */     if (id == Blocks.field_150329_H)
/*     */     {
/*  52 */       return (spreadGrass(world, x + 1, y, z) || spreadGrass(world, x - 1, y, z) || spreadGrass(world, x, y, z + 1) || spreadGrass(world, x, y, z - 1));
/*     */     }
/*  54 */     if (id == Blocks.field_150345_g) {
/*     */       
/*  56 */       ((BlockSapling)Blocks.field_150345_g).func_149878_d(world, x, y, z, world.field_73012_v);
/*  57 */       return true;
/*     */     } 
/*  59 */     if (id == Block.func_149634_a(Ic2Items.rubberSapling.func_77973_b())) {
/*     */       
/*  61 */       ((BlockRubSapling)Block.func_149634_a(Ic2Items.rubberSapling.func_77973_b())).growTree(world, x, y, z, world.field_73012_v);
/*  62 */       return true;
/*     */     } 
/*  64 */     if (id == Blocks.field_150364_r) {
/*     */       
/*  66 */       int meta = world.func_72805_g(x, y, z);
/*  67 */       world.func_147465_d(x, y + 1, z, Blocks.field_150364_r, meta, 3);
/*  68 */       createLeaves(world, x, y + 2, z, meta);
/*  69 */       createLeaves(world, x + 1, y + 1, z, meta);
/*  70 */       createLeaves(world, x - 1, y + 1, z, meta);
/*  71 */       createLeaves(world, x, y + 1, z + 1, meta);
/*  72 */       createLeaves(world, x, y + 1, z - 1, meta);
/*  73 */       return true;
/*     */     } 
/*  75 */     if (id == Blocks.field_150464_aj) {
/*     */       
/*  77 */       world.func_72921_c(x, y, z, 7, 3);
/*  78 */       return true;
/*     */     } 
/*  80 */     if (id == Blocks.field_150480_ab) {
/*     */       
/*  82 */       world.func_72921_c(x, y, z, 0, 3);
/*  83 */       return true;
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLeaves(World world, int x, int y, int z, int meta) {
/*  90 */     if (world.func_147439_a(x, y, z) == Blocks.field_150350_a)
/*     */     {
/*  92 */       world.func_147465_d(x, y, z, (Block)Blocks.field_150362_t, meta, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean spreadGrass(World world, int x, int y, int z) {
/*  98 */     if (world.field_73012_v.nextBoolean())
/*     */     {
/* 100 */       return false;
/*     */     }
/* 102 */     y = TileEntityTerra.getFirstBlockFrom(world, x, z, y);
/* 103 */     Block id = world.func_147439_a(x, y, z);
/* 104 */     if (id == Blocks.field_150346_d) {
/*     */       
/* 106 */       world.func_147449_b(x, y, z, (Block)Blocks.field_150349_c);
/* 107 */       return true;
/*     */     } 
/* 109 */     if (id == Blocks.field_150349_c) {
/*     */       
/* 111 */       world.func_147465_d(x, y + 1, z, (Block)Blocks.field_150329_H, 1, 3);
/* 112 */       return true;
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPIrrigation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */