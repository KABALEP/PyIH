/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.api.network.INetworkItemEventListener;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemEventPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   ItemStack stack;
/*    */   int eventID;
/*    */   
/*    */   public ItemEventPacket() {}
/*    */   
/*    */   public ItemEventPacket(ItemStack par1, int par2) {
/* 23 */     this.stack = par1;
/* 24 */     this.eventID = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 31 */     int id = par1.readInt();
/* 32 */     int meta = par1.readInt();
/* 33 */     this.stack = new ItemStack(Item.func_150899_d(id), 1, meta);
/* 34 */     this.eventID = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 40 */     par1.writeInt(Item.func_150891_b(this.stack.func_77973_b()));
/* 41 */     par1.writeInt(this.stack.func_77960_j());
/* 42 */     par1.writeInt(this.eventID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 48 */     Item item = this.stack.func_77973_b();
/* 49 */     if (!(item instanceof INetworkItemEventListener))
/*    */     {
/* 51 */       throw new RuntimeException("Packet contains Incorrect Data");
/*    */     }
/* 53 */     ((INetworkItemEventListener)item).onNetworkEvent(this.stack, par1, this.eventID);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\ItemEventPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */