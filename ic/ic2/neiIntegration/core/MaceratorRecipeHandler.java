/*    */ package ic2.neiIntegration.core;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.machine.gui.GuiMacerator;
/*    */ import ic2.core.block.machine.gui.GuiRotary;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaceratorRecipeHandler
/*    */   extends MachineRecipeHandler
/*    */ {
/*    */   public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
/* 20 */     List<Class<? extends GuiContainer>> list = new ArrayList<>();
/* 21 */     list.add(GuiMacerator.class);
/* 22 */     list.add(GuiRotary.class);
/* 23 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeName() {
/* 28 */     return "Macerator";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeId() {
/* 33 */     return "ic2.macerator";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiTexture() {
/* 38 */     return "ic2:textures/guiSprites/GUIMacerator.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOverlayIdentifier() {
/* 43 */     return "macerator";
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IRecipeInput, RecipeOutput> getRecipeList() {
/* 48 */     return Recipes.macerator.getRecipes();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\MaceratorRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */