/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTexturedWall
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  19 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  30 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  31 */     if (!(tile instanceof TileEntityTexturedWall)) {
/*     */       
/*  33 */       renderer.func_147784_q(block, x, y, z);
/*  34 */       return true;
/*     */     } 
/*  36 */     TileEntityTexturedWall wall = (TileEntityTexturedWall)tile;
/*  37 */     BlockMultiID id = (BlockMultiID)block;
/*  38 */     RenderBlockCable.RenderInfo endInfo = new RenderBlockCable.RenderInfo((Block)id, 0, id.func_149720_d(world, x, y, z));
/*  39 */     List<RenderBlockCable.RenderInfo> infos = new ArrayList<>();
/*  40 */     for (int i = 0; i < 6; i++) {
/*     */       
/*  42 */       Block mimBlock = wall.storedBlocks[i];
/*  43 */       if (mimBlock == null || mimBlock == Blocks.field_150350_a) {
/*     */         
/*  45 */         endInfo.addSide(i);
/*     */       } else {
/*     */         
/*  48 */         int meta = wall.storedMetas[i];
/*     */         
/*     */         try {
/*  51 */           int color = mimBlock.func_149720_d(world, x, y, z);
/*  52 */           addToList(mimBlock, meta, color, i, infos);
/*     */         }
/*  54 */         catch (Exception e) {
/*     */           
/*  56 */           endInfo.addSide(i);
/*     */         } 
/*     */       } 
/*  59 */     }  for (RenderBlockCable.RenderInfo info : infos) {
/*     */       
/*  61 */       if (!info.hasSides()) {
/*     */         continue;
/*     */       }
/*     */       
/*  65 */       id.applySpecialRender(info);
/*  66 */       renderer.func_147784_q(block, x, y, z);
/*     */     } 
/*  68 */     if (!endInfo.hasSides()) {
/*     */       
/*  70 */       id.resetSpecialRender();
/*  71 */       return true;
/*     */     } 
/*  73 */     id.applySpecialRender(endInfo);
/*  74 */     renderer.func_147784_q(block, x, y, z);
/*  75 */     id.resetSpecialRender();
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToList(Block par1, int par2, int par3, int side, List<RenderBlockCable.RenderInfo> par4) {
/*  81 */     boolean found = false;
/*  82 */     for (RenderBlockCable.RenderInfo info : par4) {
/*     */       
/*  84 */       if (info.matches(par1, par2, par3)) {
/*     */         
/*  86 */         info.addSide(side);
/*  87 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  91 */     if (found) {
/*     */       return;
/*     */     }
/*     */     
/*  95 */     par4.add(new RenderBlockCable.RenderInfo(par1, par2, par3, side));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 107 */     return renderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderTexturedWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */