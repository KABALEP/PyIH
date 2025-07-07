/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ModelPersonalChest
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer wallR;
/*    */   ModelRenderer wallL;
/*    */   ModelRenderer wallB;
/*    */   ModelRenderer wallU;
/*    */   ModelRenderer wallD;
/*    */   public ModelRenderer door;
/*    */   
/*    */   public ModelPersonalChest() {
/* 20 */     this.field_78090_t = 64;
/* 21 */     this.field_78089_u = 64;
/* 22 */     this.wallR = (new ModelRenderer(this, 0, 0)).func_78789_a(0.0F, 0.0F, 0.0F, 14, 16, 1);
/* 23 */     this.wallR.func_78793_a(1.0F, 0.0F, 15.0F);
/* 24 */     this.wallR.func_78787_b(64, 64);
/* 25 */     this.wallR.field_78809_i = true;
/* 26 */     setRotation(this.wallR, 0.0F, 1.570796F, 0.0F);
/* 27 */     this.wallL = (new ModelRenderer(this, 0, 0)).func_78789_a(0.0F, 0.0F, 0.0F, 14, 16, 1);
/* 28 */     this.wallL.func_78793_a(15.0F, 0.0F, 1.0F);
/* 29 */     this.wallL.func_78787_b(64, 64);
/* 30 */     this.wallL.field_78809_i = true;
/* 31 */     setRotation(this.wallL, 0.0F, -1.570796F, 0.0F);
/* 32 */     this.wallB = (new ModelRenderer(this, 1, 1)).func_78789_a(1.0F, 1.0F, 0.0F, 12, 14, 1);
/* 33 */     this.wallB.func_78793_a(15.0F, 0.0F, 15.0F);
/* 34 */     this.wallB.func_78787_b(64, 64);
/* 35 */     this.wallB.field_78809_i = true;
/* 36 */     setRotation(this.wallB, 0.0F, 3.141593F, 0.0F);
/* 37 */     this.wallU = (new ModelRenderer(this, 1, 17)).func_78789_a(1.0F, 0.0F, 0.0F, 12, 14, 1);
/* 38 */     this.wallU.func_78793_a(1.0F, 0.0F, 15.0F);
/* 39 */     this.wallU.func_78787_b(64, 64);
/* 40 */     this.wallU.field_78809_i = true;
/* 41 */     setRotation(this.wallU, -1.570796F, 0.0F, 0.0F);
/* 42 */     this.wallD = (new ModelRenderer(this, 1, 17)).func_78789_a(1.0F, 0.0F, 0.0F, 12, 14, 1);
/* 43 */     this.wallD.func_78793_a(15.0F, 15.0F, 1.0F);
/* 44 */     this.wallD.func_78787_b(64, 64);
/* 45 */     this.wallD.field_78809_i = true;
/* 46 */     setRotation(this.wallD, -1.570796F, 3.141593F, 0.0F);
/* 47 */     this.door = (new ModelRenderer(this, 30, 0)).func_78789_a(0.0F, 0.0F, 0.0F, 12, 14, 1);
/* 48 */     this.door.func_78793_a(2.0F, 1.0F, 2.0F);
/* 49 */     this.door.func_78787_b(64, 64);
/* 50 */     this.door.field_78809_i = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderAll() {
/* 55 */     this.wallR.func_78785_a(0.0625F);
/* 56 */     this.wallL.func_78785_a(0.0625F);
/* 57 */     this.wallB.func_78785_a(0.0625F);
/* 58 */     this.wallU.func_78785_a(0.0625F);
/* 59 */     this.wallD.func_78785_a(0.0625F);
/* 60 */     this.door.func_78785_a(0.0625F);
/*    */   }
/*    */ 
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z) {
/* 65 */     model.field_78795_f = x;
/* 66 */     model.field_78796_g = y;
/* 67 */     model.field_78808_h = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\ModelPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */