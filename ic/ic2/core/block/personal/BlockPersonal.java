/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPersonal
/*     */   extends BlockMultiID
/*     */ {
/*  27 */   public static Class tileEntityPersonalChestClass = TileEntityPersonalChest.class;
/*     */ 
/*     */   
/*     */   public BlockPersonal() {
/*  31 */     super(Material.field_151573_f);
/*  32 */     func_149722_s();
/*  33 */     func_149752_b(6000000.0F);
/*  34 */     func_149672_a(field_149777_j);
/*  35 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*  36 */     this.field_149785_s = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  42 */     return Ic2Icons.getTexture("bpersonal");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  48 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  54 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/*  59 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  69 */     return IC2.platform.getRenderId("personal");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  80 */     if (IC2.platform.isSimulating() && IC2.platform.isRendering())
/*     */     {
/*  82 */       return super.getDrops(world, x, y, z, metadata, fortune);
/*     */     }
/*  84 */     ArrayList<ItemStack> ret = new ArrayList();
/*  85 */     ret.add(new ItemStack((Block)this, 1, metadata));
/*  86 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/*     */     try {
/*  94 */       switch (meta) {
/*     */ 
/*     */         
/*     */         case 0:
/*  98 */           return tileEntityPersonalChestClass.newInstance();
/*     */ 
/*     */         
/*     */         case 1:
/* 102 */           return (TileEntityBlock)new TileEntityTradeOMat();
/*     */ 
/*     */         
/*     */         case 2:
/* 106 */           return (TileEntityBlock)new TileEntityEnergyOMat();
/*     */ 
/*     */         
/*     */         case 3:
/* 110 */           return (TileEntityBlock)new TileEntityFluidOMat();
/*     */       } 
/*     */ 
/*     */       
/* 114 */       return new TileEntityBlock();
/*     */ 
/*     */     
/*     */     }
/* 118 */     catch (Exception e) {
/*     */       
/* 120 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
/* 127 */     if (entityplayer.func_70093_af())
/*     */     {
/* 129 */       return false;
/*     */     }
/* 131 */     int meta = world.func_72805_g(i, j, k);
/* 132 */     TileEntity te = world.func_147438_o(i, j, k);
/* 133 */     return ((!IC2.platform.isSimulating() || meta == 1 || meta == 2 || !(te instanceof IPersonalBlock) || ((IPersonalBlock)te).canAccess(entityplayer)) && super.func_149727_a(world, i, j, k, entityplayer, side, a, b, c));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 140 */     return (stack.func_77960_j() == 0) ? EnumRarity.uncommon : EnumRarity.common;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/* 154 */     super.func_149689_a(world, i, j, k, entityliving, stack);
/* 155 */     if (entityliving instanceof EntityPlayer) {
/*     */       
/* 157 */       EntityPlayer player = (EntityPlayer)entityliving;
/* 158 */       TileEntity tile = world.func_147438_o(i, j, k);
/* 159 */       if (tile != null && tile instanceof IPersonalBlock)
/*     */       {
/* 161 */         ((IPersonalBlock)tile).canAccess(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 169 */     Ic2Items.personalSafe = new ItemStack((Block)this, 1, 0);
/* 170 */     Ic2Items.tradeOMat = new ItemStack((Block)this, 1, 1);
/* 171 */     Ic2Items.energyOMat = new ItemStack((Block)this, 1, 2);
/* 172 */     Ic2Items.fluidOMat = new ItemStack((Block)this, 1, 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\BlockPersonal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */