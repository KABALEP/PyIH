/*     */ package ic2.neiIntegration.core;
/*     */ 
/*     */ import codechicken.lib.gui.GuiDraw;
/*     */ import codechicken.nei.NEIServerUtils;
/*     */ import codechicken.nei.PositionedStack;
/*     */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.gui.GuiCanner;
/*     */ import ic2.core.block.machine.gui.GuiVacuum;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CanningRecipeHandler
/*     */   extends TemplateRecipeHandler
/*     */ {
/*  32 */   List<RecipeEntry> recipeList = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public CanningRecipeHandler() {
/*  36 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int recipiesPerPage() {
/*  42 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRecipeName() {
/*  48 */     return "Canning Machine";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOverlayIdentifier() {
/*  54 */     return "canning";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
/*  60 */     List<Class<? extends GuiContainer>> list = new ArrayList<>();
/*  61 */     list.add(GuiCanner.class);
/*  62 */     list.add(GuiVacuum.class);
/*  63 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiTexture() {
/*  69 */     return "ic2:textures/guiSprites/GUICanner.png";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int i) {
/*  75 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  76 */     GuiDraw.changeTexture(getGuiTexture());
/*  77 */     GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 140, 65);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawExtras(int i) {
/*  83 */     float f = (this.cycleticks >= 20) ? (((this.cycleticks - 20) % 40) / 20.0F) : 0.0F;
/*  84 */     drawProgressBar(69, 25, 176, 15, 25, 13, f, 0);
/*  85 */     f = (this.cycleticks <= 20) ? (this.cycleticks / 20.0F) : 1.0F;
/*  86 */     drawProgressBar(24, 16, 176, 0, 14, 14, f, 3);
/*  87 */     CannerRecipe recipe = this.arecipes.get(i);
/*  88 */     if (recipe != null && !recipe.recipe.extraInfo.equals(""))
/*     */     {
/*  90 */       GuiDraw.fontRenderer.func_78276_b(recipe.recipe.extraInfo, 50, 65, 4210752);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTransferRects() {
/*  97 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 25, 16), getRecipeId(), new Object[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getRecipeId() {
/* 102 */     return "ic2:canner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(String outputId, Object... results) {
/* 108 */     if (outputId.equals(getRecipeId())) {
/*     */       
/* 110 */       for (RecipeEntry entry : this.recipeList)
/*     */       {
/* 112 */         this.arecipes.add(new CannerRecipe(entry));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 117 */       super.loadCraftingRecipes(outputId, results);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCraftingRecipes(ItemStack result) {
/* 124 */     for (RecipeEntry entry : this.recipeList) {
/*     */       
/* 126 */       if (NEIServerUtils.areStacksSameTypeCrafting(entry.output, result))
/*     */       {
/* 128 */         this.arecipes.add(new CannerRecipe(entry));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadUsageRecipes(ItemStack ingredient) {
/* 136 */     for (RecipeEntry entry : this.recipeList) {
/*     */       
/* 138 */       if (NEIServerUtils.areStacksSameTypeCrafting(entry.input, ingredient) || (entry.container != null && NEIServerUtils.areStacksSameTypeCrafting(entry.container, ingredient)))
/*     */       {
/* 140 */         this.arecipes.add(new CannerRecipe(entry));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public class CannerRecipe
/*     */     extends TemplateRecipeHandler.CachedRecipe {
/*     */     CanningRecipeHandler.RecipeEntry recipe;
/*     */     
/*     */     public CannerRecipe(CanningRecipeHandler.RecipeEntry entry) {
/* 150 */       super(CanningRecipeHandler.this);
/* 151 */       this.recipe = entry;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public List<PositionedStack> getIngredients() {
/* 157 */       List<PositionedStack> list = super.getIngredients();
/* 158 */       list.add(new PositionedStack(this.recipe.input, 64, 6));
/* 159 */       if (this.recipe.container != null)
/*     */       {
/* 161 */         list.add(new PositionedStack(this.recipe.container, 64, 42));
/*     */       }
/* 163 */       list.add(new PositionedStack(Ic2Items.chargedReBattery, 25, 34));
/* 164 */       return list;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PositionedStack getResult() {
/* 170 */       return new PositionedStack(this.recipe.output, 115, 24);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class RecipeEntry
/*     */   {
/*     */     ItemStack container;
/*     */     
/*     */     ItemStack input;
/*     */     ItemStack output;
/*     */     String extraInfo;
/*     */     
/*     */     public RecipeEntry(ItemStack input, ItemStack output, String extraInfo) {
/* 184 */       this.input = input;
/* 185 */       this.output = output;
/* 186 */       this.extraInfo = extraInfo;
/*     */     }
/*     */ 
/*     */     
/*     */     public RecipeEntry setContainer(ItemStack par1) {
/* 191 */       this.container = par1;
/* 192 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void load() {
/* 198 */     loadFood();
/* 199 */     loadFuelCan();
/*     */   }
/*     */ 
/*     */   
/*     */   void loadFuelCan() {
/* 204 */     for (Map.Entry<TileEntityElectrolyzer.ItemRecipe, Integer> entry : (Iterable<Map.Entry<TileEntityElectrolyzer.ItemRecipe, Integer>>)TileEntityCanner.fuelValues.entrySet()) {
/*     */       
/* 206 */       this.recipeList.add((new RecipeEntry(((TileEntityElectrolyzer.ItemRecipe)entry.getKey()).toItem(), Ic2Items.filledFuelCan, "+" + entry.getValue() + " Fuel")).setContainer(Ic2Items.fuelCan));
/* 207 */       this.recipeList.add((new RecipeEntry(((TileEntityElectrolyzer.ItemRecipe)entry.getKey()).toItem(), Ic2Items.jetpack, "+" + entry.getValue() + " Fuel")).setContainer(Ic2Items.jetpack));
/*     */     } 
/* 209 */     for (Map.Entry<TileEntityElectrolyzer.ItemRecipe, Float> entry : (Iterable<Map.Entry<TileEntityElectrolyzer.ItemRecipe, Float>>)TileEntityCanner.fuelMultiplyers.entrySet()) {
/*     */       
/* 211 */       this.recipeList.add((new RecipeEntry(((TileEntityElectrolyzer.ItemRecipe)entry.getKey()).toItem(), Ic2Items.filledFuelCan, "+" + (int)(((Float)entry.getValue()).floatValue() * 100.0F) + "% Fuel Multiplier")).setContainer(Ic2Items.fuelCan));
/* 212 */       this.recipeList.add((new RecipeEntry(((TileEntityElectrolyzer.ItemRecipe)entry.getKey()).toItem(), Ic2Items.jetpack, "+" + (int)(((Float)entry.getValue()).floatValue() * 100.0F) + "% Fuel Multiplier")).setContainer(Ic2Items.jetpack));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     this.recipeList.add((new RecipeEntry(Ic2Items.constructionFoamPellet, Ic2Items.cfPack, "2 Points of CF")).setContainer(Ic2Items.cfPack));
/* 225 */     this.recipeList.add((new RecipeEntry(Ic2Items.constructionFoamPellet, Ic2Items.constructionFoamSprayer, "2 Points of CF")).setContainer(Ic2Items.constructionFoamSprayer));
/*     */   }
/*     */ 
/*     */   
/*     */   void loadFood() {
/* 230 */     Iterator<Item> iter = Item.field_150901_e.iterator();
/* 231 */     while (iter.hasNext()) {
/*     */       
/* 233 */       Item item = iter.next();
/* 234 */       if (item instanceof ic2.core.item.ItemTinCan) {
/*     */         continue;
/*     */       }
/*     */       
/* 238 */       if (!(item instanceof ItemFood)) {
/*     */         continue;
/*     */       }
/*     */       
/* 242 */       List<ItemStack> items = new ArrayList<>();
/* 243 */       item.func_150895_a(item, null, items);
/* 244 */       for (int i = 0; i < items.size(); i++) {
/*     */         
/* 246 */         ItemStack stack = items.get(i);
/* 247 */         if (stack != null) {
/*     */ 
/*     */ 
/*     */           
/* 251 */           int stackSize = getFoodValue(stack);
/* 252 */           int metadata = getFoodMeta(stack);
/* 253 */           if (stackSize > 0)
/*     */           {
/* 255 */             this.recipeList.add((new RecipeEntry(stack, new ItemStack(Ic2Items.filledTinCan.func_77973_b(), stackSize, metadata), "")).setContainer(Ic2Items.tinCan));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getFoodValue(ItemStack item) {
/* 263 */     if (item.func_77973_b() instanceof ItemFood) {
/*     */       
/* 265 */       ItemFood food = (ItemFood)item.func_77973_b();
/* 266 */       return (int)Math.ceil(food.func_150905_g(item) / 2.0D);
/*     */     } 
/* 268 */     if (item.func_77973_b() == Items.field_151105_aU || item.func_77973_b() == Item.func_150898_a(Blocks.field_150414_aQ))
/*     */     {
/* 270 */       return 6;
/*     */     }
/* 272 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFoodMeta(ItemStack item) {
/* 277 */     if (item == null)
/*     */     {
/* 279 */       return 0;
/*     */     }
/* 281 */     TileEntityElectrolyzer.ItemRecipe ccip = new TileEntityElectrolyzer.ItemRecipe(item);
/* 282 */     return TileEntityCanner.specialFood.containsKey(ccip) ? ((Integer)TileEntityCanner.specialFood.get(ccip)).intValue() : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\CanningRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */