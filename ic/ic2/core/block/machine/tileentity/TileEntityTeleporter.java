/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.ServerConfigurationManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityTeleporter
/*     */   extends TileEntityBlock
/*     */   implements INetworkTileEntityEventListener
/*     */ {
/*     */   private static Direction[] directions;
/*     */   public boolean targetSet = false;
/*     */   public int targetX;
/*     */   public int targetY;
/*     */   public int targetZ;
/*     */   public int targetDimension;
/*  56 */   private AudioSource audioSource = null;
/*  57 */   private static int EventTeleport = 0;
/*     */ 
/*     */   
/*     */   public TileEntityTeleporter() {
/*  61 */     addNetworkFields(new String[] { "targetX", "targetY", "targetZ", "targetDimension" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  67 */     super.func_145839_a(nbttagcompound);
/*  68 */     this.targetSet = nbttagcompound.func_74767_n("targetSet");
/*  69 */     this.targetX = nbttagcompound.func_74762_e("targetX");
/*  70 */     this.targetY = nbttagcompound.func_74762_e("targetY");
/*  71 */     this.targetZ = nbttagcompound.func_74762_e("targetZ");
/*  72 */     this.targetDimension = nbttagcompound.func_74762_e("targetDimension");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  78 */     super.func_145841_b(nbttagcompound);
/*  79 */     nbttagcompound.func_74757_a("targetSet", this.targetSet);
/*  80 */     nbttagcompound.func_74768_a("targetX", this.targetX);
/*  81 */     nbttagcompound.func_74768_a("targetY", this.targetY);
/*  82 */     nbttagcompound.func_74768_a("targetZ", this.targetZ);
/*  83 */     nbttagcompound.func_74768_a("targetDimension", this.targetDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  94 */     super.func_145845_h();
/*  95 */     if (isSimulating())
/*     */     {
/*  97 */       if (this.targetSet && this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e)) {
/*     */         
/*  99 */         World world = getWorld();
/* 100 */         if (world == null) {
/*     */           
/* 102 */           setActive(false);
/*     */         }
/*     */         else {
/*     */           
/* 106 */           boolean prevWorldChunkLoadOverride = world.field_72987_B;
/* 107 */           world.field_72987_B = true;
/* 108 */           Chunk chunk = world.func_72863_F().func_73154_d(this.targetX >> 4, this.targetZ >> 4);
/* 109 */           world.field_72987_B = prevWorldChunkLoadOverride;
/* 110 */           if (chunk == null || chunk.func_150810_a(this.targetX & 0xF, this.targetY, this.targetZ & 0xF) != Block.func_149634_a(Ic2Items.teleporter.func_77973_b()) || chunk.func_76628_c(this.targetX & 0xF, this.targetY, this.targetZ & 0xF) != Ic2Items.teleporter.func_77960_j()) {
/*     */             
/* 112 */             setActive(this.targetSet = false);
/*     */           }
/*     */           else {
/*     */             
/* 116 */             setActive(true);
/* 117 */             List<Entity> entitiesNearby = this.field_145850_b.func_72872_a(Entity.class, AxisAlignedBB.func_72330_a((this.field_145851_c - 1), this.field_145848_d, (this.field_145849_e - 1), (this.field_145851_c + 2), (this.field_145848_d + 3), (this.field_145849_e + 2)));
/* 118 */             if (!entitiesNearby.isEmpty())
/*     */             {
/* 120 */               double minDistanceSquared = Double.MAX_VALUE;
/* 121 */               Entity closestEntity = null;
/* 122 */               for (Entity entity : entitiesNearby) {
/*     */                 
/* 124 */                 if (entity.field_70154_o == null && !entity.field_70128_L) {
/*     */                   
/* 126 */                   double distSquared = (this.field_145851_c - entity.field_70165_t) * (this.field_145851_c - entity.field_70165_t) + ((this.field_145848_d + 1) - entity.field_70163_u) * ((this.field_145848_d + 1) - entity.field_70163_u) + (this.field_145849_e - entity.field_70161_v) * (this.field_145849_e - entity.field_70161_v);
/* 127 */                   if (distSquared >= minDistanceSquared) {
/*     */                     continue;
/*     */                   }
/*     */                   
/* 131 */                   minDistanceSquared = distSquared;
/* 132 */                   closestEntity = entity;
/*     */                 } 
/*     */               } 
/* 135 */               teleport(closestEntity);
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 142 */         setActive(false);
/*     */       } 
/*     */     }
/* 145 */     if (isRendering() && getActive())
/*     */     {
/* 147 */       spawnBlueParticles(2, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 153 */     return (World)MinecraftServer.func_71276_C().func_71218_a(this.targetDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 159 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 161 */       IC2.audioManager.removeSources(this);
/* 162 */       this.audioSource = null;
/*     */     } 
/* 164 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleport(Entity user) {
/* 169 */     double distance = Math.sqrt(((this.field_145851_c - this.targetX) * (this.field_145851_c - this.targetX) + (this.field_145848_d - this.targetY) * (this.field_145848_d - this.targetY) + (this.field_145849_e - this.targetZ) * (this.field_145849_e - this.targetZ)));
/* 170 */     int weight = getWeightOf(user);
/* 171 */     boolean dimSwitch = (this.field_145850_b.field_73011_w.field_76574_g != this.targetDimension);
/* 172 */     if (weight == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     int energyCost = (int)(weight * Math.pow(distance + 10.0D, 0.7D) * 5.0D);
/* 177 */     if (dimSwitch)
/*     */     {
/* 179 */       energyCost *= 10;
/*     */     }
/* 181 */     if (energyCost > getAvailableEnergy()) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     consumeEnergy(energyCost);
/*     */     
/* 187 */     if (user instanceof EntityPlayerMP) {
/*     */       
/* 189 */       EntityPlayerMP player = (EntityPlayerMP)user;
/* 190 */       if (!IC2.enableTeleporterInventory)
/*     */       {
/* 192 */         player.field_71071_by.func_70436_m();
/*     */       }
/* 194 */       if (dimSwitch)
/*     */       {
/* 196 */         user.func_70080_a(this.targetX + 0.5D, this.targetY + 1.5D + user.func_70033_W(), this.targetZ + 0.5D, user.field_70177_z, user.field_70125_A);
/*     */       }
/*     */       else
/*     */       {
/* 200 */         player.func_70634_a(this.targetX + 0.5D, this.targetY + 1.5D + user.func_70033_W(), this.targetZ + 0.5D);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 205 */       user.func_70080_a(this.targetX + 0.5D, this.targetY + 1.5D + user.func_70033_W(), this.targetZ + 0.5D, user.field_70177_z, user.field_70125_A);
/*     */     } 
/* 207 */     if (dimSwitch)
/*     */     {
/* 209 */       switchDimension(user);
/*     */     }
/* 211 */     ((NetworkManager)IC2.network.get()).initiateTileEntityEvent((TileEntity)this, 0, true);
/* 212 */     if (user instanceof EntityPlayer && distance >= 1000.0D)
/*     */     {
/* 214 */       IC2.achievements.issueAchievement((EntityPlayer)user, "teleportFarAway");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDimSwtich() {
/* 220 */     return (this.field_145850_b.field_73011_w.field_76574_g != this.targetDimension);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistance() {
/* 225 */     return Math.sqrt(((this.field_145851_c - this.targetX) * (this.field_145851_c - this.targetX) + (this.field_145848_d - this.targetY) * (this.field_145848_d - this.targetY) + (this.field_145849_e - this.targetZ) * (this.field_145849_e - this.targetZ)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnBlueParticles(int n, World dim, int x, int y, int z) {
/* 230 */     for (int i = 0; i < n; i++) {
/*     */       
/* 232 */       dim.func_72869_a("reddust", (x + this.field_145850_b.field_73012_v.nextFloat()), ((y + 1) + this.field_145850_b.field_73012_v.nextFloat()), (z + this.field_145850_b.field_73012_v.nextFloat()), -1.0D, 0.0D, 1.0D);
/* 233 */       dim.func_72869_a("reddust", (x + this.field_145850_b.field_73012_v.nextFloat()), ((y + 2) + this.field_145850_b.field_73012_v.nextFloat()), (z + this.field_145850_b.field_73012_v.nextFloat()), -1.0D, 0.0D, 1.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchDimension(Entity entity) {
/* 239 */     if (entity instanceof EntityPlayerMP) {
/*     */       
/* 241 */       transferPlayerToDimension((EntityPlayerMP)entity, this.targetDimension);
/*     */     }
/*     */     else {
/*     */       
/* 245 */       transferEntityToWorld(entity, (WorldServer)this.field_145850_b, (WorldServer)getWorld());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void transferEntityToWorld(Entity entity, WorldServer oldWorld, WorldServer newWorld) {
/* 251 */     oldWorld.func_72973_f(entity);
/* 252 */     entity.field_70128_L = false;
/* 253 */     entity.field_71093_bK = newWorld.field_73011_w.field_76574_g;
/* 254 */     if (entity.func_70089_S()) {
/*     */       
/* 256 */       entity.func_70012_b(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70177_z, entity.field_70125_A);
/* 257 */       newWorld.func_72838_d(entity);
/* 258 */       newWorld.func_72866_a(entity, false);
/*     */     } 
/* 260 */     entity.func_70029_a((World)newWorld);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void transferPlayerToDimension(EntityPlayerMP player, int dimension) {
/* 265 */     MinecraftServer server = MinecraftServer.func_71276_C();
/* 266 */     ServerConfigurationManager manager = server.func_71203_ab();
/* 267 */     int oldDim = player.field_71093_bK;
/* 268 */     WorldServer worldserver = server.func_71218_a(player.field_71093_bK);
/* 269 */     player.field_71093_bK = dimension;
/* 270 */     WorldServer worldserver1 = server.func_71218_a(player.field_71093_bK);
/* 271 */     player.field_71135_a.func_147359_a((Packet)new S07PacketRespawn(player.field_71093_bK, player.field_70170_p.field_73013_u, player.field_70170_p.func_72912_H().func_76067_t(), player.field_71134_c.func_73081_b()));
/* 272 */     worldserver.func_72973_f((Entity)player);
/* 273 */     player.field_70128_L = false;
/* 274 */     if (player.func_70089_S()) {
/*     */       
/* 276 */       player.func_70012_b(player.field_70165_t, player.field_70163_u, player.field_70161_v, player.field_70177_z, player.field_70125_A);
/* 277 */       worldserver1.func_72838_d((Entity)player);
/* 278 */       worldserver1.func_72866_a((Entity)player, false);
/*     */     } 
/* 280 */     player.func_70029_a((World)worldserver1);
/* 281 */     manager.func_72375_a(player, worldserver);
/* 282 */     player.field_71135_a.func_147364_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, player.field_70177_z, player.field_70125_A);
/* 283 */     player.field_71134_c.func_73080_a(worldserver1);
/* 284 */     manager.func_72354_b(player, worldserver1);
/* 285 */     manager.func_72385_f(player);
/* 286 */     Iterator<PotionEffect> iterator = player.func_70651_bq().iterator();
/* 287 */     while (iterator.hasNext()) {
/*     */       
/* 289 */       PotionEffect potioneffect = iterator.next();
/* 290 */       player.field_71135_a.func_147359_a((Packet)new S1DPacketEntityEffect(player.func_145782_y(), potioneffect));
/*     */     } 
/* 292 */     FMLCommonHandler.instance().firePlayerChangedDimensionEvent((EntityPlayer)player, oldDim, dimension);
/*     */   }
/*     */ 
/*     */   
/*     */   public void consumeEnergy(int energy) {
/* 297 */     List<IEnergyStorage> energySources = new LinkedList();
/* 298 */     for (Direction direction : directions) {
/*     */       
/* 300 */       TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 301 */       if (target instanceof IEnergyStorage) {
/*     */         
/* 303 */         IEnergyStorage energySource = (IEnergyStorage)target;
/* 304 */         if (energySource.isTeleporterCompatible(direction.getInverse().toForgeDirection()) && energySource.getStored() > 0)
/*     */         {
/* 306 */           energySources.add(energySource);
/*     */         }
/*     */       } 
/*     */     } 
/* 310 */     while (energy > 0) {
/*     */       
/* 312 */       int drain = (energy + energySources.size() - 1) / energySources.size();
/* 313 */       Iterator<IEnergyStorage> it = energySources.iterator();
/* 314 */       while (it.hasNext()) {
/*     */         
/* 316 */         IEnergyStorage energySource2 = it.next();
/* 317 */         if (drain > energy)
/*     */         {
/* 319 */           drain = energy;
/*     */         }
/* 321 */         if (energySource2.getStored() <= drain) {
/*     */           
/* 323 */           energy -= energySource2.getStored();
/* 324 */           energySource2.setStored(0);
/* 325 */           it.remove();
/*     */           
/*     */           continue;
/*     */         } 
/* 329 */         energy -= drain;
/* 330 */         energySource2.addEnergy(-drain);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAvailableEnergy() {
/* 338 */     int energy = 0;
/* 339 */     for (Direction direction : directions) {
/*     */       
/* 341 */       TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 342 */       if (target instanceof IEnergyStorage) {
/*     */         
/* 344 */         IEnergyStorage storage = (IEnergyStorage)target;
/* 345 */         if (storage.isTeleporterCompatible(direction.getInverse().toForgeDirection()))
/*     */         {
/* 347 */           energy += storage.getStored();
/*     */         }
/*     */       } 
/*     */     } 
/* 351 */     return energy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeightOf(Entity user) {
/* 356 */     int weight = 0;
/* 357 */     for (Entity ce = user; ce != null; ce = ce.field_70153_n) {
/*     */       
/* 359 */       if (ce instanceof EntityItem) {
/*     */         
/* 361 */         ItemStack is = ((EntityItem)ce).func_92059_d();
/* 362 */         weight += 100 * is.field_77994_a / is.func_77976_d();
/*     */       }
/* 364 */       else if (ce instanceof net.minecraft.entity.passive.EntityAnimal || ce instanceof net.minecraft.entity.item.EntityMinecart || ce instanceof net.minecraft.entity.item.EntityBoat || ce instanceof ic2.core.item.boats.EntityClassicBoat) {
/*     */         
/* 366 */         weight += 100;
/*     */       }
/* 368 */       else if (ce instanceof EntityPlayer) {
/*     */         
/* 370 */         weight += 1000;
/* 371 */         if (IC2.enableTeleporterInventory) {
/*     */           
/* 373 */           InventoryPlayer inv = ((EntityPlayer)ce).field_71071_by;
/* 374 */           for (int i = 0; i < inv.func_70302_i_(); i++)
/*     */           {
/* 376 */             ItemStack stack = inv.func_70301_a(i);
/* 377 */             if (stack != null)
/*     */             {
/* 379 */               weight += 100 * stack.field_77994_a / stack.func_77976_d();
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 384 */       } else if (ce instanceof net.minecraft.entity.monster.EntityGhast) {
/*     */         
/* 386 */         weight += 2500;
/*     */       }
/* 388 */       else if (ce instanceof net.minecraft.entity.boss.EntityDragon || ce instanceof net.minecraft.entity.boss.EntityWither) {
/*     */         
/* 390 */         weight += 10000;
/*     */       }
/* 392 */       else if (ce instanceof net.minecraft.entity.EntityCreature) {
/*     */         
/* 394 */         weight += 500;
/*     */       } 
/* 396 */       if (IC2.enableTeleporterInventory && ce instanceof EntityLivingBase && !(ce instanceof EntityPlayer)) {
/*     */         
/* 398 */         EntityLivingBase living = (EntityLivingBase)ce;
/* 399 */         for (int i = 0; i <= 4; i++) {
/*     */           
/* 401 */           ItemStack item = living.func_71124_b(i);
/* 402 */           if (item != null)
/*     */           {
/* 404 */             weight += 100 * item.field_77994_a / item.func_77976_d();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 409 */     return weight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(int dim, int x, int y, int z) {
/* 414 */     this.targetSet = true;
/* 415 */     this.targetX = x;
/* 416 */     this.targetY = y;
/* 417 */     this.targetZ = z;
/* 418 */     this.targetDimension = dim;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 424 */     if (field.equals("active") && this.prevActive != getActive()) {
/*     */       
/* 426 */       if (this.audioSource == null)
/*     */       {
/* 428 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/Teleporter/TeleChargedLoop.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */       }
/* 430 */       if (getActive()) {
/*     */         
/* 432 */         if (this.audioSource != null)
/*     */         {
/* 434 */           this.audioSource.play();
/*     */         }
/*     */       }
/* 437 */       else if (this.audioSource != null) {
/*     */         
/* 439 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/* 442 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 448 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 452 */         IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/Teleporter/TeleUse.ogg", true, IC2.audioManager.defaultVolume);
/* 453 */         IC2.audioManager.playOnce(new AudioPosition(this.field_145850_b, this.targetX + 0.5F, this.targetY + 0.5F, this.targetZ + 0.5F), PositionSpec.Center, "Machines/Teleporter/TeleUse.ogg", true, IC2.audioManager.defaultVolume);
/* 454 */         spawnBlueParticles(20, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 455 */         spawnBlueParticles(20, getWorld(), this.targetX, this.targetY, this.targetZ);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 460 */     IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 468 */     directions = Direction.values();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityTeleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */