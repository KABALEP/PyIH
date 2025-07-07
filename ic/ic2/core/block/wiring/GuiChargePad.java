/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GuiChargePad
/*    */   extends GuiContainer
/*    */ {
/*    */   ContainerChargePad pad;
/*    */   
/*    */   public GuiChargePad(ContainerChargePad p_i1072_1_) {
/* 17 */     super((Container)p_i1072_1_);
/* 18 */     this.pad = p_i1072_1_;
/* 19 */     this.field_146291_p = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 25 */     String name = StatCollector.func_74838_a(this.pad.tile.type.getName());
/* 26 */     this.field_146289_q.func_78276_b(name, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(name) / 2, 6, 4210752);
/* 27 */     this.field_146289_q.func_78276_b(I18n.func_135052_a("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 5, 4210752);
/* 28 */     int e = this.pad.tile.storedEnergy;
/* 29 */     if (e > this.pad.tile.maxEnergy)
/*    */     {
/* 31 */       e = this.pad.tile.maxEnergy;
/*    */     }
/* 33 */     this.field_146289_q.func_78276_b(" " + e, 30, 19, 4210752);
/* 34 */     this.field_146289_q.func_78276_b("/" + this.pad.tile.maxEnergy, 30, 28, 4210752);
/*    */     
/* 36 */     GL11.glPushMatrix();
/* 37 */     GL11.glTranslatef(30.0F, 38.0F, 0.0F);
/* 38 */     GL11.glScalef(0.5F, 0.5F, 0.0F);
/* 39 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.chargePad.maxIn.name", new Object[] { Integer.valueOf(this.pad.tile.maxInput) }), 0, 0, 4210752);
/* 40 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 47 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIChargePad.png"));
/* 48 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 49 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 50 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 51 */     if (this.pad.tile.storedEnergy > 0) {
/*    */       
/* 53 */       int i1 = 1 + (int)(26.0F * this.pad.tile.storedEnergy / this.pad.tile.maxEnergy);
/* 54 */       func_73729_b(j + 10, k + 46 - i1, 194, 27 - i1, 12, i1);
/*    */     } 
/* 56 */     if (this.pad.tile.type.getTier() == 1) {
/*    */       
/* 58 */       func_73729_b(j + 133, k + 28, 176, 0, 18, 18);
/* 59 */       func_73729_b(j + 133, k + 57, 176, 0, 18, 18);
/*    */     } 
/* 61 */     int upgrades = this.pad.tile.inv.func_70302_i_() - ((this.pad.tile.type.getTier() > 1) ? 3 : 1);
/* 62 */     if (upgrades < 3) {
/*    */       
/* 64 */       func_73729_b(j + 80, k + 57, 176, 0, 18, 18);
/* 65 */       if (upgrades < 2)
/*    */       {
/* 67 */         func_73729_b(j + 60, k + 57, 176, 0, 18, 18);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\GuiChargePad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */