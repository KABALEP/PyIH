/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.api.item.ICustomDamageItem;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemGradual
/*    */   extends ItemIC2
/*    */   implements IBoxable, ICustomDamageItem {
/*    */   public ItemGradual(int index) {
/* 13 */     super(index);
/* 14 */     func_77625_d(1);
/* 15 */     func_77656_e(10000);
/* 16 */     setNoRepair();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 22 */     return (itemstack.func_77973_b() == Ic2Items.hydratingCell.func_77973_b());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDamage(ItemStack stack) {
/* 28 */     return getCustomDamage(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDamaged(ItemStack stack) {
/* 34 */     return (getCustomDamage(stack) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCustomDamage(ItemStack stack) {
/* 40 */     return super.getDamage(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxCustomDamage(ItemStack stack) {
/* 46 */     return func_77612_l();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int getDisplayDamage(ItemStack stack) {
/* 53 */     return getDamage(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCustomDamage(ItemStack stack, int damage) {
/* 59 */     stack.func_77964_b(damage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/* 65 */     stack.func_77972_a(damage, src);
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemGradual.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */