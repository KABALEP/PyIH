package ic2.api.energy.tile;

public interface IEnergyConductor extends IEnergyAcceptor, IEnergyEmitter {
  double getConductionLoss();
  
  double getInsulationEnergyAbsorption();
  
  double getInsulationBreakdownEnergy();
  
  double getConductorBreakdownEnergy();
  
  void removeInsulation();
  
  void removeConductor();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IEnergyConductor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */