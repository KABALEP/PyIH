/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemTinCan
/*     */   extends ItemFood
/*     */ {
/*     */   private int iconIndex;
/*     */   
/*     */   public ItemTinCan(int index) {
/*  28 */     super(2, 0.95F, false);
/*  29 */     func_77627_a(true);
/*  30 */     this.iconIndex = index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  41 */     return Ic2Icons.getTexture("i0")[this.iconIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77849_c(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  47 */     super.func_77849_c(itemstack, world, entityplayer);
/*  48 */     onCanEaten(itemstack, world, entityplayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCanEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
/*  53 */     par3EntityPlayer.func_70691_i(2.0F);
/*  54 */     ItemStack is = Ic2Items.tinCan.func_77946_l();
/*  55 */     if (!par3EntityPlayer.field_71071_by.func_70441_a(is))
/*     */     {
/*  57 */       par3EntityPlayer.func_71019_a(is, false);
/*     */     }
/*  59 */     switch (par1ItemStack.func_77960_j()) {
/*     */ 
/*     */       
/*     */       case 1:
/*  63 */         if (par3EntityPlayer.func_70681_au().nextFloat() < 0.8F)
/*     */         {
/*  65 */           par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76438_s.field_76415_H, 600 / ((ItemFood)Items.field_151078_bh).func_150905_g(par1ItemStack) / 2, 0));
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/*  72 */         par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 100 / ((ItemFood)Items.field_151070_bp).func_150905_g(par1ItemStack) / 2, 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/*  77 */         if (par3EntityPlayer.func_70681_au().nextFloat() < 0.3F)
/*     */         {
/*  79 */           par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76438_s.field_76415_H, 600 / ((ItemFood)Items.field_151076_bf).func_150905_g(par1ItemStack) / 2, 0));
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/*  86 */         par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76428_l.field_76415_H, 100 / ((ItemFood)Items.field_151153_ao).func_150905_g(par1ItemStack) / 2, 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/*  91 */         par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76428_l.field_76415_H, 600 / ((ItemFood)Items.field_151153_ao).func_150905_g(par1ItemStack) / 2, 3));
/*  92 */         par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76429_m.field_76415_H, 6000 / ((ItemFood)Items.field_151153_ao).func_150905_g(par1ItemStack) / 2, 0));
/*  93 */         par3EntityPlayer.func_70690_d(new PotionEffect(Potion.field_76426_n.field_76415_H, 6000 / ((ItemFood)Items.field_151153_ao).func_150905_g(par1ItemStack) / 2, 0));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> info, boolean debugTooltips) {
/* 101 */     int meta = stack.func_77960_j();
/* 102 */     if (meta == 1 || meta == 2 || meta == 3)
/*     */     {
/* 104 */       info.add("This looks bad...");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemstack) {
/* 110 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/* 115 */     return "/ic2/sprites/item_0.png";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemTinCan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */