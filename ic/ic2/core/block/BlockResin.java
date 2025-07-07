/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.util.IExtraData;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockResin
/*    */   extends BlockTex
/*    */   implements IExtraData
/*    */ {
/*    */   public BlockResin(int sprite) {
/* 21 */     super(sprite, Material.field_151594_q);
/* 22 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/* 23 */     func_149711_c(1.6F);
/* 24 */     func_149752_b(0.5F);
/* 25 */     func_149672_a(field_149776_m);
/* 26 */     func_149663_c("blockHarz");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 52 */     return Ic2Items.resin.func_77973_b();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_149745_a(Random random) {
/* 58 */     return (random.nextInt(5) != 0) ? 1 : 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149742_c(World world, int i, int j, int k) {
/* 64 */     Block l = world.func_147439_a(i, j - 1, k);
/* 65 */     return (l != null && l.func_149662_c() && l.func_149688_o().func_76220_a());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/* 71 */     if (!func_149742_c(world, i, j, k)) {
/*    */       
/* 73 */       func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
/* 74 */       world.func_147468_f(i, j, k);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/* 81 */     entity.field_70143_R *= 0.75F;
/* 82 */     entity.field_70159_w *= 0.6000000238418579D;
/* 83 */     entity.field_70181_x *= 0.8500000238418579D;
/* 84 */     entity.field_70179_y *= 0.6000000238418579D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 90 */     Ic2Items.resinSheet = new ItemStack(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockResin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */