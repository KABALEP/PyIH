package ic2.api.tile;

import net.minecraft.entity.player.EntityPlayer;

public interface ISpecialWrenchable extends IWrenchable {
  boolean canDoSpecial(EntityPlayer paramEntityPlayer, int paramInt);
  
  boolean doSpecial(EntityPlayer paramEntityPlayer, int paramInt);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\tile\ISpecialWrenchable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */