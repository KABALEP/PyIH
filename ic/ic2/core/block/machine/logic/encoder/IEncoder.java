package ic2.core.block.machine.logic.encoder;

import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
import net.minecraft.nbt.NBTTagCompound;

public interface IEncoder {
  NBTTagCompound createDecodedData(String paramString);
  
  void processData(NBTTagCompound paramNBTTagCompound, TileEntityReactorPlanner paramTileEntityReactorPlanner);
  
  String createEncodedData(TileEntityReactorPlanner paramTileEntityReactorPlanner);
  
  String getName();
  
  boolean hasBitLimit();
  
  int getBitLimit();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\IEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */