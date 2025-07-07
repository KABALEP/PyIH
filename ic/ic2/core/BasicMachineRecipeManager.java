/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMachineRecipeManager
/*     */   implements IMachineRecipeManagerExp
/*     */ {
/*  21 */   private final Map<IRecipeInput, RecipeOutput> recipes = new HashMap<>();
/*  22 */   private final Map<IRecipeInput, Float> expList = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs) {
/*  27 */     addRecipe(input, metadata, 0.0F, outputs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/*  33 */     if (input == null)
/*     */     {
/*  35 */       return null;
/*     */     }
/*  37 */     for (Map.Entry<IRecipeInput, RecipeOutput> recipe : this.recipes.entrySet()) {
/*     */       
/*  39 */       IRecipeInput recipeInput = recipe.getKey();
/*  40 */       if (recipeInput.matches(input)) {
/*     */         
/*  42 */         if (input.field_77994_a < recipeInput.getAmount() || (input.func_77973_b().hasContainerItem(input) && input.field_77994_a != recipeInput.getAmount())) {
/*     */           break;
/*     */         }
/*     */         
/*  46 */         if (adjustInput)
/*     */         {
/*  48 */           if (input.func_77973_b().hasContainerItem(input)) {
/*     */             
/*  50 */             ItemStack container = input.func_77973_b().getContainerItem(input);
/*     */             
/*  52 */             input.func_150996_a(container.func_77973_b());
/*  53 */             input.field_77994_a = container.field_77994_a;
/*  54 */             input.func_77964_b(container.func_77960_j());
/*  55 */             input.field_77990_d = container.field_77990_d;
/*     */           }
/*     */           else {
/*     */             
/*  59 */             input.field_77994_a -= recipeInput.getAmount();
/*     */           } 
/*     */         }
/*  62 */         return recipe.getValue();
/*     */       } 
/*     */     } 
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<IRecipeInput, RecipeOutput> getRecipes() {
/*  71 */     return this.recipes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean overwrite, ItemStack... outputs) {
/*  77 */     addRecipe(input, metadata, outputs);
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   public class RecipeInputItemList
/*     */     implements IRecipeInput
/*     */   {
/*  84 */     public final List<ItemStack> items = new ArrayList<>();
/*     */ 
/*     */     
/*     */     public RecipeInputItemList(RecipeOutput par1) {
/*  88 */       this.items.addAll(par1.items);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(ItemStack subject) {
/*  94 */       for (ItemStack stack : this.items) {
/*     */         
/*  96 */         if (stack.func_77969_a(subject) && ItemStack.func_77970_a(stack, subject))
/*     */         {
/*  98 */           return true;
/*     */         }
/*     */       } 
/* 101 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAmount() {
/* 107 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public List<ItemStack> getInputs() {
/* 113 */       return Collections.unmodifiableList(this.items);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRecipe(IRecipeInput input, NBTTagCompound metadata, float exp, ItemStack... outputs) {
/* 122 */     if (input == null)
/*     */     {
/* 124 */       throw new NullPointerException("The recipe input is null");
/*     */     }
/* 126 */     for (int i = 0; i < outputs.length; i++) {
/*     */       
/* 128 */       if (outputs[i] == null)
/*     */       {
/* 130 */         throw new NullPointerException("The output ItemStack #" + i + " is null (counting from 0)");
/*     */       }
/* 132 */       outputs[i] = outputs[i].func_77946_l();
/*     */     } 
/* 134 */     RecipeOutput out = new RecipeOutput(metadata, outputs);
/* 135 */     this.recipes.put(input, out);
/* 136 */     if (exp > 0.0F)
/*     */     {
/* 138 */       this.expList.put(new RecipeInputItemList(out), Float.valueOf(exp));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExpResult(ItemStack output) {
/* 145 */     if (output == null)
/*     */     {
/* 147 */       return 0.0F;
/*     */     }
/* 149 */     for (Map.Entry<IRecipeInput, Float> entry : this.expList.entrySet()) {
/*     */       
/* 151 */       IRecipeInput input = entry.getKey();
/* 152 */       if (input.matches(output))
/*     */       {
/* 154 */         return ((Float)entry.getValue()).floatValue();
/*     */       }
/*     */     } 
/* 157 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<IRecipeInput, Float> getExpMap() {
/* 163 */     return this.expList;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\BasicMachineRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */