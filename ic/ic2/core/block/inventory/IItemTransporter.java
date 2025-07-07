package ic2.core.block.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IItemTransporter {
  ItemStack addItem(ItemStack paramItemStack, ForgeDirection paramForgeDirection, boolean paramBoolean);
  
  ItemStack removeItem(IFilter paramIFilter, ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean);
  
  public static interface IFilter {
    boolean matches(ItemStack param1ItemStack);
  }
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\IItemTransporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */