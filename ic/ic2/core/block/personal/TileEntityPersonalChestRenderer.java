/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileEntityPersonalChestRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 15 */   private ModelPersonalChest model = new ModelPersonalChest();
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tile, double x, double y, double z, float partialTick) {
/* 19 */     if (!(tile instanceof TileEntityPersonalChest)) {
/*    */       return;
/*    */     }
/*    */     
/* 23 */     TileEntityPersonalChest safe = (TileEntityPersonalChest)tile;
/* 24 */     func_147499_a(new ResourceLocation("ic2", "textures/guiSprites/newsafe.png"));
/* 25 */     GL11.glPushMatrix();
/* 26 */     GL11.glEnable(32826);
/* 27 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 28 */     GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
/* 29 */     GL11.glScalef(1.0F, -1.0F, -1.0F);
/* 30 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 31 */     int angle = 0;
/* 32 */     switch (safe.getFacing()) {
/*    */ 
/*    */       
/*    */       case 2:
/* 36 */         angle = 180;
/*    */         break;
/*    */ 
/*    */       
/*    */       case 4:
/* 41 */         angle = 90;
/*    */         break;
/*    */ 
/*    */       
/*    */       case 5:
/* 46 */         angle = -90;
/*    */         break;
/*    */ 
/*    */       
/*    */       default:
/* 51 */         angle = 0;
/*    */         break;
/*    */     } 
/*    */     
/* 55 */     GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
/* 56 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 57 */     float lidAngle = safe.prevLidAngle + (safe.lidAngle - safe.prevLidAngle) * partialTick;
/* 58 */     lidAngle = 1.0F - lidAngle;
/* 59 */     lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
/* 60 */     this.model.door.field_78796_g = lidAngle * 3.141593F / 2.0F;
/* 61 */     this.model.renderAll();
/* 62 */     GL11.glDisable(32826);
/* 63 */     GL11.glPopMatrix();
/* 64 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\TileEntityPersonalChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */