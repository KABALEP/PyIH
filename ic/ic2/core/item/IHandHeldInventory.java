package ic2.core.item;

import ic2.core.IHasGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IHandHeldInventory {
  IHasGui getInventory(EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\IHandHeldInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */