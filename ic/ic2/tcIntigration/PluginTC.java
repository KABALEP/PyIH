/*    */ package ic2.tcIntigration;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IBackupElectricItemManager;
/*    */ import ic2.core.IC2;
/*    */ import ic2.tcIntigration.core.ActiveEUModifier;
/*    */ import ic2.tcIntigration.core.EUMofifier;
/*    */ import ic2.tcIntigration.core.TinkerToolEUManager;
/*    */ import net.minecraft.block.Block;
/*    */ import tconstruct.library.TConstructRegistry;
/*    */ import tconstruct.library.crafting.ModifyBuilder;
/*    */ import tconstruct.library.modifier.ItemModifier;
/*    */ import tconstruct.world.TinkerWorld;
/*    */ 
/*    */ public class PluginTC {
/*    */   public static void load() {
/* 16 */     TConstructRegistry.activeModifiers.add(new ActiveEUModifier());
/* 17 */     ModifyBuilder.registerModifier((ItemModifier)new EUMofifier());
/* 18 */     ElectricItem.registerBackupManager((IBackupElectricItemManager)new TinkerToolEUManager());
/* 19 */     IC2.addValuableOre(TinkerWorld.oreSlag, 1, 6);
/* 20 */     IC2.addValuableOre(TinkerWorld.oreSlag, 2, 6);
/* 21 */     IC2.addValuableOre(TinkerWorld.oreSlag, 5, 2);
/* 22 */     IC2.addValuableOre(TinkerWorld.oreGravel, 5, 6);
/* 23 */     IC2.addValuableOre(TinkerWorld.oreGravel, 4, 2);
/* 24 */     IC2.addValuableOre(TinkerWorld.oreGravel, 0, 4);
/* 25 */     IC2.addValuableOre(TinkerWorld.oreGravel, 1, 4);
/* 26 */     IC2.addValuableOre(TinkerWorld.oreGravel, 2, 3);
/* 27 */     IC2.addValuableOre(TinkerWorld.oreGravel, 3, 3);
/* 28 */     IC2.addValuableOre((Block)TinkerWorld.oreBerrySecond, 0, 3);
/* 29 */     IC2.addValuableOre((Block)TinkerWorld.oreBerrySecond, 1, 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\tcIntigration\PluginTC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */