package ic2.api.reactor;

import net.minecraft.item.ItemStack;

public interface IReactorComponent {
  void processChamber(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2, boolean paramBoolean);
  
  boolean acceptUraniumPulse(IReactor paramIReactor, ItemStack paramItemStack1, ItemStack paramItemStack2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
  
  boolean canStoreHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2);
  
  int getMaxHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2);
  
  int getCurrentHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2);
  
  int alterHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3);
  
  float influenceExplosion(IReactor paramIReactor, ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\reactor\IReactorComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */