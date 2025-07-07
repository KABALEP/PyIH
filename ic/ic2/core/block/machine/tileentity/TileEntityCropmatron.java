/*     */ package ic2.core.block.machine.tileentity;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityCrop;
/*     */ import ic2.core.block.machine.container.ContainerCropmatron;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityCropmatron extends TileEntityMachine implements IEnergySink, IHasGui, IEnergyContainer {
/*  24 */   public int energy = 0;
/*  25 */   public int ticker = 0;
/*  26 */   public int maxEnergy = 1000;
/*  27 */   public int scanX = -4;
/*  28 */   public int scanY = -1;
/*  29 */   public int scanZ = -4;
/*     */   public boolean addedToEnergyNet = false;
/*  31 */   public static int maxInput = 32;
/*     */ 
/*     */   
/*     */   public TileEntityCropmatron() {
/*  35 */     super(9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  41 */     super.func_145839_a(nbttagcompound);
/*  42 */     this.energy = nbttagcompound.func_74765_d("energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  48 */     super.func_145841_b(nbttagcompound);
/*  49 */     nbttagcompound.func_74777_a("energy", (short)this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  55 */     super.onLoaded();
/*  56 */     if (isSimulating()) {
/*     */       
/*  58 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  59 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  66 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  68 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  69 */       this.addedToEnergyNet = false;
/*     */     } 
/*  71 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  77 */     super.func_145845_h();
/*  78 */     if (this.energy >= 31)
/*     */     {
/*  80 */       scan();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void scan() {
/*  86 */     this.scanX++;
/*  87 */     if (this.scanX > 4) {
/*     */       
/*  89 */       this.scanX = -4;
/*  90 */       this.scanZ++;
/*  91 */       if (this.scanZ > 4) {
/*     */         
/*  93 */         this.scanZ = -4;
/*  94 */         this.scanY++;
/*  95 */         if (this.scanY > 1)
/*     */         {
/*  97 */           this.scanY = -1;
/*     */         }
/*     */       } 
/*     */     } 
/* 101 */     this.energy--;
/* 102 */     TileEntity te = this.field_145850_b.func_147438_o(this.field_145851_c + this.scanX, this.field_145848_d + this.scanY, this.field_145849_e + this.scanZ);
/* 103 */     if (te instanceof TileEntityCrop) {
/*     */       
/* 105 */       TileEntityCrop crop = (TileEntityCrop)te;
/* 106 */       updateSlots();
/* 107 */       if (this.inventory[0] != null && this.inventory[0].func_77973_b() == Ic2Items.fertilizer.func_77973_b() && crop.applyFertilizer(false)) {
/*     */         
/* 109 */         this.energy -= 10;
/* 110 */         ItemStack itemStack = this.inventory[0];
/* 111 */         itemStack.field_77994_a--;
/* 112 */         checkStackSizeZero(0);
/*     */       } 
/* 114 */       if (this.inventory[3] != null && this.inventory[3].func_77973_b() == Ic2Items.hydratingCell.func_77973_b() && crop.applyHydration(false, this.inventory[3])) {
/*     */         
/* 116 */         this.energy -= 10;
/* 117 */         checkStackSizeZero(3);
/*     */       } 
/* 119 */       if (this.inventory[6] != null && this.inventory[6].func_77973_b() == Ic2Items.weedEx.func_77973_b() && crop.applyWeedEx(false)) {
/*     */         
/* 121 */         this.energy -= 10;
/* 122 */         this.inventory[6].func_77964_b(this.inventory[6].func_77960_j() + 1);
/* 123 */         if (this.inventory[6].func_77960_j() >= this.inventory[6].func_77958_k()) {
/*     */           
/* 125 */           ItemStack itemStack2 = this.inventory[6];
/* 126 */           itemStack2.field_77994_a--;
/* 127 */           checkStackSizeZero(6);
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     getNetwork().updateTileEntityField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkStackSizeZero(int x) {
/* 136 */     if (this.inventory[x] != null && (this.inventory[x]).field_77994_a <= 0)
/*     */     {
/* 138 */       this.inventory[x] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSlots() {
/* 144 */     moveFrom(1, 0);
/* 145 */     moveFrom(2, 1);
/* 146 */     moveFrom(4, 3);
/* 147 */     moveFrom(5, 4);
/* 148 */     moveFrom(7, 6);
/* 149 */     moveFrom(8, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveFrom(int from, int to) {
/* 154 */     if (this.inventory[from] != null && this.inventory[to] == null) {
/*     */       
/* 156 */       this.inventory[to] = this.inventory[from];
/* 157 */       this.inventory[from] = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 169 */     return (this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeEnergyScaled(int i) {
/* 174 */     if (this.energy <= 0)
/*     */     {
/* 176 */       return 0;
/*     */     }
/* 178 */     int r = this.energy * i / this.maxEnergy;
/* 179 */     if (r > i)
/*     */     {
/* 181 */       r = i;
/*     */     }
/* 183 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 189 */     if (amount > maxInput)
/*     */     {
/* 191 */       return 0.0D;
/*     */     }
/* 193 */     this.energy = (int)(this.energy + amount);
/* 194 */     int re = 0;
/* 195 */     if (this.energy > this.maxEnergy) {
/*     */       
/* 197 */       re = this.energy - this.maxEnergy;
/* 198 */       this.energy = this.maxEnergy;
/*     */     } 
/* 200 */     ((NetworkManager)IC2.network.get()).updateTileGuiField((TileEntity)this, "energy");
/* 201 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 207 */     return EnergyNet.instance.getTierFromPower(maxInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 213 */     return "Crop-Matron";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 219 */     return "block.machine.gui.GuiCropmatron";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 225 */     return (ContainerIC2)new ContainerCropmatron(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 235 */     if (var1 == 0)
/*     */     {
/* 237 */       return new int[] { 6, 7, 8 };
/*     */     }
/* 239 */     if (var1 == 1)
/*     */     {
/* 241 */       return new int[] { 3, 4, 5 };
/*     */     }
/* 243 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack item, int j) {
/* 249 */     if (item == null)
/*     */     {
/* 251 */       return false;
/*     */     }
/* 253 */     if (i < 3)
/*     */     {
/* 255 */       return (item.func_77973_b() == Ic2Items.fertilizer.func_77973_b());
/*     */     }
/* 257 */     if (i < 6)
/*     */     {
/* 259 */       return (item.func_77973_b() == Ic2Items.hydratingCell.func_77973_b());
/*     */     }
/*     */ 
/*     */     
/* 263 */     return (item.func_77973_b() == Ic2Items.weedEx.func_77973_b());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 270 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 276 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 282 */     return 31;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 288 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 294 */     return 32;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCropmatron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */