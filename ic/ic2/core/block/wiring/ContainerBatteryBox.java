/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.slot.SlotCharge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerBatteryBox
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityBatteryBox box;
/*    */   
/*    */   public ContainerBatteryBox(TileEntityBatteryBox par1, InventoryPlayer par2) {
/* 16 */     this.box = par1; int i;
/* 17 */     for (i = 0; i < 9; i++)
/*    */     {
/* 19 */       func_75146_a((Slot)new SlotCharge((IInventory)par1, i, 8 + i * 18, 25));
/*    */     }
/* 21 */     for (i = 0; i < 3; i++) {
/*    */       
/* 23 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 25 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 102 + i * 18));
/*    */       }
/*    */     } 
/* 28 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 30 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 160));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 37 */     return 9;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 43 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\ContainerBatteryBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */