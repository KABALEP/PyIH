/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.TileEntityCrop;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropNetherWart
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  18 */     return "NetherWart";
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
/*  30 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  36 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  40 */         return 4;
/*     */ 
/*     */       
/*     */       case 1:
/*  44 */         return 2;
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
/*  68 */     return new String[] { "Red", "Nether", "Ingredient", "Soulsand" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  74 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 36];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  80 */     return (crop.getSize() < 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  86 */     return (crop.getSize() == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropGainChance() {
/*  92 */     return 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  98 */     return new ItemStack(Items.field_151075_bm, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(ICropTile crop) {
/* 104 */     TileEntityCrop te = (TileEntityCrop)crop;
/* 105 */     if (te.isBlockBelow(Blocks.field_150425_aM)) {
/*     */       
/* 107 */       if (canGrow((ICropTile)te))
/*     */       {
/* 109 */         TileEntityCrop tmp24_23 = te;
/* 110 */         tmp24_23.growthPoints += (int)(te.calcGrowthRate() * 0.5D);
/*     */       }
/*     */     
/* 113 */     } else if (te.isBlockBelow(Blocks.field_150433_aE) && (crop.getWorld()).field_73012_v.nextInt(300) == 0) {
/*     */       
/* 115 */       te.setID((short)IC2Crops.cropTerraWart.getId());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 122 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 128 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropNetherWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */