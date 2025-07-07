/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderFlyingItem
/*    */   extends Render
/*    */ {
/*    */   public int itemIconIndex;
/*    */   public IIcon[] texturePath;
/*    */   
/*    */   public RenderFlyingItem(int icon, IIcon[] file) {
/* 24 */     this.itemIconIndex = icon;
/* 25 */     this.texturePath = file;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
/* 30 */     GL11.glPushMatrix();
/* 31 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 32 */     GL11.glEnable(32826);
/* 33 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 34 */     func_110776_a(TextureMap.field_110576_c);
/* 35 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 36 */     float f2 = this.texturePath[this.itemIconIndex].func_94209_e();
/* 37 */     float f3 = this.texturePath[this.itemIconIndex].func_94212_f();
/* 38 */     float f4 = this.texturePath[this.itemIconIndex].func_94206_g();
/* 39 */     float f5 = this.texturePath[this.itemIconIndex].func_94210_h();
/* 40 */     float f6 = 1.0F;
/* 41 */     float f7 = 0.5F;
/* 42 */     float f8 = 0.25F;
/* 43 */     GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
/* 44 */     GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
/* 45 */     tessellator.func_78382_b();
/* 46 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/* 47 */     tessellator.func_78374_a((0.0F - f7), (0.0F - f8), 0.0D, f2, f5);
/* 48 */     tessellator.func_78374_a((f6 - f7), (0.0F - f8), 0.0D, f3, f5);
/* 49 */     tessellator.func_78374_a((f6 - f7), (1.0F - f8), 0.0D, f3, f4);
/* 50 */     tessellator.func_78374_a((0.0F - f7), (1.0F - f8), 0.0D, f2, f4);
/* 51 */     tessellator.func_78381_a();
/* 52 */     GL11.glDisable(32826);
/* 53 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity entity) {
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderFlyingItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */