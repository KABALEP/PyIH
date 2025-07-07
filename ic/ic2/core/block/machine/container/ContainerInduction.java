/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityInduction;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.inventory.SlotFurnace;
/*    */ 
/*    */ public class ContainerInduction
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityInduction tileEntity;
/*    */   
/*    */   public ContainerInduction(EntityPlayer entityPlayer, TileEntityInduction tileEntity) {
/* 17 */     this.tileEntity = tileEntity;
/* 18 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 47, 17));
/* 19 */     func_75146_a(new Slot((IInventory)tileEntity, 1, 63, 17));
/* 20 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, tileEntity.tier, 2, 56, 53));
/* 21 */     func_75146_a((Slot)new SlotFurnace(entityPlayer, (IInventory)tileEntity, 3, 113, 35));
/* 22 */     func_75146_a((Slot)new SlotFurnace(entityPlayer, (IInventory)tileEntity, 4, 131, 35));
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
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 39 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 45 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 51 */     if (this.tileEntity.func_70301_a(0) != null)
/*    */     {
/* 53 */       return 1;
/*    */     }
/* 55 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerInduction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */