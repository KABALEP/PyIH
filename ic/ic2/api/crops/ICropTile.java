package ic2.api.crops;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface ICropTile {
  CropCard getCrop();
  
  void setCrop(CropCard paramCropCard);
  
  @Deprecated
  short getID();
  
  @Deprecated
  void setID(short paramShort);
  
  byte getSize();
  
  void setSize(byte paramByte);
  
  byte getGrowth();
  
  void setGrowth(byte paramByte);
  
  byte getGain();
  
  void setGain(byte paramByte);
  
  byte getResistance();
  
  void setResistance(byte paramByte);
  
  byte getScanLevel();
  
  void setScanLevel(byte paramByte);
  
  NBTTagCompound getCustomData();
  
  int getNutrientStorage();
  
  void setNutrientStorage(int paramInt);
  
  int getHydrationStorage();
  
  void setHydrationStorage(int paramInt);
  
  int getWeedExStorage();
  
  void setWeedExStorage(int paramInt);
  
  byte getHumidity();
  
  byte getNutrients();
  
  byte getAirQuality();
  
  World getWorld();
  
  ChunkCoordinates getLocation();
  
  int getLightLevel();
  
  boolean pick(boolean paramBoolean);
  
  boolean harvest(boolean paramBoolean);
  
  ItemStack[] harvest_automated(boolean paramBoolean);
  
  void reset();
  
  void updateState();
  
  boolean isBlockBelow(Block paramBlock);
  
  boolean isBlockBelow(String paramString);
  
  ItemStack generateSeeds(CropCard paramCropCard, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);
  
  @Deprecated
  ItemStack generateSeeds(short paramShort, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\crops\ICropTile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */