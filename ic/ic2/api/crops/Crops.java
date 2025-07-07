package ic2.api.crops;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public abstract class Crops {
  public static Crops instance;
  
  public static CropCard weed;
  
  public abstract void addBiomenutrientsBonus(BiomeDictionary.Type paramType, int paramInt);
  
  public abstract void addBiomehumidityBonus(BiomeDictionary.Type paramType, int paramInt);
  
  public abstract int getHumidityBiomeBonus(BiomeGenBase paramBiomeGenBase);
  
  public abstract int getNutrientBiomeBonus(BiomeGenBase paramBiomeGenBase);
  
  public abstract CropCard getCropCard(String paramString1, String paramString2);
  
  public abstract CropCard getCropCard(ItemStack paramItemStack);
  
  public abstract Collection<CropCard> getCrops();
  
  @Deprecated
  public abstract CropCard[] getCropList();
  
  public abstract short registerCrop(CropCard paramCropCard);
  
  public abstract boolean registerCrop(CropCard paramCropCard, int paramInt);
  
  @Deprecated
  public abstract boolean registerBaseSeed(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract boolean registerBaseSeed(ItemStack paramItemStack, CropCard paramCropCard, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract BaseSeed getBaseSeed(ItemStack paramItemStack);
  
  @SideOnly(Side.CLIENT)
  public abstract void startSpriteRegistration(IIconRegister paramIIconRegister);
  
  @Deprecated
  public abstract int getIdFor(CropCard paramCropCard);
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\crops\Crops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */