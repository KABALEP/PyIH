/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropStickReed
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  19 */     return "Stickreed";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  25 */     return "raa1337";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  31 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  37 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  41 */         return 2;
/*     */ 
/*     */       
/*     */       case 1:
/*  45 */         return 0;
/*     */ 
/*     */       
/*     */       case 2:
/*  49 */         return 1;
/*     */ 
/*     */       
/*     */       case 3:
/*  53 */         return 0;
/*     */ 
/*     */       
/*     */       case 4:
/*  57 */         return 1;
/*     */     } 
/*     */ 
/*     */     
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  69 */     return new String[] { "Reed", "Resin" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  75 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 27];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  81 */     return (crop.getSize() < 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/*  87 */     return (int)(humidity * 1.2D + nutrients + air * 0.8D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  93 */     return (crop.getSize() > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  99 */     if (crop.getSize() <= 3)
/*     */     {
/* 101 */       return new ItemStack(Items.field_151120_aE, crop.getSize() - 1);
/*     */     }
/* 103 */     return new ItemStack(Ic2Items.resin.func_77973_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 109 */     if (crop.getSize() == 4)
/*     */     {
/* 111 */       return (byte)(3 - IC2.random.nextInt(3));
/*     */     }
/* 113 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 125 */     if (crop.getSize() == 4)
/*     */     {
/* 127 */       return 400;
/*     */     }
/* 129 */     return 100;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 135 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 141 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropStickReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */