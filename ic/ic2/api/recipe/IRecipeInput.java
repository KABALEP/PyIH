package ic2.api.recipe;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IRecipeInput {
  boolean matches(ItemStack paramItemStack);
  
  int getAmount();
  
  List<ItemStack> getInputs();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IRecipeInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */