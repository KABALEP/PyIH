/*     */ package ic2.core.energy;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.api.energy.IPacketEnergyNet;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.PacketStat;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyNetGlobal
/*     */   implements IPacketEnergyNet
/*     */ {
/*  28 */   private static Map<World, EnergyNetLocal> worldToEnergyNetMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   private static EventHandler eventHandler;
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(World world, int x, int y, int z) {
/*  35 */     EnergyNetLocal local = getForWorld(world);
/*  36 */     if (local != null)
/*     */     {
/*  38 */       return local.getTileEntity(x, y, z);
/*     */     }
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity getNeighbor(TileEntity te, ForgeDirection dir) {
/*  46 */     if (te == null)
/*     */     {
/*  48 */       return null;
/*     */     }
/*  50 */     return getTileEntity(te.func_145831_w(), te.field_145851_c + dir.offsetX, te.field_145848_d + dir.offsetY, te.field_145849_e + dir.offsetZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTotalEnergyEmitted(TileEntity tileEntity) {
/*  56 */     if (tileEntity == null)
/*     */     {
/*  58 */       return 0.0D;
/*     */     }
/*  60 */     EnergyNetLocal local = getForWorld(tileEntity.func_145831_w());
/*  61 */     if (local != null)
/*     */     {
/*  63 */       return local.getTotalEnergyEmitted(tileEntity);
/*     */     }
/*  65 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTotalEnergySunken(TileEntity tileEntity) {
/*  71 */     if (tileEntity == null)
/*     */     {
/*  73 */       return 0.0D;
/*     */     }
/*  75 */     EnergyNetLocal local = getForWorld(tileEntity.func_145831_w());
/*  76 */     if (local != null)
/*     */     {
/*  78 */       return local.getTotalEnergySunken(tileEntity);
/*     */     }
/*  80 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPowerFromTier(int tier) {
/*  86 */     return (8 << tier * 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PacketStat> getSendedPackets(TileEntity par1) {
/*  92 */     if (par1 == null)
/*     */     {
/*  94 */       return new ArrayList<>();
/*     */     }
/*  96 */     this; EnergyNetLocal local = getForWorld(par1.func_145831_w());
/*  97 */     if (local != null)
/*     */     {
/*  99 */       return local.getSendedPackets(par1);
/*     */     }
/* 101 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PacketStat> getTotalSendedPackets(TileEntity par1) {
/* 107 */     if (par1 == null)
/*     */     {
/* 109 */       return new ArrayList<>();
/*     */     }
/* 111 */     this; EnergyNetLocal local = getForWorld(par1.func_145831_w());
/* 112 */     if (local != null)
/*     */     {
/* 114 */       return local.getTotalSendedPackets(par1);
/*     */     }
/* 116 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeStats getNodeStats(TileEntity te) {
/* 122 */     EnergyNetLocal local = getForWorld(te.func_145831_w());
/* 123 */     if (local == null)
/*     */     {
/* 125 */       return new NodeStats(0.0D, 0.0D, 0.0D);
/*     */     }
/* 127 */     return local.getNodeStats(te);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTierFromPower(double power) {
/* 133 */     if (power <= 0.0D) return 0;
/*     */     
/* 135 */     return (int)Math.ceil(Math.log(power / 8.0D) / Math.log(4.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnergyNetLocal getForWorld(World world) {
/* 140 */     if (world == null) {
/*     */       
/* 142 */       IC2.log.warn("EnergyNet.getForWorld: world = null, bad things may happen..");
/* 143 */       return null;
/*     */     } 
/* 145 */     if (!worldToEnergyNetMap.containsKey(world))
/*     */     {
/* 147 */       worldToEnergyNetMap.put(world, new EnergyNetLocal(world));
/*     */     }
/* 149 */     return worldToEnergyNetMap.get(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onTickStart(World world) {
/* 154 */     EnergyNetLocal energyNet = getForWorld(world);
/* 155 */     if (energyNet != null)
/*     */     {
/* 157 */       energyNet.onTickStart();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onTickEnd(World world) {
/* 163 */     EnergyNetLocal energyNet = getForWorld(world);
/* 164 */     if (energyNet != null)
/*     */     {
/* 166 */       energyNet.onTickEnd();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnergyNetGlobal initialize() {
/* 172 */     eventHandler = new EventHandler();
/* 173 */     EnergyNetLocal.list = new EnergyTransferList();
/* 174 */     return new EnergyNetGlobal();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EventHandler
/*     */   {
/*     */     public EventHandler() {
/* 181 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */ 
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onEnergyTileLoad(EnergyTileLoadEvent event) {
/* 187 */       EnergyNetLocal local = EnergyNetGlobal.getForWorld(event.world);
/* 188 */       if (local != null)
/*     */       {
/* 190 */         local.addTile((TileEntity)event.energyTile);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onEnergyTileUnload(EnergyTileUnloadEvent event) {
/* 197 */       EnergyNetLocal local = EnergyNetGlobal.getForWorld(event.world);
/* 198 */       if (local != null)
/*     */       {
/* 200 */         local.removeTile((TileEntity)event.energyTile);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onWorldUnload(WorldEvent.Unload event) {
/* 207 */       EnergyNetLocal local = (EnergyNetLocal)EnergyNetGlobal.worldToEnergyNetMap.get(event.world);
/* 208 */       if (local != null)
/*     */       {
/* 210 */         local.onUnload();
/*     */       }
/* 212 */       ((NetworkManager)IC2.network.get()).onWorldUnload(event.world);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<Integer, List<AxisAlignedBB>> getBoxes() {
/* 218 */     Map<Integer, List<AxisAlignedBB>> boxes = new HashMap<>();
/* 219 */     for (Map.Entry<World, EnergyNetLocal> entry : worldToEnergyNetMap.entrySet()) {
/*     */       
/* 221 */       List<AxisAlignedBB> boundingbox = ((EnergyNetLocal)entry.getValue()).getBoxes();
/* 222 */       if (boundingbox.isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 226 */       boxes.put(Integer.valueOf(((World)entry.getKey()).field_73011_w.field_76574_g), boundingbox);
/*     */     } 
/* 228 */     return boxes;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\energy\EnergyNetGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */