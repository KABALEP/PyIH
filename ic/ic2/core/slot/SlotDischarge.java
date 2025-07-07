/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.item.IElectricItem;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class SlotDischarge
/*    */   extends Slot
/*    */ {
/*    */   public int tier;
/*    */   
/*    */   public SlotDischarge(IInventory par1iInventory, int tier, int par2, int par3, int par4) {
/* 16 */     super(par1iInventory, par2, par3, par4);
/* 17 */     this.tier = Integer.MAX_VALUE;
/* 18 */     this.tier = tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public SlotDischarge(IInventory par1iInventory, int par2, int par3, int par4) {
/* 23 */     super(par1iInventory, par2, par3, par4);
/* 24 */     this.tier = Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 29 */     if (stack == null)
/*    */     {
/* 31 */       return false;
/*    */     }
/* 33 */     if (stack.func_77973_b() == Items.field_151137_ax || stack.func_77973_b() instanceof ic2.core.item.ItemBatterySU)
/*    */     {
/* 35 */       return true;
/*    */     }
/* 37 */     if (stack.func_77973_b() instanceof IElectricItem) {
/*    */       
/* 39 */       IElectricItem iee = (IElectricItem)stack.func_77973_b();
/* 40 */       if (iee.canProvideEnergy(stack) && iee.getTier(stack) <= this.tier)
/*    */       {
/* 42 */         return true;
/*    */       }
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotDischarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */