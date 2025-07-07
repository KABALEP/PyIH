/*     */ package ic2.core.block.generator.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockContainerCommon;
/*     */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
/*     */ import ic2.core.block.generator.tileentity.TileEntityReactorChamberElectric;
/*     */ import ic2.core.block.generator.tileentity.TileEntityReactorChamberSteam;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockReactorChamber
/*     */   extends BlockContainerCommon
/*     */ {
/*  29 */   public static Class tileEntityReactorChamberClass = TileEntityReactorChamberElectric.class;
/*     */ 
/*     */   
/*     */   public BlockReactorChamber() {
/*  33 */     super(Material.field_151573_f);
/*  34 */     func_149711_c(2.0F);
/*  35 */     func_149672_a(field_149777_j);
/*  36 */     func_149663_c("blockReactorChamber");
/*  37 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int par2) {
/*  44 */     if (side == 0)
/*     */     {
/*  46 */       return Ic2Icons.getTexture("b0")[(par2 == 0) ? 16 : 18];
/*     */     }
/*  48 */     if (side == 1)
/*     */     {
/*  50 */       return Ic2Icons.getTexture("b0")[(par2 == 0) ? 17 : 19];
/*     */     }
/*  52 */     return Ic2Icons.getTexture("b0")[(par2 == 0) ? 67 : 68];
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/*  57 */     if (!isReactorNearby(world, i, j, k, (world.func_72805_g(i, j, k) == 1))) {
/*     */       
/*  59 */       func_149642_a(world, i, j, k, new ItemStack(world.func_147439_a(i, j, k), 1, world.func_72805_g(i, j, k)));
/*  60 */       world.func_147468_f(i, j, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149734_b(World world, int i, int j, int k, Random random) {
/*  73 */     int meta = world.func_72805_g(i, j, k);
/*  74 */     TileEntityNuclearReactor reactor = getReactorEntity(world, i, j, k, (meta == 1));
/*  75 */     if (reactor == null) {
/*     */       
/*  77 */       func_149695_a(world, i, j, k, (Block)this);
/*     */       return;
/*     */     } 
/*  80 */     int puffs = reactor.heat / 1000;
/*  81 */     if (puffs <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     puffs = world.field_73012_v.nextInt(puffs); int n;
/*  86 */     for (n = 0; n < puffs; n++)
/*     */     {
/*  88 */       world.func_72869_a("smoke", (i + random.nextFloat()), (j + 0.95F), (k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
/*     */     }
/*  90 */     puffs -= world.field_73012_v.nextInt(4) + 3;
/*  91 */     for (n = 0; n < puffs; n++)
/*     */     {
/*  93 */       world.func_72869_a("flame", (i + random.nextFloat()), (j + 1.0F), (k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReactorNearby(World world, int i, int j, int k, boolean steam) {
/*  99 */     int count = 0;
/* 100 */     if (isReactorAt(world, i + 1, j, k, steam))
/*     */     {
/* 102 */       count++;
/*     */     }
/* 104 */     if (isReactorAt(world, i - 1, j, k, steam))
/*     */     {
/* 106 */       count++;
/*     */     }
/* 108 */     if (isReactorAt(world, i, j + 1, k, steam))
/*     */     {
/* 110 */       count++;
/*     */     }
/* 112 */     if (isReactorAt(world, i, j - 1, k, steam))
/*     */     {
/* 114 */       count++;
/*     */     }
/* 116 */     if (isReactorAt(world, i, j, k + 1, steam))
/*     */     {
/* 118 */       count++;
/*     */     }
/* 120 */     if (isReactorAt(world, i, j, k - 1, steam))
/*     */     {
/* 122 */       count++;
/*     */     }
/* 124 */     return (count == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReactorAt(World world, int x, int y, int z, boolean steam) {
/* 129 */     if (world.func_147438_o(x, y, z) instanceof TileEntityNuclearReactor) {
/*     */       
/* 131 */       if (steam)
/*     */       {
/* 133 */         return (Item.func_150898_a(world.func_147439_a(x, y, z)) == Ic2Items.steamReactor.func_77973_b() && world.func_72805_g(x, y, z) == Ic2Items.steamReactor.func_77960_j());
/*     */       }
/* 135 */       return (Item.func_150898_a(world.func_147439_a(x, y, z)) == Ic2Items.nuclearReactor.func_77973_b() && world.func_72805_g(x, y, z) == Ic2Items.nuclearReactor.func_77960_j());
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityNuclearReactor getReactorEntity(World world, int i, int j, int k, boolean steam) {
/* 142 */     if (isReactorAt(world, i + 1, j, k, steam))
/*     */     {
/* 144 */       return (TileEntityNuclearReactor)world.func_147438_o(i + 1, j, k);
/*     */     }
/* 146 */     if (isReactorAt(world, i - 1, j, k, steam))
/*     */     {
/* 148 */       return (TileEntityNuclearReactor)world.func_147438_o(i - 1, j, k);
/*     */     }
/* 150 */     if (isReactorAt(world, i, j + 1, k, steam))
/*     */     {
/* 152 */       return (TileEntityNuclearReactor)world.func_147438_o(i, j + 1, k);
/*     */     }
/* 154 */     if (isReactorAt(world, i, j - 1, k, steam))
/*     */     {
/* 156 */       return (TileEntityNuclearReactor)world.func_147438_o(i, j - 1, k);
/*     */     }
/* 158 */     if (isReactorAt(world, i, j, k + 1, steam))
/*     */     {
/* 160 */       return (TileEntityNuclearReactor)world.func_147438_o(i, j, k + 1);
/*     */     }
/* 162 */     if (isReactorAt(world, i, j, k - 1, steam))
/*     */     {
/* 164 */       return (TileEntityNuclearReactor)world.func_147438_o(i, j, k - 1);
/*     */     }
/* 166 */     func_149695_a(world, i, j, k, world.func_147439_a(i, j, k));
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 173 */     if (entityplayer.func_70093_af())
/*     */     {
/* 175 */       return false;
/*     */     }
/* 177 */     int meta = world.func_72805_g(i, j, k);
/* 178 */     TileEntityNuclearReactor reactor = getReactorEntity(world, i, j, k, (meta == 1));
/* 179 */     if (reactor == null) {
/*     */       
/* 181 */       func_149695_a(world, i, j, k, (Block)this);
/* 182 */       return false;
/*     */     } 
/* 184 */     return (!IC2.platform.isSimulating() || IC2.platform.launchGui(entityplayer, (IHasGui)reactor));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 190 */     return Ic2Items.machine.func_77973_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/* 196 */     return Ic2Items.machine.func_77960_j();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 203 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World p_149915_1_, int par2) {
/*     */     try {
/* 211 */       switch (par2) {
/*     */         case 0:
/* 213 */           return tileEntityReactorChamberClass.newInstance();
/* 214 */         case 1: return (TileEntity)new TileEntityReactorChamberSteam();
/* 215 */       }  return null;
/*     */     
/*     */     }
/* 218 */     catch (Throwable e) {
/*     */       
/* 220 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 227 */     Ic2Items.reactorChamber = new ItemStack((Block)this, 1, 0);
/* 228 */     Ic2Items.steamReactorChamber = new ItemStack((Block)this, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149666_a(Item p_149666_1_, CreativeTabs p_149666_2_, List<ItemStack> p_149666_3_) {
/* 235 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 237 */       ItemStack is = new ItemStack((Block)this, 1, i);
/* 238 */       if (p_149666_1_.func_77667_c(is) != null)
/*     */       {
/* 240 */         p_149666_3_.add(is);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\block\BlockReactorChamber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */