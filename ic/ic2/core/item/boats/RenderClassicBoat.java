/*    */ package ic2.core.item.boats;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class RenderClassicBoat
/*    */   extends Render
/*    */ {
/* 14 */   public static ModelBoat modelBoat = new ModelBoat();
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBoat(EntityClassicBoat p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
/* 19 */     GL11.glPushMatrix();
/* 20 */     GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
/* 21 */     GL11.glRotatef(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
/* 22 */     float f2 = p_76986_1_.getTimeSinceHit() - p_76986_9_;
/* 23 */     float f3 = p_76986_1_.getDamageTaken() - p_76986_9_;
/*    */     
/* 25 */     if (f3 < 0.0F)
/*    */     {
/* 27 */       f3 = 0.0F;
/*    */     }
/*    */     
/* 30 */     if (f2 > 0.0F)
/*    */     {
/* 32 */       GL11.glRotatef(MathHelper.func_76126_a(f2) * f2 * f3 / 10.0F * p_76986_1_.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 35 */     float f4 = 0.75F;
/* 36 */     GL11.glScalef(f4, f4, f4);
/* 37 */     GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
/* 38 */     func_110777_b(p_76986_1_);
/* 39 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 40 */     this; modelBoat.func_78088_a(p_76986_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 41 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
/* 47 */     renderBoat((EntityClassicBoat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity entity) {
/* 53 */     return ((EntityClassicBoat)entity).getTexture();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\RenderClassicBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */