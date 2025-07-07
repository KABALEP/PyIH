/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropWeed
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  17 */     return "Weed";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  23 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  29 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  33 */         return 0;
/*     */ 
/*     */       
/*     */       case 1:
/*  37 */         return 0;
/*     */ 
/*     */       
/*     */       case 2:
/*  41 */         return 1;
/*     */ 
/*     */       
/*     */       case 3:
/*  45 */         return 0;
/*     */ 
/*     */       
/*     */       case 4:
/*  49 */         return 5;
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
/*  61 */     return new String[] { "Weed", "Bad" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  67 */     return Ic2Icons.getTexture("bc")[crop.getSize() + 8];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  73 */     return (crop.getSize() < 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean leftclick(ICropTile crop, EntityPlayer player) {
/*  79 */     crop.reset();
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/*  98 */     return 300;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 110 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 116 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropWeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */