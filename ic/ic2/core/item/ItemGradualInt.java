/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ public class ItemGradualInt
/*    */   extends ItemGradual
/*    */ {
/*    */   public ItemGradualInt(int index, int maxdmg) {
/* 12 */     super(index);
/* 13 */     func_77656_e(maxdmg);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCustomDamage(ItemStack stack) {
/* 19 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 20 */     return nbt.func_74762_e("advDamage");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCustomDamage(ItemStack stack, int damage) {
/* 26 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 27 */     nbt.func_74768_a("advDamage", damage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDamage(ItemStack stack, int damage) {
/* 33 */     setCustomDamage(stack, damage);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemGradualInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */