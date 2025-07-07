/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.IItemTickListener;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingFallEvent;
/*     */ 
/*     */ public class ItemArmorHazmat
/*     */   extends ItemArmorUtility
/*     */   implements IItemTickListener
/*     */ {
/*     */   public ItemArmorHazmat(int spriteIndex, int renderIndex, int type) {
/*  25 */     super(spriteIndex, renderIndex, type);
/*  26 */     func_77656_e(64);
/*  27 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*  28 */     if (this.field_77881_a == 3)
/*     */     {
/*  30 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  37 */     if (this.field_77881_a == 0 && hazmatAbsorbs(source) && hasCompleteHazmat(player)) {
/*     */       
/*  39 */       if (source == DamageSource.field_76372_a || source == DamageSource.field_76371_c)
/*     */       {
/*  41 */         player.func_70690_d(new PotionEffect(Potion.field_76426_n.field_76415_H, 60, 1));
/*     */       }
/*  43 */       return new ISpecialArmor.ArmorProperties(10, 1.0D, 2147483647);
/*     */     } 
/*  45 */     if (this.field_77881_a == 3 && source == DamageSource.field_76379_h)
/*     */     {
/*  47 */       return new ISpecialArmor.ArmorProperties(10, (damage < 8.0D) ? 1.0D : 0.875D, (armor.func_77958_k() - armor.func_77960_j() + 1) * 2 * 25);
/*     */     }
/*  49 */     if (armor.func_77960_j() >= armor.func_77958_k())
/*     */     {
/*  51 */       return new ISpecialArmor.ArmorProperties(0, 1.0D, 25);
/*     */     }
/*  53 */     return new ISpecialArmor.ArmorProperties(0, 1.0D, armor.func_77958_k() - armor.func_77960_j() + 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/*  59 */     if (hazmatAbsorbs(source) && hasCompleteHazmat(entity)) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     if (source.func_76363_c()) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     int damageTotal = damage * 2;
/*  68 */     if (this.field_77881_a == 3 && source == DamageSource.field_76379_h)
/*     */     {
/*  70 */       damageTotal = (damage + 1) / 2;
/*     */     }
/*  72 */     stack.func_77972_a(damageTotal, entity);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/*  78 */     if (IC2.platform.isSimulating() && event.entity instanceof EntityPlayer) {
/*     */       
/*  80 */       EntityPlayer player = (EntityPlayer)event.entity;
/*  81 */       ItemStack armor = player.field_71071_by.field_70460_b[0];
/*  82 */       if (armor != null && armor.func_77973_b() == this) {
/*     */         
/*  84 */         int fallDamage = (int)event.distance - 3;
/*  85 */         if (fallDamage >= 8) {
/*     */           return;
/*     */         }
/*     */         
/*  89 */         int armorDamage = (fallDamage + 1) / 2;
/*  90 */         if (armorDamage <= armor.func_77958_k() - armor.func_77960_j() && armorDamage >= 0) {
/*     */           
/*  92 */           armor.func_77972_a(armorDamage, (EntityLivingBase)player);
/*  93 */           event.setCanceled(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 108 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(EntityPlayer player, ItemStack itemStack) {
/* 114 */     if (this.field_77881_a == 0) {
/*     */       
/* 116 */       if (hasCompleteHazmat((EntityLivingBase)player)) {
/*     */         
/* 118 */         if (player.func_70055_a(Material.field_151587_i) || player.func_70055_a(Material.field_151581_o))
/*     */         {
/* 120 */           player.func_70690_d(new PotionEffect(Potion.field_76426_n.field_76415_H, 20, 0, true));
/*     */         }
/* 122 */         player.func_70066_B();
/*     */       } 
/* 124 */       if (player.func_70086_ai() <= 100 && player.field_71071_by.func_146028_b(Ic2Items.airCell.func_77973_b())) {
/*     */         
/* 126 */         player.field_71071_by.func_146026_a(Ic2Items.airCell.func_77973_b());
/* 127 */         player.field_71071_by.func_70441_a(new ItemStack(Ic2Items.cell.func_77973_b()));
/* 128 */         player.func_70050_g(player.func_70086_ai() + 150);
/* 129 */         return true;
/*     */       } 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasCompleteHazmat(EntityLivingBase living) {
/* 137 */     if (!(living instanceof EntityPlayer))
/*     */     {
/* 139 */       return false;
/*     */     }
/* 141 */     EntityPlayer player = (EntityPlayer)living;
/* 142 */     ItemStack[] armor = player.field_71071_by.field_70460_b;
/* 143 */     return (armor[0] != null && armor[0].func_77973_b() instanceof ItemArmorHazmat && armor[1] != null && armor[1].func_77973_b() instanceof ItemArmorHazmat && armor[2] != null && armor[2].func_77973_b() instanceof ItemArmorHazmat && armor[3] != null && armor[3].func_77973_b() instanceof ItemArmorHazmat);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hazmatAbsorbs(DamageSource source) {
/* 148 */     return (source == DamageSource.field_76372_a || source == DamageSource.field_76368_d || source == DamageSource.field_76371_c || source == DamageSource.field_76370_b || source == IC2DamageSource.electricity || source == IC2DamageSource.radiation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 154 */     return "hazmat";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorHazmat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */