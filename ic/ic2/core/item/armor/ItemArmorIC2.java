/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.item.IMetalArmor;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ public class ItemArmorIC2
/*    */   extends ItemArmor
/*    */   implements IMetalArmor
/*    */ {
/*    */   private ItemStack repairMaterial;
/*    */   private int iconIndex;
/*    */   private boolean comp;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */   
/*    */   public ItemArmorIC2(int index, ItemArmor.ArmorMaterial enumArmorMaterial, int k, int l, ItemStack repairMaterial, boolean comp) {
/* 28 */     super(enumArmorMaterial, k, l);
/* 29 */     this.iconIndex = index;
/* 30 */     func_77656_e(enumArmorMaterial.func_78046_a(l));
/* 31 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/* 32 */     this.repairMaterial = repairMaterial;
/* 33 */     this.comp = comp;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 39 */     return Ic2Icons.getTexture("i2")[this.iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
/* 49 */     return (stack2 != null && stack2.func_77969_a(this.repairMaterial));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 54 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
/* 60 */     String text = this.comp ? "alloy" : "bronze";
/* 61 */     if (slot == 2)
/*    */     {
/* 63 */       return "ic2:textures/models/armor/" + text + "_2.png";
/*    */     }
/* 65 */     return "ic2:textures/models/armor/" + text + "_1.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */