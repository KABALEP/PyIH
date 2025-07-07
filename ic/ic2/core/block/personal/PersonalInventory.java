/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import ic2.core.block.IIndirectInventory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class PersonalInventory
/*     */   implements IPersonalInventory {
/*     */   TileEntity owner;
/*     */   String inventoryName;
/*     */   ItemStack[] inv;
/*     */   
/*     */   public PersonalInventory(IPersonalBlock par1, String name, int size) {
/*  18 */     this.owner = (TileEntity)par1;
/*  19 */     this.inv = new ItemStack[size];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  25 */     return this.inv.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  30 */     return this.inv[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int p_70304_1_) {
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/*  41 */     if (this.inv[i] == null)
/*     */     {
/*  43 */       return null;
/*     */     }
/*  45 */     if ((this.inv[i]).field_77994_a <= j) {
/*     */       
/*  47 */       ItemStack itemstack = this.inv[i];
/*  48 */       this.inv[i] = null;
/*  49 */       return itemstack;
/*     */     } 
/*  51 */     ItemStack itemstack2 = this.inv[i].func_77979_a(j);
/*  52 */     if ((this.inv[i]).field_77994_a == 0)
/*     */     {
/*  54 */       this.inv[i] = null;
/*     */     }
/*  56 */     return itemstack2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  61 */     this.inv[i] = itemstack;
/*  62 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_())
/*     */     {
/*  64 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  71 */     return this.inventoryName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  83 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  89 */     this.owner.func_70296_d();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer p_70300_1_) {
/*  95 */     return ((IPersonalBlock)this.owner).canAccess(p_70300_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 101 */     if (this.owner instanceof IIndirectInventory)
/*     */     {
/* 103 */       ((IIndirectInventory)this.owner).openInventory();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 110 */     if (this.owner instanceof IIndirectInventory)
/*     */     {
/* 112 */       ((IIndirectInventory)this.owner).closeInventory();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttag) {
/* 125 */     NBTTagList nbttaglist = nbttag.func_150295_c("PersonalInventory", 10);
/* 126 */     this.inv = new ItemStack[func_70302_i_()];
/* 127 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/* 129 */       NBTTagCompound nbttagcompound2 = nbttaglist.func_150305_b(i);
/* 130 */       byte byte0 = nbttagcompound2.func_74771_c("Slot");
/* 131 */       if (byte0 >= 0 && byte0 < this.inv.length)
/*     */       {
/* 133 */         this.inv[byte0] = ItemStack.func_77949_a(nbttagcompound2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttag) {
/* 140 */     NBTTagList nbttaglist = new NBTTagList();
/* 141 */     for (int i = 0; i < this.inv.length; i++) {
/*     */       
/* 143 */       if (this.inv[i] != null) {
/*     */         
/* 145 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 146 */         nbttagcompound2.func_74774_a("Slot", (byte)i);
/* 147 */         this.inv[i].func_77955_b(nbttagcompound2);
/* 148 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound2);
/*     */       } 
/*     */     } 
/* 151 */     nbttag.func_74782_a("PersonalInventory", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvEmpty() {
/* 156 */     boolean flag = true;
/* 157 */     for (int i = 0; i < this.inv.length; i++) {
/*     */       
/* 159 */       if (this.inv[i] != null) {
/*     */         
/* 161 */         flag = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 165 */     return flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\PersonalInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */