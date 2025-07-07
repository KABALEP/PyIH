package ic2.api.util;

import net.minecraft.entity.player.EntityPlayer;

public interface IKeyboard {
  boolean isAltKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isBoostKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isForwardKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isJumpKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isModeSwitchKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isSideinventoryKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isHudModeKeyDown(EntityPlayer paramEntityPlayer);
  
  boolean isSneakKeyDown(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\ap\\util\IKeyboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */