/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.crops.Crops;
/*    */ import ic2.core.IC2;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBlockCrop
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemBlockCrop(Block block) {
/* 23 */     super(block);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advStats) {
/* 30 */     if (advStats && IC2.enableCropHelper) {
/*    */       
/* 32 */       int x = (int)player.field_70165_t;
/* 33 */       int y = (int)player.field_70163_u - 1;
/* 34 */       int z = (int)player.field_70161_v;
/* 35 */       list.add(StatCollector.func_74837_a("itemInfo.cropAir.name", new Object[] { Byte.valueOf(getAirQuality(player.field_70170_p, x, y, z)) }));
/* 36 */       list.add(StatCollector.func_74837_a("itemInfo.cropHumidity.name", new Object[] { Byte.valueOf(getHumidity(player.field_70170_p, x, y, z)) }));
/* 37 */       list.add(StatCollector.func_74837_a("itemInfo.cropNutrient.name", new Object[] { Byte.valueOf(getNutrient(player.field_70170_p, x, y, z)) }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getAirQuality(World world, int xCoord, int yCoord, int zCoord) {
/* 43 */     int value = 0;
/* 44 */     int height = (yCoord - 64) / 15;
/* 45 */     if (height > 4)
/*    */     {
/* 47 */       height = 4;
/*    */     }
/* 49 */     if (height < 0)
/*    */     {
/* 51 */       height = 0;
/*    */     }
/* 53 */     value += height;
/* 54 */     int fresh = 9;
/* 55 */     for (int x = xCoord - 1; x < xCoord + 1 && fresh > 0; x++) {
/*    */       
/* 57 */       for (int z = zCoord - 1; z < zCoord + 1 && fresh > 0; z++) {
/*    */         
/* 59 */         if (world.func_147445_c(x, yCoord, z, false) || world.func_147438_o(x, yCoord, z) instanceof ic2.core.block.TileEntityCrop)
/*    */         {
/* 61 */           fresh--;
/*    */         }
/*    */       } 
/*    */     } 
/* 65 */     value += fresh / 2;
/* 66 */     if (world.func_72937_j(xCoord, yCoord + 1, zCoord))
/*    */     {
/* 68 */       value += 2;
/*    */     }
/* 70 */     return (byte)value;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getHumidity(World world, int xCoord, int yCoord, int zCoord) {
/* 75 */     int value = Crops.instance.getHumidityBiomeBonus(world.func_72959_q().func_76935_a(xCoord, zCoord));
/* 76 */     if (world.func_147439_a(xCoord, yCoord - 1, zCoord) == Blocks.field_150458_ak && world.func_72805_g(xCoord, yCoord - 1, zCoord) >= 7)
/*    */     {
/* 78 */       value += 2;
/*    */     }
/* 80 */     return (byte)value;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getNutrient(World world, int xCoord, int yCoord, int zCoord) {
/* 85 */     int value = Crops.instance.getNutrientBiomeBonus(world.func_72959_q().func_76935_a(xCoord, zCoord));
/* 86 */     for (int i = 2; i < 5 && world.func_147439_a(xCoord, yCoord - i, zCoord) == Blocks.field_150346_d; i++)
/*    */     {
/* 88 */       value++;
/*    */     }
/* 90 */     return (byte)value;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */