/*    */ package ic2.core.item.upgrades;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.energy.tile.IEnergyContainer;
/*    */ import ic2.api.energy.tile.IEnergySink;
/*    */ import ic2.api.tile.IMachine;
/*    */ import ic2.core.Ic2Icons;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreativeUpgrade
/*    */   extends BaseMetaUpgrade
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void addInfo(ItemStack par1, EntityPlayer par2, List<String> par3) {
/* 25 */     par3.add(StatCollector.func_74838_a("itemInfo.creativeOnly.name"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/* 31 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getProcessTimeMultiplier(ItemStack upgrade, IMachine machine) {
/* 37 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExtraTier(ItemStack upgrade, IMachine machine) {
/* 43 */     return 12;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExtraEnergyStorage(ItemStack upgrade, IMachine machine) {
/* 49 */     return 1073741823;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/* 55 */     if (machine instanceof IEnergySink) {
/*    */       
/* 57 */       IEnergySink sink = (IEnergySink)machine;
/* 58 */       double needed = sink.getDemandedEnergy();
/* 59 */       if (needed > 1.0D) {
/*    */         
/* 61 */         if (machine instanceof IEnergyContainer)
/*    */         {
/* 63 */           needed = Math.min(needed, ((IEnergyContainer)machine).getMaxEnergyInput());
/*    */         }
/* 65 */         sink.injectEnergy(ForgeDirection.UNKNOWN, needed, 0.0D);
/*    */       } 
/*    */     } 
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean needsTick() {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMachine.UpgradeType getType() {
/* 80 */     return IMachine.UpgradeType.Custom;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 86 */     return "creativeUpgrade";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 92 */     return Ic2Icons.getTexture("i0")[139];
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\CreativeUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */