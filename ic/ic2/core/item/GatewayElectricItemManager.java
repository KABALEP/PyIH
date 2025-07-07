/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItemManager;
/*    */ import ic2.api.item.ISpecialElectricItem;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class GatewayElectricItemManager
/*    */   implements IElectricItemManager
/*    */ {
/*    */   public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/* 15 */     IElectricItemManager manager = getManager(itemStack);
/* 16 */     if (manager == null)
/*    */     {
/* 18 */       return 0.0D;
/*    */     }
/* 20 */     return manager.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
/*    */   }
/*    */ 
/*    */   
/*    */   public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externalUse, boolean simulate) {
/* 25 */     IElectricItemManager manager = getManager(itemStack);
/* 26 */     if (manager == null)
/*    */     {
/* 28 */       return 0.0D;
/*    */     }
/* 30 */     return manager.discharge(itemStack, amount, tier, ignoreTransferLimit, externalUse, simulate);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getCharge(ItemStack itemStack) {
/* 35 */     IElectricItemManager manager = getManager(itemStack);
/* 36 */     if (manager == null)
/*    */     {
/* 38 */       return 0.0D;
/*    */     }
/* 40 */     return manager.getCharge(itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse(ItemStack itemStack, double amount) {
/* 45 */     IElectricItemManager manager = getManager(itemStack);
/* 46 */     if (manager == null)
/*    */     {
/* 48 */       return false;
/*    */     }
/* 50 */     return manager.canUse(itemStack, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity) {
/* 55 */     IElectricItemManager manager = getManager(itemStack);
/* 56 */     if (manager == null)
/*    */     {
/* 58 */       return false;
/*    */     }
/* 60 */     return manager.use(itemStack, amount, entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
/* 65 */     IElectricItemManager manager = getManager(itemStack);
/* 66 */     if (manager == null) {
/*    */       return;
/*    */     }
/*    */     
/* 70 */     manager.chargeFromArmor(itemStack, entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getToolTip(ItemStack itemStack) {
/* 75 */     IElectricItemManager manager = getManager(itemStack);
/* 76 */     if (manager == null)
/*    */     {
/* 78 */       return null;
/*    */     }
/* 80 */     return manager.getToolTip(itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public IElectricItemManager getManager(ItemStack stack) {
/* 85 */     Item item = stack.func_77973_b();
/* 86 */     if (item == null)
/*    */     {
/* 88 */       return null;
/*    */     }
/*    */     
/* 91 */     if (item instanceof ISpecialElectricItem)
/*    */     {
/* 93 */       return ((ISpecialElectricItem)item).getManager(stack);
/*    */     }
/* 95 */     if (item instanceof ic2.api.item.IElectricItem)
/*    */     {
/* 97 */       return ElectricItem.rawManager;
/*    */     }
/* 99 */     return (IElectricItemManager)ElectricItem.getBackupManager(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\GatewayElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */