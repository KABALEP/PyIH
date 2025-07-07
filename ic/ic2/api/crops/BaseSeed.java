/*    */ package ic2.api.crops;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaseSeed
/*    */ {
/*    */   public final CropCard crop;
/*    */   @Deprecated
/*    */   public int id;
/*    */   public int size;
/*    */   public int statGrowth;
/*    */   public int statGain;
/*    */   public int statResistance;
/*    */   public int stackSize;
/*    */   
/*    */   public BaseSeed(CropCard crop, int size, int statGrowth, int statGain, int statResistance, int stackSize) {
/* 20 */     this.crop = crop;
/* 21 */     this.id = Crops.instance.getIdFor(crop);
/* 22 */     this.size = size;
/* 23 */     this.statGrowth = statGrowth;
/* 24 */     this.statGain = statGain;
/* 25 */     this.statResistance = statResistance;
/* 26 */     this.stackSize = stackSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public BaseSeed(int id, int size, int statGrowth, int statGain, int statResistance, int stackSize) {
/* 34 */     this(getCropFromId(id), size, statGrowth, statGain, statResistance, stackSize);
/*    */   }
/*    */ 
/*    */   
/*    */   private static CropCard getCropFromId(int id) {
/* 39 */     CropCard[] crops = Crops.instance.getCropList();
/*    */     
/* 41 */     if (id < 0 || id >= crops.length) return null;
/*    */     
/* 43 */     return crops[id];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\crops\BaseSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */