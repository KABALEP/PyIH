/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeInputItemStack;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityElecFurnace
/*     */   extends TileEntityElectricMachine
/*     */ {
/*  20 */   public static IMachineRecipeManager recipes = (IMachineRecipeManager)new FurnaceRecipeList();
/*     */   
/*     */   public TileEntityElecFurnace() {
/*  23 */     super(3, 3, 130, 32);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjustInput) {
/*  29 */     ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(input);
/*  30 */     if (result == null)
/*     */     {
/*  32 */       return null;
/*     */     }
/*  34 */     if (adjustInput)
/*     */     {
/*  36 */       input.field_77994_a--;
/*     */     }
/*  38 */     return new RecipeOutput(null, new ItemStack[] { result });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  44 */     return "Electric Furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/*  50 */     return "block.machine.gui.GuiElecFurnace";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/*  56 */     return "Machines/Electro Furnace/ElectroFurnaceLoop.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/*  68 */     if (par1 == null)
/*     */     {
/*  70 */       return false;
/*     */     }
/*  72 */     if (FurnaceRecipes.func_77602_a().func_151395_a(par1) != null)
/*     */     {
/*  74 */       return super.isValidInput(par1);
/*     */     }
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/*  82 */     return recipes;
/*     */   }
/*     */   
/*     */   public static class FurnaceRecipeList
/*     */     implements IMachineRecipeManagerExp {
/*     */     boolean didInit = false;
/*  88 */     Map<IRecipeInput, RecipeOutput> recipe = new HashMap<>();
/*  89 */     Map<IRecipeInput, Float> expMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean overwrite, ItemStack... outputs) {
/*  94 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/* 106 */       ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(input);
/* 107 */       if (result == null)
/*     */       {
/* 109 */         return null;
/*     */       }
/* 111 */       if (adjustInput)
/*     */       {
/* 113 */         input.field_77994_a--;
/*     */       }
/* 115 */       return new RecipeOutput(null, new ItemStack[] { result });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<IRecipeInput, RecipeOutput> getRecipes() {
/* 121 */       if (!this.didInit)
/*     */       {
/* 123 */         init();
/*     */       }
/* 125 */       return this.recipe;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addRecipe(IRecipeInput input, NBTTagCompound metadata, float exp, ItemStack... outputs) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getExpResult(ItemStack output) {
/* 137 */       return FurnaceRecipes.func_77602_a().func_151398_b(output);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<IRecipeInput, Float> getExpMap() {
/* 143 */       if (!this.didInit)
/*     */       {
/* 145 */         init();
/*     */       }
/* 147 */       return this.expMap;
/*     */     }
/*     */ 
/*     */     
/*     */     private void init() {
/* 152 */       FurnaceRecipes recipes = FurnaceRecipes.func_77602_a();
/* 153 */       Map<ItemStack, ItemStack> map = recipes.func_77599_b();
/* 154 */       for (Map.Entry<ItemStack, ItemStack> entry : map.entrySet()) {
/*     */         
/* 156 */         RecipeInputItemStack recipeInputItemStack = new RecipeInputItemStack(entry.getKey());
/* 157 */         ItemStack out = entry.getValue();
/* 158 */         this.recipe.put(recipeInputItemStack, new RecipeOutput(null, new ItemStack[] { out }));
/* 159 */         float exp = recipes.func_151398_b(out);
/* 160 */         if (exp > 0.0F)
/*     */         {
/* 162 */           this.expMap.put(new RecipeInputItemStack(out), Float.valueOf(exp));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityElecFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */