/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropHops
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  16 */     return "Hops";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  22 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  28 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  32 */         return 2;
/*     */ 
/*     */       
/*     */       case 1:
/*  36 */         return 2;
/*     */ 
/*     */       
/*     */       case 2:
/*  40 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  44 */         return 1;
/*     */ 
/*     */       
/*     */       case 4:
/*  48 */         return 1;
/*     */     } 
/*     */ 
/*     */     
/*  52 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  60 */     return new String[] { "Green", "Ingredient", "Wheat" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  66 */     if (crop.getSize() >= 5)
/*     */     {
/*  68 */       return Ic2Icons.getTexture("bc")[crop.getSize() + 39];
/*     */     }
/*  70 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/*  76 */     return 600;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  82 */     return (crop.getSize() < 7 && crop.getLightLevel() >= 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  88 */     return (crop.getSize() == 7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  94 */     return new ItemStack(Ic2Items.hops.func_77973_b(), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 100 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 106 */     return 7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 112 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropHops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */