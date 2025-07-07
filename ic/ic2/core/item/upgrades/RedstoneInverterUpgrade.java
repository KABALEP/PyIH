/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RedstoneInverterUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public boolean useRedstoneinverter(ItemStack upgrade, IMachine machine) {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 20 */     return "redstoneInverterUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 26 */     return IMachine.UpgradeType.RedstoneControl;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 32 */     return Ic2Icons.getTexture("i0")[132];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\RedstoneInverterUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */