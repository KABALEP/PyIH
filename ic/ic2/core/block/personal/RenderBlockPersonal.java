/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.RenderBlockRotation;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderBlockPersonal
/*    */   extends RenderBlockRotation
/*    */ {
/*    */   public static int renderId;
/* 21 */   private TileEntityPersonalChest invte = new TileEntityPersonalChest();
/*    */ 
/*    */   
/*    */   public RenderBlockPersonal() {
/* 25 */     renderId = RenderingRegistry.getNextAvailableRenderId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
/* 30 */     if (metadata != 0) {
/*    */       
/* 32 */       Tessellator var4 = Tessellator.field_78398_a;
/* 33 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 34 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 35 */       var4.func_78382_b();
/* 36 */       var4.func_78375_b(0.0F, -1.0F, 0.0F);
/* 37 */       renderer.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(0, metadata)));
/* 38 */       var4.func_78381_a();
/* 39 */       var4.func_78382_b();
/* 40 */       var4.func_78375_b(0.0F, 1.0F, 0.0F);
/* 41 */       renderer.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(1, metadata)));
/* 42 */       var4.func_78381_a();
/* 43 */       var4.func_78382_b();
/* 44 */       var4.func_78375_b(0.0F, 0.0F, -1.0F);
/* 45 */       renderer.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(2, metadata)));
/* 46 */       var4.func_78381_a();
/* 47 */       var4.func_78382_b();
/* 48 */       var4.func_78375_b(0.0F, 0.0F, 1.0F);
/* 49 */       renderer.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(3, metadata)));
/* 50 */       var4.func_78381_a();
/* 51 */       var4.func_78382_b();
/* 52 */       var4.func_78375_b(-1.0F, 0.0F, 0.0F);
/* 53 */       renderer.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(4, metadata)));
/* 54 */       var4.func_78381_a();
/* 55 */       var4.func_78382_b();
/* 56 */       var4.func_78375_b(1.0F, 0.0F, 0.0F);
/* 57 */       renderer.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderer.func_147758_b(block.func_149691_a(5, metadata)));
/* 58 */       var4.func_78381_a();
/* 59 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*    */     }
/*    */     else {
/*    */       
/* 63 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 64 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 65 */       TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)this.invte, 0.0D, 0.0D, 0.0D, 0.0F);
/* 66 */       GL11.glEnable(32826);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/* 72 */     if (world.func_72805_g(x, y, z) != 0)
/*    */     {
/* 74 */       super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
/*    */     }
/* 76 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int modelID) {
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 86 */     return renderId;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\RenderBlockPersonal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */