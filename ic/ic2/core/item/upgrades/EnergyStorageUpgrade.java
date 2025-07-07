/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyStorageUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public int getExtraEnergyStorage(ItemStack upgrade, IMachine machine) {
/* 14 */     return 10000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 20 */     return "energyStorageUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 26 */     return Ic2Icons.getTexture("i0")[130];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 32 */     return IMachine.UpgradeType.MachineModifierB;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\EnergyStorageUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */