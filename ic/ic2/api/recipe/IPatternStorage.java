package ic2.api.recipe;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IPatternStorage {
  boolean addPattern(ItemStack paramItemStack);
  
  List<ItemStack> getPatterns();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IPatternStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */