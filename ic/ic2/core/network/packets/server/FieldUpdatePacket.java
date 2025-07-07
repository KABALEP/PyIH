/*     */ package ic2.core.network.packets.server;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import ic2.api.network.INetworkFieldData;
/*     */ import ic2.api.network.INetworkUpdateListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.DataEncoder;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldUpdatePacket
/*     */   extends IC2Packet
/*     */ {
/*  31 */   Map<ChunkCoordinates, Map<String, Object>> data = new HashMap<>();
/*     */   
/*     */   int dimID;
/*     */ 
/*     */   
/*     */   public FieldUpdatePacket() {}
/*     */ 
/*     */   
/*     */   public FieldUpdatePacket(List<NetworkManager.FieldData> par1, World par2) {
/*  40 */     for (NetworkManager.FieldData cu : par1) {
/*     */       
/*  42 */       ChunkCoordinates coords = cu.getCoords();
/*  43 */       if (!this.data.containsKey(coords))
/*     */       {
/*  45 */         this.data.put(coords, new HashMap<>());
/*     */       }
/*  47 */       ((Map<String, Object>)this.data.get(coords)).put(cu.fieldName, cu.data);
/*     */     } 
/*  49 */     this.dimID = par2.field_73011_w.field_76574_g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(ByteBuf par1) {
/*  56 */     this.dimID = par1.readInt();
/*  57 */     int expected = par1.readInt();
/*  58 */     byte[] byteArray = new byte[par1.readInt()];
/*  59 */     par1.readBytes(byteArray);
/*     */     
/*     */     try {
/*  62 */       DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(byteArray)));
/*  63 */       for (int i = 0; i < expected; i++) {
/*     */         
/*  65 */         int amount = ((Integer)DataEncoder.decode(stream)).intValue();
/*  66 */         ChunkCoordinates coord = (ChunkCoordinates)DataEncoder.decode(stream);
/*  67 */         if (!this.data.containsKey(coord))
/*     */         {
/*  69 */           this.data.put(coord, new HashMap<>());
/*     */         }
/*  71 */         for (int z = 0; z < amount; z++)
/*     */         {
/*  73 */           ((Map<String, Object>)this.data.get(coord)).put((String)DataEncoder.decode(stream), DataEncoder.decode(stream));
/*     */         }
/*     */       } 
/*  76 */       stream.close();
/*     */     }
/*  78 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ByteBuf par1) {
/*  86 */     par1.writeInt(this.dimID);
/*  87 */     par1.writeInt(this.data.size());
/*     */     
/*     */     try {
/*  90 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*  91 */       DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(bytes));
/*  92 */       for (Map.Entry<ChunkCoordinates, Map<String, Object>> entry : this.data.entrySet()) {
/*     */         
/*  94 */         Map<String, Object> value = entry.getValue();
/*  95 */         DataEncoder.encode(stream, Integer.valueOf(value.size()));
/*  96 */         DataEncoder.encode(stream, entry.getKey());
/*  97 */         for (Map.Entry<String, Object> dataValue : value.entrySet()) {
/*     */           
/*  99 */           DataEncoder.encode(stream, dataValue.getKey());
/* 100 */           DataEncoder.encode(stream, dataValue.getValue());
/*     */         } 
/*     */       } 
/* 103 */       stream.close();
/* 104 */       bytes.close();
/* 105 */       byte[] resultData = bytes.toByteArray();
/* 106 */       par1.writeInt(resultData.length);
/* 107 */       par1.writeBytes(resultData);
/*     */     }
/* 109 */     catch (Exception e) {
/*     */       
/* 111 */       FMLLog.getLogger().info("Packet got a problem during Writing data");
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePacket(EntityPlayer par1) {
/* 119 */     World world = IC2.platform.getWorld(this.dimID);
/* 120 */     if (world == null)
/*     */     {
/* 122 */       throw new RuntimeException("Packet Got Incorrect Data");
/*     */     }
/* 124 */     for (Map.Entry<ChunkCoordinates, Map<String, Object>> entry : this.data.entrySet()) {
/*     */       
/* 126 */       Map<String, Object> map = entry.getValue();
/* 127 */       ChunkCoordinates coord = entry.getKey();
/* 128 */       TileEntity tile = world.func_147438_o(coord.field_71574_a, coord.field_71572_b, coord.field_71573_c);
/* 129 */       if (tile != null)
/*     */       {
/* 131 */         for (Map.Entry<String, Object> fields : map.entrySet()) {
/*     */ 
/*     */           
/*     */           try {
/* 135 */             Object value = fields.getValue();
/* 136 */             String field = fields.getKey();
/* 137 */             Field targetField = null;
/* 138 */             Class<?> fieldDeclaringClass = tile.getClass();
/*     */ 
/*     */             
/*     */             do {
/*     */               try {
/* 143 */                 targetField = fieldDeclaringClass.getDeclaredField(field);
/*     */               }
/* 145 */               catch (NoSuchFieldException e4) {
/*     */                 
/* 147 */                 fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */               }
/*     */             
/* 150 */             } while (targetField == null && fieldDeclaringClass != null);
/* 151 */             if (targetField == null) {
/*     */               
/* 153 */               IC2.log.warn("Can't find field " + field + " in te " + tile + " at " + coord.field_71574_a + "/" + coord.field_71572_b + "/" + coord.field_71573_c);
/*     */             }
/*     */             else {
/*     */               
/* 157 */               targetField.setAccessible(true);
/*     */             } 
/* 159 */             if (targetField != null && tile != null) {
/*     */               
/* 161 */               if (targetField.getType().isEnum())
/*     */               {
/* 163 */                 value = targetField.getType().getEnumConstants()[((Integer)value).intValue()];
/*     */               }
/* 165 */               if (INetworkFieldData.class.isAssignableFrom(targetField.getType()) && value instanceof byte[]) {
/*     */                 
/* 167 */                 DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream((byte[])value)));
/* 168 */                 INetworkFieldData data = (INetworkFieldData)targetField.get(tile);
/* 169 */                 data.read(stream);
/* 170 */                 stream.close();
/*     */               }
/*     */               else {
/*     */                 
/* 174 */                 targetField.set(tile, value);
/*     */               } 
/*     */             } 
/* 177 */             if (tile instanceof INetworkUpdateListener)
/*     */             {
/* 179 */               ((INetworkUpdateListener)tile).onNetworkUpdate(field);
/*     */             }
/*     */           }
/* 182 */           catch (Exception e) {
/*     */             
/* 184 */             FMLLog.getLogger().info("Field: " + (String)fields.getKey());
/* 185 */             e.printStackTrace();
/* 186 */             throw new RuntimeException("Could not Inject TileEntity Data");
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\FieldUpdatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */