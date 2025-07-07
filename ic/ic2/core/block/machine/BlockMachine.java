/*     */ package ic2.core.block.machine;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCompressor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElecFurnace;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityExtractor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityInduction;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMacerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityPump;
/*     */ import ic2.core.block.machine.tileentity.TileEntityRecycler;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockMachine
/*     */   extends BlockMultiID
/*     */ {
/*     */   public BlockMachine() {
/*  41 */     super(Material.field_151573_f);
/*  42 */     func_149711_c(2.0F);
/*  43 */     func_149672_a(field_149777_j);
/*  44 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  50 */     return Ic2Icons.getTexture("bmach");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  56 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  62 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/*  67 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 1:
/*  71 */         return meta;
/*     */ 
/*     */       
/*     */       case 2:
/*  75 */         return meta;
/*     */ 
/*     */       
/*     */       case 9:
/*  79 */         return meta;
/*     */ 
/*     */       
/*     */       case 12:
/*  83 */         return 12;
/*     */ 
/*     */       
/*     */       case 13:
/*  87 */         return 12;
/*     */ 
/*     */       
/*     */       case 14:
/*  91 */         return 12;
/*     */ 
/*     */       
/*     */       case 15:
/*  95 */         return 12;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/* 107 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 1:
/* 111 */         return (TileEntityBlock)new TileEntityIronFurnace();
/*     */ 
/*     */       
/*     */       case 2:
/* 115 */         return (TileEntityBlock)new TileEntityElecFurnace();
/*     */ 
/*     */       
/*     */       case 3:
/* 119 */         return (TileEntityBlock)new TileEntityMacerator();
/*     */ 
/*     */       
/*     */       case 4:
/* 123 */         return (TileEntityBlock)new TileEntityExtractor();
/*     */ 
/*     */       
/*     */       case 5:
/* 127 */         return (TileEntityBlock)new TileEntityCompressor();
/*     */ 
/*     */       
/*     */       case 6:
/* 131 */         return (TileEntityBlock)new TileEntityCanner();
/*     */ 
/*     */       
/*     */       case 7:
/* 135 */         return (TileEntityBlock)new TileEntityMiner();
/*     */ 
/*     */       
/*     */       case 8:
/* 139 */         return (TileEntityBlock)new TileEntityPump();
/*     */ 
/*     */       
/*     */       case 9:
/* 143 */         return (TileEntityBlock)new TileEntityMagnetizer();
/*     */ 
/*     */       
/*     */       case 10:
/* 147 */         return (TileEntityBlock)new TileEntityElectrolyzer();
/*     */ 
/*     */       
/*     */       case 11:
/* 151 */         return (TileEntityBlock)new TileEntityRecycler();
/*     */ 
/*     */       
/*     */       case 13:
/* 155 */         return (TileEntityBlock)new TileEntityInduction();
/*     */ 
/*     */       
/*     */       case 14:
/* 159 */         return (TileEntityBlock)new TileEntityMatter();
/*     */ 
/*     */       
/*     */       case 15:
/* 163 */         return (TileEntityBlock)new TileEntityTerra();
/*     */     } 
/*     */     
/* 166 */     return new TileEntityBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149734_b(World world, int i, int j, int k, Random random) {
/* 171 */     if (!IC2.platform.isRendering()) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     int meta = world.func_72805_g(i, j, k);
/* 176 */     if (meta == 1 && BlockMultiID.isActive((IBlockAccess)world, i, j, k)) {
/*     */       
/* 178 */       TileEntity te = world.func_147438_o(i, j, k);
/* 179 */       int facing = (te instanceof TileEntityBlock) ? ((TileEntityBlock)te).getFacing() : 0;
/* 180 */       float f = i + 0.5F;
/* 181 */       float f2 = j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
/* 182 */       float f3 = k + 0.5F;
/* 183 */       float f4 = 0.52F;
/* 184 */       float f5 = random.nextFloat() * 0.6F - 0.3F;
/* 185 */       switch (facing) {
/*     */ 
/*     */         
/*     */         case 4:
/* 189 */           world.func_72869_a("smoke", (f - f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/* 190 */           world.func_72869_a("flame", (f - f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/* 195 */           world.func_72869_a("smoke", (f + f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/* 196 */           world.func_72869_a("flame", (f + f4), f2, (f3 + f5), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 201 */           world.func_72869_a("smoke", (f + f5), f2, (f3 - f4), 0.0D, 0.0D, 0.0D);
/* 202 */           world.func_72869_a("flame", (f + f5), f2, (f3 - f4), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 207 */           world.func_72869_a("smoke", (f + f5), f2, (f3 + f4), 0.0D, 0.0D, 0.0D);
/* 208 */           world.func_72869_a("flame", (f + f5), f2, (f3 + f4), 0.0D, 0.0D, 0.0D);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 213 */     if (meta == 3 && BlockMultiID.isActive((IBlockAccess)world, i, j, k)) {
/*     */       
/* 215 */       float f6 = i + 1.0F;
/* 216 */       float f7 = j + 1.0F;
/* 217 */       float f8 = k + 1.0F;
/* 218 */       for (int z = 0; z < 4; z++) {
/*     */         
/* 220 */         float fmod = -0.2F - random.nextFloat() * 0.6F;
/* 221 */         float f1mod = -0.1F + random.nextFloat() * 0.2F;
/* 222 */         float f2mod = -0.2F - random.nextFloat() * 0.6F;
/* 223 */         world.func_72869_a("smoke", (f6 + fmod), (f7 + f1mod), (f8 + f2mod), 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/* 231 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 232 */     if (tile != null) {
/*     */       
/* 234 */       if (tile instanceof IMachine)
/*     */       {
/* 236 */         return ((IMachine)tile).isRedstoneSensitive();
/*     */       }
/* 238 */       if (tile instanceof TileEntityMatter)
/*     */       {
/* 240 */         return true;
/*     */       }
/*     */     } 
/* 243 */     return super.canConnectRedstone(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 250 */     return (stack.func_77960_j() == 15 || stack.func_77960_j() == 13 || stack.func_77960_j() == 12) ? EnumRarity.uncommon : ((stack.func_77960_j() == 14) ? EnumRarity.rare : EnumRarity.common);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/* 256 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 257 */     if (tile != null && tile instanceof TileEntityMagnetizer) {
/*     */       
/* 259 */       ((TileEntityMagnetizer)tile).onRightClick(entityPlayer);
/* 260 */       return false;
/*     */     } 
/* 262 */     return super.func_149727_a(world, x, y, z, entityPlayer, side, a, b, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 268 */     Ic2Items.machine = new ItemStack((Block)this, 1, 0);
/* 269 */     Ic2Items.advancedMachine = new ItemStack((Block)this, 1, 12);
/* 270 */     Ic2Items.ironFurnace = new ItemStack((Block)this, 1, 1);
/* 271 */     Ic2Items.electroFurnace = new ItemStack((Block)this, 1, 2);
/* 272 */     Ic2Items.macerator = new ItemStack((Block)this, 1, 3);
/* 273 */     Ic2Items.extractor = new ItemStack((Block)this, 1, 4);
/* 274 */     Ic2Items.compressor = new ItemStack((Block)this, 1, 5);
/* 275 */     Ic2Items.canner = new ItemStack((Block)this, 1, 6);
/* 276 */     Ic2Items.miner = new ItemStack((Block)this, 1, 7);
/* 277 */     Ic2Items.pump = new ItemStack((Block)this, 1, 8);
/* 278 */     Ic2Items.magnetizer = new ItemStack((Block)this, 1, 9);
/* 279 */     Ic2Items.electrolyzer = new ItemStack((Block)this, 1, 10);
/* 280 */     Ic2Items.recycler = new ItemStack((Block)this, 1, 11);
/* 281 */     Ic2Items.inductionFurnace = new ItemStack((Block)this, 1, 13);
/* 282 */     Ic2Items.massFabricator = new ItemStack((Block)this, 1, 14);
/* 283 */     Ic2Items.terraformer = new ItemStack((Block)this, 1, 15);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\BlockMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */