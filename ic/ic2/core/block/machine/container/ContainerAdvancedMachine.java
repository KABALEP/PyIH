/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityAdvancedMachine;
/*    */ import ic2.core.slot.SlotUpgrade;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerAdvancedMachine
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityAdvancedMachine machine;
/*    */   
/*    */   public ContainerAdvancedMachine(TileEntityAdvancedMachine par1, InventoryPlayer par2) {
/* 26 */     this.machine = par1;
/* 27 */     int x = 0;
/* 28 */     for (Slot slot : par1.getInvSlots(par2)) {
/*    */       
/* 30 */       func_75146_a(slot);
/* 31 */       x++;
/*    */     } 
/*    */     int i;
/* 34 */     for (i = 0; i < 2; i++)
/*    */     {
/* 36 */       func_75146_a((Slot)new SlotUpgrade((IMachine)par1, x + i, 152, 8 + i * 18));
/*    */     }
/* 38 */     for (i = 0; i < 3; i++) {
/*    */       
/* 40 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 42 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 46 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 48 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 55 */     return this.machine.func_70302_i_();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 61 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 67 */     return this.machine.func_70300_a(p0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerAdvancedMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */