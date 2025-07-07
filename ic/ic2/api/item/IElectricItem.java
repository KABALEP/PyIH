package ic2.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IElectricItem {
  boolean canProvideEnergy(ItemStack paramItemStack);
  
  Item getChargedItem(ItemStack paramItemStack);
  
  Item getEmptyItem(ItemStack paramItemStack);
  
  double getMaxCharge(ItemStack paramItemStack);
  
  int getTier(ItemStack paramItemStack);
  
  double getTransferLimit(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IElectricItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */