/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderBlockLuminatorMultipart
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  17 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
/*  22 */     RenderingRegistry.instance().renderInventoryBlock(renderer, Block.func_149634_a(Ic2Items.luminator.func_77973_b()), 0, IC2.platform.getRenderId("luminator"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  28 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  29 */     if (!(tile instanceof TileEntityLuminatorMultipart))
/*     */     {
/*  31 */       return true;
/*     */     }
/*  33 */     BlockLuminatorMultipart part = (BlockLuminatorMultipart)block;
/*  34 */     TileEntityLuminatorMultipart multi = (TileEntityLuminatorMultipart)tile;
/*  35 */     int type = (multi.cable != null) ? multi.cable.func_77960_j() : 1;
/*  36 */     float thickness = TileEntityCable.getCableThickness(type);
/*  37 */     thickness /= 2.0F;
/*  38 */     BlockLuminatorMultipart.renderPass = 1;
/*  39 */     renderer.func_147782_a(0.5D - thickness, 0.5D - thickness, 0.5D - thickness, 0.5D + thickness, 0.5D + thickness, 0.5D + thickness);
/*  40 */     renderer.func_147784_q(block, x, y, z);
/*     */     
/*  42 */     for (int i = 0; i < 6; i++) {
/*     */       
/*  44 */       boolean lamp = multi.hasSide(i);
/*  45 */       boolean cable = multi.canConnect(i);
/*  46 */       if (lamp) {
/*     */         
/*  48 */         cable = false;
/*  49 */         BlockLuminatorMultipart.renderPass = 0;
/*  50 */         BlockLuminatorMultipart.side = i;
/*  51 */         float[] array = BlockLuminatorMultipart.getBoundingBoxForSide(i);
/*  52 */         block.func_149676_a(array[0], array[1], array[2], array[3], array[4], array[5]);
/*  53 */         renderer.func_147775_a(block);
/*  54 */         renderer.func_147784_q(block, x, y, z);
/*  55 */         BlockLuminatorMultipart.side = -1;
/*     */       } 
/*  57 */       if (lamp || cable) {
/*     */         
/*  59 */         BlockLuminatorMultipart.renderPass = 1;
/*  60 */         renderSide(i, cable, lamp, thickness, block, renderer, x, y, z);
/*     */       } 
/*     */     } 
/*  63 */     block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderSide(int side, boolean canConnect, boolean lamp, float thickness, Block block, RenderBlocks render, int x, int y, int z) {
/*  70 */     float min = canConnect ? 0.0F : (lamp ? 0.0625F : 0.0F);
/*  71 */     float max = canConnect ? 1.0F : (lamp ? 0.9375F : 1.0F);
/*  72 */     float[] array = getRenderBounds(side, min, max, thickness);
/*  73 */     block.func_149676_a(array[0], array[1], array[2], array[3], array[4], array[5]);
/*  74 */     render.func_147775_a(block);
/*  75 */     render.func_147784_q(block, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getRenderBounds(int side, float min, float max, float half) {
/*  80 */     switch (side) {
/*     */       case 0:
/*  82 */         return new float[] { 0.5F - half, min, 0.5F - half, 0.5F + half, 0.5F - half, 0.5F + half };
/*  83 */       case 1: return new float[] { 0.5F - half, 0.5F + half, 0.5F - half, 0.5F + half, max, 0.5F + half };
/*  84 */       case 2: return new float[] { 0.5F - half, 0.5F - half, min, 0.5F + half, 0.5F + half, 0.5F - half };
/*  85 */       case 3: return new float[] { 0.5F - half, 0.5F - half, 0.5F + half, 0.5F + half, 0.5F + half, max };
/*  86 */       case 4: return new float[] { min, 0.5F - half, 0.5F - half, 0.5F - half, 0.5F + half, 0.5F + half };
/*  87 */       case 5: return new float[] { 0.5F + half, 0.5F - half, 0.5F - half, max, 0.5F + half, 0.5F + half };
/*     */     } 
/*  89 */     return new float[] { min, min, min, max, max, max };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 101 */     return renderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\RenderBlockLuminatorMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */