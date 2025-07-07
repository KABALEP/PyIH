/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.api.network.INetworkTileEntityEventListener;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileServerEventPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int dimID;
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   int eventID;
/*    */   
/*    */   public TileServerEventPacket() {}
/*    */   
/*    */   public TileServerEventPacket(TileEntity par1, int par2) {
/* 25 */     this.dimID = (par1.func_145831_w()).field_73011_w.field_76574_g;
/* 26 */     this.x = par1.field_145851_c;
/* 27 */     this.y = par1.field_145848_d;
/* 28 */     this.z = par1.field_145849_e;
/* 29 */     this.eventID = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 35 */     this.dimID = par1.readInt();
/* 36 */     this.x = par1.readInt();
/* 37 */     this.y = par1.readInt();
/* 38 */     this.z = par1.readInt();
/* 39 */     this.eventID = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 45 */     par1.writeInt(this.dimID);
/* 46 */     par1.writeInt(this.x);
/* 47 */     par1.writeInt(this.y);
/* 48 */     par1.writeInt(this.z);
/* 49 */     par1.writeInt(this.eventID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 55 */     World world = IC2.platform.getWorld(this.dimID);
/* 56 */     if (world == null)
/*    */     {
/* 58 */       throw new RuntimeException("Packet was sended Wrong");
/*    */     }
/* 60 */     TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/* 61 */     if (!(tile instanceof INetworkTileEntityEventListener))
/*    */     {
/* 63 */       throw new RuntimeException("Packet Contains Wrong Data: ");
/*    */     }
/* 65 */     ((INetworkTileEntityEventListener)tile).onNetworkEvent(this.eventID);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\TileServerEventPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */