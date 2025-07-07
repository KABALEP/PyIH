/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.inventory.SlotFurnace;
/*    */ 
/*    */ 
/*    */ public class ContainerCanner
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityCanner tileEntity;
/*    */   
/*    */   public ContainerCanner(EntityPlayer entityPlayer, TileEntityCanner tileEntity) {
/* 18 */     this.tileEntity = tileEntity;
/* 19 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 69, 17));
/* 20 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, 1, 30, 45));
/* 21 */     func_75146_a((Slot)new SlotFurnace(entityPlayer, (IInventory)tileEntity, 2, 119, 35));
/* 22 */     func_75146_a(new Slot((IInventory)tileEntity, 3, 69, 53));
/* 23 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 25 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 27 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 30 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 32 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 41 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 47 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 53 */     return firstEmptyFrom(0, 2, (IInventory)this.tileEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */