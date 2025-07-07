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
/*     */ public class CropCocoa
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  16 */     return "Cocoa";
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
/*  28 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  34 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  38 */         return 1;
/*     */ 
/*     */       
/*     */       case 1:
/*  42 */         return 3;
/*     */ 
/*     */       
/*     */       case 2:
/*  46 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  50 */         return 4;
/*     */ 
/*     */       
/*     */       case 4:
/*  54 */         return 0;
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
/*  66 */     return new String[] { "Brown", "Food", "Stem" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  72 */     if (crop.getSize() == 4)
/*     */     {
/*  74 */       return Ic2Icons.getTexture("bc")[26];
/*     */     }
/*  76 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 15];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  82 */     return (crop.getSize() <= 3 && crop.getNutrients() >= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  88 */     return (int)(humidity * 0.8D + nutrients * 1.3D + air * 0.9D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  94 */     return (crop.getSize() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 100 */     return new ItemStack(Items.field_151100_aR, 1, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 106 */     if (crop.getSize() == 3)
/*     */     {
/* 108 */       return 900;
/*     */     }
/* 110 */     return 400;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 116 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 122 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 128 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropCocoa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */