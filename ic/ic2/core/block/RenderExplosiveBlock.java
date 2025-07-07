/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderExplosiveBlock
/*    */   extends Render
/*    */ {
/* 21 */   public RenderBlocks blockRenderer = new RenderBlocks();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_153_a(EntityIC2Explosive entitytntprimed, double d, double d1, double d2, float f, float f1) {
/* 27 */     GL11.glPushMatrix();
/* 28 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 29 */     if (entitytntprimed.fuse - f1 + 1.0F < 10.0F) {
/*    */       
/* 31 */       float f2 = 1.0F - (entitytntprimed.fuse - f1 + 1.0F) / 10.0F;
/* 32 */       if (f2 < 0.0F)
/*    */       {
/* 34 */         f2 = 0.0F;
/*    */       }
/* 36 */       if (f2 > 1.0F)
/*    */       {
/* 38 */         f2 = 1.0F;
/*    */       }
/* 40 */       f2 *= f2;
/* 41 */       f2 *= f2;
/* 42 */       float f3 = 1.0F + f2 * 0.3F;
/* 43 */       GL11.glScalef(f3, f3, f3);
/*    */     } 
/* 45 */     float f4 = (1.0F - (entitytntprimed.fuse - f1 + 1.0F) / 100.0F) * 0.8F;
/* 46 */     func_110776_a(TextureMap.field_110575_b);
/* 47 */     this.blockRenderer.func_147800_a(entitytntprimed.renderBlock, 0, entitytntprimed.func_70013_c(f1));
/* 48 */     if (entitytntprimed.fuse / 5 % 2 == 0) {
/*    */       
/* 50 */       GL11.glDisable(3553);
/* 51 */       GL11.glDisable(2896);
/* 52 */       GL11.glEnable(3042);
/* 53 */       GL11.glBlendFunc(770, 772);
/* 54 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, f4);
/* 55 */       this.blockRenderer.func_147800_a(entitytntprimed.renderBlock, 0, 1.0F);
/* 56 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */       GL11.glDisable(3042);
/* 58 */       GL11.glEnable(2896);
/* 59 */       GL11.glEnable(3553);
/*    */     } 
/* 61 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
/* 66 */     func_153_a((EntityIC2Explosive)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity entity) {
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderExplosiveBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */