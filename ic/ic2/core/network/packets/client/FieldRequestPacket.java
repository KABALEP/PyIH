/*    */ package ic2.core.network.packets.client;
/*    */ 
/*    */ import ic2.api.network.INetworkDataProvider;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldRequestPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int dimID;
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   
/*    */   public FieldRequestPacket() {}
/*    */   
/*    */   public FieldRequestPacket(TileEntity tile) {
/* 30 */     this.dimID = (tile.func_145831_w()).field_73011_w.field_76574_g;
/* 31 */     this.x = tile.field_145851_c;
/* 32 */     this.y = tile.field_145848_d;
/* 33 */     this.z = tile.field_145849_e;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 40 */     this.dimID = par1.readInt();
/* 41 */     this.x = par1.readInt();
/* 42 */     this.y = par1.readInt();
/* 43 */     this.z = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 49 */     par1.writeInt(this.dimID);
/* 50 */     par1.writeInt(this.x);
/* 51 */     par1.writeInt(this.y);
/* 52 */     par1.writeInt(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 58 */     World world = IC2.platform.getWorld(this.dimID);
/* 59 */     if (world == null)
/*    */     {
/* 61 */       throw new RuntimeException("Packet was sended Wrong: " + this.dimID + ":" + this.x + ":" + this.y + ":" + this.z);
/*    */     }
/* 63 */     TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/* 64 */     if (tile != null) {
/*    */       
/* 66 */       List<String> networkFields = ((INetworkDataProvider)tile).getNetworkedFields();
/* 67 */       for (String s : networkFields)
/*    */       {
/* 69 */         ((NetworkManager)IC2.network.get()).updateTileEntityField(tile, s);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\client\FieldRequestPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */