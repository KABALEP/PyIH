package ic2.api.energy;

import java.util.List;
import net.minecraft.tileentity.TileEntity;

public interface IPacketEnergyNet extends IEnergyNet {
  List<PacketStat> getSendedPackets(TileEntity paramTileEntity);
  
  List<PacketStat> getTotalSendedPackets(TileEntity paramTileEntity);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\IPacketEnergyNet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */