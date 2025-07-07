/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderCrossed
/*    */   extends Render
/*    */ {
/*    */   public ResourceLocation textureFile;
/*    */   
/*    */   public RenderCrossed(ResourceLocation file) {
/* 20 */     this.textureFile = file;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
/* 26 */     if (entity.field_70126_B == 0.0F && entity.field_70127_C == 0.0F) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 31 */     func_110776_a(func_110775_a(entity));
/*    */     
/* 33 */     GL11.glPushMatrix();
/* 34 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 35 */     GL11.glRotatef(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
/* 36 */     GL11.glRotatef(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * f1, 0.0F, 0.0F, 1.0F);
/* 37 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 38 */     int i = 0;
/* 39 */     float f2 = 0.0F;
/* 40 */     float f3 = 0.5F;
/* 41 */     float f4 = (0 + i * 10) / 32.0F;
/* 42 */     float f5 = (5 + i * 10) / 32.0F;
/* 43 */     float f6 = 0.0F;
/* 44 */     float f7 = 0.15625F;
/* 45 */     float f8 = (5 + i * 10) / 32.0F;
/* 46 */     float f9 = (10 + i * 10) / 32.0F;
/* 47 */     float f10 = 0.05625F;
/* 48 */     GL11.glEnable(32826);
/* 49 */     GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
/* 50 */     GL11.glScalef(f10, f10, f10);
/* 51 */     GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
/* 52 */     GL11.glNormal3f(f10, 0.0F, 0.0F);
/* 53 */     tessellator.func_78382_b();
/* 54 */     tessellator.func_78374_a(-7.0D, -2.0D, -2.0D, f6, f8);
/* 55 */     tessellator.func_78374_a(-7.0D, -2.0D, 2.0D, f7, f8);
/* 56 */     tessellator.func_78374_a(-7.0D, 2.0D, 2.0D, f7, f9);
/* 57 */     tessellator.func_78374_a(-7.0D, 2.0D, -2.0D, f6, f9);
/* 58 */     tessellator.func_78381_a();
/* 59 */     GL11.glNormal3f(-f10, 0.0F, 0.0F);
/* 60 */     tessellator.func_78382_b();
/* 61 */     tessellator.func_78374_a(-7.0D, 2.0D, -2.0D, f6, f8);
/* 62 */     tessellator.func_78374_a(-7.0D, 2.0D, 2.0D, f7, f8);
/* 63 */     tessellator.func_78374_a(-7.0D, -2.0D, 2.0D, f7, f9);
/* 64 */     tessellator.func_78374_a(-7.0D, -2.0D, -2.0D, f6, f9);
/* 65 */     tessellator.func_78381_a();
/* 66 */     for (int j = 0; j < 4; j++) {
/*    */       
/* 68 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 69 */       GL11.glNormal3f(0.0F, 0.0F, f10);
/* 70 */       tessellator.func_78382_b();
/* 71 */       tessellator.func_78374_a(-8.0D, -2.0D, 0.0D, f2, f4);
/* 72 */       tessellator.func_78374_a(8.0D, -2.0D, 0.0D, f3, f4);
/* 73 */       tessellator.func_78374_a(8.0D, 2.0D, 0.0D, f3, f5);
/* 74 */       tessellator.func_78374_a(-8.0D, 2.0D, 0.0D, f2, f5);
/* 75 */       tessellator.func_78381_a();
/*    */     } 
/*    */     
/* 78 */     GL11.glDisable(32826);
/* 79 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity entity) {
/* 85 */     return this.textureFile;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\RenderCrossed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */