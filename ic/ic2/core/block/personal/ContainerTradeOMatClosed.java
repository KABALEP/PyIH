/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.slot.SlotDisplay;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerTradeOMatClosed
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityTradeOMat tileEntity;
/*    */   
/*    */   public ContainerTradeOMatClosed(EntityPlayer entityPlayer, TileEntityTradeOMat tileEntity) {
/* 16 */     (this.tileEntity = tileEntity).updateStock();
/* 17 */     IInventory inv = tileEntity.getInventory(tileEntity.getOwner());
/* 18 */     func_75146_a((Slot)new SlotDisplay(inv, 0, 50, 19));
/* 19 */     func_75146_a((Slot)new SlotDisplay(inv, 1, 50, 38));
/* 20 */     func_75146_a(new Slot(tileEntity, 0, 143, 17));
/* 21 */     func_75146_a(new Slot(tileEntity, 1, 143, 53));
/* 22 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 24 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 26 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 29 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 31 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 38 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 44 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 50 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerTradeOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */