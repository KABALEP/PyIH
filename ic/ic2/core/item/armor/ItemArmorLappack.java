/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ItemArmorLappack
/*    */   extends ItemArmorElectric
/*    */ {
/*    */   public ItemArmorLappack(int index, int armorrendering) {
/* 13 */     super(index, armorrendering, 1, 300000, 250, 2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack par1) {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 25 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 31 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 37 */     return EnumRarity.uncommon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 43 */     return "lappack";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorLappack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */