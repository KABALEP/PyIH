/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLeaves;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.ItemLeaves;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.ColorizerFoliage;
/*    */ 
/*    */ public class ItemRubLeaves
/*    */   extends ItemLeaves
/*    */ {
/*    */   public ItemRubLeaves(Block block) {
/* 18 */     super((BlockLeaves)block);
/* 19 */     func_77627_a(false);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_82790_a(ItemStack par1ItemStack, int par2) {
/* 25 */     return ColorizerFoliage.func_77469_b();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack par1ItemStack) {
/* 30 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77617_a(int par1) {
/* 35 */     return Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()).func_149691_a(0, par1);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister p_94581_1_) {}
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemRubLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */