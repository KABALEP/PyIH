/*    */ package ic2.core.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2Potion;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTerraWart extends ItemFood {
/*    */   private int iconIndex;
/*    */   
/*    */   public ItemTerraWart(int index) {
/* 24 */     super(0, 1.0F, false);
/* 25 */     this.iconIndex = index;
/* 26 */     func_77848_i();
/* 27 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 38 */     return Ic2Icons.getTexture("i0")[this.iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77849_c(ItemStack itemstack, World world, EntityPlayer player) {
/* 43 */     itemstack.field_77994_a--;
/* 44 */     world.func_72956_a((Entity)player, "random.burp", 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
/* 45 */     IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76431_k.field_76415_H);
/* 46 */     IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76419_f.field_76415_H);
/* 47 */     IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76438_s.field_76415_H);
/* 48 */     IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76421_d.field_76415_H);
/* 49 */     IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76437_t.field_76415_H);
/* 50 */     IC2.platform.removePotion((EntityLivingBase)player, IC2Potion.radiation.field_76415_H);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 56 */     return EnumRarity.rare;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 61 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemTerraWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */