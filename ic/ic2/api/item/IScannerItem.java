package ic2.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IScannerItem {
  int startLayerScan(ItemStack paramItemStack);
  
  boolean isValuableOre(ItemStack paramItemStack, Block paramBlock, int paramInt);
  
  int getOreValue(ItemStack paramItemStack, Block paramBlock, int paramInt);
  
  int getOreValueOfArea(ItemStack paramItemStack, World paramWorld, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IScannerItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */