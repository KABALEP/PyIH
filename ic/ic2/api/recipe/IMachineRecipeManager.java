package ic2.api.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeManager {
  void addRecipe(IRecipeInput paramIRecipeInput, NBTTagCompound paramNBTTagCompound, ItemStack... paramVarArgs);
  
  RecipeOutput getOutputFor(ItemStack paramItemStack, boolean paramBoolean);
  
  Map<IRecipeInput, RecipeOutput> getRecipes();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IMachineRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */