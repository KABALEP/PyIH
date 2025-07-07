/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IItemTickListener;
/*     */ import ic2.core.util.KeyboardClient;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ public class ItemArmorNightvisionGoggles
/*     */   extends ItemArmorUtility
/*     */   implements IElectricItem, IItemTickListener
/*     */ {
/*     */   public ItemArmorNightvisionGoggles(int spriteIndex, int renderIndex) {
/*  26 */     super(spriteIndex, renderIndex, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/*  39 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/*  45 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/*  51 */     return 20000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack itemStack) {
/*  57 */     return 200.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack par1) {
/*  63 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(EntityPlayer player, ItemStack itemStack) {
/*  69 */     if (player.field_70170_p.field_72995_K)
/*     */     {
/*  71 */       return false;
/*     */     }
/*  73 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemStack);
/*  74 */     int ticker = nbt.func_74762_e("Ticker");
/*  75 */     if (ticker > 0)
/*     */     {
/*  77 */       ticker--;
/*     */     }
/*  79 */     boolean enabled = nbt.func_74767_n("Enabled");
/*  80 */     if (ticker <= 0 && IC2.keyboard.isHudModeKeyDown(player)) {
/*     */       
/*  82 */       ticker = 20;
/*  83 */       enabled = !enabled;
/*  84 */       nbt.func_74757_a("Enabled", enabled);
/*  85 */       IC2.platform.messagePlayer(player, enabled ? "Enabled NightVision" : "Disabled NightVision");
/*     */     } 
/*  87 */     if (enabled)
/*     */     {
/*  89 */       if (ElectricItem.manager.use(itemStack, 1.0D, (EntityLivingBase)player)) {
/*     */         
/*  91 */         IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76440_q.func_76396_c());
/*  92 */         player.func_70690_d(new PotionEffect(Potion.field_76439_r.func_76396_c(), 250, 0, true));
/*     */       } 
/*     */     }
/*  95 */     nbt.func_74768_a("Ticker", ticker);
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 102 */     ItemStack stack = new ItemStack((Item)this, 1);
/* 103 */     if (getChargedItem(stack) == this) {
/*     */       
/* 105 */       ItemStack charged = new ItemStack((Item)this, 1);
/* 106 */       ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 107 */       itemList.add(charged);
/*     */     } 
/* 109 */     if (getEmptyItem(stack) == this)
/*     */     {
/* 111 */       itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 118 */     return "nightvision";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean adv) {
/* 125 */     super.func_77624_a(stack, player, list, adv);
/* 126 */     list.add("Press HudKey (" + ((KeyboardClient)IC2.keyboard).getKey(4) + ") To toggle the NightVision");
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorNightvisionGoggles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */