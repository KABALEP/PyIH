/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.event.PaintEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.BlockContainerCommon;
/*     */ import ic2.core.item.tool.ItemToolCutter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public class BlockLuminatorMultipart
/*     */   extends BlockContainerCommon {
/*  30 */   public static int renderPass = 0;
/*  31 */   public static int side = -1;
/*  32 */   ItemStack drop = null;
/*     */ 
/*     */   
/*     */   public BlockLuminatorMultipart() {
/*  36 */     super(Material.field_151592_s);
/*  37 */     func_149672_a(field_149778_k);
/*  38 */     func_149711_c(0.3F);
/*  39 */     func_149752_b(0.5F);
/*  40 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*  41 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onColoring(PaintEvent event) {
/*  47 */     TileEntity tile = event.world.func_147438_o(event.x, event.y, event.z);
/*  48 */     if (!(tile instanceof TileEntityLuminatorMultipart)) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     TileEntityLuminatorMultipart lumi = (TileEntityLuminatorMultipart)tile;
/*  53 */     if ((lumi.hasSide(event.side) && lumi.colors[event.side] == event.color) || (!lumi.hasSide(event.side) && lumi.cableColor == IEnergyConductorColored.WireColor.values()[event.color + 1])) {
/*     */       return;
/*     */     }
/*     */     
/*  57 */     event.painted = lumi.addColor(event.side, event.color);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int par1, int par2) {
/*  63 */     return Ic2Icons.getTexture("b0")[31];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*  70 */     if (renderPass == 0)
/*     */     {
/*  72 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  74 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  75 */     if (!(tile instanceof TileEntityLuminatorMultipart))
/*     */     {
/*  77 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  79 */     TileEntityLuminatorMultipart multiPart = (TileEntityLuminatorMultipart)tile;
/*  80 */     if (multiPart.cable == null)
/*     */     {
/*  82 */       return super.func_149673_e(world, x, y, z, side);
/*     */     }
/*  84 */     return Ic2Icons.getTexture("bcable")[multiPart.cable.func_77960_j() * 17 + multiPart.cableColor.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  90 */     Ic2Items.luminatorMultipart = new ItemStack((Block)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
/*  96 */     return (TileEntity)new TileEntityLuminatorMultipart();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 102 */     return IC2.platform.getRenderId("luminatorMulti");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockNormalCube(World world, int x, int y, int z) {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
/* 118 */     if (entityplayer.func_71045_bC() != null && entityplayer.func_71045_bC().func_77973_b() instanceof ItemToolCutter)
/*     */     {
/* 120 */       ItemToolCutter.cutInsulationFrom(entityplayer.func_71045_bC(), world, i, j, k, (EntityLivingBase)entityplayer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block p_149695_5_) {
/* 127 */     super.func_149695_a(world, x, y, z, p_149695_5_);
/* 128 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 129 */     if (tile instanceof TileEntityLuminatorMultipart)
/*     */     {
/* 131 */       ((TileEntityLuminatorMultipart)tile).onBlockChange();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getBoundingBoxForSide(int side) {
/* 137 */     float px = 0.0625F;
/* 138 */     switch (side) {
/*     */       case 0:
/* 140 */         return new float[] { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F * px, 1.0F };
/* 141 */       case 1: return new float[] { 0.0F, 15.0F * px, 0.0F, 1.0F, 1.0F, 1.0F };
/* 142 */       case 2: return new float[] { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F * px };
/* 143 */       case 3: return new float[] { 0.0F, 0.0F, 15.0F * px, 1.0F, 1.0F, 1.0F };
/* 144 */       case 4: return new float[] { 0.0F, 0.0F, 0.0F, 1.0F * px, 1.0F, 1.0F };
/* 145 */       case 5: return new float[] { 15.0F * px, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };
/*     */     } 
/* 147 */     return new float[] { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 153 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 154 */     if (tile instanceof TileEntityLuminatorMultipart)
/*     */     {
/* 156 */       return ((TileEntityLuminatorMultipart)tile).getActive() ? 15 : 0;
/*     */     }
/* 158 */     return super.getLightValue(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149720_d(IBlockAccess world, int x, int y, int z) {
/* 165 */     if (side != -1) {
/*     */       
/* 167 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 168 */       if (tile instanceof TileEntityLuminatorMultipart) {
/*     */         
/* 170 */         int color = ((TileEntityLuminatorMultipart)tile).colors[side];
/* 171 */         if (color != -1)
/*     */         {
/* 173 */           return ItemDye.field_150922_c[color];
/*     */         }
/*     */       } 
/*     */     } 
/* 177 */     return super.func_149720_d(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
/* 183 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 184 */     if (tile instanceof TileEntityLuminatorMultipart)
/*     */     {
/* 186 */       this.drop = ((TileEntityLuminatorMultipart)tile).getDrop();
/*     */     }
/* 188 */     super.func_149749_a(world, x, y, z, p_149749_5_, p_149749_6_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/* 194 */     ArrayList<ItemStack> items = new ArrayList<>();
/* 195 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 196 */     if (tile instanceof TileEntityLuminatorMultipart) {
/*     */       
/* 198 */       items.add(((TileEntityLuminatorMultipart)tile).getDrop());
/*     */ 
/*     */     
/*     */     }
/* 202 */     else if (this.drop != null) {
/*     */       
/* 204 */       items.add(this.drop);
/*     */     } 
/*     */     
/* 207 */     return items;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random p_149745_1_) {
/* 213 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\BlockLuminatorMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */