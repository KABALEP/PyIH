/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCropScanner;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCropScanner
/*    */   extends ContainerIC2 {
/*    */   public TileEntityCropScanner tileEntity;
/*    */   
/*    */   public ContainerCropScanner(InventoryPlayer player, TileEntityCropScanner scanner) {
/* 17 */     this.tileEntity = scanner;
/* 18 */     func_75146_a((Slot)new SlotDischarge((IInventory)this.tileEntity, 2147483647, 0, 56, 53));
/* 19 */     func_75146_a(new Slot((IInventory)this.tileEntity, 1, 56, 17));
/* 20 */     func_75146_a((Slot)new SlotOutput(player.field_70458_d, (IInventory)this.tileEntity, 2, 116, 35));
/*    */     
/* 22 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 24 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 26 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 29 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 31 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 38 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerCropScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */