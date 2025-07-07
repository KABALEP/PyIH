/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderBlockLuminator
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  20 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderblocks) {
/*  24 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  25 */     float f4 = 0.125F;
/*  26 */     block.func_149676_a(0.5F - f4, 0.0F, 0.5F - f4, 0.5F + f4, 1.0F, 0.5F + f4);
/*  27 */     renderblocks.func_147775_a(block);
/*  28 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  29 */     tessellator.func_78382_b();
/*  30 */     tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  31 */     renderblocks.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(0)));
/*  32 */     tessellator.func_78381_a();
/*  33 */     tessellator.func_78382_b();
/*  34 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  35 */     renderblocks.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(1)));
/*  36 */     tessellator.func_78381_a();
/*  37 */     tessellator.func_78382_b();
/*  38 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  39 */     renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(2)));
/*  40 */     tessellator.func_78381_a();
/*  41 */     tessellator.func_78382_b();
/*  42 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  43 */     renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(3)));
/*  44 */     tessellator.func_78381_a();
/*  45 */     tessellator.func_78382_b();
/*  46 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  47 */     renderblocks.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(4)));
/*  48 */     tessellator.func_78381_a();
/*  49 */     tessellator.func_78382_b();
/*  50 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  51 */     renderblocks.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(5)));
/*  52 */     tessellator.func_78381_a();
/*  53 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  54 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  55 */     renderblocks.func_147775_a(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess iblockaccess, int i, int j, int k, Block block, int modelId, RenderBlocks renderblocks) {
/*  60 */     BlockLuminator.renderPass = 0;
/*  61 */     float[] box = BlockLuminator.getBoxOfLuminator(iblockaccess, i, j, k);
/*  62 */     block.func_149676_a(box[0], box[1], box[2], box[3], box[4], box[5]);
/*  63 */     renderblocks.func_147775_a(block);
/*  64 */     renderblocks.func_147784_q(block, i, j, k);
/*  65 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  66 */     renderblocks.func_147775_a(block);
/*  67 */     BlockLuminator.renderPass = 1;
/*  68 */     if (BlockLuminator.isGlass(iblockaccess, i, j, k)) {
/*     */       
/*  70 */       BlockLuminator.colorMultiplier = getColor(iblockaccess, i, j, k);
/*  71 */       renderblocks.func_147784_q(block, i, j, k);
/*     */     } 
/*  73 */     BlockLuminator.colorMultiplier = -1;
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockAccess access, int x, int y, int z) {
/*  79 */     ItemStack item = BlockLuminator.getGlass(access, x, y, z);
/*  80 */     if (item != null) {
/*     */       
/*  82 */       Block block = Block.func_149634_a(item.func_77973_b());
/*  83 */       if (block != Blocks.field_150350_a) {
/*     */         
/*     */         try {
/*     */           
/*  87 */           return block.func_149720_d(access, x, y, z);
/*     */         }
/*  89 */         catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int id) {
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 104 */     return renderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\RenderBlockLuminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */