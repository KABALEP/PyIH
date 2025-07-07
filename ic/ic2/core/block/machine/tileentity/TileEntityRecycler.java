/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import ic2.api.recipe.IListRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeInputItemStack;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.BasicListRecipeManager;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityRecycler
/*     */   extends TileEntityElectricMachine
/*     */ {
/*     */   public TileEntityRecycler() {
/*  34 */     super(3, 1, 45, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init(Configuration config) {
/*  39 */     Property prop = config.get("general", "recyclerBlacklist", getRecyclerBlacklistString());
/*  40 */     prop.comment = "List of blocks and items which should not be turned into scrap by the recycler. Comma separated, format is id(Block/ItemName)-metadata";
/*  41 */     Recipes.recyclerBlacklist.getInputs().clear();
/*  42 */     setRecyclerBlacklistFromString(prop.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void preInit() {
/*  47 */     Recipes.recycler = new RecyclerRecipeManager();
/*  48 */     Recipes.recyclerWhitelist = (IListRecipeManager)new BasicListRecipeManager();
/*  49 */     Recipes.recyclerBlacklist = (IListRecipeManager)new BasicListRecipeManager();
/*     */     
/*  51 */     addBlacklistItem(Blocks.field_150410_aZ);
/*  52 */     addBlacklistItem(Items.field_151055_y);
/*  53 */     addBlacklistItem(Items.field_151126_ay);
/*  54 */     addBlacklistItem(Ic2Items.scaffold);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isMetal(ItemStack par1) {
/*  59 */     boolean flag = false;
/*  60 */     for (int id : OreDictionary.getOreIDs(par1)) {
/*     */       
/*  62 */       String name = OreDictionary.getOreName(id);
/*  63 */       if (name.contains("ingot") || name.contains("metal")) {
/*     */         
/*  65 */         flag = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  69 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, List<ItemStack> items) {
/*  75 */     if (this.field_145850_b.field_73012_v.nextInt(recycleChance(this.inventory[0])) == 0) {
/*     */       
/*  77 */       super.operateOnce(result, items);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       getResultFor(this.inventory[0], true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack itemstack, boolean adjustInput) {
/*  88 */     return Recipes.recycler.getOutputFor(itemstack, adjustInput);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  94 */     return "Recycler";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 100 */     return "block.machine.gui.GuiRecycler";
/*     */   }
/*     */ 
/*     */   
/*     */   public static int recycleChance() {
/* 105 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int recycleChance(ItemStack par1) {
/* 110 */     return isMetal(par1) ? 4 : 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 116 */     return "Machines/RecyclerOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 122 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 127 */     return 0.85F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlacklistItem(Item item) {
/* 132 */     addBlacklistItem(new ItemStack(item));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlacklistItem(Block block) {
/* 137 */     addBlacklistItem(new ItemStack(block));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlacklistItem(ItemStack item) {
/* 142 */     Recipes.recyclerBlacklist.add((IRecipeInput)new RecipeInputItemStack(item));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getIsItemBlacklisted(ItemStack itemStack) {
/* 147 */     for (IRecipeInput blackItem : Recipes.recyclerBlacklist) {
/*     */       
/* 149 */       if (blackItem.matches(itemStack))
/*     */       {
/* 151 */         return true;
/*     */       }
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getRecyclerBlacklistString() {
/* 159 */     StringBuilder ret = new StringBuilder();
/* 160 */     boolean first = true;
/* 161 */     for (IRecipeInput entry : Recipes.recyclerBlacklist.getInputs()) {
/*     */       
/* 163 */       if (entry != null) {
/*     */         
/* 165 */         if (first) {
/*     */           
/* 167 */           first = false;
/*     */         }
/*     */         else {
/*     */           
/* 171 */           ret.append(", ");
/*     */         } 
/* 173 */         ret.append(Item.field_150901_e.func_148750_c(((ItemStack)entry.getInputs().get(0)).func_77973_b()));
/* 174 */         if (((ItemStack)entry.getInputs().get(0)).func_77960_j() == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 178 */         ret.append("-");
/* 179 */         ret.append(((ItemStack)entry.getInputs().get(0)).func_77960_j());
/*     */       } 
/*     */     } 
/* 182 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setRecyclerBlacklistFromString(String str) {
/* 187 */     String[] arr$ = str.trim().split("\\s*,\\s*");
/* 188 */     for (String strPart : arr$) {
/*     */       
/* 190 */       String[] idMeta = strPart.split("\\s*-\\s*");
/*     */       
/* 192 */       if (idMeta[0].length() != 0) {
/*     */         
/* 194 */         String[] split = idMeta[0].split(":");
/* 195 */         Item blockId = GameRegistry.findItem(split[0], split[1]);
/* 196 */         ItemStack stack = null;
/* 197 */         if (blockId == null) {
/*     */           
/* 199 */           Block block = GameRegistry.findBlock(split[0], split[1]);
/* 200 */           if (block != null)
/*     */           {
/* 202 */             stack = new ItemStack(block);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 207 */           stack = new ItemStack(blockId);
/*     */         } 
/* 209 */         if (stack == null) {
/*     */           
/* 211 */           FMLLog.getLogger().info("Recycler Error: Could not find: " + idMeta[0] + " Please check the registry names to fix it");
/*     */         } else {
/*     */           
/* 214 */           int metaData = 32767;
/* 215 */           if (idMeta.length == 2)
/*     */           {
/* 217 */             metaData = Integer.parseInt(idMeta[1]);
/*     */           }
/* 219 */           stack.func_77964_b(metaData);
/* 220 */           addBlacklistItem(stack);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMachine.UpgradeType> getSupportedTypes() {
/* 230 */     List<IMachine.UpgradeType> list = super.getSupportedTypes();
/* 231 */     list.remove(IMachine.UpgradeType.WorldInteraction);
/* 232 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class RecyclerRecipeManager
/*     */     implements IMachineRecipeManager
/*     */   {
/*     */     private RecyclerRecipeManager() {}
/*     */ 
/*     */     
/*     */     public void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs) {}
/*     */ 
/*     */     
/*     */     public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/*     */       RecipeOutput ret;
/* 247 */       if (!TileEntityRecycler.getIsItemBlacklisted(input)) {
/*     */         
/* 249 */         boolean flag = false;
/* 250 */         for (int i : OreDictionary.getOreIDs(input)) {
/*     */           
/* 252 */           String name = OreDictionary.getOreName(i);
/* 253 */           if (name.contains("ingot") || name.contains("metal")) {
/*     */             
/* 255 */             flag = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 259 */         if (flag)
/*     */         {
/* 261 */           ret = new RecipeOutput(null, new ItemStack[] { StackUtil.copyWithSize(Ic2Items.scrapMetal.func_77946_l(), 1) });
/*     */         }
/*     */         else
/*     */         {
/* 265 */           ret = new RecipeOutput(null, new ItemStack[] { StackUtil.copyWithSize(Ic2Items.scrap.func_77946_l(), 1) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 270 */         return null;
/*     */       } 
/*     */       
/* 273 */       if (adjustInput)
/*     */       {
/* 275 */         input.field_77994_a--;
/*     */       }
/*     */       
/* 278 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<IRecipeInput, RecipeOutput> getRecipes() {
/* 283 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 290 */     if (par1 == null)
/*     */     {
/* 292 */       return false;
/*     */     }
/* 294 */     if (!getIsItemBlacklisted(par1))
/*     */     {
/* 296 */       return super.isValidInput(par1);
/*     */     }
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 304 */     return Recipes.recycler;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityRecycler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */