package ic2.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchable {
  boolean wrenchCanSetFacing(EntityPlayer paramEntityPlayer, int paramInt);
  
  short getFacing();
  
  void setFacing(short paramShort);
  
  boolean wrenchCanRemove(EntityPlayer paramEntityPlayer);
  
  float getWrenchDropRate();
  
  ItemStack getWrenchDrop(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\tile\IWrenchable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */