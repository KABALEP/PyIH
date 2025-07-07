/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotCharge
/*    */   extends Slot
/*    */ {
/*    */   int tier;
/*    */   
/*    */   public SlotCharge(IInventory par1iInventory, int tier, int par2, int par3, int par4) {
/* 15 */     super(par1iInventory, par2, par3, par4);
/* 16 */     this.tier = Integer.MAX_VALUE;
/* 17 */     this.tier = tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public SlotCharge(IInventory par1iInventory, int par2, int par3, int par4) {
/* 22 */     this(par1iInventory, 2147483647, par2, par3, par4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 27 */     return (stack != null && ((stack.func_77973_b() instanceof IElectricItem && ((IElectricItem)stack.func_77973_b()).getTier(stack) <= this.tier) || ElectricItem.getBackupManager(stack) != null));
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */