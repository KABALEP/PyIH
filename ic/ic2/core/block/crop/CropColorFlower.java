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
/*     */ public class CropColorFlower
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name;
/*     */   public String[] attributes;
/*     */   public int sprite;
/*     */   public int color;
/*     */   
/*     */   public CropColorFlower(String n, String[] a, int s, int c) {
/*  20 */     this.name = n;
/*  21 */     this.attributes = a;
/*  22 */     this.sprite = s;
/*  23 */     this.color = c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  29 */     if (name().equals("Dandelion") || name().equals("Rose"))
/*     */     {
/*  31 */       return "Notch";
/*     */     }
/*  33 */     return "Alblaka";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  39 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  45 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  51 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  55 */         return 1;
/*     */ 
/*     */       
/*     */       case 1:
/*  59 */         return 1;
/*     */ 
/*     */       
/*     */       case 2:
/*  63 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  67 */         return 5;
/*     */ 
/*     */       
/*     */       case 4:
/*  71 */         return 1;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  83 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  89 */     switch (crop.getSize()) {
/*     */ 
/*     */       
/*     */       case 1:
/*  93 */         return Ic2Icons.getTexture("bc")[12];
/*     */ 
/*     */       
/*     */       case 2:
/*  97 */         return Ic2Icons.getTexture("bc")[13];
/*     */ 
/*     */       
/*     */       case 3:
/* 101 */         return Ic2Icons.getTexture("bc")[14];
/*     */ 
/*     */       
/*     */       case 4:
/* 105 */         return Ic2Icons.getTexture("bc")[this.sprite];
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return Ic2Icons.getTexture("bc")[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/* 117 */     return (crop.getSize() <= 3 && crop.getLightLevel() >= 12);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/* 123 */     return (crop.getSize() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 129 */     return new ItemStack(Items.field_151100_aR, 1, this.color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 135 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 141 */     if (crop.getSize() == 3)
/*     */     {
/* 143 */       return 600;
/*     */     }
/* 145 */     return 400;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 151 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 157 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropColorFlower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */