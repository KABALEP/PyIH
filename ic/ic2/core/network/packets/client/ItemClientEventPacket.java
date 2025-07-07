/*    */ package ic2.core.network.packets.client;
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
/*    */ public class ItemClientEventPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   ItemStack stack;
/*    */   int eventID;
/*    */   
/*    */   public ItemClientEventPacket() {}
/*    */   
/*    */   public ItemClientEventPacket(ItemStack par1, int id) {
/* 21 */     this.stack = par1;
/* 22 */     this.eventID = id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 28 */     int id = par1.readInt();
/* 29 */     int meta = par1.readInt();
/* 30 */     this.stack = new ItemStack(Item.func_150899_d(id), 1, meta);
/* 31 */     this.eventID = par1.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 37 */     par1.writeInt(Item.func_150891_b(this.stack.func_77973_b()));
/* 38 */     par1.writeInt(this.stack.func_77960_j());
/* 39 */     par1.writeInt(this.eventID);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 45 */     Item item = this.stack.func_77973_b();
/* 46 */     if (!(item instanceof INetworkItemEventListener))
/*    */     {
/* 48 */       throw new RuntimeException("Wrong packet with a Item that has not the required Classes: " + item);
/*    */     }
/* 50 */     ((INetworkItemEventListener)item).onNetworkEvent(this.stack, par1, this.eventID);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\client\ItemClientEventPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */