package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchHandler {
  boolean supportsItem(ItemStack paramItemStack);
  
  boolean canWrench(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer);
  
  void useWrench(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer);
  
  public static interface IWrenchRegistry {
    void registerWrenchSupporter(IWrenchHandler param1IWrenchHandler);
  }
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IWrenchHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */