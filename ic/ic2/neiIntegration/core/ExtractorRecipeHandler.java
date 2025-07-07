/*    */ package ic2.neiIntegration.core;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.machine.gui.GuiCentrifuge;
/*    */ import ic2.core.block.machine.gui.GuiExtractor;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtractorRecipeHandler
/*    */   extends MachineRecipeHandler
/*    */ {
/*    */   public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
/* 24 */     List<Class<? extends GuiContainer>> list = new ArrayList<>();
/* 25 */     list.add(GuiExtractor.class);
/* 26 */     list.add(GuiCentrifuge.class);
/* 27 */     return list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRecipeName() {
/* 33 */     return "Extractor";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRecipeId() {
/* 39 */     return "ic2.extractor";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getOverlayIdentifier() {
/* 45 */     return "extractor";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<IRecipeInput, RecipeOutput> getRecipeList() {
/* 51 */     return Recipes.extractor.getRecipes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getGuiTexture() {
/* 57 */     return "ic2:textures/guiSprites/GUIExtractor.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\ExtractorRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */