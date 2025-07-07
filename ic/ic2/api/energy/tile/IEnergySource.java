package ic2.api.energy.tile;

public interface IEnergySource extends IEnergyEmitter {
  double getOfferedEnergy();
  
  void drawEnergy(double paramDouble);
  
  int getSourceTier();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IEnergySource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */