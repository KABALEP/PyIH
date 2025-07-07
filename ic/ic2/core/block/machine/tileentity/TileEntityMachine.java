/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityMachine
/*     */   extends TileEntityBlock
/*     */   implements IInventory, ISidedInventory
/*     */ {
/*     */   public ItemStack[] inventory;
/*     */   
/*     */   public TileEntityMachine(int slotcount) {
/*  20 */     this.inventory = new ItemStack[slotcount];
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  25 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  40 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/*  45 */     if (this.inventory[i] == null)
/*     */     {
/*  47 */       return null;
/*     */     }
/*  49 */     if ((this.inventory[i]).field_77994_a <= j) {
/*     */       
/*  51 */       ItemStack itemstack = this.inventory[i];
/*  52 */       this.inventory[i] = null;
/*  53 */       return itemstack;
/*     */     } 
/*  55 */     ItemStack itemstack2 = this.inventory[i].func_77979_a(j);
/*  56 */     if ((this.inventory[i]).field_77994_a == 0)
/*     */     {
/*  58 */       this.inventory[i] = null;
/*     */     }
/*  60 */     return itemstack2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  65 */     this.inventory[i] = itemstack;
/*  66 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_())
/*     */     {
/*  68 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  74 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  79 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70011_f(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String func_145825_b();
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  87 */     super.func_145839_a(nbttagcompound);
/*  88 */     NBTTagList nbttaglist = nbttagcompound.func_150295_c("Items", 10);
/*  89 */     this.inventory = new ItemStack[func_70302_i_()];
/*  90 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/*  92 */       NBTTagCompound nbttagcompound2 = nbttaglist.func_150305_b(i);
/*  93 */       byte byte0 = nbttagcompound2.func_74771_c("Slot");
/*  94 */       if (byte0 >= 0 && byte0 < this.inventory.length)
/*     */       {
/*  96 */         this.inventory[byte0] = ItemStack.func_77949_a(nbttagcompound2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 104 */     super.func_145841_b(nbttagcompound);
/* 105 */     NBTTagList nbttaglist = new NBTTagList();
/* 106 */     for (int i = 0; i < this.inventory.length; i++) {
/*     */       
/* 108 */       if (this.inventory[i] != null) {
/*     */         
/* 110 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 111 */         nbttagcompound2.func_74774_a("Slot", (byte)i);
/* 112 */         this.inventory[i].func_77955_b(nbttagcompound2);
/* 113 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound2);
/*     */       } 
/*     */     } 
/* 116 */     nbttagcompound.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 122 */     return isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 127 */     super.func_145845_h();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int var1) {
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public void func_70295_k_() {}
/*     */   
/*     */   public void func_70305_f() {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */