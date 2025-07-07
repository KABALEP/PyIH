/*    */ package ic2.core.block.inventory.filter;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.block.inventory.IItemTransporter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class MachineFilter
/*    */   implements IItemTransporter.IFilter
/*    */ {
/*    */   IMachine machine;
/*    */   
/*    */   public MachineFilter(IMachine par1) {
/* 13 */     this.machine = par1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 19 */     return this.machine.isValidInput(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\filter\MachineFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */