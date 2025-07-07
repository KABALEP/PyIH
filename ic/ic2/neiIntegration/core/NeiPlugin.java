/*    */ package ic2.neiIntegration.core;
/*    */ import codechicken.nei.api.API;
/*    */ import codechicken.nei.api.INEIGuiHandler;
/*    */ import codechicken.nei.guihook.GuiContainerManager;
/*    */ import codechicken.nei.recipe.ICraftingHandler;
/*    */ import codechicken.nei.recipe.IUsageHandler;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.gui.GuiCanner;
/*    */ import ic2.core.block.machine.gui.GuiCentrifuge;
/*    */ import ic2.core.block.machine.gui.GuiCompressor;
/*    */ import ic2.core.block.machine.gui.GuiElecFurnace;
/*    */ import ic2.core.block.machine.gui.GuiExtractor;
/*    */ import ic2.core.block.machine.gui.GuiInduction;
/*    */ import ic2.core.block.machine.gui.GuiIronFurnace;
/*    */ import ic2.core.block.machine.gui.GuiMacerator;
/*    */ import ic2.core.block.machine.gui.GuiRotary;
/*    */ import ic2.core.block.machine.gui.GuiSingularity;
/*    */ import ic2.core.block.machine.gui.GuiVacuum;
/*    */ 
/*    */ public class NeiPlugin {
/*    */   public static void init() {
/* 23 */     API.registerRecipeHandler((ICraftingHandler)new AdvRecipeHandler());
/* 24 */     API.registerUsageHandler((IUsageHandler)new AdvRecipeHandler());
/* 25 */     API.registerRecipeHandler((ICraftingHandler)new AdvShapelessRecipeHandler());
/* 26 */     API.registerUsageHandler((IUsageHandler)new AdvShapelessRecipeHandler());
/* 27 */     API.registerRecipeHandler((ICraftingHandler)new MaceratorRecipeHandler());
/* 28 */     API.registerUsageHandler((IUsageHandler)new MaceratorRecipeHandler());
/* 29 */     API.registerRecipeHandler((ICraftingHandler)new ExtractorRecipeHandler());
/* 30 */     API.registerUsageHandler((IUsageHandler)new ExtractorRecipeHandler());
/* 31 */     API.registerRecipeHandler((ICraftingHandler)new CompressorRecipeHandler());
/* 32 */     API.registerUsageHandler((IUsageHandler)new CompressorRecipeHandler());
/* 33 */     API.registerUsageHandler((IUsageHandler)new ScrapboxRecipeHandler());
/* 34 */     API.registerRecipeHandler((ICraftingHandler)new ScrapboxRecipeHandler());
/* 35 */     API.registerUsageHandler((IUsageHandler)new CanningRecipeHandler());
/* 36 */     API.registerRecipeHandler((ICraftingHandler)new CanningRecipeHandler());
/* 37 */     API.registerGuiOverlay(GuiMacerator.class, "macerator", 5, 11);
/* 38 */     API.registerGuiOverlay(GuiExtractor.class, "extractor", 5, 11);
/* 39 */     API.registerGuiOverlay(GuiCompressor.class, "compressor", 5, 11);
/* 40 */     API.registerGuiOverlay(GuiIronFurnace.class, "smelting", 5, 11);
/* 41 */     API.registerGuiOverlay(GuiElecFurnace.class, "smelting", 5, 11);
/* 42 */     API.registerGuiOverlay(GuiInduction.class, "smelting", -4, 11);
/* 43 */     API.registerGuiOverlay(GuiCanner.class, "canning", 5, 11);
/* 44 */     API.registerGuiOverlay(GuiRotary.class, "macerator", 5, 11);
/* 45 */     API.registerGuiOverlay(GuiCentrifuge.class, "extractor", -10, 11);
/* 46 */     API.registerGuiOverlay(GuiSingularity.class, "compressor", 5, 11);
/* 47 */     API.registerGuiOverlay(GuiInduction.class, "smelting", -4, 11);
/* 48 */     API.registerGuiOverlay(GuiVacuum.class, "canning", 5, 11);
/* 49 */     API.registerNEIGuiHandler((INEIGuiHandler)new GuiHandlerReactorPlanner());
/* 50 */     if (IC2.enableCropHelper)
/*    */     {
/* 52 */       GuiContainerManager.tooltipHandlers.add(new CropHelper());
/*    */     }
/* 54 */     if (!IC2.displayNoUseItems)
/*    */     {
/* 56 */       API.hideItem(Ic2Items.noUse);
/*    */     }
/* 58 */     API.hideItem(Ic2Items.texturedWall);
/* 59 */     API.hideItem(Ic2Items.insulatedCopperCableBlock);
/* 60 */     API.hideItem(Ic2Items.reinforcedDoorBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\NeiPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */