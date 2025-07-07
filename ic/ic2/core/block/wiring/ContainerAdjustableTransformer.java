/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerAdjustableTransformer
/*    */   extends ContainerIC2 {
/*    */   public TileEntityAdjustableTransformer tile;
/*    */   
/*    */   public ContainerAdjustableTransformer(TileEntityAdjustableTransformer par1, InventoryPlayer par2) {
/* 14 */     this.tile = par1;
/* 15 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 17 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 19 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 62 + i * 18));
/*    */       }
/*    */     } 
/* 22 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 24 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 120));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 31 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 37 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\ContainerAdjustableTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */