/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class BlockChargePads
/*     */   extends BlockMultiID
/*     */ {
/*     */   public BlockChargePads() {
/*  32 */     super(Material.field_151573_f);
/*  33 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*  34 */     func_149711_c(1.5F);
/*  35 */     func_149663_c("chargePad");
/*  36 */     func_149672_a(field_149777_j);
/*  37 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  43 */     Ic2Items.lvChargePad = new ItemStack((Block)this, 1, TileEntityChargePad.ChargePadType.LV.ordinal());
/*  44 */     Ic2Items.mvChargePad = new ItemStack((Block)this, 1, TileEntityChargePad.ChargePadType.MV.ordinal());
/*  45 */     Ic2Items.hvChargePad = new ItemStack((Block)this, 1, TileEntityChargePad.ChargePadType.HV.ordinal());
/*  46 */     Ic2Items.nuclearChargePad = new ItemStack((Block)this, 1, TileEntityChargePad.ChargePadType.Nuclear.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int p_149692_1_) {
/*  52 */     return p_149692_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  58 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  64 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  70 */     return Ic2Icons.getTexture("CPad");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/*  76 */     TileEntityChargePad.ChargePadType[] types = TileEntityChargePad.ChargePadType.values();
/*  77 */     if (meta >= 0 && meta < types.length)
/*     */     {
/*  79 */       return types[meta].createPad();
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/*  87 */     return (side == ForgeDirection.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRedstoneStrengh(IBlockAccess world, int x, int y, int z, int side) {
/*  92 */     if (side == ForgeDirection.DOWN.ordinal())
/*     */     {
/*  94 */       return 0;
/*     */     }
/*  96 */     return isPadActive(world, x, y, z) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149709_b(IBlockAccess world, int x, int y, int z, int side) {
/* 102 */     return getRedstoneStrengh(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149748_c(IBlockAccess world, int x, int y, int z, int side) {
/* 108 */     return getRedstoneStrengh(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPadActive(IBlockAccess world, int x, int y, int z) {
/* 119 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 120 */     if (tile instanceof TileEntityChargePad) {
/*     */       
/* 122 */       TileEntityChargePad pad = (TileEntityChargePad)tile;
/* 123 */       if (pad.isForcedOff)
/*     */       {
/* 125 */         return false;
/*     */       }
/* 127 */       return pad.getActive();
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149746_a(World world, int x, int y, int z, Entity entity, float maxImpact) {
/* 135 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     TileEntityChargePad.ChargePadType type = TileEntityChargePad.ChargePadType.getType(world.func_72805_g(x, y, z));
/* 140 */     if (type == null) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     if (type.maxImpact >= maxImpact) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 149 */     if (entity instanceof EntityLivingBase)
/*     */     {
/* 151 */       ((EntityLivingBase)entity).func_70097_a((DamageSource)IC2DamageSource.electricity, (3 + 3 * type.ordinal()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int x, int y, int z, Entity entity) {
/* 158 */     if (world.field_72995_K || !(entity instanceof EntityLivingBase)) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 163 */     if (!(tile instanceof TileEntityChargePad)) {
/*     */       return;
/*     */     }
/*     */     
/* 167 */     TileEntityChargePad pad = (TileEntityChargePad)tile;
/* 168 */     if (pad.isForcedOff) {
/*     */       return;
/*     */     }
/*     */     
/* 172 */     if (pad.getActive()) {
/*     */       
/* 174 */       pad.activeTime = 20;
/*     */       return;
/*     */     } 
/* 177 */     pad.setActive(true);
/* 178 */     pad.activeTime = 20;
/* 179 */     ((NetworkManager)IC2.network.get()).initiateTileEntityEvent((TileEntity)pad, 1, true);
/* 180 */     for (ForgeDirection dir : ForgeDirection.values()) {
/*     */       
/* 182 */       if (world.func_72899_e(x + dir.offsetZ, y + dir.offsetY, z + dir.offsetZ))
/*     */       {
/* 184 */         world.func_147460_e(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, (Block)this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149721_r() {
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149734_b(World world, int x, int y, int z, Random rand) {
/* 205 */     if (!isPadActive((IBlockAccess)world, x, y, z)) {
/*     */       return;
/*     */     }
/*     */     
/* 209 */     ((TileEntityChargePad)world.func_147438_o(x, y, z)).spawnParticals(rand);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/* 215 */     if (entityPlayer.func_70093_af())
/*     */     {
/* 217 */       return false;
/*     */     }
/* 219 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 220 */     if (tile != null && tile instanceof TileEntityChargePad) {
/*     */       
/* 222 */       TileEntityChargePad pad = (TileEntityChargePad)tile;
/* 223 */       if ((pad.installedUpgrades[TileEntityChargePad.PadUpgrade.Drain.ordinal()] || pad.installedUpgrades[TileEntityChargePad.PadUpgrade.Damage.ordinal()]) && side != ForgeDirection.DOWN.ordinal())
/*     */       {
/* 225 */         return true;
/*     */       }
/* 227 */       return super.func_149727_a(world, x, y, z, entityPlayer, side, a, b, c);
/*     */     } 
/* 229 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\BlockChargePads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */