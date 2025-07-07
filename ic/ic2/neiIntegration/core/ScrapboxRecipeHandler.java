/*     */ package ic2.neiIntegration.core;
/*     */ 
/*     */ import codechicken.lib.gui.GuiDraw;
/*     */ import codechicken.nei.NEIServerUtils;
/*     */ import codechicken.nei.PositionedStack;
/*     */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.awt.Rectangle;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrapboxRecipeHandler
/*     */   extends TemplateRecipeHandler
/*     */ {
/*  22 */   public static PositionedStack scrapboxPositionedStack = new PositionedStack(Ic2Items.scrapBox, 51, 24);
/*  23 */   public static DecimalFormat liquidAmountFormat = new DecimalFormat("0.##%");
/*     */ 
/*     */   
/*     */   public String getRecipeName() {
/*  27 */     return "Scrapbox";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRecipeId() {
/*  32 */     return "ic2.scrapbox";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiTexture() {
/*  37 */     return "ic2:textures/guiSprites/ScrapboxRecipes.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawExtras(int recipe) {
/*  42 */     Float chance = ((CachedScrapboxRecipe)this.arecipes.get(recipe)).chance;
/*  43 */     String costString = liquidAmountFormat.format(chance);
/*  44 */     GuiDraw.drawStringC(costString, 85, 11, -8355712, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTransferRects() {
/*  49 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 25, 16), getRecipeId(), new Object[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Map.Entry<ItemStack, Float>> getRecipeList() {
/*  54 */     List<Map.Entry<ItemStack, Float>> result = new ArrayList<>();
/*     */     
/*  56 */     Map<ItemStack, Float> input = Recipes.scrapboxDrops.getDrops();
/*     */     
/*  58 */     for (Map.Entry<ItemStack, Float> entry : input.entrySet())
/*     */     {
/*  60 */       result.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
/*     */     }
/*     */     
/*  63 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(String outputId, Object... results) {
/*  68 */     if (outputId.equals(getRecipeId())) {
/*     */       
/*  70 */       for (Map.Entry<ItemStack, Float> irecipe : getRecipeList())
/*     */       {
/*  72 */         this.arecipes.add(new CachedScrapboxRecipe(irecipe));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  77 */       super.loadCraftingRecipes(outputId, results);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(ItemStack result) {
/*  83 */     for (Map.Entry<ItemStack, Float> irecipe : getRecipeList()) {
/*     */       
/*  85 */       if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getKey(), result))
/*     */       {
/*  87 */         this.arecipes.add(new CachedScrapboxRecipe(irecipe));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadUsageRecipes(ItemStack ingredient) {
/*  95 */     if (NEIServerUtils.areStacksSameTypeCrafting(Ic2Items.scrapBox, ingredient))
/*     */     {
/*  97 */       for (Map.Entry<ItemStack, Float> irecipe : getRecipeList())
/*     */       {
/*  99 */         this.arecipes.add(new CachedScrapboxRecipe(irecipe));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class CachedScrapboxRecipe
/*     */     extends TemplateRecipeHandler.CachedRecipe
/*     */   {
/*     */     public PositionedStack output;
/*     */     public Float chance;
/*     */     
/*     */     public PositionedStack getIngredient() {
/* 112 */       return ScrapboxRecipeHandler.scrapboxPositionedStack;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionedStack getResult() {
/* 117 */       return this.output;
/*     */     }
/*     */     
/*     */     public CachedScrapboxRecipe(Map.Entry<ItemStack, Float> entry) {
/* 121 */       super(ScrapboxRecipeHandler.this);
/* 122 */       this.output = new PositionedStack(entry.getKey(), 111, 24);
/* 123 */       this.chance = entry.getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\ScrapboxRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */