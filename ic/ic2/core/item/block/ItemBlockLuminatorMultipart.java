/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.wiring.TileEntityCable;
/*    */ import ic2.core.block.wiring.TileEntityLuminatorMultipart;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBlockLuminatorMultipart
/*    */   extends ItemBlockRare
/*    */ {
/*    */   public ItemBlockLuminatorMultipart(Block block) {
/* 21 */     super(block);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset) {
/* 26 */     Block block = world.func_147439_a(x, y, z);
/*    */     
/* 28 */     if (item.field_77994_a == 0)
/*    */     {
/* 30 */       return false;
/*    */     }
/* 32 */     if (!player.func_82247_a(x, y, z, side, item))
/*    */     {
/* 34 */       return false;
/*    */     }
/* 36 */     if (y == 255 && this.field_150939_a.func_149688_o().func_76220_a())
/*    */     {
/* 38 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 42 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 43 */     if (tile instanceof TileEntityLuminatorMultipart) {
/*    */       
/* 45 */       TileEntityLuminatorMultipart lumi = (TileEntityLuminatorMultipart)tile;
/* 46 */       if (lumi.hasSide(side))
/*    */       {
/* 48 */         return false;
/*    */       }
/* 50 */       lumi.addLamp(side);
/* 51 */       if (!player.field_71075_bZ.field_75098_d)
/*    */       {
/* 53 */         item.field_77994_a--;
/*    */       }
/* 55 */       Block.SoundType type = Block.field_149778_k;
/* 56 */       world.func_72908_a((x + 0.5F), (y + 0.5F), (z + 0.5F), type.func_150496_b(), (type.func_150497_c() + 1.0F) / 2.0F, type.func_150494_d() * 0.8F);
/* 57 */       return true;
/*    */     } 
/* 59 */     if (tile instanceof TileEntityCable) {
/*    */       
/* 61 */       TileEntityCable cable = (TileEntityCable)tile;
/* 62 */       if (cable.cableType == 11 || cable.cableType == 12)
/*    */       {
/* 64 */         return false;
/*    */       }
/* 66 */       ItemStack itemCable = new ItemStack(Ic2Items.copperCableItem.func_77973_b(), 1, cable.cableType);
/* 67 */       if (world.func_147449_b(x, y, z, Block.func_149634_a(Ic2Items.luminatorMultipart.func_77973_b()))) {
/*    */         
/* 69 */         TileEntity newTile = world.func_147438_o(x, y, z);
/* 70 */         if (newTile instanceof TileEntityLuminatorMultipart) {
/*    */           
/* 72 */           TileEntityLuminatorMultipart lumi = (TileEntityLuminatorMultipart)newTile;
/* 73 */           lumi.setCable(itemCable, cable.color, side);
/* 74 */           if (!player.field_71075_bZ.field_75098_d)
/*    */           {
/* 76 */             item.field_77994_a--;
/*    */           }
/* 78 */           Block.SoundType type = Block.field_149778_k;
/* 79 */           world.func_72908_a((x + 0.5F), (y + 0.5F), (z + 0.5F), type.func_150496_b(), (type.func_150497_c() + 1.0F) / 2.0F, type.func_150494_d() * 0.8F);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockLuminatorMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */