/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemAxe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ public class ItemIC2Axe
/*    */   extends ItemAxe
/*    */ {
/*    */   private ItemStack repairMaterial;
/*    */   public float a;
/*    */   public int iconIndex;
/*    */   
/*    */   public ItemIC2Axe(int index, Item.ToolMaterial enumtoolmaterial, float efficiency, ItemStack repairMaterial) {
/* 25 */     super(enumtoolmaterial);
/* 26 */     this.a = efficiency;
/* 27 */     this.iconIndex = index;
/* 28 */     this.repairMaterial = repairMaterial;
/* 29 */     setHarvestLevel("axe", 2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 39 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 45 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77619_b() {
/* 50 */     return 13;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
/* 55 */     return (stack2 != null && stack2.func_77973_b() == Ic2Items.bronzeIngot.func_77973_b());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
/* 62 */     if (IC2.dissableBronzeStuff) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     super.func_150895_a(p_150895_1_, p_150895_2_, p_150895_3_);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemIC2Axe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */