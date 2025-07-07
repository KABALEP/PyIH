/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.reactor.IReactorProduct;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemEnrichedUraniumStuff
/*     */   extends ItemIC2
/*     */   implements IReactorProduct, IExtraData
/*     */ {
/*     */   public ItemEnrichedUraniumStuff() {
/*  26 */     super(0);
/*  27 */     func_77627_a(true);
/*  28 */     func_77625_d(64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  35 */     switch (par1) {
/*     */       case 0:
/*  37 */         return Ic2Icons.getTexture("i0")[23];
/*  38 */       case 1: return Ic2Icons.getTexture("i0")[24];
/*  39 */       case 2: return Ic2Icons.getTexture("i0")[25];
/*  40 */       case 3: return Ic2Icons.getTexture("i0")[26];
/*  41 */       case 4: return Ic2Icons.getTexture("i0")[27];
/*  42 */       case 100: return Ic2Icons.getTexture("i3")[144];
/*  43 */       case 101: return Ic2Icons.getTexture("i3")[145];
/*  44 */       case 102: return Ic2Icons.getTexture("i3")[146];
/*  45 */       case 103: return Ic2Icons.getTexture("i3")[147];
/*  46 */       case 104: return Ic2Icons.getTexture("i3")[148];
/*  47 */       case 200: return Ic2Icons.getTexture("i3")[160];
/*  48 */       case 201: return Ic2Icons.getTexture("i3")[161];
/*  49 */       case 202: return Ic2Icons.getTexture("i3")[162];
/*  50 */       case 203: return Ic2Icons.getTexture("i3")[163];
/*  51 */       case 204: return Ic2Icons.getTexture("i3")[164];
/*     */     } 
/*  53 */     return super.func_77617_a(par1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack item) {
/*  59 */     switch (item.func_77960_j()) {
/*     */       case 0:
/*  61 */         return "item.itemRedstoneEnrichedUranium";
/*  62 */       case 1: return "item.itemBlazeEnrichedUranium";
/*  63 */       case 2: return "item.itemEnderPearlEnrichedUranium";
/*  64 */       case 3: return "item.itemNetherStarEnrichedUranium";
/*  65 */       case 4: return "item.itemCharcoalEnrichedUranium";
/*  66 */       case 100: return "item.itemCellRedstoneEnrichedUranEmpty";
/*  67 */       case 101: return "item.itemCellBlazeEnrichedUranEmpty";
/*  68 */       case 102: return "item.itemCellEnderPearlEnrichedUranEmpty";
/*  69 */       case 103: return "item.itemCellNetherStarEnrichedUranEmpty";
/*  70 */       case 104: return "item.itemCellCharcoalEnrichedUranEmpty";
/*  71 */       case 200: return "item.itemCellRedstoneUranEnriched";
/*  72 */       case 201: return "item.itemCellBlazeUranEnriched";
/*  73 */       case 202: return "item.itemCellEnderPearlUranEnriched";
/*  74 */       case 203: return "item.itemCellNetherStarUranEnriched";
/*  75 */       case 204: return "item.itemCellCharcoalUranEnriched";
/*     */     } 
/*  77 */     return super.func_77667_c(item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProduct(ItemStack item) {
/*  83 */     if (item == null || item.func_77973_b() != this)
/*     */     {
/*  85 */       return false;
/*     */     }
/*  87 */     int meta = item.func_77960_j();
/*  88 */     if ((meta >= 100 && meta <= 104) || (meta >= 200 && meta <= 204))
/*     */     {
/*  90 */       return true;
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  98 */     Ic2Items.redstoneEnrichedUraniumIngot = new ItemStack(this, 1, 0);
/*  99 */     Ic2Items.blazeEnrichedUraniumIngot = new ItemStack(this, 1, 1);
/* 100 */     Ic2Items.enderpearlEnrichedUraniumIngot = new ItemStack(this, 1, 2);
/* 101 */     Ic2Items.netherstarEnrichedUraniumIngot = new ItemStack(this, 1, 3);
/* 102 */     Ic2Items.charcoalEnrichedUraniumIngot = new ItemStack(this, 1, 4);
/*     */     
/* 104 */     Ic2Items.nearDepletedRedstoneEnrichedUraniumCell = new ItemStack(this, 1, 100);
/* 105 */     Ic2Items.nearDepletedBlazeEnrichedUraniumCell = new ItemStack(this, 1, 101);
/* 106 */     Ic2Items.nearDepletedEnderPearlEnrichedUraniumCell = new ItemStack(this, 1, 102);
/* 107 */     Ic2Items.nearDepletedNetherStarEnrichedUraniumCell = new ItemStack(this, 1, 103);
/* 108 */     Ic2Items.nearDepletedCharcoalEnrichedUraniumCell = new ItemStack(this, 1, 104);
/*     */     
/* 110 */     Ic2Items.reEnrichedRedstoneUraniumCell = new ItemStack(this, 1, 200);
/* 111 */     Ic2Items.reEnrichedBlazeUraniumCell = new ItemStack(this, 1, 201);
/* 112 */     Ic2Items.reEnrichedEnderPearlUraniumCell = new ItemStack(this, 1, 202);
/* 113 */     Ic2Items.reEnrichedNetherStarUraniumCell = new ItemStack(this, 1, 203);
/* 114 */     Ic2Items.reEnrichedCharcoalUraniumCell = new ItemStack(this, 1, 204);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> list) {
/* 121 */     for (int i = 0; i < 5; i++) {
/*     */       
/* 123 */       list.add(new ItemStack(this, 1, i));
/* 124 */       list.add(new ItemStack(this, 1, i + 100));
/* 125 */       list.add(new ItemStack(this, 1, i + 200));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemEnrichedUraniumStuff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */