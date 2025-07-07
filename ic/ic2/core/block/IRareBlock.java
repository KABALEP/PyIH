package ic2.core.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface IRareBlock {
  @SideOnly(Side.CLIENT)
  EnumRarity getRarity(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\IRareBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */