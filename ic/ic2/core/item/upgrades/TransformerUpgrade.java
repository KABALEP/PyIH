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
/*    */ public class TransformerUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public int getExtraTier(ItemStack upgrade, IMachine machine) {
/* 15 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 21 */     return "transformerUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 27 */     return Ic2Icons.getTexture("i0")[129];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 33 */     return IMachine.UpgradeType.MachineModifierB;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\TransformerUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */