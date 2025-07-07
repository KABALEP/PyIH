/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RedstoneSensitivityUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public void onInstalling(ItemStack upgrade, IMachine machine) {
/* 15 */     machine.setRedstoneSensitive(!machine.isRedstoneSensitive());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 21 */     return IMachine.UpgradeType.RedstoneControl;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 27 */     return "redstoneSensitivityUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 33 */     return Ic2Icons.getTexture("i0")[131];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\RedstoneSensitivityUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */