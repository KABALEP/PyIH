package ic2.core;

import net.minecraft.entity.player.EntityPlayer;

public interface IHasGui {
  ContainerIC2 getGuiContainer(EntityPlayer paramEntityPlayer);
  
  String getGuiClassName(EntityPlayer paramEntityPlayer);
  
  void onGuiClosed(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IHasGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */