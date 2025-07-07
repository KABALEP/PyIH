package ic2.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IElectricHelper {
  boolean chargeFromArmor(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  IInventory getBaublesInventory(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\IElectricHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */