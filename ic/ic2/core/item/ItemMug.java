/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.TileEntityBarrel;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemMug
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemMug(int index) {
/* 15 */     super(index);
/* 16 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/* 21 */     if (world.func_147439_a(i, j, k) != Block.func_149634_a(Ic2Items.blockBarrel.func_77973_b()))
/*    */     {
/* 23 */       return false;
/*    */     }
/* 25 */     TileEntityBarrel barrel = (TileEntityBarrel)world.func_147438_o(i, j, k);
/* 26 */     if (barrel.treetapSide < 2 || barrel.treetapSide != side)
/*    */     {
/* 28 */       return false;
/*    */     }
/* 30 */     int value = barrel.calculateMetaValue();
/* 31 */     if (barrel.drainLiquid(1) && IC2.platform.isSimulating()) {
/*    */       
/* 33 */       ItemStack is = new ItemStack(Ic2Items.mugBooze.func_77973_b(), 1, value);
/* 34 */       if ((entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c]).field_77994_a > 1) {
/*    */         
/* 36 */         ItemStack itemStack = entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c];
/* 37 */         itemStack.field_77994_a--;
/* 38 */         if (!entityplayer.field_71071_by.func_70441_a(is))
/*    */         {
/* 40 */           entityplayer.func_71019_a(is, false);
/*    */         }
/*    */       }
/*    */       else {
/*    */         
/* 45 */         entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = is;
/*    */       } 
/* 47 */       return true;
/*    */     } 
/* 49 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemMug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */