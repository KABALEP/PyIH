/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.event.PaintEvent;
/*     */ import ic2.api.event.RetextureEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.item.tool.ItemToolCutter;
/*     */ import ic2.core.util.AabbUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.client.particle.EntityDiggingFX;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class BlockCable
/*     */   extends BlockMultiID
/*     */ {
/*  44 */   private static Direction[] directions = Direction.values();
/*     */ 
/*     */   
/*     */   public BlockCable() {
/*  48 */     super(Material.field_151573_f);
/*  49 */     func_149711_c(0.2F);
/*  50 */     func_149672_a(field_149775_l);
/*  51 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/*  57 */     return "/ic2/sprites/block_cable.png";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  63 */     return Ic2Icons.getTexture("bcable");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  69 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  75 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int x, int y, int z, int side) {
/*  81 */     int cableType, color = 0;
/*  82 */     TileEntity te = iblockaccess.func_147438_o(x, y, z);
/*     */     
/*  84 */     int baseTexture = -1;
/*  85 */     if (te instanceof TileEntityCable) {
/*     */       
/*  87 */       TileEntityCable cable = (TileEntityCable)te;
/*  88 */       if (cable.foamed == 0) {
/*     */         
/*  90 */         cableType = cable.cableType;
/*  91 */         if (te instanceof TileEntityCableDetector || te instanceof TileEntityCableSplitter)
/*     */         {
/*  93 */           baseTexture = (te instanceof TileEntityCableDetector) ? 272 : 274;
/*  94 */           color = cable.getActive() ? 1 : 0;
/*     */         }
/*     */         else
/*     */         {
/*  98 */           color = cable.color.ordinal();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 103 */         if (cable.foamed == 1)
/*     */         {
/* 105 */           return Ic2Icons.getTexture("bcable")[276];
/*     */         }
/* 107 */         if (cable.foamed == 2 && cable.textureMimic) {
/*     */           
/* 109 */           Block block = cable.textureBlock[side];
/* 110 */           if (block != null && block != Blocks.field_150350_a)
/*     */           {
/* 112 */             return block.func_149691_a(cable.textureSide[side], cable.textureMeta[side]);
/*     */           }
/*     */         } 
/* 115 */         return Ic2Icons.getTexture("bcable")[176 + cable.foamColor.ordinal()];
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 120 */       cableType = iblockaccess.func_72805_g(x, y, z);
/*     */     } 
/* 122 */     if (baseTexture != -1)
/*     */     {
/* 124 */       return Ic2Icons.getTexture("bcable")[baseTexture + color];
/*     */     }
/* 126 */     return Ic2Icons.getTexture("bcable")[cableType * 17 + color];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition func_149731_a(World world, int x, int y, int z, Vec3 origin, Vec3 absDirection) {
/* 132 */     TileEntity te = world.func_147438_o(x, y, z);
/* 133 */     if (!(te instanceof TileEntityCable))
/*     */     {
/* 135 */       return null;
/*     */     }
/* 137 */     TileEntityCable cable = (TileEntityCable)te;
/* 138 */     if (cable.foamed > 0) {
/*     */       
/* 140 */       func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 141 */       return super.func_149731_a(world, x, y, z, origin, absDirection);
/*     */     } 
/* 143 */     Vec3 direction = Vec3.func_72443_a(absDirection.field_72450_a - origin.field_72450_a, absDirection.field_72448_b - origin.field_72448_b, absDirection.field_72449_c - origin.field_72449_c);
/* 144 */     double maxLength = direction.func_72433_c();
/* 145 */     double halfThickness = cable.getCableThickness() / 2.0D;
/* 146 */     boolean hit = false;
/* 147 */     Vec3 intersection = Vec3.func_72443_a(0.0D, 0.0D, 0.0D);
/* 148 */     Direction intersectingDirection = AabbUtil.getIntersection(origin, direction, AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y + 0.5D - halfThickness, z + 0.5D - halfThickness, x + 0.5D + halfThickness, y + 0.5D + halfThickness, z + 0.5D + halfThickness), intersection);
/* 149 */     if (intersectingDirection != null && intersection.func_72438_d(origin) <= maxLength)
/*     */     {
/* 151 */       hit = true;
/*     */     }
/* 153 */     for (Direction dir : directions) {
/*     */       
/* 155 */       if (hit) {
/*     */         break;
/*     */       }
/*     */       
/* 159 */       if (cable.canConnect(dir.toSideValue())) {
/*     */         
/* 161 */         AxisAlignedBB bbox = null;
/* 162 */         switch (dir) {
/*     */ 
/*     */           
/*     */           case XN:
/* 166 */             bbox = AxisAlignedBB.func_72330_a(x, y + 0.5D - halfThickness, z + 0.5D - halfThickness, x + 0.5D, y + 0.5D + halfThickness, z + 0.5D + halfThickness);
/*     */             break;
/*     */ 
/*     */           
/*     */           case XP:
/* 171 */             bbox = AxisAlignedBB.func_72330_a(x + 0.5D, y + 0.5D - halfThickness, z + 0.5D - halfThickness, x + 1.0D, y + 0.5D + halfThickness, z + 0.5D + halfThickness);
/*     */             break;
/*     */ 
/*     */           
/*     */           case YN:
/* 176 */             bbox = AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y, z + 0.5D - halfThickness, x + 0.5D + halfThickness, y + 0.5D, z + 0.5D + halfThickness);
/*     */             break;
/*     */ 
/*     */           
/*     */           case YP:
/* 181 */             bbox = AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y + 0.5D, z + 0.5D - halfThickness, x + 0.5D + halfThickness, y + 1.0D, z + 0.5D + halfThickness);
/*     */             break;
/*     */ 
/*     */           
/*     */           case ZN:
/* 186 */             bbox = AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y + 0.5D - halfThickness, z, x + 0.5D + halfThickness, y + 0.5D, z + 0.5D);
/*     */             break;
/*     */ 
/*     */           
/*     */           case ZP:
/* 191 */             bbox = AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y + 0.5D - halfThickness, z + 0.5D, x + 0.5D + halfThickness, y + 0.5D + halfThickness, z + 1.0D);
/*     */             break;
/*     */         } 
/*     */         
/* 195 */         intersectingDirection = AabbUtil.getIntersection(origin, direction, bbox, intersection);
/* 196 */         if (intersectingDirection != null && intersection.func_72438_d(origin) <= maxLength)
/*     */         {
/* 198 */           hit = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 202 */     if (hit)
/*     */     {
/* 204 */       return new MovingObjectPosition(x, y, z, intersectingDirection.toSideValue(), intersection);
/*     */     }
/* 206 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z, int meta) {
/* 211 */     double halfThickness = TileEntityCable.getCableThickness(meta);
/* 212 */     return AxisAlignedBB.func_72330_a(x + 0.5D - halfThickness, y + 0.5D - halfThickness, z + 0.5D - halfThickness, x + 0.5D + halfThickness, y + 0.5D + halfThickness, z + 0.5D + halfThickness);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int x, int y, int z) {
/* 218 */     return getCommonBoundingBoxFromPool(world, x, y, z, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
/* 224 */     return getCommonBoundingBoxFromPool(world, x, y, z, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCommonBoundingBoxFromPool(World world, int x, int y, int z, boolean selectionBoundingBox) {
/* 229 */     TileEntity te = world.func_147438_o(x, y, z);
/* 230 */     if (!(te instanceof TileEntityCable))
/*     */     {
/* 232 */       return getCollisionBoundingBoxFromPool(world, x, y, z, 3);
/*     */     }
/* 234 */     TileEntityCable cable = (TileEntityCable)te;
/* 235 */     if (cable.foamed > 0)
/*     */     {
/* 237 */       return AxisAlignedBB.func_72330_a(x, y, z, (x + 1), (y + 1), (z + 1));
/*     */     }
/* 239 */     double halfThickness = cable.getCableThickness() / 2.0D;
/* 240 */     double minX = x + 0.5D - halfThickness;
/* 241 */     double minY = y + 0.5D - halfThickness;
/* 242 */     double minZ = z + 0.5D - halfThickness;
/* 243 */     double maxX = x + 0.5D + halfThickness;
/* 244 */     double maxY = y + 0.5D + halfThickness;
/* 245 */     double maxZ = z + 0.5D + halfThickness;
/* 246 */     if (cable.canConnect(ForgeDirection.WEST.ordinal()))
/*     */     {
/* 248 */       minX = x;
/*     */     }
/* 250 */     if (cable.canConnect(ForgeDirection.DOWN.ordinal()))
/*     */     {
/* 252 */       minY = y;
/*     */     }
/* 254 */     if (cable.canConnect(ForgeDirection.NORTH.ordinal()))
/*     */     {
/* 256 */       minZ = z;
/*     */     }
/* 258 */     if (cable.canConnect(ForgeDirection.EAST.ordinal()))
/*     */     {
/* 260 */       maxX = (x + 1);
/*     */     }
/* 262 */     if (cable.canConnect(ForgeDirection.UP.ordinal()))
/*     */     {
/* 264 */       maxY = (y + 1);
/*     */     }
/* 266 */     if (cable.canConnect(ForgeDirection.SOUTH.ordinal()))
/*     */     {
/* 268 */       maxZ = (z + 1);
/*     */     }
/* 270 */     return AxisAlignedBB.func_72330_a(minX, minY, minZ, maxX, maxY, maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int x, int y, int z) {
/* 275 */     TileEntity te = world.func_147438_o(x, y, z);
/* 276 */     if (te instanceof TileEntityCable) {
/*     */       
/* 278 */       TileEntityCable cable = (TileEntityCable)te;
/* 279 */       if (cable.foamed > 0)
/*     */       {
/* 281 */         return true;
/*     */       }
/*     */     } 
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/* 290 */     ItemStack cur = entityPlayer.func_71045_bC();
/* 291 */     if (cur != null) {
/*     */       
/* 293 */       if (!IC2.platform.isSimulating())
/*     */       {
/* 295 */         return true;
/*     */       }
/* 297 */       TileEntity te = world.func_147438_o(x, y, z);
/* 298 */       if (te instanceof TileEntityCable) {
/*     */         
/* 300 */         TileEntityCable cable = (TileEntityCable)te;
/* 301 */         if (cur.func_77969_a(new ItemStack((Block)Blocks.field_150354_m))) {
/*     */           
/* 303 */           if (cable.foamed == 1 && cable.changeFoam((byte)2))
/*     */           {
/* 305 */             ItemStack itemStack = cur;
/* 306 */             itemStack.field_77994_a--;
/* 307 */             if (cur.field_77994_a <= 0)
/*     */             {
/* 309 */               entityPlayer.field_71071_by.field_70462_a[entityPlayer.field_71071_by.field_70461_c] = null;
/*     */             }
/* 311 */             return true;
/*     */           }
/*     */         
/* 314 */         } else if (cur.func_77969_a(Ic2Items.constructionFoam)) {
/*     */           
/* 316 */           if (cable.foamed == 0 && cable.changeFoam((byte)1)) {
/*     */             
/* 318 */             ItemStack itemStack = cur;
/* 319 */             itemStack.field_77994_a--;
/* 320 */             if (cur.field_77994_a <= 0)
/*     */             {
/* 322 */               entityPlayer.field_71071_by.field_70462_a[entityPlayer.field_71071_by.field_70461_c] = null;
/*     */             }
/* 324 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCableColor(IBlockAccess iblockaccess, int i, int j, int k) {
/* 334 */     TileEntity te = iblockaccess.func_147438_o(i, j, k);
/* 335 */     return (te instanceof TileEntityCable) ? ((TileEntityCable)te).color.ordinal() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPainted(PaintEvent event) {
/* 341 */     if (event.world.func_147439_a(event.x, event.y, event.z) != this) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 346 */     event.painted = ((TileEntityCable)event.world.func_147438_o(event.x, event.y, event.z)).changeColor(IEnergyConductorColored.WireColor.values()[event.color + 1]);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRetextured(RetextureEvent event) {
/* 352 */     if (event.world.func_147439_a(event.x, event.y, event.z) != this) {
/*     */       return;
/*     */     }
/*     */     
/* 356 */     event.applied = ((TileEntityCable)event.world.func_147438_o(event.x, event.y, event.z)).applyTexture(event.side, event.referencedBlock, event.referencedMeta, event.referencedSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(EntityPlayer player, int md) {
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/* 367 */     ArrayList<ItemStack> ret = new ArrayList();
/* 368 */     TileEntity te = world.func_147438_o(x, y, z);
/* 369 */     if (te instanceof TileEntityCable) {
/*     */       
/* 371 */       TileEntityCable cable = (TileEntityCable)te;
/* 372 */       ret.add(new ItemStack(Ic2Items.insulatedCopperCableItem.func_77973_b(), 1, cable.cableType));
/*     */     }
/*     */     else {
/*     */       
/* 376 */       ret.add(new ItemStack(Ic2Items.insulatedCopperCableItem.func_77973_b(), 1, metadata));
/*     */     } 
/* 378 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/* 385 */     if (meta == 11)
/*     */     {
/* 387 */       return new TileEntityCableDetector((short)meta);
/*     */     }
/* 389 */     if (meta == 12)
/*     */     {
/* 391 */       return new TileEntityCableSplitter((short)meta);
/*     */     }
/* 393 */     return new TileEntityCable((short)meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 398 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 404 */     return IC2.platform.getRenderId("cable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 409 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
/* 414 */     if (entityplayer.func_71045_bC() != null && entityplayer.func_71045_bC().func_77973_b() instanceof ItemToolCutter)
/*     */     {
/* 416 */       ItemToolCutter.cutInsulationFrom(entityplayer.func_71045_bC(), world, i, j, k, (EntityLivingBase)entityplayer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess iblockaccess, int x, int y, int z, int side) {
/* 422 */     TileEntity te = iblockaccess.func_147438_o(x, y, z);
/* 423 */     if (te instanceof TileEntityCableDetector)
/*     */     {
/* 425 */       return ((TileEntityCableDetector)te).getActive() ? 15 : 0;
/*     */     }
/* 427 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item i, CreativeTabs tabs, List itemList) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_149712_f(World world, int x, int y, int z) {
/* 437 */     return 0.2F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity exploder, World world, int x, int y, int z, double src_x, double src_y, double src_z) {
/* 442 */     TileEntity te = world.func_147438_o(x, y, z);
/* 443 */     if (te instanceof TileEntityCable) {
/*     */       
/* 445 */       TileEntityCable cable = (TileEntityCable)te;
/* 446 */       if (cable.foamed >= 2)
/*     */       {
/* 448 */         return 90.0F;
/*     */       }
/*     */     } 
/* 451 */     return 6.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int X, int Y, int Z, int direction) {
/* 456 */     int meta = world.func_72805_g(X, Y, Z);
/* 457 */     return (meta == 11 || meta == 12);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 463 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 464 */     if (tile != null && tile instanceof TileEntityCable) {
/*     */       
/* 466 */       TileEntityCable cable = (TileEntityCable)tile;
/* 467 */       return new ItemStack(Ic2Items.insulatedCopperCableItem.func_77973_b(), 1, cable.cableType);
/*     */     } 
/* 469 */     return new ItemStack(Ic2Items.insulatedCopperCableItem.func_77973_b(), 1, world.func_72805_g(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149726_b(World par1World, int par2, int par3, int par4) {
/* 475 */     super.func_149726_b(par1World, par2, par3, par4);
/* 476 */     for (ForgeDirection dir : ForgeDirection.values())
/*     */     {
/* 478 */       par1World.func_147460_e(par2, par3, par4, (Block)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149695_a(World par1World, int par2, int par3, int par4, Block par5) {
/* 487 */     super.func_149695_a(par1World, par2, par3, par4, par5);
/* 488 */     TileEntity tile = par1World.func_147438_o(par2, par3, par4);
/* 489 */     if (tile != null && tile instanceof TileEntityCable)
/*     */     {
/* 491 */       ((TileEntityCable)tile).onBlockChange();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 498 */     Ic2Items.copperCableBlock = new ItemStack((Block)this, 1, 1);
/* 499 */     Ic2Items.insulatedCopperCableBlock = new ItemStack((Block)this, 1, 0);
/* 500 */     Ic2Items.goldCableBlock = new ItemStack((Block)this, 1, 2);
/* 501 */     Ic2Items.insulatedGoldCableBlock = new ItemStack((Block)this, 1, 3);
/* 502 */     Ic2Items.doubleInsulatedGoldCableBlock = new ItemStack((Block)this, 1, 4);
/* 503 */     Ic2Items.ironCableBlock = new ItemStack((Block)this, 1, 5);
/* 504 */     Ic2Items.insulatedIronCableBlock = new ItemStack((Block)this, 1, 6);
/* 505 */     Ic2Items.doubleInsulatedIronCableBlock = new ItemStack((Block)this, 1, 7);
/* 506 */     Ic2Items.trippleInsulatedIronCableBlock = new ItemStack((Block)this, 1, 8);
/* 507 */     Ic2Items.glassFiberCableBlock = new ItemStack((Block)this, 1, 9);
/* 508 */     Ic2Items.tinCableBlock = new ItemStack((Block)this, 1, 10);
/* 509 */     Ic2Items.detectorCableBlock = new ItemStack((Block)this, 1, 11);
/* 510 */     Ic2Items.splitterCableBlock = new ItemStack((Block)this, 1, 12);
/* 511 */     Ic2Items.bronzeCableBlock = new ItemStack((Block)this, 1, 13);
/* 512 */     Ic2Items.insulatedBronzeCableBlock = new ItemStack((Block)this, 1, 14);
/* 513 */     Ic2Items.doubleInsulatedBronzeCableBlock = new ItemStack((Block)this, 1, 15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
/* 521 */     byte b0 = 4;
/* 522 */     for (int i1 = 0; i1 < b0; i1++) {
/*     */       
/* 524 */       for (int j1 = 0; j1 < b0; j1++) {
/*     */         
/* 526 */         for (int k1 = 0; k1 < b0; k1++) {
/*     */           
/* 528 */           double d0 = x + (i1 + 0.5D) / b0;
/* 529 */           double d1 = y + (j1 + 0.5D) / b0;
/* 530 */           double d2 = z + (k1 + 0.5D) / b0;
/* 531 */           EntityDiggingFX fx = (new EntityDiggingFX(world, d0, d1, d2, d0 - x - 0.5D, d1 - y - 0.5D, d2 - z - 0.5D, (Block)this, meta)).func_70596_a(x, y, z);
/* 532 */           fx.func_110125_a(func_149673_e((IBlockAccess)world, x, y, z, 0));
/* 533 */           effectRenderer.func_78873_a((EntityFX)fx);
/*     */         } 
/*     */       } 
/*     */     } 
/* 537 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
/* 544 */     Block block = worldObj.func_147439_a(target.field_72311_b, target.field_72312_c, target.field_72309_d);
/*     */     
/* 546 */     if (block.func_149688_o() != Material.field_151579_a) {
/*     */       
/* 548 */       float f = 0.1F;
/* 549 */       double d0 = target.field_72311_b + worldObj.field_73012_v.nextDouble() * (func_149753_y() - block.func_149704_x() - (f * 2.0F)) + f + block.func_149704_x();
/* 550 */       double d1 = target.field_72312_c + worldObj.field_73012_v.nextDouble() * (func_149669_A() - block.func_149665_z() - (f * 2.0F)) + f + block.func_149665_z();
/* 551 */       double d2 = target.field_72309_d + worldObj.field_73012_v.nextDouble() * (func_149693_C() - block.func_149706_B() - (f * 2.0F)) + f + block.func_149706_B();
/*     */       
/* 553 */       if (target.field_72310_e == 0)
/*     */       {
/* 555 */         d1 = target.field_72312_c + block.func_149665_z() - f;
/*     */       }
/*     */       
/* 558 */       if (target.field_72310_e == 1)
/*     */       {
/* 560 */         d1 = target.field_72312_c + block.func_149669_A() + f;
/*     */       }
/*     */       
/* 563 */       if (target.field_72310_e == 2)
/*     */       {
/* 565 */         d2 = target.field_72309_d + block.func_149706_B() - f;
/*     */       }
/*     */       
/* 568 */       if (target.field_72310_e == 3)
/*     */       {
/* 570 */         d2 = target.field_72309_d + block.func_149693_C() + f;
/*     */       }
/*     */       
/* 573 */       if (target.field_72310_e == 4)
/*     */       {
/* 575 */         d0 = target.field_72311_b + block.func_149704_x() - f;
/*     */       }
/*     */       
/* 578 */       if (target.field_72310_e == 5)
/*     */       {
/* 580 */         d0 = target.field_72311_b + block.func_149753_y() + f;
/*     */       }
/* 582 */       EntityFX effect = (new EntityDiggingFX(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, block, worldObj.func_72805_g(target.field_72311_b, target.field_72312_c, target.field_72309_d))).func_70596_a(target.field_72311_b, target.field_72312_c, target.field_72309_d).func_70543_e(0.2F).func_70541_f(0.6F);
/* 583 */       effect.func_110125_a(func_149673_e((IBlockAccess)worldObj, target.field_72311_b, target.field_72312_c, target.field_72309_d, target.field_72310_e));
/* 584 */       effectRenderer.func_78873_a(effect);
/*     */     } 
/* 586 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\BlockCable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */