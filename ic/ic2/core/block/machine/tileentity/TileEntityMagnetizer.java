/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityMagnetizer
/*     */   extends TileEntityBlock
/*     */   implements IEnergySink, IEnergyContainer
/*     */ {
/*  26 */   public int energy = 0;
/*  27 */   public int ticker = 0;
/*  28 */   public int maxEnergy = 100;
/*  29 */   public int maxInput = 32;
/*     */   
/*     */   public boolean addedToEnergyNet = false;
/*     */   public boolean upgraded = false;
/*     */   
/*     */   public TileEntityMagnetizer() {
/*  35 */     addGuiFields(new String[] { "upgraded", "maxEnergy", "energy" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  41 */     super.func_145839_a(nbttagcompound);
/*  42 */     this.energy = nbttagcompound.func_74765_d("energy");
/*  43 */     this.upgraded = nbttagcompound.func_74767_n("Upgrade");
/*  44 */     if (this.upgraded)
/*     */     {
/*  46 */       upgradeMagnetizer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  53 */     super.func_145841_b(nbttagcompound);
/*  54 */     nbttagcompound.func_74777_a("energy", (short)this.energy);
/*  55 */     nbttagcompound.func_74757_a("Upgrade", this.upgraded);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  61 */     return isSimulating();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  67 */     super.onLoaded();
/*  68 */     if (isSimulating()) {
/*     */       
/*  70 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  71 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  78 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  80 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  81 */       this.addedToEnergyNet = false;
/*     */     } 
/*  83 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  88 */     super.func_145845_h();
/*  89 */     if (this.ticker-- > 0) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     boolean change = false; int y;
/*  94 */     for (y = this.field_145848_d - 1; y > 0 && (this.upgraded || (!this.upgraded && y >= this.field_145848_d - 20)) && ((this.upgraded && this.energy > 9) || (!this.upgraded && this.energy > 0)) && this.field_145850_b.func_147439_a(this.field_145851_c, y, this.field_145849_e) == Block.func_149634_a(Ic2Items.ironFence.func_77973_b()); y--) {
/*     */       
/*  96 */       int need = 15 - this.field_145850_b.func_72805_g(this.field_145851_c, y, this.field_145849_e);
/*  97 */       if (need > 0) {
/*     */         
/*  99 */         if (this.upgraded)
/*     */         {
/* 101 */           need *= 10;
/*     */         }
/* 103 */         change = true;
/* 104 */         if (need > this.energy)
/*     */         {
/* 106 */           this.energy = need;
/*     */         }
/* 108 */         this.field_145850_b.func_72921_c(this.field_145851_c, y, this.field_145849_e, this.field_145850_b.func_72805_g(this.field_145851_c, y, this.field_145849_e) + (this.upgraded ? (need / 10) : need), 3);
/* 109 */         getNetwork().announceBlockUpdate(this.field_145850_b, this.field_145851_c, y, this.field_145849_e);
/* 110 */         this.energy -= need;
/*     */       } 
/*     */     } 
/* 113 */     for (y = this.field_145848_d + 1; y < IC2.getWorldHeight(this.field_145850_b) && (this.upgraded || (!this.upgraded && y <= this.field_145848_d + 20)) && ((this.upgraded && this.energy > 9) || (!this.upgraded && this.energy > 0)) && this.field_145850_b.func_147439_a(this.field_145851_c, y, this.field_145849_e) == Block.func_149634_a(Ic2Items.ironFence.func_77973_b()); y++) {
/*     */       
/* 115 */       int need = 15 - this.field_145850_b.func_72805_g(this.field_145851_c, y, this.field_145849_e);
/* 116 */       if (need > 0) {
/*     */         
/* 118 */         if (this.upgraded)
/*     */         {
/* 120 */           need *= 10;
/*     */         }
/* 122 */         change = true;
/* 123 */         if (need > this.energy)
/*     */         {
/* 125 */           this.energy = need;
/*     */         }
/* 127 */         this.field_145850_b.func_72921_c(this.field_145851_c, y, this.field_145849_e, this.field_145850_b.func_72805_g(this.field_145851_c, y, this.field_145849_e) + (this.upgraded ? (need / 10) : need), 3);
/* 128 */         getNetwork().announceBlockUpdate(this.field_145850_b, this.field_145851_c, y, this.field_145849_e);
/* 129 */         this.energy -= need;
/*     */       } 
/*     */     } 
/* 132 */     if (!change) {
/*     */       
/* 134 */       this.ticker = 10;
/*     */     }
/*     */     else {
/*     */       
/* 138 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 150 */     return (this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 156 */     if (amount > this.maxInput)
/*     */     {
/* 158 */       return 0.0D;
/*     */     }
/* 160 */     this.energy = (int)(this.energy + amount);
/* 161 */     int re = 0;
/* 162 */     if (this.energy > this.maxEnergy) {
/*     */       
/* 164 */       re = this.energy - this.maxEnergy;
/* 165 */       this.energy = this.maxEnergy;
/*     */     } 
/* 167 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 168 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRightClick(EntityPlayer player) {
/* 173 */     if (!this.field_145850_b.field_72995_K && !this.upgraded) {
/*     */       
/* 175 */       ItemStack stack = player.func_71045_bC();
/* 176 */       if (stack != null && stack.func_77969_a(Ic2Items.overclockerUpgrade)) {
/*     */         
/* 178 */         stack.field_77994_a--;
/* 179 */         upgradeMagnetizer();
/* 180 */         player.field_71069_bz.func_75142_b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void upgradeMagnetizer() {
/* 187 */     this.upgraded = true;
/* 188 */     this.maxEnergy = 1000;
/* 189 */     this.maxInput = 128;
/* 190 */     getNetwork().updateTileGuiField((TileEntity)this, "upgraded");
/* 191 */     getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/* 192 */     getNetwork().updateTileGuiField((TileEntity)this, "maxInput");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockBreak(Block a, int b) {
/* 198 */     super.onBlockBreak(a, b);
/* 199 */     if (this.upgraded)
/*     */     {
/* 201 */       StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, Ic2Items.overclockerUpgrade.func_77946_l());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 208 */     return EnergyNet.instance.getTierFromPower(this.maxInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 214 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 220 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 226 */     return this.upgraded ? 150 : 15;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 238 */     return this.upgraded ? 128 : 32;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityMagnetizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */