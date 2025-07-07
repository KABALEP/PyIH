/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class BlockScaffold extends BlockTex implements IExtraData {
/*  24 */   public static int standardStrength = 2;
/*  25 */   public static int standardIronStrength = 5;
/*  26 */   public static int reinforcedStrength = 5;
/*  27 */   public static int reinforcedIronStrength = 12;
/*  28 */   public static int tickDelay = 1;
/*     */   
/*     */   public Material material;
/*     */   public int blockIndexInTexture;
/*     */   
/*     */   public BlockScaffold(Material material) {
/*  34 */     super(material);
/*  35 */     this.blockIndexInTexture = (material == Material.field_151573_f) ? 132 : 116;
/*  36 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*  37 */     this.material = material;
/*  38 */     if (material == Material.field_151575_d) {
/*     */       
/*  40 */       func_149711_c(0.5F);
/*  41 */       func_149752_b(0.2F);
/*  42 */       func_149663_c("blockScaffold");
/*  43 */       func_149672_a(field_149766_f);
/*     */     }
/*  45 */     else if (material == Material.field_151573_f) {
/*     */       
/*  47 */       func_149711_c(0.8F);
/*  48 */       func_149752_b(10.0F);
/*  49 */       func_149663_c("blockIronScaffold");
/*  50 */       func_149672_a(field_149777_j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  57 */     if (func_149688_o() == Material.field_151575_d) {
/*     */       
/*  59 */       Ic2Items.scaffold = new ItemStack(this);
/*     */     }
/*  61 */     else if (func_149688_o() == Material.field_151573_f) {
/*     */       
/*  63 */       Ic2Items.ironScaffold = new ItemStack(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_149737_a(EntityPlayer player, World p_149737_2_, int p_149737_3_, int p_149737_4_, int p_149737_5_) {
/*  70 */     if (func_149688_o() == Material.field_151573_f)
/*     */     {
/*  72 */       if (player.func_71045_bC() != null && Ic2Items.ironScaffold.func_77969_a(player.func_71045_bC()))
/*     */       {
/*  74 */         return super.func_149737_a(player, p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_) / 100.0F;
/*     */       }
/*     */     }
/*  77 */     return super.func_149737_a(player, p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStandardStrength() {
/*  82 */     if (this.material == Material.field_151573_f)
/*     */     {
/*  84 */       return standardIronStrength;
/*     */     }
/*  86 */     return standardStrength;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReinforcedStrength() {
/*  91 */     if (this.material == Material.field_151573_f)
/*     */     {
/*  93 */       return reinforcedIronStrength;
/*     */     }
/*  95 */     return reinforcedStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/* 101 */     int meta = iblockaccess.func_72805_g(i, j, k);
/* 102 */     if (side < 2)
/*     */     {
/* 104 */       return Ic2Icons.getTexture("b0")[this.blockIndexInTexture + 1];
/*     */     }
/* 106 */     if (meta == getReinforcedStrength())
/*     */     {
/* 108 */       return Ic2Icons.getTexture("b0")[this.blockIndexInTexture + 2];
/*     */     }
/* 110 */     return Ic2Icons.getTexture("b0")[this.blockIndexInTexture];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/* 116 */     if (side < 2)
/*     */     {
/* 118 */       return Ic2Icons.getTexture("b0")[this.blockIndexInTexture + 1];
/*     */     }
/* 120 */     return Ic2Icons.getTexture("b0")[this.blockIndexInTexture];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149637_q() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149721_r() {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/* 145 */     if (entity instanceof EntityPlayer) {
/*     */       
/* 147 */       EntityPlayer player = (EntityPlayer)entity;
/* 148 */       player.field_70143_R = 0.0F;
/* 149 */       if (player.field_70181_x < -0.15D)
/*     */       {
/* 151 */         player.field_70181_x = -0.15D;
/*     */       }
/* 153 */       if (player.func_70093_af())
/*     */       {
/* 155 */         player.field_70181_x = 0.08D;
/*     */       }
/* 157 */       if (IC2.keyboard.isForwardKeyDown(player) && player.field_70181_x < 0.2D)
/*     */       {
/* 159 */         player.field_70181_x = (this.material == Material.field_151573_f) ? 0.3D : 0.2D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 166 */     float factor = 1.0F;
/* 167 */     float f = factor / 16.0F;
/* 168 */     return AxisAlignedBB.func_72330_a((i + f), j, (k + f), (i + factor - f), (j + factor), (k + factor - f));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 173 */     return (side == ForgeDirection.DOWN || side == ForgeDirection.UP);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
/* 179 */     return AxisAlignedBB.func_72330_a(i, j, k, (i + 1), (j + 1), (k + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
/* 185 */     ArrayList<ItemStack> tr = new ArrayList();
/* 186 */     tr.add(new ItemStack(this, 1));
/* 187 */     if (meta == getReinforcedStrength()) {
/*     */       
/* 189 */       if (this.material == Material.field_151573_f)
/*     */       {
/* 191 */         tr.add(new ItemStack(Ic2Items.ironFence.func_77973_b(), 1));
/*     */       }
/* 193 */       if (this.material == Material.field_151575_d)
/*     */       {
/* 195 */         tr.add(new ItemStack(Items.field_151055_y, 2));
/*     */       }
/*     */     } 
/* 198 */     return tr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 204 */     if (entityplayer.func_70093_af())
/*     */     {
/* 206 */       return false;
/*     */     }
/* 208 */     ItemStack sticks = entityplayer.field_71071_by.func_70448_g();
/* 209 */     if (sticks == null || (this.material == Material.field_151575_d && (sticks.func_77973_b() != Items.field_151055_y || sticks.field_77994_a < 2)) || (this.material == Material.field_151573_f && sticks.func_77973_b() != Ic2Items.ironFence.func_77973_b()))
/*     */     {
/* 211 */       return false;
/*     */     }
/* 213 */     if (world.func_72805_g(i, j, k) == getReinforcedStrength() || !isPillar(world, i, j, k))
/*     */     {
/* 215 */       return false;
/*     */     }
/* 217 */     if (this.material == Material.field_151575_d) {
/*     */       
/* 219 */       ItemStack itemStack = sticks;
/* 220 */       itemStack.field_77994_a -= 2;
/*     */     }
/*     */     else {
/*     */       
/* 224 */       ItemStack itemStack2 = sticks;
/* 225 */       itemStack2.field_77994_a--;
/*     */     } 
/* 227 */     if ((entityplayer.func_71045_bC()).field_77994_a <= 0)
/*     */     {
/* 229 */       entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/*     */     }
/* 231 */     world.func_72921_c(i, j, k, getReinforcedStrength(), 3);
/* 232 */     world.func_147458_c(i, j, k, i, j, k);
/* 233 */     ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, i, j, k);
/* 234 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
/* 240 */     if (entityplayer.func_71045_bC() != null && Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) == this) {
/*     */       
/* 242 */       while (world.func_147439_a(i, j, k) == this)
/*     */       {
/* 244 */         j++;
/*     */       }
/* 246 */       if (func_149742_c(world, i, j, k) && j < IC2.getWorldHeight(world)) {
/*     */         
/* 248 */         world.func_147449_b(i, j, k, this);
/* 249 */         func_149714_e(world, i, j, k, 0);
/* 250 */         if (!entityplayer.field_71075_bZ.field_75098_d) {
/*     */           
/* 252 */           ItemStack currentEquippedItem = entityplayer.func_71045_bC();
/* 253 */           currentEquippedItem.field_77994_a--;
/* 254 */           if ((entityplayer.func_71045_bC()).field_77994_a <= 0)
/*     */           {
/* 256 */             entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/* 266 */     return (getStrengthFrom(world, i, j, k) > -1 && super.func_149742_c(world, i, j, k));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPillar(World world, int i, int j, int k) {
/* 271 */     while (world.func_147439_a(i, j, k) == this)
/*     */     {
/* 273 */       j--;
/*     */     }
/* 275 */     return world.func_147445_c(i, j, k, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/* 281 */     updateSupportStatus(world, i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149714_e(World world, int i, int j, int k, int l) {
/* 287 */     func_149674_a(world, i, j, k, (Random)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int i, int j, int k, Random random) {
/* 293 */     int ownStrength = world.func_72805_g(i, j, k);
/* 294 */     if (ownStrength >= getReinforcedStrength()) {
/*     */       
/* 296 */       if (!isPillar(world, i, j, k))
/*     */       {
/* 298 */         ownStrength = getStrengthFrom(world, i, j, k);
/* 299 */         ItemStack drop = new ItemStack(Items.field_151055_y, 2);
/* 300 */         if (this.material == Material.field_151573_f)
/*     */         {
/* 302 */           drop = new ItemStack(Ic2Items.ironFence.func_77973_b());
/*     */         }
/* 304 */         func_149642_a(world, i, j, k, drop);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 309 */       ownStrength = getStrengthFrom(world, i, j, k);
/*     */     } 
/* 311 */     if (ownStrength <= -1) {
/*     */       
/* 313 */       world.func_147468_f(i, j, k);
/* 314 */       func_149642_a(world, i, j, k, new ItemStack(this));
/*     */     }
/* 316 */     else if (ownStrength != world.func_72805_g(i, j, k)) {
/*     */       
/* 318 */       world.func_72921_c(i, j, k, ownStrength, 3);
/* 319 */       world.func_147458_c(i, j, k, i, j, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrengthFrom(World world, int i, int j, int k) {
/* 325 */     int strength = 0;
/* 326 */     if (isPillar(world, i, j - 1, k))
/*     */     {
/* 328 */       strength = getStandardStrength() + 1;
/*     */     }
/* 330 */     strength = compareStrengthTo(world, i, j - 1, k, strength);
/* 331 */     strength = compareStrengthTo(world, i + 1, j, k, strength);
/* 332 */     strength = compareStrengthTo(world, i - 1, j, k, strength);
/* 333 */     strength = compareStrengthTo(world, i, j, k + 1, strength);
/* 334 */     strength = compareStrengthTo(world, i, j, k - 1, strength);
/* 335 */     return strength - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareStrengthTo(World world, int i, int j, int k, int strength) {
/* 340 */     int s = 0;
/* 341 */     if (world.func_147439_a(i, j, k) == this) {
/*     */       
/* 343 */       s = world.func_72805_g(i, j, k);
/* 344 */       if (s > getReinforcedStrength())
/*     */       {
/* 346 */         s = getReinforcedStrength();
/*     */       }
/*     */     } 
/* 349 */     if (s > strength)
/*     */     {
/* 351 */       return s;
/*     */     }
/* 353 */     return strength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSupportStatus(World world, int i, int j, int k) {
/* 358 */     world.func_147464_a(i, j, k, this, tickDelay);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */