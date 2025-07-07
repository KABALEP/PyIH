/*     */ package ic2.core.block.machine;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCentrifuge;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCharged;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCompacting;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropScanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropmatron;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectricEnchanter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityOreScanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityRotary;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySingularity;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySoundBeacon;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTesla;
/*     */ import ic2.core.block.machine.tileentity.TileEntityUraniumEnricher;
/*     */ import ic2.core.block.machine.tileentity.TileEntityVacuumCanner;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockMachine2
/*     */   extends BlockMultiID {
/*     */   public BlockMachine2() {
/*  46 */     super(Material.field_151573_f);
/*  47 */     func_149711_c(2.0F);
/*  48 */     func_149672_a(field_149777_j);
/*  49 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/*  55 */     return Ic2Icons.getTexture("bmach2");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/*  61 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/*  67 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int meta, Random p_149650_2_, int fortune) {
/*  73 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 0:
/*  77 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 4:
/*  81 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 5:
/*  85 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 6:
/*  89 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 7:
/*  93 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 8:
/*  97 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 9:
/* 101 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 10:
/* 105 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */ 
/*     */       
/*     */       case 12:
/* 109 */         return Ic2Items.generator.func_77973_b();
/*     */ 
/*     */       
/*     */       case 14:
/* 113 */         return Ic2Items.advancedMachine.func_77973_b();
/*     */     } 
/*     */ 
/*     */     
/* 117 */     return Ic2Items.machine.func_77973_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/* 124 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 0:
/* 128 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 3:
/* 132 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 4:
/* 136 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 5:
/* 140 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 6:
/* 144 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 7:
/* 148 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 8:
/* 152 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 9:
/* 156 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 10:
/* 160 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */ 
/*     */       
/*     */       case 12:
/* 164 */         return Ic2Items.generator.func_77960_j();
/*     */ 
/*     */       
/*     */       case 14:
/* 168 */         return Ic2Items.advancedMachine.func_77960_j();
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return Ic2Items.machine.func_77960_j();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World world, int meta) {
/* 180 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 0:
/* 184 */         return (TileEntityBlock)new TileEntityTeleporter();
/*     */ 
/*     */       
/*     */       case 1:
/* 188 */         return (TileEntityBlock)new TileEntityTesla();
/*     */ 
/*     */       
/*     */       case 2:
/* 192 */         return (TileEntityBlock)new TileEntityCropmatron();
/*     */ 
/*     */       
/*     */       case 3:
/* 196 */         return (TileEntityBlock)new TileEntitySingularity();
/*     */ 
/*     */       
/*     */       case 4:
/* 200 */         return (TileEntityBlock)new TileEntityCentrifuge();
/*     */ 
/*     */       
/*     */       case 5:
/* 204 */         return (TileEntityBlock)new TileEntityRotary();
/*     */ 
/*     */       
/*     */       case 6:
/* 208 */         return (TileEntityBlock)new TileEntityCharged();
/*     */ 
/*     */       
/*     */       case 7:
/* 212 */         return (TileEntityBlock)new TileEntityVacuumCanner();
/*     */ 
/*     */       
/*     */       case 8:
/* 216 */         return (TileEntityBlock)new TileEntityCompacting();
/*     */ 
/*     */       
/*     */       case 9:
/* 220 */         return (TileEntityBlock)new TileEntityElectricEnchanter();
/*     */ 
/*     */       
/*     */       case 10:
/* 224 */         return (TileEntityBlock)new TileEntityOreScanner();
/*     */ 
/*     */       
/*     */       case 11:
/* 228 */         return (TileEntityBlock)new TileEntityCropScanner();
/*     */ 
/*     */       
/*     */       case 12:
/* 232 */         return (TileEntityBlock)new TileEntityReactorPlanner();
/*     */ 
/*     */       
/*     */       case 13:
/* 236 */         return (TileEntityBlock)new TileEntitySoundBeacon();
/*     */ 
/*     */       
/*     */       case 14:
/* 240 */         return (TileEntityBlock)new TileEntityUraniumEnricher();
/*     */ 
/*     */       
/*     */       case 15:
/* 244 */         return (TileEntityBlock)new TileEntityCropHarvester();
/*     */     } 
/*     */ 
/*     */     
/* 248 */     return new TileEntityBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/* 256 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 257 */     if (tile != null)
/*     */     {
/* 259 */       if (tile instanceof IMachine)
/*     */       {
/* 261 */         return ((IMachine)tile).isRedstoneSensitive();
/*     */       }
/*     */     }
/* 264 */     return super.canConnectRedstone(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, float b, float c) {
/* 270 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 271 */     if (tile instanceof TileEntityReactorPlanner) {
/*     */       
/* 273 */       TileEntityReactorPlanner planner = (TileEntityReactorPlanner)tile;
/* 274 */       if (planner.onRightClick(entityPlayer, EnumFacing.func_82600_a(side), FMLCommonHandler.instance().getEffectiveSide()))
/*     */       {
/* 276 */         return true;
/*     */       }
/*     */     } 
/* 279 */     if (entityPlayer.func_70093_af())
/*     */     {
/* 281 */       return false;
/*     */     }
/*     */     
/* 284 */     if (tile instanceof TileEntityTeleporter && entityPlayer.func_71045_bC() == null) {
/*     */       
/* 286 */       TileEntityTeleporter tele = (TileEntityTeleporter)tile;
/* 287 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 289 */         if (tele.targetSet) {
/*     */           
/* 291 */           IC2.platform.messagePlayer(entityPlayer, StatCollector.func_74837_a("blockInfo.teleporterTarget.name", new Object[] { Integer.valueOf(tele.targetDimension), Integer.valueOf(tele.targetX), Integer.valueOf(tele.targetY), Integer.valueOf(tele.targetZ) }));
/* 292 */           return true;
/*     */         } 
/* 294 */         IC2.platform.messagePlayer(entityPlayer, StatCollector.func_74838_a("blockInfo.teleporterNoTarget.name"));
/*     */       } 
/* 296 */       return true;
/*     */     } 
/* 298 */     return super.func_149727_a(world, x, y, z, entityPlayer, side, a, b, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 305 */     switch (stack.func_77960_j()) {
/*     */       case 0:
/* 307 */         return EnumRarity.rare;
/*     */       case 3: case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 12:
/*     */       case 14:
/*     */       case 15:
/* 318 */         return EnumRarity.uncommon;
/* 319 */     }  return EnumRarity.common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 326 */     Ic2Items.teleporter = new ItemStack((Block)this, 1, 0);
/* 327 */     Ic2Items.teslaCoil = new ItemStack((Block)this, 1, 1);
/* 328 */     Ic2Items.cropmatron = new ItemStack((Block)this, 1, 2);
/* 329 */     Ic2Items.singularityCompressor = new ItemStack((Block)this, 1, 3);
/* 330 */     Ic2Items.centrifugeExtractor = new ItemStack((Block)this, 1, 4);
/* 331 */     Ic2Items.rotaryMacerator = new ItemStack((Block)this, 1, 5);
/* 332 */     Ic2Items.chargedElectrolyzer = new ItemStack((Block)this, 1, 6);
/* 333 */     Ic2Items.vacuumCanner = new ItemStack((Block)this, 1, 7);
/* 334 */     Ic2Items.compactingRecycler = new ItemStack((Block)this, 1, 8);
/* 335 */     Ic2Items.electricEnchanter = new ItemStack((Block)this, 1, 9);
/* 336 */     Ic2Items.cropScanner = new ItemStack((Block)this, 1, 11);
/* 337 */     Ic2Items.oreScanner = new ItemStack((Block)this, 1, 10);
/* 338 */     Ic2Items.reactorPlanner = new ItemStack((Block)this, 1, 12);
/* 339 */     Ic2Items.soundBeacon = new ItemStack((Block)this, 1, 13);
/* 340 */     Ic2Items.uraniumEnricher = new ItemStack((Block)this, 1, 14);
/* 341 */     Ic2Items.cropHarvester = new ItemStack((Block)this, 1, 15);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\BlockMachine2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */