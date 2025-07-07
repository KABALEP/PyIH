package ic2.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ICustomDamageItem {
  int getCustomDamage(ItemStack paramItemStack);
  
  int getMaxCustomDamage(ItemStack paramItemStack);
  
  void setCustomDamage(ItemStack paramItemStack, int paramInt);
  
  boolean applyCustomDamage(ItemStack paramItemStack, int paramInt, EntityLivingBase paramEntityLivingBase);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\ICustomDamageItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */