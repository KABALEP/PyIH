/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyMultiplierUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public double getEnergyStorageMultiplier(ItemStack upgrade, IMachine machine) {
/* 14 */     return 2.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 20 */     return IMachine.UpgradeType.MachineModifierB;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return "energyMultiplierUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 32 */     return Ic2Icons.getTexture("i0")[142];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 38 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\EnergyMultiplierUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */