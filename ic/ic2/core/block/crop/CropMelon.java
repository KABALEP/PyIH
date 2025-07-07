/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropMelon
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  18 */     return "Melon";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  24 */     return "Chao";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  36 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  40 */         return 0;
/*     */ 
/*     */       
/*     */       case 1:
/*  44 */         return 4;
/*     */ 
/*     */       
/*     */       case 2:
/*  48 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  52 */         return 2;
/*     */ 
/*     */       
/*     */       case 4:
/*  56 */         return 0;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  68 */     return new String[] { "Green", "Food", "Stem" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  74 */     if (crop.getSize() == 4)
/*     */     {
/*  76 */       return Ic2Icons.getTexture("bc")[20];
/*     */     }
/*  78 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 15];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  84 */     return (crop.getSize() <= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  90 */     return (int)(humidity * 1.1D + nutrients * 0.9D + air);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  96 */     return (crop.getSize() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 102 */     if (IC2.random.nextInt(3) == 0)
/*     */     {
/* 104 */       return new ItemStack(Blocks.field_150440_ba);
/*     */     }
/* 106 */     return new ItemStack(Items.field_151127_ba, IC2.random.nextInt(4) + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSeeds(ICropTile crop) {
/* 112 */     if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
/*     */     {
/* 114 */       return new ItemStack(Items.field_151081_bc, IC2.random.nextInt(2) + 1);
/*     */     }
/* 116 */     return super.getSeeds(crop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 122 */     if (crop.getSize() == 3)
/*     */     {
/* 124 */       return 700;
/*     */     }
/* 126 */     return 250;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 132 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 138 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 144 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropMelon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */