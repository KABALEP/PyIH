/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityAdjustableTransformer
/*     */   extends TileEntityBlock
/*     */   implements IHasGui, IEnergySink, IMultiEnergySource, IEnergyContainer, INetworkClientTileEntityEventListener
/*     */ {
/*     */   public int energy;
/*  26 */   public int sinkTier = 6;
/*  27 */   public int packetCount = 1;
/*  28 */   public int energyPacket = 32;
/*  29 */   public int maxEnergy = 256;
/*     */   
/*     */   public boolean enet;
/*     */ 
/*     */   
/*     */   public TileEntityAdjustableTransformer() {
/*  35 */     addGuiFields(new String[] { "energy", "maxEnergy", "packetCount", "energyPacket" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  42 */     super.func_145839_a(nbt);
/*  43 */     this.energy = nbt.func_74762_e("Energy");
/*  44 */     this.packetCount = nbt.func_74762_e("Packet");
/*  45 */     this.energyPacket = nbt.func_74762_e("PacketEnergy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  51 */     super.func_145841_b(nbt);
/*  52 */     nbt.func_74768_a("Energy", this.energy);
/*  53 */     nbt.func_74768_a("Packet", this.packetCount);
/*  54 */     nbt.func_74768_a("PacketEnergy", this.energyPacket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  61 */     super.onLoaded();
/*  62 */     if (isSimulating() && !this.enet) {
/*     */       
/*  64 */       this.enet = true;
/*  65 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  72 */     super.onUnloaded();
/*  73 */     if (isSimulating() && this.enet) {
/*     */       
/*  75 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  76 */       this.enet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacing(short side) {
/*  83 */     if (this.enet)
/*     */     {
/*  85 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     }
/*  87 */     this.enet = false;
/*  88 */     super.setFacing(side);
/*  89 */     MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  90 */     this.enet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/*  96 */     return (direction.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 102 */     return (direction.ordinal() != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 108 */     if (this.energyPacket > this.energy)
/*     */     {
/* 110 */       return 0.0D;
/*     */     }
/* 112 */     return this.energyPacket;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 118 */     this.energy = (int)(this.energy - amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 124 */     return EnergyNet.instance.getTierFromPower(this.energyPacket);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 130 */     return Math.max(0, this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 136 */     return this.sinkTier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 142 */     if (amount > EnergyNet.instance.getPowerFromTier(this.sinkTier))
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
/*     */   public boolean sendMultibleEnergyPackets() {
/* 160 */     return (this.packetCount > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultibleEnergyPacketAmount() {
/* 166 */     return this.packetCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 173 */     if (event >= 0 && event <= 1) {
/*     */       
/* 175 */       int amount = (event == 0) ? 1 : 10;
/* 176 */       this.packetCount += amount;
/* 177 */       if (this.packetCount > 32)
/*     */       {
/* 179 */         this.packetCount = 32;
/*     */       }
/* 181 */       getNetwork().updateTileGuiField((TileEntity)this, "packetCount");
/*     */     } 
/* 183 */     if (event >= 2 && event <= 3) {
/*     */       
/* 185 */       int amount = (event == 2) ? 1 : 10;
/* 186 */       this.packetCount -= amount;
/* 187 */       if (this.packetCount < 1)
/*     */       {
/* 189 */         this.packetCount = 1;
/*     */       }
/* 191 */       getNetwork().updateTileGuiField((TileEntity)this, "packetCount");
/*     */     } 
/*     */     
/* 194 */     if (event >= 4 && event <= 7) {
/*     */       
/* 196 */       int amount = (event == 4) ? 1 : ((event == 5) ? 10 : ((event == 6) ? 100 : 1000));
/* 197 */       this.energyPacket += amount;
/* 198 */       if (this.energyPacket > 32767)
/*     */       {
/* 200 */         this.energyPacket = 32767;
/*     */       }
/* 202 */       this.maxEnergy = this.energyPacket * 32;
/* 203 */       getNetwork().updateTileGuiField((TileEntity)this, "energyPacket");
/* 204 */       getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */     } 
/* 206 */     if (event >= 8 && event <= 11) {
/*     */       
/* 208 */       int amount = (event == 8) ? 1 : ((event == 9) ? 10 : ((event == 10) ? 100 : 1000));
/* 209 */       this.energyPacket -= amount;
/* 210 */       if (this.energyPacket < 1)
/*     */       {
/* 212 */         this.energyPacket = 1;
/*     */       }
/* 214 */       this.maxEnergy = this.energyPacket * 32;
/* 215 */       if (this.energy > this.maxEnergy) {
/*     */         
/* 217 */         this.energy = this.maxEnergy;
/* 218 */         getNetwork().updateTileGuiField((TileEntity)this, "energy");
/*     */       } 
/* 220 */       getNetwork().updateTileGuiField((TileEntity)this, "energyPacket");
/* 221 */       getNetwork().updateTileGuiField((TileEntity)this, "maxEnergy");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 228 */     return (side != getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 234 */     return this.energy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 240 */     return this.maxEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 246 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 252 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 258 */     return (int)EnergyNet.instance.getPowerFromTier(this.sinkTier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 264 */     return new ContainerAdjustableTransformer(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 270 */     return "block.wiring.GuiAdjustableTransformer";
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityAdjustableTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */