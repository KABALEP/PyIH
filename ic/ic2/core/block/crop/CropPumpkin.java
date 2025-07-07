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
/*     */ public class CropPumpkin
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  18 */     return "Pumpkin";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  24 */     return "Notch";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  30 */     return 1;
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
/*  44 */         return 1;
/*     */ 
/*     */       
/*     */       case 2:
/*  48 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  52 */         return 3;
/*     */ 
/*     */       
/*     */       case 4:
/*  56 */         return 1;
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
/*  68 */     return new String[] { "Orange", "Decoration", "Stem" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  74 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 15];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  80 */     return (crop.getSize() <= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  86 */     return (int)(humidity * 1.1D + nutrients * 0.9D + air);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  92 */     return (crop.getSize() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  98 */     return new ItemStack(Blocks.field_150423_aK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSeeds(ICropTile crop) {
/* 104 */     if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
/*     */     {
/* 106 */       return new ItemStack(Items.field_151080_bb, IC2.random.nextInt(3) + 1);
/*     */     }
/* 108 */     return super.getSeeds(crop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 114 */     if (crop.getSize() == 3)
/*     */     {
/* 116 */       return 600;
/*     */     }
/* 118 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 124 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 130 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 136 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropPumpkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */