/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerPersonalChest
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityPersonalChest tileEntity;
/*    */   
/*    */   public ContainerPersonalChest(EntityPlayer entityPlayer, TileEntityPersonalChest tileEntity) {
/* 14 */     this.tileEntity = tileEntity;
/* 15 */     IInventory inv = tileEntity.inv;
/* 16 */     tileEntity.openInventory(); int y;
/* 17 */     for (y = 0; y < 6; y++) {
/*    */       
/* 19 */       for (int x = 0; x < 9; x++)
/*    */       {
/* 21 */         func_75146_a(new Slot(inv, x + y * 9, 8 + x * 18, 18 + y * 18));
/*    */       }
/*    */     } 
/* 24 */     for (y = 0; y < 3; y++) {
/*    */       
/* 26 */       for (int x = 0; x < 9; x++)
/*    */       {
/* 28 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, 9 + x + y * 9, 8 + x * 18, 140 + y * 18));
/*    */       }
/*    */     } 
/* 31 */     for (int x2 = 0; x2 < 9; x2++)
/*    */     {
/* 33 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, x2, 8 + x2 * 18, 198));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75137_b(int index, int value) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 45 */     return this.tileEntity.canAccess(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 51 */     return 54;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 57 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer entityplayer) {
/* 62 */     this.tileEntity.closeInventory();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */