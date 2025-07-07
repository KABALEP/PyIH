/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OverclockerUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public double getProcessTimeMultiplier(ItemStack upgrade, IMachine machine) {
/* 14 */     return 0.7D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/* 20 */     return 1.6D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return "overclockerUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 32 */     return IMachine.UpgradeType.MachineModifierA;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 38 */     return Ic2Icons.getTexture("i0")[128];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\OverclockerUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */