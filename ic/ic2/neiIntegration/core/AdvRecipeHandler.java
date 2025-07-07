/*     */ package ic2.neiIntegration.core;
/*     */ 
/*     */ import codechicken.nei.PositionedStack;
/*     */ import codechicken.nei.recipe.ShapedRecipeHandler;
/*     */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*     */ import ic2.core.AdvRecipe;
/*     */ import ic2.core.IC2;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvRecipeHandler
/*     */   extends ShapedRecipeHandler
/*     */ {
/*     */   public void loadCraftingRecipes(String outputId, Object... results) {
/*  23 */     if (outputId.equals("crafting") && getClass() == AdvRecipeHandler.class) {
/*     */       
/*  25 */       List<IRecipe> allRecipes = CraftingManager.func_77594_a().func_77592_b();
/*  26 */       for (IRecipe irecipe : allRecipes) {
/*     */         
/*  28 */         if (irecipe != null && irecipe instanceof AdvRecipe) {
/*     */           
/*  30 */           AdvRecipe advRecipe = (AdvRecipe)irecipe;
/*  31 */           if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advRecipe))
/*     */           {
/*  33 */             CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advRecipe.length, advRecipe.height, (Object[])advRecipe.input.clone(), advRecipe.output.func_77946_l());
/*  34 */             if (recipe.isAvailable)
/*     */             {
/*  36 */               this.arecipes.add(recipe);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  44 */       super.loadCraftingRecipes(outputId, results);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadUsageRecipes(ItemStack ingredient) {
/*  51 */     List<IRecipe> allRecipes = CraftingManager.func_77594_a().func_77592_b();
/*  52 */     for (IRecipe irecipe : allRecipes) {
/*     */       
/*  54 */       if (irecipe != null && irecipe instanceof AdvRecipe) {
/*     */         
/*  56 */         AdvRecipe advRecipe = (AdvRecipe)irecipe;
/*  57 */         if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advRecipe)) {
/*     */           
/*  59 */           CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advRecipe.length, advRecipe.height, (Object[])advRecipe.input.clone(), advRecipe.output.func_77946_l());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(ItemStack result) {
/*  75 */     List<IRecipe> allRecipes = CraftingManager.func_77594_a().func_77592_b();
/*  76 */     for (IRecipe irecipe : allRecipes) {
/*     */       
/*  78 */       if (irecipe != null && irecipe instanceof AdvRecipe) {
/*     */         
/*  80 */         AdvRecipe advRecipe = (AdvRecipe)irecipe;
/*  81 */         if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advRecipe))
/*     */         {
/*  83 */           if (areStacksSameTypeCraftingIc2(advRecipe.output, result)) {
/*     */             
/*  85 */             CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advRecipe.length, advRecipe.height, (Object[])advRecipe.input.clone(), advRecipe.output.func_77946_l());
/*  86 */             if (recipe.isAvailable)
/*     */             {
/*  88 */               this.arecipes.add(recipe);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsIc2(List<PositionedStack> ingredients, ItemStack ingredient) {
/*  98 */     for (PositionedStack stack : ingredients) {
/*     */       
/* 100 */       for (ItemStack item : stack.items) {
/*     */         
/* 102 */         if (areStacksSameTypeCraftingIc2(ingredient, item))
/*     */         {
/* 104 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areStacksSameTypeCraftingIc2(ItemStack stack1, ItemStack stack2) {
/* 113 */     if (stack1 == null || stack2 == null)
/*     */     {
/* 115 */       return false;
/*     */     }
/* 117 */     return (stack1.func_77973_b() == stack2.func_77973_b() && (stack1.func_77960_j() == stack2.func_77960_j() || stack1.func_77960_j() == 32767 || stack2.func_77960_j() == 32767 || (stack1.func_77973_b() instanceof ic2.api.item.IElectricItem && !(stack1.func_77973_b() instanceof ic2.api.item.ISpecialElectricItem)) || stack1.func_77973_b().func_77645_m()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRecipeName() {
/* 125 */     return "Shaped IC2 Crafting";
/*     */   }
/*     */ 
/*     */   
/*     */   public class CachedShapedRecipe2
/*     */     extends TemplateRecipeHandler.CachedRecipe
/*     */   {
/*     */     public ArrayList<PositionedStack> ingredients;
/*     */     public PositionedStack result;
/*     */     public boolean isAvailable;
/*     */     
/*     */     public CachedShapedRecipe2(int width, int height, Object[] items, ItemStack out) {
/* 137 */       super((TemplateRecipeHandler)AdvRecipeHandler.this);
/* 138 */       this.result = new PositionedStack(out, 119, 24);
/* 139 */       this.ingredients = new ArrayList<>();
/* 140 */       this.isAvailable = false;
/* 141 */       setIngredients(width, height, items);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setIngredients(int width, int height, Object[] items) {
/* 146 */       for (int x = 0; x < width; x++) {
/*     */         
/* 148 */         for (int y = 0; y < height; y++) {
/*     */           
/* 150 */           Object obj = items[y * width + x];
/* 151 */           if (obj != null) {
/*     */             
/* 153 */             PositionedStack stack = new PositionedStack(obj, 25 + x * 18, 6 + y * 18);
/* 154 */             stack.setMaxSize(1);
/* 155 */             this.ingredients.add(stack);
/*     */           } 
/*     */         } 
/*     */       } 
/* 159 */       this.isAvailable = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<PositionedStack> getIngredients() {
/* 164 */       return getCycledIngredients(AdvRecipeHandler.this.cycleticks / 20, this.ingredients);
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionedStack getResult() {
/* 169 */       return this.result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\AdvRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */