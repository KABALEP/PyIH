/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.StatCollector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReactorPlannerEncoderPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   String message;
/*    */   
/*    */   public ReactorPlannerEncoderPacket() {}
/*    */   
/*    */   public ReactorPlannerEncoderPacket(String encoded) {
/* 23 */     this.message = encoded;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 29 */     this.message = readString(par1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 35 */     writeString(par1, this.message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 41 */     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.message), null);
/* 42 */     IC2.platform.messagePlayer(par1, StatCollector.func_74838_a("container.reactorplannerAction.exportedToClipboard.name"));
/*    */   }
/*    */ 
/*    */   
/*    */   private String readString(ByteBuf par1) {
/* 47 */     byte[] array = new byte[par1.readInt()];
/* 48 */     par1.readBytes(array);
/* 49 */     return new String(array);
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeString(ByteBuf par1, String text) {
/* 54 */     byte[] array = text.getBytes();
/* 55 */     par1.writeInt(array.length);
/* 56 */     par1.writeBytes(array);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\ReactorPlannerEncoderPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */