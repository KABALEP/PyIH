/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityUraniumEnricher;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerUraniumEnricher
/*    */   extends ContainerIC2 {
/*    */   public TileEntityUraniumEnricher tile;
/*    */   
/*    */   public ContainerUraniumEnricher(TileEntityUraniumEnricher par1, InventoryPlayer player) {
/* 20 */     this.tile = par1;
/* 21 */     func_75146_a((Slot)new SlotCustom((IInventory)par1, new Object[] { Ic2Items.uraniumIngot }, 0, 81, 58));
/* 22 */     func_75146_a((Slot)new SlotCustom((IInventory)par1, new Object[] { Items.field_151137_ax, Items.field_151079_bi, Items.field_151072_bj, Items.field_151156_bN, new ItemStack(Items.field_151044_h, 1, 1) }1, 45, 27));
/* 23 */     func_75146_a((Slot)new SlotOutput(player.field_70458_d, (IInventory)par1, 2, 152, 43));
/* 24 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 26 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 28 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 31 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 33 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 41 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 47 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerUraniumEnricher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */