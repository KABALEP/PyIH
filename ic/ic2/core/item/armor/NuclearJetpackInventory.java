/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ public class NuclearJetpackInventory
/*     */   implements IInventory, IHasGui
/*     */ {
/*     */   public ItemStack[] parts;
/*     */   public int id;
/*     */   
/*     */   public NuclearJetpackInventory(EntityPlayer par1, ItemStack par2) {
/*  21 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par2);
/*  22 */     readFromNBT(nbt.func_74775_l("Inventory"));
/*  23 */     this.id = par1.field_70170_p.field_73012_v.nextInt();
/*  24 */     nbt.func_74775_l("Logic").func_74768_a("output", 0);
/*  25 */     nbt.func_74768_a("Uuid", this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  31 */     return 25;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  37 */     return this.parts[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int par1, int par2) {
/*  43 */     if (this.parts[par1] != null) {
/*     */ 
/*     */ 
/*     */       
/*  47 */       if ((this.parts[par1]).field_77994_a <= par2) {
/*     */         
/*  49 */         ItemStack itemStack = this.parts[par1];
/*  50 */         this.parts[par1] = null;
/*  51 */         return itemStack;
/*     */       } 
/*     */ 
/*     */       
/*  55 */       ItemStack itemstack = this.parts[par1].func_77979_a(par2);
/*     */       
/*  57 */       if ((this.parts[par1]).field_77994_a == 0)
/*     */       {
/*  59 */         this.parts[par1] = null;
/*     */       }
/*     */       
/*  62 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int par1) {
/*  74 */     if (this.parts[par1] != null) {
/*     */       
/*  76 */       ItemStack itemstack = this.parts[par1];
/*  77 */       this.parts[par1] = null;
/*  78 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int par1, ItemStack par2ItemStack) {
/*  89 */     this.parts[par1] = par2ItemStack;
/*     */     
/*  91 */     if (par2ItemStack != null && par2ItemStack.field_77994_a > func_70297_j_())
/*     */     {
/*  93 */       par2ItemStack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 100 */     return "Nuclear Jetpack Inventory";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 107 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 126 */     return new ContainerNuclearJetpack(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 132 */     return "item.armor.GuiNuclearJetpack";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {
/* 138 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 140 */       ItemStack item = getItemFromPlayer(p0);
/* 141 */       if (item != null) {
/*     */         
/* 143 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 144 */         NBTTagCompound data = new NBTTagCompound();
/* 145 */         writeToNBT(data);
/* 146 */         nbt.func_74782_a("Inventory", (NBTBase)data);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemFromPlayer(EntityPlayer par1) {
/* 153 */     ItemStack stack = null;
/* 154 */     for (int i = 0; i < par1.field_71071_by.func_70302_i_(); i++) {
/*     */       
/* 156 */       ItemStack item = par1.field_71071_by.func_70301_a(i);
/* 157 */       if (item != null && item.func_77973_b() instanceof INuclearJetpack) {
/*     */         
/* 159 */         int Uid = ((INuclearJetpack)item.func_77973_b()).getID(item);
/* 160 */         if (Uid == this.id) {
/*     */           
/* 162 */           stack = item;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 167 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound par1) {
/* 172 */     NBTTagList nbttaglist = par1.func_150295_c("Items", 10);
/* 173 */     this.parts = new ItemStack[func_70302_i_()];
/* 174 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/* 176 */       NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
/* 177 */       byte b0 = nbttagcompound1.func_74771_c("Slot");
/* 178 */       if (b0 >= 0 && b0 < this.parts.length)
/*     */       {
/* 180 */         this.parts[b0] = ItemStack.func_77949_a(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound par1) {
/* 187 */     NBTTagList nbttaglist = new NBTTagList();
/* 188 */     for (int i = 0; i < this.parts.length; i++) {
/*     */       
/* 190 */       if (this.parts[i] != null) {
/*     */         
/* 192 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 193 */         nbttagcompound1.func_74774_a("Slot", (byte)i);
/* 194 */         this.parts[i].func_77955_b(nbttagcompound1);
/* 195 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/* 198 */     par1.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 204 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70296_d() {}
/*     */   
/*     */   public void func_70295_k_() {}
/*     */   
/*     */   public void func_70305_f() {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\NuclearJetpackInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */