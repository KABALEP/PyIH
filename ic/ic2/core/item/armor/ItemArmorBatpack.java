/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorBatpack
/*    */   extends ItemArmorElectric
/*    */ {
/*    */   public ItemArmorBatpack(int index, int armorrendering) {
/* 12 */     super(index, armorrendering, 1, 60000, 100, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack par1) {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 24 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 30 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 36 */     return "batpack";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorBatpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */