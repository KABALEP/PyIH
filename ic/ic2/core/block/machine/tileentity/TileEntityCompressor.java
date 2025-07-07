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
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ public class TileEntityCompressor
/*     */   extends TileEntityElectricMachine
/*     */ {
/*     */   public TileEntityPump validPump;
/*     */   
/*     */   public TileEntityCompressor() {
/*  28 */     super(3, 2, 400, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  33 */     addRecipe(Ic2Items.plantBall, Ic2Items.compressedPlantBall, 0.2F);
/*  34 */     addRecipe(Ic2Items.hydratedCoalDust, Ic2Items.hydratedCoalClump, 0.1F);
/*  35 */     addRecipe(new ItemStack(Blocks.field_150424_aL, 3), new ItemStack(Blocks.field_150385_bj), 0.1F);
/*  36 */     addRecipe(new ItemStack((Block)Blocks.field_150354_m), new ItemStack(Blocks.field_150322_A), 0.1F);
/*  37 */     addRecipe(new ItemStack(Items.field_151126_ay), new ItemStack(Blocks.field_150432_aD), 0.1F);
/*  38 */     addRecipe(Ic2Items.waterCell, new ItemStack(Items.field_151126_ay), 0.1F);
/*  39 */     addRecipe(Ic2Items.mixedMetalIngot, Ic2Items.advancedAlloy, 0.3F);
/*  40 */     addRecipe(Ic2Items.carbonMesh, Ic2Items.carbonPlate, 0.3F);
/*  41 */     addRecipe(Ic2Items.coalBall, Ic2Items.compressedCoalBall, 0.2F);
/*  42 */     addRecipe(Ic2Items.coalChunk, Ic2Items.industrialDiamond, 1.0F);
/*  43 */     addRecipe(Ic2Items.constructionFoam, Ic2Items.constructionFoamPellet, 0.1F);
/*  44 */     addRecipe(Ic2Items.cell, Ic2Items.airCell, 0.1F);
/*  45 */     addRecipe(StackUtil.copyWithSize(Ic2Items.scrapMetal, 8), Ic2Items.scrapMetalChunk, 0.5F);
/*  46 */     addRecipe(new ItemStack(Items.field_151065_br, 5), new ItemStack(Items.field_151072_bj), 1.0F);
/*  47 */     addRecipe(Ic2Items.rawObsidianBlade, Ic2Items.obsidianBlade, 0.3F);
/*  48 */     addRecipe(StackUtil.copyWithSize(Ic2Items.cropmatron, 3), Ic2Items.cropHarvester);
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
/*  83 */     IMachineRecipeManager manager = Recipes.compressor;
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
/*  96 */     Recipes.compressor.addRecipe(input, null, new ItemStack[] { output });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack itemStack, boolean adjustInput) {
/* 102 */     RecipeOutput output = (itemStack == null) ? null : Recipes.compressor.getOutputFor(itemStack, adjustInput);
/* 103 */     if (output == null) {
/*     */       
/* 105 */       if (getValidPump() != null) {
/*     */         
/* 107 */         NBTTagCompound nbt = new NBTTagCompound();
/* 108 */         nbt.func_74757_a("Pump", true);
/* 109 */         return new RecipeOutput(nbt, new ItemStack[] { new ItemStack(Items.field_151126_ay) });
/*     */       } 
/* 111 */       return null;
/*     */     } 
/* 113 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canWorkWithoutItems() {
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, List<ItemStack> items) {
/* 125 */     super.operateOnce(result, items);
/* 126 */     ItemStack item = result.items.get(0);
/* 127 */     if (item != null && item.func_77973_b() == Items.field_151126_ay && result.metadata != null)
/*     */     {
/* 129 */       if (result.metadata.func_74767_n("Pump")) {
/*     */         
/* 131 */         TileEntityPump pump = getValidPump();
/* 132 */         if (pump == null) {
/*     */           return;
/*     */         }
/*     */         
/* 136 */         pump.pumpCharge = 0;
/* 137 */         getNetwork().updateTileGuiField((TileEntity)pump, "pumpCharge");
/* 138 */         this.field_145850_b.func_147468_f(pump.field_145851_c, pump.field_145848_d - 1, pump.field_145849_e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityPump getValidPump() {
/* 145 */     if (this.validPump != null && !this.validPump.func_145837_r() && this.validPump.isPumpReady() && this.validPump.isWaterBelow())
/*     */     {
/* 147 */       return this.validPump;
/*     */     }
/* 149 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/* 151 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/* 152 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 154 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 157 */     if (this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/* 159 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
/* 160 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 162 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 165 */     if (this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/* 167 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
/* 168 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 170 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 173 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityPump) {
/*     */       
/* 175 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
/* 176 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 178 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 181 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityPump) {
/*     */       
/* 183 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
/* 184 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 186 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 195 */     return "Compressor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 201 */     return "block.machine.gui.GuiCompressor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 207 */     return "Machines/CompressorOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 213 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 218 */     return 0.85F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 224 */     if (par1 == null)
/*     */     {
/* 226 */       return false;
/*     */     }
/* 228 */     for (IRecipeInput input : Recipes.compressor.getRecipes().keySet()) {
/*     */       
/* 230 */       if (input.matches(par1))
/*     */       {
/* 232 */         return super.isValidInput(par1);
/*     */       }
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 241 */     return Recipes.compressor;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */