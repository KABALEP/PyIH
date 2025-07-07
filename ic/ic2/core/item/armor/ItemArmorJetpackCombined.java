/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorJetpackCombined
/*    */   extends ItemArmorJetpackElectric
/*    */ {
/*    */   public ItemArmorJetpackCombined(int index, int armorrendering) {
/* 15 */     super(index, armorrendering);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMaxCharge(ItemStack itemStack) {
/* 21 */     return 360000.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getTransferLimit(ItemStack par1) {
/* 27 */     return 300.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTier(ItemStack par1) {
/* 33 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canDoRocketMode() {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPower() {
/* 45 */     return 1.4F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getDropPercentage() {
/* 51 */     return 0.05F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getThruster(ItemArmorJetpack.HoverMode hoverMode) {
/* 57 */     switch (hoverMode) {
/*    */       case Adv:
/* 59 */         return 1.8F;
/* 60 */       case Basic: return 1.2F;
/* 61 */       case None: return 0.6F;
/*    */     } 
/* 63 */     return 0.6F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFuelCost(ItemArmorJetpack.HoverMode hoverMode) {
/* 69 */     switch (hoverMode) {
/*    */       case None:
/* 71 */         return 15;
/* 72 */       case Basic: return 12;
/* 73 */       case Adv: return 15;
/*    */     } 
/* 75 */     return 15;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxHeight(int worldHight) {
/* 81 */     return (int)(worldHight / 1.1F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxRocketCharge() {
/* 87 */     return 25000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canDoAdvHover() {
/* 93 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 99 */     return "jetpack_Combined_Electric";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpackCombined.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */