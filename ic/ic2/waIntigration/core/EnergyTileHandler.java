/*     */ package ic2.waIntigration.core;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.network.internal.INetworkGuiDataProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import mcp.mobius.waila.api.IWailaConfigHandler;
/*     */ import mcp.mobius.waila.api.IWailaDataAccessor;
/*     */ import mcp.mobius.waila.api.IWailaDataProvider;
/*     */ import mcp.mobius.waila.api.impl.ConfigHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyTileHandler
/*     */   implements IWailaDataProvider
/*     */ {
/*  40 */   public HudMode mode = HudMode.All;
/*  41 */   public int ticker = 0;
/*     */   public final boolean ignoreWaila;
/*  43 */   public static Map<EntityPlayer, TileEntity> tracking = new HashMap<>();
/*  44 */   public static Set<EntityPlayer> skipping = new HashSet<>();
/*     */ 
/*     */   
/*     */   public EnergyTileHandler(boolean par1) {
/*  48 */     this.ignoreWaila = par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound arg2, World arg3, int arg4, int arg5, int arg6) {
/*  54 */     NetworkManager manager = (NetworkManager)IC2.network.get();
/*  55 */     if (tracking.get(arg0) != arg1) {
/*     */       
/*  57 */       this.ticker = 0;
/*  58 */       if (arg1 instanceof INetworkGuiDataProvider)
/*     */       {
/*  60 */         manager.requestInitialGuiData((EntityPlayer)arg0, (INetworkGuiDataProvider)arg1);
/*     */       }
/*  62 */       tracking.put(arg0, arg1);
/*     */     } 
/*  64 */     skipping.add(arg0);
/*  65 */     manager.updateGuiChanges((EntityPlayer)arg0, arg1);
/*  66 */     return arg2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getWailaBody(ItemStack item, List<String> list, IWailaDataAccessor access, IWailaConfigHandler config) {
/*  72 */     if (this.ticker > 0)
/*     */     {
/*  74 */       this.ticker--;
/*     */     }
/*  76 */     TileEntity tile = access.getTileEntity();
/*  77 */     if (tile instanceof ic2.api.energy.tile.IEnergyTile) {
/*     */       
/*  79 */       EntityPlayer player = access.getPlayer();
/*  80 */       if (IC2.keyboard.isHudModeKeyDown(player) && this.ticker == 0) {
/*     */         
/*  82 */         this.ticker = 20;
/*  83 */         switchMode(player);
/*     */       } 
/*  85 */       if (this.mode == HudMode.Dissabled)
/*     */       {
/*  87 */         return list;
/*     */       }
/*     */       
/*  90 */       if (!hasHotbarItem(player, Ic2Items.ecMeter) && !hasHotbarItem(player, Ic2Items.debug)) {
/*     */         
/*  92 */         list.add(StatCollector.func_74838_a("wailaInfo.requireEUReader.name"));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  97 */         if (tile instanceof IEnergySink && (this.mode == HudMode.SinkOnly || this.mode == HudMode.All)) {
/*     */           
/*  99 */           IEnergySink sink = (IEnergySink)tile;
/* 100 */           if (this.mode == HudMode.SinkOnly || (this.mode == HudMode.All && !(tile instanceof IEnergyContainer)))
/*     */           {
/* 102 */             addToList(list, HudConfig.SinkInput, config, StatCollector.func_74837_a("wailaInfo.energySinkMaxInput.name", new Object[] { Integer.valueOf((int)EnergyNet.instance.getPowerFromTier(sink.getSinkTier())) }));
/*     */           }
/* 104 */           addToList(list, HudConfig.SinkDemainded, config, StatCollector.func_74837_a("wailaInfo.energySinkDemainded.name", new Object[] { Integer.valueOf((int)sink.getDemandedEnergy()) }));
/*     */         } 
/*     */         
/* 107 */         if (tile instanceof IEnergyStorage && (this.mode == HudMode.StorageOnly || this.mode == HudMode.All)) {
/*     */           
/* 109 */           IEnergyStorage storage = (IEnergyStorage)tile;
/* 110 */           addToList(list, HudConfig.StorageTeleporter, config, StatCollector.func_74837_a("wailaInfo.energyStorageTeleporterInfo.name", new Object[] { getBooleanString(storage.isTeleporterCompatible(access.getSide())) }));
/* 111 */           if (this.mode == HudMode.StorageOnly || (this.mode == HudMode.All && !(tile instanceof IEnergyContainer)))
/*     */           {
/* 113 */             addToList(list, HudConfig.StorageStorageInfo, config, StatCollector.func_74837_a("wailaInfo.energyStorageStorageInfo.name", new Object[] { Integer.valueOf(storage.getStored()), Integer.valueOf(storage.getCapacity()) }));
/*     */           }
/*     */         } 
/* 116 */         if (tile instanceof IEnergyContainer && (this.mode == HudMode.EnergyContainer || this.mode == HudMode.All)) {
/*     */           
/* 118 */           IEnergyContainer container = (IEnergyContainer)tile;
/* 119 */           addToList(list, HudConfig.ContainerStorage, config, StatCollector.func_74837_a("wailaInfo.energyContainerStorage.name", new Object[] { Integer.valueOf(container.getStoredEnergy()), Integer.valueOf(container.getEnergyCapacity()) }));
/* 120 */           int amount = container.getMaxEnergyInput();
/* 121 */           if (amount > 0)
/*     */           {
/* 123 */             addToList(list, HudConfig.ContainerMaxIn, config, StatCollector.func_74837_a("wailaInfo.energyContainerMaxInput.name", new Object[] { Integer.valueOf(amount) }));
/*     */           }
/* 125 */           amount = container.getEnergyUsage();
/* 126 */           if (amount > 0)
/*     */           {
/* 128 */             addToList(list, HudConfig.ContainerUsage, config, StatCollector.func_74837_a("wailaInfo.energyContainerUsage.name", new Object[] { Integer.valueOf(amount) }));
/*     */           }
/* 130 */           amount = container.getEnergyProduction();
/* 131 */           if (amount > 0)
/*     */           {
/* 133 */             addToList(list, HudConfig.ContainerProduction, config, StatCollector.func_74837_a("wailaInfo.energyContainerProduction.name", new Object[] { Integer.valueOf(amount) }));
/*     */           }
/*     */         } 
/*     */         
/* 137 */         if (tile instanceof IEnergySource && (this.mode == HudMode.SourceOnly || this.mode == HudMode.All)) {
/*     */           
/* 139 */           IEnergySource source = (IEnergySource)tile;
/* 140 */           int packets = 1;
/* 141 */           if (tile instanceof IMultiEnergySource) {
/*     */             
/* 143 */             IMultiEnergySource multi = (IMultiEnergySource)tile;
/* 144 */             if (multi.sendMultibleEnergyPackets())
/*     */             {
/* 146 */               packets = multi.getMultibleEnergyPacketAmount();
/*     */             }
/*     */           } 
/* 149 */           addToList(list, HudConfig.SourceProvide, config, StatCollector.func_74837_a("wailaInfo.energySourceProvide.name", new Object[] { Double.valueOf(source.getOfferedEnergy()) }));
/* 150 */           addToList(list, HudConfig.SourcePackets, config, StatCollector.func_74837_a("wailaInfo.energySourcePackets.name", new Object[] { Integer.valueOf(packets) }));
/*     */         } 
/*     */         
/* 153 */         if (tile instanceof IEnergyConductor && (this.mode == HudMode.CableOnly || this.mode == HudMode.All)) {
/*     */           
/* 155 */           IEnergyConductor wire = (IEnergyConductor)tile;
/* 156 */           if (tile instanceof IEnergyConductorColored) {
/*     */             
/* 158 */             IEnergyConductorColored color = (IEnergyConductorColored)tile;
/* 159 */             addToList(list, HudConfig.ConductorColor, config, StatCollector.func_74837_a("wailaInfo.energyConductorColor.name", new Object[] { color.getConductorColor() }));
/*     */           } 
/* 161 */           addToList(list, HudConfig.ConductorMaxEnergy, config, StatCollector.func_74837_a("wailaInfo.energyConductorMaxEnergy.name", new Object[] { Integer.valueOf((int)(wire.getConductorBreakdownEnergy() - 1.0D)) }));
/* 162 */           addToList(list, HudConfig.ConductorLoss, config, StatCollector.func_74837_a("wailaInfo.energyConductorLoss.name", new Object[] { Double.valueOf(wire.getConductionLoss()) }));
/*     */         } 
/*     */       } 
/*     */     } 
/* 166 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToList(List<String> par1, HudConfig type, IWailaConfigHandler config, String toAdd) {
/* 171 */     if (!this.ignoreWaila || ((ConfigHandler)config).getConfig("modules", type.getConfig(), false))
/*     */     {
/* 173 */       par1.add(toAdd);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getWailaHead(ItemStack item, List<String> list, IWailaDataAccessor access, IWailaConfigHandler arg3) {
/* 180 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWailaStack(IWailaDataAccessor arg0, IWailaConfigHandler arg1) {
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getWailaTail(ItemStack item, List<String> list, IWailaDataAccessor access, IWailaConfigHandler arg3) {
/* 192 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBooleanString(boolean par1) {
/* 197 */     if (par1)
/*     */     {
/* 199 */       return StatCollector.func_74838_a("wailaInfo.yes.name");
/*     */     }
/* 201 */     return StatCollector.func_74838_a("wailaInfo.no.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHotbarItem(EntityPlayer player, ItemStack item) {
/* 206 */     InventoryPlayer inv = player.field_71071_by;
/* 207 */     for (int i = 0; i < 9; i++) {
/*     */       
/* 209 */       ItemStack stack = inv.func_70301_a(i);
/* 210 */       if (stack != null && stack.func_77969_a(item))
/*     */       {
/* 212 */         return true;
/*     */       }
/*     */     } 
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchMode(EntityPlayer player) {
/* 220 */     int value = this.mode.ordinal() + 1;
/* 221 */     if ((HudMode.values()).length <= value)
/*     */     {
/* 223 */       value = 0;
/*     */     }
/* 225 */     this.mode = HudMode.values()[value];
/* 226 */     IC2.platform.messagePlayer(player, StatCollector.func_74837_a("wailaInfo.switchHud.name", new Object[] { this.mode.getName() }));
/*     */   }
/*     */   
/*     */   public enum HudConfig
/*     */   {
/* 231 */     SinkInput("ic2.sink.in"),
/* 232 */     SinkDemainded("ic2.sink.req"),
/* 233 */     StorageTeleporter("ic2.storage.tele"),
/* 234 */     StorageStorageInfo("ic2.storage.info"),
/* 235 */     ContainerStorage("ic2.cont.info"),
/* 236 */     ContainerMaxIn("ic2.cont.in"),
/* 237 */     ContainerUsage("ic2.cont.use"),
/* 238 */     ContainerProduction("ic2.cont.prod"),
/* 239 */     SourceProvide("ic2.source.prov"),
/* 240 */     SourcePackets("ic2.source.packets"),
/* 241 */     ConductorColor("ic2.cable.color"),
/* 242 */     ConductorMaxEnergy("ic2.cable.max"),
/* 243 */     ConductorLoss("ic2.cable.loss");
/*     */     
/*     */     String config;
/*     */     
/*     */     HudConfig(String par1) {
/* 248 */       this.config = par1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConfig() {
/* 253 */       return this.config;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 258 */       return StatCollector.func_74838_a(this.config + ".name");
/*     */     }
/*     */   }
/*     */   
/*     */   public enum HudMode
/*     */   {
/* 264 */     SinkOnly("wailaInfo.SinkHudOnly.name"),
/* 265 */     CableOnly("wailaInfo.WireHudOnly.name"),
/* 266 */     SourceOnly("wailaInfo.SourceHudOnly.name"),
/* 267 */     StorageOnly("wailaInfo.StorageHudOnly.name"),
/* 268 */     EnergyContainer("wailaInfo.ContainerHudOnly.name"),
/* 269 */     All("wailaInfo.HudAll.name"),
/* 270 */     Dissabled("wailaInfo.HudDissabled.name");
/*     */     
/*     */     String unloclaizedName;
/*     */     
/*     */     HudMode(String par1) {
/* 275 */       this.unloclaizedName = par1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 280 */       return StatCollector.func_74838_a(this.unloclaizedName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\waIntigration\core\EnergyTileHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */