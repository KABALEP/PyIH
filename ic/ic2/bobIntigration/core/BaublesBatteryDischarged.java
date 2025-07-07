/*    */ package ic2.bobIntigration.core;
/*    */ 
/*    */ import baubles.api.BaubleType;
/*    */ import baubles.api.IBauble;
/*    */ import ic2.core.item.ItemBatteryDischarged;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BaublesBatteryDischarged
/*    */   extends ItemBatteryDischarged
/*    */   implements IBauble
/*    */ {
/*    */   public BaublesBatteryDischarged(int sprite, int maxCharge, int transferLimit, int tier) {
/* 14 */     super(sprite, maxCharge, transferLimit, tier);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BaubleType getBaubleType(ItemStack itemstack) {
/* 20 */     return BaubleType.BELT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bobIntigration\core\BaublesBatteryDischarged.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */