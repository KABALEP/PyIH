/*    */ package ic2.core.network.packets.client;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyboardPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int keyState;
/*    */   
/*    */   public KeyboardPacket() {}
/*    */   
/*    */   public KeyboardPacket(int par1) {
/* 20 */     this.keyState = par1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 28 */     this.keyState = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 34 */     par1.writeInt(this.keyState);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 40 */     IC2.keyboard.processKeyUpdate(par1, this.keyState);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\client\KeyboardPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */