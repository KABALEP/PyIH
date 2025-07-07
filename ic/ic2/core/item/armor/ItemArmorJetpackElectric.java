/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmorJetpackElectric
/*     */   extends ItemArmorJetpack
/*     */   implements IElectricItem
/*     */ {
/*     */   public ItemArmorJetpackElectric(int index, int armorrendering) {
/*  19 */     super(index, armorrendering);
/*  20 */     func_77656_e(27);
/*  21 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharge(ItemStack itemStack) {
/*  27 */     return (int)ElectricItem.manager.discharge(itemStack, 2.147483647E9D, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void use(ItemStack itemStack, int amount) {
/*  33 */     ElectricItem.manager.discharge(itemStack, amount, 2147483647, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/*  45 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/*  51 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/*  58 */     return 30000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack par1) {
/*  64 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack par1) {
/*  70 */     return 60.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/*  77 */     ItemStack stack = new ItemStack((Item)this, 1);
/*  78 */     if (getChargedItem(stack) == this) {
/*     */       
/*  80 */       ItemStack charged = new ItemStack((Item)this, 1);
/*  81 */       ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/*  82 */       itemList.add(charged);
/*     */     } 
/*  84 */     if (getEmptyItem(stack) == this)
/*     */     {
/*  86 */       itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDropPercentage() {
/*  93 */     return 0.05F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPower() {
/*  99 */     return 0.7F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isElectricJetpack() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFuelCost(ItemArmorJetpack.HoverMode hoverMode) {
/* 111 */     return (hoverMode == ItemArmorJetpack.HoverMode.Basic) ? 4 : 7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeight(int worldHight) {
/* 117 */     return (int)(worldHight / 1.28F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getThruster(ItemArmorJetpack.HoverMode hoverMode) {
/* 123 */     return (hoverMode == ItemArmorJetpack.HoverMode.Basic) ? 0.65F : 0.3F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 129 */     return "jetpack_Electric";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpackElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */