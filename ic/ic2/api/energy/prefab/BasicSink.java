/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySink;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicSink
/*     */   extends TileEntity
/*     */   implements IEnergySink
/*     */ {
/*     */   public final TileEntity parent;
/*     */   protected int capacity;
/*     */   protected int tier;
/*     */   protected double energyStored;
/*     */   protected boolean addedToEnet;
/*     */   
/*     */   public BasicSink(TileEntity parent1, int capacity1, int tier1) {
/* 104 */     this.parent = parent1;
/* 105 */     this.capacity = capacity1;
/* 106 */     this.tier = tier1;
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
/* 117 */     if (!this.addedToEnet) onLoaded();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 125 */     if (!this.addedToEnet && 
/* 126 */       !FMLCommonHandler.instance().getEffectiveSide().isClient() && 
/* 127 */       Info.isIc2Available()) {
/* 128 */       this.field_145850_b = this.parent.func_145831_w();
/* 129 */       this.field_145851_c = this.parent.field_145851_c;
/* 130 */       this.field_145848_d = this.parent.field_145848_d;
/* 131 */       this.field_145849_e = this.parent.field_145849_e;
/*     */       
/* 133 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */       
/* 135 */       this.addedToEnet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 145 */     super.func_145843_s();
/*     */     
/* 147 */     onChunkUnload();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 156 */     if (this.addedToEnet && 
/* 157 */       Info.isIc2Available()) {
/* 158 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */       
/* 160 */       this.addedToEnet = false;
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
/* 171 */     super.func_145839_a(tag);
/*     */     
/* 173 */     NBTTagCompound data = tag.func_74775_l("IC2BasicSink");
/*     */     
/* 175 */     this.energyStored = data.func_74769_h("energy");
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
/* 186 */       super.func_145841_b(tag);
/* 187 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */     
/* 191 */     NBTTagCompound data = new NBTTagCompound();
/*     */     
/* 193 */     data.func_74780_a("energy", this.energyStored);
/*     */     
/* 195 */     tag.func_74782_a("IC2BasicSink", (NBTBase)data);
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
/*     */   public int getCapacity() {
/* 207 */     return this.capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCapacity(int capacity1) {
/* 216 */     this.capacity = capacity1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier() {
/* 225 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTier(int tier1) {
/* 234 */     this.tier = tier1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStored() {
/* 243 */     return this.energyStored;
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
/* 255 */     this.energyStored = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseEnergy(double amount) {
/* 265 */     return (this.energyStored >= amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 275 */     if (canUseEnergy(amount) && !FMLCommonHandler.instance().getEffectiveSide().isClient()) {
/* 276 */       this.energyStored -= amount;
/*     */       
/* 278 */       return true;
/*     */     } 
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean discharge(ItemStack stack, int limit) {
/* 291 */     if (stack == null || !Info.isIc2Available()) return false;
/*     */     
/* 293 */     double amount = this.capacity - this.energyStored;
/* 294 */     if (amount <= 0.0D) return false;
/*     */     
/* 296 */     if (limit > 0 && limit < amount) amount = limit;
/*     */     
/* 298 */     amount = ElectricItem.manager.discharge(stack, amount, this.tier, (limit > 0), true, false);
/*     */     
/* 300 */     this.energyStored += amount;
/*     */     
/* 302 */     return (amount > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void onUpdateEntity() {
/* 311 */     func_145845_h();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onInvalidate() {
/* 316 */     func_145843_s();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onOnChunkUnload() {
/* 321 */     onChunkUnload();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onReadFromNbt(NBTTagCompound tag) {
/* 326 */     func_145839_a(tag);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void onWriteToNbt(NBTTagCompound tag) {
/* 331 */     func_145841_b(tag);
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
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 349 */     return Math.max(0.0D, this.capacity - this.energyStored);
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 354 */     this.energyStored += amount;
/*     */     
/* 356 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 361 */     return this.tier;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\prefab\BasicSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */