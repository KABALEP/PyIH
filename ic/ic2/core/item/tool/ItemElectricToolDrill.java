/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.api.item.IMiningDrill;
/*     */ import ic2.core.IC2;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ItemElectricToolDrill
/*     */   extends ItemElectricTool
/*     */   implements IMiningDrill, IElectricTool {
/*     */   public int soundTicker;
/*     */   
/*     */   public ItemElectricToolDrill(int sprite) {
/*  21 */     super(sprite, Item.ToolMaterial.IRON, 50);
/*  22 */     this.soundTicker = 0;
/*  23 */     this.maxCharge = 10000;
/*  24 */     this.transferLimit = 100;
/*  25 */     this.tier = 1;
/*  26 */     this.field_77864_a = 8.0F;
/*  27 */     this.co = 1;
/*  28 */     setHarvestLevel("shovel", 2);
/*  29 */     setHarvestLevel("pickaxe", 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemElectricToolDrill(int sprite, Item.ToolMaterial material, int operationEnergyCost) {
/*  34 */     this(sprite);
/*  35 */     this.field_77862_b = material;
/*  36 */     this.operationEnergyCost = operationEnergyCost;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  41 */     this.mineableBlocks.add(Blocks.field_150347_e);
/*  42 */     this.mineableBlocks.add(Blocks.field_150333_U);
/*  43 */     this.mineableBlocks.add(Blocks.field_150334_T);
/*  44 */     this.mineableBlocks.add(Blocks.field_150446_ar);
/*  45 */     this.mineableBlocks.add(Blocks.field_150348_b);
/*  46 */     this.mineableBlocks.add(Blocks.field_150322_A);
/*  47 */     this.mineableBlocks.add(Blocks.field_150372_bz);
/*  48 */     this.mineableBlocks.add(Blocks.field_150341_Y);
/*  49 */     this.mineableBlocks.add(Blocks.field_150366_p);
/*  50 */     this.mineableBlocks.add(Blocks.field_150339_S);
/*  51 */     this.mineableBlocks.add(Blocks.field_150365_q);
/*  52 */     this.mineableBlocks.add(Blocks.field_150340_R);
/*  53 */     this.mineableBlocks.add(Blocks.field_150352_o);
/*  54 */     this.mineableBlocks.add(Blocks.field_150482_ag);
/*  55 */     this.mineableBlocks.add(Blocks.field_150484_ah);
/*  56 */     this.mineableBlocks.add(Blocks.field_150432_aD);
/*  57 */     this.mineableBlocks.add(Blocks.field_150424_aL);
/*  58 */     this.mineableBlocks.add(Blocks.field_150369_x);
/*  59 */     this.mineableBlocks.add(Blocks.field_150368_y);
/*  60 */     this.mineableBlocks.add(Blocks.field_150450_ax);
/*  61 */     this.mineableBlocks.add(Blocks.field_150451_bX);
/*  62 */     this.mineableBlocks.add(Blocks.field_150336_V);
/*  63 */     this.mineableBlocks.add(Blocks.field_150389_bf);
/*  64 */     this.mineableBlocks.add(Blocks.field_150426_aN);
/*  65 */     this.mineableBlocks.add(Blocks.field_150349_c);
/*  66 */     this.mineableBlocks.add(Blocks.field_150346_d);
/*  67 */     this.mineableBlocks.add(Blocks.field_150391_bh);
/*  68 */     this.mineableBlocks.add(Blocks.field_150354_m);
/*  69 */     this.mineableBlocks.add(Blocks.field_150351_n);
/*  70 */     this.mineableBlocks.add(Blocks.field_150433_aE);
/*  71 */     this.mineableBlocks.add(Blocks.field_150431_aC);
/*  72 */     this.mineableBlocks.add(Blocks.field_150435_aG);
/*  73 */     this.mineableBlocks.add(Blocks.field_150458_ak);
/*  74 */     this.mineableBlocks.add(Blocks.field_150417_aV);
/*  75 */     this.mineableBlocks.add(Blocks.field_150390_bg);
/*  76 */     this.mineableBlocks.add(Blocks.field_150385_bj);
/*  77 */     this.mineableBlocks.add(Blocks.field_150387_bl);
/*  78 */     this.mineableBlocks.add(Blocks.field_150425_aM);
/*  79 */     this.mineableBlocks.add(Blocks.field_150467_bQ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150897_b(Block block) {
/*  85 */     return (block.func_149688_o() == Material.field_151576_e || block.func_149688_o() == Material.field_151573_f || super.func_150897_b(block));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack itemstack, Block block) {
/*  91 */     this.soundTicker++;
/*  92 */     return super.func_150893_a(itemstack, block);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDigSpeed(ItemStack itemstack, Block block, int md) {
/*  98 */     this.soundTicker++;
/*  99 */     return super.getDigSpeed(itemstack, block, md);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRandomDrillSound() {
/* 104 */     switch (IC2.random.nextInt(2)) {
/*     */ 
/*     */       
/*     */       default:
/* 108 */         return "DrillHard";
/*     */       case 1:
/*     */         break;
/*     */     } 
/* 112 */     return "DrillSoft";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBasicDrill(ItemStack drill) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraSpeed(ItemStack drill) {
/* 126 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyCost(ItemStack drill) {
/* 132 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void useDrill(ItemStack drill) {
/* 138 */     ElectricItem.manager.use(drill, this.operationEnergyCost, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMine(ItemStack drill) {
/* 144 */     return ElectricItem.manager.canUse(drill, this.operationEnergyCost);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 150 */     return EnumEnchantmentType.digger;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 162 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemElectricToolDrill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */