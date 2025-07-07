/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderBlockFence
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  19 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderblocks) {
/*  23 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  24 */     for (int i1 = 0; i1 < 4; i1++) {
/*     */       
/*  26 */       float f4 = 0.125F;
/*  27 */       if (i1 == 0)
/*     */       {
/*  29 */         block.func_149676_a(0.5F - f4, 0.0F, 0.0F, 0.5F + f4, 1.0F, f4 * 2.0F);
/*     */       }
/*  31 */       if (i1 == 1)
/*     */       {
/*  33 */         block.func_149676_a(0.5F - f4, 0.0F, 1.0F - f4 * 2.0F, 0.5F + f4, 1.0F, 1.0F);
/*     */       }
/*  35 */       f4 = 0.0625F;
/*  36 */       if (i1 == 2)
/*     */       {
/*  38 */         block.func_149676_a(0.5F - f4, 1.0F - f4 * 3.0F, -f4 * 2.0F, 0.5F + f4, 1.0F - f4, 1.0F + f4 * 2.0F);
/*     */       }
/*  40 */       if (i1 == 3)
/*     */       {
/*  42 */         block.func_149676_a(0.5F - f4, 0.5F - f4 * 3.0F, -f4 * 2.0F, 0.5F + f4, 0.5F - f4, 1.0F + f4 * 2.0F);
/*     */       }
/*  44 */       renderblocks.func_147775_a(block);
/*  45 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  46 */       tessellator.func_78382_b();
/*  47 */       tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  48 */       renderblocks.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(0)));
/*  49 */       tessellator.func_78381_a();
/*  50 */       tessellator.func_78382_b();
/*  51 */       tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  52 */       renderblocks.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(1)));
/*  53 */       tessellator.func_78381_a();
/*  54 */       tessellator.func_78382_b();
/*  55 */       tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  56 */       renderblocks.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(2)));
/*  57 */       tessellator.func_78381_a();
/*  58 */       tessellator.func_78382_b();
/*  59 */       tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  60 */       renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(3)));
/*  61 */       tessellator.func_78381_a();
/*  62 */       tessellator.func_78382_b();
/*  63 */       tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  64 */       renderblocks.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(4)));
/*  65 */       tessellator.func_78381_a();
/*  66 */       tessellator.func_78382_b();
/*  67 */       tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  68 */       renderblocks.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(5)));
/*  69 */       tessellator.func_78381_a();
/*  70 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*     */     } 
/*  72 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  73 */     renderblocks.func_147775_a(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess iblockaccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks) {
/*  78 */     float w = 0.25F;
/*  79 */     float d = (1.0F - w) / 2.0F;
/*  80 */     float wi = 0.125F;
/*  81 */     float di = (1.0F - wi) / 2.0F;
/*  82 */     float ht1 = 0.75F;
/*  83 */     float ht2 = 0.9375F;
/*  84 */     float hb1 = 0.375F;
/*  85 */     float hb2 = 0.5625F;
/*  86 */     block.func_149676_a(d, 0.0F, d, d + w, 1.0F, d + w);
/*  87 */     renderblocks.func_147775_a(block);
/*  88 */     renderblocks.func_147784_q(block, x, y, z);
/*  89 */     Block blockId = iblockaccess.func_147439_a(x + 1, y, z);
/*  90 */     if (blockId == block || blockId == Blocks.field_150422_aJ) {
/*     */       
/*  92 */       block.func_149676_a(d + w, ht1, di, 1.0F + d, ht2, di + wi);
/*  93 */       renderblocks.func_147775_a(block);
/*  94 */       renderblocks.func_147784_q(block, x, y, z);
/*  95 */       block.func_149676_a(d + w, hb1, di, 1.0F + d, hb2, di + wi);
/*  96 */       renderblocks.func_147775_a(block);
/*  97 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     } 
/*  99 */     blockId = iblockaccess.func_147439_a(x, y, z + 1);
/* 100 */     if (blockId == block || blockId == Blocks.field_150422_aJ) {
/*     */       
/* 102 */       block.func_149676_a(di, ht1, d + w, di + wi, ht2, 1.0F + d);
/* 103 */       renderblocks.func_147775_a(block);
/* 104 */       renderblocks.func_147784_q(block, x, y, z);
/* 105 */       block.func_149676_a(di, hb1, d + w, di + wi, hb2, 1.0F + d);
/* 106 */       renderblocks.func_147775_a(block);
/* 107 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     } 
/* 109 */     blockId = iblockaccess.func_147439_a(x - 1, y, z);
/* 110 */     if (blockId == Blocks.field_150422_aJ) {
/*     */       
/* 112 */       block.func_149676_a(-d, ht1, di, d, ht2, di + wi);
/* 113 */       renderblocks.func_147784_q(block, x, y, z);
/* 114 */       block.func_149676_a(-d, hb1, di, d, hb2, di + wi);
/* 115 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     } 
/* 117 */     blockId = iblockaccess.func_147439_a(x, y, z - 1);
/* 118 */     if (blockId == Blocks.field_150422_aJ) {
/*     */       
/* 120 */       block.func_149676_a(di, ht1, -d, di + wi, ht2, d);
/* 121 */       renderblocks.func_147775_a(block);
/* 122 */       renderblocks.func_147784_q(block, x, y, z);
/* 123 */       block.func_149676_a(di, hb1, -d, di + wi, hb2, d);
/* 124 */       renderblocks.func_147775_a(block);
/* 125 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     } 
/* 127 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 128 */     renderblocks.func_147775_a(block);
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int id) {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 139 */     return renderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderBlockFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */