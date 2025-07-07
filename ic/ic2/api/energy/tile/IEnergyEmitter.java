package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyEmitter extends IEnergyTile {
  boolean emitsEnergyTo(TileEntity paramTileEntity, ForgeDirection paramForgeDirection);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IEnergyEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */