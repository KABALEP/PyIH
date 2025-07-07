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
/*    */ public class MuteUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   public IIcon getTexture() {
/* 15 */     return Ic2Icons.getTexture("i0")[138];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 21 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 27 */     return "muteUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 33 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 39 */     return IMachine.UpgradeType.Sounds;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\MuteUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */