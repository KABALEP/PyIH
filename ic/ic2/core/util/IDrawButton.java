package ic2.core.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public interface IDrawButton {
  void onPostDraw(Minecraft paramMinecraft, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\IDrawButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */