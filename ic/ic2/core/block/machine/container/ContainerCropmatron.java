/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCropmatron;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCropmatron
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityCropmatron tileEntity;
/*    */   
/*    */   public ContainerCropmatron(EntityPlayer entityPlayer, TileEntityCropmatron tileEntity) {
/* 17 */     this.tileEntity = tileEntity;
/* 18 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.fertilizer.func_77973_b() }, 0, 62, 20));
/* 19 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.fertilizer.func_77973_b() }, 1, 62, 38));
/* 20 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.fertilizer.func_77973_b() }, 2, 62, 56));
/* 21 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.hydratingCell.func_77973_b() }, 3, 98, 20));
/* 22 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.hydratingCell.func_77973_b() }, 4, 98, 38));
/* 23 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.hydratingCell.func_77973_b() }, 5, 98, 56));
/* 24 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.weedEx.func_77973_b() }, 6, 134, 20));
/* 25 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.weedEx.func_77973_b() }, 7, 134, 38));
/* 26 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.weedEx.func_77973_b() }, 8, 134, 56));
/* 27 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 29 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 31 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 34 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 36 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 43 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 49 */     return 9;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 55 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerCropmatron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */