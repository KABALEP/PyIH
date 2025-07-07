/*    */ package ic2.bcIntigration.core;
/*    */ 
/*    */ import buildcraft.api.crops.CropManager;
/*    */ import buildcraft.api.crops.ICropHandler;
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.StatementManager;
/*    */ import ic2.bcIntigration.core.crop.IC2CropManager;
/*    */ import ic2.bcIntigration.core.triggers.TriggerCapacitor;
/*    */ import ic2.bcIntigration.core.triggers.TriggerEnergyFlow;
/*    */ import ic2.bcIntigration.core.triggers.TriggerFuel;
/*    */ import ic2.bcIntigration.core.triggers.TriggerHeat;
/*    */ import ic2.bcIntigration.core.triggers.TriggerScrap;
/*    */ import ic2.bcIntigration.core.triggers.TriggerWork;
/*    */ 
/*    */ public class BuildcraftModul {
/*    */   public static TriggerCapacitor triggerCapacitorEmpty;
/*    */   public static TriggerCapacitor triggerCapacitorHasEnergy;
/*    */   public static TriggerCapacitor triggerCapacitorHasRoom;
/*    */   public static TriggerCapacitor triggerCapacitorFull;
/*    */   public static TriggerCapacitor triggerChargeEmpty;
/*    */   public static TriggerCapacitor triggerChargePartial;
/*    */   public static TriggerCapacitor triggerChargeFull;
/*    */   public static TriggerCapacitor triggerDischargeEmpty;
/*    */   public static TriggerCapacitor triggerDischargePartial;
/*    */   public static TriggerCapacitor triggerDischargeFull;
/*    */   public static TriggerWork triggerWorking;
/*    */   public static TriggerWork triggerNotWorking;
/*    */   public static TriggerEnergyFlow triggerEnergyFlowing;
/*    */   public static TriggerEnergyFlow triggerEnergyNotFlowing;
/*    */   public static TriggerFuel triggerHasFuel;
/*    */   public static TriggerFuel triggerNoFuel;
/*    */   public static TriggerScrap triggerHasScrap;
/*    */   public static TriggerScrap triggerNoScrap;
/*    */   public static TriggerHeat triggerFullHeat;
/*    */   public static TriggerHeat triggerNoFullHeat;
/*    */   
/*    */   public static void postLoad() {
/* 38 */     handleTriggers();
/* 39 */     StatementManager.registerTriggerProvider(new TriggerHandler());
/* 40 */     CropManager.registerHandler((ICropHandler)new IC2CropManager());
/*    */   }
/*    */ 
/*    */   
/*    */   private static void handleTriggers() {
/* 45 */     triggerCapacitorEmpty = new TriggerCapacitor(0);
/* 46 */     triggerCapacitorHasEnergy = new TriggerCapacitor(1);
/* 47 */     triggerCapacitorHasRoom = new TriggerCapacitor(2);
/* 48 */     triggerCapacitorFull = new TriggerCapacitor(3);
/* 49 */     triggerChargeEmpty = new TriggerCapacitor(4);
/* 50 */     triggerChargePartial = new TriggerCapacitor(5);
/* 51 */     triggerChargeFull = new TriggerCapacitor(6);
/* 52 */     triggerDischargeEmpty = new TriggerCapacitor(7);
/* 53 */     triggerDischargePartial = new TriggerCapacitor(8);
/* 54 */     triggerDischargeFull = new TriggerCapacitor(9);
/* 55 */     triggerWorking = new TriggerWork(true);
/* 56 */     triggerNotWorking = new TriggerWork(false);
/* 57 */     triggerEnergyFlowing = new TriggerEnergyFlow(true);
/* 58 */     triggerEnergyNotFlowing = new TriggerEnergyFlow(false);
/* 59 */     triggerHasFuel = new TriggerFuel(true);
/* 60 */     triggerNoFuel = new TriggerFuel(false);
/* 61 */     triggerHasScrap = new TriggerScrap(true);
/* 62 */     triggerNoScrap = new TriggerScrap(false);
/* 63 */     triggerFullHeat = new TriggerHeat(true);
/* 64 */     triggerNoFullHeat = new TriggerHeat(false);
/* 65 */     StatementManager.registerStatement((IStatement)triggerCapacitorEmpty);
/* 66 */     StatementManager.registerStatement((IStatement)triggerCapacitorHasEnergy);
/* 67 */     StatementManager.registerStatement((IStatement)triggerCapacitorHasRoom);
/* 68 */     StatementManager.registerStatement((IStatement)triggerCapacitorFull);
/* 69 */     StatementManager.registerStatement((IStatement)triggerChargeEmpty);
/* 70 */     StatementManager.registerStatement((IStatement)triggerChargePartial);
/* 71 */     StatementManager.registerStatement((IStatement)triggerChargeFull);
/* 72 */     StatementManager.registerStatement((IStatement)triggerDischargeEmpty);
/* 73 */     StatementManager.registerStatement((IStatement)triggerDischargePartial);
/* 74 */     StatementManager.registerStatement((IStatement)triggerDischargeFull);
/* 75 */     StatementManager.registerStatement((IStatement)triggerWorking);
/* 76 */     StatementManager.registerStatement((IStatement)triggerNotWorking);
/* 77 */     StatementManager.registerStatement((IStatement)triggerEnergyFlowing);
/* 78 */     StatementManager.registerStatement((IStatement)triggerEnergyNotFlowing);
/* 79 */     StatementManager.registerStatement((IStatement)triggerHasFuel);
/* 80 */     StatementManager.registerStatement((IStatement)triggerNoFuel);
/* 81 */     StatementManager.registerStatement((IStatement)triggerHasScrap);
/* 82 */     StatementManager.registerStatement((IStatement)triggerNoScrap);
/* 83 */     StatementManager.registerStatement((IStatement)triggerFullHeat);
/* 84 */     StatementManager.registerStatement((IStatement)triggerNoFullHeat);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\BuildcraftModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */