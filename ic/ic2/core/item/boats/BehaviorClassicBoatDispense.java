/*    */ package ic2.core.item.boats;
/*    */ 
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BehaviorClassicBoatDispense
/*    */   extends BehaviorDefaultDispenseItem
/*    */ {
/* 15 */   private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack stack) {
/*    */     double d3;
/* 23 */     EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.func_82620_h());
/* 24 */     World world = p_82487_1_.func_82618_k();
/* 25 */     double d0 = p_82487_1_.func_82615_a() + (enumfacing.func_82601_c() * 1.125F);
/* 26 */     double d1 = p_82487_1_.func_82617_b() + (enumfacing.func_96559_d() * 1.125F);
/* 27 */     double d2 = p_82487_1_.func_82616_c() + (enumfacing.func_82599_e() * 1.125F);
/* 28 */     int i = p_82487_1_.func_82623_d() + enumfacing.func_82601_c();
/* 29 */     int j = p_82487_1_.func_82622_e() + enumfacing.func_96559_d();
/* 30 */     int k = p_82487_1_.func_82621_f() + enumfacing.func_82599_e();
/* 31 */     Material material = world.func_147439_a(i, j, k).func_149688_o();
/*    */ 
/*    */     
/* 34 */     if (Material.field_151586_h.equals(material)) {
/*    */       
/* 36 */       d3 = 1.0D;
/*    */     }
/*    */     else {
/*    */       
/* 40 */       if (!Material.field_151579_a.equals(material) || !Material.field_151586_h.equals(world.func_147439_a(i, j - 1, k).func_149688_o()))
/*    */       {
/* 42 */         return this.field_150842_b.func_82482_a(p_82487_1_, stack);
/*    */       }
/* 44 */       d3 = 0.0D;
/*    */     } 
/*    */     
/* 47 */     EntityClassicBoat entityboat = ItemIC2Boat.makeBoat(world, d0, d1 + d3, d2, stack.func_77960_j());
/* 48 */     if (entityboat == null)
/*    */     {
/* 50 */       return this.field_150842_b.func_82482_a(p_82487_1_, stack);
/*    */     }
/* 52 */     world.func_72838_d(entityboat);
/* 53 */     stack.func_77979_a(1);
/* 54 */     return stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_82485_a(IBlockSource p_82485_1_) {
/* 62 */     p_82485_1_.func_82618_k().func_72926_e(1000, p_82485_1_.func_82623_d(), p_82485_1_.func_82622_e(), p_82485_1_.func_82621_f(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\BehaviorClassicBoatDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */