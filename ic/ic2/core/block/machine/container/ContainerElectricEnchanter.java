/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricEnchanter;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerElectricEnchanter
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityElectricEnchanter tile;
/* 20 */   public float lastChargeLevel = -1.0F;
/* 21 */   public float lastProgress = -1.0F;
/* 22 */   public int lastExp = -1;
/* 23 */   public int lastNeeded = -1;
/*    */ 
/*    */   
/*    */   public ContainerElectricEnchanter(TileEntityElectricEnchanter tile, InventoryPlayer player) {
/* 27 */     this.tile = tile;
/* 28 */     Object[] books = { Items.field_151122_aG, Items.field_151134_bR };
/*    */     
/* 30 */     func_75146_a((Slot)new SlotDischarge((IInventory)tile, 3, 0, 9, 49));
/* 31 */     func_75146_a((Slot)new SlotCustom((IInventory)tile, books, 1, 46, 13));
/* 32 */     func_75146_a((Slot)new SlotCustom((IInventory)tile, books, 2, 64, 13));
/* 33 */     func_75146_a((Slot)new SlotCustom((IInventory)tile, books, 3, 82, 13));
/* 34 */     func_75146_a(new Slot((IInventory)tile, 4, 64, 36));
/* 35 */     func_75146_a((Slot)new SlotOutput(player.field_70458_d, (IInventory)tile, 5, 126, 36));
/* 36 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 38 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 40 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 43 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 45 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 52 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 58 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 64 */     return this.tile.func_70300_a(p0);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerElectricEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */