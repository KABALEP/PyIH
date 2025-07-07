/*    */ package ic2.core.item.boats;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.IHasGui;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectricBoat
/*    */   extends ContainerIC2
/*    */ {
/*    */   IHasGui gui;
/*    */   
/*    */   public ContainerElectricBoat(InventoryPlayer player, IInventory inv, IHasGui entity) {
/* 16 */     this.gui = entity;
/* 17 */     func_75146_a(new Slot(inv, 0, 80, 39));
/*    */     
/*    */     int var3;
/* 20 */     for (var3 = 0; var3 < 3; var3++) {
/*    */       
/* 22 */       for (int var4 = 0; var4 < 9; var4++)
/*    */       {
/* 24 */         func_75146_a(new Slot((IInventory)player, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/*    */       }
/*    */     } 
/*    */     
/* 28 */     for (var3 = 0; var3 < 9; var3++)
/*    */     {
/* 30 */       func_75146_a(new Slot((IInventory)player, var3, 8 + var3 * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 37 */     return 1;
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
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer p_75134_1_) {
/* 55 */     super.func_75134_a(p_75134_1_);
/* 56 */     this.gui.onGuiClosed(p_75134_1_);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\ContainerElectricBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */