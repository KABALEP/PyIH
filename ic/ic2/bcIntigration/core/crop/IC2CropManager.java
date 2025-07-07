/*    */ package ic2.bcIntigration.core.crop;
/*    */ 
/*    */ import buildcraft.api.crops.ICropHandler;
/*    */ import ic2.api.crops.CropCard;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IC2CropManager
/*    */   implements ICropHandler
/*    */ {
/*    */   public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> list) {
/* 30 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 31 */     if (tile instanceof ICropTile) {
/*    */       
/* 33 */       ICropTile crop = (ICropTile)tile;
/* 34 */       boolean optimal = (crop.getCrop() != null && crop.getCrop().getOptimalHavestSize(crop) == crop.getSize());
/* 35 */       list.addAll(Arrays.asList(crop.harvest_automated(optimal)));
/* 36 */       return true;
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMature(IBlockAccess world, Block block, int meta, int x, int y, int z) {
/* 44 */     if (block instanceof ic2.core.block.BlockCrop) {
/*    */       
/* 46 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 47 */       if (tile instanceof ICropTile) {
/*    */         
/* 49 */         ICropTile crop = (ICropTile)tile;
/* 50 */         if (crop.getCrop() != null) {
/*    */           
/* 52 */           CropCard card = crop.getCrop();
/* 53 */           if (card.canBeHarvested(crop))
/*    */           {
/* 55 */             return true;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSeed(ItemStack item) {
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean plantCrop(World world, EntityPlayer player, ItemStack item, int x, int y, int z) {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\crop\IC2CropManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */