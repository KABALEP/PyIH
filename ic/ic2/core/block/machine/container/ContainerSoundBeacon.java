/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.api.item.IMachineUpgradeItem;
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySoundBeacon;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ContainerSoundBeacon
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntitySoundBeacon sound;
/*    */   
/*    */   public ContainerSoundBeacon(TileEntitySoundBeacon par1, InventoryPlayer par2) {
/* 20 */     this.sound = par1;
/* 21 */     int x = 22;
/* 22 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 0, x, 70));
/* 23 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 1, x + 24, 70));
/* 24 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 2, x + 13, 48));
/* 25 */     x = 73;
/* 26 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 3, x, 70));
/* 27 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 4, x + 24, 70));
/* 28 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 5, x + 13, 48));
/* 29 */     x = 125;
/* 30 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 6, x, 70));
/* 31 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 7, x + 24, 70));
/* 32 */     func_75146_a(new SlotSoundBeacon((IInventory)par1, 8, x + 13, 48));
/*    */     
/* 34 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 36 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 38 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 102 + i * 18));
/*    */       }
/*    */     } 
/* 41 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 43 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 160));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 50 */     return 9;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class SlotSoundBeacon
/*    */     extends Slot
/*    */   {
/*    */     public SlotSoundBeacon(IInventory par1, int par2, int par3, int par4) {
/* 70 */       super(par1, par2, par3, par4);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean func_75214_a(ItemStack item) {
/* 76 */       if (item == null)
/*    */       {
/* 78 */         return false;
/*    */       }
/* 80 */       if (item.func_77973_b() instanceof IMachineUpgradeItem)
/*    */       {
/* 82 */         return (((IMachineUpgradeItem)item.func_77973_b()).getType(item) == IMachine.UpgradeType.Sounds);
/*    */       }
/* 84 */       if (item.func_77973_b() instanceof ic2.core.item.ItemChargePadUpgrade) {
/*    */         
/* 86 */         int meta = item.func_77960_j();
/* 87 */         return (meta == 9 || meta == 10 || meta == 11);
/*    */       } 
/* 89 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerSoundBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */