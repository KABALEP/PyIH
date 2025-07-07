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
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class TileEntityMacerator
/*     */   extends TileEntityElectricMachine
/*     */ {
/*     */   public TileEntityMacerator() {
/*  22 */     super(3, 2, 400, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  27 */     addRecipe(new ItemStack(Items.field_151044_h), Ic2Items.coalDust, 0.2F);
/*  28 */     addRecipe(new ItemStack(Items.field_151042_j), Ic2Items.ironDust, 1.0F);
/*  29 */     addRecipe(Ic2Items.refinedIronIngot.func_77946_l(), Ic2Items.ironDust.func_77946_l());
/*  30 */     addRecipe(new ItemStack(Items.field_151043_k), Ic2Items.goldDust, 0.7F);
/*  31 */     addRecipe(new ItemStack(Blocks.field_150325_L), new ItemStack(Items.field_151007_F, 3), 0.1F);
/*  32 */     addRecipe(new ItemStack(Blocks.field_150351_n), new ItemStack(Items.field_151145_ak), 0.1F);
/*  33 */     addRecipe(new ItemStack(Blocks.field_150348_b), new ItemStack(Blocks.field_150347_e), 0.1F);
/*  34 */     addRecipe(new ItemStack(Blocks.field_150347_e), new ItemStack((Block)Blocks.field_150354_m), 0.1F);
/*  35 */     addRecipe(new ItemStack(Blocks.field_150322_A), new ItemStack((Block)Blocks.field_150354_m), 0.1F);
/*  36 */     addRecipe(new ItemStack(Blocks.field_150432_aD), new ItemStack(Items.field_151126_ay), 0.1F);
/*  37 */     addRecipe(new ItemStack(Blocks.field_150435_aG), StackUtil.copyWithSize(Ic2Items.clayDust, 2), 0.3F);
/*  38 */     addRecipe(new ItemStack(Blocks.field_150426_aN), new ItemStack(Items.field_151114_aO, 4), 0.15F);
/*  39 */     addRecipe(new ItemStack(Items.field_151103_aS), new ItemStack(Items.field_151100_aR, 5, 15), 0.2F);
/*  40 */     addRecipe(Ic2Items.plantBall, new ItemStack(Blocks.field_150346_d, 8), 0.1F);
/*  41 */     addRecipe(Ic2Items.coffeeBeans, new ItemStack(Ic2Items.coffeePowder.func_77973_b(), 3), 0.4F);
/*  42 */     addRecipe(new ItemStack(Items.field_151072_bj), new ItemStack(Items.field_151065_br, 5), 0.3F);
/*  43 */     addRecipe(new ItemStack(Items.field_151070_bp), new ItemStack(Ic2Items.grinPowder.func_77973_b(), 2), 0.4F);
/*  44 */     addRecipe("oreGold", 1, StackUtil.copyWithSize(Ic2Items.goldDust, 2), 1.0F);
/*  45 */     addRecipe("oreIron", 1, StackUtil.copyWithSize(Ic2Items.ironDust, 2), 0.7F);
/*  46 */     addRecipe(new ItemStack(Blocks.field_150365_q), StackUtil.copyWithSize(Ic2Items.coalDust, 2), 0.2F);
/*  47 */     addRecipe(new ItemStack(Blocks.field_150402_ci), StackUtil.copyWithSize(Ic2Items.coalDust, 9), 0.2F);
/*  48 */     addRecipe(new ItemStack(Blocks.field_150450_ax), StackUtil.copyWithSize(new ItemStack(Items.field_151137_ax), 16), 0.7F);
/*  49 */     addRecipe(new ItemStack(Blocks.field_150343_Z), Ic2Items.obsidianDust, 0.1F);
/*  50 */     addRecipe("cropWheat", 1, StackUtil.copyWithSize(Ic2Items.flour, 1), 0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, ItemStack output) {
/*  55 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, int stacksize, ItemStack output) {
/*  60 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input, stacksize), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(String input, int stacksize, ItemStack output) {
/*  65 */     addRecipe((IRecipeInput)new RecipeInputOreDict(input, stacksize), output);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, ItemStack output, float exp) {
/*  70 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(ItemStack input, int stacksize, ItemStack output, float exp) {
/*  75 */     addRecipe((IRecipeInput)new RecipeInputItemStack(input, stacksize), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(String input, int stacksize, ItemStack output, float exp) {
/*  80 */     addRecipe((IRecipeInput)new RecipeInputOreDict(input, stacksize), output, exp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(IRecipeInput input, ItemStack output, float exp) {
/*  85 */     IMachineRecipeManager manager = Recipes.macerator;
/*  86 */     if (manager != null && manager instanceof IMachineRecipeManagerExp) {
/*     */       
/*  88 */       ((IMachineRecipeManagerExp)manager).addRecipe(input, null, exp, new ItemStack[] { output });
/*     */     }
/*     */     else {
/*     */       
/*  92 */       addRecipe(input, output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRecipe(IRecipeInput input, ItemStack output) {
/*  98 */     Recipes.macerator.addRecipe(input, null, new ItemStack[] { output });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack itemStack, boolean adjustInput) {
/* 104 */     return Recipes.macerator.getOutputFor(itemStack, adjustInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 110 */     return "Macerator";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 116 */     return "block.machine.gui.GuiMacerator";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 122 */     return "Machines/MaceratorOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 128 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 133 */     return 0.85F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 139 */     if (par1 == null)
/*     */     {
/* 141 */       return false;
/*     */     }
/* 143 */     for (IRecipeInput input : Recipes.macerator.getRecipes().keySet()) {
/*     */       
/* 145 */       if (input.matches(par1))
/*     */       {
/* 147 */         return super.isValidInput(par1);
/*     */       }
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 156 */     return Recipes.macerator;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityMacerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */