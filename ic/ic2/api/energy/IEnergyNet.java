package ic2.api.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyNet {
  TileEntity getTileEntity(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
  
  TileEntity getNeighbor(TileEntity paramTileEntity, ForgeDirection paramForgeDirection);
  
  @Deprecated
  double getTotalEnergyEmitted(TileEntity paramTileEntity);
  
  @Deprecated
  double getTotalEnergySunken(TileEntity paramTileEntity);
  
  NodeStats getNodeStats(TileEntity paramTileEntity);
  
  double getPowerFromTier(int paramInt);
  
  int getTierFromPower(double paramDouble);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\IEnergyNet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */