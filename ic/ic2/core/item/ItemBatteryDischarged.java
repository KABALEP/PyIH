/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ public class ItemBatteryDischarged
/*    */   extends ItemBattery
/*    */   implements IBoxable {
/*    */   public ItemBatteryDischarged(int sprite, int maxCharge, int transferLimit, int tier) {
/* 14 */     super(sprite, maxCharge, transferLimit, tier);
/* 15 */     func_77656_e(0);
/* 16 */     func_77625_d(16);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getEmptyItem(ItemStack itemStack) {
/* 22 */     return Ic2Items.reBattery.func_77973_b();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_77617_a(int i) {
/* 28 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemBatteryDischarged.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */