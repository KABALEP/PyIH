/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropRedWheat
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  17 */     return "Redwheat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  23 */     return "raa1337";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  29 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  35 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  39 */         return 3;
/*     */ 
/*     */       
/*     */       case 1:
/*  43 */         return 0;
/*     */ 
/*     */       
/*     */       case 2:
/*  47 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  51 */         return 2;
/*     */ 
/*     */       
/*     */       case 4:
/*  55 */         return 0;
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
/*  67 */     return new String[] { "Red", "Redstone", "Wheat" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  73 */     if (crop.getSize() == 7)
/*     */     {
/*  75 */       return Ic2Icons.getTexture("bc")[27];
/*     */     }
/*  77 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  83 */     return (crop.getSize() < 7 && crop.getLightLevel() <= 10 && crop.getLightLevel() >= 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  89 */     return (crop.getSize() == 7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropGainChance() {
/*  95 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 101 */     ChunkCoordinates coords = crop.getLocation();
/* 102 */     if (crop.getWorld().func_94577_B(coords.field_71574_a, coords.field_71572_b, coords.field_71573_c) > 0 || (crop.getWorld()).field_73012_v.nextBoolean())
/*     */     {
/* 104 */       return new ItemStack(Items.field_151137_ax, 1);
/*     */     }
/* 106 */     return new ItemStack(Items.field_151015_O, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int emitRedstone(ICropTile crop) {
/* 112 */     return (crop.getSize() == 7) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmittedLight(ICropTile crop) {
/* 118 */     return (crop.getSize() == 7) ? 7 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 124 */     return 600;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 130 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 136 */     return 7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 142 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropRedWheat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */