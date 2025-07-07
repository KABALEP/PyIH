/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerTradeOMatOpen
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityTradeOMat tileEntity;
/*    */   
/*    */   public ContainerTradeOMatOpen(EntityPlayer entityPlayer, TileEntityTradeOMat tileEntity) {
/* 16 */     this.tileEntity = tileEntity;
/* 17 */     IInventory inv = tileEntity.getInventory(entityPlayer);
/* 18 */     func_75146_a(new Slot(inv, 0, 24, 17));
/* 19 */     func_75146_a(new Slot(inv, 1, 24, 53));
/* 20 */     func_75146_a(new Slot(tileEntity, 0, 80, 17));
/* 21 */     func_75146_a(new Slot(tileEntity, 1, 80, 53));
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


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerTradeOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */