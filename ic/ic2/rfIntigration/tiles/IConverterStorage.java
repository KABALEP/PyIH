package ic2.rfIntigration.tiles;

public interface IConverterStorage {
  int getFreeSpace();
  
  int charge(int paramInt);
  
  int useEnergy(int paramInt);
  
  int useEnergy(int paramInt, boolean paramBoolean);
  
  float getLoss();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\tiles\IConverterStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */