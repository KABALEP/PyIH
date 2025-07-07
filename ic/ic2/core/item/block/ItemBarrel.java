/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.BlockScaffold;
/*    */ import ic2.core.block.TileEntityBarrel;
/*    */ import ic2.core.item.ItemBooze;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBarrel
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemBarrel(int index) {
/* 18 */     super(index);
/* 19 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemDisplayName(ItemStack itemstack) {
/* 24 */     int v = ItemBooze.getAmountOfValue(itemstack.func_77960_j());
/* 25 */     if (v > 0)
/*    */     {
/* 27 */       return StatCollector.func_74837_a("item.filledBarrel.name", new Object[] { Integer.valueOf(v) });
/*    */     }
/* 29 */     return StatCollector.func_74838_a("item.emptyBarrel.name");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/* 36 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.scaffold.func_77973_b()) && world.func_72805_g(i, j, k) < BlockScaffold.reinforcedStrength) {
/*    */       
/* 38 */       world.func_147449_b(i, j, k, Block.func_149634_a(Ic2Items.blockBarrel.func_77973_b()));
/* 39 */       ((TileEntityBarrel)world.func_147438_o(i, j, k)).set(itemstack.func_77960_j());
/* 40 */       if (!entityplayer.field_71075_bZ.field_75098_d) {
/*    */         
/* 42 */         ItemStack itemStack = entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c];
/* 43 */         itemStack.field_77994_a--;
/* 44 */         if ((entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c]).field_77994_a == 0)
/*    */         {
/* 46 */           entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/*    */         }
/*    */       } 
/* 49 */       return true;
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBarrel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */