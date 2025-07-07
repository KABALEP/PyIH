/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.recipe.IScrapboxManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemScrapbox
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemScrapbox(int sprite) {
/*  29 */     super(sprite);
/*  30 */     BlockDispenser.field_149943_a.func_82595_a(this, new BehaviorScrapboxDispense());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initData() {
/*  35 */     Recipes.scrapboxDrops = new ScrapboxRecipeManager();
/*  36 */     if (IC2.suddenlyHoes) {
/*     */       
/*  38 */       addDrop(Items.field_151017_I, 9001.0F);
/*     */     }
/*     */     else {
/*     */       
/*  42 */       addDrop(Items.field_151017_I, 5.01F);
/*     */     } 
/*  44 */     addDrop(Blocks.field_150346_d, 5.0F);
/*  45 */     addDrop(Items.field_151055_y, 4.0F);
/*  46 */     addDrop((Block)Blocks.field_150349_c, 3.0F);
/*  47 */     addDrop(Blocks.field_150351_n, 3.0F);
/*  48 */     addDrop(Blocks.field_150424_aL, 2.0F);
/*  49 */     addDrop(Items.field_151078_bh, 2.0F);
/*  50 */     addDrop(Items.field_151034_e, 1.5F);
/*  51 */     addDrop(Items.field_151025_P, 1.5F);
/*  52 */     addDrop(Ic2Items.filledTinCan.func_77973_b(), 1.5F);
/*  53 */     addDrop(Items.field_151041_m);
/*  54 */     addDrop(Items.field_151038_n);
/*  55 */     addDrop(Items.field_151039_o);
/*  56 */     addDrop(Blocks.field_150425_aM);
/*  57 */     addDrop(Items.field_151155_ap);
/*  58 */     addDrop(Items.field_151116_aA);
/*  59 */     addDrop(Items.field_151008_G);
/*  60 */     addDrop(Items.field_151103_aS);
/*  61 */     addDrop(Items.field_151157_am, 0.9F);
/*  62 */     addDrop(Items.field_151083_be, 0.9F);
/*  63 */     addDrop(Blocks.field_150423_aK, 0.9F);
/*  64 */     addDrop(Items.field_151077_bg, 0.9F);
/*  65 */     addDrop(Items.field_151143_au, 0.9F);
/*  66 */     addDrop(Items.field_151137_ax, 0.9F);
/*  67 */     addDrop(Ic2Items.rubber.func_77973_b(), 0.8F);
/*  68 */     addDrop(Items.field_151114_aO, 0.8F);
/*  69 */     addDrop(Ic2Items.coalDust.func_77973_b(), 0.8F);
/*  70 */     addDrop(Ic2Items.copperDust.func_77973_b(), 0.8F);
/*  71 */     addDrop(Ic2Items.tinDust.func_77973_b(), 0.8F);
/*  72 */     addDrop(Ic2Items.plantBall.func_77973_b(), 0.7F);
/*  73 */     addDrop(Ic2Items.suBattery.func_77973_b(), 0.7F);
/*  74 */     addDrop(Ic2Items.ironDust.func_77973_b(), 0.7F);
/*  75 */     addDrop(Ic2Items.goldDust.func_77973_b(), 0.7F);
/*  76 */     addDrop(Items.field_151123_aH, 0.6F);
/*  77 */     addDrop(Blocks.field_150366_p, 0.5F);
/*  78 */     addDrop((Item)Items.field_151169_ag, 0.5F);
/*  79 */     addDrop(Blocks.field_150352_o, 0.5F);
/*  80 */     addDrop(Items.field_151105_aU, 0.5F);
/*  81 */     addDrop(Items.field_151045_i, 0.1F);
/*  82 */     addDrop(Items.field_151166_bC, 0.05F);
/*  83 */     if (Ic2Items.copperOre != null) {
/*     */       
/*  85 */       addDrop(Ic2Items.copperOre.func_77973_b(), 0.7F);
/*     */     }
/*     */     else {
/*     */       
/*  89 */       List<ItemStack> ores = OreDictionary.getOres("oreCopper");
/*  90 */       if (!ores.isEmpty())
/*     */       {
/*  92 */         addDrop(((ItemStack)ores.get(0)).func_77946_l(), 0.7F);
/*     */       }
/*     */     } 
/*  95 */     if (Ic2Items.tinOre != null) {
/*     */       
/*  97 */       addDrop(Ic2Items.tinOre.func_77973_b(), 0.7F);
/*     */     }
/*     */     else {
/*     */       
/* 101 */       List<ItemStack> ores = OreDictionary.getOres("oreTin");
/* 102 */       if (!ores.isEmpty())
/*     */       {
/* 104 */         addDrop(((ItemStack)ores.get(0)).func_77946_l(), 0.7F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(Item item) {
/* 111 */     addDrop(new ItemStack(item), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(Item item, float chance) {
/* 116 */     addDrop(new ItemStack(item), chance);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(Block block) {
/* 121 */     addDrop(new ItemStack(block), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(Block block, float chance) {
/* 126 */     addDrop(new ItemStack(block), chance);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(ItemStack item) {
/* 131 */     addDrop(item, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDrop(ItemStack item, float chance) {
/* 136 */     Recipes.scrapboxDrops.addDrop(item, chance);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 141 */     ItemStack itemStack = Recipes.scrapboxDrops.getDrop(itemstack, !entityplayer.field_71075_bZ.field_75098_d);
/* 142 */     if (itemStack != null)
/*     */     {
/* 144 */       entityplayer.func_71019_a(itemStack, false);
/*     */     }
/* 146 */     return itemstack;
/*     */   }
/*     */   
/*     */   static class ScrapboxRecipeManager
/*     */     implements IScrapboxManager {
/* 151 */     private final List<ItemScrapbox.Drop> drops = new ArrayList<>();
/* 152 */     private final Random random = new Random();
/*     */ 
/*     */     
/*     */     public void addDrop(ItemStack drop, float rawChance) {
/* 156 */       this.drops.add(new ItemScrapbox.Drop(drop, rawChance));
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getDrop(ItemStack input, boolean adjustInput) {
/* 161 */       if (this.drops.isEmpty())
/*     */       {
/* 163 */         return null;
/*     */       }
/* 165 */       if (adjustInput)
/*     */       {
/* 167 */         input.field_77994_a--;
/*     */       }
/* 169 */       float chance = this.random.nextFloat() * ItemScrapbox.Drop.topChance;
/* 170 */       for (ItemScrapbox.Drop drop : this.drops) {
/*     */         
/* 172 */         if (drop.upperChanceBound >= chance)
/*     */         {
/* 174 */           return drop.item.func_77946_l();
/*     */         }
/*     */       } 
/* 177 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<ItemStack, Float> getDrops() {
/* 182 */       Map<ItemStack, Float> ret = new HashMap<>(this.drops.size());
/*     */       
/* 184 */       for (ItemScrapbox.Drop drop : this.drops)
/*     */       {
/* 186 */         ret.put(drop.item, Float.valueOf(drop.originalChance.floatValue() / ItemScrapbox.Drop.topChance));
/*     */       }
/*     */       
/* 189 */       return ret;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Drop
/*     */   {
/*     */     ItemStack item;
/*     */     Float originalChance;
/*     */     float upperChanceBound;
/*     */     static float topChance;
/*     */     
/*     */     Drop(ItemStack item1, float chance) {
/* 202 */       this.item = item1;
/* 203 */       this.originalChance = Float.valueOf(chance);
/*     */       
/* 205 */       this.upperChanceBound = topChance += chance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemScrapbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */