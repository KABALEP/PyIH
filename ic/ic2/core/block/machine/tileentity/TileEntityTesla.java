/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.item.armor.ItemArmorHazmat;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityTesla
/*     */   extends TileEntityBlock implements IEnergySink, IEnergyContainer {
/*  25 */   public int energy = 0;
/*  26 */   public int ticker = 0;
/*  27 */   public int maxEnergy = 10000;
/*  28 */   public int maxInput = 128;
/*     */   
/*     */   public boolean addedToEnergyNet = false;
/*     */   
/*     */   public TileEntityTesla() {
/*  33 */     addGuiFields(new String[] { "energy" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  39 */     super.func_145839_a(nbttagcompound);
/*  40 */     this.energy = nbttagcompound.func_74765_d("energy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  46 */     super.func_145841_b(nbttagcompound);
/*  47 */     nbttagcompound.func_74777_a("energy", (short)this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  53 */     super.onLoaded();
/*  54 */     if (isSimulating()) {
/*     */       
/*  56 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  57 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  64 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  66 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  67 */       this.addedToEnergyNet = false;
/*     */     } 
/*  69 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  75 */     return isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  80 */     super.func_145845_h();
/*  81 */     if (!isSimulating() || !redstoned()) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     if (this.energy < getCost()) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     int damage = this.energy / getCost();
/*  90 */     this.energy--;
/*  91 */     if (this.ticker++ % 32 == 0 && shock(damage))
/*     */     {
/*  93 */       this.energy = 0;
/*     */     }
/*  95 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shock(int damage) {
/* 100 */     boolean shock = false;
/* 101 */     List<EntityLivingBase> list1 = this.field_145850_b.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a((this.field_145851_c - 4), (this.field_145848_d - 4), (this.field_145849_e - 4), (this.field_145851_c + 5), (this.field_145848_d + 5), (this.field_145849_e + 5)));
/* 102 */     for (int l = 0; l < list1.size(); l++) {
/*     */       
/* 104 */       EntityLivingBase victim = list1.get(l);
/* 105 */       if (!ItemArmorHazmat.hasCompleteHazmat(victim)) {
/*     */         
/* 107 */         shock = true;
/* 108 */         victim.func_70097_a((DamageSource)IC2DamageSource.electricity, damage);
/* 109 */         for (int i = 0; i < damage; i++)
/*     */         {
/* 111 */           this.field_145850_b.func_72869_a("reddust", ((Entity)victim).field_70165_t + this.field_145850_b.field_73012_v.nextFloat(), ((Entity)victim).field_70163_u + (this.field_145850_b.field_73012_v.nextFloat() * 2.0F), ((Entity)victim).field_70161_v + this.field_145850_b.field_73012_v.nextFloat(), 0.0D, 0.0D, 1.0D);
/*     */         }
/*     */       } 
/*     */     } 
/* 115 */     return shock;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean redstoned() {
/* 120 */     return (this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e) || this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCost() {
/* 125 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 136 */     return (this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 142 */     if (amount > this.maxInput)
/*     */     {
/* 144 */       return 0.0D;
/*     */     }
/* 146 */     this.energy = (int)(this.energy + amount);
/* 147 */     int re = 0;
/* 148 */     if (this.energy > this.maxEnergy) {
/*     */       
/* 150 */       re = this.energy - this.maxEnergy;
/* 151 */       this.energy = this.maxEnergy;
/*     */     } 
/* 153 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 154 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 160 */     return EnergyNet.instance.getTierFromPower(this.maxInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 166 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 172 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 178 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 190 */     return 128;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityTesla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */