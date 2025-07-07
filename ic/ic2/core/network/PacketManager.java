/*    */ package ic2.core.network;
/*    */ 
/*    */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*    */ import cpw.mods.fml.common.network.FMLOutboundHandler;
/*    */ import cpw.mods.fml.common.network.NetworkRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.SimpleChannelInboundHandler;
/*    */ import java.util.EnumMap;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class PacketManager
/*    */   extends SimpleChannelInboundHandler<IC2Packet>
/*    */ {
/* 24 */   public static PacketManager instance = new PacketManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   private EnumMap<Side, FMLEmbeddedChannel> channel = NetworkRegistry.INSTANCE.newChannel("ic2", new ChannelHandler[] { (ChannelHandler)new ChannelManager(), (ChannelHandler)this });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void channelRead0(ChannelHandlerContext ctx, IC2Packet msg) throws Exception {
/*    */     try {
/* 38 */       INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 39 */       EntityPlayer player = getPlayer(netHandler);
/* 40 */       if (player == null)
/*    */       {
/* 42 */         throw new RuntimeException("A case which never should happen did happen please Send a message to the ModAuthor and tell him how that Happend!");
/*    */       }
/* 44 */       msg.handlePacket(player);
/*    */     
/*    */     }
/* 47 */     catch (Exception e) {
/*    */       
/* 49 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer getPlayer(INetHandler handler) {
/* 55 */     if (handler instanceof NetHandlerPlayServer)
/*    */     {
/* 57 */       return (EntityPlayer)((NetHandlerPlayServer)handler).field_147369_b;
/*    */     }
/* 59 */     return IC2.platform.getPlayerInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendToPlayer(IC2Packet par1, EntityPlayer par2) {
/* 64 */     ((FMLEmbeddedChannel)this.channel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
/* 65 */     ((FMLEmbeddedChannel)this.channel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(par2);
/* 66 */     ((FMLEmbeddedChannel)this.channel.get(Side.SERVER)).writeOutbound(new Object[] { par1 });
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendToServer(IC2Packet par1) {
/* 71 */     ((FMLEmbeddedChannel)this.channel.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/* 72 */     ((FMLEmbeddedChannel)this.channel.get(Side.CLIENT)).writeOutbound(new Object[] { par1 });
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\PacketManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */