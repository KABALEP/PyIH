package ic2.api.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyStorage {
  int getStored();
  
  void setStored(int paramInt);
  
  int addEnergy(int paramInt);
  
  int getCapacity();
  
  int getOutput();
  
  double getOutputEnergyUnitsPerTick();
  
  boolean isTeleporterCompatible(ForgeDirection paramForgeDirection);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\tile\IEnergyStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */