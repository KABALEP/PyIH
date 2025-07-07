/*     */ package ic2.core.network;
/*     */ 
/*     */ import ic2.api.network.INetworkDataProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.network.internal.INetworkGuiDataProvider;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import ic2.core.network.packets.server.BlockUpdatePacket;
/*     */ import ic2.core.network.packets.server.ExplosionPacket;
/*     */ import ic2.core.network.packets.server.FieldUpdatePacket;
/*     */ import ic2.core.network.packets.server.GuiFieldPacket;
/*     */ import ic2.core.network.packets.server.ItemEventPacket;
/*     */ import ic2.core.network.packets.server.ItemGuiPacket;
/*     */ import ic2.core.network.packets.server.TileGuiPacket;
/*     */ import ic2.core.network.packets.server.TileServerEventPacket;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */ {
/*  42 */   private int updatePeriod = 2;
/*  43 */   protected static Map<World, FieldStorage> fieldsToUpdateSet = new HashMap<>();
/*  44 */   protected static Map<World, GuiFieldStorage> guiToSync = new LinkedHashMap<>();
/*  45 */   public static Map<EntityPlayer, TileEntity> watchingTile = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnload() {
/*  50 */     fieldsToUpdateSet.clear();
/*  51 */     guiToSync.clear();
/*  52 */     watchingTile.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearGuiFields() {
/*  57 */     for (GuiFieldStorage storage : guiToSync.values())
/*     */     {
/*  59 */       storage.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWorldUnload(World world) {
/*  65 */     FieldStorage storage = fieldsToUpdateSet.remove(world);
/*  66 */     if (storage != null)
/*     */     {
/*  68 */       storage.clear();
/*     */     }
/*  70 */     GuiFieldStorage gui = guiToSync.remove(world);
/*  71 */     if (gui != null)
/*     */     {
/*  73 */       gui.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(World world) {
/*  79 */     sendUpdatePacket(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startWatchingTile(EntityPlayer player, TileEntity tile) {
/*  84 */     watchingTile.put(player, tile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGuiChanges(EntityPlayer player, TileEntity tile) {
/*  89 */     GuiFieldStorage storage = guiToSync.get(tile.func_145831_w());
/*  90 */     if (storage == null) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     List<FieldData> data = storage.getData(tile);
/*  95 */     if (data.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     PacketManager.instance.sendToPlayer((IC2Packet)new GuiFieldPacket(tile, data), player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestInitialGuiData(EntityPlayer player, INetworkGuiDataProvider dataProvider) {
/* 104 */     if (!(dataProvider instanceof TileEntity)) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     List<FieldData> data = new ArrayList<>();
/* 109 */     for (String field : dataProvider.getGuiFields())
/*     */     {
/* 111 */       data.add(GuiFieldStorage.createData((TileEntity)dataProvider, field));
/*     */     }
/* 113 */     PacketManager.instance.sendToPlayer((IC2Packet)new GuiFieldPacket((TileEntity)dataProvider, data), player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTileGuiField(TileEntity te, String field) {
/* 118 */     GuiFieldStorage worldFieldStorage = guiToSync.get(te.func_145831_w());
/* 119 */     if (worldFieldStorage == null) {
/*     */       
/* 121 */       worldFieldStorage = new GuiFieldStorage();
/* 122 */       guiToSync.put(te.func_145831_w(), worldFieldStorage);
/*     */     } 
/* 124 */     worldFieldStorage.addTileEntityField(te, field);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTileEntityField(TileEntity te, String field) {
/* 129 */     FieldStorage worldFieldStorage = fieldsToUpdateSet.get(te.func_145831_w());
/* 130 */     if (worldFieldStorage == null) {
/*     */       
/* 132 */       worldFieldStorage = new FieldStorage();
/* 133 */       fieldsToUpdateSet.put(te.func_145831_w(), worldFieldStorage);
/*     */     } 
/* 135 */     if (worldFieldStorage.addGlobalField(new TileEntityField(te, field)))
/*     */     {
/* 137 */       sendUpdatePacket(te.func_145831_w());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTileEntityField(TileEntity te, String field, EntityPlayerMP player) {
/* 143 */     FieldStorage worldFieldStorage = fieldsToUpdateSet.get(te.func_145831_w());
/* 144 */     if (worldFieldStorage == null) {
/*     */       
/* 146 */       worldFieldStorage = new FieldStorage();
/* 147 */       fieldsToUpdateSet.put(te.func_145831_w(), worldFieldStorage);
/*     */     } 
/* 149 */     if (worldFieldStorage.addPlayerField(player, new TileEntityField(te, field)))
/*     */     {
/* 151 */       sendUpdatePacket(te.func_145831_w());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange) {
/* 157 */     int maxDistance = (MinecraftServer.func_71276_C().func_71203_ab().func_72372_a() + 16) / 2;
/* 158 */     World world = te.func_145831_w();
/* 159 */     TileServerEventPacket packet = null;
/* 160 */     for (Object obj : world.field_73010_i) {
/*     */       
/* 162 */       EntityPlayerMP entityPlayer = (EntityPlayerMP)obj;
/* 163 */       if (entityPlayer.func_70011_f(te.field_145851_c, te.field_145848_d, te.field_145849_e) < maxDistance) {
/*     */         
/* 165 */         if (packet == null)
/*     */         {
/* 167 */           packet = new TileServerEventPacket(te, event);
/*     */         }
/* 169 */         PacketManager.instance.sendToPlayer((IC2Packet)packet, (EntityPlayer)entityPlayer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange) {
/* 176 */     ItemEventPacket packet = new ItemEventPacket(itemStack, event);
/* 177 */     PacketManager.instance.sendToPlayer((IC2Packet)packet, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void announceBlockUpdate(World world, int x, int y, int z) {
/* 182 */     BlockUpdatePacket packet = null;
/* 183 */     for (Object obj : world.field_73010_i) {
/*     */       
/* 185 */       EntityPlayerMP entityPlayer = (EntityPlayerMP)obj;
/* 186 */       int distance = Math.min(Math.abs(x - (int)entityPlayer.field_70165_t), Math.abs(z - (int)entityPlayer.field_70161_v));
/* 187 */       if (distance <= MinecraftServer.func_71276_C().func_71203_ab().func_72372_a() + 16) {
/*     */         
/* 189 */         if (packet == null)
/*     */         {
/* 191 */           packet = new BlockUpdatePacket(world, x, y, z);
/*     */         }
/* 193 */         PacketManager.instance.sendToPlayer((IC2Packet)packet, (EntityPlayer)entityPlayer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendCustomPacket(IC2Packet packet) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendCustomPacket(EntityPlayer player, IC2Packet packet) {
/* 205 */     PacketManager.instance.sendToPlayer(packet, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestInitialData(INetworkDataProvider dataProvider) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateClientItemEvent(ItemStack itemStack, int event) {}
/*     */ 
/*     */   
/*     */   public void initiateClientTileEntityEvent(TileEntity te, int event) {}
/*     */ 
/*     */   
/*     */   public void initiateGuiDisplay(EntityPlayerMP entityPlayer, IHasGui inventory, int windowId) {
/*     */     ItemGuiPacket itemGuiPacket;
/* 222 */     IC2Packet packet = null;
/* 223 */     if (inventory instanceof TileEntity) {
/*     */       
/* 225 */       TileEntity tile = (TileEntity)inventory;
/* 226 */       TileGuiPacket tileGuiPacket = new TileGuiPacket(tile, windowId);
/* 227 */       if (tile instanceof INetworkGuiDataProvider)
/*     */       {
/* 229 */         requestInitialGuiData((EntityPlayer)entityPlayer, (INetworkGuiDataProvider)tile);
/*     */       }
/* 231 */       startWatchingTile((EntityPlayer)entityPlayer, tile);
/*     */     }
/* 233 */     else if (inventory instanceof Entity) {
/*     */       
/* 235 */       itemGuiPacket = new ItemGuiPacket(((Entity)inventory).func_145782_y(), windowId, true);
/*     */     }
/* 237 */     else if (entityPlayer.field_71071_by.func_70448_g() != null && entityPlayer.field_71071_by.func_70448_g().func_77973_b() instanceof ic2.core.item.IHandHeldInventory) {
/*     */       
/* 239 */       itemGuiPacket = new ItemGuiPacket(entityPlayer.field_71071_by.field_70461_c, windowId, false);
/*     */     }
/*     */     else {
/*     */       
/* 243 */       IC2.platform.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + inventory + ")");
/*     */     } 
/* 245 */     if (itemGuiPacket != null)
/*     */     {
/* 247 */       PacketManager.instance.sendToPlayer((IC2Packet)itemGuiPacket, (EntityPlayer)entityPlayer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendUpdatePacket(World world) {
/* 253 */     if (!fieldsToUpdateSet.containsKey(world)) {
/*     */       return;
/*     */     }
/*     */     
/* 257 */     FieldStorage worldFieldStorage = fieldsToUpdateSet.get(world);
/* 258 */     if (worldFieldStorage.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 262 */     if (world.field_73010_i.isEmpty()) {
/*     */       
/* 264 */       worldFieldStorage.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 268 */     Map<EntityPlayer, List<FieldData>> playerQuere = new HashMap<>();
/* 269 */     int viewDistance = MinecraftServer.func_71276_C().func_71203_ab().func_72372_a() + 16;
/* 270 */     if (worldFieldStorage.hasGlobalFields())
/*     */     {
/* 272 */       for (TileEntityField field : worldFieldStorage.getGlobalFields()) {
/*     */         
/* 274 */         if (field.te.func_145837_r() || field.te.func_145831_w() != world) {
/*     */           continue;
/*     */         }
/*     */         
/* 278 */         FieldData data = new FieldData();
/* 279 */         data.x = field.te.field_145851_c;
/* 280 */         data.y = field.te.field_145848_d;
/* 281 */         data.z = field.te.field_145849_e;
/* 282 */         data.fieldName = field.field;
/* 283 */         data.data = field.getData();
/* 284 */         for (Object obj : world.field_73010_i) {
/*     */           
/* 286 */           EntityPlayerMP player = (EntityPlayerMP)obj;
/* 287 */           int distance = Math.min(Math.abs(data.x - (int)player.field_70165_t), Math.abs(data.z - (int)player.field_70161_v));
/* 288 */           if (distance > viewDistance) {
/*     */             continue;
/*     */           }
/*     */           
/* 292 */           List<FieldData> quere = playerQuere.get(player);
/* 293 */           if (quere == null) {
/*     */             
/* 295 */             quere = new ArrayList<>(worldFieldStorage.size());
/* 296 */             playerQuere.put(player, quere);
/*     */           } 
/* 298 */           quere.add(data);
/*     */         } 
/*     */       } 
/*     */     }
/* 302 */     if (worldFieldStorage.hasPlayerFields())
/*     */     {
/* 304 */       for (Map.Entry<TileEntityField, Set<EntityPlayerMP>> entry : worldFieldStorage.getPlayerFields()) {
/*     */         
/* 306 */         TileEntityField field = entry.getKey();
/* 307 */         if (field.te.func_145837_r() || field.te.func_145831_w() != world) {
/*     */           continue;
/*     */         }
/*     */         
/* 311 */         FieldData data = new FieldData();
/* 312 */         data.x = field.te.field_145851_c;
/* 313 */         data.y = field.te.field_145848_d;
/* 314 */         data.z = field.te.field_145849_e;
/* 315 */         data.fieldName = field.field;
/* 316 */         data.data = field.getData();
/* 317 */         for (EntityPlayerMP player : entry.getValue()) {
/*     */           
/* 319 */           if (player.field_70128_L || player.field_70170_p != world) {
/*     */             continue;
/*     */           }
/*     */           
/* 323 */           int distance = Math.min(Math.abs(data.x - (int)player.field_70165_t), Math.abs(data.z - (int)player.field_70161_v));
/* 324 */           if (distance > viewDistance) {
/*     */             continue;
/*     */           }
/*     */           
/* 328 */           List<FieldData> quere = playerQuere.get(player);
/* 329 */           if (quere == null) {
/*     */             
/* 331 */             quere = new ArrayList<>(worldFieldStorage.size());
/* 332 */             playerQuere.put(player, quere);
/*     */           } 
/* 334 */           quere.add(data);
/*     */         } 
/*     */       } 
/*     */     }
/* 338 */     for (Map.Entry<EntityPlayer, List<FieldData>> entry : playerQuere.entrySet()) {
/*     */       
/* 340 */       FieldUpdatePacket packet = new FieldUpdatePacket(entry.getValue(), world);
/* 341 */       PacketManager.instance.sendToPlayer((IC2Packet)packet, entry.getKey());
/*     */     } 
/* 343 */     worldFieldStorage.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateKeyUpdate(int keyState) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendLoginData() {}
/*     */ 
/*     */   
/*     */   public void sendInitialData(EntityPlayerMP mp, TileEntity tile) {
/* 356 */     if (tile instanceof INetworkDataProvider)
/*     */     {
/* 358 */       for (String key : ((INetworkDataProvider)tile).getNetworkedFields())
/*     */       {
/* 360 */         updateTileEntityField(tile, key);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateExplosionEffect(World world, double x, double y, double z) {
/* 367 */     initiateExplosionEffect(world, x, y, z, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateExplosionEffect(World world, double x, double y, double z, boolean nuke) {
/* 372 */     ExplosionPacket packet = new ExplosionPacket(world, x, y, z, nuke);
/* 373 */     for (Object player : world.field_73010_i) {
/*     */       
/* 375 */       EntityPlayerMP entityPlayer = (EntityPlayerMP)player;
/* 376 */       if (entityPlayer.func_70092_e(x, y, z) < 128.0D)
/*     */       {
/* 378 */         PacketManager.instance.sendToPlayer((IC2Packet)packet, (EntityPlayer)entityPlayer);
/*     */       }
/*     */     } 
/*     */     
/* 382 */     if (!nuke)
/*     */     {
/* 384 */       world.func_72908_a(x, y, z, "random.explode", 4.0F, (1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldData
/*     */   {
/*     */     public int x;
/*     */     public int y;
/*     */     public int z;
/*     */     public String fieldName;
/*     */     public Object data;
/*     */     
/*     */     public ChunkCoordinates getCoords() {
/* 398 */       return new ChunkCoordinates(this.x, this.y, this.z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class TileEntityField
/*     */   {
/*     */     TileEntity te;
/*     */     
/*     */     String field;
/*     */     EntityPlayerMP target;
/*     */     
/*     */     TileEntityField(TileEntity te, String field) {
/* 411 */       this.target = null;
/* 412 */       this.te = te;
/* 413 */       this.field = field;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getData() {
/*     */       try {
/* 420 */         Field targetfield = null;
/* 421 */         Class<?> fieldDeclaringClass = this.te.getClass();
/*     */ 
/*     */         
/*     */         do {
/*     */           try {
/* 426 */             targetfield = fieldDeclaringClass.getDeclaredField(this.field);
/*     */           }
/* 428 */           catch (NoSuchFieldException e3) {
/*     */             
/* 430 */             fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */           }
/*     */         
/* 433 */         } while (targetfield == null && fieldDeclaringClass != null);
/* 434 */         if (targetfield == null)
/*     */         {
/* 436 */           throw new NoSuchFieldException(this.field);
/*     */         }
/* 438 */         targetfield.setAccessible(true);
/* 439 */         return targetfield.get(this.te);
/*     */       }
/* 441 */       catch (Exception e) {
/*     */         
/* 443 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     TileEntityField(TileEntity te, String field, EntityPlayerMP target) {
/* 450 */       this.target = null;
/* 451 */       this.te = te;
/* 452 */       this.field = field;
/* 453 */       this.target = target;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 459 */       if (obj instanceof TileEntityField) {
/*     */         
/* 461 */         TileEntityField tef = (TileEntityField)obj;
/* 462 */         return (tef.te == this.te && tef.field.equals(this.field));
/*     */       } 
/* 464 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 470 */       return this.te.hashCode() * 31 ^ this.field.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FieldStorage
/*     */   {
/* 476 */     Set<NetworkManager.TileEntityField> globalFields = new LinkedHashSet<>();
/* 477 */     Map<NetworkManager.TileEntityField, Set<EntityPlayerMP>> playerFields = new LinkedHashMap<>();
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 481 */       return (this.globalFields.isEmpty() && this.playerFields.isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasGlobalFields() {
/* 486 */       return (this.globalFields.size() > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPlayerFields() {
/* 491 */       return (this.playerFields.size() > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return this.globalFields.size() + this.playerFields.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<NetworkManager.TileEntityField> getGlobalFields() {
/* 501 */       return this.globalFields;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<NetworkManager.TileEntityField, Set<EntityPlayerMP>>> getPlayerFields() {
/* 506 */       return this.playerFields.entrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 511 */       this.globalFields = new LinkedHashSet<>();
/* 512 */       this.playerFields = new LinkedHashMap<>();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addGlobalField(NetworkManager.TileEntityField field) {
/* 517 */       if (!this.globalFields.add(field))
/*     */       {
/* 519 */         return false;
/*     */       }
/* 521 */       this.playerFields.remove(field);
/* 522 */       return (size() >= 10000);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addPlayerField(EntityPlayerMP player, NetworkManager.TileEntityField field) {
/* 527 */       if (this.globalFields.contains(field))
/*     */       {
/* 529 */         return false;
/*     */       }
/* 531 */       Set<EntityPlayerMP> fields = this.playerFields.get(field);
/* 532 */       boolean result = false;
/* 533 */       if (fields == null) {
/*     */         
/* 535 */         fields = new LinkedHashSet<>();
/* 536 */         this.playerFields.put(field, fields);
/* 537 */         result = (size() >= 10000);
/*     */       } 
/* 539 */       if (fields.add(player))
/*     */       {
/* 541 */         return result;
/*     */       }
/* 543 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiFieldStorage
/*     */   {
/* 549 */     Map<TileEntity, Set<String>> fieldStorage = new HashMap<>();
/* 550 */     Map<TileEntity, Map<String, NetworkManager.FieldData>> dataStorage = new HashMap<>();
/*     */ 
/*     */     
/*     */     public void addTileEntityField(TileEntity tile, String id) {
/* 554 */       Set<String> keys = this.fieldStorage.get(tile);
/* 555 */       if (keys == null) {
/*     */         
/* 557 */         keys = new LinkedHashSet<>();
/* 558 */         this.fieldStorage.put(tile, keys);
/*     */       } 
/* 560 */       keys.add(id);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 565 */       this.fieldStorage = new LinkedHashMap<>();
/* 566 */       this.dataStorage = new HashMap<>();
/*     */     }
/*     */ 
/*     */     
/*     */     public List<NetworkManager.FieldData> getData(TileEntity tile) {
/* 571 */       Set<String> keys = this.fieldStorage.get(tile);
/* 572 */       if (keys == null || keys.isEmpty())
/*     */       {
/* 574 */         return new ArrayList<>();
/*     */       }
/* 576 */       Map<String, NetworkManager.FieldData> data = this.dataStorage.get(tile);
/* 577 */       if (data == null) {
/*     */         
/* 579 */         data = new HashMap<>();
/* 580 */         this.dataStorage.put(tile, data);
/*     */       } 
/* 582 */       List<NetworkManager.FieldData> result = new ArrayList<>();
/* 583 */       for (String key : keys) {
/*     */         
/* 585 */         NetworkManager.FieldData local = data.get(key);
/* 586 */         if (local == null) {
/*     */           
/* 588 */           local = createData(tile, key);
/* 589 */           data.put(key, local);
/*     */         } 
/* 591 */         result.add(local);
/*     */       } 
/* 593 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public static NetworkManager.FieldData createData(TileEntity tile, String field) {
/* 598 */       NetworkManager.FieldData data = new NetworkManager.FieldData();
/* 599 */       data.x = tile.field_145851_c;
/* 600 */       data.y = tile.field_145848_d;
/* 601 */       data.z = tile.field_145849_e;
/* 602 */       data.fieldName = field;
/* 603 */       data.data = getData(tile, field);
/* 604 */       return data;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static Object getData(TileEntity tile, String field) {
/*     */       try {
/* 611 */         Field targetfield = null;
/* 612 */         Class<?> fieldDeclaringClass = tile.getClass();
/*     */ 
/*     */         
/*     */         do {
/*     */           try {
/* 617 */             targetfield = fieldDeclaringClass.getDeclaredField(field);
/*     */           }
/* 619 */           catch (NoSuchFieldException e3) {
/*     */             
/* 621 */             fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */           }
/*     */         
/* 624 */         } while (targetfield == null && fieldDeclaringClass != null);
/* 625 */         if (targetfield == null)
/*     */         {
/* 627 */           throw new NoSuchFieldException(field);
/*     */         }
/* 629 */         targetfield.setAccessible(true);
/* 630 */         return targetfield.get(tile);
/*     */       }
/* 632 */       catch (Exception e) {
/*     */         
/* 634 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */