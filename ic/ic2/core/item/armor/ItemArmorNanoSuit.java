/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IMetalArmor;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraftforge.common.ISpecialArmor;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.entity.EntityEvent;
/*    */ import net.minecraftforge.event.entity.living.LivingFallEvent;
/*    */ 
/*    */ public class ItemArmorNanoSuit
/*    */   extends ItemArmorElectric
/*    */   implements IMetalArmor {
/*    */   public ItemArmorNanoSuit(int index, int armorrendering, int armorType) {
/* 23 */     super(index, armorrendering, armorType, 100000, 160, 2);
/* 24 */     if (armorType == 3)
/*    */     {
/* 26 */       MinecraftForge.EVENT_BUS.register(this);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 33 */     if (source == DamageSource.field_76379_h && this.field_77881_a == 3) {
/*    */       
/* 35 */       int energyPerDamage = getEnergyPerDamage();
/* 36 */       int damageLimit = (int)((energyPerDamage > 0) ? (25.0D * ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, true, true) / energyPerDamage) : 0.0D);
/* 37 */       float absorbtion = (damage < 8.0D) ? 1.0F : 0.875F;
/* 38 */       absorbtion *= IC2.electricSuitAbsorbtionScale;
/* 39 */       return new ISpecialArmor.ArmorProperties(10, absorbtion, damageLimit);
/*    */     } 
/* 41 */     return super.getProperties(player, armor, source, damage, slot);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/* 47 */     if (IC2.platform.isSimulating() && ((EntityEvent)event).entity instanceof EntityPlayer) {
/*    */       
/* 49 */       EntityPlayer player = (EntityPlayer)((EntityEvent)event).entity;
/* 50 */       ItemStack armor = player.field_71071_by.field_70460_b[0];
/* 51 */       if (armor != null && armor.func_77973_b() == this) {
/*    */         
/* 53 */         int fallDamage = (int)event.distance - 3;
/* 54 */         if (fallDamage >= 8) {
/*    */           return;
/*    */         }
/*    */         
/* 58 */         int energyCost = getEnergyPerDamage() * fallDamage;
/* 59 */         if (energyCost <= ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, true, true)) {
/*    */           
/* 61 */           ElectricItem.manager.discharge(armor, energyCost, 2147483647, true, true, false);
/* 62 */           event.setCanceled(true);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 71 */     return 0.9D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 77 */     return 800;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 83 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 89 */     return EnumRarity.uncommon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 95 */     return "nano";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorNanoSuit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */