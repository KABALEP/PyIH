/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.slot.SlotDisplay;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerFluidOMatClosed
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityFluidOMat tileEntity;
/*    */   
/*    */   public ContainerFluidOMatClosed(EntityPlayer par1, TileEntityFluidOMat par2) {
/* 16 */     this.tileEntity = par2;
/* 17 */     IInventory inv = par2.getInventory(par2.getOwner());
/* 18 */     func_75146_a((Slot)new SlotDisplay(inv, 0, 50, 19));
/* 19 */     func_75146_a(new Slot(par2, 0, 143, 17));
/* 20 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 22 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 24 */         func_75146_a(new Slot((IInventory)par1.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 27 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 29 */       func_75146_a(new Slot((IInventory)par1.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 36 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 42 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 48 */     return this.tileEntity.func_70300_a(p0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerFluidOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */