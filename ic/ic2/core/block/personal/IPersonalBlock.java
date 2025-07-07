package ic2.core.block.personal;

import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;

public interface IPersonalBlock {
  boolean canAccess(EntityPlayer paramEntityPlayer);
  
  boolean canAccess(UUID paramUUID);
  
  IPersonalInventory getInventory(EntityPlayer paramEntityPlayer);
  
  IPersonalInventory getInventory(UUID paramUUID);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\IPersonalBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */