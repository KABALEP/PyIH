/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.inventory.SlotFurnace;
/*    */ 
/*    */ public class ContainerIronFurnace
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityIronFurnace tileEntity;
/*    */   
/*    */   public ContainerIronFurnace(EntityPlayer entityPlayer, TileEntityIronFurnace tileEntity) {
/* 16 */     this.tileEntity = tileEntity;
/* 17 */     func_75146_a(new Slot((IInventory)tileEntity, 0, 56, 17));
/* 18 */     func_75146_a(new Slot((IInventory)tileEntity, 1, 56, 53));
/* 19 */     func_75146_a((Slot)new SlotFurnace(entityPlayer, (IInventory)tileEntity, 2, 116, 35));
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
/* 48 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerIronFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */