/*     */ package ic2.tcIntigration.core;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import tconstruct.library.modifier.IModifyable;
/*     */ import tconstruct.modifiers.tools.ModBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EUMofifier
/*     */   extends ModBoolean
/*     */ {
/*  20 */   public ArrayList<ItemStack> batteries = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public EUMofifier() {
/*  24 */     super(new ItemStack[0], 5000, "EUEnergy", EnumChatFormatting.BLUE.toString(), "");
/*  25 */     this.batteries.add(Ic2Items.chargedReBattery);
/*  26 */     this.batteries.add(Ic2Items.reBattery);
/*  27 */     this.batteries.add(Ic2Items.energyCrystal);
/*  28 */     this.batteries.add(Ic2Items.lapotronCrystal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(ItemStack[] input, ItemStack tool) {
/*  34 */     String[] traits = ((IModifyable)tool.func_77973_b()).getTraits();
/*  35 */     for (String trait : traits) {
/*     */       
/*  37 */       if ("ammo".equals(trait))
/*     */       {
/*  39 */         return false;
/*     */       }
/*     */     } 
/*  42 */     ItemStack foundBattery = null;
/*     */     
/*  44 */     for (ItemStack stack : input) {
/*     */       
/*  46 */       for (ItemStack battery : this.batteries) {
/*     */         
/*  48 */         if (stack == null) {
/*     */           break;
/*     */         }
/*     */         
/*  52 */         if (stack.func_77973_b() != battery.func_77973_b()) {
/*     */           continue;
/*     */         }
/*     */         
/*  56 */         if (!(stack.func_77973_b() instanceof IElectricItem)) {
/*     */           continue;
/*     */         }
/*     */         
/*  60 */         if (foundBattery != null)
/*     */         {
/*  62 */           return false;
/*     */         }
/*  64 */         foundBattery = stack;
/*     */       } 
/*     */     } 
/*  67 */     if (foundBattery == null)
/*     */     {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     NBTTagCompound tags = tool.func_77978_p().func_74775_l("InfiTool");
/*  73 */     if (tags.func_74767_n(this.key)) {
/*     */       
/*  75 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(tool).func_74775_l(this.key);
/*  76 */       int tier = nbt.func_74762_e("Tier");
/*  77 */       int maxEnergy = nbt.func_74762_e("maxEnergy");
/*  78 */       IElectricItem item = (IElectricItem)foundBattery.func_77973_b();
/*  79 */       return ((item.getMaxCharge(foundBattery) > maxEnergy && item.getTier(foundBattery) >= tier) || (item.getMaxCharge(foundBattery) >= maxEnergy && item.getTier(foundBattery) > tier));
/*     */     } 
/*  81 */     if (tags.func_74762_e("Modifiers") < 1)
/*     */     {
/*  83 */       return false;
/*     */     }
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modify(ItemStack[] input, ItemStack tool) {
/*  91 */     NBTTagCompound tags = tool.func_77978_p().func_74775_l("InfiTool");
/*  92 */     ItemStack foundBattery = null;
/*  93 */     boolean hasModifier = true;
/*  94 */     if (!tags.func_74764_b(this.key)) {
/*     */       
/*  96 */       int modifiers = tags.func_74775_l("InfiTool").func_74762_e("Modifiers");
/*  97 */       modifiers--;
/*  98 */       tags.func_74775_l("InfiTool").func_74768_a("Modifiers", modifiers);
/*  99 */       addModifierTip(tool, EnumChatFormatting.BLUE + "EU");
/* 100 */       hasModifier = false;
/*     */     } 
/* 102 */     tags.func_74757_a(this.key, true);
/*     */     
/* 104 */     for (ItemStack stack : input) {
/*     */       
/* 106 */       for (ItemStack itemStack : this.batteries) {
/*     */         
/* 108 */         if (stack == null) {
/*     */           break;
/*     */         }
/*     */         
/* 112 */         if (stack.func_77973_b() != itemStack.func_77973_b()) {
/*     */           continue;
/*     */         }
/*     */         
/* 116 */         if (!(stack.func_77973_b() instanceof IElectricItem)) {
/*     */           continue;
/*     */         }
/*     */         
/* 120 */         foundBattery = stack;
/*     */       } 
/*     */     } 
/* 123 */     NBTTagCompound nbt = null;
/* 124 */     if (tool.func_77978_p().func_74764_b(this.key)) {
/*     */       
/* 126 */       nbt = tool.func_77978_p().func_74775_l("EUEnergy");
/*     */     }
/*     */     else {
/*     */       
/* 130 */       tool.func_77978_p().func_74782_a("EUEnergy", (NBTBase)new NBTTagCompound());
/* 131 */       nbt = tool.func_77978_p().func_74775_l("EUEnergy");
/*     */     } 
/* 133 */     IElectricItem battery = (IElectricItem)foundBattery.func_77973_b();
/* 134 */     int charge = (int)ElectricItem.manager.getCharge(foundBattery);
/* 135 */     if (hasModifier)
/*     */     {
/* 137 */       charge = (int)(charge + ElectricItem.manager.getCharge(tool));
/*     */     }
/*     */     
/* 140 */     int maxCharge = (int)Math.max(battery.getMaxCharge(foundBattery), nbt.func_74762_e("maxCharge"));
/* 141 */     charge = Math.min(charge, maxCharge);
/* 142 */     int maxTier = Math.max(battery.getTier(foundBattery), nbt.func_74762_e("Tier"));
/* 143 */     int limit = (int)Math.max(battery.getTransferLimit(foundBattery), nbt.func_74762_e("limit"));
/* 144 */     nbt.func_74768_a("maxCharge", maxCharge);
/* 145 */     nbt.func_74768_a("Tier", maxTier);
/* 146 */     nbt.func_74768_a("limit", limit);
/* 147 */     ElectricItem.manager.charge(tool, charge, 2147483647, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validType(IModifyable input) {
/* 154 */     return super.validType(input);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\tcIntigration\core\EUMofifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */