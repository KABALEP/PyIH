/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropReed
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  17 */     return "Reed";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  23 */     return "Notch";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  35 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  39 */         return 0;
/*     */ 
/*     */       
/*     */       case 1:
/*  43 */         return 0;
/*     */ 
/*     */       
/*     */       case 2:
/*  47 */         return 1;
/*     */ 
/*     */       
/*     */       case 3:
/*  51 */         return 0;
/*     */ 
/*     */       
/*     */       case 4:
/*  55 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  67 */     return new String[] { "Reed" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  73 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 27];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  79 */     return (crop.getSize() < 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  85 */     return (int)(humidity * 1.2D + nutrients + air * 0.8D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  91 */     return (crop.getSize() > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  97 */     return new ItemStack(Items.field_151120_aE, crop.getSize() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 109 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 115 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 121 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */