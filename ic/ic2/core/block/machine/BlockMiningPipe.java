/*    */ package ic2.core.block.machine;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.BlockTex;
/*    */ import ic2.core.util.IExtraData;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockMiningPipe extends BlockTex implements IExtraData {
/*    */   public BlockMiningPipe(int sprite) {
/* 16 */     super(sprite, Material.field_151573_f);
/* 17 */     func_149711_c(6.0F);
/* 18 */     func_149752_b(10.0F);
/* 19 */     func_149663_c("blockMiningPipe");
/* 20 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149742_c(World world, int i, int j, int k) {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBlockNormalCube(World world, int i, int j, int k) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149645_b() {
/* 45 */     return IC2.platform.getRenderId("miningPipe");
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 50 */     return AxisAlignedBB.func_72330_a((i + 0.375F), j, (k + 0.375F), (i + 0.625F), (j + 1.0F), (k + 0.625F));
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
/* 55 */     return AxisAlignedBB.func_72330_a((i + 0.375F), j, (k + 0.375F), (i + 0.625F), (j + 1.0F), (k + 0.625F));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 61 */     Ic2Items.miningPipe = new ItemStack((Block)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\BlockMiningPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */