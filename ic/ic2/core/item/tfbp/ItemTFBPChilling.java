/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTFBPChilling
/*    */   extends ItemTFBP
/*    */ {
/*    */   public ItemTFBPChilling(int index) {
/* 13 */     super(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getConsume(ItemStack item) {
/* 19 */     return 2000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack item) {
/* 25 */     return 50;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/* 31 */     int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 10);
/* 32 */     if (y == -1)
/*    */     {
/* 34 */       return false;
/*    */     }
/* 36 */     Block id = world.func_147439_a(x, y, z);
/* 37 */     if (id == Blocks.field_150355_j || id == Blocks.field_150358_i) {
/*    */       
/* 39 */       world.func_147449_b(x, y, z, Blocks.field_150432_aD);
/* 40 */       return true;
/*    */     } 
/* 42 */     if (id == Blocks.field_150432_aD) {
/*    */       
/* 44 */       Block id2 = world.func_147439_a(x, y - 1, z);
/* 45 */       if (id2 == Blocks.field_150358_i || id2 == Blocks.field_150355_j) {
/*    */         
/* 47 */         world.func_147449_b(x, y - 1, z, Blocks.field_150432_aD);
/* 48 */         return true;
/*    */       } 
/*    */     } 
/* 51 */     if (id == Blocks.field_150431_aC && isSurroundedBySnow(world, x, y, z)) {
/*    */       
/* 53 */       world.func_147449_b(x, y, z, Blocks.field_150433_aE);
/* 54 */       return true;
/*    */     } 
/* 56 */     if (Blocks.field_150431_aC.func_149742_c(world, x, y + 1, z) || id == Blocks.field_150432_aD)
/*    */     {
/* 58 */       world.func_147449_b(x, y + 1, z, Blocks.field_150431_aC);
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSurroundedBySnow(World world, int x, int y, int z) {
/* 65 */     return (isSnowHere(world, x + 1, y, z) && isSnowHere(world, x - 1, y, z) && isSnowHere(world, x, y, z + 1) && isSnowHere(world, x, y, z - 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSnowHere(World world, int x, int y, int z) {
/* 70 */     int saveY = y;
/* 71 */     y = TileEntityTerra.getFirstBlockFrom(world, x, z, y + 16);
/* 72 */     if (saveY > y)
/*    */     {
/* 74 */       return false;
/*    */     }
/* 76 */     Block id = world.func_147439_a(x, y, z);
/* 77 */     if (id == Blocks.field_150433_aE || id == Blocks.field_150431_aC)
/*    */     {
/* 79 */       return true;
/*    */     }
/* 81 */     if (Blocks.field_150431_aC.func_149742_c(world, x, y + 1, z) || id == Blocks.field_150432_aD)
/*    */     {
/* 83 */       world.func_147449_b(x, y + 1, z, Blocks.field_150431_aC);
/*    */     }
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPChilling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */