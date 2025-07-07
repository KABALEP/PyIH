package ic2.api.reactor;

public interface IReactorPlanner extends IReactor {
  boolean isCollecting();
  
  void addFuelPulse();
  
  void addReEnrichPulse();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\reactor\IReactorPlanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */