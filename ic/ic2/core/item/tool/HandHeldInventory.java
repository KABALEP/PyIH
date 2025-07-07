/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ public abstract class HandHeldInventory
/*     */   implements IHasGui, IInventory
/*     */ {
/*     */   protected ItemStack itemStack;
/*     */   protected ItemStack[] inventory;
/*     */   
/*     */   public HandHeldInventory(EntityPlayer entityPlayer, ItemStack itemStack, int inventorySize) {
/*  22 */     this.itemStack = itemStack;
/*  23 */     this.inventory = new ItemStack[inventorySize];
/*  24 */     if (IC2.platform.isSimulating()) {
/*     */       
/*  26 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
/*  27 */       nbtTagCompound.func_74768_a("uid", (new Random()).nextInt());
/*  28 */       NBTTagList nbtTagList = nbtTagCompound.func_150295_c("Items", 10);
/*  29 */       for (int i = 0; i < nbtTagList.func_74745_c(); i++) {
/*     */         
/*  31 */         NBTTagCompound nbtTagCompoundSlot = nbtTagList.func_150305_b(i);
/*  32 */         int slot = nbtTagCompoundSlot.func_74771_c("Slot");
/*  33 */         if (slot >= 0 && slot < this.inventory.length)
/*     */         {
/*  35 */           this.inventory[slot] = ItemStack.func_77949_a(nbtTagCompoundSlot);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  43 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  48 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/*  53 */     if (this.inventory[slot] == null)
/*     */     {
/*  55 */       return null;
/*     */     }
/*  57 */     if ((this.inventory[slot]).field_77994_a <= amount) {
/*     */       
/*  59 */       ItemStack itemstack = this.inventory[slot];
/*  60 */       this.inventory[slot] = null;
/*  61 */       return itemstack;
/*     */     } 
/*  63 */     ItemStack ret = this.inventory[slot].func_77979_a(amount);
/*  64 */     if ((this.inventory[slot]).field_77994_a == 0)
/*     */     {
/*  66 */       this.inventory[slot] = null;
/*     */     }
/*  68 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack itemStack) {
/*  73 */     this.inventory[slot] = itemStack;
/*  74 */     if (itemStack != null && itemStack.field_77994_a > func_70297_j_())
/*     */     {
/*  76 */       itemStack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  82 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityPlayer) {
/*  91 */     return true;
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
/*     */   
/*     */   public boolean func_145818_k_() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int var1) {
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {
/* 116 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 118 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(this.itemStack);
/* 119 */       boolean dropItself = false;
/* 120 */       for (int i = 0; i < func_70302_i_(); i++) {
/*     */         
/* 122 */         if (this.inventory[i] != null) {
/*     */           
/* 124 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(this.inventory[i]);
/* 125 */           if (nbtTagCompound.func_74762_e("uid") == nbtTagCompoundSlot.func_74762_e("uid")) {
/*     */             
/* 127 */             this.itemStack.field_77994_a = 1;
/* 128 */             this.inventory[i] = null;
/* 129 */             dropItself = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 134 */       NBTTagList nbtTagList = new NBTTagList(); int j;
/* 135 */       for (j = 0; j < this.inventory.length; j++) {
/*     */         
/* 137 */         if (this.inventory[j] != null) {
/*     */           
/* 139 */           NBTTagCompound nbtTagCompoundSlot = new NBTTagCompound();
/* 140 */           nbtTagCompoundSlot.func_74774_a("Slot", (byte)j);
/* 141 */           this.inventory[j].func_77955_b(nbtTagCompoundSlot);
/* 142 */           nbtTagList.func_74742_a((NBTBase)nbtTagCompoundSlot);
/*     */         } 
/*     */       } 
/* 145 */       nbtTagCompound.func_74782_a("Items", (NBTBase)nbtTagList);
/* 146 */       if (dropItself) {
/*     */         
/* 148 */         StackUtil.dropAsEntity(entityPlayer.field_70170_p, (int)entityPlayer.field_70165_t, (int)entityPlayer.field_70163_u, (int)entityPlayer.field_70161_v, this.itemStack);
/*     */       }
/*     */       else {
/*     */         
/* 152 */         for (j = -1; j < entityPlayer.field_71071_by.func_70302_i_(); j++) {
/*     */           ItemStack itemStackSlot;
/*     */           
/* 155 */           if (j == -1) {
/*     */             
/* 157 */             itemStackSlot = entityPlayer.field_71071_by.func_70445_o();
/*     */           }
/*     */           else {
/*     */             
/* 161 */             itemStackSlot = entityPlayer.field_71071_by.func_70301_a(j);
/*     */           } 
/* 163 */           if (itemStackSlot != null) {
/*     */             
/* 165 */             NBTTagCompound nbtTagCompoundSlot2 = itemStackSlot.func_77978_p();
/* 166 */             if (nbtTagCompoundSlot2 != null && nbtTagCompound.func_74762_e("uid") == nbtTagCompoundSlot2.func_74762_e("uid")) {
/*     */               
/* 168 */               this.itemStack.field_77994_a = 1;
/* 169 */               if (j == -1) {
/*     */                 
/* 171 */                 entityPlayer.field_71071_by.func_70437_b(this.itemStack);
/*     */                 break;
/*     */               } 
/* 174 */               entityPlayer.field_71071_by.func_70299_a(j, this.itemStack);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesUid(int uid) {
/* 185 */     NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(this.itemStack);
/* 186 */     return (nbtTagCompound.func_74762_e("uid") == uid);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\HandHeldInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */