package ic2.core.item.upgrades;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.tile.IMachine;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public interface IUpgradeMetaItem {
  int getExtraProcessTime(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getProcessTimeMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraEnergyDemand(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getEnergyDemandMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraEnergyStorage(ItemStack paramItemStack, IMachine paramIMachine);
  
  double getEnergyStorageMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  int getExtraTier(ItemStack paramItemStack, IMachine paramIMachine);
  
  boolean onTick(ItemStack paramItemStack, IMachine paramIMachine);
  
  boolean needsTick();
  
  void onProcessEnd(ItemStack paramItemStack, IMachine paramIMachine, List<ItemStack> paramList);
  
  boolean useRedstoneinverter(ItemStack paramItemStack, IMachine paramIMachine);
  
  void onInstalling(ItemStack paramItemStack, IMachine paramIMachine);
  
  float getSoundVolumeMultiplier(ItemStack paramItemStack, IMachine paramIMachine);
  
  boolean usesOwner();
  
  boolean usesDirection();
  
  IMachine.UpgradeType getType();
  
  String getName();
  
  IIcon getTexture();
  
  int getMaxStackSize();
  
  @SideOnly(Side.CLIENT)
  void addInfo(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List<String> paramList);
  
  boolean usesExtraRightClick(ItemStack paramItemStack);
  
  void onRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\IUpgradeMetaItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */