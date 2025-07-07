package ic2.api.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeManagerExp extends IMachineRecipeManagerExt {
  void addRecipe(IRecipeInput paramIRecipeInput, NBTTagCompound paramNBTTagCompound, float paramFloat, ItemStack... paramVarArgs);
  
  float getExpResult(ItemStack paramItemStack);
  
  Map<IRecipeInput, Float> getExpMap();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IMachineRecipeManagerExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */