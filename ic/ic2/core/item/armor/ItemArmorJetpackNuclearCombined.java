/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorJetpackNuclearCombined
/*    */   extends ItemArmorJetpackNuclear
/*    */ {
/*    */   public ItemArmorJetpackNuclearCombined(int spriteIndex, int armorrendering) {
/* 11 */     super(spriteIndex, armorrendering);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMaxCharge(ItemStack itemStack) {
/* 17 */     return 360000.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPower() {
/* 23 */     return 1.8F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getThruster(ItemArmorJetpack.HoverMode hoverMode) {
/* 29 */     switch (hoverMode) {
/*    */       case Adv:
/* 31 */         return 3.0F;
/* 32 */       case Basic: return 2.0F;
/* 33 */       case None: return 1.0F;
/*    */     } 
/* 35 */     return 2.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFuelCost(ItemArmorJetpack.HoverMode hoverMode) {
/* 41 */     switch (hoverMode) {
/*    */       case None:
/* 43 */         return 27;
/* 44 */       case Basic: return 23;
/* 45 */       case Adv: return 27;
/*    */     } 
/* 47 */     return 27;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxRocketCharge() {
/* 53 */     return 30000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 59 */     return "jetpack_Combined_Nuclear";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpackNuclearCombined.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */