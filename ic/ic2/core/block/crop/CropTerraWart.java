/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityCrop;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropTerraWart
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  18 */     return "TerraWart";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  24 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  30 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  34 */         return 2;
/*     */ 
/*     */       
/*     */       case 1:
/*  38 */         return 4;
/*     */ 
/*     */       
/*     */       case 2:
/*  42 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  46 */         return 3;
/*     */ 
/*     */       
/*     */       case 4:
/*  50 */         return 0;
/*     */     } 
/*     */ 
/*     */     
/*  54 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  62 */     return new String[] { "Blue", "Aether", "Consumable", "Snow" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  68 */     if (crop.getSize() == 1)
/*     */     {
/*  70 */       return Ic2Icons.getTexture("bc")[37];
/*     */     }
/*  72 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 38];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  78 */     return (crop.getSize() < 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  84 */     return (crop.getSize() == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropGainChance() {
/*  90 */     return 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  96 */     return new ItemStack(Ic2Items.terraWart.func_77973_b(), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(ICropTile crop) {
/* 102 */     TileEntityCrop te = (TileEntityCrop)crop;
/* 103 */     if (te.isBlockBelow(Blocks.field_150433_aE)) {
/*     */       
/* 105 */       if (canGrow((ICropTile)te))
/*     */       {
/* 107 */         TileEntityCrop tmp24_23 = te;
/* 108 */         tmp24_23.growthPoints += (int)(te.calcGrowthRate() * 0.5D);
/*     */       }
/*     */     
/* 111 */     } else if (te.isBlockBelow(Blocks.field_150425_aM) && (crop.getWorld()).field_73012_v.nextInt(300) == 0) {
/*     */       
/* 113 */       te.setID((short)IC2Crops.cropNetherWart.getId());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 120 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 126 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropTerraWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */