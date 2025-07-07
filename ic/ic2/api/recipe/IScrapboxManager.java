package ic2.api.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;

public interface IScrapboxManager {
  void addDrop(ItemStack paramItemStack, float paramFloat);
  
  ItemStack getDrop(ItemStack paramItemStack, boolean paramBoolean);
  
  Map<ItemStack, Float> getDrops();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IScrapboxManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */