/*     */ package ic2.peIntigration.core;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import ic2.api.recipe.ICraftingRecipeManagerList;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.AdvCraftingRecipeManager;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.EntityDynamite;
/*     */ import ic2.core.block.EntityItnt;
/*     */ import ic2.core.block.EntityNuke;
/*     */ import ic2.core.block.EntityStickyDynamite;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarHV;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarLV;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarMV;
/*     */ import ic2.core.block.wiring.TileEntityCableDetector;
/*     */ import ic2.core.item.tool.EntityMiningLaser;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import moze_intel.projecte.api.ProjectEAPI;
/*     */ import moze_intel.projecte.api.proxy.IBlacklistProxy;
/*     */ import moze_intel.projecte.api.proxy.IConversionProxy;
/*     */ import moze_intel.projecte.api.proxy.IEMCProxy;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeLoader
/*     */ {
/*     */   public static void init() {
/*  42 */     if (!loadBlackList()) {
/*     */       
/*  44 */       FMLLog.getLogger().info("Did Not Load 0");
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     if (!loadCustomEmc()) {
/*     */       
/*  50 */       FMLLog.getLogger().info("Did Not Load 1");
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     if (!loadRecipeEmc()) {
/*     */       
/*  56 */       FMLLog.getLogger().info("Did Not Load 2");
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean loadCustomEmc() {
/*  63 */     IEMCProxy proxy = ProjectEAPI.getEMCProxy();
/*  64 */     if (proxy == null)
/*     */     {
/*  66 */       return false;
/*     */     }
/*  68 */     proxy.registerCustomEMC(Ic2Items.resin.func_77946_l(), 48);
/*  69 */     proxy.registerCustomEMC(Ic2Items.rubber.func_77946_l(), 16);
/*  70 */     if (Ic2Items.rubberSapling != null)
/*     */     {
/*  72 */       proxy.registerCustomEMC(Ic2Items.rubberSapling.func_77946_l(), 32);
/*     */     }
/*  74 */     if (Ic2Items.rubberWood != null)
/*     */     {
/*  76 */       proxy.registerCustomEMC(Ic2Items.rubberWood.func_77946_l(), 24);
/*     */     }
/*  78 */     proxy.registerCustomEMC(Ic2Items.electrolyzedWaterCell.func_77946_l(), 64);
/*  79 */     proxy.registerCustomEMC(Ic2Items.goldDust.func_77946_l(), 2048);
/*  80 */     proxy.registerCustomEMC(Ic2Items.ironDust.func_77946_l(), 256);
/*  81 */     proxy.registerCustomEMC(Ic2Items.copperDust.func_77946_l(), 128);
/*  82 */     proxy.registerCustomEMC(Ic2Items.tinDust.func_77946_l(), 256);
/*  83 */     proxy.registerCustomEMC(Ic2Items.silverDust.func_77946_l(), 512);
/*  84 */     proxy.registerCustomEMC(Ic2Items.specialFertilzer.func_77946_l(), 1024);
/*  85 */     proxy.registerCustomEMC(Ic2Items.clayDust.func_77946_l(), 32);
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean loadRecipeEmc() {
/*  91 */     IConversionProxy convert = ProjectEAPI.getConversionProxy();
/*  92 */     if (convert == null)
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     for (ICraftingRecipeManagerList.IAdvRecipe recipe : ((AdvCraftingRecipeManager)Recipes.advRecipes).getRecipeList()) {
/*     */       
/*  98 */       if (isBlackListed(((IRecipe)recipe).func_77571_b())) {
/*     */         continue;
/*     */       }
/*     */       
/* 102 */       Map<Object, Integer> amounts = new HashMap<>();
/* 103 */       List<ICraftingRecipeManagerList.RecipeObject> recipeObjects = recipe.getRecipeInput();
/* 104 */       for (ICraftingRecipeManagerList.RecipeObject recipeItem : recipeObjects) {
/*     */         
/* 106 */         Object obj = convertBack(recipeItem.getItems());
/* 107 */         if (obj == null || isBlackListed(obj)) {
/*     */           continue;
/*     */         }
/*     */         
/* 111 */         if (amounts.containsKey(obj)) {
/*     */           
/* 113 */           amounts.put(obj, Integer.valueOf(((Integer)amounts.get(obj)).intValue() + 1));
/*     */           continue;
/*     */         } 
/* 116 */         amounts.put(obj, Integer.valueOf(1));
/*     */       } 
/* 118 */       ItemStack stack = ((IRecipe)recipe).func_77571_b();
/* 119 */       convert.addConversion(stack.field_77994_a, StackUtil.copyWithSize(stack, 1), amounts);
/*     */     } 
/* 121 */     IMachineRecipeManager[] managers = { Recipes.macerator, Recipes.extractor, Recipes.compressor };
/* 122 */     for (IMachineRecipeManager manager : managers) {
/*     */       
/* 124 */       Map<IRecipeInput, RecipeOutput> recipeMap = new HashMap<>(manager.getRecipes());
/* 125 */       for (Map.Entry<IRecipeInput, RecipeOutput> recipe : recipeMap.entrySet()) {
/*     */         
/* 127 */         Object input = convertBack(((IRecipeInput)recipe.getKey()).getInputs());
/* 128 */         if (input == null || isBlackListed(input)) {
/*     */           continue;
/*     */         }
/*     */         
/* 132 */         RecipeOutput out = recipe.getValue();
/* 133 */         if (out == null || out.items == null || out.items.size() == 0 || isBlackListed(out.items.get(0))) {
/*     */           continue;
/*     */         }
/*     */         
/* 137 */         ItemStack output = out.items.get(0);
/* 138 */         Map<Object, Integer> amounts = new HashMap<>();
/* 139 */         amounts.put(input, Integer.valueOf(((IRecipeInput)recipe.getKey()).getAmount()));
/* 140 */         convert.addConversion(output.field_77994_a, StackUtil.copyWithSize(output, 1), amounts);
/*     */       } 
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean loadBlackList() {
/* 148 */     IBlacklistProxy proxy = ProjectEAPI.getBlacklistProxy();
/* 149 */     if (proxy == null) {
/*     */       
/* 151 */       FMLLog.getLogger().info("Could not Blacklist Proxy. ProjectE support stops");
/* 152 */       return false;
/*     */     } 
/* 154 */     proxy.blacklistSwiftwolf(EntityStickyDynamite.class);
/* 155 */     proxy.blacklistSwiftwolf(EntityDynamite.class);
/* 156 */     proxy.blacklistSwiftwolf(EntityItnt.class);
/* 157 */     proxy.blacklistSwiftwolf(EntityNuke.class);
/* 158 */     proxy.blacklistSwiftwolf(EntityMiningLaser.class);
/* 159 */     proxy.blacklistInterdiction(EntityStickyDynamite.class);
/* 160 */     proxy.blacklistInterdiction(EntityDynamite.class);
/* 161 */     proxy.blacklistInterdiction(EntityItnt.class);
/* 162 */     proxy.blacklistInterdiction(EntityNuke.class);
/* 163 */     proxy.blacklistInterdiction(EntityMiningLaser.class);
/* 164 */     proxy.blacklistTimeWatch(TileEntityCableDetector.class);
/* 165 */     proxy.blacklistTimeWatch(TileEntityGeoGenerator.class);
/* 166 */     proxy.blacklistTimeWatch(TileEntitySolarLV.class);
/* 167 */     proxy.blacklistTimeWatch(TileEntitySolarMV.class);
/* 168 */     proxy.blacklistTimeWatch(TileEntitySolarHV.class);
/* 169 */     proxy.blacklistTimeWatch(TileEntitySolarGenerator.class);
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBlackListed(Object par1) {
/* 175 */     if (par1 instanceof ItemStack) {
/*     */       
/* 177 */       ItemStack stack = (ItemStack)par1;
/* 178 */       if (stack.func_77969_a(Ic2Items.matter) || stack.func_77969_a(Ic2Items.scrap) || stack.func_77969_a(Ic2Items.scrapMetal) || stack.func_77969_a(Ic2Items.scrapBox))
/*     */       {
/* 180 */         return true;
/*     */       }
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object convertBack(List<ItemStack> items) {
/* 188 */     int size = (items == null) ? 0 : items.size();
/* 189 */     if (size == 0)
/*     */     {
/* 191 */       return null;
/*     */     }
/* 193 */     if (size == 1)
/*     */     {
/* 195 */       return items.get(0);
/*     */     }
/* 197 */     if (size > 1) {
/*     */       
/* 199 */       ItemStack item = items.get(0);
/* 200 */       if (FluidContainerRegistry.isFilledContainer(item))
/*     */       {
/* 202 */         return FluidContainerRegistry.getFluidForFilledItem(item);
/*     */       }
/* 204 */       if (StackUtil.hasOreID(item))
/*     */       {
/* 206 */         return StackUtil.getFirstOreID(item);
/*     */       }
/*     */     } 
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\peIntigration\core\RecipeLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */