package ic2.api.energy.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface IKineticSource {
  int maxrequestkineticenergyTick(ForgeDirection paramForgeDirection);
  
  int requestkineticenergy(ForgeDirection paramForgeDirection, int paramInt);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IKineticSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */