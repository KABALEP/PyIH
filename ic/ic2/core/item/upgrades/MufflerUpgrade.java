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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MufflerUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 19 */     return 0.8F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 25 */     return IMachine.UpgradeType.Sounds;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return "mufflerUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 37 */     return Ic2Icons.getTexture("i0")[137];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\MufflerUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */