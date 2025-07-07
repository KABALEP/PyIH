/*     */ package ic2.tcIntigration.core;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IBackupElectricItemManager;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.IElectricHelper;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TinkerToolEUManager
/*     */   implements IBackupElectricItemManager
/*     */ {
/*     */   public boolean canUse(ItemStack arg0, double arg1) {
/*  27 */     return (getCharge(arg0) >= arg1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/*  33 */     NBTTagCompound itemNBT = StackUtil.getOrCreateNbtData(itemStack);
/*  34 */     NBTTagCompound nbt = new NBTTagCompound();
/*  35 */     if (itemNBT.func_74764_b("EUEnergy")) {
/*     */       
/*  37 */       nbt = itemNBT.func_74775_l("EUEnergy");
/*     */     }
/*     */     else {
/*     */       
/*  41 */       itemNBT.func_74782_a("EUEnergy", (NBTBase)nbt);
/*     */     } 
/*     */     
/*  44 */     assert nbt.func_74762_e("maxCharge") > 0;
/*  45 */     if (amount < 0.0D || itemStack.field_77994_a > 1 || nbt.func_74762_e("Tier") > tier)
/*     */     {
/*  47 */       return 0.0D;
/*     */     }
/*  49 */     if (!ignoreTransferLimit && amount > nbt.func_74762_e("limit"))
/*     */     {
/*  51 */       amount = nbt.func_74762_e("limit");
/*     */     }
/*  53 */     int tNewCharge = nbt.func_74762_e("charge");
/*     */     
/*  55 */     if (amount > (nbt.func_74762_e("maxCharge") - tNewCharge))
/*     */     {
/*  57 */       amount = (nbt.func_74762_e("maxCharge") - tNewCharge);
/*     */     }
/*  59 */     if (!simulate) {
/*     */       
/*  61 */       tNewCharge = (int)(tNewCharge + amount);
/*     */       
/*  63 */       if (tNewCharge > 0)
/*     */       {
/*  65 */         nbt.func_74768_a("charge", tNewCharge);
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
/*  75 */     if (!IC2.platform.isSimulating() || entity == null) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     boolean inventoryChanged = false;
/*  80 */     if (entity instanceof EntityPlayer && IC2.modul.containsKey("Baubles Modul"))
/*     */     {
/*  82 */       inventoryChanged = ((IElectricHelper)IC2.modul.get("Baubles Modul")).chargeFromArmor(itemStack, (EntityPlayer)entity);
/*     */     }
/*  84 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  86 */       ItemStack armorItemStack = entity.func_71124_b(i + 1);
/*     */       
/*  88 */       if (armorItemStack != null && armorItemStack.func_77973_b() instanceof IElectricItem) {
/*     */         
/*  90 */         IElectricItem armorItem = (IElectricItem)armorItemStack.func_77973_b();
/*     */         
/*  92 */         if (armorItem.canProvideEnergy(armorItemStack) && armorItem.getTier(armorItemStack) >= ((IElectricItem)itemStack.func_77973_b()).getTier(itemStack)) {
/*     */           
/*  94 */           double transfer = charge(itemStack, 2.147483647E9D, 2147483647, true, true);
/*     */           
/*  96 */           transfer = ElectricItem.manager.discharge(armorItemStack, transfer, 2147483647, true, true, false);
/*     */           
/*  98 */           if (transfer > 0.0D) {
/*     */             
/* 100 */             charge(itemStack, transfer, 2147483647, true, false);
/* 101 */             inventoryChanged = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     if (inventoryChanged && entity instanceof EntityPlayer)
/*     */     {
/* 109 */       ((EntityPlayer)entity).field_71070_bA.func_75142_b();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externalUse, boolean simulate) {
/* 116 */     if (externalUse)
/*     */     {
/* 118 */       return 0.0D;
/*     */     }
/* 120 */     NBTTagCompound itemNBT = StackUtil.getOrCreateNbtData(itemStack);
/* 121 */     NBTTagCompound nbt = new NBTTagCompound();
/* 122 */     if (itemNBT.func_74764_b("EUEnergy")) {
/*     */       
/* 124 */       nbt = itemNBT.func_74775_l("EUEnergy");
/*     */     }
/*     */     else {
/*     */       
/* 128 */       itemNBT.func_74782_a("EUEnergy", (NBTBase)nbt);
/*     */     } 
/*     */     
/* 131 */     assert nbt.func_74762_e("maxCharge") > 0;
/* 132 */     if (amount < 0.0D || itemStack.field_77994_a > 1 || nbt.func_74762_e("Tier") > tier)
/*     */     {
/* 134 */       return 0.0D;
/*     */     }
/* 136 */     if (!ignoreTransferLimit && amount > nbt.func_74762_e("limit"))
/*     */     {
/* 138 */       amount = nbt.func_74762_e("limit");
/*     */     }
/*     */     
/* 141 */     int tNewCharge = nbt.func_74762_e("charge");
/*     */     
/* 143 */     if (amount > tNewCharge)
/*     */     {
/* 145 */       amount = tNewCharge;
/*     */     }
/* 147 */     if (!simulate) {
/*     */       
/* 149 */       tNewCharge = (int)(tNewCharge - amount);
/* 150 */       nbt.func_74768_a("charge", tNewCharge);
/*     */     } 
/* 152 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack arg0) {
/* 158 */     return discharge(arg0, 2.147483647E9D, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTip(ItemStack itemStack) {
/* 164 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemStack).func_74775_l("EUEnergy");
/* 165 */     int max = nbt.func_74762_e("maxCharge");
/* 166 */     return getCharge(itemStack) + "/" + max + " EU";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity) {
/* 172 */     if (!IC2.platform.isSimulating())
/*     */     {
/* 174 */       return false;
/*     */     }
/* 176 */     ElectricItem.manager.chargeFromArmor(itemStack, entity);
/* 177 */     double transfer = discharge(itemStack, amount, 2147483647, true, false, true);
/* 178 */     if (transfer == amount) {
/*     */       
/* 180 */       discharge(itemStack, amount, 2147483647, true, false, false);
/* 181 */       ElectricItem.manager.chargeFromArmor(itemStack, entity);
/* 182 */       return true;
/*     */     } 
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handles(ItemStack arg0) {
/* 190 */     if (arg0 != null && arg0.func_77973_b() instanceof tconstruct.library.modifier.IModifyable) {
/*     */       
/* 192 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(arg0);
/* 193 */       if (nbt.func_74764_b("EUEnergy"))
/*     */       {
/* 195 */         return true;
/*     */       }
/*     */     } 
/* 198 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\tcIntigration\core\TinkerToolEUManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */