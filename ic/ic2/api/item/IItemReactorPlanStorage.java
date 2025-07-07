package ic2.api.item;

import net.minecraft.item.ItemStack;

public interface IItemReactorPlanStorage {
  boolean isPlanStorage(ItemStack paramItemStack);
  
  boolean setSetup(ItemStack paramItemStack, String paramString);
  
  void setPlanName(ItemStack paramItemStack, String paramString);
  
  boolean hasSetup(ItemStack paramItemStack);
  
  String getSetup(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IItemReactorPlanStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */