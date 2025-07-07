/*    */ package ic2.core.block.machine;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.BlockTex;
/*    */ import ic2.core.util.IExtraData;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockMiningTip
/*    */   extends BlockTex
/*    */   implements IExtraData
/*    */ {
/*    */   public BlockMiningTip(int sprite) {
/* 20 */     super(sprite, Material.field_151573_f);
/* 21 */     func_149711_c(6.0F);
/* 22 */     func_149752_b(10.0F);
/* 23 */     func_149663_c("blockMiningTip");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149742_c(World world, int i, int j, int k) {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 34 */     return Ic2Items.miningPipe.func_77973_b();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149666_a(Item i, CreativeTabs tabs, List itemList) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 44 */     Ic2Items.miningPipeTip = new ItemStack((Block)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\BlockMiningTip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */