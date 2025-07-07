/*     */ package ic2.neiIntegration.core;
/*     */ 
/*     */ import codechicken.lib.gui.GuiDraw;
/*     */ import codechicken.nei.NEIServerUtils;
/*     */ import codechicken.nei.PositionedStack;
/*     */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MachineRecipeHandler
/*     */   extends TemplateRecipeHandler
/*     */ {
/*     */   int ticks;
/*     */   
/*     */   public abstract String getRecipeName();
/*     */   
/*     */   public abstract String getRecipeId();
/*     */   
/*     */   public abstract String getOverlayIdentifier();
/*     */   
/*     */   public abstract Map<IRecipeInput, RecipeOutput> getRecipeList();
/*     */   
/*     */   public void drawBackground(int i) {
/*  37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  38 */     GuiDraw.changeTexture(getGuiTexture());
/*  39 */     GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 140, 65);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawExtras(int i) {
/*  45 */     float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
/*  46 */     drawProgressBar(74, 23, 176, 14, 25, 16, f, 0);
/*  47 */     f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
/*  48 */     drawProgressBar(51, 25, 176, 0, 14, 14, f, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  54 */     super.onUpdate();
/*  55 */     this.ticks++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTransferRects() {
/*  61 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 25, 16), getRecipeId(), new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(String outputId, Object... results) {
/*  67 */     if (outputId.equals(getRecipeId())) {
/*     */       
/*  69 */       for (Map.Entry<IRecipeInput, RecipeOutput> entry : getRecipeList().entrySet())
/*     */       {
/*  71 */         this.arecipes.add(new CachedIORecipe(entry.getKey(), entry.getValue()));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  76 */       super.loadCraftingRecipes(outputId, results);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(ItemStack result) {
/*  85 */     for (Map.Entry<IRecipeInput, RecipeOutput> list : getRecipeList().entrySet()) {
/*     */       
/*  87 */       for (ItemStack output : ((RecipeOutput)list.getValue()).items) {
/*     */         
/*  89 */         if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
/*     */         {
/*  91 */           this.arecipes.add(new CachedIORecipe(list.getKey(), list.getValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadUsageRecipes(ItemStack ingredient) {
/* 101 */     for (Map.Entry<IRecipeInput, RecipeOutput> list : getRecipeList().entrySet()) {
/*     */       
/* 103 */       if (((IRecipeInput)list.getKey()).matches(ingredient))
/*     */       {
/* 105 */         this.arecipes.add(new CachedIORecipe(list.getKey(), list.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class CachedIORecipe
/*     */     extends TemplateRecipeHandler.CachedRecipe
/*     */   {
/* 114 */     public List<PositionedStack> ingredients = new ArrayList<>();
/*     */     public PositionedStack output;
/* 116 */     public List<PositionedStack> otherStacks = new ArrayList<>();
/*     */ 
/*     */     
/*     */     public List<PositionedStack> getIngredients() {
/* 120 */       return getCycledIngredients(MachineRecipeHandler.this.cycleticks / 20, this.ingredients);
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionedStack getResult() {
/* 125 */       return this.output;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<PositionedStack> getOtherStacks() {
/* 130 */       return this.otherStacks;
/*     */     }
/*     */ 
/*     */     
/*     */     public CachedIORecipe(ItemStack input, ItemStack output1) {
/* 135 */       super(MachineRecipeHandler.this);
/* 136 */       this.ingredients.add(new PositionedStack(ItemStack.func_77944_b(input), 51, 6));
/* 137 */       this.output = new PositionedStack(ItemStack.func_77944_b(output1), 111, 24);
/*     */     }
/*     */     
/*     */     public CachedIORecipe(IRecipeInput input, RecipeOutput output1) {
/* 141 */       super(MachineRecipeHandler.this);
/* 142 */       List<ItemStack> items = new ArrayList<>();
/*     */       
/* 144 */       for (ItemStack item : input.getInputs())
/*     */       {
/* 146 */         items.add(StackUtil.copyWithSize(item, input.getAmount()));
/*     */       }
/*     */       
/* 149 */       this.ingredients.add(new PositionedStack(items, 51, 6));
/* 150 */       this.output = new PositionedStack(ItemStack.func_77944_b(output1.items.get(0)), 111, 24);
/*     */       
/* 152 */       for (int i = 1; i < output1.items.size(); i++)
/*     */       {
/* 154 */         this.otherStacks.add(new PositionedStack(ItemStack.func_77944_b(output1.items.get(i)), 111, 24 + i * 18));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\MachineRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */