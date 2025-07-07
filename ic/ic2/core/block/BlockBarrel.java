/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockBarrel
/*     */   extends BlockContainer
/*     */   implements IExtraData
/*     */ {
/*     */   public BlockBarrel() {
/*  28 */     super(Material.field_151575_d);
/*  29 */     func_149711_c(1.0F);
/*  30 */     func_149672_a(field_149766_f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/*  40 */     int treetap = ((TileEntityBarrel)iblockaccess.func_147438_o(i, j, k)).treetapSide;
/*  41 */     if (treetap > 1 && side == treetap)
/*     */     {
/*  43 */       return Ic2Icons.getTexture("b0")[29];
/*     */     }
/*  45 */     return func_149691_a(side, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  50 */     if (side < 2)
/*     */     {
/*  52 */       return Ic2Icons.getTexture("b0")[28];
/*     */     }
/*  54 */     return Ic2Icons.getTexture("b0")[27];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/*  60 */     return ((TileEntityBarrel)world.func_147438_o(x, y, z)).rightclick(entityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int x, int y, int z, EntityPlayer player) {
/*  66 */     TileEntityBarrel barrel = (TileEntityBarrel)world.func_147438_o(x, y, z);
/*  67 */     if (barrel.treetapSide > 1) {
/*     */       
/*  69 */       if (IC2.platform.isSimulating())
/*     */       {
/*  71 */         StackUtil.dropAsEntity(world, x, y, z, new ItemStack(Ic2Items.treetap.func_77973_b()));
/*     */       }
/*  73 */       barrel.treetapSide = 0;
/*  74 */       barrel.update();
/*  75 */       barrel.drainLiquid(1);
/*     */       return;
/*     */     } 
/*  78 */     if (IC2.platform.isSimulating())
/*     */     {
/*  80 */       StackUtil.dropAsEntity(world, x, y, z, new ItemStack(Ic2Items.barrel.func_77973_b(), 1, barrel.calculateMetaValue()));
/*     */     }
/*  82 */     world.func_147449_b(x, y, z, Block.func_149634_a(Ic2Items.scaffold.func_77973_b()));
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World world) {
/*  87 */     return new TileEntityBarrel();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  92 */     ArrayList<ItemStack> re = new ArrayList();
/*  93 */     re.add(new ItemStack(Ic2Items.scaffold.func_77973_b()));
/*  94 */     re.add(new ItemStack(Ic2Items.barrel.func_77973_b(), 1, 0));
/*  95 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
/* 101 */     return createNewTileEntity(p_149915_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 107 */     Ic2Items.blockBarrel = new ItemStack((Block)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockBarrel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */