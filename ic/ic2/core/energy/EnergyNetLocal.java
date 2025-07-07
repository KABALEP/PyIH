/*      */ package ic2.core.energy;
/*      */ 
/*      */ import ic2.api.Direction;
/*      */ import ic2.api.energy.EnergyNet;
/*      */ import ic2.api.energy.NodeStats;
/*      */ import ic2.api.energy.PacketStat;
/*      */ import ic2.api.energy.tile.IEnergyAcceptor;
/*      */ import ic2.api.energy.tile.IEnergyConductor;
/*      */ import ic2.api.energy.tile.IEnergyEmitter;
/*      */ import ic2.api.energy.tile.IEnergySink;
/*      */ import ic2.api.energy.tile.IEnergySource;
/*      */ import ic2.api.energy.tile.IEnergyTile;
/*      */ import ic2.api.energy.tile.IMetaDelegate;
/*      */ import ic2.api.energy.tile.IMultiEnergySource;
/*      */ import ic2.core.ExplosionIC2;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.IC2DamageSource;
/*      */ import ic2.core.util.FilteredList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.ChunkCoordinates;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.common.util.ForgeDirection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EnergyNetLocal
/*      */ {
/*   45 */   public static double minConductionLoss = 1.0E-4D;
/*   46 */   private static Direction[] directions = Direction.values();
/*      */   public static EnergyTransferList list;
/*      */   private World world;
/*   49 */   private EnergyPathMap energySourceToEnergyPathMap = new EnergyPathMap();
/*   50 */   private Map<EntityLivingBase, Integer> entityLivingToShockEnergyMap = new HashMap<>();
/*   51 */   private Map<ChunkCoordinates, IEnergyTile> registeredTiles = new HashMap<>();
/*   52 */   private Map<ChunkCoordinates, IEnergySource> sources = new HashMap<>();
/*   53 */   private WaitingList waitingList = new WaitingList();
/*   54 */   private UnloadingList unloading = new UnloadingList();
/*      */ 
/*      */   
/*      */   EnergyNetLocal(World world) {
/*   58 */     this.world = world;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTile(TileEntity par1) {
/*   63 */     if (par1 instanceof IMetaDelegate) {
/*      */       
/*   65 */       List<TileEntity> tiles = ((IMetaDelegate)par1).getSubTiles();
/*   66 */       for (TileEntity tile : tiles)
/*      */       {
/*   68 */         addTileEntity(coords(tile), par1);
/*      */       }
/*   70 */       if (par1 instanceof IEnergySource)
/*      */       {
/*   72 */         this.sources.put(coords(tiles.get(0)), (IEnergySource)par1);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*   77 */       addTileEntity(coords(par1), par1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(ChunkCoordinates coords, TileEntity tile) {
/*   83 */     if (!(tile instanceof IEnergyTile) || (this.registeredTiles.containsKey(coords) && !this.unloading.contains(coords))) {
/*      */       return;
/*      */     }
/*      */     
/*   87 */     this.registeredTiles.put(coords, (IEnergyTile)tile);
/*   88 */     update(coords.field_71574_a, coords.field_71572_b, coords.field_71573_c);
/*   89 */     if (tile instanceof IEnergyAcceptor) {
/*      */       
/*   91 */       this.waitingList.onTileEntityAdded(getValidReceivers(tile, true), tile);
/*   92 */       this.unloading.onLoaded(coords);
/*      */     } 
/*      */     
/*   95 */     if (tile instanceof IEnergySource && !(tile instanceof IMetaDelegate))
/*      */     {
/*   97 */       this.sources.put(coords, (IEnergySource)tile);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTile(TileEntity par1) {
/*  103 */     if (par1 instanceof IMetaDelegate) {
/*      */       
/*  105 */       List<TileEntity> tiles = ((IMetaDelegate)par1).getSubTiles();
/*  106 */       for (TileEntity tile : tiles)
/*      */       {
/*  108 */         removeTileEntity(coords(tile), par1);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  113 */       removeTileEntity(coords(par1), par1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(ChunkCoordinates coords, TileEntity tile) {
/*  119 */     if (!(tile instanceof IEnergyTile) || !this.registeredTiles.containsKey(coords)) {
/*      */       
/*  121 */       boolean alreadyRemoved = !this.registeredTiles.containsKey(coords);
/*  122 */       if (!alreadyRemoved)
/*      */       {
/*  124 */         IC2.log.warn("removing " + tile + " from the EnergyNet failed, already removed: " + alreadyRemoved);
/*      */       }
/*      */       return;
/*      */     } 
/*  128 */     if (tile instanceof IEnergyAcceptor) {
/*      */       
/*  130 */       this.waitingList.onTileEntityRemoved(tile);
/*  131 */       this.unloading.onUnloaded(getValidReceivers(tile, true), coords, tile);
/*      */     } 
/*  133 */     if (tile instanceof IEnergySource) {
/*      */       
/*  135 */       this.sources.remove(coords);
/*  136 */       this.energySourceToEnergyPathMap.remove(tile);
/*  137 */       if (!(tile instanceof IEnergyAcceptor)) {
/*      */         
/*  139 */         this.registeredTiles.remove(coords);
/*  140 */         update(coords.field_71574_a, coords.field_71572_b, coords.field_71573_c);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<PacketStat> getSendedPackets(TileEntity tileEntity) {
/*  147 */     Map<Integer, EnergyPacket> totalPackets = new LinkedHashMap<>();
/*  148 */     if (tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink)
/*      */     {
/*  150 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor)tileEntity)) {
/*      */         
/*  152 */         if (energyPath.backupEnergyPackets.isEmpty() && !energyPath.mesuredEnergyPackets.isEmpty()) {
/*      */           
/*  154 */           energyPath.backupEnergyPackets.putAll(energyPath.mesuredEnergyPackets);
/*  155 */           energyPath.mesuredEnergyPackets.clear();
/*      */         } 
/*  157 */         addPackets(totalPackets, energyPath.backupEnergyPackets);
/*      */       } 
/*      */     }
/*  160 */     if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey(tileEntity))
/*      */     {
/*  162 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.get(tileEntity)) {
/*      */         
/*  164 */         if (energyPath.backupEnergyPackets.isEmpty() && !energyPath.mesuredEnergyPackets.isEmpty()) {
/*      */           
/*  166 */           energyPath.backupEnergyPackets.putAll(energyPath.mesuredEnergyPackets);
/*  167 */           energyPath.mesuredEnergyPackets.clear();
/*      */         } 
/*  169 */         addPackets(totalPackets, energyPath.backupEnergyPackets);
/*      */       } 
/*      */     }
/*  172 */     List<PacketStat> packets = new ArrayList<>();
/*  173 */     for (Map.Entry<Integer, EnergyPacket> entry : totalPackets.entrySet())
/*      */     {
/*  175 */       packets.add(new PacketStat(((Integer)entry.getKey()).intValue(), ((EnergyPacket)entry.getValue()).getAmount()));
/*      */     }
/*  177 */     Collections.sort(packets);
/*  178 */     return packets;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<PacketStat> getTotalSendedPackets(TileEntity tileEntity) {
/*  183 */     Map<Integer, EnergyPacket> totalPackets = new LinkedHashMap<>();
/*  184 */     if (tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink)
/*      */     {
/*  186 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor)tileEntity))
/*      */       {
/*  188 */         addPackets(totalPackets, energyPath.totalEnergyPackets);
/*      */       }
/*      */     }
/*  191 */     if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey(tileEntity))
/*      */     {
/*  193 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.get(tileEntity))
/*      */       {
/*  195 */         addPackets(totalPackets, energyPath.totalEnergyPackets);
/*      */       }
/*      */     }
/*  198 */     List<PacketStat> packets = new ArrayList<>();
/*  199 */     for (Map.Entry<Integer, EnergyPacket> entry : totalPackets.entrySet())
/*      */     {
/*  201 */       packets.add(new PacketStat(((Integer)entry.getKey()).intValue(), ((EnergyPacket)entry.getValue()).getAmount()));
/*      */     }
/*  203 */     Collections.sort(packets);
/*  204 */     return packets;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getTotalEnergyEmitted(TileEntity tileEntity) {
/*  209 */     double ret = 0.0D;
/*  210 */     if (tileEntity instanceof IEnergyConductor)
/*      */     {
/*  212 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor)tileEntity))
/*      */       {
/*  214 */         ret += energyPath.totalEnergyConducted;
/*      */       }
/*      */     }
/*  217 */     if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey(tileEntity))
/*      */     {
/*  219 */       for (EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get(tileEntity))
/*      */       {
/*  221 */         ret += energyPath2.totalEnergyConducted;
/*      */       }
/*      */     }
/*  224 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getTotalEnergySunken(TileEntity tileEntity) {
/*  229 */     double ret = 0.0D;
/*  230 */     if (tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink)
/*      */     {
/*  232 */       for (EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor)tileEntity))
/*      */       {
/*  234 */         ret += energyPath.totalEnergyConducted;
/*      */       }
/*      */     }
/*  237 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public int emitEnergyFrom(ChunkCoordinates coords, IEnergySource energySource, int amount) {
/*  242 */     if (!this.registeredTiles.containsKey(coords)) {
/*      */       
/*  244 */       IC2.log.warn("EnergyNet.emitEnergyFrom: " + energySource + " is not added to the enet");
/*  245 */       return amount;
/*      */     } 
/*  247 */     if (!this.energySourceToEnergyPathMap.containsKey(energySource))
/*      */     {
/*  249 */       this.energySourceToEnergyPathMap.put(energySource, discover((TileEntity)energySource, false, EnergyTransferList.getMaxEnergy(energySource, amount)));
/*      */     }
/*  251 */     List<EnergyPath> paths = this.energySourceToEnergyPathMap.get(energySource);
/*  252 */     if (paths.isEmpty())
/*      */     {
/*  254 */       return amount;
/*      */     }
/*  256 */     double totalInvLoss = 0.0D;
/*  257 */     List<EnergyPath> activeEnergyPaths = new ArrayList<>(paths.size());
/*  258 */     for (EnergyPath energyPath : paths) {
/*      */       
/*  260 */       assert energyPath.target instanceof IEnergySink;
/*  261 */       IEnergySink energySink = (IEnergySink)energyPath.target;
/*  262 */       if (energySink.getDemandedEnergy() <= 0.0D || energyPath.loss >= amount) {
/*      */         continue;
/*      */       }
/*      */       
/*  266 */       if (IC2.enableIC2EasyMode && conductorToWeak(energyPath, amount)) {
/*      */         continue;
/*      */       }
/*      */       
/*  270 */       totalInvLoss += 1.0D / energyPath.loss;
/*  271 */       activeEnergyPaths.add(energyPath);
/*      */     } 
/*  273 */     if (activeEnergyPaths.isEmpty())
/*      */     {
/*  275 */       return amount;
/*      */     }
/*      */     
/*  278 */     Collections.shuffle(activeEnergyPaths);
/*  279 */     for (int i = activeEnergyPaths.size() - amount; i > 0; i--) {
/*      */       
/*  281 */       EnergyPath removedEnergyPath = activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
/*  282 */       totalInvLoss -= 1.0D / removedEnergyPath.loss;
/*      */     } 
/*  284 */     double source = EnergyNet.instance.getPowerFromTier(energySource.getSourceTier());
/*  285 */     Map<EnergyPath, Integer> suppliedEnergyPaths = new LinkedHashMap<>();
/*  286 */     while (!activeEnergyPaths.isEmpty() && amount > 0) {
/*      */       
/*  288 */       int energyConsumed = 0;
/*  289 */       double newTotalInvLoss = 0.0D;
/*  290 */       List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
/*  291 */       activeEnergyPaths = new ArrayList<>(currentActiveEnergyPaths.size());
/*  292 */       for (EnergyPath energyPath2 : currentActiveEnergyPaths) {
/*      */         
/*  294 */         IEnergySink energySink2 = (IEnergySink)energyPath2.target;
/*  295 */         int energyProvided = (int)Math.floor(Math.round(amount / totalInvLoss / energyPath2.loss * 100000.0D) / 100000.0D);
/*  296 */         int energyLoss = (int)Math.floor(energyPath2.loss);
/*  297 */         if (energyProvided > energyLoss) {
/*      */           
/*  299 */           double providing = (energyProvided - energyLoss);
/*  300 */           double adding = Math.min(providing, energySink2.getDemandedEnergy());
/*      */           
/*  302 */           if (adding <= 0.0D && EnergyTransferList.hasOverrideInput(energySink2))
/*      */           {
/*  304 */             adding = EnergyTransferList.getOverrideInput(energySink2);
/*      */           }
/*      */           
/*  307 */           if (adding <= 0.0D) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           
/*  312 */           int accepting = (int)EnergyNet.instance.getPowerFromTier(energySink2.getSinkTier());
/*      */           
/*  314 */           if (accepting <= 0)
/*      */           {
/*  316 */             accepting = Integer.MAX_VALUE;
/*      */           }
/*      */           
/*  319 */           if (providing > accepting) {
/*      */             
/*  321 */             if (!IC2.enableIC2EasyMode)
/*      */             {
/*  323 */               explodeTiles(energySink2);
/*      */             }
/*      */             continue;
/*      */           } 
/*  327 */           double energyReturned = energySink2.injectEnergy(energyPath2.targetDirection.toForgeDirection(), adding, source);
/*  328 */           if (energyReturned == 0.0D) {
/*      */             
/*  330 */             if (energySink2.getDemandedEnergy() >= 1.0D)
/*      */             {
/*  332 */               activeEnergyPaths.add(energyPath2);
/*  333 */               newTotalInvLoss += 1.0D / energyPath2.loss;
/*      */             }
/*      */           
/*  336 */           } else if (energyReturned >= (energyProvided - energyLoss)) {
/*      */             
/*  338 */             energyReturned = (energyProvided - energyLoss);
/*  339 */             IC2.log.warn("API ERROR: " + energySink2 + " didn't implement demandsEnergy() properly, no energy from injectEnergy accepted although demandsEnergy() returned true.");
/*      */           } 
/*  341 */           energyConsumed = (int)(energyConsumed + adding - energyReturned + energyLoss);
/*  342 */           int energyInjected = (int)(adding - energyReturned);
/*  343 */           if (!suppliedEnergyPaths.containsKey(energyPath2)) {
/*      */             
/*  345 */             suppliedEnergyPaths.put(energyPath2, Integer.valueOf(energyInjected));
/*      */             
/*      */             continue;
/*      */           } 
/*  349 */           suppliedEnergyPaths.put(energyPath2, Integer.valueOf(energyInjected + ((Integer)suppliedEnergyPaths.get(energyPath2)).intValue()));
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  354 */         activeEnergyPaths.add(energyPath2);
/*  355 */         newTotalInvLoss += 1.0D / energyPath2.loss;
/*      */       } 
/*      */       
/*  358 */       if (energyConsumed == 0 && !activeEnergyPaths.isEmpty()) {
/*      */         
/*  360 */         EnergyPath removedEnergyPath2 = activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
/*  361 */         newTotalInvLoss -= 1.0D / removedEnergyPath2.loss;
/*      */       } 
/*  363 */       totalInvLoss = newTotalInvLoss;
/*  364 */       amount -= energyConsumed;
/*      */     } 
/*  366 */     for (Map.Entry<EnergyPath, Integer> entry : suppliedEnergyPaths.entrySet()) {
/*      */       
/*  368 */       EnergyPath energyPath3 = entry.getKey();
/*  369 */       int energyInjected2 = ((Integer)entry.getValue()).intValue();
/*  370 */       EnergyPath energyPath4 = energyPath3;
/*  371 */       energyPath4.totalEnergyConducted += energyInjected2;
/*  372 */       addPacket(energyPath4, energyInjected2);
/*  373 */       if (energyInjected2 > energyPath3.minInsulationEnergyAbsorption) {
/*      */         
/*  375 */         Map<EntityLivingBase, Integer> shocks = new HashMap<>();
/*  376 */         List<EntityLivingBase> entitiesNearEnergyPath = this.world.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a((energyPath3.minX - 1), (energyPath3.minY - 1), (energyPath3.minZ - 1), (energyPath3.maxX + 2), (energyPath3.maxY + 2), (energyPath3.maxZ + 2)));
/*  377 */         for (IEnergyConductor energyConductor : energyPath3.conductors) {
/*      */           
/*  379 */           int shockAbsorbing = (int)energyConductor.getInsulationEnergyAbsorption();
/*  380 */           if (shockAbsorbing >= energyInjected2) {
/*      */             continue;
/*      */           }
/*      */           
/*  384 */           ChunkCoordinates coord = coords((TileEntity)energyConductor);
/*  385 */           AxisAlignedBB box = AxisAlignedBB.func_72330_a((coords.field_71574_a - 1), (coords.field_71572_b - 1), (coords.field_71573_c - 1), (coords.field_71574_a + 2), (coords.field_71572_b + 2), (coords.field_71573_c + 2));
/*  386 */           for (EntityLivingBase entity : entitiesNearEnergyPath) {
/*      */             
/*  388 */             if (entity.field_70121_D.func_72326_a(box)) {
/*      */               
/*  390 */               int shockEnergy = energyInjected2 - shockAbsorbing;
/*  391 */               if (shockEnergy < 0) {
/*      */                 continue;
/*      */               }
/*      */               
/*  395 */               if (shocks.containsKey(entity)) {
/*      */                 
/*  397 */                 int oldValue = ((Integer)shocks.get(entity)).intValue();
/*  398 */                 if (shockEnergy > oldValue)
/*      */                 {
/*  400 */                   shocks.put(entity, Integer.valueOf(shockEnergy));
/*      */                 }
/*      */                 continue;
/*      */               } 
/*  404 */               shocks.put(entity, Integer.valueOf(shockEnergy));
/*      */             } 
/*      */           } 
/*      */         } 
/*  408 */         if (shocks.size() > 0)
/*      */         {
/*  410 */           for (Map.Entry<EntityLivingBase, Integer> shockEntry : shocks.entrySet()) {
/*      */             
/*  412 */             EntityLivingBase base = shockEntry.getKey();
/*  413 */             if (this.entityLivingToShockEnergyMap.containsKey(base)) {
/*      */               
/*  415 */               this.entityLivingToShockEnergyMap.put(base, Integer.valueOf(((Integer)shockEntry.getValue()).intValue() + ((Integer)this.entityLivingToShockEnergyMap.get(base)).intValue()));
/*      */               continue;
/*      */             } 
/*  418 */             this.entityLivingToShockEnergyMap.put(base, shockEntry.getValue());
/*      */           } 
/*      */         }
/*      */         
/*  422 */         if (energyInjected2 >= energyPath3.minInsulationBreakdownEnergy)
/*      */         {
/*  424 */           for (IEnergyConductor energyConductor2 : energyPath3.conductors) {
/*      */             
/*  426 */             if (energyInjected2 >= energyConductor2.getInsulationBreakdownEnergy()) {
/*      */               
/*  428 */               energyConductor2.removeInsulation();
/*  429 */               if (energyConductor2.getInsulationEnergyAbsorption() >= energyPath3.minInsulationEnergyAbsorption) {
/*      */                 continue;
/*      */               }
/*      */               
/*  433 */               energyPath3.minInsulationEnergyAbsorption = (int)energyConductor2.getInsulationEnergyAbsorption();
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*  438 */       if (energyInjected2 >= energyPath3.minConductorBreakdownEnergy && !IC2.enableIC2EasyMode)
/*      */       {
/*  440 */         for (IEnergyConductor energyConductor3 : energyPath3.conductors) {
/*      */           
/*  442 */           if (energyInjected2 >= energyConductor3.getConductorBreakdownEnergy())
/*      */           {
/*  444 */             energyConductor3.removeConductor();
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  449 */     return amount;
/*      */   }
/*      */ 
/*      */   
/*      */   private FilteredList<EnergyPath> discover(TileEntity emitter, boolean reverse, int lossLimit) {
/*  454 */     Map<TileEntity, EnergyBlockLink> reachedTileEntities = new HashMap<>();
/*  455 */     LinkedList<TileEntity> tileEntitiesToCheck = new LinkedList<>();
/*  456 */     tileEntitiesToCheck.add(emitter);
/*  457 */     int totalSinks = 0;
/*  458 */     while (!tileEntitiesToCheck.isEmpty()) {
/*      */       
/*  460 */       TileEntity currentTileEntity = tileEntitiesToCheck.remove();
/*  461 */       if (!currentTileEntity.func_145837_r()) {
/*      */         
/*  463 */         double currentLoss = 0.0D;
/*  464 */         if (this.registeredTiles.get(coords(currentTileEntity)) != null && this.registeredTiles.get(coords(currentTileEntity)) != emitter && reachedTileEntities.containsKey(currentTileEntity))
/*      */         {
/*  466 */           currentLoss = ((EnergyBlockLink)reachedTileEntities.get(currentTileEntity)).loss;
/*      */         }
/*  468 */         List<EnergyTarget> validReceivers = getValidReceivers(currentTileEntity, reverse);
/*  469 */         for (EnergyTarget validReceiver : validReceivers) {
/*      */           
/*  471 */           if (validReceiver.tileEntity != emitter) {
/*      */             
/*  473 */             double additionalLoss = 0.0D;
/*  474 */             if (validReceiver.tileEntity instanceof IEnergyConductor) {
/*      */               
/*  476 */               additionalLoss = ((IEnergyConductor)validReceiver.tileEntity).getConductionLoss();
/*  477 */               if (additionalLoss < 1.0E-4D)
/*      */               {
/*  479 */                 additionalLoss = 1.0E-4D;
/*      */               }
/*  481 */               if (currentLoss + additionalLoss >= lossLimit) {
/*      */                 continue;
/*      */               }
/*      */             } 
/*      */             
/*  486 */             if (reachedTileEntities.containsKey(validReceiver.tileEntity) && ((EnergyBlockLink)reachedTileEntities.get(validReceiver.tileEntity)).loss <= currentLoss + additionalLoss) {
/*      */               continue;
/*      */             }
/*      */             
/*  490 */             reachedTileEntities.put(validReceiver.tileEntity, new EnergyBlockLink(validReceiver.direction, currentLoss + additionalLoss));
/*  491 */             if (validReceiver.tileEntity instanceof IEnergySink)
/*      */             {
/*  493 */               totalSinks++;
/*      */             }
/*  495 */             if (!(validReceiver.tileEntity instanceof IEnergyConductor)) {
/*      */               continue;
/*      */             }
/*      */             
/*  499 */             tileEntitiesToCheck.remove(validReceiver.tileEntity);
/*  500 */             tileEntitiesToCheck.add(validReceiver.tileEntity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  505 */     if (totalSinks < 10)
/*      */     {
/*  507 */       totalSinks = 10;
/*      */     }
/*  509 */     FilteredList<EnergyPath> energyPaths = new FilteredList(totalSinks);
/*  510 */     for (Map.Entry<TileEntity, EnergyBlockLink> entry : reachedTileEntities.entrySet()) {
/*      */       
/*  512 */       TileEntity tileEntity = entry.getKey();
/*  513 */       if ((!reverse && tileEntity instanceof IEnergySink) || (reverse && tileEntity instanceof IEnergySource)) {
/*      */         
/*  515 */         EnergyBlockLink energyBlockLink = entry.getValue();
/*  516 */         EnergyPath energyPath = new EnergyPath();
/*  517 */         if (energyBlockLink.loss > 0.1D) {
/*      */           
/*  519 */           energyPath.loss = energyBlockLink.loss;
/*      */         }
/*      */         else {
/*      */           
/*  523 */           energyPath.loss = 0.1D;
/*      */         } 
/*  525 */         energyPath.target = tileEntity;
/*  526 */         energyPath.targetDirection = energyBlockLink.direction;
/*  527 */         if (!reverse && emitter instanceof IEnergySource)
/*      */         {
/*      */           while (true) {
/*      */             
/*  531 */             tileEntity = EnergyNet.instance.getNeighbor(tileEntity, energyBlockLink.direction.toForgeDirection());
/*  532 */             if (tileEntity == emitter) {
/*      */               break;
/*      */             }
/*      */             
/*  536 */             if (!(tileEntity instanceof IEnergyConductor)) {
/*      */               break;
/*      */             }
/*      */             
/*  540 */             IEnergyConductor energyConductor = (IEnergyConductor)tileEntity;
/*  541 */             if (tileEntity.field_145851_c < energyPath.minX)
/*      */             {
/*  543 */               energyPath.minX = tileEntity.field_145851_c;
/*      */             }
/*  545 */             if (tileEntity.field_145848_d < energyPath.minY)
/*      */             {
/*  547 */               energyPath.minY = tileEntity.field_145848_d;
/*      */             }
/*  549 */             if (tileEntity.field_145849_e < energyPath.minZ)
/*      */             {
/*  551 */               energyPath.minZ = tileEntity.field_145849_e;
/*      */             }
/*  553 */             if (tileEntity.field_145851_c > energyPath.maxX)
/*      */             {
/*  555 */               energyPath.maxX = tileEntity.field_145851_c;
/*      */             }
/*  557 */             if (tileEntity.field_145848_d > energyPath.maxY)
/*      */             {
/*  559 */               energyPath.maxY = tileEntity.field_145848_d;
/*      */             }
/*  561 */             if (tileEntity.field_145849_e > energyPath.maxZ)
/*      */             {
/*  563 */               energyPath.maxZ = tileEntity.field_145849_e;
/*      */             }
/*  565 */             energyPath.conductors.add(energyConductor);
/*  566 */             if (energyConductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
/*      */             {
/*  568 */               energyPath.minInsulationEnergyAbsorption = (int)energyConductor.getInsulationEnergyAbsorption();
/*      */             }
/*  570 */             if (energyConductor.getInsulationBreakdownEnergy() < energyPath.minInsulationBreakdownEnergy)
/*      */             {
/*  572 */               energyPath.minInsulationBreakdownEnergy = (int)energyConductor.getInsulationBreakdownEnergy();
/*      */             }
/*  574 */             if (energyConductor.getConductorBreakdownEnergy() < energyPath.minConductorBreakdownEnergy)
/*      */             {
/*  576 */               energyPath.minConductorBreakdownEnergy = (int)energyConductor.getConductorBreakdownEnergy();
/*      */             }
/*  578 */             energyBlockLink = reachedTileEntities.get(tileEntity);
/*  579 */             if (energyBlockLink != null) {
/*      */               continue;
/*      */             }
/*      */             
/*  583 */             IC2.platform.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: " + emitter + " (" + emitter.field_145851_c + "," + emitter.field_145848_d + "," + emitter.field_145849_e + ")\nC: " + tileEntity + " (" + tileEntity.field_145851_c + "," + tileEntity.field_145848_d + "," + tileEntity.field_145849_e + ")\nR: " + energyPath.target + " (" + energyPath.target.field_145851_c + "," + energyPath.target.field_145848_d + "," + energyPath.target.field_145849_e + ")");
/*      */           } 
/*      */         }
/*  586 */         energyPaths.add(energyPath);
/*      */       } 
/*      */     } 
/*  589 */     return energyPaths;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<TileEntity> discoverTargets(TileEntity emitter, boolean reverse, int lossLimit) {
/*  594 */     FilteredList<EnergyPath> filteredList = discover(emitter, reverse, lossLimit);
/*  595 */     List<TileEntity> targets = new ArrayList<>();
/*  596 */     for (EnergyPath path : filteredList)
/*      */     {
/*  598 */       targets.add(path.target);
/*      */     }
/*  600 */     return targets;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<EnergyTarget> getValidReceivers(TileEntity emitter, boolean reverse) {
/*  605 */     List<EnergyTarget> validReceivers = new ArrayList<>();
/*  606 */     for (Direction direction : directions) {
/*      */       
/*  608 */       if (emitter instanceof IMetaDelegate) {
/*      */         
/*  610 */         IMetaDelegate meta = (IMetaDelegate)emitter;
/*  611 */         List<TileEntity> targets = meta.getSubTiles();
/*  612 */         for (TileEntity tile : targets) {
/*      */           
/*  614 */           TileEntity target = EnergyNet.instance.getNeighbor(tile, direction.toForgeDirection());
/*  615 */           if (target == emitter) {
/*      */             continue;
/*      */           }
/*      */           
/*  619 */           if (target instanceof IEnergyTile && this.registeredTiles.containsKey(coords(target)))
/*      */           {
/*  621 */             Direction inverseDirection = direction.getInverse();
/*  622 */             if (reverse) {
/*      */               
/*  624 */               if (emitter instanceof IEnergyAcceptor && target instanceof IEnergyEmitter) {
/*      */                 
/*  626 */                 IEnergyEmitter sender = (IEnergyEmitter)target;
/*  627 */                 IEnergyAcceptor receiver = (IEnergyAcceptor)emitter;
/*  628 */                 if (sender.emitsEnergyTo(emitter, inverseDirection.toForgeDirection()) && receiver.acceptsEnergyFrom(target, direction.toForgeDirection()))
/*      */                 {
/*  630 */                   validReceivers.add(new EnergyTarget(target, inverseDirection));
/*      */                 }
/*      */               } 
/*      */               
/*      */               continue;
/*      */             } 
/*  636 */             if (emitter instanceof IEnergyEmitter && target instanceof IEnergyAcceptor)
/*      */             {
/*  638 */               IEnergyEmitter sender = (IEnergyEmitter)emitter;
/*  639 */               IEnergyAcceptor receiver = (IEnergyAcceptor)target;
/*  640 */               if (sender.emitsEnergyTo(target, direction.toForgeDirection()) && receiver.acceptsEnergyFrom(emitter, inverseDirection.toForgeDirection()))
/*      */               {
/*  642 */                 validReceivers.add(new EnergyTarget(target, inverseDirection));
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  651 */         TileEntity target = EnergyNet.instance.getNeighbor(emitter, direction.toForgeDirection());
/*  652 */         if (target instanceof IEnergyTile && this.registeredTiles.containsKey(coords(target))) {
/*      */           
/*  654 */           Direction inverseDirection = direction.getInverse();
/*  655 */           if (reverse) {
/*      */             
/*  657 */             if (emitter instanceof IEnergyAcceptor && target instanceof IEnergyEmitter)
/*      */             {
/*  659 */               IEnergyEmitter sender = (IEnergyEmitter)target;
/*  660 */               IEnergyAcceptor receiver = (IEnergyAcceptor)emitter;
/*  661 */               if (sender.emitsEnergyTo(emitter, inverseDirection.toForgeDirection()) && receiver.acceptsEnergyFrom(target, direction.toForgeDirection()))
/*      */               {
/*  663 */                 validReceivers.add(new EnergyTarget(target, inverseDirection));
/*      */               
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*  669 */           else if (emitter instanceof IEnergyEmitter && target instanceof IEnergyAcceptor) {
/*      */             
/*  671 */             IEnergyEmitter sender = (IEnergyEmitter)emitter;
/*  672 */             IEnergyAcceptor receiver = (IEnergyAcceptor)target;
/*  673 */             if (sender.emitsEnergyTo(target, direction.toForgeDirection()) && receiver.acceptsEnergyFrom(emitter, inverseDirection.toForgeDirection()))
/*      */             {
/*  675 */               validReceivers.add(new EnergyTarget(target, inverseDirection));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  683 */     return validReceivers;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean conductorToWeak(EnergyPath path, int energyToSend) {
/*  688 */     if (path.minConductorBreakdownEnergy > energyToSend)
/*      */     {
/*  690 */       return false;
/*      */     }
/*  692 */     boolean flag = false;
/*  693 */     for (IEnergyConductor cond : path.conductors) {
/*      */       
/*  695 */       if (cond.getConductorBreakdownEnergy() <= energyToSend) {
/*      */         
/*  697 */         flag = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  701 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<IEnergySource> discoverFirstPathOrSources(TileEntity par1) {
/*  706 */     Set<TileEntity> reached = new HashSet<>();
/*  707 */     List<IEnergySource> result = new ArrayList<>();
/*  708 */     LinkedList<TileEntity> workList = new LinkedList<>();
/*  709 */     workList.add(par1);
/*  710 */     while (workList.size() > 0) {
/*      */       
/*  712 */       TileEntity tile = workList.remove();
/*  713 */       if (!tile.func_145837_r()) {
/*      */         
/*  715 */         List<EnergyTarget> targets = getValidReceivers(tile, true);
/*  716 */         for (int i = 0; i < targets.size(); i++) {
/*      */           
/*  718 */           TileEntity target = ((EnergyTarget)targets.get(i)).tileEntity;
/*  719 */           if (target != par1)
/*      */           {
/*      */ 
/*      */             
/*  723 */             if (!reached.contains(target)) {
/*      */ 
/*      */ 
/*      */               
/*  727 */               reached.add(target);
/*  728 */               if (target instanceof IEnergySource)
/*      */               {
/*  730 */                 result.add((IEnergySource)target);
/*      */               }
/*  732 */               if (target instanceof IEnergyConductor)
/*      */               {
/*  734 */                 workList.add(target); } 
/*      */             }  } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  739 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ChunkCoordinates coords(TileEntity par1) {
/*  744 */     if (par1 == null)
/*      */     {
/*  746 */       return null;
/*      */     }
/*  748 */     return new ChunkCoordinates(par1.field_145851_c, par1.field_145848_d, par1.field_145849_e);
/*      */   }
/*      */ 
/*      */   
/*      */   void addPacket(EnergyPath link, int energy) {
/*  753 */     if (link.backupEnergyPackets.size() > 0)
/*      */     {
/*  755 */       link.backupEnergyPackets = new HashMap<>();
/*      */     }
/*  757 */     EnergyPacket totalPackets = link.totalEnergyPackets.get(Integer.valueOf(energy));
/*  758 */     if (totalPackets == null) {
/*      */       
/*  760 */       totalPackets = new EnergyPacket();
/*  761 */       link.totalEnergyPackets.put(Integer.valueOf(energy), totalPackets);
/*      */     } 
/*  763 */     totalPackets.add();
/*  764 */     EnergyPacket mesurePackets = link.mesuredEnergyPackets.get(Integer.valueOf(energy));
/*  765 */     if (mesurePackets == null) {
/*      */       
/*  767 */       mesurePackets = new EnergyPacket();
/*  768 */       link.totalEnergyPackets.put(Integer.valueOf(energy), mesurePackets);
/*      */     } 
/*  770 */     mesurePackets.add();
/*      */   }
/*      */ 
/*      */   
/*      */   private void addPackets(Map<Integer, EnergyPacket> input, Map<Integer, EnergyPacket> ref) {
/*  775 */     for (Map.Entry<Integer, EnergyPacket> entry : ref.entrySet()) {
/*      */       
/*  777 */       int key = ((Integer)entry.getKey()).intValue();
/*  778 */       EnergyPacket result = input.get(Integer.valueOf(key));
/*  779 */       if (result == null) {
/*      */         
/*  781 */         result = new EnergyPacket();
/*  782 */         input.put(Integer.valueOf(key), result);
/*      */       } 
/*  784 */       result.combine(entry.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTickStart() {
/*  790 */     for (Map.Entry<EntityLivingBase, Integer> entry : this.entityLivingToShockEnergyMap.entrySet()) {
/*      */       
/*  792 */       EntityLivingBase target = entry.getKey();
/*  793 */       int damage = (((Integer)entry.getValue()).intValue() + 63) / 64;
/*  794 */       if (target.func_70089_S() && damage > 0)
/*      */       {
/*  796 */         target.func_70097_a((DamageSource)IC2DamageSource.electricity, damage);
/*      */       }
/*      */     } 
/*  799 */     this.entityLivingToShockEnergyMap.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTickEnd() {
/*  804 */     if (this.unloading.hasWork()) {
/*      */       
/*  806 */       this.energySourceToEnergyPathMap.clearSources(this.unloading.getWork());
/*  807 */       List<ChunkCoordinates> coords = this.unloading.getWorkEnd();
/*  808 */       for (ChunkCoordinates coord : coords) {
/*      */         
/*  810 */         this.registeredTiles.remove(coord);
/*  811 */         update(coord.field_71574_a, coord.field_71572_b, coord.field_71573_c);
/*      */       } 
/*  813 */       this.unloading.clear();
/*      */     } 
/*      */     
/*  816 */     if (this.waitingList.hasWork()) {
/*      */       
/*  818 */       List<TileEntity> tiles = this.waitingList.getPathTiles();
/*  819 */       for (TileEntity tile : tiles) {
/*      */         
/*  821 */         List<IEnergySource> sources = discoverFirstPathOrSources(tile);
/*  822 */         if (sources.size() > 0)
/*      */         {
/*  824 */           this.energySourceToEnergyPathMap.removeAll(sources);
/*      */         }
/*      */       } 
/*  827 */       this.waitingList.clear();
/*      */     } 
/*      */     
/*  830 */     Iterator<Map.Entry<ChunkCoordinates, IEnergySource>> iter = (new LinkedHashMap<>(this.sources)).entrySet().iterator();
/*  831 */     for (int z = 0; iter.hasNext(); z++) {
/*      */       
/*  833 */       Map.Entry<ChunkCoordinates, IEnergySource> entry = iter.next();
/*  834 */       if (entry != null && this.sources.containsKey(entry.getKey())) {
/*      */ 
/*      */ 
/*      */         
/*  838 */         IEnergySource source = entry.getValue();
/*  839 */         if (source != null) {
/*      */ 
/*      */ 
/*      */           
/*  843 */           int offer = (int)source.getOfferedEnergy();
/*  844 */           if (offer >= 1)
/*      */           {
/*      */ 
/*      */             
/*  848 */             if (source instanceof IMultiEnergySource && ((IMultiEnergySource)source).sendMultibleEnergyPackets()) {
/*      */               
/*  850 */               IMultiEnergySource multi = (IMultiEnergySource)source;
/*  851 */               int counts = multi.getMultibleEnergyPacketAmount();
/*  852 */               for (int i = 0; i < counts; ) {
/*      */                 
/*  854 */                 offer = (int)source.getOfferedEnergy();
/*  855 */                 if (offer < 1) {
/*      */                   break;
/*      */                 }
/*      */                 
/*  859 */                 int removed = offer - emitEnergyFrom(entry.getKey(), source, offer);
/*  860 */                 if (removed > 0) {
/*      */                   
/*  862 */                   source.drawEnergy(removed);
/*      */ 
/*      */ 
/*      */                   
/*      */                   i++;
/*      */                 } 
/*      */               } 
/*      */             } else {
/*  870 */               int removed = offer - emitEnergyFrom(entry.getKey(), source, offer);
/*  871 */               if (removed > 0)
/*      */               {
/*  873 */                 source.drawEnergy(removed); } 
/*      */             }  } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void explodeTiles(IEnergySink sink) {
/*  881 */     removeTile((TileEntity)sink);
/*  882 */     if (sink instanceof IMetaDelegate) {
/*      */       
/*  884 */       IMetaDelegate meta = (IMetaDelegate)sink;
/*  885 */       for (TileEntity tile : meta.getSubTiles())
/*      */       {
/*  887 */         explodeMachineAt(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  892 */       explodeMachineAt(((TileEntity)sink).field_145851_c, ((TileEntity)sink).field_145848_d, ((TileEntity)sink).field_145849_e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(int x, int y, int z) {
/*  898 */     ChunkCoordinates coords = new ChunkCoordinates(x, y, z);
/*  899 */     if (this.registeredTiles.containsKey(coords))
/*      */     {
/*  901 */       return (TileEntity)this.registeredTiles.get(coords);
/*      */     }
/*  903 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public NodeStats getNodeStats(TileEntity tile) {
/*  908 */     double emitted = getTotalEnergyEmitted(tile);
/*  909 */     double received = getTotalEnergySunken(tile);
/*  910 */     double volt = Math.max(EnergyNet.instance.getTierFromPower(emitted), EnergyNet.instance.getTierFromPower(received));
/*  911 */     return new NodeStats(received, emitted, volt);
/*      */   }
/*      */ 
/*      */   
/*      */   void explodeMachineAt(int x, int y, int z) {
/*  916 */     this.world.func_147468_f(x, y, z);
/*  917 */     ExplosionIC2 explosion = new ExplosionIC2(this.world, null, 0.5D + x, 0.5D + y, 0.5D + z, 2.5F, 0.75F, 0.75F);
/*  918 */     explosion.doExplosion();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void update(int x, int y, int z) {
/*  924 */     for (ForgeDirection dir : ForgeDirection.values()) {
/*      */       
/*  926 */       if (this.world.func_72899_e(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
/*      */       {
/*  928 */         this.world.func_147460_e(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, Blocks.field_150350_a);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUnload() {
/*  935 */     this.energySourceToEnergyPathMap.clear();
/*  936 */     this.registeredTiles.clear();
/*  937 */     this.sources.clear();
/*  938 */     this.entityLivingToShockEnergyMap.clear();
/*  939 */     this.unloading.clear();
/*  940 */     this.waitingList.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   static class EnergyTarget
/*      */   {
/*      */     TileEntity tileEntity;
/*      */     
/*      */     Direction direction;
/*      */     
/*      */     EnergyTarget(TileEntity tileEntity, Direction direction) {
/*  951 */       this.tileEntity = tileEntity;
/*  952 */       this.direction = direction;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class EnergyBlockLink
/*      */   {
/*      */     Direction direction;
/*      */     
/*      */     double loss;
/*      */     
/*      */     EnergyBlockLink(Direction direction, double loss) {
/*  964 */       this.direction = direction;
/*  965 */       this.loss = loss;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class EnergyPath
/*      */   {
/*  992 */     TileEntity target = null;
/*  993 */     List<IEnergyConductor> conductors = (List<IEnergyConductor>)new FilteredList(); Direction targetDirection;
/*  994 */     int minX = Integer.MAX_VALUE;
/*  995 */     int minY = Integer.MAX_VALUE;
/*  996 */     int minZ = Integer.MAX_VALUE;
/*  997 */     int maxX = Integer.MIN_VALUE;
/*  998 */     int maxY = Integer.MIN_VALUE;
/*  999 */     int maxZ = Integer.MIN_VALUE;
/* 1000 */     double loss = 0.0D;
/* 1001 */     int minInsulationEnergyAbsorption = Integer.MAX_VALUE;
/* 1002 */     int minInsulationBreakdownEnergy = Integer.MAX_VALUE;
/* 1003 */     int minConductorBreakdownEnergy = Integer.MAX_VALUE;
/* 1004 */     long totalEnergyConducted = 0L;
/* 1005 */     HashMap<Integer, EnergyNetLocal.EnergyPacket> totalEnergyPackets = new HashMap<>();
/* 1006 */     HashMap<Integer, EnergyNetLocal.EnergyPacket> mesuredEnergyPackets = new HashMap<>();
/* 1007 */     HashMap<Integer, EnergyNetLocal.EnergyPacket> backupEnergyPackets = new HashMap<>();
/*      */   }
/*      */ 
/*      */   
/*      */   static class EnergyPathMap
/*      */   {
/* 1013 */     Map<IEnergySource, FilteredList<EnergyNetLocal.EnergyPath>> senderPath = new HashMap<>();
/* 1014 */     Map<EnergyNetLocal.EnergyPath, IEnergySource> pathToSender = new HashMap<>();
/*      */ 
/*      */     
/*      */     public void put(IEnergySource par1, FilteredList<EnergyNetLocal.EnergyPath> par2) {
/* 1018 */       this.senderPath.put(par1, par2);
/* 1019 */       for (int i = 0; i < par2.size(); i++)
/*      */       {
/* 1021 */         this.pathToSender.put(par2.get(i), par1);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object par1) {
/* 1027 */       return this.senderPath.containsKey(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public List<EnergyNetLocal.EnergyPath> get(Object par1) {
/* 1032 */       return (List<EnergyNetLocal.EnergyPath>)this.senderPath.get(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove(Object par1) {
/* 1037 */       List<EnergyNetLocal.EnergyPath> paths = (List<EnergyNetLocal.EnergyPath>)this.senderPath.remove(par1);
/* 1038 */       if (paths != null)
/*      */       {
/* 1040 */         for (int i = 0; i < paths.size(); i++)
/*      */         {
/* 1042 */           this.pathToSender.remove(paths.get(i));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeAll(List<IEnergySource> par1) {
/* 1049 */       for (int i = 0; i < par1.size(); i++)
/*      */       {
/* 1051 */         remove(par1.get(i));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearSources(List<EnergyNetLocal.BoundingBox> boxes) {
/* 1057 */       FilteredList filteredList = new FilteredList();
/* 1058 */       for (Map.Entry<EnergyNetLocal.EnergyPath, IEnergySource> entry : this.pathToSender.entrySet()) {
/*      */         
/* 1060 */         if (filteredList.contains(entry.getValue())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1064 */         EnergyNetLocal.EnergyPath path = entry.getKey();
/* 1065 */         EnergyNetLocal.BoundingBox pathBox = new EnergyNetLocal.BoundingBox(path, entry.getValue());
/* 1066 */         for (EnergyNetLocal.BoundingBox box : boxes) {
/*      */           
/* 1068 */           if (pathBox.intersectsWith(box)) {
/*      */             
/* 1070 */             if (box.sink.contains(path.target)) {
/*      */               
/* 1072 */               filteredList.add(entry.getValue());
/*      */               break;
/*      */             } 
/* 1075 */             boolean found = false;
/* 1076 */             for (IEnergyConductor con : box.conductors) {
/*      */               
/* 1078 */               if (path.conductors.contains(con)) {
/*      */                 
/* 1080 */                 found = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1084 */             if (found)
/*      */             {
/* 1086 */               filteredList.add(entry.getValue());
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1092 */       removeAll((List<IEnergySource>)filteredList);
/*      */     }
/*      */ 
/*      */     
/*      */     public List<EnergyNetLocal.EnergyPath> getPaths(IEnergyAcceptor par1) {
/* 1097 */       FilteredList<EnergyNetLocal.EnergyPath> filteredList = new FilteredList();
/* 1098 */       for (IEnergySource source : getSources(par1)) {
/*      */         
/* 1100 */         if (containsKey(source))
/*      */         {
/* 1102 */           filteredList.addAll(get(source));
/*      */         }
/*      */       } 
/* 1105 */       return (List<EnergyNetLocal.EnergyPath>)filteredList;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<IEnergySource> getSources(IEnergyAcceptor par1) {
/* 1110 */       FilteredList filteredList = new FilteredList();
/* 1111 */       for (Map.Entry<EnergyNetLocal.EnergyPath, IEnergySource> entry : this.pathToSender.entrySet()) {
/*      */         
/* 1113 */         if (filteredList.contains(entry.getValue())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1117 */         EnergyNetLocal.EnergyPath path = entry.getKey();
/* 1118 */         EnergyNetLocal.BoundingBox box = new EnergyNetLocal.BoundingBox(path, entry.getValue());
/* 1119 */         if (!box.intersectsWith((TileEntity)par1)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1123 */         if ((par1 instanceof IEnergyConductor && path.conductors.contains(par1)) || (par1 instanceof IEnergySink && path.target == par1))
/*      */         {
/* 1125 */           filteredList.add(entry.getValue());
/*      */         }
/*      */       } 
/*      */       
/* 1129 */       return (List<IEnergySource>)filteredList;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1135 */       this.senderPath.clear();
/* 1136 */       this.pathToSender.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public List<EnergyNetLocal.BoundingBox> getBoxes() {
/* 1141 */       Set<EnergyNetLocal.BoundingBox> boxes = new HashSet<>();
/* 1142 */       for (Map.Entry<EnergyNetLocal.EnergyPath, IEnergySource> path : this.pathToSender.entrySet())
/*      */       {
/* 1144 */         boxes.add(new EnergyNetLocal.BoundingBox(path.getKey(), path.getValue()));
/*      */       }
/* 1146 */       return new ArrayList<>(boxes);
/*      */     }
/*      */   }
/*      */   
/*      */   class WaitingList
/*      */   {
/* 1152 */     List<EnergyNetLocal.PathLogic> paths = new ArrayList<>();
/*      */ 
/*      */     
/*      */     public void onTileEntityAdded(List<EnergyNetLocal.EnergyTarget> around, TileEntity tile) {
/* 1156 */       if (around.isEmpty() || this.paths.isEmpty()) {
/*      */         
/* 1158 */         createNewPath(tile);
/*      */         return;
/*      */       } 
/* 1161 */       boolean found = false;
/* 1162 */       List<EnergyNetLocal.PathLogic> logics = new ArrayList<>();
/* 1163 */       for (int i = 0; i < this.paths.size(); i++) {
/*      */         
/* 1165 */         EnergyNetLocal.PathLogic logic = this.paths.get(i);
/* 1166 */         if (logic.contains(tile)) {
/*      */           
/* 1168 */           found = true;
/* 1169 */           if (tile instanceof IEnergyConductor)
/*      */           {
/* 1171 */             logics.add(logic);
/*      */           }
/*      */         } else {
/*      */           
/* 1175 */           for (EnergyNetLocal.EnergyTarget target : around) {
/*      */             
/* 1177 */             if (logic.contains(tile)) {
/*      */               
/* 1179 */               found = true;
/* 1180 */               if (tile instanceof IEnergyConductor)
/*      */               {
/* 1182 */                 logics.add(logic);
/*      */               }
/*      */               break;
/*      */             } 
/* 1186 */             if (logic.contains(target.tileEntity)) {
/*      */               
/* 1188 */               found = true;
/* 1189 */               logic.add(tile);
/* 1190 */               if (target.tileEntity instanceof IEnergyConductor)
/*      */               {
/* 1192 */                 logics.add(logic); } 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1198 */       if (logics.size() > 1 && tile instanceof IEnergyConductor) {
/*      */         
/* 1200 */         EnergyNetLocal.PathLogic newLogic = new EnergyNetLocal.PathLogic();
/* 1201 */         for (EnergyNetLocal.PathLogic logic : logics) {
/*      */           
/* 1203 */           this.paths.remove(logic);
/* 1204 */           for (TileEntity toMove : logic.tiles) {
/*      */             
/* 1206 */             if (!newLogic.contains(toMove))
/*      */             {
/* 1208 */               newLogic.add(toMove);
/*      */             }
/*      */           } 
/* 1211 */           logic.clear();
/*      */         } 
/* 1213 */         this.paths.add(newLogic);
/*      */       } 
/*      */       
/* 1216 */       if (!found)
/*      */       {
/* 1218 */         createNewPath(tile);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTileEntityRemoved(TileEntity par1) {
/* 1224 */       if (this.paths.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 1228 */       List<TileEntity> toRecalculate = new ArrayList<>(); int i;
/* 1229 */       for (i = 0; i < this.paths.size(); i++) {
/*      */         
/* 1231 */         EnergyNetLocal.PathLogic logic = this.paths.get(i);
/* 1232 */         if (logic.contains(par1)) {
/*      */           
/* 1234 */           logic.remove(par1);
/* 1235 */           toRecalculate.addAll(logic.tiles);
/* 1236 */           this.paths.remove(i--);
/*      */         } 
/*      */       } 
/* 1239 */       for (i = 0; i < toRecalculate.size(); i++) {
/*      */         
/* 1241 */         TileEntity tile = toRecalculate.get(i);
/* 1242 */         onTileEntityAdded(EnergyNetLocal.this.getValidReceivers(tile, true), tile);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void createNewPath(TileEntity par1) {
/* 1248 */       EnergyNetLocal.PathLogic logic = new EnergyNetLocal.PathLogic();
/* 1249 */       logic.add(par1);
/* 1250 */       this.paths.add(logic);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1255 */       if (this.paths.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 1259 */       for (int i = 0; i < this.paths.size(); i++)
/*      */       {
/* 1261 */         ((EnergyNetLocal.PathLogic)this.paths.get(i)).clear();
/*      */       }
/* 1263 */       this.paths.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasWork() {
/* 1268 */       return (this.paths.size() > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public List<TileEntity> getPathTiles() {
/* 1273 */       List<TileEntity> tiles = new ArrayList<>();
/* 1274 */       for (int i = 0; i < this.paths.size(); i++) {
/*      */         
/* 1276 */         TileEntity tile = ((EnergyNetLocal.PathLogic)this.paths.get(i)).getRepresentingTile();
/* 1277 */         if (tile != null)
/*      */         {
/* 1279 */           tiles.add(tile);
/*      */         }
/*      */       } 
/* 1282 */       return tiles;
/*      */     }
/*      */   }
/*      */   
/*      */   static class PathLogic
/*      */   {
/* 1288 */     List<TileEntity> tiles = (List<TileEntity>)new FilteredList();
/*      */ 
/*      */     
/*      */     public boolean contains(TileEntity par1) {
/* 1292 */       return this.tiles.contains(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(TileEntity par1) {
/* 1297 */       this.tiles.add(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove(TileEntity par1) {
/* 1302 */       this.tiles.remove(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1307 */       this.tiles.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public TileEntity getRepresentingTile() {
/* 1312 */       if (this.tiles.isEmpty())
/*      */       {
/* 1314 */         return null;
/*      */       }
/* 1316 */       return this.tiles.get(0);
/*      */     }
/*      */   }
/*      */   
/*      */   class UnloadingList
/*      */   {
/* 1322 */     List<EnergyNetLocal.UnloadLogic> logics = new ArrayList<>();
/*      */ 
/*      */     
/*      */     public void onLoaded(ChunkCoordinates coords) {
/* 1326 */       if (this.logics.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 1330 */       List<ChunkCoordinates> toRecalculate = new ArrayList<>(); int i;
/* 1331 */       for (i = 0; i < this.logics.size(); i++) {
/*      */         
/* 1333 */         EnergyNetLocal.UnloadLogic logic = this.logics.get(i);
/* 1334 */         if (logic.contains(coords)) {
/*      */           
/* 1336 */           logic.remove(coords);
/* 1337 */           toRecalculate.addAll(logic.getAll());
/* 1338 */           this.logics.remove(i--);
/*      */         } 
/*      */       } 
/* 1341 */       for (i = 0; i < toRecalculate.size(); i++) {
/*      */         
/* 1343 */         ChunkCoordinates coord = toRecalculate.get(i);
/* 1344 */         TileEntity tile = EnergyNetLocal.this.getTileEntity(coord.field_71574_a, coord.field_71572_b, coord.field_71573_c);
/* 1345 */         if (tile != null)
/*      */         {
/* 1347 */           onUnloaded(EnergyNetLocal.this.getValidReceivers(tile, true), coord, tile);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onUnloaded(List<EnergyNetLocal.EnergyTarget> around, ChunkCoordinates coords, TileEntity tile) {
/* 1354 */       if (around.isEmpty() || this.logics.isEmpty()) {
/*      */         
/* 1356 */         createNewPath(coords);
/*      */         return;
/*      */       } 
/* 1359 */       boolean found = false;
/* 1360 */       List<EnergyNetLocal.UnloadLogic> combine = new ArrayList<>();
/* 1361 */       for (int i = 0; i < this.logics.size(); i++) {
/*      */         
/* 1363 */         EnergyNetLocal.UnloadLogic logic = this.logics.get(i);
/* 1364 */         if (logic.contains(coords)) {
/*      */           
/* 1366 */           found = true;
/* 1367 */           if (tile instanceof IEnergyConductor)
/*      */           {
/* 1369 */             combine.add(logic);
/*      */           }
/*      */         } else {
/*      */           
/* 1373 */           for (EnergyNetLocal.EnergyTarget target : around) {
/*      */             
/* 1375 */             if (logic.contains(coords)) {
/*      */               
/* 1377 */               found = true;
/* 1378 */               if (tile instanceof IEnergyConductor)
/*      */               {
/* 1380 */                 combine.add(logic);
/*      */               }
/*      */               break;
/*      */             } 
/* 1384 */             if (logic.contains(EnergyNetLocal.coords(target.tileEntity))) {
/*      */               
/* 1386 */               found = true;
/* 1387 */               logic.add(coords);
/* 1388 */               if (target.tileEntity instanceof IEnergyConductor)
/*      */               {
/* 1390 */                 combine.add(logic); } 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1396 */       if (combine.size() > 1 && tile instanceof IEnergyConductor) {
/*      */         
/* 1398 */         EnergyNetLocal.UnloadLogic newLogic = new EnergyNetLocal.UnloadLogic();
/* 1399 */         for (EnergyNetLocal.UnloadLogic logic : combine) {
/*      */           
/* 1401 */           this.logics.remove(logic);
/* 1402 */           for (ChunkCoordinates toMove : logic.getAll()) {
/*      */             
/* 1404 */             if (!newLogic.contains(toMove))
/*      */             {
/* 1406 */               newLogic.add(toMove);
/*      */             }
/*      */           } 
/* 1409 */           logic.clear();
/*      */         } 
/* 1411 */         this.logics.add(newLogic);
/*      */       } 
/*      */       
/* 1414 */       if (!found)
/*      */       {
/* 1416 */         createNewPath(coords);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void createNewPath(ChunkCoordinates coords) {
/* 1422 */       EnergyNetLocal.UnloadLogic logic = new EnergyNetLocal.UnloadLogic();
/* 1423 */       logic.add(coords);
/* 1424 */       this.logics.add(logic);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(ChunkCoordinates coords) {
/* 1429 */       if (this.logics.isEmpty())
/*      */       {
/* 1431 */         return false;
/*      */       }
/* 1433 */       for (EnergyNetLocal.UnloadLogic logic : this.logics) {
/*      */         
/* 1435 */         if (logic.contains(coords))
/*      */         {
/* 1437 */           return true;
/*      */         }
/*      */       } 
/* 1440 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1445 */       if (this.logics.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 1449 */       for (int i = 0; i < this.logics.size(); i++)
/*      */       {
/* 1451 */         ((EnergyNetLocal.UnloadLogic)this.logics.get(i)).clear();
/*      */       }
/* 1453 */       this.logics.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public List<EnergyNetLocal.BoundingBox> getWork() {
/* 1458 */       List<EnergyNetLocal.BoundingBox> boxes = new ArrayList<>(this.logics.size());
/* 1459 */       for (EnergyNetLocal.UnloadLogic logic : this.logics)
/*      */       {
/* 1461 */         boxes.add(new EnergyNetLocal.BoundingBox(logic.getAll(), EnergyNetLocal.this));
/*      */       }
/* 1463 */       return boxes;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<ChunkCoordinates> getWorkEnd() {
/* 1468 */       Set<ChunkCoordinates> coords = new HashSet<>();
/* 1469 */       for (EnergyNetLocal.UnloadLogic logic : this.logics)
/*      */       {
/* 1471 */         coords.addAll(logic.getAll());
/*      */       }
/* 1473 */       return new ArrayList<>(coords);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasWork() {
/* 1478 */       return (this.logics.size() > 0);
/*      */     }
/*      */   }
/*      */   
/*      */   class UnloadLogic
/*      */   {
/* 1484 */     List<ChunkCoordinates> coords = (List<ChunkCoordinates>)new FilteredList();
/*      */ 
/*      */     
/*      */     public boolean contains(ChunkCoordinates par1) {
/* 1488 */       return this.coords.contains(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(ChunkCoordinates par1) {
/* 1493 */       this.coords.add(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove(ChunkCoordinates par1) {
/* 1498 */       this.coords.remove(par1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1503 */       this.coords.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public List<ChunkCoordinates> getAll() {
/* 1508 */       return this.coords;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class EnergyPacket
/*      */   {
/*      */     long count;
/*      */     
/*      */     public void clear() {
/* 1518 */       this.count = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add() {
/* 1523 */       add(1L);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long amount) {
/* 1528 */       this.count += amount;
/*      */     }
/*      */ 
/*      */     
/*      */     public void combine(EnergyPacket other) {
/* 1533 */       this.count += other.count;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getAmount() {
/* 1538 */       return this.count;
/*      */     }
/*      */   }
/*      */   
/*      */   static class BoundingBox
/*      */   {
/*      */     int minX;
/*      */     int minY;
/*      */     int minZ;
/*      */     int maxX;
/*      */     int maxY;
/*      */     int maxZ;
/* 1550 */     Set<IEnergyConductor> conductors = new HashSet<>();
/* 1551 */     Set<IEnergySink> sink = new HashSet<>();
/*      */ 
/*      */     
/*      */     public BoundingBox(EnergyNetLocal.EnergyPath par1) {
/* 1555 */       this.minX = par1.minX;
/* 1556 */       this.minY = par1.minY;
/* 1557 */       this.minZ = par1.minZ;
/* 1558 */       this.maxX = par1.maxX;
/* 1559 */       this.maxY = par1.maxY;
/* 1560 */       this.maxZ = par1.maxZ;
/*      */     }
/*      */ 
/*      */     
/*      */     public BoundingBox(EnergyNetLocal.EnergyPath par1, IEnergySource par2) {
/* 1565 */       this.minX = par1.minX;
/* 1566 */       this.minY = par1.minY;
/* 1567 */       this.minZ = par1.minZ;
/* 1568 */       this.maxX = par1.maxX;
/* 1569 */       this.maxY = par1.maxY;
/* 1570 */       this.maxZ = par1.maxZ;
/* 1571 */       TileEntity tile = (TileEntity)par2;
/* 1572 */       if (tile.field_145851_c < this.minX) this.minX = tile.field_145851_c; 
/* 1573 */       if (tile.field_145848_d < this.minY) this.minY = tile.field_145848_d; 
/* 1574 */       if (tile.field_145849_e < this.minZ) this.minZ = tile.field_145849_e; 
/* 1575 */       if (tile.field_145851_c > this.maxX) this.maxX = tile.field_145851_c; 
/* 1576 */       if (tile.field_145848_d > this.maxY) this.maxY = tile.field_145848_d; 
/* 1577 */       if (tile.field_145849_e > this.maxZ) this.maxZ = tile.field_145849_e;
/*      */       
/* 1579 */       tile = par1.target;
/* 1580 */       if (tile.field_145851_c < this.minX) this.minX = tile.field_145851_c; 
/* 1581 */       if (tile.field_145848_d < this.minY) this.minY = tile.field_145848_d; 
/* 1582 */       if (tile.field_145849_e < this.minZ) this.minZ = tile.field_145849_e; 
/* 1583 */       if (tile.field_145851_c > this.maxX) this.maxX = tile.field_145851_c; 
/* 1584 */       if (tile.field_145848_d > this.maxY) this.maxY = tile.field_145848_d; 
/* 1585 */       if (tile.field_145849_e > this.maxZ) this.maxZ = tile.field_145849_e;
/*      */     
/*      */     }
/*      */     
/*      */     public BoundingBox(List<ChunkCoordinates> coords, EnergyNetLocal local) {
/* 1590 */       this.minX = Integer.MAX_VALUE;
/* 1591 */       this.minY = Integer.MAX_VALUE;
/* 1592 */       this.minZ = Integer.MAX_VALUE;
/* 1593 */       this.maxX = Integer.MIN_VALUE;
/* 1594 */       this.maxY = Integer.MIN_VALUE;
/* 1595 */       this.maxZ = Integer.MIN_VALUE;
/* 1596 */       for (ChunkCoordinates coord : coords) {
/*      */         
/* 1598 */         if (this.minX > coord.field_71574_a)
/*      */         {
/* 1600 */           this.minX = coord.field_71574_a;
/*      */         }
/* 1602 */         if (this.minY > coord.field_71572_b)
/*      */         {
/* 1604 */           this.minY = coord.field_71572_b;
/*      */         }
/* 1606 */         if (this.minZ > coord.field_71573_c)
/*      */         {
/* 1608 */           this.minZ = coord.field_71573_c;
/*      */         }
/* 1610 */         if (this.maxX < coord.field_71574_a)
/*      */         {
/* 1612 */           this.maxX = coord.field_71574_a;
/*      */         }
/* 1614 */         if (this.maxY < coord.field_71572_b)
/*      */         {
/* 1616 */           this.maxY = coord.field_71572_b;
/*      */         }
/* 1618 */         if (this.maxZ < coord.field_71573_c)
/*      */         {
/* 1620 */           this.maxZ = coord.field_71573_c;
/*      */         }
/* 1622 */         TileEntity tile = local.getTileEntity(coord.field_71574_a, coord.field_71572_b, coord.field_71573_c);
/* 1623 */         if (tile instanceof IEnergySink)
/*      */         {
/* 1625 */           this.sink.add((IEnergySink)tile);
/*      */         }
/* 1627 */         if (tile instanceof IEnergyConductor)
/*      */         {
/* 1629 */           this.conductors.add((IEnergyConductor)tile);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean intersectsWith(BoundingBox par1) {
/* 1636 */       if ((par1.maxX >= this.minX || par1.minX <= this.maxX) && (par1.maxY >= this.minY || par1.minY <= this.maxY) && (par1.maxZ >= this.minZ || par1.minZ <= this.maxZ))
/*      */       {
/* 1638 */         return true;
/*      */       }
/* 1640 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean intersectsWith(TileEntity tile) {
/* 1645 */       if ((tile.field_145851_c >= this.minX || tile.field_145851_c <= this.maxX) && (tile.field_145848_d >= this.minY || tile.field_145848_d <= this.maxY) && (tile.field_145849_e >= this.minZ || tile.field_145849_e <= this.maxZ))
/*      */       {
/* 1647 */         return true;
/*      */       }
/* 1649 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public AxisAlignedBB toAxis() {
/* 1654 */       return AxisAlignedBB.func_72330_a(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ).func_72321_a(1.0D, 1.0D, 1.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1660 */       return "Min: " + this.minX + ":" + this.minY + ":" + this.minZ + " Max: " + this.maxX + ":" + this.maxY + ":" + this.maxZ;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AxisAlignedBB> getBoxes() {
/* 1667 */     List<BoundingBox> boxes = this.energySourceToEnergyPathMap.getBoxes();
/* 1668 */     Set<AxisAlignedBB> result = new HashSet<>();
/* 1669 */     for (BoundingBox box : boxes)
/*      */     {
/* 1671 */       result.add(box.toAxis());
/*      */     }
/* 1673 */     return new ArrayList<>(result);
/*      */   }
/*      */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\energy\EnergyNetLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */