/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.util.IExtraData;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ 
/*    */ public class BlockMetal
/*    */   extends Block
/*    */   implements IExtraData
/*    */ {
/*    */   public BlockMetal() {
/* 25 */     super(Material.field_151573_f);
/* 26 */     func_149711_c(4.0F);
/* 27 */     func_149672_a(field_149777_j);
/* 28 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_149692_a(int i) {
/* 34 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int side, int meta) {
/* 46 */     switch (meta) {
/*    */ 
/*    */       
/*    */       case 0:
/* 50 */         return Ic2Icons.getTexture("b0")[93];
/*    */ 
/*    */       
/*    */       case 1:
/* 54 */         return Ic2Icons.getTexture("b0")[94];
/*    */ 
/*    */       
/*    */       case 2:
/* 58 */         return Ic2Icons.getTexture("b0")[78];
/*    */ 
/*    */       
/*    */       case 3:
/* 62 */         return (side < 2) ? Ic2Icons.getTexture("b0")[79] : Ic2Icons.getTexture("b0")[95];
/*    */     } 
/*    */ 
/*    */     
/* 66 */     return Ic2Icons.getTexture("b0")[60];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149666_a(Item j, CreativeTabs tabs, List<ItemStack> itemList) {
/* 74 */     for (int i = 0; i < 16; i++) {
/*    */       
/* 76 */       ItemStack is = new ItemStack(this, 1, i);
/* 77 */       if (j.func_77667_c(is) != null)
/*    */       {
/* 79 */         itemList.add(is);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
/* 87 */     int meta = worldObj.func_72805_g(x, y, z);
/* 88 */     return (meta == 2 || meta == 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 94 */     Ic2Items.bronzeBlock = new ItemStack(this, 1, 2);
/* 95 */     Ic2Items.copperBlock = new ItemStack(this, 1, 0);
/* 96 */     Ic2Items.tinBlock = new ItemStack(this, 1, 1);
/* 97 */     Ic2Items.uraniumBlock = new ItemStack(this, 1, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockMetal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */