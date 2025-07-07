/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropWheat
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  16 */     return "Wheat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  22 */     return "Notch";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  28 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  34 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  38 */         return 0;
/*     */ 
/*     */       
/*     */       case 1:
/*  42 */         return 4;
/*     */ 
/*     */       
/*     */       case 2:
/*  46 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  50 */         return 0;
/*     */ 
/*     */       
/*     */       case 4:
/*  54 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  66 */     return new String[] { "Yellow", "Food", "Wheat" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  72 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  78 */     return (crop.getSize() < 7 && crop.getLightLevel() >= 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  84 */     return (crop.getSize() == 7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  90 */     return new ItemStack(Items.field_151015_O, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSeeds(ICropTile crop) {
/*  96 */     if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
/*     */     {
/*  98 */       return new ItemStack(Items.field_151014_N);
/*     */     }
/* 100 */     return super.getSeeds(crop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 106 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 112 */     return 7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 118 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropWheat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */