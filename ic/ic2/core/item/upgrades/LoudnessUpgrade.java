/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoudnessUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 14 */     return 1.35F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 20 */     return "loudnessUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 26 */     return Ic2Icons.getTexture("i0")[144];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 32 */     return IMachine.UpgradeType.Sounds;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\LoudnessUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */