/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.info.Info;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicSource
/*     */   extends TileEntity
/*     */   implements IEnergySource
/*     */ {
/*     */   public final TileEntity parent;
/*     */   protected double capacity;
/*     */   protected int tier;
/*     */   protected double power;
/*     */   protected double energyStored;
/*     */   protected boolean addedToEnet;
/*     */   
/*     */   public BasicSource(TileEntity parent1, double capacity1, int tier1) {
/* 103 */     double power = EnergyNet.instance.getPowerFromTier(tier1);
/*     */     
/* 105 */     this.parent = parent1;
/* 106 */     this.capacity = (capacity1 < power) ? power : capacity1;
/* 107 */     this.tier = tier1;
/* 108 */     this.power = power;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 119 */     if (!this.addedToEnet) onLoaded();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 127 */     if (!this.addedToEnet && 
/* 128 */       !FMLCommonHandler.instance().getEffectiveSide().isClient() && 
/* 129 */       Info.isIc2Available()) {
/* 130 */       this.field_145850_b = this.parent.func_145831_w();
/* 131 */       this.field_145851_c = this.parent.field_145851_c;
/* 132 */       this.field_145848_d = this.parent.field_145848_d;
/* 133 */       this.field_145849_e = this.parent.field_145849_e;
/*     */       
/* 135 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */       
/* 137 */       this.addedToEnet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 147 */     super.func_145843_s();
/*     */     
/* 149 */     onChunkUnload();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 158 */     if (this.addedToEnet && 
/* 159 */       Info.isIc2Available()) {
/* 160 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */       
/* 162 */       this.addedToEnet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound tag) {
/* 173 */     super.func_145839_a(tag);
/*     */     
/* 175 */     NBTTagCompound data = tag.func_74775_l("IC2BasicSource");
/*     */     
/* 177 */     this.energyStored = data.func_74769_h("energy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound tag) {
/*     */     try {
/* 188 */       super.func_145841_b(tag);
/* 189 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */     
/* 193 */     NBTTagCompound data = new NBTTagCompound();
/*     */     
/* 195 */     data.func_74780_a("energy", this.energyStored);
/*     */     
/* 197 */     tag.func_74782_a("IC2BasicSource", (NBTBase)data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCapacity() {
/* 209 */     return this.capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCapacity(double capacity1) {
/* 218 */     if (capacity1 < this.power) capacity1 = this.power;
/*     */     
/* 220 */     this.capacity = capacity1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier() {
/* 229 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTier(int tier1) {
/* 238 */     double power = EnergyNet.instance.getPowerFromTier(tier1);
/*     */     
/* 240 */     if (this.capacity < power) this.capacity = power;
/*     */     
/* 242 */     this.tier = tier1;
/* 243 */     this.power = power;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStored() {
/* 253 */     return this.energyStored;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnergyStored(double amount) {
/* 265 */     this.energyStored = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFreeCapacity() {
/* 274 */     return this.capacity - this.energyStored;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double addEnergy(double amount) {
/* 284 */     if (FMLCommonHandler.instance().getEffectiveSide().isClient()) return 0.0D; 
/* 285 */     if (amount > this.capacity - this.energyStored) amount = this.capacity - this.energyStored;
/*     */     
/* 287 */     this.energyStored += amount;
/*     */     
/* 289 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charge(ItemStack stack) {
/* 299 */     if (stack == null || !Info.isIc2Available()) return false;
/*     */     
/* 301 */     double amount = ElectricItem.manager.charge(stack, this.energyStored, this.tier, false, false);
/*     */     
/* 303 */     this.energyStored -= amount;
/*     */     
/* 305 */     return (amount > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void onUpdateEntity() {
/* 314 */     func_145845_h();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onInvalidate() {
/* 319 */     func_145843_s();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onOnChunkUnload() {
/* 324 */     onChunkUnload();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onReadFromNbt(NBTTagCompound tag) {
/* 329 */     func_145839_a(tag);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onWriteToNbt(NBTTagCompound tag) {
/* 334 */     func_145841_b(tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 347 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 352 */     return Math.min(this.energyStored, this.power);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 357 */     this.energyStored -= amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 362 */     return this.tier;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\prefab\BasicSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */