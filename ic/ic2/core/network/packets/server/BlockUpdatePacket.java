/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockUpdatePacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int dimID;
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   int blockID;
/*    */   int meta;
/*    */   
/*    */   public BlockUpdatePacket() {}
/*    */   
/*    */   public BlockUpdatePacket(World world, int par1, int par2, int par3) {
/* 26 */     this.dimID = world.field_73011_w.field_76574_g;
/* 27 */     this.x = par1;
/* 28 */     this.y = par2;
/* 29 */     this.z = par3;
/* 30 */     this.blockID = Block.func_149682_b(world.func_147439_a(par1, par2, par3));
/* 31 */     this.meta = world.func_72805_g(par1, par2, par3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 38 */     this.dimID = par1.readInt();
/* 39 */     this.x = par1.readInt();
/* 40 */     this.y = par1.readInt();
/* 41 */     this.z = par1.readInt();
/* 42 */     this.blockID = par1.readInt();
/* 43 */     this.meta = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 49 */     par1.writeInt(this.dimID);
/* 50 */     par1.writeInt(this.x);
/* 51 */     par1.writeInt(this.y);
/* 52 */     par1.writeInt(this.z);
/* 53 */     par1.writeInt(this.blockID);
/* 54 */     par1.writeInt(this.meta);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 60 */     World world = IC2.platform.getWorld(this.dimID);
/* 61 */     if (world == null)
/*    */     {
/* 63 */       throw new RuntimeException("Packet Contains Incorrect data");
/*    */     }
/* 65 */     world.func_147465_d(this.x, this.y, this.z, Block.func_149729_e(this.blockID), this.meta, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\BlockUpdatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */