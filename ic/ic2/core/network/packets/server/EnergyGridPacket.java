/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.item.tool.ItemDebug;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyGridPacket
/*    */   extends IC2Packet
/*    */ {
/* 18 */   Map<Integer, List<AxisAlignedBB>> toRead = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public EnergyGridPacket() {}
/*    */ 
/*    */   
/*    */   public EnergyGridPacket(Map<Integer, List<AxisAlignedBB>> data) {
/* 26 */     this.toRead.putAll(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 32 */     int amount = par1.readInt();
/* 33 */     for (int i = 0; i < amount; i++) {
/*    */       
/* 35 */       int id = par1.readInt();
/* 36 */       int count = par1.readInt();
/* 37 */       List<AxisAlignedBB> list = new ArrayList<>(count);
/* 38 */       for (int z = 0; z < count; z++)
/*    */       {
/* 40 */         list.add(AxisAlignedBB.func_72330_a(par1.readDouble(), par1.readDouble(), par1.readDouble(), par1.readDouble(), par1.readDouble(), par1.readDouble()));
/*    */       }
/* 42 */       this.toRead.put(Integer.valueOf(id), list);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 49 */     par1.writeInt(this.toRead.size());
/* 50 */     for (Map.Entry<Integer, List<AxisAlignedBB>> entry : this.toRead.entrySet()) {
/*    */       
/* 52 */       par1.writeInt(((Integer)entry.getKey()).intValue());
/* 53 */       List<AxisAlignedBB> list = entry.getValue();
/* 54 */       par1.writeInt(list.size());
/* 55 */       for (AxisAlignedBB box : list) {
/*    */         
/* 57 */         par1.writeDouble(box.field_72340_a);
/* 58 */         par1.writeDouble(box.field_72338_b);
/* 59 */         par1.writeDouble(box.field_72339_c);
/* 60 */         par1.writeDouble(box.field_72336_d);
/* 61 */         par1.writeDouble(box.field_72337_e);
/* 62 */         par1.writeDouble(box.field_72334_f);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 70 */     ItemDebug.grids.clear();
/* 71 */     ItemDebug.grids.putAll(this.toRead);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\EnergyGridPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */