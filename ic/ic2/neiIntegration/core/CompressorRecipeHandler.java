/*    */ package ic2.neiIntegration.core;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.machine.gui.GuiCompressor;
/*    */ import ic2.core.block.machine.gui.GuiSingularity;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompressorRecipeHandler
/*    */   extends MachineRecipeHandler
/*    */ {
/*    */   public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
/* 21 */     List<Class<? extends GuiContainer>> list = new ArrayList<>();
/* 22 */     list.add(GuiCompressor.class);
/* 23 */     list.add(GuiSingularity.class);
/* 24 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeName() {
/* 29 */     return "Compressor";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeId() {
/* 34 */     return "ic2.compressor";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiTexture() {
/* 39 */     return "ic2:textures/guiSprites/GUICompressor.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOverlayIdentifier() {
/* 44 */     return "compressor";
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IRecipeInput, RecipeOutput> getRecipeList() {
/* 49 */     return Recipes.compressor.getRecipes();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\CompressorRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */