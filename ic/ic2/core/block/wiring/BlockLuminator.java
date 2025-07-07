/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.event.PaintEvent;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockContainerCommon;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockLuminator
/*     */   extends BlockContainerCommon
/*     */ {
/*  42 */   public static int renderPass = 0;
/*  43 */   public static int colorMultiplier = -1;
/*     */ 
/*     */   
/*     */   public BlockLuminator() {
/*  47 */     super(Material.field_151592_s);
/*  48 */     func_149672_a(field_149778_k);
/*  49 */     func_149711_c(0.3F);
/*  50 */     func_149752_b(0.5F);
/*  51 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*  52 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/*  57 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int par1, int par2) {
/*  63 */     return Ic2Icons.getTexture("b0")[31];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*  70 */     if (renderPass == 0)
/*     */     {
/*  72 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  74 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  75 */     if (tile == null || !(tile instanceof TileEntityLuminator))
/*     */     {
/*  77 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  79 */     TileEntityLuminator luma = (TileEntityLuminator)tile;
/*  80 */     ItemStack item = luma.getGlass();
/*  81 */     if (item == null)
/*     */     {
/*  83 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  85 */     Block id = Block.func_149634_a(item.func_77973_b());
/*  86 */     int meta = item.func_77973_b().func_77647_b(item.func_77960_j());
/*  87 */     return id.func_149691_a(side, meta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149720_d(IBlockAccess world, int x, int y, int z) {
/*  94 */     if (colorMultiplier != -1)
/*     */     {
/*  96 */       return colorMultiplier;
/*     */     }
/*  98 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  99 */     if (tile instanceof TileEntityLuminator) {
/*     */       
/* 101 */       TileEntityLuminator lumi = (TileEntityLuminator)tile;
/* 102 */       if (lumi.color != -1)
/*     */       {
/* 104 */         return ItemDye.field_150922_c[lumi.color];
/*     */       }
/*     */     } 
/* 107 */     return 16777215;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149660_a(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
/* 112 */     return par5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149707_d(World world, int i, int j, int k, int direction) {
/* 118 */     if (!world.func_147437_c(i, j, k))
/*     */     {
/* 120 */       return false;
/*     */     }
/* 122 */     switch (direction) {
/*     */ 
/*     */       
/*     */       case 0:
/* 126 */         j++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 131 */         j--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 136 */         k++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 141 */         k--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 146 */         i++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 151 */         i--;
/*     */         break;
/*     */     } 
/*     */     
/* 155 */     return isSupportingBlock(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupportingBlock(World world, int i, int j, int k) {
/* 160 */     return (!world.func_147437_c(i, j, k) && (world.func_147445_c(i, j, k, true) || isSpecialSupporter((IBlockAccess)world, i, j, k)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSpecialSupporter(IBlockAccess world, int i, int j, int k) {
/* 165 */     Block block = world.func_147439_a(i, j, k);
/* 166 */     return (!world.func_147437_c(i, j, k) && (block instanceof net.minecraft.block.BlockFence || block instanceof ic2.core.block.BlockPoleFence || block instanceof BlockCable || block instanceof BlockLuminatorMultipart || block == Block.func_149634_a(Ic2Items.reinforcedGlass.func_77973_b()) || block == Blocks.field_150359_w));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149718_j(World world, int i, int j, int k) {
/* 172 */     TileEntity te = world.func_147438_o(i, j, k);
/* 173 */     if (te != null && ((TileEntityLuminator)te).ignoreBlockStay)
/*     */     {
/* 175 */       return true;
/*     */     }
/* 177 */     int facing = world.func_72805_g(i, j, k);
/* 178 */     switch (facing) {
/*     */ 
/*     */       
/*     */       case 0:
/* 182 */         j++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 187 */         j--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 192 */         k++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 197 */         k--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 202 */         i++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 207 */         i--;
/*     */         break;
/*     */     } 
/*     */     
/* 211 */     return isSupportingBlock(world, i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/* 217 */     if (!func_149718_j(world, i, j, k)) { this; if (!isGlass((IBlockAccess)world, i, j, k)) {
/*     */         
/* 219 */         if (!world.field_72995_K)
/*     */         {
/* 221 */           world.func_72838_d((Entity)new EntityItem(world, i, j, k, Ic2Items.luminator.func_77946_l()));
/*     */         }
/* 223 */         world.func_147468_f(i, j, k);
/*     */       }  }
/* 225 */      super.func_149695_a(world, i, j, k, l);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 235 */     return IC2.platform.getRenderId("luminator");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 241 */     float[] box = getBoxOfLuminator((IBlockAccess)world, i, j, k);
/* 242 */     return AxisAlignedBB.func_72330_a((box[0] + i), (box[1] + j), (box[2] + k), (box[3] + i), (box[4] + j), (box[5] + k));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
/* 248 */     float[] box = getBoxOfLuminator((IBlockAccess)world, i, j, k);
/* 249 */     return AxisAlignedBB.func_72330_a((box[0] + i), (box[1] + j), (box[2] + k), (box[3] + i), (box[4] + j), (box[5] + k));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getBoxOfLuminator(IBlockAccess world, int i, int j, int k) {
/* 254 */     if (renderPass == 1) {
/*     */       
/* 256 */       TileEntity tileEntity = world.func_147438_o(i, j, k);
/* 257 */       if (tileEntity instanceof TileEntityLuminator) {
/*     */         
/* 259 */         TileEntityLuminator luma = (TileEntityLuminator)tileEntity;
/* 260 */         ItemStack item = luma.getGlass();
/* 261 */         if (item != null)
/*     */         {
/* 263 */           return new float[] { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };
/*     */         }
/*     */       } 
/*     */     } 
/* 267 */     boolean hasGlass = false;
/* 268 */     TileEntity tile = world.func_147438_o(i, j, k);
/* 269 */     if (tile instanceof TileEntityLuminator) {
/*     */       
/* 271 */       TileEntityLuminator luma = (TileEntityLuminator)tile;
/* 272 */       ItemStack item = luma.getGlass();
/* 273 */       if (item != null)
/*     */       {
/* 275 */         hasGlass = true;
/*     */       }
/*     */     } 
/* 278 */     int facing = world.func_72805_g(i, j, k);
/* 279 */     float px = 0.0625F;
/* 280 */     float glass = hasGlass ? 0.01F : 0.0F;
/* 281 */     switch (facing) {
/*     */ 
/*     */       
/*     */       case 0:
/* 285 */         j++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 290 */         j--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 295 */         k++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 300 */         k--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 305 */         i++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 310 */         i--;
/*     */         break;
/*     */     } 
/*     */     
/* 314 */     boolean fullCover = isSpecialSupporter(world, i, j, k);
/* 315 */     switch (facing) {
/*     */ 
/*     */       
/*     */       case 1:
/* 319 */         return new float[] { 0.0F + glass, 0.0F, 0.0F + glass, 1.0F - glass, 1.0F * px, 1.0F - glass };
/*     */ 
/*     */       
/*     */       case 2:
/* 323 */         if (fullCover)
/*     */         {
/* 325 */           return new float[] { 0.0F + glass, 0.0F + glass, 15.0F * px, 1.0F - glass, 1.0F - glass, 1.0F };
/*     */         }
/* 327 */         return new float[] { 6.0F * px, 3.0F * px, 14.0F * px, 1.0F - 6.0F * px, 1.0F - 3.0F * px, 1.0F };
/*     */ 
/*     */       
/*     */       case 3:
/* 331 */         if (fullCover)
/*     */         {
/* 333 */           return new float[] { 0.0F + glass, 0.0F + glass, 0.0F, 1.0F - glass, 1.0F - glass, 1.0F * px };
/*     */         }
/* 335 */         return new float[] { 6.0F * px, 3.0F * px, 0.0F, 1.0F - 6.0F * px, 1.0F - 3.0F * px, 2.0F * px };
/*     */ 
/*     */       
/*     */       case 4:
/* 339 */         if (fullCover)
/*     */         {
/* 341 */           return new float[] { 15.0F * px, 0.0F + glass, 0.0F + glass, 1.0F, 1.0F - glass, 1.0F - glass };
/*     */         }
/* 343 */         return new float[] { 14.0F * px, 3.0F * px, 6.0F * px, 1.0F, 1.0F - 3.0F * px, 1.0F - 6.0F * px };
/*     */ 
/*     */       
/*     */       case 5:
/* 347 */         if (fullCover)
/*     */         {
/* 349 */           return new float[] { 0.0F, 0.0F + glass, 0.0F + glass, 1.0F * px, 1.0F - glass, 1.0F - glass };
/*     */         }
/* 351 */         return new float[] { 0.0F, 3.0F * px, 6.0F * px, 2.0F * px, 1.0F - 3.0F * px, 1.0F - 6.0F * px };
/*     */     } 
/*     */ 
/*     */     
/* 355 */     if (fullCover)
/*     */     {
/* 357 */       return new float[] { 0.0F + glass, 15.0F * px, 0.0F + glass, 1.0F - glass, 1.0F, 1.0F - glass };
/*     */     }
/* 359 */     return new float[] { 4.0F * px, 13.0F * px, 4.0F * px, 1.0F - 4.0F * px, 1.0F, 1.0F - 4.0F * px };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int i, int j, int k) {
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149701_w() {
/* 378 */     this; return renderPass;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onColoring(PaintEvent event) {
/* 384 */     TileEntity tile = event.world.func_147438_o(event.x, event.y, event.z);
/* 385 */     if (!(tile instanceof TileEntityLuminator)) {
/*     */       return;
/*     */     }
/*     */     
/* 389 */     TileEntityLuminator lumi = (TileEntityLuminator)tile;
/* 390 */     if (lumi.color == event.color) {
/*     */       return;
/*     */     }
/*     */     
/* 394 */     lumi.setColor(event.color);
/* 395 */     event.painted = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 400 */     ItemStack itemStack = entityplayer.func_71045_bC();
/* 401 */     if (itemStack == null || !(itemStack.func_77973_b() instanceof ic2.api.item.IElectricItem)) {
/*     */       
/* 403 */       if (itemStack == null) {
/*     */         
/* 405 */         if (entityplayer.func_70093_af()) {
/*     */           
/* 407 */           TileEntityLuminator tileEntityLuminator = (TileEntityLuminator)world.func_147438_o(i, j, k);
/* 408 */           if (tileEntityLuminator.getGlass() != null) {
/*     */             
/* 410 */             if (!entityplayer.field_71071_by.func_70441_a(tileEntityLuminator.getGlass()))
/*     */             {
/* 412 */               entityplayer.func_70099_a(tileEntityLuminator.getGlass(), 0.5F);
/*     */             }
/* 414 */             tileEntityLuminator.setGlass(null);
/*     */           } 
/*     */         } 
/* 417 */         return false;
/*     */       } 
/* 419 */       if (StackUtil.hasOreTag("blockGlass", itemStack) || itemStack.func_77969_a(Ic2Items.reinforcedGlass)) {
/*     */         
/* 421 */         TileEntityLuminator tileEntityLuminator = (TileEntityLuminator)world.func_147438_o(i, j, k);
/* 422 */         if (tileEntityLuminator.getGlass() == null) {
/*     */           
/* 424 */           itemStack.field_77994_a--;
/* 425 */           ItemStack item = itemStack.func_77946_l();
/* 426 */           item.field_77994_a = 1;
/* 427 */           tileEntityLuminator.setGlass(item);
/* 428 */           entityplayer.field_71069_bz.func_75142_b();
/* 429 */           return true;
/*     */         } 
/*     */       } 
/* 432 */       return false;
/*     */     } 
/* 434 */     TileEntityLuminator lumi = (TileEntityLuminator)world.func_147438_o(i, j, k);
/* 435 */     int transfer = lumi.getMaxEnergy() - lumi.energy;
/* 436 */     if (transfer <= 0)
/*     */     {
/* 438 */       return false;
/*     */     }
/* 440 */     transfer = (int)ElectricItem.manager.discharge(itemStack, transfer, 10, true, true, false);
/* 441 */     lumi.energy += transfer;
/* 442 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/* 447 */     TileEntity tile = world.func_147438_o(i, j, k);
/* 448 */     if (tile instanceof TileEntityLuminator && ((TileEntityLuminator)tile).active && entity instanceof net.minecraft.entity.monster.EntityMob)
/*     */     {
/* 450 */       entity.func_70015_d((entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70668_bt() == EnumCreatureAttribute.UNDEAD) ? 20 : 10);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149719_a(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
/* 457 */     this; float[] floats = getBoxOfLuminator(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
/* 458 */     func_149676_a(floats[0], floats[1], floats[2], floats[3], floats[4], floats[5]);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World world) {
/* 463 */     return new TileEntityLuminator();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/* 468 */     return "/ic2/sprites/block_0.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item i, CreativeTabs tabs, List itemList) {
/* 473 */     super.func_149666_a(i, tabs, itemList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
/* 479 */     return createNewTileEntity(p_149915_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 485 */     Ic2Items.activeLuminator = new ItemStack((Block)this);
/* 486 */     Ic2Items.luminator = new ItemStack((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isGlass(IBlockAccess world, int x, int y, int z) {
/* 491 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 492 */     if (tile instanceof TileEntityLuminator) {
/*     */       
/* 494 */       TileEntityLuminator luma = (TileEntityLuminator)tile;
/* 495 */       return (luma.getGlass() != null);
/*     */     } 
/* 497 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getGlass(IBlockAccess world, int x, int y, int z) {
/* 502 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 503 */     if (tile instanceof TileEntityLuminator) {
/*     */       
/* 505 */       TileEntityLuminator luma = (TileEntityLuminator)tile;
/* 506 */       return luma.getGlass();
/*     */     } 
/* 508 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 514 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 515 */     if (tile instanceof TileEntityLuminator)
/*     */     {
/* 517 */       return ((TileEntityLuminator)tile).active ? 15 : 0;
/*     */     }
/* 519 */     return super.getLightValue(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
/* 525 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 526 */     if (tile instanceof TileEntityLuminator) {
/*     */       
/* 528 */       TileEntityLuminator lumi = (TileEntityLuminator)tile;
/* 529 */       if (lumi.getGlass() != null) {
/*     */         
/* 531 */         ItemStack item = lumi.getGlass();
/* 532 */         Block block = Block.func_149634_a(item.func_77973_b());
/* 533 */         if (block != Blocks.field_150350_a)
/*     */         {
/* 535 */           return block.func_149638_a(par1Entity);
/*     */         }
/*     */       } 
/*     */     } 
/* 539 */     return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\BlockLuminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */