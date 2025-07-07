/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Items;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityChargePadHV
/*    */   extends TileEntityChargePad
/*    */ {
/*    */   public TileEntityChargePadHV() {
/* 18 */     super(TileEntityChargePad.ChargePadType.HV);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected int getMaxParticalAge() {
/* 25 */     return 14;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected float[] getParticalColour(Random rand) {
/* 32 */     float red = (this.installedUpgrades[TileEntityChargePad.PadUpgrade.Drain.ordinal()] || this.installedUpgrades[TileEntityChargePad.PadUpgrade.Damage.ordinal()]) ? 1.0F : 0.0F;
/* 33 */     float green = this.installedUpgrades[TileEntityChargePad.PadUpgrade.Damage.ordinal()] ? 0.0F : 0.6F;
/* 34 */     return new float[] { red, green * 0.4F, green + rand.nextFloat() * 0.4F };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected double[] getParticleVelocity(Random rand) {
/* 41 */     return new double[] { 0.0D, 7.6D, 0.0D };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected int getParticalAmount(Random rand) {
/* 48 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
/* 54 */     return Ic2Items.hvChargePad.func_77946_l();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityChargePadHV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */