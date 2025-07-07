/*     */ package ic2.cgIntegration.core;
/*     */ 
/*     */ import ic2.core.AdvRecipe;
/*     */ import ic2.core.AdvShapelessRecipe;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
/*     */ import uristqwerty.CraftGuide.api.ItemSlot;
/*     */ import uristqwerty.CraftGuide.api.RecipeGenerator;
/*     */ import uristqwerty.CraftGuide.api.RecipeProvider;
/*     */ import uristqwerty.CraftGuide.api.RecipeTemplate;
/*     */ import uristqwerty.CraftGuide.api.Slot;
/*     */ 
/*     */ public class AdvRecipeGenerator extends CraftGuideAPIObject implements RecipeProvider {
/*  18 */   private final ItemSlot[] slots = new ItemSlot[] { new ItemSlot(3, 3, 16, 16), new ItemSlot(21, 3, 16, 16), new ItemSlot(39, 3, 16, 16), new ItemSlot(3, 21, 16, 16), new ItemSlot(21, 21, 16, 16), new ItemSlot(39, 21, 16, 16), new ItemSlot(3, 39, 16, 16), new ItemSlot(21, 39, 16, 16), new ItemSlot(39, 39, 16, 16), new ItemSlot(59, 21, 16, 16, true) };
/*     */   
/*  20 */   private final ItemSlot[] smallSlots = new ItemSlot[] { new ItemSlot(12, 12, 16, 16), new ItemSlot(30, 12, 16, 16), new ItemSlot(12, 30, 16, 16), new ItemSlot(30, 30, 16, 16), new ItemSlot(59, 21, 16, 16, true) };
/*     */ 
/*     */   
/*     */   public void generateRecipes(RecipeGenerator generator) {
/*  24 */     RecipeTemplate shapedTemplate = generator.createRecipeTemplate((Slot[])this.slots, Ic2Items.machine.func_77946_l(), "/gui/CraftGuideRecipe.png", 1, 1, 82, 1);
/*  25 */     RecipeTemplate shapedSmallTemplate = generator.createRecipeTemplate((Slot[])this.smallSlots, Ic2Items.machine.func_77946_l(), "/gui/CraftGuideRecipe.png", 1, 61, 82, 61);
/*     */     
/*  27 */     for (Object o : CraftingManager.func_77594_a().func_77592_b()) {
/*     */ 
/*     */       
/*  30 */       if (o instanceof AdvRecipe) {
/*     */         
/*  32 */         AdvRecipe recipe = (AdvRecipe)o;
/*     */         
/*  34 */         if (AdvRecipe.canShow(recipe)) {
/*     */           
/*  36 */           ItemStack[] temp = new ItemStack[9];
/*     */           
/*  38 */           for (int i = 0; i < 9 && i < recipe.input.length; i++) {
/*     */             
/*  40 */             if (recipe.input[i] != null) {
/*     */               
/*  42 */               List<Object> inputs = new ArrayList();
/*  43 */               if (recipe.input[i] instanceof ArrayList) {
/*     */                 
/*  45 */                 inputs.addAll((ArrayList)recipe.input[i]);
/*     */               }
/*     */               else {
/*     */                 
/*  49 */                 inputs.add(recipe.input[i]);
/*     */               } 
/*     */               
/*  52 */               if (inputs.size() > 0) {
/*     */                 
/*  54 */                 temp[i] = (ItemStack)inputs.get(0);
/*     */               }
/*     */               else {
/*     */                 
/*  58 */                 System.out.println("[IC2] craftguide recipe gen: can't find ore dict match for " + recipe.input[i]);
/*     */               } 
/*     */             } 
/*     */           } 
/*  62 */           switch (recipe.length) {
/*     */             
/*     */             case 1:
/*  65 */               temp = new ItemStack[] { null, temp[0], null, null, temp[1], null, null, temp[2], null, recipe.output };
/*     */               break;
/*     */             case 2:
/*  68 */               temp = new ItemStack[] { null, temp[0], temp[1], null, temp[2], temp[3], null, temp[4], temp[5], recipe.output };
/*     */               break;
/*     */             default:
/*  71 */               temp = new ItemStack[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], recipe.output };
/*     */               break;
/*     */           } 
/*  74 */           if (temp[2] == null && temp[5] == null && temp[8] == null && temp[6] == null && temp[7] == null) {
/*     */             
/*  76 */             generator.addRecipe(shapedSmallTemplate, (Object[])new ItemStack[] { temp[0], temp[1], temp[6], temp[7], temp[9] });
/*     */             
/*     */             continue;
/*     */           } 
/*  80 */           generator.addRecipe(shapedTemplate, (Object[])temp);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     RecipeTemplate shapelessTemplate = generator.createRecipeTemplate((Slot[])this.slots, Ic2Items.machine.func_77946_l(), "/gui/CraftGuideRecipe.png", 1, 121, 82, 121);
/*     */     
/*  87 */     for (Object o : CraftingManager.func_77594_a().func_77592_b()) {
/*     */ 
/*     */       
/*  90 */       if (o instanceof AdvShapelessRecipe) {
/*     */         
/*  92 */         AdvShapelessRecipe recipe = (AdvShapelessRecipe)o;
/*     */         
/*  94 */         if (AdvRecipe.canShow(recipe)) {
/*     */           
/*  96 */           ItemStack[] temp = new ItemStack[10];
/*     */           
/*  98 */           for (int i = 0; i < 9 && i < recipe.input.size(); i++) {
/*     */             
/* 100 */             List<ItemStack> inputs = new ArrayList();
/* 101 */             if (recipe.input.get(i) instanceof ArrayList) {
/*     */               
/* 103 */               inputs.addAll(recipe.input.get(i));
/*     */             }
/*     */             else {
/*     */               
/* 107 */               inputs.add(recipe.input.get(i));
/*     */             } 
/* 109 */             if (inputs.size() > 0) {
/*     */               
/* 111 */               temp[i] = inputs.get(0);
/*     */             }
/*     */             else {
/*     */               
/* 115 */               System.out.println("[IC2] craftguide recipe gen: can't find ore dict match for " + recipe.input.get(i));
/*     */             } 
/*     */           } 
/*     */           
/* 119 */           temp[9] = recipe.output;
/*     */           
/* 121 */           generator.addRecipe(shapelessTemplate, (Object[])temp);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cgIntegration\core\AdvRecipeGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */