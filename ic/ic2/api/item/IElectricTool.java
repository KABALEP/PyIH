package ic2.api.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public interface IElectricTool extends IElectricItem {
  EnumEnchantmentType getType(ItemStack paramItemStack);
  
  boolean isSpecialSupport(ItemStack paramItemStack, Enchantment paramEnchantment);
  
  boolean isExcluded(ItemStack paramItemStack, Enchantment paramEnchantment);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IElectricTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */