/*     */ package ic2.core.block.generator.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.generator.subBlocks.MetaBlockBasicTurbine;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class BlockConverter
/*     */   extends BlockMultiID
/*     */ {
/*  25 */   private static Converter[] converterBlocks = new Converter[16];
/*     */ 
/*     */   
/*     */   public BlockConverter() {
/*  29 */     super(Material.field_151573_f);
/*  30 */     func_149711_c(3.0F);
/*  31 */     func_149672_a(Block.field_149777_j);
/*  32 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addTile(Converter par1) {
/*  37 */     converterBlocks[par1.meta] = par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  43 */     addTile((Converter)new MetaBlockBasicTurbine());
/*  44 */     for (int i = 0; i < converterBlocks.length; i++) {
/*     */       
/*  46 */       if (isValidMeta(i))
/*     */       {
/*  48 */         getConverter(i).init((Block)this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int p_149692_1_) {
/*  56 */     if (!isValidMeta(p_149692_1_))
/*     */     {
/*  58 */       return 0;
/*     */     }
/*  60 */     return p_149692_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  66 */     if (isValidMeta(meta))
/*     */     {
/*  68 */       return getConverter(meta).getIconSheet();
/*     */     }
/*  70 */     return Ic2Icons.getTexture("conv");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  76 */     if (isValidMeta(meta))
/*     */     {
/*  78 */       return getConverter(meta).getMaxSheetSize();
/*     */     }
/*  80 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  86 */     if (isValidMeta(meta))
/*     */     {
/*  88 */       return getConverter(meta).getIconMeta();
/*     */     }
/*  90 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World p0, int p1) {
/*  96 */     if (isValidMeta(p1))
/*     */     {
/*  98 */       return getConverter(p1).getNewTile();
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item j, CreativeTabs tabs, List<ItemStack> itemList) {
/* 106 */     for (int i = 0; i < converterBlocks.length; i++) {
/*     */       
/* 108 */       if (isValidMeta(i))
/*     */       {
/* 110 */         itemList.add(new ItemStack((Block)this, 1, i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidMeta(int meta) {
/* 117 */     return (converterBlocks[meta] != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Converter getConverter(int meta) {
/* 122 */     return converterBlocks[meta];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int i, int j, int k) {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockOpaqueCube(World world, int i, int j, int k) {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149747_d(IBlockAccess world, int i, int j, int k, int l) {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/* 148 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     TileEntityBlock te = (TileEntityBlock)world.func_147438_o(i, j, k);
/* 153 */     if (entityliving == null) {
/*     */       
/* 155 */       te.setFacing((short)1);
/*     */     }
/*     */     else {
/*     */       
/* 159 */       int yaw = MathHelper.func_76128_c((entityliving.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 160 */       int pitch = Math.round(entityliving.field_70125_A);
/* 161 */       if (pitch >= 65) {
/*     */         
/* 163 */         te.setFacing((short)1);
/*     */       }
/* 165 */       else if (pitch <= -65) {
/*     */         
/* 167 */         te.setFacing((short)0);
/*     */       }
/*     */       else {
/*     */         
/* 171 */         switch (yaw) {
/*     */ 
/*     */           
/*     */           case 0:
/* 175 */             te.setFacing((short)2);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 1:
/* 180 */             te.setFacing((short)5);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 185 */             te.setFacing((short)3);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 190 */             te.setFacing((short)4);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class Converter
/*     */   {
/*     */     final int meta;
/*     */     
/*     */     public Converter(int par1) {
/* 203 */       this.meta = par1;
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract TileEntityBlock getNewTile();
/*     */     
/*     */     public IIcon[] getIconSheet() {
/* 210 */       return Ic2Icons.getTexture("conv");
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconMeta() {
/* 215 */       return this.meta;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(Block par1) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void addInfo(List par1) {}
/*     */ 
/*     */     
/*     */     public abstract String getUnlocizedName();
/*     */ 
/*     */     
/*     */     public int getMaxSheetSize() {
/* 231 */       return 16;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\block\BlockConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */