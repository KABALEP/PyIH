/*    */ package ic2.core.network.packets.client;
/*    */ 
/*    */ import ic2.core.item.armor.ItemArmorQuantumSuit;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   boolean quantum;
/*    */   
/*    */   public LoginPacket() {}
/*    */   
/*    */   public LoginPacket(boolean par1) {
/* 27 */     this.quantum = par1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 33 */     this.quantum = par1.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 39 */     par1.writeBoolean(this.quantum);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 45 */     ItemArmorQuantumSuit.enableQuantumSpeedOnSprintMap.put(par1, Boolean.valueOf(this.quantum));
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\client\LoginPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */