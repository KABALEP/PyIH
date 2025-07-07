/*    */ package ic2.core.item;
/*    */ 
/*    */ import cpw.mods.fml.common.IFuelHandler;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFuelCanFilled
/*    */   extends ItemFuelCan
/*    */   implements IFuelHandler
/*    */ {
/*    */   public ItemFuelCanFilled(int index) {
/* 19 */     super(index);
/* 20 */     GameRegistry.registerFuelHandler(this);
/* 21 */     func_77625_d(1);
/* 22 */     func_77656_e(100);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBurnTime(ItemStack stack) {
/* 28 */     if (stack.func_77973_b() != this)
/*    */     {
/* 30 */       return 0;
/*    */     }
/* 32 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(stack);
/* 33 */     if (!data.func_74764_b("value") && stack.func_77960_j() > 0)
/*    */     {
/* 35 */       data.func_74768_a("value", stack.func_77960_j());
/*    */     }
/* 37 */     int fv = data.func_74762_e("value") * 2;
/* 38 */     return (fv > 500) ? 500 : fv;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDamaged(ItemStack stack) {
/* 44 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 45 */     return (nbt.func_74762_e("value") < nbt.func_74762_e("maxValue"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getContainerItem(ItemStack stack) {
/* 51 */     if (stack != null && stack.func_77973_b() == this) {
/*    */       
/* 53 */       ItemStack itemStack = stack.func_77946_l();
/* 54 */       NBTTagCompound data = StackUtil.getOrCreateNbtData(itemStack);
/* 55 */       int amount = data.func_74762_e("value");
/* 56 */       amount -= 250;
/* 57 */       if (amount <= 0)
/*    */       {
/* 59 */         return super.getContainerItem(stack);
/*    */       }
/* 61 */       data.func_74768_a("value", amount);
/* 62 */       return itemStack;
/*    */     } 
/* 64 */     return super.getContainerItem(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 70 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(stack);
/* 71 */     if (!data.func_74764_b("value") || !data.func_74764_b("maxValue"))
/*    */     {
/* 73 */       return super.getDurabilityForDisplay(stack);
/*    */     }
/* 75 */     return data.func_74762_e("value") / data.func_74762_e("maxValue");
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemFuelCanFilled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */