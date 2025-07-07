package ic2.api.energy.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergySink extends IEnergyAcceptor {
  double getDemandedEnergy();
  
  int getSinkTier();
  
  double injectEnergy(ForgeDirection paramForgeDirection, double paramDouble1, double paramDouble2);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IEnergySink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */