/*    */ package ic2.cgIntegration.core;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.RecipeOutput;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
/*    */ import uristqwerty.CraftGuide.api.ItemSlot;
/*    */ import uristqwerty.CraftGuide.api.RecipeGenerator;
/*    */ import uristqwerty.CraftGuide.api.RecipeProvider;
/*    */ import uristqwerty.CraftGuide.api.RecipeTemplate;
/*    */ import uristqwerty.CraftGuide.api.Slot;
/*    */ 
/*    */ public class MachineGenerator
/*    */   extends CraftGuideAPIObject implements RecipeProvider {
/* 16 */   private final ItemSlot[] slots = new ItemSlot[] { new ItemSlot(13, 21, 16, 16, true), new ItemSlot(50, 21, 16, 16, true) };
/*    */   
/*    */   private final Map<IRecipeInput, RecipeOutput> recipeMap;
/*    */   private final int backgroundX;
/*    */   private final int backgroundY;
/*    */   private final int backgroundSelectedX;
/*    */   private final int backgroundSelectedY;
/*    */   private final ItemStack craftingType;
/*    */   
/*    */   public MachineGenerator(Map<IRecipeInput, RecipeOutput> recipes, int backgroundX, int backgroundY, int backgroundSelectedX, int backgroundSelectedY, ItemStack craftingType) {
/* 26 */     this.recipeMap = recipes;
/* 27 */     this.backgroundX = backgroundX;
/* 28 */     this.backgroundY = backgroundY;
/* 29 */     this.backgroundSelectedX = backgroundSelectedX;
/* 30 */     this.backgroundSelectedY = backgroundSelectedY;
/* 31 */     this.craftingType = craftingType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void generateRecipes(RecipeGenerator generator) {
/* 36 */     RecipeTemplate template = generator.createRecipeTemplate((Slot[])this.slots, this.craftingType, "ic2:craftguide.png", this.backgroundX, this.backgroundY, this.backgroundSelectedX, this.backgroundSelectedY);
/* 37 */     for (Map.Entry<IRecipeInput, RecipeOutput> entry : this.recipeMap.entrySet()) {
/*    */       
/* 39 */       generator.addRecipe(template, (Object[])new ItemStack[] { ((ItemStack)((IRecipeInput)entry.getKey()).getInputs().get(0)).func_77946_l(), ((ItemStack)((RecipeOutput)entry.getValue()).items.get(0)).func_77946_l() });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cgIntegration\core\MachineGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */