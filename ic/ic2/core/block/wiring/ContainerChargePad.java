/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.personal.PersonalInventory;
/*     */ import ic2.core.item.ItemChargePadUpgrade;
/*     */ import ic2.core.slot.SlotCustom;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import ic2.core.slot.SlotUpgrade;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class ContainerChargePad
/*     */   extends ContainerIC2 {
/*     */   int slotSize;
/*     */   TileEntityChargePad tile;
/*     */   
/*     */   public ContainerChargePad(TileEntityChargePad inv, InventoryPlayer player) {
/*  28 */     this.tile = inv;
/*  29 */     PersonalInventory personalInventory = inv.inv;
/*  30 */     this.slotSize = personalInventory.func_70302_i_();
/*  31 */     int slot = 0;
/*  32 */     func_75146_a((inv.type == TileEntityChargePad.ChargePadType.Nuclear) ? (Slot)new SlotCustom((IInventory)personalInventory, new Object[] { IElectricItem.class, StackUtil.copyWithWildCard(Ic2Items.reactorUraniumSimple), StackUtil.copyWithWildCard(Ic2Items.reactorUraniumDual), StackUtil.copyWithWildCard(Ic2Items.reactorUraniumQuad) }, slot++, 8, 58) : (Slot)new SlotDischarge((IInventory)personalInventory, slot++, 8, 58));
/*  33 */     if (inv.type.getTier() > 1) {
/*     */       
/*  35 */       func_75146_a(new PadUpgradeSlot((IInventory)personalInventory, inv, slot++, 134, 58, true, inv.type.getTier()));
/*  36 */       func_75146_a(new PadUpgradeSlot((IInventory)personalInventory, inv, slot++, 134, 29, false, inv.type.getTier()));
/*     */     } 
/*  38 */     int left = this.slotSize - slot;
/*  39 */     func_75146_a((Slot)new SlotUpgrade((IInventory)personalInventory, slot++, 71, 39, (IMachine)inv.getMachine(), (TileEntity)inv));
/*  40 */     if (left > 1) {
/*     */       
/*  42 */       func_75146_a((Slot)new SlotUpgrade((IInventory)personalInventory, slot++, 61, 58, (IMachine)inv.getMachine(), (TileEntity)inv));
/*  43 */       if (left > 2)
/*     */       {
/*  45 */         func_75146_a((Slot)new SlotUpgrade((IInventory)personalInventory, slot++, 81, 58, (IMachine)inv.getMachine(), (TileEntity)inv));
/*     */       }
/*     */     } 
/*  48 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  50 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  52 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*  55 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  57 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int guiInventorySize() {
/*  64 */     return this.slotSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInput() {
/*  70 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer p0) {
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   public class PadUpgradeSlot
/*     */     extends Slot
/*     */   {
/*     */     int tier;
/*     */     boolean forFieldMods;
/*     */     TileEntityChargePad pad;
/*     */     
/*     */     public PadUpgradeSlot(IInventory par1, TileEntityChargePad pad, int par2, int par3, int par4, boolean par5, int par6) {
/*  87 */       super(par1, par2, par3, par4);
/*  88 */       this.forFieldMods = par5;
/*  89 */       this.tier = par6;
/*  90 */       this.pad = pad;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/*  96 */       if (stack == null)
/*     */       {
/*  98 */         return false;
/*     */       }
/* 100 */       Item item = stack.func_77973_b();
/* 101 */       if (item instanceof ItemChargePadUpgrade) {
/*     */         
/* 103 */         TileEntityChargePad.PadUpgrade upgrade = ((ItemChargePadUpgrade)item).getUpgrade(stack);
/* 104 */         if (upgrade != null) {
/*     */           
/* 106 */           if (upgrade.requiredTier > this.tier)
/*     */           {
/* 108 */             return false;
/*     */           }
/* 110 */           if (upgrade.isProject != this.forFieldMods)
/*     */           {
/* 112 */             return false;
/*     */           }
/* 114 */           return true;
/*     */         } 
/*     */       } 
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_75218_e() {
/* 123 */       if (IC2.platform.isSimulating())
/*     */       {
/* 125 */         this.pad.updateUpgrades();
/*     */       }
/* 127 */       super.func_75218_e();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int func_75219_a() {
/* 133 */       return 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_75135_a(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
/* 140 */     boolean flag1 = false;
/* 141 */     int k = p_75135_2_;
/*     */     
/* 143 */     if (p_75135_4_)
/*     */     {
/* 145 */       k = p_75135_3_ - 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (p_75135_1_.func_77985_e())
/*     */     {
/* 153 */       while (p_75135_1_.field_77994_a > 0 && ((!p_75135_4_ && k < p_75135_3_) || (p_75135_4_ && k >= p_75135_2_))) {
/*     */         
/* 155 */         Slot slot = this.field_75151_b.get(k);
/* 156 */         ItemStack itemstack1 = slot.func_75211_c();
/*     */         
/* 158 */         if (itemstack1 != null && itemstack1.func_77973_b() == p_75135_1_.func_77973_b() && (!p_75135_1_.func_77981_g() || p_75135_1_.func_77960_j() == itemstack1.func_77960_j()) && ItemStack.func_77970_a(p_75135_1_, itemstack1)) {
/*     */           
/* 160 */           int l = itemstack1.field_77994_a + p_75135_1_.field_77994_a;
/*     */           
/* 162 */           if (l <= Math.min(p_75135_1_.func_77976_d(), slot.func_75219_a())) {
/*     */             
/* 164 */             p_75135_1_.field_77994_a = 0;
/* 165 */             itemstack1.field_77994_a = l;
/* 166 */             slot.func_75218_e();
/* 167 */             flag1 = true;
/*     */           }
/* 169 */           else if (itemstack1.field_77994_a < Math.min(p_75135_1_.func_77976_d(), slot.func_75219_a())) {
/*     */             
/* 171 */             p_75135_1_.field_77994_a -= Math.min(p_75135_1_.func_77976_d(), slot.func_75219_a()) - itemstack1.field_77994_a;
/* 172 */             itemstack1.field_77994_a = Math.min(p_75135_1_.func_77976_d(), slot.func_75219_a());
/* 173 */             slot.func_75218_e();
/* 174 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */         
/* 178 */         if (p_75135_4_) {
/*     */           
/* 180 */           k--;
/*     */           
/*     */           continue;
/*     */         } 
/* 184 */         k++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 189 */     if (p_75135_1_.field_77994_a > 0) {
/*     */       
/* 191 */       if (p_75135_4_) {
/*     */         
/* 193 */         k = p_75135_3_ - 1;
/*     */       }
/*     */       else {
/*     */         
/* 197 */         k = p_75135_2_;
/*     */       } 
/*     */       
/* 200 */       while ((!p_75135_4_ && k < p_75135_3_) || (p_75135_4_ && k >= p_75135_2_)) {
/*     */         
/* 202 */         Slot slot = this.field_75151_b.get(k);
/* 203 */         ItemStack itemstack1 = slot.func_75211_c();
/*     */         
/* 205 */         if (itemstack1 == null) {
/*     */           
/* 207 */           if (slot.func_75219_a() >= p_75135_1_.field_77994_a) {
/*     */             
/* 209 */             slot.func_75215_d(p_75135_1_.func_77946_l());
/* 210 */             slot.func_75218_e();
/* 211 */             p_75135_1_.field_77994_a = 0;
/* 212 */             flag1 = true;
/*     */             break;
/*     */           } 
/* 215 */           int resultStackSize = p_75135_1_.field_77994_a - slot.func_75219_a();
/* 216 */           slot.func_75215_d(p_75135_1_.func_77946_l().func_77979_a(slot.func_75219_a()));
/* 217 */           slot.func_75218_e();
/* 218 */           p_75135_1_.field_77994_a = resultStackSize;
/*     */         } 
/*     */         
/* 221 */         if (p_75135_4_) {
/*     */           
/* 223 */           k--;
/*     */           
/*     */           continue;
/*     */         } 
/* 227 */         k++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 232 */     return flag1;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\ContainerChargePad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */