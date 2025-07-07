/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemResin
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemResin(int index) {
/* 15 */     super(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/* 20 */     if (world.func_147439_a(i, j, k) == Blocks.field_150331_J && world.func_72805_g(i, j, k) == side) {
/*    */       
/* 22 */       world.func_147465_d(i, j, k, (Block)Blocks.field_150320_F, side, 3);
/* 23 */       if (!entityplayer.field_71075_bZ.field_75098_d)
/*    */       {
/* 25 */         itemstack.field_77994_a--;
/*    */       }
/* 27 */       return true;
/*    */     } 
/* 29 */     if (side != 1)
/*    */     {
/* 31 */       return false;
/*    */     }
/* 33 */     j++;
/* 34 */     if (world.func_147439_a(i, j, k) != null || !Block.func_149634_a(Ic2Items.resinSheet.func_77973_b()).func_149742_c(world, i, j, k))
/*    */     {
/* 36 */       return false;
/*    */     }
/* 38 */     world.func_147449_b(i, j, k, Block.func_149634_a(Ic2Items.resinSheet.func_77973_b()));
/* 39 */     if (!entityplayer.field_71075_bZ.field_75098_d)
/*    */     {
/* 41 */       itemstack.field_77994_a--;
/*    */     }
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemResin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */