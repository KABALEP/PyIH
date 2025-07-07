/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemToolWrenchElectric
/*     */   extends ItemToolWrench
/*     */   implements IElectricTool
/*     */ {
/*     */   public ItemToolWrenchElectric(int index) {
/*  25 */     super(index);
/*  26 */     func_77656_e(27);
/*  27 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  33 */     if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(entityplayer)) {
/*     */       
/*  35 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/*  36 */       boolean newValue = !nbtData.func_74767_n("losslessMode");
/*  37 */       nbtData.func_74757_a("losslessMode", newValue);
/*  38 */       if (newValue) {
/*     */         
/*  40 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.losslessWrenchModeEnabled.name"));
/*     */       }
/*     */       else {
/*     */         
/*  44 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.losslessWrenchModeDisabled.name"));
/*     */       } 
/*     */     } 
/*  47 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/*  53 */     return (!IC2.keyboard.isModeSwitchKeyDown(entityPlayer) && super.onItemUseFirst(itemstack, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  59 */     amount *= 50;
/*  60 */     return (ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, true) == amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void damage(ItemStack itemStack, int amount, EntityPlayer player) {
/*  66 */     ElectricItem.manager.use(itemStack, (50 * amount), (EntityLivingBase)player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/*  72 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/*  78 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack par1) {
/*  90 */     return 12000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack par1) {
/*  96 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack par1) {
/* 102 */     return 250.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 107 */     ItemStack charged = new ItemStack((Item)this, 1);
/* 108 */     ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 109 */     itemList.add(charged);
/* 110 */     itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideWrenchSuccessRate(ItemStack itemStack) {
/* 116 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
/* 117 */     return nbtData.func_74767_n("losslessMode");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getModifier(ItemStack stack) {
/* 123 */     return 1.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 129 */     return EnumEnchantmentType.breakable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 135 */     return (ench == Enchantment.field_77346_s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolWrenchElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */