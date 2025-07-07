/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.item.IMachineUpgradeItem;
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityAdvancedMachine;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*    */ import ic2.core.block.wiring.TileEntityChargePad;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotUpgrade
/*    */   extends Slot
/*    */ {
/*    */   IMachine machine;
/*    */   TileEntity owner;
/*    */   
/*    */   public SlotUpgrade(IInventory par1, int par2, int par3, int par4, IMachine par5, TileEntity par6) {
/* 23 */     super(par1, par2, par3, par4);
/* 24 */     this.machine = par5;
/* 25 */     this.owner = par6;
/*    */   }
/*    */ 
/*    */   
/*    */   public SlotUpgrade(IMachine par1, int par2, int par3, int par4) {
/* 30 */     super((IInventory)par1, par2, par3, par4);
/* 31 */     this.machine = par1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack par1) {
/* 36 */     if (par1 != null && par1.func_77973_b() instanceof IMachineUpgradeItem) {
/*    */       
/* 38 */       IMachineUpgradeItem item = (IMachineUpgradeItem)par1.func_77973_b();
/* 39 */       IMachine.UpgradeType type = item.getType(par1);
/* 40 */       if (type != null && this.machine.getSupportedTypes().contains(type))
/*    */       {
/* 42 */         return true;
/*    */       }
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75218_e() {
/* 50 */     if (IC2.platform.isSimulating()) {
/*    */       
/* 52 */       if (this.machine instanceof TileEntityElectricMachine)
/*    */       {
/* 54 */         ((TileEntityElectricMachine)this.machine).setOverclockRates();
/*    */       }
/* 56 */       if (this.machine instanceof TileEntityAdvancedMachine)
/*    */       {
/* 58 */         ((TileEntityAdvancedMachine)this.machine).setOverclockRates();
/*    */       }
/* 60 */       if (this.machine instanceof ic2.core.block.machine.tileentity.FakeMachine)
/*    */       {
/* 62 */         if (this.owner instanceof TileEntityChargePad)
/*    */         {
/* 64 */           ((TileEntityChargePad)this.owner).updateUpgrades();
/*    */         }
/*    */       }
/*    */     } 
/* 68 */     super.func_75218_e();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */