package ic2.api.recipe;

import net.minecraft.item.ItemStack;

public interface ICraftingRecipeManager {
  void addRecipe(ItemStack paramItemStack, Object... paramVarArgs);
  
  void addShapelessRecipe(ItemStack paramItemStack, Object... paramVarArgs);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ICraftingRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */