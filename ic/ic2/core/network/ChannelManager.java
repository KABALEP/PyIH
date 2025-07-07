/*    */ package ic2.core.network;
/*    */ 
/*    */ import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
/*    */ import ic2.core.network.internal.PayloadPacket;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import ic2.core.network.packets.client.FieldRequestPacket;
/*    */ import ic2.core.network.packets.client.ItemClientEventPacket;
/*    */ import ic2.core.network.packets.client.KeyboardPacket;
/*    */ import ic2.core.network.packets.client.LoginPacket;
/*    */ import ic2.core.network.packets.client.TileClientEventPacket;
/*    */ import ic2.core.network.packets.server.BlockUpdatePacket;
/*    */ import ic2.core.network.packets.server.EnergyGridPacket;
/*    */ import ic2.core.network.packets.server.ExplosionPacket;
/*    */ import ic2.core.network.packets.server.FieldUpdatePacket;
/*    */ import ic2.core.network.packets.server.GuiFieldPacket;
/*    */ import ic2.core.network.packets.server.ItemEventPacket;
/*    */ import ic2.core.network.packets.server.ItemGuiPacket;
/*    */ import ic2.core.network.packets.server.ReactorPlannerEncoderPacket;
/*    */ import ic2.core.network.packets.server.TileGuiPacket;
/*    */ import ic2.core.network.packets.server.TileServerEventPacket;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class ChannelManager
/*    */   extends FMLIndexedMessageToMessageCodec<IC2Packet>
/*    */ {
/*    */   public ChannelManager() {
/* 31 */     addDiscriminator(0, FieldRequestPacket.class);
/* 32 */     addDiscriminator(1, ItemClientEventPacket.class);
/* 33 */     addDiscriminator(2, KeyboardPacket.class);
/* 34 */     addDiscriminator(3, LoginPacket.class);
/* 35 */     addDiscriminator(4, TileClientEventPacket.class);
/* 36 */     addDiscriminator(5, BlockUpdatePacket.class);
/* 37 */     addDiscriminator(6, ExplosionPacket.class);
/* 38 */     addDiscriminator(7, FieldUpdatePacket.class);
/* 39 */     addDiscriminator(8, ItemEventPacket.class);
/* 40 */     addDiscriminator(9, ItemGuiPacket.class);
/* 41 */     addDiscriminator(10, TileGuiPacket.class);
/* 42 */     addDiscriminator(11, TileServerEventPacket.class);
/* 43 */     addDiscriminator(14, EnergyGridPacket.class);
/* 44 */     addDiscriminator(15, GuiFieldPacket.class);
/* 45 */     addDiscriminator(16, PayloadPacket.class);
/* 46 */     addDiscriminator(17, ReactorPlannerEncoderPacket.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, IC2Packet msg, ByteBuf target) throws Exception {
/* 52 */     msg.write(target);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IC2Packet msg) {
/* 58 */     msg.read(source);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\ChannelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */