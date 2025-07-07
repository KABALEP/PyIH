/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBlockConverter
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemBlockConverter(Block par1) {
/* 19 */     super(par1);
/* 20 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77647_b(int p_77647_1_) {
/* 26 */     return p_77647_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack item, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
/* 33 */     int meta = item.func_77960_j();
/* 34 */     if (BlockConverter.isValidMeta(meta))
/*    */     {
/* 36 */       BlockConverter.getConverter(meta).addInfo(list);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack item) {
/* 43 */     int meta = item.func_77960_j();
/* 44 */     if (BlockConverter.isValidMeta(meta))
/*    */     {
/* 46 */       return BlockConverter.getConverter(meta).getUnlocizedName();
/*    */     }
/* 48 */     return super.func_77667_c(item);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */