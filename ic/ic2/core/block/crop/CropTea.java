/*    */ package ic2.core.block.crop;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropTea
/*    */   extends CropCardBase
/*    */ {
/*    */   public String name() {
/* 18 */     return "Tea";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String discoveredBy() {
/* 24 */     return "Chocohead";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int tier() {
/* 30 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int stat(int n) {
/* 36 */     switch (n) {
/*    */       case 0:
/* 38 */         return 1;
/* 39 */       case 1: return 3;
/* 40 */       case 2: return 1;
/* 41 */       case 3: return 2;
/* 42 */       case 4: return 0;
/*    */     } 
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] attributes() {
/* 50 */     return new String[] { "Leaves", "Ingrident", "Green", "Tea" };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int maxSize() {
/* 56 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 62 */     return (crop.getSize() < 5 && crop.getLightLevel() >= 7);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getOptimalHavestSize(ICropTile crop) {
/* 68 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 74 */     return (crop.getSize() == 5);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 80 */     return Ic2Items.teaLeaf;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 86 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getSprite(ICropTile crop) {
/* 93 */     return Ic2Icons.getTexture("bc")[61 + crop.getSize()];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropTea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */