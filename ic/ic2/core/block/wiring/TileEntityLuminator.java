/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.network.INetworkDataProvider;
/*     */ import ic2.api.network.INetworkUpdateListener;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityLuminator
/*     */   extends TileEntity implements IEnergySink, IWrenchable, INetworkUpdateListener, INetworkDataProvider {
/*     */   public boolean active;
/*     */   public boolean preActive;
/*     */   public boolean ignoreBlockStay = false;
/*     */   public boolean addedToEnergyNet = false;
/*     */   private boolean loaded = false;
/*  33 */   public int energy = 0;
/*  34 */   public int ticker = -1;
/*  35 */   public int maxInput = 32;
/*  36 */   public ItemStack glass = null;
/*  37 */   public int color = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/*  42 */     List<String> list = new ArrayList<>();
/*  43 */     list.add("color");
/*  44 */     list.add("active");
/*  45 */     list.add("glass");
/*  46 */     list.add("energy");
/*  47 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  52 */     super.func_145839_a(nbt);
/*  53 */     this.energy = nbt.func_74765_d("energy");
/*  54 */     this.active = nbt.func_74767_n("Active");
/*  55 */     this.ignoreBlockStay = nbt.func_74767_n("Ignore");
/*  56 */     this.color = nbt.func_74762_e("Color");
/*  57 */     if (nbt.func_74764_b("Glass"))
/*     */     {
/*  59 */       this.glass = ItemStack.func_77949_a(nbt.func_74775_l("Glass"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  65 */     super.func_145841_b(nbt);
/*  66 */     nbt.func_74777_a("energy", (short)this.energy);
/*  67 */     nbt.func_74757_a("Active", this.active);
/*  68 */     nbt.func_74757_a("Ignore", this.ignoreBlockStay);
/*  69 */     nbt.func_74768_a("Color", this.color);
/*  70 */     if (this.glass != null) {
/*     */       
/*  72 */       NBTTagCompound data = new NBTTagCompound();
/*  73 */       this.glass.func_77955_b(data);
/*  74 */       nbt.func_74782_a("Glass", (NBTBase)data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getGlass() {
/*  80 */     return this.glass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlass(ItemStack item) {
/*  85 */     this.glass = item;
/*  86 */     ((NetworkManager)IC2.network.get()).updateTileEntityField(this, "glass");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/*  92 */     if (field.equals("glass") || field.equals("color"))
/*     */     {
/*  94 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*  96 */     if (field.equals("active")) {
/*     */       
/*  98 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  99 */       this.field_145850_b.func_147463_c(EnumSkyBlock.Block, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/* 105 */     super.func_145829_t();
/* 106 */     if (!this.loaded) {
/*     */       
/* 108 */       onLoaded();
/* 109 */       this.loaded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 115 */     super.func_145843_s();
/* 116 */     if (this.loaded)
/*     */     {
/* 118 */       onUnloaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 124 */     super.onChunkUnload();
/* 125 */     if (this.loaded)
/*     */     {
/* 127 */       onUnloaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 133 */     if (IC2.platform.isSimulating() && !this.addedToEnergyNet) {
/*     */       
/* 135 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 136 */       this.addedToEnergyNet = true;
/*     */     } 
/* 138 */     this.loaded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 143 */     if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 145 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 146 */       this.addedToEnergyNet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 152 */     if (this.active) {
/*     */       
/* 154 */       this.ticker++;
/* 155 */       if (this.ticker % 4 == 0)
/*     */       {
/* 157 */         this.energy--;
/* 158 */         if (this.energy <= 0)
/*     */         {
/* 160 */           setActive(false);
/*     */         }
/*     */       }
/*     */     
/* 164 */     } else if (this.energy > 0 && !this.active) {
/*     */       
/* 166 */       setActive(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 173 */     return IC2.platform.isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 178 */     return ((emitter instanceof TileEntityCable || emitter instanceof ic2.api.energy.tile.IEnergyConductor) && canCableConnectFrom(emitter.field_145851_c, emitter.field_145848_d, emitter.field_145849_e));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 183 */     return (getMaxEnergy() - this.energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 188 */     if (amount > this.maxInput)
/*     */     {
/* 190 */       return 0.0D;
/*     */     }
/* 192 */     if (amount <= 0.0D)
/*     */     {
/* 194 */       return 0.0D;
/*     */     }
/* 196 */     this.energy = (int)(this.energy + amount);
/* 197 */     int re = 0;
/* 198 */     if (this.energy > getMaxEnergy()) {
/*     */       
/* 200 */       re = this.energy - getMaxEnergy();
/* 201 */       this.energy = getMaxEnergy();
/*     */     } 
/* 203 */     if (!this.active)
/*     */     {
/* 205 */       setActive(true);
/*     */     }
/* 207 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 213 */     return EnergyNet.instance.getTierFromPower(this.maxInput);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/* 218 */     return 10000;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCableConnectFrom(int x, int y, int z) {
/* 223 */     int facing = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 224 */     switch (facing) {
/*     */ 
/*     */       
/*     */       case 0:
/* 228 */         return (x == this.field_145851_c && y == this.field_145848_d + 1 && z == this.field_145849_e);
/*     */ 
/*     */       
/*     */       case 1:
/* 232 */         return (x == this.field_145851_c && y == this.field_145848_d - 1 && z == this.field_145849_e);
/*     */ 
/*     */       
/*     */       case 2:
/* 236 */         return (x == this.field_145851_c && y == this.field_145848_d && z == this.field_145849_e + 1);
/*     */ 
/*     */       
/*     */       case 3:
/* 240 */         return (x == this.field_145851_c && y == this.field_145848_d && z == this.field_145849_e - 1);
/*     */ 
/*     */       
/*     */       case 4:
/* 244 */         return (x == this.field_145851_c + 1 && y == this.field_145848_d && z == this.field_145849_e);
/*     */ 
/*     */       
/*     */       case 5:
/* 248 */         return (x == this.field_145851_c - 1 && y == this.field_145848_d && z == this.field_145849_e);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getFacing() {
/* 266 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacing(short facing) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 278 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 284 */     return 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
/* 290 */     return Ic2Items.luminator.func_77946_l();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setActive(boolean par1) {
/* 295 */     this.active = par1;
/* 296 */     if (this.active != this.preActive)
/*     */     {
/* 298 */       ((NetworkManager)IC2.network.get()).updateTileEntityField(this, "active");
/*     */     }
/* 300 */     this.preActive = par1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int par1) {
/* 305 */     this.color = par1;
/* 306 */     ((NetworkManager)IC2.network.get()).updateTileEntityField(this, "color");
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityLuminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */