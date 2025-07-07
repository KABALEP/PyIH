/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.api.item.ItemWrapper;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoxableItems
/*     */   implements IBoxable
/*     */ {
/*  25 */   static BoxableItems instance = new BoxableItems();
/*  26 */   private HashSet<Item> items = new HashSet<>();
/*  27 */   private HashMap<Item, Integer> metas = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static void init() {
/*  31 */     instance.load();
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  36 */     registerItem(Ic2Items.miningPipe, true);
/*  37 */     registerItem(Ic2Items.treetap, true);
/*  38 */     registerItem(Ic2Items.wrench, true);
/*  39 */     registerItem(Ic2Items.cutter, true);
/*  40 */     registerItem(Ic2Items.constructionFoamSprayer, true);
/*  41 */     registerItem(Ic2Items.miningDrill, true);
/*  42 */     registerItem(Ic2Items.diamondDrill, true);
/*  43 */     registerItem(Ic2Items.chainsaw, true);
/*  44 */     registerItem(Ic2Items.electricWrench, true);
/*  45 */     registerItem(Ic2Items.electricTreetap, true);
/*  46 */     registerItem(Ic2Items.miningLaser, true);
/*  47 */     registerItem(Ic2Items.ecMeter, true);
/*  48 */     registerItem(Ic2Items.odScanner, true);
/*  49 */     registerItem(Ic2Items.ovScanner, true);
/*  50 */     registerItem(Ic2Items.frequencyTransmitter, true);
/*  51 */     registerItem(Ic2Items.nanoSaber, true);
/*  52 */     registerItem(Ic2Items.enabledNanoSaber, true);
/*  53 */     registerItem(Ic2Items.jetpack, true);
/*  54 */     registerItem(Ic2Items.electricJetpack, true);
/*  55 */     registerItem(Ic2Items.batPack, true);
/*  56 */     registerItem(Ic2Items.lapPack, true);
/*  57 */     registerItem(Ic2Items.cfPack, true);
/*  58 */     registerItem(Ic2Items.solarHelmet, true);
/*  59 */     registerItem(Ic2Items.staticBoots, true);
/*  60 */     registerItem(Ic2Items.cell, true);
/*  61 */     registerItem(Ic2Items.terraformerBlueprint, true);
/*  62 */     registerItem(Ic2Items.cultivationTerraformerBlueprint, true);
/*  63 */     registerItem(Ic2Items.irrigationTerraformerBlueprint, true);
/*  64 */     registerItem(Ic2Items.chillingTerraformerBlueprint, true);
/*  65 */     registerItem(Ic2Items.desertificationTerraformerBlueprint, true);
/*  66 */     registerItem(Ic2Items.flatificatorTerraformerBlueprint, true);
/*  67 */     registerItem(Ic2Items.mushroomTerraformerBlueprint, true);
/*  68 */     registerItem(Ic2Items.dynamite, true);
/*  69 */     registerItem(Ic2Items.stickyDynamite, true);
/*  70 */     registerItem(Ic2Items.remote, true);
/*  71 */     registerItem(Ic2Items.overclockerUpgrade, true);
/*  72 */     registerItem(Ic2Items.transformerUpgrade, true);
/*  73 */     registerItem(Ic2Items.energyStorageUpgrade, true);
/*  74 */     registerItem(Ic2Items.coin, true);
/*  75 */     registerItem(Ic2Items.constructionFoamPellet, true);
/*  76 */     registerItem(Ic2Items.cropnalyzer, true);
/*  77 */     registerItem(Ic2Items.electricHoe, true);
/*  78 */     registerItem(Ic2Items.advSolarHelmet, true);
/*  79 */     registerItem(Items.field_151032_g);
/*  80 */     registerItem(Items.field_151104_aV);
/*  81 */     registerItem(Items.field_151133_ar);
/*  82 */     registerItem(new ItemStack(Blocks.field_150478_aa), true);
/*  83 */     registerItem(Ic2Items.scaffold, true);
/*     */     
/*  85 */     Iterator<Item> iter = Item.field_150901_e.iterator();
/*     */     
/*  87 */     while (iter.hasNext()) {
/*     */       
/*  89 */       Item item = iter.next();
/*  90 */       if (item != null)
/*     */       {
/*  92 */         if (item instanceof net.minecraft.item.ItemTool || item instanceof net.minecraft.item.ItemBow || item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemArmor || item instanceof ic2.api.reactor.IReactorComponent || item instanceof ic2.api.reactor.ISteamReactorComponent || item instanceof ic2.api.item.IScannerItem)
/*     */         {
/*  94 */           registerItem(item);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItem(Item par1) {
/* 102 */     registerItem(new ItemStack(par1), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItem(ItemStack par1, boolean ignoreMeta) {
/* 107 */     if (par1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     ItemWrapper.registerBoxable(par1.func_77973_b(), this);
/* 112 */     if (ignoreMeta) {
/*     */       
/* 114 */       this.items.add(par1.func_77973_b());
/*     */       return;
/*     */     } 
/* 117 */     this.metas.put(par1.func_77973_b(), Integer.valueOf(par1.func_77960_j()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 123 */     if (itemstack == null)
/*     */     {
/* 125 */       return false;
/*     */     }
/* 127 */     if (this.items.contains(itemstack.func_77973_b())) {
/*     */       
/* 129 */       if (this.metas.containsKey(itemstack.func_77973_b()))
/*     */       {
/* 131 */         return (((Integer)this.metas.get(itemstack.func_77973_b())).intValue() == itemstack.func_77960_j());
/*     */       }
/* 133 */       return true;
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\BoxableItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */