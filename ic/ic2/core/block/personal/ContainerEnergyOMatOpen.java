/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerEnergyOMatOpen
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityEnergyOMat tileEntity;
/*    */   
/*    */   public ContainerEnergyOMatOpen(EntityPlayer entityPlayer, TileEntityEnergyOMat tileEntity) {
/* 15 */     this.tileEntity = tileEntity;
/* 16 */     IInventory inv = tileEntity.getInventory(entityPlayer);
/* 17 */     func_75146_a(new Slot(inv, 0, 24, 17));
/* 18 */     func_75146_a(new Slot(inv, 1, 24, 53));
/* 19 */     func_75146_a(new Slot(tileEntity, 0, 80, 17));
/* 20 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 22 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 24 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 27 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 29 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 36 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 42 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 48 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerEnergyOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */