package ic2.api.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface ILatheItem {
  int getWidth(ItemStack paramItemStack);
  
  int[] getCurrentState(ItemStack paramItemStack);
  
  void setState(ItemStack paramItemStack, int paramInt1, int paramInt2);
  
  ItemStack getOutputItem(ItemStack paramItemStack, int paramInt);
  
  float getOutputChance(ItemStack paramItemStack, int paramInt);
  
  @SideOnly(Side.CLIENT)
  ResourceLocation getTexture(ItemStack paramItemStack);
  
  int getHardness(ItemStack paramItemStack);
  
  public static interface ILatheTool extends ICustomDamageItem {
    int getHardness(ItemStack param1ItemStack);
  }
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\ILatheItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */