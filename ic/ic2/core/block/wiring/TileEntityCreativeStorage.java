/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
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
/*     */ public class TileEntityCreativeStorage
/*     */   extends TileEntityBlock
/*     */   implements IMultiEnergySource, IEnergyContainer, INetworkClientTileEntityEventListener, IHasGui
/*     */ {
/*  24 */   public int offer = 32;
/*  25 */   public int packets = 4;
/*  26 */   public byte step = 0;
/*     */   
/*     */   public boolean enet;
/*     */   
/*     */   public TileEntityCreativeStorage() {
/*  31 */     addGuiFields(new String[] { "offer", "packets" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/*  37 */     return this.offer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/*  49 */     return EnergyNet.instance.getTierFromPower(this.offer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/*  73 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/*  79 */     return this.offer * this.packets;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/*  85 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendMultibleEnergyPackets() {
/*  91 */     return (this.packets > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultibleEnergyPacketAmount() {
/*  97 */     return this.packets;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 103 */     if (this.step == 0) {
/*     */       
/* 105 */       this.offer = event;
/*     */     }
/* 107 */     else if (this.step == 1) {
/*     */       
/* 109 */       this.packets = event;
/*     */     } 
/* 111 */     this.step = (byte)(this.step + 1);
/* 112 */     if (this.step == 2) {
/*     */       
/* 114 */       this.step = 0;
/* 115 */       getNetwork().updateTileGuiField((TileEntity)this, "offer");
/* 116 */       getNetwork().updateTileGuiField((TileEntity)this, "packets");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 123 */     return new ContainerCreativeStorage(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 129 */     return "block.wiring.GuiCreativeStorage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 141 */     super.func_145839_a(nbttagcompound);
/* 142 */     this.offer = nbttagcompound.func_74762_e("energy");
/* 143 */     this.packets = nbttagcompound.func_74762_e("Packets");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 149 */     super.func_145841_b(nbttagcompound);
/* 150 */     nbttagcompound.func_74768_a("energy", this.offer);
/* 151 */     nbttagcompound.func_74768_a("Packets", this.packets);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 157 */     super.onLoaded();
/* 158 */     if (isSimulating() && !this.enet) {
/*     */       
/* 160 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 161 */       this.enet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 168 */     if (isSimulating() && this.enet) {
/*     */       
/* 170 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 171 */       this.enet = false;
/*     */     } 
/* 173 */     super.onUnloaded();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityCreativeStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */