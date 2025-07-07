/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EfficenyUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/* 14 */     return 0.95D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 20 */     return 16;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 26 */     return IMachine.UpgradeType.MachineModifierA;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return "efficencyUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 38 */     return Ic2Icons.getTexture("i0")[141];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\EfficenyUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */