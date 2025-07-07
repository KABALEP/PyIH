/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.Ic2Icons;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemArmorElectric
/*     */   extends ItemArmor
/*     */   implements ISpecialArmor, IElectricItem, IElectricTool
/*     */ {
/*     */   public int maxCharge;
/*     */   public int transferLimit;
/*     */   public int tier;
/*     */   private int iconIndex;
/*     */   
/*     */   public ItemArmorElectric(int spriteIndex, int armorRendering, int armorType, int maxCharge, int transferLimit, int tier) {
/*  39 */     super(ItemArmor.ArmorMaterial.DIAMOND, armorRendering, armorType);
/*  40 */     this.iconIndex = spriteIndex;
/*  41 */     this.maxCharge = maxCharge;
/*  42 */     this.tier = tier;
/*  43 */     this.transferLimit = transferLimit;
/*  44 */     func_77656_e(27);
/*  45 */     func_77625_d(1);
/*  46 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  58 */     return Ic2Icons.getTexture("i2")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getTextureName();
/*     */ 
/*     */   
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
/*  71 */     String name = getTextureName();
/*  72 */     if (slot == 2)
/*     */     {
/*  74 */       return "ic2:textures/models/armor/" + name + "_2.png";
/*     */     }
/*  76 */     return "ic2:textures/models/armor/" + name + "_1.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/*  81 */     ItemStack charged = new ItemStack((Item)this, 1);
/*  82 */     ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/*  83 */     itemList.add(charged);
/*  84 */     itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/*  89 */     return "/ic2/sprites/item_0.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockingEverything() {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  99 */     if (source.func_76363_c() && !isBlockingEverything())
/*     */     {
/* 101 */       return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
/*     */     }
/* 103 */     if (source == IC2DamageSource.electricity)
/*     */     {
/* 105 */       if (IC2.enableSpecialElectricArmor) {
/*     */         
/* 107 */         if (!hasElectricBoots(player))
/*     */         {
/* 109 */           return new ISpecialArmor.ArmorProperties(0, 1.0D, this.maxCharge - (int)ElectricItem.manager.getCharge(armor));
/*     */         }
/* 111 */         return new ISpecialArmor.ArmorProperties(0, 1.0D, this.maxCharge);
/*     */       } 
/*     */     }
/* 114 */     double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
/* 115 */     double energyPerDamage = getEnergyPerDamage();
/* 116 */     energyPerDamage *= IC2.electricSuitEnergyCostModifier;
/* 117 */     absorptionRatio *= IC2.electricSuitAbsorbtionScale;
/* 118 */     int damageLimit = (int)((energyPerDamage > 0.0D) ? (25.0D * ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true) / energyPerDamage) : 0.0D);
/* 119 */     return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasElectricBoots(EntityLivingBase par1) {
/* 124 */     if (par1 instanceof EntityPlayer) {
/*     */       
/* 126 */       ItemStack itemStack = ((EntityPlayer)par1).field_71071_by.func_70440_f(0);
/* 127 */       if (itemStack != null && itemStack.func_77973_b() instanceof IElectricItem)
/*     */       {
/* 129 */         return !par1.field_70170_p.func_147437_c(MathHelper.func_76128_c(par1.field_70165_t), MathHelper.func_76128_c(par1.field_70163_u - 0.20000000298023224D - par1.field_70129_M), MathHelper.func_76128_c(par1.field_70161_v));
/*     */       }
/*     */     } 
/* 132 */     ItemStack item = par1.func_71124_b(1);
/* 133 */     if (item != null && item.func_77973_b() instanceof IElectricItem)
/*     */     {
/* 135 */       return !par1.field_70170_p.func_147437_c(MathHelper.func_76128_c(par1.field_70165_t), MathHelper.func_76128_c(par1.field_70163_u - 0.20000000298023224D - par1.field_70129_M), MathHelper.func_76128_c(par1.field_70161_v));
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 142 */     return (int)Math.round(20.0D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio() * IC2.electricSuitAbsorbtionScale);
/*     */   }
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/* 147 */     if (source == IC2DamageSource.electricity && IC2.enableSpecialElectricArmor) {
/*     */       
/* 149 */       ElectricItem.manager.charge(stack, damage, 2147483647, true, false);
/*     */       return;
/*     */     } 
/* 152 */     ElectricItem.manager.use(stack, (damage * (int)(getEnergyPerDamage() * IC2.electricSuitAbsorbtionScale)), entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/* 169 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/* 175 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/* 181 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack itemStack) {
/* 187 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack itemStack) {
/* 193 */     return this.transferLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract double getDamageAbsorptionRatio();
/*     */   
/*     */   public abstract int getEnergyPerDamage();
/*     */   
/*     */   private double getBaseAbsorptionRatio() {
/* 207 */     switch (this.field_77881_a) {
/*     */ 
/*     */       
/*     */       case 0:
/* 211 */         return 0.3D;
/*     */ 
/*     */       
/*     */       case 1:
/* 215 */         return 0.4D;
/*     */ 
/*     */       
/*     */       case 2:
/* 219 */         return 0.3D;
/*     */ 
/*     */       
/*     */       case 3:
/* 223 */         return 0.15D;
/*     */     } 
/*     */ 
/*     */     
/* 227 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 235 */     switch (this.field_77881_a) {
/*     */       case 0:
/* 237 */         return EnumEnchantmentType.armor_head;
/* 238 */       case 1: return EnumEnchantmentType.armor_torso;
/* 239 */       case 2: return EnumEnchantmentType.armor_legs;
/* 240 */       case 3: return EnumEnchantmentType.armor_feet;
/*     */     } 
/* 242 */     return EnumEnchantmentType.armor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 254 */     if (ench == Enchantment.field_92091_k)
/*     */     {
/* 256 */       return true;
/*     */     }
/* 258 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */