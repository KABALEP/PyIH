/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.ITickCallback;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemCropSeed;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandHeldCropnalyzer
/*     */   implements IHasGui, ITickCallback, IInventory
/*     */ {
/*     */   private ItemStack itemStack;
/*     */   private ItemStack[] inventory;
/*     */   
/*     */   public HandHeldCropnalyzer(EntityPlayer entityPlayer, ItemStack itemStack) {
/*  34 */     this.inventory = new ItemStack[3];
/*  35 */     this.itemStack = itemStack;
/*  36 */     if (IC2.platform.isSimulating()) {
/*     */       
/*  38 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
/*  39 */       nbtTagCompound.func_74768_a("uid", (new Random()).nextInt());
/*  40 */       NBTTagList nbtTagList = nbtTagCompound.func_150295_c("Items", 10);
/*  41 */       for (int i = 0; i < nbtTagList.func_74745_c(); i++) {
/*     */         
/*  43 */         NBTTagCompound nbtTagCompoundSlot = nbtTagList.func_150305_b(i);
/*  44 */         int slot = nbtTagCompoundSlot.func_74771_c("Slot");
/*  45 */         if (slot >= 0 && slot < this.inventory.length)
/*     */         {
/*  47 */           this.inventory[slot] = ItemStack.func_77949_a(nbtTagCompoundSlot);
/*     */         }
/*     */       } 
/*  50 */       IC2.addContinuousTickCallback(entityPlayer.field_70170_p, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  56 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  61 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/*  66 */     if (this.inventory[slot] == null)
/*     */     {
/*  68 */       return null;
/*     */     }
/*  70 */     if ((this.inventory[slot]).field_77994_a <= amount) {
/*     */       
/*  72 */       ItemStack itemstack = this.inventory[slot];
/*  73 */       this.inventory[slot] = null;
/*  74 */       return itemstack;
/*     */     } 
/*  76 */     ItemStack ret = this.inventory[slot].func_77979_a(amount);
/*  77 */     if ((this.inventory[slot]).field_77994_a == 0)
/*     */     {
/*  79 */       this.inventory[slot] = null;
/*     */     }
/*  81 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack itemStack) {
/*  86 */     this.inventory[slot] = itemStack;
/*  87 */     if (itemStack != null && itemStack.field_77994_a > func_70297_j_())
/*     */     {
/*  89 */       itemStack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  95 */     return "Cropnalyzer";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 100 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityPlayer) {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int var1) {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 134 */     return new ContainerCropnalyzer(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 140 */     return "item.tool.GuiCropnalyzer";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {
/* 146 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 148 */       IC2.removeContinuousTickCallback(entityPlayer.field_70170_p, this);
/* 149 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(this.itemStack);
/* 150 */       boolean dropItself = false;
/* 151 */       for (int i = 0; i < func_70302_i_(); i++) {
/*     */         
/* 153 */         if (this.inventory[i] != null) {
/*     */           
/* 155 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(this.inventory[i]);
/* 156 */           if (nbtTagCompound.func_74762_e("uid") == nbtTagCompoundSlot.func_74762_e("uid")) {
/*     */             
/* 158 */             this.itemStack.field_77994_a = 1;
/* 159 */             this.inventory[i] = null;
/* 160 */             dropItself = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 165 */       NBTTagList nbtTagList = new NBTTagList(); int j;
/* 166 */       for (j = 0; j < this.inventory.length; j++) {
/*     */         
/* 168 */         if (this.inventory[j] != null) {
/*     */           
/* 170 */           NBTTagCompound nbtTagCompoundSlot = new NBTTagCompound();
/* 171 */           nbtTagCompoundSlot.func_74774_a("Slot", (byte)j);
/* 172 */           this.inventory[j].func_77955_b(nbtTagCompoundSlot);
/* 173 */           nbtTagList.func_74742_a((NBTBase)nbtTagCompoundSlot);
/*     */         } 
/*     */       } 
/* 176 */       nbtTagCompound.func_74782_a("Items", (NBTBase)nbtTagList);
/* 177 */       if (dropItself) {
/*     */         
/* 179 */         StackUtil.dropAsEntity(entityPlayer.field_70170_p, (int)entityPlayer.field_70165_t, (int)entityPlayer.field_70163_u, (int)entityPlayer.field_70161_v, this.itemStack);
/*     */       }
/*     */       else {
/*     */         
/* 183 */         for (j = -1; j < entityPlayer.field_71071_by.func_70302_i_(); j++) {
/*     */           ItemStack itemStackSlot;
/*     */           
/* 186 */           if (j == -1) {
/*     */             
/* 188 */             itemStackSlot = entityPlayer.field_71071_by.func_70445_o();
/*     */           }
/*     */           else {
/*     */             
/* 192 */             itemStackSlot = entityPlayer.field_71071_by.func_70301_a(j);
/*     */           } 
/* 194 */           if (itemStackSlot != null) {
/*     */             
/* 196 */             NBTTagCompound nbtTagCompoundSlot2 = itemStackSlot.func_77978_p();
/* 197 */             if (nbtTagCompoundSlot2 != null && nbtTagCompound.func_74762_e("uid") == nbtTagCompoundSlot2.func_74762_e("uid")) {
/*     */               
/* 199 */               this.itemStack.field_77994_a = 1;
/* 200 */               if (j == -1) {
/*     */                 
/* 202 */                 entityPlayer.field_71071_by.func_70437_b(this.itemStack);
/*     */                 break;
/*     */               } 
/* 205 */               entityPlayer.field_71071_by.func_70299_a(j, this.itemStack);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickCallback(World world) {
/* 217 */     if (this.inventory[1] == null && this.inventory[0] != null && this.inventory[0].func_77973_b() == Ic2Items.cropSeed.func_77973_b()) {
/*     */       
/* 219 */       int level = ItemCropSeed.getScannedFromStack(this.inventory[0]);
/* 220 */       if (level == 4) {
/*     */         
/* 222 */         this.inventory[1] = this.inventory[0];
/* 223 */         this.inventory[0] = null;
/*     */         return;
/*     */       } 
/* 226 */       if (this.inventory[2] == null || !(this.inventory[2].func_77973_b() instanceof ic2.api.item.IElectricItem)) {
/*     */         return;
/*     */       }
/*     */       
/* 230 */       int ned = energyForLevel(level);
/* 231 */       int got = (int)ElectricItem.manager.discharge(this.inventory[2], ned, 2, true, true, false);
/* 232 */       if (got < ned) {
/*     */         return;
/*     */       }
/*     */       
/* 236 */       ItemCropSeed.incrementScannedOfStack(this.inventory[0]);
/* 237 */       this.inventory[1] = this.inventory[0];
/* 238 */       this.inventory[0] = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int energyForLevel(int i) {
/* 244 */     switch (i) {
/*     */ 
/*     */       
/*     */       default:
/* 248 */         return 10;
/*     */ 
/*     */       
/*     */       case 1:
/* 252 */         return 90;
/*     */ 
/*     */       
/*     */       case 2:
/* 256 */         return 900;
/*     */       case 3:
/*     */         break;
/*     */     } 
/* 260 */     return 9000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard crop() {
/* 267 */     return Crops.instance.getCropList()[ItemCropSeed.getIdFromStack(this.inventory[1])];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScannedLevel() {
/* 272 */     if (this.inventory[1] == null || this.inventory[1].func_77973_b() != Ic2Items.cropSeed.func_77973_b())
/*     */     {
/* 274 */       return -1;
/*     */     }
/* 276 */     return ItemCropSeed.getScannedFromStack(this.inventory[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeedName() {
/* 281 */     return crop().displayName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeedTier() {
/* 286 */     switch (crop().tier()) {
/*     */ 
/*     */       
/*     */       default:
/* 290 */         return "0";
/*     */ 
/*     */       
/*     */       case 1:
/* 294 */         return "I";
/*     */ 
/*     */       
/*     */       case 2:
/* 298 */         return "II";
/*     */ 
/*     */       
/*     */       case 3:
/* 302 */         return "III";
/*     */ 
/*     */       
/*     */       case 4:
/* 306 */         return "IV";
/*     */ 
/*     */       
/*     */       case 5:
/* 310 */         return "V";
/*     */ 
/*     */       
/*     */       case 6:
/* 314 */         return "VI";
/*     */ 
/*     */       
/*     */       case 7:
/* 318 */         return "VII";
/*     */ 
/*     */       
/*     */       case 8:
/* 322 */         return "VIII";
/*     */ 
/*     */       
/*     */       case 9:
/* 326 */         return "IX";
/*     */ 
/*     */       
/*     */       case 10:
/* 330 */         return "X";
/*     */ 
/*     */       
/*     */       case 11:
/* 334 */         return "XI";
/*     */ 
/*     */       
/*     */       case 12:
/* 338 */         return "XII";
/*     */ 
/*     */       
/*     */       case 13:
/* 342 */         return "XIII";
/*     */ 
/*     */       
/*     */       case 14:
/* 346 */         return "XIV";
/*     */ 
/*     */       
/*     */       case 15:
/* 350 */         return "XV";
/*     */       case 16:
/*     */         break;
/*     */     } 
/* 354 */     return "XVI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeedDiscovered() {
/* 361 */     return crop().discoveredBy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeedDesc(int i) {
/* 366 */     return crop().desc(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeedGrowth() {
/* 371 */     return ItemCropSeed.getGrowthFromStack(this.inventory[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeedGain() {
/* 376 */     return ItemCropSeed.getGainFromStack(this.inventory[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeedResistence() {
/* 381 */     return ItemCropSeed.getResistanceFromStack(this.inventory[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesUid(int uid) {
/* 386 */     NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(this.itemStack);
/* 387 */     return (nbtTagCompound.func_74762_e("uid") == uid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 392 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 397 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\HandHeldCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */