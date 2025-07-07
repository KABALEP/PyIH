/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.recipe.ICraftingRecipeManagerList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvRecipe
/*     */   implements IRecipe, ICraftingRecipeManagerList.IAdvRecipe
/*     */ {
/*     */   private static final int MAX_CRAFT_GRID_WIDTH = 3;
/*     */   private static final int MAX_CRAFT_GRID_HEIGHT = 3;
/*     */   public ItemStack output;
/*     */   public Object[] input;
/*     */   public int height;
/*     */   public int length;
/*     */   public boolean hidden;
/*  38 */   public List<ICraftingRecipeManagerList.RecipeObject> objects = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public static AdvRecipe addAndRegister(ItemStack result, Object[] args) {
/*  42 */     AdvRecipe recipe = new AdvRecipe(result, args);
/*  43 */     CraftingManager.func_77594_a().func_77592_b().add(recipe);
/*  44 */     return recipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvRecipe(ItemStack par1, Object[] par2) {
/*  49 */     this.output = par1.func_77946_l();
/*  50 */     List<String> template = new ArrayList<>();
/*  51 */     Character lastChar = null;
/*  52 */     Map<Character, Object> items = new HashMap<>();
/*  53 */     for (Object obj : par2) {
/*     */       
/*  55 */       if (obj != null)
/*     */       {
/*  57 */         if (obj instanceof String) {
/*     */           
/*  59 */           if (lastChar == null)
/*     */           {
/*  61 */             template.add((String)obj);
/*     */           }
/*     */           else
/*     */           {
/*  65 */             String text = (String)obj;
/*  66 */             if (text.startsWith("liquid$")) {
/*     */               
/*  68 */               items.put(lastChar, getFluids(text));
/*     */             }
/*     */             else {
/*     */               
/*  72 */               items.put(lastChar, OreDictionary.getOres(text));
/*     */             } 
/*  74 */             lastChar = null;
/*     */           }
/*     */         
/*  77 */         } else if (obj instanceof Character) {
/*     */           
/*  79 */           lastChar = (Character)obj;
/*     */         }
/*  81 */         else if (obj instanceof Boolean) {
/*     */           
/*  83 */           this.hidden = ((Boolean)obj).booleanValue();
/*     */         }
/*  85 */         else if (obj instanceof Item) {
/*     */           
/*  87 */           items.put(lastChar, new ItemStack((Item)obj, 1, 32767));
/*  88 */           lastChar = null;
/*     */         }
/*  90 */         else if (obj instanceof Block) {
/*     */           
/*  92 */           items.put(lastChar, new ItemStack((Block)obj, 1, 32767));
/*  93 */           lastChar = null;
/*     */         }
/*  95 */         else if (obj instanceof ItemStack) {
/*     */           
/*  97 */           ItemStack stack = (ItemStack)obj;
/*  98 */           stack = ItemStack.func_77944_b(stack);
/*  99 */           if (stack.func_77973_b() instanceof ic2.api.item.IElectricItem && !(stack.func_77973_b() instanceof ic2.api.item.ISpecialElectricItem)) {
/*     */             
/* 101 */             stack.func_77964_b(32767);
/* 102 */             items.put(lastChar, stack);
/* 103 */             lastChar = null;
/*     */           }
/*     */           else {
/*     */             
/* 107 */             items.put(lastChar, stack);
/* 108 */             lastChar = null;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 113 */     if (template.size() == 0 || template.size() > 3)
/*     */     {
/* 115 */       throw new RuntimeException("Template is not Matching");
/*     */     }
/* 117 */     String shape = "";
/* 118 */     for (String key : template) {
/*     */       
/* 120 */       shape = shape + key;
/* 121 */       this.length = key.length();
/* 122 */       this.height++;
/*     */     } 
/* 124 */     if (this.length * this.height != shape.length())
/*     */     {
/* 126 */       throw new RuntimeException("Template is not Matching");
/*     */     }
/* 128 */     this.input = new Object[this.length * this.height];
/* 129 */     int x = 0;
/* 130 */     for (char chars : shape.toCharArray()) {
/*     */       
/* 132 */       Object obj = items.get(Character.valueOf(chars));
/* 133 */       this.input[x] = obj;
/* 134 */       addObject(x, obj);
/* 135 */       x++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addObject(int slot, Object item) {
/* 142 */     if (item instanceof ItemStack) {
/*     */       
/* 144 */       this.objects.add(new ICraftingRecipeManagerList.RecipeObject(slot, (ItemStack)item));
/*     */     }
/* 146 */     else if (item instanceof ArrayList) {
/*     */       
/* 148 */       this.objects.add(new ICraftingRecipeManagerList.RecipeObject(slot, (ArrayList)item));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting inv, World world) {
/* 155 */     for (int x = 0; x <= 3 - this.length; x++) {
/*     */       
/* 157 */       for (int y = 0; y <= 3 - this.height; y++) {
/*     */         
/* 159 */         if (checkMatch(inv, x, y))
/*     */         {
/* 161 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkMatch(InventoryCrafting inv, int startX, int startY) {
/* 170 */     for (int x = 0; x < 3; x++) {
/*     */       
/* 172 */       for (int y = 0; y < 3; y++) {
/*     */         
/* 174 */         int subX = x - startX;
/* 175 */         int subY = y - startY;
/* 176 */         Object target = null;
/*     */         
/* 178 */         if (subX >= 0 && subY >= 0 && subX < this.length && subY < this.height)
/*     */         {
/* 180 */           target = this.input[subX + subY * this.length];
/*     */         }
/*     */         
/* 183 */         ItemStack slot = inv.func_70463_b(x, y);
/*     */         
/* 185 */         if (target instanceof ItemStack) {
/*     */           
/* 187 */           if (!ItemsMatch((ItemStack)target, slot))
/*     */           {
/* 189 */             return false;
/*     */           }
/*     */         }
/* 192 */         else if (target instanceof ArrayList) {
/*     */           
/* 194 */           boolean matched = false;
/*     */           
/* 196 */           for (ItemStack item : target)
/*     */           {
/* 198 */             matched = (matched || ItemsMatch(item, slot));
/*     */           }
/*     */           
/* 201 */           if (!matched)
/*     */           {
/* 203 */             return false;
/*     */           }
/*     */         }
/* 206 */         else if (target == null && slot != null) {
/*     */           
/* 208 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting par1) {
/* 219 */     int totalCharge = 0;
/* 220 */     for (int i = 0; i < par1.func_70302_i_(); i++) {
/*     */       
/* 222 */       ItemStack stack = par1.func_70301_a(i);
/* 223 */       if (stack != null && stack.func_77973_b() instanceof ic2.api.item.IElectricItem)
/*     */       {
/* 225 */         totalCharge = (int)(totalCharge + ElectricItem.manager.getCharge(stack));
/*     */       }
/*     */     } 
/* 228 */     ItemStack out = this.output.func_77946_l();
/* 229 */     if (out.func_77973_b() instanceof ic2.api.item.IElectricItem && totalCharge > 0)
/*     */     {
/* 231 */       ElectricItem.manager.charge(out, totalCharge, 2147483647, true, false);
/*     */     }
/* 233 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77570_a() {
/* 239 */     return this.input.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 245 */     return this.output.func_77946_l();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canShow(Object[] input, ItemStack output, boolean hidden) {
/* 250 */     return ((!IC2.enableSecretRecipeHiding || !hidden) && !recipeContains(input, Ic2Items.chargedReBattery) && (!recipeContains(input, Ic2Items.industrialDiamond) || output.func_77973_b() != Items.field_151045_i));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean recipeContains(Object[] inputs, ItemStack item) {
/* 255 */     for (Object input : inputs) {
/*     */       
/* 257 */       if (input != null)
/*     */       {
/* 259 */         if (input instanceof ItemStack) {
/*     */           
/* 261 */           if (ItemsMatch((ItemStack)input, item))
/*     */           {
/* 263 */             return true;
/*     */           }
/*     */         }
/* 266 */         else if (input instanceof ArrayList) {
/*     */           
/* 268 */           if (ItemsMatch(item, (ArrayList<ItemStack>)input))
/*     */           {
/* 270 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canShow(AdvRecipe recipe) {
/* 280 */     return canShow(recipe.input, recipe.output, recipe.hidden);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canShow(AdvShapelessRecipe recipe) {
/* 285 */     return canShow(recipe.input.toArray(), recipe.output, recipe.hidden);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean ItemsMatch(ItemStack par1, ItemStack par2) {
/* 290 */     if (par1 != null && par2 != null && par1.func_77973_b() == par2.func_77973_b())
/*     */     {
/* 292 */       if (par1.func_77960_j() == par2.func_77960_j() || par1.func_77960_j() == 32767 || par2.func_77960_j() == 32767)
/*     */       {
/* 294 */         return true;
/*     */       }
/*     */     }
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean ItemsMatch(ItemStack par1, ArrayList<ItemStack> par2) {
/* 302 */     for (ItemStack stack : par2) {
/*     */       
/* 304 */       if (ItemsMatch(stack, par1))
/*     */       {
/* 306 */         return true;
/*     */       }
/*     */     } 
/* 309 */     return false;
/*     */   }
/*     */   
/*     */   public static ArrayList<ItemStack> getFluids(String text) {
/*     */     int id;
/* 314 */     ArrayList<ItemStack> list = new ArrayList<>();
/* 315 */     int colon = text.indexOf(':');
/*     */ 
/*     */     
/* 318 */     if (colon != -1) {
/*     */       
/* 320 */       id = Integer.valueOf(text.substring(7, colon - 1)).intValue();
/* 321 */       int meta = Integer.valueOf(text.substring(colon + 1)).intValue();
/*     */     }
/*     */     else {
/*     */       
/* 325 */       id = Integer.valueOf(text.substring(7)).intValue();
/* 326 */       int meta = -1;
/*     */     } 
/* 328 */     for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/*     */       
/* 330 */       FluidStack stack = data.fluid;
/* 331 */       if (stack != null && stack.getFluid() != null && stack.getFluid().canBePlacedInWorld())
/*     */       {
/* 333 */         if (id == Block.func_149682_b(stack.getFluid().getBlock()))
/*     */         {
/* 335 */           list.add(data.filledContainer);
/*     */         }
/*     */       }
/*     */     } 
/* 339 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShaped() {
/* 345 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 351 */     return this.hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ICraftingRecipeManagerList.RecipeObject> getRecipeInput() {
/* 357 */     return new ArrayList<>(this.objects);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeHeight() {
/* 363 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeLength() {
/* 369 */     return this.length;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\AdvRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */