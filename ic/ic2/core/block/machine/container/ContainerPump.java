/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityPump;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerPump
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityPump tileEntity;
/*    */   
/*    */   public ContainerPump(EntityPlayer entityPlayer, TileEntityPump tileEntity) {
/* 20 */     this.tileEntity = tileEntity;
/* 21 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Ic2Items.cell, Items.field_151133_ar }, 0, 62, 17));
/* 22 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, tileEntity.tier, 1, 62, 53));
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
/* 45 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 51 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerPump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */