/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropFerru
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  17 */     return "Ferru";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  23 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  29 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  33 */         return 2;
/*     */ 
/*     */       
/*     */       case 1:
/*  37 */         return 0;
/*     */ 
/*     */       
/*     */       case 2:
/*  41 */         return 0;
/*     */ 
/*     */       
/*     */       case 3:
/*  45 */         return 1;
/*     */ 
/*     */       
/*     */       case 4:
/*  49 */         return 0;
/*     */     } 
/*     */ 
/*     */     
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  61 */     return new String[] { "Gray", "Leaves", "Metal" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  67 */     if (crop.getSize() == 4)
/*     */     {
/*  69 */       return Ic2Icons.getTexture("bc")[35];
/*     */     }
/*  71 */     return Ic2Icons.getTexture("bc")[31 + crop.getSize()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  77 */     return (crop.getSize() < 3 || (crop.getSize() == 3 && crop.isBlockBelow(Blocks.field_150366_p)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  83 */     return (crop.getSize() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  89 */     return new ItemStack(Ic2Items.smallIronDust.func_77973_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropGainChance() {
/*  95 */     return super.dropGainChance() / 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 101 */     if (crop.getSize() == 3)
/*     */     {
/* 103 */       return 2000;
/*     */     }
/* 105 */     return 800;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 111 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 117 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 123 */     return maxSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropFerru.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */