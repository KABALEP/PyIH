/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemTFBPCultivation
/*     */   extends ItemTFBP
/*     */ {
/*  18 */   public static ArrayList<Block> plantIDs = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public ItemTFBPCultivation(int index) {
/*  22 */     super(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  27 */     plantIDs.add(Blocks.field_150329_H);
/*  28 */     plantIDs.add(Blocks.field_150328_O);
/*  29 */     plantIDs.add(Blocks.field_150327_N);
/*  30 */     plantIDs.add(Blocks.field_150345_g);
/*  31 */     plantIDs.add(Blocks.field_150464_aj);
/*  32 */     plantIDs.add(Blocks.field_150337_Q);
/*  33 */     plantIDs.add(Blocks.field_150338_P);
/*  34 */     plantIDs.add(Blocks.field_150423_aK);
/*  35 */     plantIDs.add(Blocks.field_150440_ba);
/*  36 */     if (Ic2Items.rubberSapling != null)
/*     */     {
/*  38 */       plantIDs.add(Block.func_149634_a(Ic2Items.rubberSapling.func_77973_b()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/*  45 */     if (super.func_77648_a(itemstack, entityplayer, world, i, j, k, l, a, b, c)) {
/*     */       
/*  47 */       if (entityplayer.field_71093_bK == 1)
/*     */       {
/*  49 */         IC2.achievements.issueAchievement(entityplayer, "terraformEndCultivation");
/*     */       }
/*  51 */       return true;
/*     */     } 
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConsume(ItemStack item) {
/*  59 */     return 4000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack item) {
/*  65 */     return 40;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/*  71 */     int y = TileEntityTerra.getFirstSolidBlockFrom(world, x, z, yCoord + 10);
/*  72 */     if (y == -1)
/*     */     {
/*  74 */       return false;
/*     */     }
/*  76 */     if (TileEntityTerra.switchGround(world, (Block)Blocks.field_150354_m, Blocks.field_150346_d, x, y, z, true))
/*     */     {
/*  78 */       return true;
/*     */     }
/*  80 */     Block id = world.func_147439_a(x, y, z);
/*  81 */     if (id == Blocks.field_150346_d) {
/*     */       
/*  83 */       world.func_147449_b(x, y, z, (Block)Blocks.field_150349_c);
/*  84 */       return true;
/*     */     } 
/*  86 */     return (id == Blocks.field_150349_c && growPlantsOn(world, x, y + 1, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean growPlantsOn(World world, int x, int y, int z) {
/*  91 */     Block id = world.func_147439_a(x, y, z);
/*  92 */     if (id != null && (id != Blocks.field_150329_H || world.field_73012_v.nextInt(4) != 0))
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     Block plant = pickRandomPlantId(world.field_73012_v);
/*  97 */     if (plant == Blocks.field_150464_aj)
/*     */     {
/*  99 */       world.func_147449_b(x, y - 1, z, Blocks.field_150458_ak);
/*     */     }
/* 101 */     if (plant == Blocks.field_150329_H) {
/*     */       
/* 103 */       world.func_147465_d(x, y, z, plant, 1, 3);
/* 104 */       return true;
/*     */     } 
/* 106 */     world.func_147449_b(x, y, z, plant);
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block pickRandomPlantId(Random random) {
/* 112 */     for (int i = 0; i < plantIDs.size(); i++) {
/*     */       
/* 114 */       if (random.nextInt(5) <= 1)
/*     */       {
/* 116 */         return plantIDs.get(i);
/*     */       }
/*     */     } 
/* 119 */     return (Block)Blocks.field_150329_H;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPCultivation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */