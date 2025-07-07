/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.api.item.IScannerItem;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityOreScanner;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.slot.SlotDisplay;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerOreScanner extends ContainerIC2 {
/*    */   public TileEntityOreScanner scanner;
/*    */   
/*    */   public ContainerOreScanner(InventoryPlayer player, TileEntityOreScanner par2) {
/* 19 */     this.scanner = par2;
/* 20 */     func_75146_a((Slot)new SlotDischarge((IInventory)par2, 0, 151, 9));
/* 21 */     func_75146_a((Slot)new SlotCustom((IInventory)par2, new Object[] { IScannerItem.class }, 1, 7, 9));
/* 22 */     func_75146_a((Slot)new SlotDisplay((IInventory)par2.inv, 0, 10, 32));
/* 23 */     func_75146_a((Slot)new SlotDisplay((IInventory)par2.inv, 1, 10, 50));
/* 24 */     func_75146_a((Slot)new SlotDisplay((IInventory)par2.inv, 2, 10, 68));
/* 25 */     func_75146_a((Slot)new SlotDisplay((IInventory)par2.inv, 3, 10, 86));
/* 26 */     func_75146_a((Slot)new SlotDisplay((IInventory)par2.inv, 4, 10, 104));
/* 27 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 29 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 31 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 140 + i * 18));
/*    */       }
/*    */     } 
/* 34 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 36 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 198));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 43 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer par1) {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_75144_a(int slotID, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_) {
/* 61 */     if (slotID >= 0 && slotID < this.field_75151_b.size()) {
/*    */       
/* 63 */       Slot slot = func_75139_a(slotID);
/* 64 */       if (slot instanceof SlotDisplay)
/*    */       {
/* 66 */         return null;
/*    */       }
/*    */     } 
/* 69 */     return super.func_75144_a(slotID, p_75144_2_, p_75144_3_, p_75144_4_);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerOreScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */