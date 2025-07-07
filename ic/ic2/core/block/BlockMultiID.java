/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public abstract class BlockMultiID
/*     */   extends BlockContainerCommon
/*     */ {
/*  31 */   public static int[][] sideAndFacingToSpriteOffset = new int[][] { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
/*     */   
/*  33 */   public boolean[] canRender = new boolean[6];
/*     */   
/*     */   public int colorMulti;
/*     */   public boolean specialRender = false;
/*     */   
/*     */   protected BlockMultiID(Material mat) {
/*  39 */     super(mat);
/*  40 */     func_149647_a((CreativeTabs)IC2.tabIC2);
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
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149720_d(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
/*  53 */     if (this.specialRender)
/*     */     {
/*  55 */       return this.colorMulti;
/*     */     }
/*  57 */     return super.func_149720_d(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_149646_a(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
/*  64 */     if (this.specialRender)
/*     */     {
/*  66 */       if (!this.canRender[p_149646_5_])
/*     */       {
/*  68 */         return false;
/*     */       }
/*     */     }
/*  71 */     return super.func_149646_a(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/*  77 */     TileEntity te = iblockaccess.func_147438_o(i, j, k);
/*  78 */     int meta = iblockaccess.func_72805_g(i, j, k);
/*  79 */     int extraMeta = getIconMeta(meta);
/*  80 */     int facing = 0;
/*  81 */     if (te instanceof TileEntityBlock) {
/*     */       
/*  83 */       TileEntityBlock block = (TileEntityBlock)te;
/*  84 */       facing = block.getFacing();
/*  85 */       if (block.hasTileMeta())
/*     */       {
/*  87 */         extraMeta = block.getTileMeta();
/*     */       }
/*     */     } 
/*     */     
/*  91 */     if (isActive(iblockaccess, i, j, k))
/*     */     {
/*  93 */       return getIconSheet(meta)[extraMeta + (sideAndFacingToSpriteOffset[side][facing] + 6) * getMaxSheetSize(meta)];
/*     */     }
/*  95 */     return getIconSheet(meta)[extraMeta + sideAndFacingToSpriteOffset[side][facing] * getMaxSheetSize(meta)];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/* 101 */     return getIconSheet(meta)[getIconMeta(meta) + sideAndFacingToSpriteOffset[side][3] * getMaxSheetSize(meta)];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/* 107 */     if (entityPlayer.func_70093_af())
/*     */     {
/* 109 */       return false;
/*     */     }
/* 111 */     TileEntity te = world.func_147438_o(x, y, z);
/* 112 */     return (te instanceof IHasGui && (!IC2.platform.isSimulating() || IC2.platform.launchGui(entityPlayer, (IHasGui)te)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/* 118 */     ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
/* 119 */     TileEntity te = world.func_147438_o(x, y, z);
/* 120 */     if (te instanceof IInventory) {
/*     */       
/* 122 */       IInventory inv = (IInventory)te; int i;
/* 123 */       for (i = 0; i < inv.func_70302_i_(); i++) {
/*     */         
/* 125 */         ItemStack itemStack = inv.func_70301_a(i);
/* 126 */         if (itemStack != null) {
/*     */           
/* 128 */           ret.add(itemStack);
/* 129 */           inv.func_70299_a(i, (ItemStack)null);
/*     */         } 
/*     */       } 
/* 132 */       if (te instanceof TileEntityCropHarvester) {
/*     */         
/* 134 */         TileEntityCropHarvester.CustomInv customInv = ((TileEntityCropHarvester)te).inv;
/* 135 */         for (i = 0; i < customInv.func_70302_i_(); i++) {
/*     */           
/* 137 */           ItemStack itemStack = customInv.func_70301_a(i);
/* 138 */           if (itemStack != null) {
/*     */             
/* 140 */             ret.add(itemStack);
/* 141 */             customInv.func_70299_a(i, (ItemStack)null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 146 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block a, int b) {
/* 155 */     TileEntity te = world.func_147438_o(x, y, z);
/* 156 */     if (te instanceof TileEntityBlock)
/*     */     {
/* 158 */       ((TileEntityBlock)te).onBlockBreak(a, b);
/*     */     }
/* 160 */     boolean firstItem = true;
/* 161 */     for (ItemStack itemStack : getDrops(world, x, y, z, world.func_72805_g(x, y, z), 0)) {
/*     */       
/* 163 */       if (firstItem) {
/*     */         
/* 165 */         firstItem = false;
/*     */         
/*     */         continue;
/*     */       } 
/* 169 */       StackUtil.dropAsEntity(world, x, y, z, itemStack);
/*     */     } 
/*     */     
/* 172 */     super.func_149749_a(world, x, y, z, a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/* 178 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     TileEntityBlock te = (TileEntityBlock)world.func_147438_o(i, j, k);
/* 183 */     if (entityliving == null) {
/*     */       
/* 185 */       te.setFacing((short)2);
/*     */     }
/*     */     else {
/*     */       
/* 189 */       int l = MathHelper.func_76128_c((((Entity)entityliving).field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 190 */       switch (l) {
/*     */ 
/*     */         
/*     */         case 0:
/* 194 */           te.setFacing((short)2);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/* 199 */           te.setFacing((short)5);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 204 */           te.setFacing((short)3);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 209 */           te.setFacing((short)4);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k) {
/* 218 */     TileEntity te = iblockaccess.func_147438_o(i, j, k);
/* 219 */     return (te instanceof TileEntityBlock && ((TileEntityBlock)te).getActive());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item j, CreativeTabs tabs, List<ItemStack> itemList) {
/* 225 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 227 */       ItemStack is = new ItemStack((Block)this, 1, i);
/* 228 */       if (Item.func_150898_a((Block)this).func_77667_c(is) != null)
/*     */       {
/* 230 */         itemList.add(is);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 238 */     return new ItemStack((Block)this, 1, world.func_72805_g(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetSpecialRender() {
/* 243 */     this.canRender = new boolean[6];
/* 244 */     this.specialRender = false;
/* 245 */     this.colorMulti = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void applySpecialRender(RenderBlockCable.RenderInfo par1) {
/* 251 */     this.canRender = par1.renderSides;
/* 252 */     this.specialRender = true;
/* 253 */     this.colorMulti = par1.color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 259 */     return IC2.platform.getRenderId("rotated");
/*     */   }
/*     */   
/*     */   protected abstract IIcon[] getIconSheet(int paramInt);
/*     */   
/*     */   protected abstract int getIconMeta(int paramInt);
/*     */   
/*     */   protected abstract int getMaxSheetSize(int paramInt);
/*     */   
/*     */   public abstract TileEntityBlock createNewTileEntity(World paramWorld, int paramInt);
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockMultiID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */