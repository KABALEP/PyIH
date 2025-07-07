package ic2.api.reactor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface IReactor {
  ChunkCoordinates getPosition();
  
  World getWorld();
  
  int getHeat();
  
  void setHeat(int paramInt);
  
  int addHeat(int paramInt);
  
  int getMaxHeat();
  
  void setMaxHeat(int paramInt);
  
  void addEmitHeat(int paramInt);
  
  float getHeatEffectModifier();
  
  void setHeatEffectModifier(float paramFloat);
  
  float getReactorEnergyOutput();
  
  double getReactorEUEnergyOutput();
  
  float addOutput(float paramFloat);
  
  ItemStack getItemAt(int paramInt1, int paramInt2);
  
  void setItemAt(int paramInt1, int paramInt2, ItemStack paramItemStack);
  
  void explode();
  
  int getTickRate();
  
  boolean produceEnergy();
  
  void setRedstoneSignal(boolean paramBoolean);
  
  boolean isFluidCooled();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\reactor\IReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */