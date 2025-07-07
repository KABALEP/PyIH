/*     */ package ic2.core.block.generator.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
/*     */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactorElectric;
/*     */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactorSteam;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarHV;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarLV;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarMV;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterHV;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterLV;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterMV;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockGenerator
/*     */   extends BlockMultiID
/*     */ {
/*  41 */   public static Class tileEntityNuclearReactorClass = TileEntityNuclearReactorElectric.class;
/*  42 */   public static ItemStack[] optionalDrops = new ItemStack[16];
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockGenerator() {
/*  47 */     super(Material.field_151573_f);
/*  48 */     func_149711_c(3.0F);
/*  49 */     func_149672_a(Block.field_149777_j);
/*  50 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  56 */     return Ic2Icons.getTexture("bgen");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  62 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/*  68 */     switch (meta) {
/*     */       case 2:
/*  70 */         return 2;
/*  71 */       case 7: return meta;
/*  72 */       case 8: return meta;
/*  73 */       case 9: return meta;
/*  74 */       case 10: return meta;
/*  75 */       case 11: return meta;
/*  76 */       case 12: return meta;
/*  77 */     }  return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  84 */     if (optionalDrops[metadata] != null) {
/*     */       
/*  86 */       ArrayList<ItemStack> list = new ArrayList<>();
/*  87 */       list.add(optionalDrops[metadata].func_77946_l());
/*  88 */       return list;
/*     */     } 
/*  90 */     return super.getDrops(world, x, y, z, metadata, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  96 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/* 102 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/*     */     try {
/* 110 */       switch (meta) {
/*     */ 
/*     */         
/*     */         case 0:
/* 114 */           return (TileEntityBlock)new TileEntityGenerator();
/*     */ 
/*     */         
/*     */         case 1:
/* 118 */           return (TileEntityBlock)new TileEntityGeoGenerator();
/*     */ 
/*     */         
/*     */         case 2:
/* 122 */           return (TileEntityBlock)new TileEntityWaterGenerator();
/*     */ 
/*     */         
/*     */         case 3:
/* 126 */           return (TileEntityBlock)new TileEntitySolarGenerator();
/*     */ 
/*     */         
/*     */         case 4:
/* 130 */           return (TileEntityBlock)new TileEntityWindGenerator();
/*     */ 
/*     */         
/*     */         case 5:
/* 134 */           return tileEntityNuclearReactorClass.newInstance();
/*     */ 
/*     */         
/*     */         case 6:
/* 138 */           return (TileEntityBlock)new TileEntityNuclearReactorSteam();
/*     */ 
/*     */         
/*     */         case 7:
/* 142 */           return (TileEntityBlock)new TileEntitySolarLV();
/*     */ 
/*     */         
/*     */         case 8:
/* 146 */           return (TileEntityBlock)new TileEntitySolarMV();
/*     */ 
/*     */         
/*     */         case 9:
/* 150 */           return (TileEntityBlock)new TileEntitySolarHV();
/*     */ 
/*     */         
/*     */         case 10:
/* 154 */           return (TileEntityBlock)new TileEntityWaterLV();
/*     */ 
/*     */         
/*     */         case 11:
/* 158 */           return (TileEntityBlock)new TileEntityWaterMV();
/*     */ 
/*     */         
/*     */         case 12:
/* 162 */           return (TileEntityBlock)new TileEntityWaterHV();
/*     */       } 
/*     */ 
/*     */     
/* 166 */     } catch (Exception e) {
/*     */       
/* 168 */       throw new RuntimeException(e);
/*     */     } 
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
/* 176 */     int meta = world.func_72805_g(x, y, z);
/* 177 */     if (meta == 3 || meta == 7 || meta == 8 || meta == 9)
/*     */     {
/* 179 */       return false;
/*     */     }
/* 181 */     return super.canCreatureSpawn(type, world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149734_b(World world, int i, int j, int k, Random random) {
/* 187 */     if (!IC2.platform.isRendering()) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     int meta = world.func_72805_g(i, j, k);
/* 192 */     if (meta == 0 && isActive((IBlockAccess)world, i, j, k)) {
/*     */       
/* 194 */       TileEntityBlock te = (TileEntityBlock)world.func_147438_o(i, j, k);
/* 195 */       int l = te.getFacing();
/* 196 */       float f = i + 0.5F;
/* 197 */       float f2 = j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
/* 198 */       float f3 = k + 0.5F;
/* 199 */       float f4 = 0.52F;
/* 200 */       float f5 = random.nextFloat() * 0.6F - 0.3F;
/* 201 */       switch (l) {
/*     */ 
/*     */         
/*     */         case 4:
/* 205 */           world.func_72869_a("smoke", (f - f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/* 206 */           world.func_72869_a("flame", (f - f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/* 211 */           world.func_72869_a("smoke", (f + f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/* 212 */           world.func_72869_a("flame", (f + f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 217 */           world.func_72869_a("smoke", (f + f5), f2, (f3 - f4), 0.0D, 0.0D, 0.0D);
/* 218 */           world.func_72869_a("flame", (f + f5), f2, (f3 - f4), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 223 */           world.func_72869_a("smoke", (f + f5), f2, (f3 + f4), 0.0D, 0.0D, 0.0D);
/* 224 */           world.func_72869_a("flame", (f + f5), f2, (f3 + f4), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/* 229 */     } else if (meta == 5 || meta == 6) {
/*     */       
/* 231 */       int puffs = ((TileEntityNuclearReactor)world.func_147438_o(i, j, k)).heat / 1000;
/* 232 */       if (puffs <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 236 */       puffs = world.field_73012_v.nextInt(puffs); int n;
/* 237 */       for (n = 0; n < puffs; n++)
/*     */       {
/* 239 */         world.func_72869_a("smoke", (i + random.nextFloat()), (j + 0.95F), (k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
/*     */       }
/* 241 */       puffs -= world.field_73012_v.nextInt(4) + 3;
/* 242 */       for (n = 0; n < puffs; n++)
/*     */       {
/* 244 */         world.func_72869_a("flame", (i + random.nextFloat()), (j + 1.0F), (k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 252 */     return ((entityplayer.func_71045_bC() == null || !entityplayer.func_71045_bC().func_77969_a(Ic2Items.reactorChamber) || !entityplayer.func_71045_bC().func_77969_a(Ic2Items.steamReactorChamber)) && super.func_149727_a(world, i, j, k, entityplayer, side, a, b, c));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 259 */     return (stack.func_77960_j() == 5 || stack.func_77960_j() == 6) ? EnumRarity.uncommon : EnumRarity.common;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 265 */     Ic2Items.generator = new ItemStack((Block)this, 1, 0);
/* 266 */     Ic2Items.geothermalGenerator = new ItemStack((Block)this, 1, 1);
/* 267 */     Ic2Items.waterMill = new ItemStack((Block)this, 1, 2);
/* 268 */     Ic2Items.solarPanel = new ItemStack((Block)this, 1, 3);
/* 269 */     Ic2Items.windMill = new ItemStack((Block)this, 1, 4);
/* 270 */     Ic2Items.nuclearReactor = new ItemStack((Block)this, 1, 5);
/* 271 */     Ic2Items.steamReactor = new ItemStack((Block)this, 1, 6);
/* 272 */     Ic2Items.lvSolarPanel = new ItemStack((Block)this, 1, 7);
/* 273 */     Ic2Items.mvSolarPanel = new ItemStack((Block)this, 1, 8);
/* 274 */     Ic2Items.hvSolarPanel = new ItemStack((Block)this, 1, 9);
/* 275 */     Ic2Items.lvWaterMill = new ItemStack((Block)this, 1, 10);
/* 276 */     Ic2Items.mvWaterMill = new ItemStack((Block)this, 1, 11);
/* 277 */     Ic2Items.hvWaterMill = new ItemStack((Block)this, 1, 12);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\block\BlockGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */