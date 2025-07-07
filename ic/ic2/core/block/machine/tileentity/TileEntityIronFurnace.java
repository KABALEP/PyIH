/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerIronFurnace;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ 
/*     */ public class TileEntityIronFurnace extends TileEntityMachine implements IHasGui {
/*  20 */   public int fuel = 0;
/*  21 */   public int maxFuel = 0;
/*  22 */   public short progress = 0;
/*  23 */   public short operationLength = 160;
/*     */   
/*     */   public AudioSource sound;
/*     */   
/*     */   public TileEntityIronFurnace() {
/*  28 */     super(3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  34 */     super.func_145839_a(nbttagcompound);
/*  35 */     this.fuel = nbttagcompound.func_74762_e("fuel");
/*  36 */     this.maxFuel = nbttagcompound.func_74762_e("maxFuel");
/*  37 */     this.progress = nbttagcompound.func_74765_d("progress");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  43 */     super.func_145841_b(nbttagcompound);
/*  44 */     nbttagcompound.func_74768_a("fuel", this.fuel);
/*  45 */     nbttagcompound.func_74768_a("maxFuel", this.maxFuel);
/*  46 */     nbttagcompound.func_74777_a("progress", this.progress);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeProgressScaled(int i) {
/*  51 */     return (int)(this.progress / 160.0D * i);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  56 */     if (this.maxFuel == 0) {
/*     */       
/*  58 */       this.maxFuel = this.fuel;
/*  59 */       if (this.maxFuel == 0)
/*     */       {
/*  61 */         this.maxFuel = 160;
/*     */       }
/*     */     } 
/*  64 */     return (int)(this.fuel / this.maxFuel * i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  70 */     super.func_145845_h();
/*  71 */     boolean wasOperating = isBurning();
/*  72 */     boolean needsInvUpdate = false;
/*  73 */     if (this.fuel <= 0 && canOperate()) {
/*     */       
/*  75 */       int fuelValue = getFuelValueFor(this.inventory[1]);
/*  76 */       this.maxFuel = fuelValue;
/*  77 */       this.fuel = fuelValue;
/*  78 */       getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/*  79 */       getNetwork().updateTileGuiField((TileEntity)this, "maxFuel");
/*  80 */       if (this.fuel > 0) {
/*     */         
/*  82 */         if (this.inventory[1].func_77973_b().hasContainerItem(this.inventory[1])) {
/*     */           
/*  84 */           this.inventory[1] = this.inventory[1].func_77973_b().getContainerItem(this.inventory[1]);
/*     */         }
/*     */         else {
/*     */           
/*  88 */           ItemStack itemStack = this.inventory[1];
/*  89 */           itemStack.field_77994_a--;
/*     */         } 
/*  91 */         if ((this.inventory[1]).field_77994_a <= 0)
/*     */         {
/*  93 */           this.inventory[1] = null;
/*     */         }
/*  95 */         needsInvUpdate = true;
/*     */       } 
/*     */     } 
/*  98 */     if (isBurning() && canOperate()) {
/*     */       
/* 100 */       this.progress = (short)(this.progress + 1);
/* 101 */       if (this.progress >= 160) {
/*     */         
/* 103 */         this.progress = 0;
/* 104 */         operate();
/* 105 */         needsInvUpdate = true;
/*     */       } 
/* 107 */       getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */ 
/*     */     
/*     */     }
/* 111 */     else if (this.progress != 0) {
/*     */       
/* 113 */       this.progress = 0;
/* 114 */       getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */     } 
/*     */     
/* 117 */     if (this.fuel > 0) {
/*     */       
/* 119 */       this.fuel--;
/* 120 */       getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/*     */     } 
/* 122 */     if (wasOperating != isBurning()) {
/*     */       
/* 124 */       setActive(isBurning());
/* 125 */       needsInvUpdate = true;
/*     */     } 
/* 127 */     if (needsInvUpdate)
/*     */     {
/* 129 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void operate() {
/* 135 */     if (!canOperate()) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     ItemStack itemstack = getResultFor(this.inventory[0]);
/* 140 */     if (this.inventory[2] == null) {
/*     */       
/* 142 */       this.inventory[2] = itemstack.func_77946_l();
/*     */     }
/*     */     else {
/*     */       
/* 146 */       ItemStack itemStack = this.inventory[2];
/* 147 */       itemStack.field_77994_a += itemstack.field_77994_a;
/*     */     } 
/* 149 */     if (this.inventory[0].func_77973_b().hasContainerItem(this.inventory[0])) {
/*     */       
/* 151 */       this.inventory[0] = this.inventory[0].func_77973_b().getContainerItem(this.inventory[0]);
/*     */     }
/*     */     else {
/*     */       
/* 155 */       ItemStack itemStack2 = this.inventory[0];
/* 156 */       itemStack2.field_77994_a--;
/*     */     } 
/* 158 */     if ((this.inventory[0]).field_77994_a <= 0)
/*     */     {
/* 160 */       this.inventory[0] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 166 */     return (this.fuel > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canOperate() {
/* 171 */     if (this.inventory[0] == null)
/*     */     {
/* 173 */       return false;
/*     */     }
/* 175 */     ItemStack itemstack = getResultFor(this.inventory[0]);
/* 176 */     return (itemstack != null && (this.inventory[2] == null || (this.inventory[2].func_77969_a(itemstack) && (this.inventory[2]).field_77994_a + itemstack.field_77994_a <= this.inventory[2].func_77976_d())));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFuelValueFor(ItemStack itemstack) {
/* 181 */     if (itemstack == null)
/*     */     {
/* 183 */       return 0;
/*     */     }
/* 185 */     Item itemIndex = itemstack.func_77973_b();
/* 186 */     if (itemIndex == Items.field_151129_at || itemstack.func_77969_a(Ic2Items.lavaCell))
/*     */     {
/* 188 */       return 2000;
/*     */     }
/* 190 */     return TileEntityFurnace.func_145952_a(itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultFor(ItemStack itemstack) {
/* 195 */     return FurnaceRecipes.func_77602_a().func_151395_a(itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 201 */     return "Iron Furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 207 */     return (ContainerIC2)new ContainerIronFurnace(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 213 */     return "block.machine.gui.GuiIronFurnace";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 223 */     switch (var1) {
/*     */ 
/*     */       
/*     */       case 1:
/* 227 */         return new int[] { 0 };
/*     */ 
/*     */       
/*     */       case 0:
/* 231 */         return new int[] { 1 };
/*     */     } 
/*     */ 
/*     */     
/* 235 */     return new int[] { 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 243 */     if (i == 2)
/*     */     {
/* 245 */       return false;
/*     */     }
/* 247 */     return super.func_102007_a(i, itemstack, j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 253 */     super.onUnloaded();
/* 254 */     if (this.sound != null) {
/*     */       
/* 256 */       this.sound.remove();
/* 257 */       this.sound = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 264 */     super.onNetworkUpdate(field);
/* 265 */     if (field.equals("active")) {
/*     */       
/* 267 */       if (this.sound == null)
/*     */       {
/* 269 */         this.sound = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/IronFurnaceOp.ogg", true, true, IC2.audioManager.getDefaultVolume());
/*     */       }
/* 271 */       if (this.sound != null)
/*     */       {
/* 273 */         if (getActive() && !this.sound.isPlaying()) {
/*     */           
/* 275 */           this.sound.play();
/*     */         }
/* 277 */         else if (!getActive() && this.sound.isPlaying()) {
/*     */           
/* 279 */           this.sound.stop();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityIronFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */