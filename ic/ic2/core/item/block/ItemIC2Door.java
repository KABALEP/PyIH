/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemDoor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemIC2Door
/*    */   extends ItemDoor
/*    */ {
/*    */   public Block block;
/*    */   private int iconIndex;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */   
/*    */   public ItemIC2Door(int index, Block doorblock) {
/* 28 */     super(Material.field_151573_f);
/* 29 */     this.iconIndex = index;
/* 30 */     this.block = doorblock;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77617_a(int par1) {
/* 35 */     return Ic2Icons.getTexture("i0")[this.iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 40 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float a, float b, float c) {
/* 45 */     if (par7 != 1)
/*    */     {
/* 47 */       return false;
/*    */     }
/* 49 */     par5++;
/* 50 */     if (!par2EntityPlayer.func_82246_f(par4, par5, par6) || !par2EntityPlayer.func_82246_f(par4, par5 + 1, par6))
/*    */     {
/* 52 */       return false;
/*    */     }
/* 54 */     if (!this.block.func_149742_c(par3World, par4, par5, par6))
/*    */     {
/* 56 */       return false;
/*    */     }
/* 58 */     int var9 = MathHelper.func_76128_c(((par2EntityPlayer.field_70177_z + 180.0F) * 4.0F / 360.0F) - 0.5D) & 0x3;
/* 59 */     func_150924_a(par3World, par4, par5, par6, var9, this.block);
/* 60 */     par1ItemStack.field_77994_a--;
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemIC2Door.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */