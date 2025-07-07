/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectrolyzer
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityElectrolyzer tileEntity;
/*    */   
/*    */   public ContainerElectrolyzer(EntityPlayer entityPlayer, TileEntityElectrolyzer tileEntity) {
/* 15 */     this.tileEntity = tileEntity;
/* 16 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 54, 35));
/* 17 */     func_75146_a(new Slot((IInventory)tileEntity, 1, 112, 35));
/* 18 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 20 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 22 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 25 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 27 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 34 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 40 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 46 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */