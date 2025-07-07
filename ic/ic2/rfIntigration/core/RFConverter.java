/*    */ package ic2.rfIntigration.core;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import ic2.api.recipe.ICraftingRecipeManager;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.generator.block.BlockConverter;
/*    */ import ic2.rfIntigration.tiles.converters.EU.HighEUConverter;
/*    */ import ic2.rfIntigration.tiles.converters.EU.LowEUConverter;
/*    */ import ic2.rfIntigration.tiles.converters.EU.MediumEUConverter;
/*    */ import ic2.rfIntigration.tiles.converters.EU.MetaBlockHighEU;
/*    */ import ic2.rfIntigration.tiles.converters.EU.MetaBlockMedEU;
/*    */ import ic2.rfIntigration.tiles.converters.EU.MetaBlockSmallEU;
/*    */ import ic2.rfIntigration.tiles.converters.RF.HighRFConverter;
/*    */ import ic2.rfIntigration.tiles.converters.RF.LowRFConverter;
/*    */ import ic2.rfIntigration.tiles.converters.RF.MediumRFConverter;
/*    */ import ic2.rfIntigration.tiles.converters.RF.MetBlockHighRF;
/*    */ import ic2.rfIntigration.tiles.converters.RF.MetaBlockMedRF;
/*    */ import ic2.rfIntigration.tiles.converters.RF.MetaBlockSmallRF;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ 
/*    */ 
/*    */ public class RFConverter
/*    */ {
/*    */   public static void loadTiles() {
/* 27 */     BlockConverter.addTile((BlockConverter.Converter)new MetaBlockSmallRF());
/* 28 */     BlockConverter.addTile((BlockConverter.Converter)new MetaBlockMedRF());
/* 29 */     BlockConverter.addTile((BlockConverter.Converter)new MetBlockHighRF());
/* 30 */     BlockConverter.addTile((BlockConverter.Converter)new MetaBlockSmallEU());
/* 31 */     BlockConverter.addTile((BlockConverter.Converter)new MetaBlockMedEU());
/* 32 */     BlockConverter.addTile((BlockConverter.Converter)new MetaBlockHighEU());
/* 33 */     GameRegistry.registerTileEntity(LowRFConverter.class, "LowRFConverter");
/* 34 */     GameRegistry.registerTileEntity(MediumRFConverter.class, "MedRFConverter");
/* 35 */     GameRegistry.registerTileEntity(HighRFConverter.class, "HigRFConverter");
/* 36 */     GameRegistry.registerTileEntity(LowEUConverter.class, "LowEUConverter");
/* 37 */     GameRegistry.registerTileEntity(MediumEUConverter.class, "MedEUConverter");
/* 38 */     GameRegistry.registerTileEntity(HighEUConverter.class, "HigEUConverter");
/*    */   }
/*    */ 
/*    */   
/*    */   public static void loadRecipes() {
/* 43 */     ICraftingRecipeManager advRecipes = Recipes.advRecipes;
/* 44 */     advRecipes.addRecipe(Ic2Items.smallRF, new Object[] { " X ", "YCV", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.lvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/*    */     
/* 46 */     advRecipes.addRecipe(Ic2Items.medRF, new Object[] { "DXD", "YCV", "DXD", Character.valueOf('D'), Ic2Items.electronicCircuit, Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.mvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/* 47 */     advRecipes.addRecipe(Ic2Items.medRF, new Object[] { " X ", "YCV", " X ", Character.valueOf('X'), Ic2Items.electronicCircuit, Character.valueOf('Y'), Ic2Items.mvTransformer, Character.valueOf('V'), Blocks.field_150451_bX, Character.valueOf('C'), Ic2Items.smallRF });
/*    */     
/* 49 */     advRecipes.addRecipe(Ic2Items.highRF, new Object[] { "DXD", "YCV", "DXD", Character.valueOf('D'), Ic2Items.advancedCircuit, Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.hvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/* 50 */     advRecipes.addRecipe(Ic2Items.highRF, new Object[] { " X ", "YCV", " X ", Character.valueOf('X'), Ic2Items.advancedCircuit, Character.valueOf('Y'), Ic2Items.hvTransformer, Character.valueOf('V'), Blocks.field_150451_bX, Character.valueOf('C'), Ic2Items.medRF });
/*    */     
/* 52 */     advRecipes.addRecipe(Ic2Items.smallEU, new Object[] { " X ", "VCY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.lvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/*    */     
/* 54 */     advRecipes.addRecipe(Ic2Items.medEU, new Object[] { "DXD", "VCY", "DXD", Character.valueOf('D'), Ic2Items.electronicCircuit, Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.mvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/* 55 */     advRecipes.addRecipe(Ic2Items.medEU, new Object[] { " X ", "VCY", " X ", Character.valueOf('X'), Ic2Items.electronicCircuit, Character.valueOf('Y'), Ic2Items.mvTransformer, Character.valueOf('V'), Blocks.field_150451_bX, Character.valueOf('C'), Ic2Items.smallEU });
/*    */     
/* 57 */     advRecipes.addRecipe(Ic2Items.highEU, new Object[] { "DXD", "VCY", "DXD", Character.valueOf('D'), Ic2Items.advancedCircuit, Character.valueOf('X'), "ingotCopper", Character.valueOf('C'), Items.field_151042_j, Character.valueOf('Y'), Ic2Items.hvTransformer, Character.valueOf('V'), Blocks.field_150451_bX });
/* 58 */     advRecipes.addRecipe(Ic2Items.highEU, new Object[] { " X ", "VCY", " X ", Character.valueOf('X'), Ic2Items.advancedCircuit, Character.valueOf('Y'), Ic2Items.hvTransformer, Character.valueOf('V'), Blocks.field_150451_bX, Character.valueOf('C'), Ic2Items.medEU });
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\core\RFConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */