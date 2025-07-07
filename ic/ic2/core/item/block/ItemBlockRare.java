/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.IRareBlock;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemBlockRare
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemBlockRare(Block block) {
/* 16 */     super(block);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 22 */     if (Block.func_149634_a(stack.func_77973_b()) instanceof IRareBlock)
/*    */     {
/* 24 */       return ((IRareBlock)Block.func_149634_a(stack.func_77973_b())).getRarity(stack);
/*    */     }
/* 26 */     return super.func_77613_e(stack);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister p_94581_1_) {}
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockRare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */