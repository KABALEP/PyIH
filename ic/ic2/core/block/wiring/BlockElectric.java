/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.IRareBlock;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class BlockElectric
/*     */   extends BlockMultiID
/*     */   implements IRareBlock {
/*     */   public BlockElectric() {
/*  30 */     super(Material.field_151573_f);
/*  31 */     func_149711_c(1.5F);
/*  32 */     func_149672_a(field_149777_j);
/*  33 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  39 */     switch (meta) {
/*     */       case 0:
/*  41 */         return Ic2Icons.getTexture("batBox");
/*  42 */       case 1: return Ic2Icons.getTexture("mfe");
/*  43 */       case 2: return Ic2Icons.getTexture("mfsu");
/*  44 */       case 7: return Ic2Icons.getTexture("bBox");
/*  45 */     }  return Ic2Icons.getTexture("belec");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  53 */     if (meta < 3 || meta == 7)
/*     */     {
/*  55 */       return 4;
/*     */     }
/*  57 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  63 */     if (meta >= 3 && meta <= 6)
/*     */     {
/*  65 */       return meta - 3;
/*     */     }
/*  67 */     if (meta == 8)
/*     */     {
/*  69 */       return 4;
/*     */     }
/*  71 */     if (meta == 9)
/*     */     {
/*  73 */       return 5;
/*     */     }
/*  75 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int meta, Random p_149650_2_, int p_149650_3_) {
/*  81 */     switch (meta) {
/*     */       case 0:
/*     */       case 3:
/*     */       case 7:
/*  85 */         return super.func_149650_a(meta, p_149650_2_, p_149650_3_);
/*  86 */       case 8: return Ic2Items.advancedMachine.func_77973_b();
/*  87 */     }  return Ic2Items.machine.func_77973_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/*  93 */     switch (meta) {
/*     */       case 0:
/*  95 */         return meta;
/*  96 */       case 3: return meta;
/*  97 */       case 7: return meta;
/*  98 */       case 8: return Ic2Items.advancedMachine.func_77960_j();
/*  99 */     }  return Ic2Items.machine.func_77960_j();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/* 105 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess iblockaccess, int x, int y, int z, int side) {
/* 111 */     TileEntity te = iblockaccess.func_147438_o(x, y, z);
/* 112 */     if (te instanceof TileEntityElectricBlock) {
/*     */       
/* 114 */       TileEntityElectricBlock electricBlock = (TileEntityElectricBlock)te;
/* 115 */       return electricBlock.isEmittingRedstone() ? 15 : 0;
/*     */     } 
/* 117 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149748_c(IBlockAccess iblockaccess, int x, int y, int z, int side) {
/* 123 */     TileEntity te = iblockaccess.func_147438_o(x, y, z);
/* 124 */     if (te instanceof TileEntityElectricBlock) {
/*     */       
/* 126 */       TileEntityElectricBlock electricBlock = (TileEntityElectricBlock)te;
/* 127 */       return electricBlock.isEmittingRedstone() ? 15 : 0;
/*     */     } 
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int i, int j, int k) {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149747_d(IBlockAccess world, int i, int j, int k, int l) {
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/* 150 */     switch (meta) {
/*     */       case 0:
/* 152 */         return (TileEntityBlock)new TileEntityElectricBatBox();
/* 153 */       case 1: return (TileEntityBlock)new TileEntityElectricMFE();
/* 154 */       case 2: return (TileEntityBlock)new TileEntityElectricMFSU();
/* 155 */       case 3: return new TileEntityTransformerLV();
/* 156 */       case 4: return new TileEntityTransformerMV();
/* 157 */       case 5: return new TileEntityTransformerHV();
/* 158 */       case 6: return new TileEntityTransformerEV();
/* 159 */       case 7: return (TileEntityBlock)new TileEntityBatteryBox();
/* 160 */       case 8: return new TileEntityAdjustableTransformer();
/* 161 */       case 9: return new TileEntityCreativeStorage();
/*     */     } 
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/* 169 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     TileEntityBlock te = (TileEntityBlock)world.func_147438_o(i, j, k);
/* 174 */     if (entityliving == null) {
/*     */       
/* 176 */       te.setFacing((short)1);
/*     */     }
/*     */     else {
/*     */       
/* 180 */       int yaw = MathHelper.func_76128_c((entityliving.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 181 */       int pitch = Math.round(entityliving.field_70125_A);
/* 182 */       if (pitch >= 65) {
/*     */         
/* 184 */         te.setFacing((short)1);
/*     */       }
/* 186 */       else if (pitch <= -65) {
/*     */         
/* 188 */         te.setFacing((short)0);
/*     */       }
/*     */       else {
/*     */         
/* 192 */         switch (yaw) {
/*     */ 
/*     */           
/*     */           case 0:
/* 196 */             te.setFacing((short)2);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 1:
/* 201 */             te.setFacing((short)5);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 206 */             te.setFacing((short)3);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 211 */             te.setFacing((short)4);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 223 */     return (stack.func_77960_j() == 2 || stack.func_77960_j() == 6 || stack.func_77960_j() == 5 || stack.func_77960_j() == 8) ? EnumRarity.uncommon : EnumRarity.common;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 235 */     Ic2Items.batBox = new ItemStack((Block)this, 1, 0);
/* 236 */     Ic2Items.mfeUnit = new ItemStack((Block)this, 1, 1);
/* 237 */     Ic2Items.mfsUnit = new ItemStack((Block)this, 1, 2);
/* 238 */     Ic2Items.lvTransformer = new ItemStack((Block)this, 1, 3);
/* 239 */     Ic2Items.mvTransformer = new ItemStack((Block)this, 1, 4);
/* 240 */     Ic2Items.hvTransformer = new ItemStack((Block)this, 1, 5);
/* 241 */     Ic2Items.evTransformer = new ItemStack((Block)this, 1, 6);
/* 242 */     Ic2Items.batteryBox = new ItemStack((Block)this, 1, 7);
/* 243 */     Ic2Items.adjustableTransformer = new ItemStack((Block)this, 1, 8);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\BlockElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */