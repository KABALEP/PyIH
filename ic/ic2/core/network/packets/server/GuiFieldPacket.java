/*     */ package ic2.core.network.packets.server;
/*     */ 
/*     */ import ic2.api.network.INetworkFieldData;
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
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiFieldPacket
/*     */   extends IC2Packet
/*     */ {
/*  28 */   Map<String, Object> data = new LinkedHashMap<>();
/*     */   
/*     */   int dim;
/*     */   
/*     */   int x;
/*     */   
/*     */   int y;
/*     */   int z;
/*     */   
/*     */   public GuiFieldPacket() {}
/*     */   
/*     */   public GuiFieldPacket(TileEntity tile, List<NetworkManager.FieldData> fields) {
/*  40 */     this.dim = (tile.func_145831_w()).field_73011_w.field_76574_g;
/*  41 */     this.x = tile.field_145851_c;
/*  42 */     this.y = tile.field_145848_d;
/*  43 */     this.z = tile.field_145849_e;
/*  44 */     for (int i = 0; i < fields.size(); i++) {
/*     */       
/*  46 */       NetworkManager.FieldData field = fields.get(i);
/*  47 */       this.data.put(field.fieldName, field.data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(ByteBuf par1) {
/*  54 */     this.dim = par1.readInt();
/*  55 */     this.x = par1.readInt();
/*  56 */     this.y = par1.readInt();
/*  57 */     this.z = par1.readInt();
/*  58 */     int expected = par1.readInt();
/*  59 */     byte[] byteArray = new byte[par1.readInt()];
/*  60 */     par1.readBytes(byteArray);
/*     */     
/*     */     try {
/*  63 */       DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(byteArray)));
/*  64 */       for (int i = 0; i < expected; i++)
/*     */       {
/*  66 */         String key = (String)DataEncoder.decode(stream);
/*  67 */         Object value = DataEncoder.decode(stream);
/*  68 */         this.data.put(key, value);
/*     */       }
/*     */     
/*  71 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ByteBuf par1) {
/*  79 */     par1.writeInt(this.dim);
/*  80 */     par1.writeInt(this.x);
/*  81 */     par1.writeInt(this.y);
/*  82 */     par1.writeInt(this.z);
/*  83 */     par1.writeInt(this.data.size());
/*     */     
/*     */     try {
/*  86 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*  87 */       DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(bytes));
/*  88 */       for (Map.Entry<String, Object> entry : this.data.entrySet()) {
/*     */         
/*  90 */         DataEncoder.encode(stream, entry.getKey());
/*  91 */         DataEncoder.encode(stream, entry.getValue());
/*     */       } 
/*  93 */       stream.close();
/*  94 */       bytes.close();
/*  95 */       byte[] resultData = bytes.toByteArray();
/*  96 */       par1.writeInt(resultData.length);
/*  97 */       par1.writeBytes(resultData);
/*     */     }
/*  99 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePacket(EntityPlayer par1) {
/* 107 */     World world = IC2.platform.getWorld(this.dim);
/* 108 */     if (world == null)
/*     */     {
/* 110 */       throw new RuntimeException("Packet Got Incorrect Data");
/*     */     }
/* 112 */     TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/* 113 */     if (tile == null) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     for (Map.Entry<String, Object> entry : this.data.entrySet()) {
/*     */ 
/*     */       
/*     */       try {
/* 121 */         Object value = entry.getValue();
/* 122 */         String field = entry.getKey();
/* 123 */         Field targetField = null;
/* 124 */         Class<?> fieldDeclaringClass = tile.getClass();
/*     */ 
/*     */         
/*     */         do {
/*     */           try {
/* 129 */             targetField = fieldDeclaringClass.getDeclaredField(field);
/*     */           }
/* 131 */           catch (NoSuchFieldException e4) {
/*     */             
/* 133 */             fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */           }
/*     */         
/* 136 */         } while (targetField == null && fieldDeclaringClass != null);
/* 137 */         if (targetField == null) {
/*     */           
/* 139 */           IC2.log.warn("Can't find field " + field + " in te " + tile + " at " + this.x + "/" + this.y + "/" + this.z);
/*     */         }
/*     */         else {
/*     */           
/* 143 */           targetField.setAccessible(true);
/*     */         } 
/* 145 */         if (targetField != null && tile != null)
/*     */         {
/* 147 */           if (targetField.getType().isEnum())
/*     */           {
/* 149 */             value = targetField.getType().getEnumConstants()[((Integer)value).intValue()];
/*     */           }
/* 151 */           if (INetworkFieldData.class.isAssignableFrom(targetField.getType()) && value instanceof byte[]) {
/*     */             
/* 153 */             DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream((byte[])value)));
/* 154 */             INetworkFieldData data = (INetworkFieldData)targetField.get(tile);
/* 155 */             data.read(stream);
/* 156 */             stream.close();
/*     */             
/*     */             continue;
/*     */           } 
/* 160 */           targetField.set(tile, value);
/*     */         }
/*     */       
/*     */       }
/* 164 */       catch (Exception e) {
/*     */         
/* 166 */         e.printStackTrace();
/* 167 */         throw new RuntimeException("Could not Inject TileEntity Data: " + (String)entry.getKey());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\GuiFieldPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */