package ic2.api.item;

import net.minecraft.item.ItemStack;

public interface IMiningDrill {
  boolean isBasicDrill(ItemStack paramItemStack);
  
  int getExtraSpeed(ItemStack paramItemStack);
  
  int getExtraEnergyCost(ItemStack paramItemStack);
  
  void useDrill(ItemStack paramItemStack);
  
  boolean canMine(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IMiningDrill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */