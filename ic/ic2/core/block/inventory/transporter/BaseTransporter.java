/*    */ package ic2.core.block.inventory.transporter;
/*    */ 
/*    */ import ic2.core.block.inventory.IItemTransporter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseTransporter
/*    */   implements IItemTransporter
/*    */ {
/*    */   public ItemStack addItem(ItemStack stack, ForgeDirection dir, boolean doAdd) {
/* 13 */     ItemStack item = stack.func_77946_l();
/* 14 */     item.field_77994_a = injectItem(stack, dir, doAdd);
/* 15 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract int injectItem(ItemStack paramItemStack, ForgeDirection paramForgeDirection, boolean paramBoolean);
/*    */   
/*    */   public boolean canMerge(ItemStack key, ItemStack value) {
/* 22 */     if (key == null || value == null)
/*    */     {
/* 24 */       return false;
/*    */     }
/* 26 */     if (!key.func_77969_a(value))
/*    */     {
/* 28 */       return false;
/*    */     }
/* 30 */     if (!ItemStack.func_77970_a(key, value))
/*    */     {
/* 32 */       return false;
/*    */     }
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\transporter\BaseTransporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */