/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileGuiPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int dimID;
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   int windowID;
/*    */   
/*    */   public TileGuiPacket() {}
/*    */   
/*    */   public TileGuiPacket(TileEntity par1, int par2) {
/* 25 */     this.dimID = (par1.func_145831_w()).field_73011_w.field_76574_g;
/* 26 */     this.x = par1.field_145851_c;
/* 27 */     this.y = par1.field_145848_d;
/* 28 */     this.z = par1.field_145849_e;
/* 29 */     this.windowID = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 36 */     this.dimID = par1.readInt();
/* 37 */     this.x = par1.readInt();
/* 38 */     this.y = par1.readInt();
/* 39 */     this.z = par1.readInt();
/* 40 */     this.windowID = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 46 */     par1.writeInt(this.dimID);
/* 47 */     par1.writeInt(this.x);
/* 48 */     par1.writeInt(this.y);
/* 49 */     par1.writeInt(this.z);
/* 50 */     par1.writeInt(this.windowID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 56 */     World world = IC2.platform.getWorld(this.dimID);
/* 57 */     if (world == null)
/*    */     {
/* 59 */       throw new RuntimeException("Packet Contains Incorrect data");
/*    */     }
/* 61 */     TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/* 62 */     if (tile instanceof IHasGui)
/*    */     {
/* 64 */       IC2.platform.launchGuiClient(par1, (IHasGui)tile);
/*    */     }
/* 66 */     par1.field_71070_bA.field_75152_c = this.windowID;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\TileGuiPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */