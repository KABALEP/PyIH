/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropSeedFood
/*     */   extends CropCardBase
/*     */ {
/*     */   private String name;
/*     */   private int spriteIndex;
/*     */   private String color;
/*     */   private ItemStack gain;
/*     */   
/*     */   public CropSeedFood(String name, int spriteIndex, String color, ItemStack gain) {
/*  19 */     this.name = name;
/*  20 */     this.spriteIndex = spriteIndex;
/*  21 */     this.color = color;
/*  22 */     this.gain = gain;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  28 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  34 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  40 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  44 */         return 0;
/*     */ 
/*     */       
/*     */       case 1:
/*  48 */         return 4;
/*     */ 
/*     */       
/*     */       case 2:
/*  52 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  56 */         return 0;
/*     */ 
/*     */       
/*     */       case 4:
/*  60 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  72 */     return new String[] { this.color, "Food", this.name };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  78 */     return Ic2Icons.getTexture("bc")[(crop.getSize() < 3) ? (47 + crop.getSize()) : this.spriteIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  84 */     return (crop.getSize() < 3 && crop.getLightLevel() >= 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  90 */     return (crop.getSize() == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  96 */     return this.gain.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 102 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 108 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 114 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropSeedFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */