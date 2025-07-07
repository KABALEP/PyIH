/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerFluidOMatOpen
/*    */   extends ContainerIC2
/*    */ {
/*    */   TileEntityFluidOMat tile;
/*    */   
/*    */   public ContainerFluidOMatOpen(EntityPlayer par1, TileEntityFluidOMat par2) {
/* 18 */     this.tile = par2;
/* 19 */     IInventory inv = par2.getInventory(par1);
/* 20 */     func_75146_a(new Slot(inv, 0, 24, 17));
/* 21 */     func_75146_a(new Slot(inv, 1, 24, 53));
/* 22 */     func_75146_a(new Slot(par2, 0, 80, 17));
/* 23 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 25 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 27 */         func_75146_a(new Slot((IInventory)par1.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 30 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 32 */       func_75146_a(new Slot((IInventory)par1.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 39 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 51 */     return this.tile.func_70300_a(p0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ContainerFluidOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */