/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTFBPDesertification
/*    */   extends ItemTFBP
/*    */ {
/*    */   public ItemTFBPDesertification(int index) {
/* 14 */     super(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getConsume(ItemStack item) {
/* 20 */     return 2500;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack item) {
/* 26 */     return 40;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/* 32 */     int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 10);
/* 33 */     if (y == -1)
/*    */     {
/* 35 */       return false;
/*    */     }
/* 37 */     if (TileEntityTerra.switchGround(world, Blocks.field_150346_d, (Block)Blocks.field_150354_m, x, y, z, false) || TileEntityTerra.switchGround(world, (Block)Blocks.field_150349_c, (Block)Blocks.field_150354_m, x, y, z, false) || TileEntityTerra.switchGround(world, Blocks.field_150458_ak, (Block)Blocks.field_150354_m, x, y, z, false)) {
/*    */       
/* 39 */       TileEntityTerra.switchGround(world, Blocks.field_150346_d, (Block)Blocks.field_150354_m, x, y, z, false);
/* 40 */       return true;
/*    */     } 
/* 42 */     Block id = world.func_147439_a(x, y, z);
/* 43 */     if (id == Blocks.field_150355_j || id == Blocks.field_150358_i || id == Blocks.field_150433_aE || id == Blocks.field_150362_t || id == Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()) || isPlant(id)) {
/*    */       
/* 45 */       world.func_147449_b(x, y, z, Blocks.field_150350_a);
/* 46 */       return true;
/*    */     } 
/* 48 */     if (id == Blocks.field_150432_aD || id == Blocks.field_150433_aE) {
/*    */       
/* 50 */       world.func_147449_b(x, y, z, Blocks.field_150355_j);
/* 51 */       return true;
/*    */     } 
/* 53 */     if ((id == Blocks.field_150344_f || id == Blocks.field_150364_r || id == Blocks.field_150363_s || id == Block.func_149634_a(Ic2Items.rubberWood.func_77973_b())) && world.field_73012_v.nextInt(15) == 0) {
/*    */       
/* 55 */       world.func_147449_b(x, y, z, (Block)Blocks.field_150480_ab);
/* 56 */       return true;
/*    */     } 
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPlant(Block id) {
/* 63 */     for (int i = 0; i < ItemTFBPCultivation.plantIDs.size(); i++) {
/*    */       
/* 65 */       if (ItemTFBPCultivation.plantIDs.get(i) == id)
/*    */       {
/* 67 */         return true;
/*    */       }
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPDesertification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */