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
/*     */ public class CropCoffee
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  16 */     return "Coffee";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  22 */     return "Snoochy";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  28 */     return 7;
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
/*  42 */         return 4;
/*     */ 
/*     */       
/*     */       case 2:
/*  46 */         return 1;
/*     */ 
/*     */       
/*     */       case 3:
/*  50 */         return 2;
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
/*  66 */     return new String[] { "Leaves", "Ingrident", "Beans" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  72 */     if (crop.getSize() == 5)
/*     */     {
/*  74 */       return Ic2Icons.getTexture("bc")[43];
/*     */     }
/*  76 */     if (crop.getSize() == 4)
/*     */     {
/*  78 */       return Ic2Icons.getTexture("bc")[42];
/*     */     }
/*  80 */     return Ic2Icons.getTexture("bc")[31 + crop.getSize()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  86 */     return (crop.getSize() < 5 && crop.getLightLevel() >= 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  92 */     return (int)(0.4D * humidity + 1.4D * nutrients + 1.2D * air);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/*  98 */     if (crop.getSize() == 3)
/*     */     {
/* 100 */       return (int)(super.growthDuration(crop) * 0.5D);
/*     */     }
/* 102 */     if (crop.getSize() == 4)
/*     */     {
/* 104 */       return (int)(super.growthDuration(crop) * 1.5D);
/*     */     }
/* 106 */     return super.growthDuration(crop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/* 112 */     return (crop.getSize() >= 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 118 */     if (crop.getSize() == 4)
/*     */     {
/* 120 */       return null;
/*     */     }
/* 122 */     return new ItemStack(Ic2Items.coffeeBeans.func_77973_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 128 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 134 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 140 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropCoffee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */