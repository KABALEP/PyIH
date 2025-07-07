package ic2.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IElectricItemManager {
  double charge(ItemStack paramItemStack, double paramDouble, int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  double discharge(ItemStack paramItemStack, double paramDouble, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  double getCharge(ItemStack paramItemStack);
  
  boolean canUse(ItemStack paramItemStack, double paramDouble);
  
  boolean use(ItemStack paramItemStack, double paramDouble, EntityLivingBase paramEntityLivingBase);
  
  void chargeFromArmor(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
  
  String getToolTip(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */