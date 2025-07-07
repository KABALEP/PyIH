/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IMetalArmor;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2Potion;
/*     */ import ic2.core.IItemTickListener;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemTinCan;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingFallEvent;
/*     */ 
/*     */ public class ItemArmorQuantumSuit
/*     */   extends ItemArmorElectric
/*     */   implements IMetalArmor, IItemTickListener
/*     */ {
/*  33 */   public static Map<EntityPlayer, Integer> speedTickerMap = new HashMap<>();
/*  34 */   public static Map<EntityPlayer, Float> jumpChargeMap = new HashMap<>();
/*  35 */   public static Map<EntityPlayer, Boolean> enableQuantumSpeedOnSprintMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ItemArmorQuantumSuit(int index, int armorrendering, int armorType) {
/*  39 */     super(index, armorrendering, armorType, 1000000, 1000, 3);
/*  40 */     if (armorType == 3)
/*     */     {
/*  42 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockingEverything() {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  55 */     if (source == DamageSource.field_76379_h && this.field_77881_a == 3) {
/*     */       
/*  57 */       int energyPerDamage = getEnergyPerDamage();
/*  58 */       int damageLimit = (int)((energyPerDamage > 0) ? (25.0D * ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true) / energyPerDamage) : 0.0D);
/*  59 */       return new ISpecialArmor.ArmorProperties(10, (1.0F * IC2.electricSuitAbsorbtionScale), damageLimit);
/*     */     } 
/*  61 */     return super.getProperties(player, armor, source, damage, slot);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/*  67 */     if (IC2.platform.isSimulating() && event.entity instanceof EntityPlayer) {
/*     */       
/*  69 */       EntityPlayer player = (EntityPlayer)event.entity;
/*  70 */       ItemStack armor = player.field_71071_by.field_70460_b[0];
/*  71 */       if (armor != null && armor.func_77973_b() == this) {
/*     */         
/*  73 */         int fallDamage = (int)event.distance - 3;
/*  74 */         int energyCost = getEnergyPerDamage() * fallDamage;
/*  75 */         if (energyCost <= ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true)) {
/*     */           
/*  77 */           ElectricItem.manager.discharge(armor, energyCost, 2147483647, true, false, false);
/*  78 */           event.setCanceled(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDamageAbsorptionRatio() {
/*  87 */     if (this.field_77881_a == 1)
/*     */     {
/*  89 */       return 1.1D;
/*     */     }
/*  91 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyPerDamage() {
/*  97 */     return 900;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 109 */     return EnumRarity.rare;
/*     */   } public boolean onTick(EntityPlayer player, ItemStack itemStack) {
/*     */     int air;
/*     */     boolean enableQuantumSpeedOnSprint;
/*     */     float jumpCharge;
/*     */     PotionEffect poison, radiation, wither;
/* 115 */     boolean ret = false;
/* 116 */     switch (this.field_77881_a) {
/*     */ 
/*     */       
/*     */       case 0:
/* 120 */         air = player.func_70086_ai();
/* 121 */         if (ElectricItem.manager.canUse(itemStack, 1000.0D) && air < 100) {
/*     */           
/* 123 */           player.func_70050_g(air + 200);
/* 124 */           ElectricItem.manager.use(itemStack, 1000.0D, (EntityLivingBase)player);
/* 125 */           ret = true;
/*     */         }
/* 127 */         else if (air <= 0) {
/*     */           
/* 129 */           IC2.achievements.issueAchievement(player, "starveWithQHelmet");
/*     */         } 
/* 131 */         if (ElectricItem.manager.canUse(itemStack, 1000.0D) && player.func_71024_bL().func_75121_c()) {
/*     */           
/* 133 */           int slot = -1;
/* 134 */           for (int i = 0; i < player.field_71071_by.field_70462_a.length; i++) {
/*     */             
/* 136 */             if (player.field_71071_by.field_70462_a[i] != null && player.field_71071_by.field_70462_a[i].func_77973_b() == Ic2Items.filledTinCan.func_77973_b()) {
/*     */               
/* 138 */               slot = i;
/*     */               break;
/*     */             } 
/*     */           } 
/* 142 */           if (slot > -1)
/*     */           {
/* 144 */             ItemTinCan can = (ItemTinCan)player.field_71071_by.field_70462_a[slot].func_77973_b();
/* 145 */             player.func_71024_bL().func_75122_a(can.func_150905_g(player.field_71071_by.field_70462_a[slot]), can.func_150906_h(player.field_71071_by.field_70462_a[slot]));
/* 146 */             can.onCanEaten(player.field_71071_by.field_70462_a[slot], player.field_70170_p, player);
/* 147 */             ItemStack itemStack2 = player.field_71071_by.field_70462_a[slot];
/* 148 */             if (--itemStack2.field_77994_a <= 0)
/*     */             {
/* 150 */               player.field_71071_by.field_70462_a[slot] = null;
/*     */             }
/* 152 */             ElectricItem.manager.use(itemStack, 1000.0D, (EntityLivingBase)player);
/* 153 */             ret = true;
/*     */           }
/*     */         
/* 156 */         } else if (player.func_71024_bL().func_75116_a() <= 0) {
/*     */           
/* 158 */           IC2.achievements.issueAchievement(player, "starveWithQHelmet");
/*     */         } 
/* 160 */         poison = player.func_70660_b(Potion.field_76436_u);
/* 161 */         if (poison != null && ElectricItem.manager.canUse(itemStack, (10000 * poison.func_76458_c()))) {
/*     */           
/* 163 */           ElectricItem.manager.use(itemStack, (10000 * poison.func_76458_c()), (EntityLivingBase)player);
/* 164 */           IC2.platform.removePotion((EntityLivingBase)player, Potion.field_76436_u.field_76415_H);
/*     */         } 
/* 166 */         radiation = player.func_70660_b((Potion)IC2Potion.radiation);
/* 167 */         if (radiation != null && ElectricItem.manager.canUse(itemStack, (20000 * radiation.func_76458_c()))) {
/*     */           
/* 169 */           ElectricItem.manager.use(itemStack, (20000 * radiation.func_76458_c()), (EntityLivingBase)player);
/* 170 */           IC2.platform.removePotion((EntityLivingBase)player, IC2Potion.radiation.field_76415_H);
/*     */         } 
/* 172 */         wither = player.func_70660_b(Potion.field_82731_v);
/* 173 */         if (wither != null && ElectricItem.manager.canUse(itemStack, (25000 * wither.func_76458_c()))) {
/*     */           
/* 175 */           ElectricItem.manager.use(itemStack, (25000 * wither.func_76458_c()), (EntityLivingBase)player);
/* 176 */           IC2.platform.removePotion((EntityLivingBase)player, Potion.field_82731_v.field_76415_H);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 182 */         player.func_70015_d(0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 187 */         enableQuantumSpeedOnSprint = true;
/* 188 */         if (IC2.platform.isRendering()) {
/*     */           
/* 190 */           enableQuantumSpeedOnSprint = IC2.enableQuantumSpeedOnSprint;
/*     */         }
/* 192 */         else if (enableQuantumSpeedOnSprintMap.containsKey(player)) {
/*     */           
/* 194 */           enableQuantumSpeedOnSprint = ((Boolean)enableQuantumSpeedOnSprintMap.get(player)).booleanValue();
/*     */         } 
/* 196 */         if ((ElectricItem.manager.canUse(itemStack, 1000.0D) && player.field_70122_E && Math.abs(player.field_70159_w) + Math.abs(player.field_70179_y) > 0.1000000014901161D && ((enableQuantumSpeedOnSprint && player.func_70051_ag()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) || (player.func_70090_H() && ((enableQuantumSpeedOnSprint && player.func_70051_ag()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player))))) {
/*     */           
/* 198 */           int speedTicker = speedTickerMap.containsKey(player) ? ((Integer)speedTickerMap.get(player)).intValue() : 0;
/* 199 */           if (++speedTicker >= 10) {
/*     */             
/* 201 */             speedTicker = 0;
/* 202 */             ElectricItem.manager.use(itemStack, 1000.0D, (EntityLivingBase)player);
/* 203 */             ret = true;
/*     */           } 
/* 205 */           speedTickerMap.put(player, Integer.valueOf(speedTicker));
/* 206 */           float speed = 0.22F;
/* 207 */           if (player.func_70090_H())
/*     */           {
/* 209 */             speed = 0.1F;
/*     */           }
/* 211 */           if (speed > 0.0F)
/*     */           {
/* 213 */             player.func_70060_a(0.0F, 1.0F, speed);
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 220 */         jumpCharge = jumpChargeMap.containsKey(player) ? ((Float)jumpChargeMap.get(player)).floatValue() : 1.0F;
/* 221 */         if (ElectricItem.manager.canUse(itemStack, 1000.0D) && player.field_70122_E && jumpCharge < 1.0F) {
/*     */           
/* 223 */           jumpCharge = 1.0F;
/* 224 */           ElectricItem.manager.use(itemStack, 1000.0D, (EntityLivingBase)player);
/* 225 */           ret = true;
/*     */         } 
/* 227 */         if (player.field_70181_x >= 0.0D && jumpCharge > 0.0F && !player.func_70090_H())
/*     */         {
/* 229 */           if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
/*     */             
/* 231 */             if (jumpCharge == 1.0F) {
/*     */               
/* 233 */               player.field_70159_w *= 3.5D;
/* 234 */               player.field_70179_y *= 3.5D;
/*     */             } 
/* 236 */             player.field_70181_x += (jumpCharge * 0.3F);
/* 237 */             jumpCharge *= 0.75F;
/*     */           }
/* 239 */           else if (jumpCharge < 1.0F) {
/*     */             
/* 241 */             jumpCharge = 0.0F;
/*     */           } 
/*     */         }
/* 244 */         jumpChargeMap.put(player, Float.valueOf(jumpCharge));
/*     */         break;
/*     */     } 
/*     */     
/* 248 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77623_v() {
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82816_b_(ItemStack aStack) {
/* 259 */     return (func_82814_b(aStack) != 10511680);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77618_c(int par1, int par2) {
/* 265 */     return func_77617_a(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_82815_c(ItemStack par1ItemStack) {
/* 270 */     NBTTagCompound tNBT = par1ItemStack.func_77978_p();
/* 271 */     if (tNBT != null) {
/*     */       
/* 273 */       tNBT = tNBT.func_74775_l("display");
/* 274 */       if (tNBT.func_74764_b("color")) {
/* 275 */         tNBT.func_82580_o("color");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int func_82814_b(ItemStack aStack) {
/* 281 */     NBTTagCompound tNBT = aStack.func_77978_p();
/* 282 */     if (tNBT == null)
/* 283 */       return 10511680; 
/* 284 */     tNBT = tNBT.func_74775_l("display");
/* 285 */     return tNBT.func_74764_b("color") ? tNBT.func_74762_e("color") : ((tNBT == null) ? 10511680 : 10511680);
/*     */   }
/*     */ 
/*     */   
/*     */   public void colorQArmor(ItemStack aStack, int par2) {
/* 290 */     NBTTagCompound tNBT = aStack.func_77978_p();
/* 291 */     if (tNBT == null) {
/*     */       
/* 293 */       tNBT = new NBTTagCompound();
/* 294 */       aStack.func_77982_d(tNBT);
/*     */     } 
/* 296 */     if (!tNBT.func_74764_b("display"))
/* 297 */       tNBT.func_74782_a("display", (NBTBase)new NBTTagCompound()); 
/* 298 */     tNBT = tNBT.func_74775_l("display");
/* 299 */     tNBT.func_74768_a("color", par2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_82813_b(ItemStack p_82813_1_, int p_82813_2_) {
/* 305 */     colorQArmor(p_82813_1_, p_82813_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 311 */     return "quantum";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorQuantumSuit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */