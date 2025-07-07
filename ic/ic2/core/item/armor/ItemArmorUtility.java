/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.common.ISpecialArmor;
/*    */ 
/*    */ 
/*    */ public abstract class ItemArmorUtility
/*    */   extends ItemArmor
/*    */   implements ISpecialArmor
/*    */ {
/*    */   private int iconIndex;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */   
/*    */   public ItemArmorUtility(int spriteIndex, int renderIndex, int type) {
/* 29 */     super(ItemArmor.ArmorMaterial.DIAMOND, renderIndex, type);
/* 30 */     this.iconIndex = spriteIndex;
/* 31 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 37 */     return Ic2Icons.getTexture("i2")[this.iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77619_b() {
/* 42 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRepairable() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 57 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 62 */     return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 67 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}
/*    */ 
/*    */   
/*    */   public abstract String getTextureName();
/*    */ 
/*    */   
/*    */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
/* 79 */     String name = getTextureName();
/* 80 */     if (slot == 2)
/*    */     {
/* 82 */       return "ic2:textures/models/armor/" + name + "_2.png";
/*    */     }
/* 84 */     return "ic2:textures/models/armor/" + name + "_1.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */