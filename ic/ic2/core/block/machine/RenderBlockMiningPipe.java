/*    */ package ic2.core.block.machine;
/*    */ 
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderBlockMiningPipe
/*    */   implements ISimpleBlockRenderingHandler
/*    */ {
/*    */   public static int renderId;
/*    */   
/*    */   public RenderBlockMiningPipe() {
/* 22 */     renderId = RenderingRegistry.getNextAvailableRenderId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderblocks) {
/* 27 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 28 */     float f4 = 0.125F;
/* 29 */     block.func_149676_a(0.5F - f4, 0.0F, 0.5F - f4, 0.5F + f4, 1.0F, 0.5F + f4);
/* 30 */     renderblocks.func_147775_a(block);
/* 31 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 32 */     tessellator.func_78382_b();
/* 33 */     tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/* 34 */     renderblocks.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(0)));
/* 35 */     tessellator.func_78381_a();
/* 36 */     tessellator.func_78382_b();
/* 37 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/* 38 */     renderblocks.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(1)));
/* 39 */     tessellator.func_78381_a();
/* 40 */     tessellator.func_78382_b();
/* 41 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/* 42 */     renderblocks.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(2)));
/* 43 */     tessellator.func_78381_a();
/* 44 */     tessellator.func_78382_b();
/* 45 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/* 46 */     renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(3)));
/* 47 */     tessellator.func_78381_a();
/* 48 */     tessellator.func_78382_b();
/* 49 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/* 50 */     renderblocks.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(4)));
/* 51 */     tessellator.func_78381_a();
/* 52 */     tessellator.func_78382_b();
/* 53 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/* 54 */     renderblocks.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147758_b(block.func_149733_h(5)));
/* 55 */     tessellator.func_78381_a();
/* 56 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 57 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 58 */     renderblocks.func_147775_a(block);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks) {
/* 63 */     float f = 0.25F;
/* 64 */     float fi = (1.0F - f) / 2.0F;
/* 65 */     block.func_149676_a(fi, 0.0F, fi, fi + f, 1.0F, fi + f);
/* 66 */     renderblocks.func_147775_a(block);
/* 67 */     renderblocks.func_147784_q(block, x, y, z);
/* 68 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 69 */     renderblocks.func_147775_a(block);
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int id) {
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 80 */     return renderId;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\RenderBlockMiningPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */