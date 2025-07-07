package ic2.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITerraformerBP {
  int getConsume(ItemStack paramItemStack);
  
  int getRange(ItemStack paramItemStack);
  
  boolean terraform(ItemStack paramItemStack, World paramWorld, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\ITerraformerBP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */