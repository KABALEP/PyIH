/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.IElectricHelper;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElectricItemManager
/*     */   implements IElectricItemManager
/*     */ {
/*     */   public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/*  22 */     IElectricItem item = (IElectricItem)itemStack.func_77973_b();
/*     */     
/*  24 */     assert item.getMaxCharge(itemStack) > 0.0D;
/*     */     
/*  26 */     if (amount < 0.0D || itemStack.field_77994_a > 1 || item.getTier(itemStack) > tier) {
/*  27 */       return 0.0D;
/*     */     }
/*  29 */     if (!ignoreTransferLimit && amount > item.getTransferLimit(itemStack)) {
/*  30 */       amount = item.getTransferLimit(itemStack);
/*     */     }
/*  32 */     NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(itemStack);
/*  33 */     int tNewCharge = tNBT.func_74762_e("charge");
/*     */     
/*  35 */     if (amount > item.getMaxCharge(itemStack) - tNewCharge) {
/*  36 */       amount = item.getMaxCharge(itemStack) - tNewCharge;
/*     */     }
/*  38 */     if (!simulate) {
/*     */       
/*  40 */       tNewCharge = (int)(tNewCharge + amount);
/*     */       
/*  42 */       if (tNewCharge > 0) {
/*     */         
/*  44 */         tNBT.func_74768_a("charge", tNewCharge);
/*     */       }
/*     */       else {
/*     */         
/*  48 */         tNBT.func_82580_o("charge");
/*  49 */         if (tNBT.func_150296_c().isEmpty())
/*     */         {
/*  51 */           itemStack.func_77982_d(null);
/*     */         }
/*     */       } 
/*     */       
/*  55 */       itemStack.func_150996_a((tNewCharge > 0) ? item.getChargedItem(itemStack) : item.getEmptyItem(itemStack));
/*     */       
/*  57 */       if (itemStack.func_77973_b() instanceof IElectricItem) {
/*     */         
/*  59 */         item = (IElectricItem)itemStack.func_77973_b();
/*     */         
/*  61 */         if (itemStack.func_77958_k() > 2) {
/*  62 */           itemStack.func_77964_b((int)(1.0D + (item.getMaxCharge(itemStack) - tNewCharge) * (itemStack.func_77958_k() - 2L) / item.getMaxCharge(itemStack)));
/*     */         } else {
/*  64 */           itemStack.func_77964_b(0);
/*     */         } 
/*     */       } else {
/*     */         
/*  68 */         itemStack.func_77964_b(0);
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externalUse, boolean simulate) {
/*  78 */     IElectricItem item = (IElectricItem)itemStack.func_77973_b();
/*     */     
/*  80 */     assert item.getMaxCharge(itemStack) > 0.0D;
/*     */     
/*  82 */     if (amount < 0.0D || itemStack.field_77994_a > 1 || item.getTier(itemStack) > tier)
/*     */     {
/*  84 */       return 0.0D;
/*     */     }
/*  86 */     if (externalUse && !item.canProvideEnergy(itemStack))
/*     */     {
/*  88 */       return 0.0D;
/*     */     }
/*  90 */     if (!ignoreTransferLimit && amount > item.getTransferLimit(itemStack))
/*     */     {
/*  92 */       amount = item.getTransferLimit(itemStack);
/*     */     }
/*     */     
/*  95 */     NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(itemStack);
/*  96 */     int tNewCharge = tNBT.func_74762_e("charge");
/*     */     
/*  98 */     if (amount > tNewCharge)
/*     */     {
/* 100 */       amount = tNewCharge;
/*     */     }
/*     */     
/* 103 */     if (!simulate) {
/*     */       
/* 105 */       tNewCharge = (int)(tNewCharge - amount);
/*     */       
/* 107 */       if (tNewCharge > 0) {
/*     */         
/* 109 */         tNBT.func_74768_a("charge", tNewCharge);
/*     */       }
/*     */       else {
/*     */         
/* 113 */         tNBT.func_82580_o("charge");
/* 114 */         if (tNBT.func_150296_c().isEmpty())
/*     */         {
/* 116 */           itemStack.func_77982_d(null);
/*     */         }
/*     */       } 
/* 119 */       itemStack.func_150996_a((tNewCharge > 0.0D) ? item.getChargedItem(itemStack) : item.getEmptyItem(itemStack));
/*     */       
/* 121 */       if (itemStack.func_77973_b() instanceof IElectricItem) {
/*     */         
/* 123 */         item = (IElectricItem)itemStack.func_77973_b();
/*     */         
/* 125 */         if (itemStack.func_77958_k() > 2)
/*     */         {
/* 127 */           itemStack.func_77964_b((int)(1.0D + (item.getMaxCharge(itemStack) - tNewCharge) * (itemStack.func_77958_k() - 2L) / item.getMaxCharge(itemStack)));
/*     */         }
/*     */         else
/*     */         {
/* 131 */           itemStack.func_77964_b(0);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 136 */         itemStack.func_77964_b(0);
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack itemStack) {
/* 146 */     return ElectricItem.manager.discharge(itemStack, 2.147483647E9D, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUse(ItemStack itemStack, double amount) {
/* 152 */     return (ElectricItem.manager.getCharge(itemStack) >= applyEnchantmentEffect(itemStack, amount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity) {
/* 158 */     if (!IC2.platform.isSimulating())
/*     */     {
/* 160 */       return false;
/*     */     }
/*     */     
/* 163 */     amount = applyEnchantmentEffect(itemStack, amount);
/*     */     
/* 165 */     ElectricItem.manager.chargeFromArmor(itemStack, entity);
/*     */     
/* 167 */     double transfer = ElectricItem.manager.discharge(itemStack, amount, 2147483647, true, false, true);
/*     */     
/* 169 */     if ((int)transfer == (int)amount) {
/*     */       
/* 171 */       ElectricItem.manager.discharge(itemStack, amount, 2147483647, true, false, false);
/* 172 */       ElectricItem.manager.chargeFromArmor(itemStack, entity);
/* 173 */       return true;
/*     */     } 
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
/* 181 */     if (!IC2.platform.isSimulating() || entity == null) {
/*     */       return;
/*     */     }
/* 184 */     boolean inventoryChanged = false;
/*     */     
/* 186 */     if (entity instanceof EntityPlayer && IC2.modul.containsKey("Baubles Modul"))
/*     */     {
/* 188 */       inventoryChanged = ((IElectricHelper)IC2.modul.get("Baubles Modul")).chargeFromArmor(itemStack, (EntityPlayer)entity);
/*     */     }
/*     */     
/* 191 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 193 */       ItemStack armorItemStack = entity.func_71124_b(i + 1);
/*     */       
/* 195 */       if (armorItemStack != null && armorItemStack.func_77973_b() instanceof IElectricItem) {
/*     */         
/* 197 */         IElectricItem armorItem = (IElectricItem)armorItemStack.func_77973_b();
/*     */         
/* 199 */         if (armorItem.canProvideEnergy(armorItemStack) && armorItem.getTier(armorItemStack) >= ((IElectricItem)itemStack.func_77973_b()).getTier(itemStack)) {
/*     */           
/* 201 */           double transfer = ElectricItem.manager.charge(itemStack, 2.147483647E9D, 2147483647, true, true);
/*     */           
/* 203 */           transfer = ElectricItem.manager.discharge(armorItemStack, transfer, 2147483647, true, true, false);
/*     */           
/* 205 */           if (transfer > 0.0D) {
/*     */             
/* 207 */             ElectricItem.manager.charge(itemStack, transfer, 2147483647, true, false);
/*     */             
/* 209 */             inventoryChanged = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if (inventoryChanged && entity instanceof EntityPlayer)
/*     */     {
/* 217 */       ((EntityPlayer)entity).field_71070_bA.func_75142_b();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTip(ItemStack itemStack) {
/* 224 */     IElectricItem item = (IElectricItem)itemStack.func_77973_b();
/* 225 */     return ElectricItem.manager.getCharge(itemStack) + "/" + item.getMaxCharge(itemStack) + " EU";
/*     */   }
/*     */ 
/*     */   
/*     */   public double applyEnchantmentEffect(ItemStack item, double amount) {
/* 230 */     int level = EnchantmentHelper.func_77506_a(Enchantment.field_77349_p.field_77352_x, item);
/* 231 */     if (level > 0) {
/*     */       
/* 233 */       double effect = 1.0D + 0.15D * level;
/* 234 */       amount *= effect;
/*     */     } 
/* 236 */     level = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, item);
/* 237 */     if (level > 0) {
/*     */       
/* 239 */       double effect = 1.0D - 0.1D * level;
/* 240 */       amount *= effect;
/*     */     } 
/* 242 */     return amount;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */