package ic2.core.block.inventory;

import net.minecraft.item.ItemStack;

public interface IInvSlot {
  int getSlotIndex();
  
  ItemStack getStack();
  
  ItemStack decreaseStackInSlot();
  
  ItemStack decreaseStackInSlot(int paramInt);
  
  void setStack(ItemStack paramItemStack);
  
  boolean canInsertStack(ItemStack paramItemStack);
  
  boolean canRemoveStack(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\IInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */