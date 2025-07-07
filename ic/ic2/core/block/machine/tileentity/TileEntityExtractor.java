/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeInputItemStack;
/*     */ import ic2.api.recipe.RecipeInputOreDict;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityExtractor
/*     */   extends TileEntityElectricMachine
/*     */ {
/*  25 */   public static List<AbstractMap.SimpleEntry<ItemStack, ItemStack>> recipes = new Vector<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityExtractor() {
/*  30 */     super(3, 2, 400, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  35 */     if (Ic2Items.rubberSapling != null)
/*     */     {
/*  37 */       addRecipe(Ic2Items.rubberSapling, Ic2Items.rubber);
/*     */     }
/*  39 */     addRecipe(Ic2Items.resin, StackUtil.copyWithSize(Ic2Items.rubber, 3), 0.2F);
/*  40 */     addRecipe(Ic2Items.bioCell, Ic2Items.biofuelCell, 0.1F);
/*  41 */     addRecipe(Ic2Items.hydratedCoalCell, Ic2Items.coalfuelCell, 0.1F);
/*  42 */     addRecipe(Ic2Items.waterCell, Ic2Items.hydratingCell, 0.1F);
/*  43 */     addRecipe("itemRawRubber", 1, StackUtil.copyWithSize(Ic2Items.rubber, 3), 0.2F);
/*  44 */     addRecipe("woodRubber", 1, Ic2Items.rubber, 0.2F);
/*  45 */     addRecipe(new ItemStack(Blocks.field_150365_q), new ItemStack(Items.field_151044_h, 3), 0.3F);
/*  46 */     addRecipe(new ItemStack(Blocks.field_150369_x), new ItemStack(Items.field_151100_aR, 10, 4), 0.5F);
/*  47 */     addRecipe(new ItemStack(Blocks.field_150482_ag), new ItemStack(Items.field_151045_i, 3), 1.0F);
/*  48 */     addRecipe(new ItemStack(Blocks.field_150412_bA), new ItemStack(Items.field_151166_bC, 3), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, ItemStack output) {
/*  53 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, int stacksize, ItemStack output) {
/*  58 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input, stacksize), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(String input, int stacksize, ItemStack output) {
/*  63 */     addRecipe((IRecipeInput)new RecipeInputOreDict(input, stacksize), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, ItemStack output, float exp) {
/*  68 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, int stacksize, ItemStack output, float exp) {
/*  73 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input, stacksize), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(String input, int stacksize, ItemStack output, float exp) {
/*  78 */     addRecipe((IRecipeInput)new RecipeInputOreDict(input, stacksize), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(IRecipeInput input, ItemStack output, float exp) {
/*  83 */     IMachineRecipeManager manager = Recipes.extractor;
/*  84 */     if (manager != null && manager instanceof IMachineRecipeManagerExp) {
/*     */       
/*  86 */       ((IMachineRecipeManagerExp)manager).addRecipe(input, null, exp, new ItemStack[] { output });
/*     */     }
/*     */     else {
/*     */       
/*  90 */       addRecipe(input, output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(IRecipeInput input, ItemStack output) {
/*  96 */     Recipes.extractor.addRecipe(input, null, new ItemStack[] { output });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack itemStack, boolean adjustInput) {
/* 102 */     return Recipes.extractor.getOutputFor(itemStack, adjustInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 108 */     return "Extractor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 114 */     return "block.machine.gui.GuiExtractor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 120 */     return "Machines/ExtractorOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 126 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 131 */     return 0.85F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 137 */     if (par1 == null)
/*     */     {
/* 139 */       return false;
/*     */     }
/* 141 */     for (IRecipeInput input : Recipes.extractor.getRecipes().keySet()) {
/*     */       
/* 143 */       if (input.matches(par1))
/*     */       {
/* 145 */         return super.isValidInput(par1);
/*     */       }
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 154 */     return Recipes.extractor;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */