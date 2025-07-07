/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import ic2.core.slot.SlotUpgrade;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.inventory.SlotFurnace;
/*    */ 
/*    */ public class ContainerElectricMachine
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityElectricMachine tileEntity;
/*    */   
/*    */   public ContainerElectricMachine(EntityPlayer entityPlayer, TileEntityElectricMachine tileEntity) {
/* 20 */     this.tileEntity = tileEntity;
/* 21 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 56, 17));
/* 22 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, 2147483647, 1, 56, 53));
/* 23 */     if (tileEntity instanceof ic2.core.block.machine.tileentity.TileEntityElecFurnace) {
/*    */       
/* 25 */       func_75146_a((Slot)new SlotFurnace(entityPlayer, (IInventory)tileEntity, 2, 116, 35));
/*    */     }
/*    */     else {
/*    */       
/* 29 */       func_75146_a((Slot)new SlotOutput(entityPlayer, (IInventory)tileEntity, 2, 116, 35));
/*    */     }  int i;
/* 31 */     for (i = 0; i < 4; i++)
/*    */     {
/* 33 */       func_75146_a((Slot)new SlotUpgrade((IMachine)tileEntity, 3 + i, 152, 8 + i * 18));
/*    */     }
/* 35 */     for (i = 0; i < 3; i++) {
/*    */       
/* 37 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 39 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 42 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 44 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 51 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 57 */     return 7;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 63 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerElectricMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */