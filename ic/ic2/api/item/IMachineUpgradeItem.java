package ic2.api.item;

import ic2.api.tile.IMachine;
import java.util.List;
import net.minecraft.item.ItemStack;

public interface IMachineUpgradeItem {
  int getExtraProcessTime(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getProcessTimeMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraEnergyDemand(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getEnergyDemandMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraEnergyStorage(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getEnergyStorageMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraTier(ItemStack paramItemStack, IMachine paramIMachine);
  
  boolean onTick(ItemStack paramItemStack, IMachine paramIMachine);
  
  void onProcessEnd(ItemStack paramItemStack, IMachine paramIMachine, List<ItemStack> paramList);
  
  boolean useRedstoneinverter(ItemStack paramItemStack, IMachine paramIMachine);
  
  void onInstalling(ItemStack paramItemStack, IMachine paramIMachine);
  
  float getSoundVolumeMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  IMachine.UpgradeType getType(ItemStack paramItemStack);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IMachineUpgradeItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */