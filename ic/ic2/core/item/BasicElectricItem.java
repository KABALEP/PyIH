/*    */ package ic2.core.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public abstract class BasicElectricItem
/*    */   extends ItemIC2
/*    */   implements IElectricItem
/*    */ {
/*    */   public int maxCharge;
/*    */   public int transferLimit;
/*    */   public int tier;
/*    */   
/*    */   public BasicElectricItem(int index) {
/* 22 */     super(index);
/* 23 */     this.transferLimit = 100;
/* 24 */     this.tier = 1;
/* 25 */     func_77656_e(27);
/* 26 */     func_77625_d(1);
/* 27 */     setNoRepair();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack par1) {
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getChargedItem(ItemStack itemStack) {
/* 39 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getEmptyItem(ItemStack itemStack) {
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMaxCharge(ItemStack par1) {
/* 51 */     return this.maxCharge;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTier(ItemStack par1) {
/* 57 */     return this.tier;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getTransferLimit(ItemStack par1) {
/* 63 */     return this.transferLimit;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 69 */     ItemStack stack = new ItemStack(this, 1);
/* 70 */     if (getChargedItem(stack) == this) {
/*    */       
/* 72 */       ItemStack charged = new ItemStack(this, 1);
/* 73 */       ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 74 */       itemList.add(charged);
/*    */     } 
/* 76 */     if (getEmptyItem(stack) == this)
/*    */     {
/* 78 */       itemList.add(new ItemStack(this, 1, func_77612_l()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\BasicElectricItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */