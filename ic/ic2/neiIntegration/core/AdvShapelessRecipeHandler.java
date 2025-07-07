/*     */ package ic2.neiIntegration.core;
/*     */ 
/*     */ import codechicken.nei.PositionedStack;
/*     */ import codechicken.nei.recipe.ShapedRecipeHandler;
/*     */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*     */ import ic2.core.AdvRecipe;
/*     */ import ic2.core.AdvShapelessRecipe;
/*     */ import ic2.core.IC2;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ 
/*     */ 
/*     */ public class AdvShapelessRecipeHandler
/*     */   extends ShapedRecipeHandler
/*     */ {
/*  19 */   static final int[][] stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 } };
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(String outputId, Object... results) {
/*  24 */     if (outputId.equals("crafting") && getClass() == AdvShapelessRecipeHandler.class) {
/*     */       
/*  26 */       List<IRecipe> allrecipes = CraftingManager.func_77594_a().func_77592_b();
/*  27 */       for (IRecipe irecipe : allrecipes) {
/*     */         
/*  29 */         if (irecipe instanceof AdvShapelessRecipe) {
/*     */           
/*  31 */           AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
/*  32 */           if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe))
/*     */           {
/*  34 */             CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2((List)advshapelessrecipe.input.clone(), advshapelessrecipe.output.func_77946_l());
/*  35 */             if (recipe.isAvailable)
/*     */             {
/*  37 */               this.arecipes.add(recipe);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  45 */       super.loadCraftingRecipes(outputId, results);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadUsageRecipes(ItemStack ingredient) {
/*  51 */     List<IRecipe> allrecipes = CraftingManager.func_77594_a().func_77592_b();
/*  52 */     for (IRecipe irecipe : allrecipes) {
/*     */       
/*  54 */       if (irecipe instanceof AdvShapelessRecipe) {
/*     */         
/*  56 */         AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
/*  57 */         if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe)) {
/*     */           
/*  59 */           CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2((List)advshapelessrecipe.input.clone(), advshapelessrecipe.output.func_77946_l());
/*  60 */           if (recipe.isAvailable && containsIc2(recipe.ingredients, ingredient)) {
/*     */             
/*  62 */             recipe.setIngredientPermutation(recipe.ingredients, ingredient);
/*  63 */             this.arecipes.add(recipe);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(ItemStack result) {
/*  72 */     List<IRecipe> allrecipes = CraftingManager.func_77594_a().func_77592_b();
/*  73 */     for (IRecipe irecipe : allrecipes) {
/*     */       
/*  75 */       if (irecipe instanceof AdvShapelessRecipe) {
/*     */         
/*  77 */         AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
/*  78 */         if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe))
/*     */         {
/*  80 */           if (areStacksSameTypeCraftingIc2(advshapelessrecipe.output, result)) {
/*     */             
/*  82 */             CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2((List)advshapelessrecipe.input.clone(), advshapelessrecipe.output.func_77946_l());
/*  83 */             if (recipe.isAvailable)
/*     */             {
/*  85 */               this.arecipes.add(recipe);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsIc2(List<PositionedStack> ingredients, ItemStack ingredient) {
/*  95 */     for (PositionedStack stack : ingredients) {
/*     */       
/*  97 */       for (ItemStack item : stack.items) {
/*     */         
/*  99 */         if (AdvRecipe.ItemsMatch(ingredient, item))
/*     */         {
/* 101 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRecipeName() {
/* 111 */     return "Shapeless IC2 Crafting";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areStacksSameTypeCraftingIc2(ItemStack stack1, ItemStack stack2) {
/* 116 */     if (stack1 == null || stack2 == null)
/*     */     {
/* 118 */       return false;
/*     */     }
/* 120 */     return (stack1.func_77973_b() == stack2.func_77973_b() && (stack1.func_77960_j() == stack2.func_77960_j() || stack1.func_77960_j() == 32767 || stack2.func_77960_j() == 32767 || stack1.func_77973_b() instanceof ic2.api.item.IElectricItem || stack1.func_77973_b().func_77645_m()));
/*     */   }
/*     */   
/*     */   public class CachedShapelessRecipe2
/*     */     extends TemplateRecipeHandler.CachedRecipe {
/*     */     ArrayList<PositionedStack> ingredients;
/*     */     PositionedStack result;
/*     */     public boolean isAvailable;
/*     */     
/*     */     public CachedShapelessRecipe2(List input, ItemStack output) {
/* 130 */       super((TemplateRecipeHandler)AdvShapelessRecipeHandler.this);
/* 131 */       this.result = new PositionedStack(output, 119, 24);
/* 132 */       this.ingredients = new ArrayList<>();
/* 133 */       this.isAvailable = false;
/* 134 */       setIngredients(input);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setIngredients(List items) {
/* 139 */       for (int ingred = 0; ingred < items.size(); ingred++) {
/*     */         
/* 141 */         Object obj = items.get(ingred);
/* 142 */         PositionedStack stack = new PositionedStack(obj, 25 + AdvShapelessRecipeHandler.stackorder[ingred][0] * 18, 6 + AdvShapelessRecipeHandler.stackorder[ingred][1] * 18);
/* 143 */         stack.setMaxSize(1);
/* 144 */         this.ingredients.add(stack);
/*     */       } 
/* 146 */       this.isAvailable = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<PositionedStack> getIngredients() {
/* 151 */       return getCycledIngredients(AdvShapelessRecipeHandler.this.cycleticks / 20, this.ingredients);
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionedStack getResult() {
/* 156 */       return this.result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\AdvShapelessRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */