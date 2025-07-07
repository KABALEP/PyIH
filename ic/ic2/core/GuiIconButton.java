/*    */ package ic2.core;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiIconButton
/*    */   extends GuiButton
/*    */ {
/*    */   private ResourceLocation texture;
/*    */   private int textureX;
/*    */   private int textureY;
/*    */   private ItemStack icon;
/*    */   private boolean drawQuantity;
/*    */   private RenderItem renderItem;
/*    */   
/*    */   public GuiIconButton(int id, int x, int y, int w, int h, ResourceLocation texture, int textureX, int textureY) {
/* 26 */     super(id, x, y, w, h, "");
/* 27 */     this.icon = null;
/* 28 */     this.texture = texture;
/* 29 */     this.textureX = textureX;
/* 30 */     this.textureY = textureY;
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiIconButton(int id, int x, int y, int w, int h, ItemStack icon, boolean drawQuantity) {
/* 35 */     super(id, x, y, w, h, "");
/* 36 */     this.icon = null;
/* 37 */     this.icon = icon;
/* 38 */     this.drawQuantity = drawQuantity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft minecraft, int i, int j) {
/* 43 */     super.func_146112_a(minecraft, i, j);
/* 44 */     if (this.icon == null) {
/*    */       
/* 46 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 47 */       minecraft.field_71446_o.func_110577_a(this.texture);
/* 48 */       func_73729_b(this.field_146128_h + 2, this.field_146129_i + 1, this.textureX, this.textureY, this.field_146120_f - 4, this.field_146121_g - 4);
/*    */     }
/*    */     else {
/*    */       
/* 52 */       if (this.renderItem == null)
/*    */       {
/* 54 */         this.renderItem = new RenderItem();
/*    */       }
/* 56 */       this.renderItem.func_77015_a(minecraft.field_71466_p, minecraft.field_71446_o, this.icon, this.field_146128_h + 2, this.field_146129_i + 1);
/* 57 */       if (this.drawQuantity)
/*    */       {
/* 59 */         this.renderItem.func_77021_b(minecraft.field_71466_p, minecraft.field_71446_o, this.icon, this.field_146128_h + 2, this.field_146128_h + 1);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\GuiIconButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */