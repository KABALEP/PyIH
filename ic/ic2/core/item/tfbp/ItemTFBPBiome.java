/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class ItemTFBPBiome
/*     */   extends ItemTFBP
/*     */   implements IExtraData
/*     */ {
/*     */   public ItemTFBPBiome() {
/*  24 */     super(129);
/*  25 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*  26 */     func_77655_b("itemBiomeTFBP");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  32 */     return Ic2Icons.getTexture(this.spriteID)[this.iconIndex + par1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConsume(ItemStack item) {
/*  38 */     int meta = item.func_77960_j();
/*  39 */     switch (meta) {
/*     */       case 0:
/*  41 */         return 4000;
/*  42 */       case 1: return 2500;
/*  43 */       case 2: return 2000;
/*  44 */       case 3: return 5000;
/*  45 */       case 4: return 8000;
/*  46 */       case 5: return 5000;
/*  47 */       case 6: return 6000;
/*     */     } 
/*  49 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack item) {
/*  55 */     int meta = item.func_77960_j();
/*  56 */     switch (meta) {
/*     */       case 0:
/*  58 */         return 40;
/*  59 */       case 1: return 40;
/*  60 */       case 2: return 50;
/*  61 */       case 3: return 30;
/*  62 */       case 4: return 25;
/*  63 */       case 5: return 30;
/*  64 */       case 6: return 35;
/*     */     } 
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/*  72 */     BiomeGenBase newBiome = getBiome(item.func_77960_j());
/*  73 */     if (newBiome == null)
/*     */     {
/*  75 */       return false;
/*     */     }
/*  77 */     BiomeGenBase oldBiome = TileEntityTerra.getBiomeAt(world, x, z);
/*  78 */     if (newBiome.func_150569_a(oldBiome))
/*     */     {
/*  80 */       return false;
/*     */     }
/*  82 */     TileEntityTerra.setBiomeAt(world, x, z, newBiome);
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiome(int meta) {
/*  88 */     switch (meta) {
/*     */       case 0:
/*  90 */         return BiomeGenBase.field_76772_c;
/*  91 */       case 1: return BiomeGenBase.field_76769_d;
/*  92 */       case 2: return BiomeGenBase.field_76767_f;
/*  93 */       case 3: return BiomeGenBase.field_76780_h;
/*  94 */       case 4: return BiomeGenBase.field_76789_p;
/*  95 */       case 5: return BiomeGenBase.field_76782_w;
/*  96 */       case 6: return BiomeGenBase.field_76774_n;
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack item) {
/* 104 */     switch (item.func_77960_j()) {
/*     */       case 0:
/* 106 */         return "item.itemBiomeBPCultivation";
/* 107 */       case 1: return "item.itemBiomeBPDesertification";
/* 108 */       case 2: return "item.itemBiomeBPForestification";
/* 109 */       case 3: return "item.itemBiomeBPIrrigation";
/* 110 */       case 4: return "item.itemBiomeBPMushroom";
/* 111 */       case 5: return "item.itemBiomeBPUndergrowth";
/* 112 */       case 6: return "item.itemBiomeBPChilling";
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 129 */     Ic2Items.cultivationBiomeBlueprint = new ItemStack((Item)this, 1, 0);
/* 130 */     Ic2Items.desertificationBiomeBlueprint = new ItemStack((Item)this, 1, 1);
/* 131 */     Ic2Items.forestificationBiomeBlueprint = new ItemStack((Item)this, 1, 2);
/* 132 */     Ic2Items.irrigationBiomeBlueprint = new ItemStack((Item)this, 1, 3);
/* 133 */     Ic2Items.mushroomBiomeBlueprint = new ItemStack((Item)this, 1, 4);
/* 134 */     Ic2Items.undergrowthBiomeBlueprint = new ItemStack((Item)this, 1, 5);
/* 135 */     Ic2Items.chillingBiomeBlueprint = new ItemStack((Item)this, 1, 6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs p_150895_2_, List<ItemStack> list) {
/* 142 */     for (int i = 0; i < 7; i++)
/*     */     {
/* 144 */       list.add(new ItemStack((Item)this, 1, i));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBPBiome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */