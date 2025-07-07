/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class RenderBlockRotation
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  16 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
/*  21 */     Tessellator var4 = Tessellator.field_78398_a;
/*  22 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  23 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  24 */     var4.func_78382_b();
/*  25 */     var4.func_78375_b(0.0F, -1.0F, 0.0F);
/*  26 */     renderer.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(0, metadata)));
/*  27 */     var4.func_78381_a();
/*  28 */     var4.func_78382_b();
/*  29 */     var4.func_78375_b(0.0F, 1.0F, 0.0F);
/*  30 */     renderer.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(1, metadata)));
/*  31 */     var4.func_78381_a();
/*  32 */     var4.func_78382_b();
/*  33 */     var4.func_78375_b(0.0F, 0.0F, -1.0F);
/*  34 */     renderer.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(2, metadata)));
/*  35 */     var4.func_78381_a();
/*  36 */     var4.func_78382_b();
/*  37 */     var4.func_78375_b(0.0F, 0.0F, 1.0F);
/*  38 */     renderer.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(3, metadata)));
/*  39 */     var4.func_78381_a();
/*  40 */     var4.func_78382_b();
/*  41 */     var4.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  42 */     renderer.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(4, metadata)));
/*  43 */     var4.func_78381_a();
/*  44 */     var4.func_78382_b();
/*  45 */     var4.func_78375_b(1.0F, 0.0F, 0.0F);
/*  46 */     renderer.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(5, metadata)));
/*  47 */     var4.func_78381_a();
/*  48 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  54 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  55 */     if (tile instanceof TileEntityBlock) {
/*     */       
/*  57 */       int facing = ((TileEntityBlock)tile).getFacing();
/*  58 */       if (facing == 1) {
/*     */         
/*  60 */         renderer.field_147875_q = 3;
/*  61 */         renderer.field_147873_r = 3;
/*     */       }
/*  63 */       else if (facing == 2) {
/*     */         
/*  65 */         renderer.field_147865_v = 3;
/*  66 */         renderer.field_147867_u = 3;
/*     */       }
/*  68 */       else if (facing == 4) {
/*     */         
/*  70 */         renderer.field_147865_v = 2;
/*  71 */         renderer.field_147867_u = 1;
/*     */       }
/*  73 */       else if (facing == 5) {
/*     */         
/*  75 */         renderer.field_147865_v = 1;
/*  76 */         renderer.field_147867_u = 2;
/*     */       } 
/*     */       
/*  79 */       if (facing == 0 || facing == 1) {
/*     */         
/*  81 */         renderer.field_147869_t = 2;
/*  82 */         renderer.field_147871_s = 1;
/*     */       } 
/*     */     } 
/*  85 */     renderer.func_147784_q(block, x, y, z);
/*  86 */     renderer.field_147865_v = 0;
/*  87 */     renderer.field_147867_u = 0;
/*  88 */     renderer.field_147869_t = 0;
/*  89 */     renderer.field_147871_s = 0;
/*  90 */     renderer.field_147875_q = 0;
/*  91 */     renderer.field_147873_r = 0;
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 104 */     return renderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderBlockRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */