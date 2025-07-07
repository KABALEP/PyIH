/*    */ package ic2.bcIntigration.core;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerProvider;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggerHandler
/*    */   implements ITriggerProvider
/*    */ {
/*    */   public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection arg0, TileEntity tile) {
/* 26 */     ArrayList<ITriggerExternal> external = new ArrayList<>();
/* 27 */     if (tile instanceof ic2.core.block.machine.tileentity.TileEntityElectricMachine || tile instanceof ic2.core.block.generator.tileentity.TileEntityBaseGenerator || tile instanceof ic2.core.block.wiring.TileEntityElectricBlock) {
/*    */       
/* 29 */       external.add(BuildcraftModul.triggerCapacitorEmpty);
/* 30 */       external.add(BuildcraftModul.triggerCapacitorHasEnergy);
/* 31 */       external.add(BuildcraftModul.triggerCapacitorHasRoom);
/* 32 */       external.add(BuildcraftModul.triggerCapacitorFull);
/*    */     } 
/* 34 */     if (tile instanceof ic2.core.block.generator.tileentity.TileEntityBaseGenerator || tile instanceof ic2.core.block.wiring.TileEntityElectricBlock) {
/*    */       
/* 36 */       external.add(BuildcraftModul.triggerChargeEmpty);
/* 37 */       external.add(BuildcraftModul.triggerChargePartial);
/* 38 */       external.add(BuildcraftModul.triggerChargeFull);
/*    */     } 
/* 40 */     if (tile instanceof ic2.core.block.machine.tileentity.TileEntityElectricMachine || tile instanceof ic2.core.block.wiring.TileEntityElectricBlock) {
/*    */       
/* 42 */       external.add(BuildcraftModul.triggerDischargeEmpty);
/* 43 */       external.add(BuildcraftModul.triggerDischargePartial);
/* 44 */       external.add(BuildcraftModul.triggerDischargeFull);
/*    */     } 
/* 46 */     if (tile instanceof ic2.core.block.machine.tileentity.TileEntityElectricMachine || tile instanceof ic2.core.block.machine.tileentity.TileEntityAdvancedMachine || tile instanceof ic2.core.block.generator.tileentity.TileEntityBaseGenerator) {
/*    */       
/* 48 */       external.add(BuildcraftModul.triggerWorking);
/* 49 */       external.add(BuildcraftModul.triggerNotWorking);
/*    */     } 
/* 51 */     if (tile instanceof ic2.core.block.generator.tileentity.TileEntityBaseGenerator) {
/*    */       
/* 53 */       external.add(BuildcraftModul.triggerHasFuel);
/* 54 */       external.add(BuildcraftModul.triggerNoFuel);
/*    */     } 
/* 56 */     if (tile instanceof ic2.core.block.wiring.TileEntityCableDetector) {
/*    */       
/* 58 */       external.add(BuildcraftModul.triggerEnergyFlowing);
/* 59 */       external.add(BuildcraftModul.triggerEnergyNotFlowing);
/*    */     } 
/* 61 */     if (tile instanceof ic2.core.block.machine.tileentity.TileEntityMatter) {
/*    */       
/* 63 */       external.add(BuildcraftModul.triggerHasScrap);
/* 64 */       external.add(BuildcraftModul.triggerNoScrap);
/*    */     } 
/* 66 */     if (tile instanceof ic2.core.block.machine.tileentity.TileEntityAdvancedMachine) {
/*    */       
/* 68 */       external.add(BuildcraftModul.triggerFullHeat);
/* 69 */       external.add(BuildcraftModul.triggerNoFullHeat);
/*    */     } 
/* 71 */     return external;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer arg0) {
/* 77 */     return new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\TriggerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */