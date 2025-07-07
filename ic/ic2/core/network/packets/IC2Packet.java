package ic2.core.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class IC2Packet {
  public abstract void read(ByteBuf paramByteBuf);
  
  public abstract void write(ByteBuf paramByteBuf);
  
  public abstract void handlePacket(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\IC2Packet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */