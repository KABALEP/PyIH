/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCharged;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerChargedElectrolyzer
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityCharged tileEntity;
/*    */   
/*    */   public ContainerChargedElectrolyzer(EntityPlayer entityPlayer, TileEntityCharged tileEntity) {
/* 16 */     this.tileEntity = tileEntity;
/* 17 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 54, 35));
/* 18 */     func_75146_a(new Slot((IInventory)tileEntity, 1, 112, 35));
/* 19 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 21 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 23 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 26 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 28 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 35 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 41 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 47 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerChargedElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */