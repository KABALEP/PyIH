/*    */ package ic2.core.slot;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotDisplay
/*    */   extends Slot
/*    */ {
/*    */   public SlotDisplay(IInventory par1iInventory, int par2, int par3, int par4) {
/* 12 */     super(par1iInventory, par2, par3, par4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_82870_a(EntityPlayer player, ItemStack stack) {}
/*    */ 
/*    */   
/*    */   public boolean func_82869_a(EntityPlayer player) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_75209_a(int par1) {
/* 31 */     return func_75211_c();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotDisplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */