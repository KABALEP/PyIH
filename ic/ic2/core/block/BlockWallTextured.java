/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.event.PaintEvent;
/*     */ import ic2.api.event.RetextureEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockWallTextured
/*     */   extends BlockMultiID
/*     */   implements ITileEntityProvider
/*     */ {
/*     */   public BlockWallTextured() {
/*  34 */     super(Material.field_151576_e);
/*  35 */     func_149711_c(3.0F);
/*  36 */     func_149752_b(30.0F);
/*  37 */     func_149663_c("blockWallTextured");
/*  38 */     func_149672_a(Block.field_149769_e);
/*  39 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPaint(PaintEvent event) {
/*  45 */     if (event.world.func_147439_a(event.x, event.y, event.z) != this) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     TileEntity tile = event.world.func_147438_o(event.x, event.y, event.z);
/*  50 */     if (tile instanceof TileEntityTexturedWall) {
/*     */       
/*  52 */       TileEntityTexturedWall wall = (TileEntityTexturedWall)tile;
/*  53 */       event.painted = wall.setColor(event.side, IEnergyConductorColored.WireColor.values()[event.color + 1]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRetexture(RetextureEvent event) {
/*  60 */     Block block = event.world.func_147439_a(event.x, event.y, event.z);
/*  61 */     if (block != this && !(block instanceof BlockWall)) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     if (!validContent(event)) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     int meta = -1;
/*  70 */     if (block != this) {
/*     */       
/*  72 */       meta = event.world.func_72805_g(event.x, event.y, event.z);
/*  73 */       event.world.func_147449_b(event.x, event.y, event.z, (Block)this);
/*     */     } 
/*  75 */     TileEntity tile = event.world.func_147438_o(event.x, event.y, event.z);
/*  76 */     if (tile instanceof TileEntityTexturedWall) {
/*     */       
/*  78 */       TileEntityTexturedWall wall = (TileEntityTexturedWall)tile;
/*  79 */       if (meta != -1)
/*     */       {
/*  81 */         wall.setColor(IEnergyConductorColored.WireColor.values()[meta + 1]);
/*     */       }
/*  83 */       event.applied = wall.setIcon(event.side, event.referencedBlock, event.referencedMeta, event.referencedSide);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validContent(RetextureEvent event) {
/*  89 */     if (event.isCanceled() || event.referencedBlock == null || event.referencedBlock == Blocks.field_150350_a)
/*     */     {
/*  91 */       return false;
/*     */     }
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random r) {
/* 105 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_149644_j(int i) {
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 117 */     return Ic2Items.constructionFoam.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityBlock createNewTileEntity(World p_149915_1_, int p_149915_2_) {
/* 123 */     return new TileEntityTexturedWall();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 129 */     Ic2Items.texturedWall = new ItemStack((Block)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon[] getIconSheet(int meta) {
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIconMeta(int meta) {
/* 141 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxSheetSize(int meta) {
/* 147 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/* 153 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 154 */     if (tile != null && tile instanceof TileEntityTexturedWall) {
/*     */       
/* 156 */       TileEntityTexturedWall wall = (TileEntityTexturedWall)tile;
/* 157 */       return wall.getIcon(side);
/*     */     } 
/* 159 */     return Ic2Icons.getTexture("bcable")[187];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/* 165 */     return Ic2Icons.getTexture("bcable")[187];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item j, CreativeTabs tabs, List itemList) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 176 */     return IC2.platform.getRenderId("wall");
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockWallTextured.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */