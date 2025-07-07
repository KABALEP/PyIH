/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCrop
/*     */   extends BlockContainer implements IExtraData {
/*     */   public static TileEntityCrop tempStore;
/*     */   
/*     */   public BlockCrop() {
/*  30 */     super(Material.field_151585_k);
/*  31 */     func_149711_c(0.8F);
/*  32 */     func_149752_b(0.2F);
/*  33 */     func_149663_c("blockCrop");
/*  34 */     func_149672_a(Block.field_149779_h);
/*  35 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*  36 */     func_149676_a(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int p_149915_2_) {
/*  42 */     return createNewTileEntity(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World world) {
/*  47 */     return new TileEntityCrop();
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/*  52 */     return ((TileEntityCrop)iblockaccess.func_147438_o(i, j, k)).getSprite();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister par1IconRegister) {
/*  58 */     Crops.instance.startSpriteRegistration(par1IconRegister);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  63 */     return Ic2Icons.getTexture("bc")[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/*  68 */     return (world.func_147439_a(i, j - 1, k) == Blocks.field_150458_ak && super.func_149742_c(world, i, j, k));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/*  73 */     super.func_149695_a(world, i, j, k, l);
/*  74 */     if (world.func_147439_a(i, j - 1, k) != Blocks.field_150458_ak) {
/*     */       
/*  76 */       world.func_147468_f(i, j, k);
/*  77 */       func_149697_b(world, i, j, k, 0, 0);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       ((TileEntityCrop)world.func_147438_o(i, j, k)).onNeighbourChange();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/*  87 */     double d = 0.2D;
/*  88 */     return AxisAlignedBB.func_72330_a(d, 0.0D, d, 1.0D - d, 0.7D, 1.0D - d);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/*  94 */     ((TileEntityCrop)world.func_147438_o(i, j, k)).onEntityCollision(entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 109 */     return IC2.platform.getRenderId("crop");
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 114 */     return ((TileEntityCrop)iblockaccess.func_147438_o(i, j, k)).emitRedstone() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int i, int j, int k, Block a, int b) {
/* 119 */     if (world != null)
/*     */     {
/* 121 */       tempStore = (TileEntityCrop)world.func_147438_o(i, j, k);
/*     */     }
/* 123 */     super.func_149749_a(world, i, j, k, a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
/* 128 */     if (tempStore != null)
/*     */     {
/* 130 */       tempStore.onBlockDestroyed();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k) {
/* 136 */     return ((TileEntityCrop)iblockaccess.func_147438_o(i, j, k)).getEmittedLight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
/* 141 */     ((TileEntityCrop)world.func_147438_o(i, j, k)).leftclick(entityplayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 146 */     if (IC2.platform.isRendering())
/*     */     {
/* 148 */       return true;
/*     */     }
/* 150 */     return ((TileEntityCrop)world.func_147438_o(i, j, k)).rightclick(entityplayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 158 */     Ic2Items.crop = new ItemStack((Block)this, 1, 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */