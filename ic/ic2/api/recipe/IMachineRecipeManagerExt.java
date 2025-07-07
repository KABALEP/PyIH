package ic2.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeManagerExt extends IMachineRecipeManager {
  boolean addRecipe(IRecipeInput paramIRecipeInput, NBTTagCompound paramNBTTagCompound, boolean paramBoolean, ItemStack... paramVarArgs);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\IMachineRecipeManagerExt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */