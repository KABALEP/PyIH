package ic2.core.block.wiring;

public interface IElectrolyzerProvider {
  int getProcessRate();
  
  int getTier();
  
  void drawPower(int paramInt);
  
  void addPower(int paramInt);
  
  int getStoredPower();
  
  int getMaxStorage();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\IElectrolyzerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */