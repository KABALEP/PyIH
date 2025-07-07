/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.item.IHandHeldInventory;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGuiPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int currentItem;
/*    */   int windowID;
/*    */   boolean entity;
/*    */   
/*    */   public ItemGuiPacket() {}
/*    */   
/*    */   public ItemGuiPacket(int par1, int par2, boolean par3) {
/* 25 */     this.currentItem = par1;
/* 26 */     this.windowID = par2;
/* 27 */     this.entity = par3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 33 */     this.currentItem = par1.readInt();
/* 34 */     this.windowID = par1.readInt();
/* 35 */     this.entity = par1.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 41 */     par1.writeInt(this.currentItem);
/* 42 */     par1.writeInt(this.windowID);
/* 43 */     par1.writeBoolean(this.entity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 49 */     if (this.entity) {
/*    */       
/* 51 */       handleEntity(par1);
/*    */     }
/*    */     else {
/*    */       
/* 55 */       handleItem(par1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleEntity(EntityPlayer player) {
/* 61 */     if (player.field_70170_p == null) {
/*    */       return;
/*    */     }
/*    */     
/* 65 */     Entity entity = player.field_70170_p.func_73045_a(this.currentItem);
/* 66 */     if (entity != null && entity instanceof IHasGui)
/*    */     {
/* 68 */       IC2.platform.launchGuiClient(player, (IHasGui)entity);
/*    */     }
/* 70 */     player.field_71070_bA.field_75152_c = this.windowID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleItem(EntityPlayer par1) {
/* 75 */     if (par1.field_71071_by.field_70461_c != this.currentItem) {
/*    */       return;
/*    */     }
/*    */     
/* 79 */     ItemStack currentItem = par1.field_71071_by.func_70448_g();
/* 80 */     if (currentItem != null && currentItem.func_77973_b() instanceof IHandHeldInventory)
/*    */     {
/* 82 */       IC2.platform.launchGuiClient(par1, ((IHandHeldInventory)currentItem.func_77973_b()).getInventory(par1, currentItem));
/*    */     }
/* 84 */     par1.field_71070_bA.field_75152_c = this.windowID;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\ItemGuiPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */