/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderBlockCrop
/*    */   implements ISimpleBlockRenderingHandler {
/* 19 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*    */   
/*    */   public static void renderBlockCropsImpl(Block block, IBlockAccess blockAccess, int x, int y, int z) {
/*    */     TextureAtlasSprite textureAtlasSprite;
/* 23 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 24 */     IIcon j = block.func_149673_e(blockAccess, x, y, z, 0);
/* 25 */     if (j == null)
/*    */     {
/* 27 */       textureAtlasSprite = Minecraft.func_71410_x().func_147117_R().func_110572_b(null);
/*    */     }
/* 29 */     double d = x;
/* 30 */     double d2 = y - 0.0625D;
/* 31 */     double d3 = z;
/* 32 */     double d4 = textureAtlasSprite.func_94209_e();
/* 33 */     double d5 = textureAtlasSprite.func_94212_f();
/* 34 */     double d6 = textureAtlasSprite.func_94206_g();
/* 35 */     double d7 = textureAtlasSprite.func_94210_h();
/* 36 */     double d8 = d + 0.5D - 0.25D;
/* 37 */     double d9 = d + 0.5D + 0.25D;
/* 38 */     double d10 = d3 + 0.5D - 0.5D;
/* 39 */     double d11 = d3 + 0.5D + 0.5D;
/* 40 */     tessellator.func_78374_a(d8, d2 + 1.0D, d10, d4, d6);
/* 41 */     tessellator.func_78374_a(d8, d2 + 0.0D, d10, d4, d7);
/* 42 */     tessellator.func_78374_a(d8, d2 + 0.0D, d11, d5, d7);
/* 43 */     tessellator.func_78374_a(d8, d2 + 1.0D, d11, d5, d6);
/* 44 */     tessellator.func_78374_a(d8, d2 + 1.0D, d11, d4, d6);
/* 45 */     tessellator.func_78374_a(d8, d2 + 0.0D, d11, d4, d7);
/* 46 */     tessellator.func_78374_a(d8, d2 + 0.0D, d10, d5, d7);
/* 47 */     tessellator.func_78374_a(d8, d2 + 1.0D, d10, d5, d6);
/* 48 */     tessellator.func_78374_a(d9, d2 + 1.0D, d11, d4, d6);
/* 49 */     tessellator.func_78374_a(d9, d2 + 0.0D, d11, d4, d7);
/* 50 */     tessellator.func_78374_a(d9, d2 + 0.0D, d10, d5, d7);
/* 51 */     tessellator.func_78374_a(d9, d2 + 1.0D, d10, d5, d6);
/* 52 */     tessellator.func_78374_a(d9, d2 + 1.0D, d10, d4, d6);
/* 53 */     tessellator.func_78374_a(d9, d2 + 0.0D, d10, d4, d7);
/* 54 */     tessellator.func_78374_a(d9, d2 + 0.0D, d11, d5, d7);
/* 55 */     tessellator.func_78374_a(d9, d2 + 1.0D, d11, d5, d6);
/* 56 */     d8 = d + 0.5D - 0.5D;
/* 57 */     d9 = d + 0.5D + 0.5D;
/* 58 */     d10 = d3 + 0.5D - 0.25D;
/* 59 */     d11 = d3 + 0.5D + 0.25D;
/* 60 */     tessellator.func_78374_a(d8, d2 + 1.0D, d10, d4, d6);
/* 61 */     tessellator.func_78374_a(d8, d2 + 0.0D, d10, d4, d7);
/* 62 */     tessellator.func_78374_a(d9, d2 + 0.0D, d10, d5, d7);
/* 63 */     tessellator.func_78374_a(d9, d2 + 1.0D, d10, d5, d6);
/* 64 */     tessellator.func_78374_a(d9, d2 + 1.0D, d10, d4, d6);
/* 65 */     tessellator.func_78374_a(d9, d2 + 0.0D, d10, d4, d7);
/* 66 */     tessellator.func_78374_a(d8, d2 + 0.0D, d10, d5, d7);
/* 67 */     tessellator.func_78374_a(d8, d2 + 1.0D, d10, d5, d6);
/* 68 */     tessellator.func_78374_a(d9, d2 + 1.0D, d11, d4, d6);
/* 69 */     tessellator.func_78374_a(d9, d2 + 0.0D, d11, d4, d7);
/* 70 */     tessellator.func_78374_a(d8, d2 + 0.0D, d11, d5, d7);
/* 71 */     tessellator.func_78374_a(d8, d2 + 1.0D, d11, d5, d6);
/* 72 */     tessellator.func_78374_a(d8, d2 + 1.0D, d11, d4, d6);
/* 73 */     tessellator.func_78374_a(d8, d2 + 0.0D, d11, d4, d7);
/* 74 */     tessellator.func_78374_a(d9, d2 + 0.0D, d11, d5, d7);
/* 75 */     tessellator.func_78374_a(d9, d2 + 1.0D, d11, d5, d6);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess blockAccess, int i, int j, int k, Block block, int modelId, RenderBlocks renderer) {
/* 84 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 85 */     TileEntity te = blockAccess.func_147438_o(i, j, k);
/* 86 */     tessellator.func_78380_c(block.func_149677_c(blockAccess, i, j, k));
/* 87 */     tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 88 */     renderBlockCropsImpl(block, blockAccess, i, j, k);
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int id) {
/* 94 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 99 */     return renderId;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderBlockCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */