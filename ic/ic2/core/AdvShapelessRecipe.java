/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.recipe.ICraftingRecipeManagerList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvShapelessRecipe
/*     */   implements IRecipe, ICraftingRecipeManagerList.IAdvRecipe
/*     */ {
/*     */   public ItemStack output;
/*     */   public boolean hidden;
/*  25 */   public ArrayList input = new ArrayList();
/*  26 */   public List<ICraftingRecipeManagerList.RecipeObject> objects = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public static AdvShapelessRecipe addAndRegister(ItemStack result, Object[] args) {
/*  30 */     AdvShapelessRecipe recipe = new AdvShapelessRecipe(result, args);
/*  31 */     CraftingManager.func_77594_a().func_77592_b().add(recipe);
/*  32 */     return recipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvShapelessRecipe(ItemStack output, Object[] inputs) {
/*  37 */     this.output = output.func_77946_l();
/*  38 */     for (Object obj : inputs) {
/*     */       
/*  40 */       if (obj != null)
/*     */       {
/*  42 */         if (obj instanceof String) {
/*     */           
/*  44 */           String text = (String)obj;
/*  45 */           if (text.startsWith("liquid$"))
/*     */           {
/*  47 */             add(AdvRecipe.getFluids(text));
/*     */           }
/*     */           else
/*     */           {
/*  51 */             add(OreDictionary.getOres(text));
/*     */           }
/*     */         
/*  54 */         } else if (obj instanceof Boolean) {
/*     */           
/*  56 */           this.hidden = ((Boolean)obj).booleanValue();
/*     */         }
/*  58 */         else if (obj instanceof Item) {
/*     */           
/*  60 */           add(new ItemStack((Item)obj, 1, 32767));
/*     */         }
/*  62 */         else if (obj instanceof Block) {
/*     */           
/*  64 */           add(new ItemStack((Block)obj, 1, 32767));
/*     */         }
/*  66 */         else if (obj instanceof ItemStack) {
/*     */           
/*  68 */           add(ItemStack.func_77944_b((ItemStack)obj));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void add(ItemStack par1) {
/*  76 */     this.input.add(par1);
/*  77 */     this.objects.add(new ICraftingRecipeManagerList.RecipeObject(-1, par1));
/*     */   }
/*     */ 
/*     */   
/*     */   private void add(List<ItemStack> par1) {
/*  82 */     this.input.add(par1);
/*  83 */     this.objects.add(new ICraftingRecipeManagerList.RecipeObject(-1, par1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting inventorycrafting, World world) {
/*  90 */     return (func_77572_b(inventorycrafting) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting par1) {
/*  96 */     ArrayList list = new ArrayList(this.input);
/*  97 */     int totalCharge = 0;
/*  98 */     for (int i = 0; i < par1.func_70302_i_(); i++) {
/*     */       
/* 100 */       ItemStack stack = par1.func_70301_a(i);
/* 101 */       if (stack != null) {
/*     */         
/* 103 */         boolean inRecipe = false;
/* 104 */         Iterator iter = list.iterator();
/* 105 */         while (iter.hasNext()) {
/*     */           
/* 107 */           boolean match = false;
/* 108 */           Object next = iter.next();
/* 109 */           if (next instanceof ItemStack) {
/*     */             
/* 111 */             ItemStack recipeInput = (ItemStack)next;
/* 112 */             match = AdvRecipe.ItemsMatch(stack, recipeInput);
/*     */           }
/* 114 */           else if (next instanceof ArrayList) {
/*     */             
/* 116 */             match = AdvRecipe.ItemsMatch(stack, (ArrayList<ItemStack>)next);
/*     */           } 
/* 118 */           if (match) {
/*     */             
/* 120 */             inRecipe = true;
/* 121 */             list.remove(next);
/*     */             break;
/*     */           } 
/*     */         } 
/* 125 */         if (stack.func_77973_b() instanceof ic2.api.item.IElectricItem)
/*     */         {
/* 127 */           totalCharge = (int)(totalCharge + ElectricItem.manager.getCharge(stack));
/*     */         }
/* 129 */         if (!inRecipe)
/*     */         {
/* 131 */           return null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     if (!list.isEmpty())
/*     */     {
/* 138 */       return null;
/*     */     }
/*     */     
/* 141 */     ItemStack out = this.output.func_77946_l();
/* 142 */     if (out.func_77973_b() instanceof ic2.api.item.IElectricItem && totalCharge > 0)
/*     */     {
/* 144 */       ElectricItem.manager.charge(out, totalCharge, 2147483647, true, false);
/*     */     }
/* 146 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77570_a() {
/* 152 */     return this.input.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 158 */     return this.output.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShaped() {
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 170 */     return this.hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ICraftingRecipeManagerList.RecipeObject> getRecipeInput() {
/* 176 */     return new ArrayList<>(this.objects);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeHeight() {
/* 182 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeLength() {
/* 188 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\AdvShapelessRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */