/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.util.IExtraData;
/*    */ import net.minecraft.block.BlockContainer;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.Explosion;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class BlockContainerCommon
/*    */   extends BlockContainer
/*    */   implements IRareBlock, IExtraData
/*    */ {
/*    */   public BlockContainerCommon(Material par3Material) {
/* 19 */     super(par3Material);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149723_a(World world, int x, int y, int z, Explosion ex) {
/* 25 */     world.func_147475_p(x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 36 */     return EnumRarity.common;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockContainerCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */