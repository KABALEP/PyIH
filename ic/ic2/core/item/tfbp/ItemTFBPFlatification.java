/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemTFBPFlatification
/*    */   extends ItemTFBP
/*    */ {
/* 15 */   public static ArrayList<Block> removeIDs = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public ItemTFBPFlatification(int index) {
/* 19 */     super(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void init() {
/* 24 */     removeIDs.add(Blocks.field_150433_aE);
/* 25 */     removeIDs.add(Blocks.field_150432_aD);
/* 26 */     removeIDs.add(Blocks.field_150349_c);
/* 27 */     removeIDs.add(Blocks.field_150348_b);
/* 28 */     removeIDs.add(Blocks.field_150351_n);
/* 29 */     removeIDs.add(Blocks.field_150354_m);
/* 30 */     removeIDs.add(Blocks.field_150346_d);
/* 31 */     removeIDs.add(Blocks.field_150362_t);
/* 32 */     removeIDs.add(Blocks.field_150364_r);
/* 33 */     removeIDs.add(Blocks.field_150363_s);
/* 34 */     removeIDs.add(Blocks.field_150329_H);
/* 35 */     removeIDs.add(Blocks.field_150328_O);
/* 36 */     removeIDs.add(Blocks.field_150327_N);
/* 37 */     removeIDs.add(Blocks.field_150345_g);
/* 38 */     removeIDs.add(Blocks.field_150464_aj);
/* 39 */     removeIDs.add(Blocks.field_150337_Q);
/* 40 */     removeIDs.add(Blocks.field_150338_P);
/* 41 */     removeIDs.add(Blocks.field_150423_aK);
/* 42 */     if (Ic2Items.rubberLeaves != null)
/*    */     {
/* 44 */       removeIDs.add(Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()));
/*    */     }
/* 46 */     if (Ic2Items.rubberSapling != null)
/*    */     {
/* 48 */       removeIDs.add(Block.func_149634_a(Ic2Items.rubberSapling.func_77973_b()));
/*    */     }
/* 50 */     if (Ic2Items.rubberWood != null)
/*    */     {
/* 52 */       removeIDs.add(Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getConsume(ItemStack item) {
/* 59 */     return 4000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack item) {
/* 65 */     return 40;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/* 71 */     int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 20);
/* 72 */     if (y == -1)
/*    */     {
/* 74 */       return false;
/*    */     }
/* 76 */     if (world.func_147439_a(x, y, z) == Blocks.field_150433_aE)
/*    */     {
/* 78 */       y--;
/*    */     }
/* 80 */     if (y == yCoord)
/*    */     {
/* 82 */       return false;
/*    */     }
/* 84 */     if (y < yCoord) {
/*    */       
/* 86 */       world.func_147449_b(x, y + 1, z, Blocks.field_150346_d);
/* 87 */       return true;
/*    */     } 
/* 89 */     if (canRemove(world.func_147439_a(x, y, z))) {
/*    */       
/* 91 */       world.func_147449_b(x, y, z, Blocks.field_150350_a);
/* 92 */       return true;
/*    */     } 
/* 94 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canRemove(Block block) {
/* 99 */     return removeIDs.contains(block);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPFlatification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */